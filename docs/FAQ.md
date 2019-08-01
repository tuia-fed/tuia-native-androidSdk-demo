# 常见问题及处理方法

## 参数问题
「 ***appKey*** 」和「 ***slotId*** 」从哪里来的？
> 「 ***appKey*** 」和「 ***slotId*** 」是系统分配的加密字段，请在推啊后台-我的媒体中获取「 ***appKey*** 」，在推啊后台-我的广告位中获取广告位ID，即「 ***slotId*** 」。并且这两个字段和**收益**相关，请仔细确认。

若有多个投放位置需要如何对接？
> 一个投放位置对应一组「 ***appKey*** 」、「 ***slotId*** 」及一个「 ***init*** 」 方法，请勿将「 ***appKey*** 」和「 ***slotId*** 」搞混。

「 ***userId*** 」和「 ***deviceId*** 」是什么？
> 「 ***userId*** 」为媒体的自身用户ID，用于对接虚拟奖品，确定用户身份用。「 ***deviceId*** 」为设备号。若体系中只存在「 ***userId*** 」或者「 ***deviceId*** 」, 两者传入相同的值即可。若体系中不存在「 ***userId*** 」和「 ***deviceId*** 」，可以传入「***IMEI***」或媒体自身用于识别用户的编号

---

## 弹窗问题
为什么弹框上的关闭按钮点击没反应
> 1）请确任弹窗中webview的页面加载是否完成，因为h5和原生的交互通过jsbridge来完成，加入页面没有完全加载会导致jsbridge功能受影响。

>2）请确认ad对象init时是否传入了adWrap对象，弹窗式活动不需要adWrap对象，建议传空。同时弹窗式活动会自动铺满当前activity并自带蒙层。

为什么活动加载过长，一直处于loading状态
> 通过我们对接线上其他媒体的反映来看，活动的加载时长基本上在2s以内，因为活动的加载受网络状况的影响较大，对接时请确认网络状态良好，假如在wifi状态下加载时间过长，建议将网络切换为4G并再次加载。

为什么嵌入式互动无法完全显示
> 因为嵌入式活动的大小基本上受前端活动的样式影响，一般建议将ViewGroup的高度设置为230dp左右。具体的大小请联系我们的媒体确认。

---
## Android support库兼容问题
当你在接入我们Tuia的SDK以后编译出现如下错误：
```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:processDebugResources'.
> Android resource linking failed
  error: resource style/Theme.AppCompat.Light.NoActionBar (aka com.example.nativesdk:style/Theme.AppCompat.Light.NoActionBar) not found.
  /Users/jintai/.gradle/caches/transforms-2/files-2.1/5455e65c7cbe603cbd9b682457264906/res/values/values.xml:11:5-16:13: AAPT: error: style attribute 'attr/colorPrimary (aka com.example.nativesdk:attr/colorPrimary)' not found.
      
  /Users/jintai/.gradle/caches/transforms-2/files-2.1/5455e65c7cbe603cbd9b682457264906/res/values/values.xml:11:5-16:13: AAPT: error: style attribute 'attr/colorPrimaryDark (aka com.example.nativesdk:attr/colorPrimaryDark)' not found.
      
  /Users/jintai/.gradle/caches/transforms-2/files-2.1/5455e65c7cbe603cbd9b682457264906/res/values/values.xml:11:5-16:13: AAPT: error: style attribute 'attr/colorAccent (aka com.example.nativesdk:attr/colorAccent)' not found.
      
  error: failed linking references.

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org
```
### 原因:
Tuia的SDK采用的compileSdkVersion=28,suppotrSdkVersion=28,所以当你项目中的com.android.support低于Tuia SDK依赖的版本时便会出现以上错误。
### 解决方案：
1）将你项目中的compileSdkVersion升级为28，同时这也能让你的应用能够在高版本的手机系统上获得更好的兼容性。

2）你可以通过以下方式将Tuia SDK中support排除，只依赖你项目中的support库
```
implementation('com.tuia.ad:native_ad:1.0.4.5') {
    exclude group: "com.android.support"
}
```
---

## 其他问题
需要发放虚拟奖品怎么办？
> 奖品发放需要媒体有用户体系，请在调用 「 ***init*** 」 方法时传入正确的 「 ***userId*** 」并提供一个充值接口用于推啊后端主动调用。