package com.example.nativesdk;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tuia.ad.BuildConfig;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-05-14-10:38
 * 描    述：h5 js广告
 * 修订历史：
 * ================================================
 */
public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    public static final String url = "http://yun.dui88.com/h5-mami/short-link/test/index.html";
    //public static final String url = "https://activity.tuidragon.com/activity/index?id=12526&slotId=290807&login=normal&appKey=jTghexH4wMYRNGtYyQrMb79a5PL&deviceId=5dcd48ab-68b2-4471-abf2-d7a8b9dd8426&dsm=1.290807.0.0&dsm2=1.290807.2.12526&tenter=SOW&subActivityWay=1&tck_rid_6c8=0ad0172cjx1tvkfr-4441298&tck_loc_c5d=tactivity-12526&dcm=401.290807.0.0&&tenter=SOW&specialType=0&userType=1&isTestActivityType=0";
    //public static final String url = "http://activity.russiango.cn/activity/index?id=12213&slotId=290913&login=normal&appKey=Y9QfBgZHDjQgir26UWWxDZDmfGU&deviceId=laoguo1&dsm=1.290913.0.0&dsm2=1.290913.2.10473&tenter=SOW&subActivityWay=0&tck_rid_6c8=0acc103djwagmdnm-36600965&tck_loc_c5d=tactivity-10473&dcm=401.290913.0.0&&tenter=SOW&isTestActivityType=0";
    //public static final String url = "http://yun.dui88.com/qiho/todayrob/vedio.html";
    //public static final String url = "http://yun.dui88.com/qiho/todayrob/1.mp4";

    WebView mWebView;
    TextView mBack;
    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webview);
        mBack = findViewById(R.id.tv_back);
        mTitle = findViewById(R.id.tv_title);
        mBack.setText("<");
        mBack.setOnClickListener(v -> {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        });
        initWebView();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //view.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        // For API level below 18 (This method was deprecated in API level 18)
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webSetting.setGeolocationEnabled(false);
        webSetting.setSupportZoom(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        //设置自适应手机屏幕
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.supportMultipleWindows();

        //设置可以访问文件
        webSetting.setAllowFileAccess(true);
        //视频相关设置
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setTextZoom(100);
        webSetting.setDomStorageEnabled(true);
        //webView 缓存相关设置
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        //设置可以缓存 和缓存策略
        webSetting.setAppCacheEnabled(true);
        //设置app缓存路径
        webSetting.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        //设置app 数据库缓存路径
        webSetting.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        //webSetting.setMediaPlaybackRequiresUserGesture(false);

        // 设置4.2以后版本支持autoPlay，非用户手势促发
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(true);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(2);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !BuildConfig.Release) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
