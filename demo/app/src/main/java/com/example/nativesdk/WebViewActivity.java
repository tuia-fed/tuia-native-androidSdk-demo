package com.example.nativesdk;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tuia.ad.BuildConfig;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-05-14-10:38
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WebViewActivity extends AppCompatActivity {
    public static final String url = "http://172.16.61.75:5555/activity/shortLink/bridge_scratchCard_embed/entry.html";

    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webview);
        initWebview();
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(url);

    }

    private void initWebview() {
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

//        if (getWebViewType() == BaseJsBridgeWebView.WEBVIEW_TYPE_ACTIVITY) {
//            //是否阻塞加载网络网络图像 先阻塞图片加载然后等页面加载完毕再去加载图片  提升这个页面的加载速度
//            webSetting.setBlockNetworkImage(true);
//            //先设置不加载图片资源
//            webSetting.setLoadsImagesAutomatically(false);
//        } else {
//            //是否阻塞加载网络网络图像 先阻塞图片加载然后等页面加载完毕再去加载图片  提升这个页面的加载速度
//            webSetting.setBlockNetworkImage(false);
//            //先设置不加载图片资源
//            webSetting.setLoadsImagesAutomatically(true);
//        }

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

        // 设置4.2以后版本支持autoPlay，非用户手势促发
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(true);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
    }
}
