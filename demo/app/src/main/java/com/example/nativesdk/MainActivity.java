package com.example.nativesdk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebStorage;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tuia.ad.Ad;
import com.tuia.ad.DefaultAdCallBack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Ad ad = null;

    public static String slotId = "285730";
    public static String appkey = "3qKwty87tP6VxztdZB3CWnT5aNty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSplash = findViewById(R.id.btn_splash);
        Button buttonJumpSplash = findViewById(R.id.btn_jump_splash);
        Button buttonJumpInsert = findViewById(R.id.btn_jump_insert);
        Button buttonClean = findViewById(R.id.btn_clean);

        TextView tvSoltId = findViewById(R.id.tv_soltId);
        tvSoltId.setText("当前页面的广告位id:" + slotId);

        TextView tvAppkey = findViewById(R.id.tv_appkey);
        tvAppkey.setText("当前页面的appkey:" + appkey);
        buttonSplash.setOnClickListener(this);
        buttonJumpSplash.setOnClickListener(this);
        buttonJumpInsert.setOnClickListener(this);
        buttonClean.setOnClickListener(this);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //线上
        ad = new Ad(appkey, slotId, "jint");

        ad.init(this, null, new DefaultAdCallBack() {
            @Override
            public void onActivityClose() {
                ToastUtils.showShort("活动弹窗关闭");
            }

            @Override
            public void onActivityShow() {
                super.onActivityShow();
                ToastUtils.showShort("活动弹窗显示");
            }

            @Override
            public void onRewardClose() {
                ToastUtils.showShort("奖励弹窗关闭");
            }

            @Override
            public void onRewardShow() {
                super.onRewardShow();
                ToastUtils.showShort("奖励弹窗显示");
            }
        });
        getPermissions();

    }

    private void getPermissions() {
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }


        }
    }

    //  清除 deviceId 缓存
    public void clearCache(Context context) {
        SharedPreferences pref = context.getSharedPreferences("tuiad", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(context, "缓存清除成功，请重启app", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_splash:
                //ad.resetSlotId(slotId);
                ad.show();
                break;
            case R.id.btn_jump_splash:
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                break;
            case R.id.btn_jump_insert:
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
                break;
            case R.id.btn_clean:
                SPUtils.getInstance().clear(true);
                //清楚webview缓存
                WebStorage.getInstance().deleteAllData();
                //startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ad.resetAdSize(newConfig.orientation);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsume = ad.onKeyBack(keyCode, event);
        if (!isConsume) {
            return super.onKeyDown(keyCode, event);
        }

        return isConsume;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ad.destroy();
    }


}
