# 推啊（短链接）Android-SDK 说明文档

---

## 概述

通过使用推啊（短链接）Android-SDK，媒体可在自身 Activity 页面内载入推啊互动活动。

## 使用说明

> 步骤一：引入aar

1)添加远程仓库
```
repositories {
        google()
        jcenter()
        maven { url "https://raw.githubusercontent.com/tuia-fed/tuia-native-androidSdk/master" }
    }
```
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://raw.githubusercontent.com/tuia-fed/tuia-native-androidSdk/master" }
    }
}
```

建议将tuia创库放在repositories中的第一顺位并打开翻墙工具VPN，这样可以保证tuia的广告sdk率先加载。假如还是没有down下来，建议使用命令行试试。比如：

windows：gradlew clean assembleDebug

mac ：./gradlew clean assembleDebug

假如
https://raw.githubusercontent.com/tuia-fed/tuia-native-androidSdk/master
这个仓库不能down，请将仓库地址换成
https://gitee.com/jtsky/tuia-native-androidSdk/raw/master
试下

2)项目中添加广告aar
```
implementation 'com.tuia.ad:native_ad:1.0.4.7'
```
具体的版本号请参考：https://github.com/tuia-fed/tuia-native-androidSdk


> 步骤二：Application广告配置初始化
```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TuiaAdConfig.init(this);
    }
}
```

> 步骤三：Activity中单个广告初始化(建议在onCreate生命周期中初始化)
```
import com.tuia.ad.Ad;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Ad ad = new Ad(appKey,slotId, userId,deviceId);
    ad.init(activity,viewGroup,AdCallBack);
  }
```
AdCallBack的注释如下
```
public interface AdCallBack {
    /**
     * 活动弹窗关闭回调 只针对弹窗类型广告有效
     */
    void onActivityClose();

    /**
     * 活动弹窗show回调 只针对弹窗类型广告有效
     */
    void onActivityShow();

    /**
     * 奖励弹窗关闭回调
     */
    void onRewardClose();

    /**
     * 奖励弹窗show回调
     */
    void onRewardShow();

    /**
     * 我的奖品页弹窗关闭回调
     */
    void onPrizeClose();

    /**
     * 我的奖品页弹窗show回调
     */
    void onPrizeShow();

}
```

AdCallBack的默认实现为DefaultAdCallBack，已在sdk中实现可以直接调用。

参数说明

| 参数名 | 必填 | 类型   | 默认值 |          描述               |
| ------ | :--: | ------ | --------- | ------------------ |
| appKey |  是  | string | 000000 | 系统分配的加密字段 |
| slotId |  是  | string（可空） | 000000 | 广告位id(需根据该id后台配置活动) |
| userId |  否  | string |  deviceId | 媒体端对接虚拟奖品是需要设置 |
| deviceId |  否  | string |  UUID.randomUUID | 用户唯一身份标识 |
| activity |  是  | Activity |   | 展示互动广告的activity |
| viewGroup |  否 | FrameLayout | null | 展示互动广告的viewGroup viewGroup为空的情况下即为插屏广告否则即为嵌入式广告 |
| AdCallBack | 否 | AdCallBack | null|弹窗行为回调 包括show 和dismiss |

> 步骤三 设置soltId (不是必须的 slotId可以在new Ad()时初始化，resetSlotId()方法的作用主要是方便某些媒体需要根据后端接口动态配置广告位Id)

```
ad.resetSlotId(slotId)
```

> 步骤四 点击某个按钮展示广告或者进入页面直接展现广告

```
ad.show()
```

## 接口说明
>new Ad(appKey,slotId)
>new Ad(appKey,slotId,userId)
>new Ad(appKey,slotId,userId,deviceId)

初始化Ad对象，其中appKey为必填选项,slotId可以为空或者申请默认广告位。假如初始化slotId为空，必须在展现广告前通过resetSlotId方法设置广告位id。userId当用户需要对接虚拟奖品并且有用户体系时可以传，不传的时候默认userId等于deviceId，deviceId可以为空，sdk内部根据UUID自动分配。

appKey和slotId请联系推啊对接人员获取。

>ad.init(activity,viewGroup,AdCallBack)

其中viewGroup可以为空，假如viewGroup为空则sdk默认展现dialog类型广告，否则展现嵌入在你指定布局文件中的嵌入式广告。AdCallBack则为弹窗的行为回调包括show和dismiss

>resetSlotId 重置广告位id

```
ad.resetSlotId(slotId)
```

针对需要通过接口动态下发广告位的媒体

> show 展示互动广告

```
ad.show();
```

> hide 隐藏互动广告 只针对插屏广告有效

```
ad.hide();
```

> ad 横竖屏切换（保留api 暂未对横屏活动进行适配 不建议开发者在横屏模式下进行广告展现）

```
 <activity
            android:name=".MainActivity"
            android:configChanges="screenSize|orientation">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```
重点代码：android:configChanges="screenSize|orientation"

不设置以上代码横竖屏切换时会导致弹窗消失

然后重写activity的onConfigurationChanged方法

```
@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ad.resetAdSize(newConfig.orientation);

    }
```


>弹窗广告的返回拦截
```
 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsume = ad.onKeyBack(keyCode, event);
        if (!isConsume) {
            return super.onKeyDown(keyCode, event);
        }

        return isConsume;
    }
```

>广告的资源回收
```
@Override
    protected void onDestroy() {
        super.onDestroy();
        ad.destroy();
    }
```
建议在activity销毁时进行资源回收 否则有可能会造成内存泄漏

## 兼容性

1、android  minSdkVersion=14

2、Google表示，为保证用户数据和设备的安全，针对下一代 Android 系统(Android P) 的应用程序，将要求默认使用加密连接，这意味着 Android P 将禁止 App 使用所有未加密的连接，因此运行 Android P 系统的安卓设备无论是接收或者发送流量，未来都不能明码传输，需要使用下一代(Transport Layer Security)传输层安全协议，而 Android Nougat 和 Oreo 则不受影响。假如你的应用已经适配Android 9.0，即targetSdkVersion=28，为使广告SDK正常使用，请务必进行http的适配。以下两种解决方案提供参考：
1）参考文档：https://www.cnblogs.com/renhui/p/9921790.html

2）
```
<application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:networkSecurityConfig="@xml/tuia_network_security_config"
        android:roundIcon="@mipmap/ic_launcher_round"></application>
```
android:networkSecurityConfig="@xml/tuia_network_security_config" 是重点。tuia_network_security_config文件已包含在aar包中，直接调用即可。

3、第三方库冲突解决
本sdk中依赖的第三方库如下：

com.android.support:***:28.0.0

okhttp:3.14.2

gson:2.8.2


如果跟你的项目中的第三方库发生版本冲突，请采用以下方式排除冲突，以support和gson为例：

```
implementation('com.tuia.ad:native_ad:1.0.4') {
        exclude group: 'com.android.support'
        exclude group: 'com.google.code.gson'
    }
```

## 权限

本广告SDK需要的动态权限为
```
Manifest.permission.WRITE_EXTERNAL_STORAGE

Manifest.permission.READ_EXTERNAL_STORAGE
```
针对6.0及以上系统如果不赋予以上动态权限 则会影响正常的下载和安装apk

## 混淆 

如果你的app采用的是第三方加固 可以不用添加混淆文件
假如需要自定义混淆 参考如下： 
```
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.推啊广告---------------------------------

-keep class com.tuia.ad_base.** { *; }

-keep class com.tuia.ad.** { *; }

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#gson
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }


#androidUtil
-keep class com.blankj.utilcode.**{*; }

#json
-keep class org.json.** { *; }


#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------


#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------



```



## 补充说明

该文档会存在不全的问题，会在后期补全，同步。
