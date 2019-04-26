# 推啊（短链接）Android-SDK 说明文档

---

## 概述

通过使用推啊（短链接）Android-SDK，媒体可在自身 Activity 页面内载入推啊互动活动。

## 使用说明

> 步骤一：引入 aar 文件 


> 步骤二：打开activity的时候初始化

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
| viewGroup |  否 | LinearLayout | 全屏view | 展示互动广告的viewGroup |

> 设置回调

```
import com.tuia.ad.AdCallBack;

ad.setCallBack(new AdCallBack() {
  @Override
  public void close() {
      
  }
});
```

  - close 活动关闭按钮回调
  - 回调务必在init之前设置

> 步骤三 点击某个按钮展示活动

```
ad.show()
```

## 接口说明

> show 展示互动广告

```
ad.show();
```

> hide 隐藏互动广告

```
ad.hide();
```

## 兼容性

> Android 4.0 +

## 补充说明

该文档会存在不全的问题，会在后期补全，同步。