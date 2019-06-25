package com.example.nativesdk;

import android.app.Application;

import com.tuia.ad.TuiaAdConfig;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-04-30-17:53
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        TuiaAdConfig.init(this);
    }
}
