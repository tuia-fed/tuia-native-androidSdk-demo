# 推啊原生互动示例工程 - 安卓

- [原生互动产品介绍](/docs/product.pdf)
- [技术对接文档](/docs/TUIA-SDK.md)
- [sdk更新记录](/docs/CHANGELOG.md)
- [FAQ](/docs/FAQ.md)


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

假如https://raw.githubusercontent.com/tuia-fed/tuia-native-androidSdk/master这个仓库不能down，请将仓库地址换成https://gitee.com/jtsky/tuia-native-androidSdk/raw/master试下


2)项目中添加广告aar
```
implementation 'com.tuia.ad:native_ad:1.0.3'
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

> 步骤三：Activity中单个广告初始化
```
import com.tuia.ad.Ad;

Ad ad = new Ad(appKey,slotId,deviceId);
ad.init(activity,viewGroup);
```

参数说明

| 参数名 | 必填 | 类型   | 默认值 |          描述               |
| ------ | :--: | ------ | --------- | ------------------ |
| appKey |  是  | string |  | 系统分配的加密字段 |
| slotId |  是  | string |  | 广告位id(需根据该id后台配置活动) |
| deviceId |  否  | string |  UUID.randomUUID | 用户唯一身份标识 |
| activity |  是  | Activity |   | 展示互动广告的activity |
| viewGroup |  否 | FrameLayout | null | 展示互动广告的viewGroup viewGroup为空的情况下即为插屏广告否则即为嵌入式广告 |

> 设置回调

此回调只针对插屏广告有效
```
ad.setCallBack(new AdCallBack() {
            @Override
            public void close() {
                //完全关闭插屏广告时回调
            }
        });
```


 

> 步骤三 点击某个按钮展示广告或者进入页面直接展现广告

```
ad.show()
```

## 接口说明

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

1、android  minSdkVersion=16

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

appcompat-v7:28.0.0

recyclerview-v7:28.0.0

design:28.0.0

jsbridge:1.0.4

okhttp:3.8.1

gson:2.8.2

如果跟你的项目中的库发生冲突，请采用以下方式排除冲突，以support和gson为例：

```
implementation('com.tuia.ad:native_ad:1.0.3') {
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

## 补充说明

该文档会存在不全的问题，会在后期补全，同步。
