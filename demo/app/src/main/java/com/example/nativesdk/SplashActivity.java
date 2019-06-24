package com.example.nativesdk;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tuia.ad.Ad;
import com.tuia.ad.DefaultAdCallBack;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Ad ad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tvId = findViewById(R.id.tv_id);
        tvId.setText("当前页面的广告位id:285746");
        ad = new Ad("3qKwty87tP6VxztdZB3CWnT5aNty", "285746");
        //测试
        //ad = new Ad("3xWBQRwCbh4J5NpomhxCWHrRx3pe", "258497");
        ad.init(this, null, new DefaultAdCallBack() {
            @Override
            public void onActivityClose() {
                ToastUtils.showShort("活动弹窗关闭");
            }

            @Override
            public void onRewardClose() {
                ToastUtils.showShort("奖励弹窗关闭");
            }

            @Override
            public void onPrizeClose() {
                ToastUtils.showShort("我的奖品弹窗关闭");
            }
        });
        ad.show();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ad.resetAdSize(newConfig.orientation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ad.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isDeal = ad.onKeyBack(keyCode, event);
        if (!isDeal) {
            return super.onKeyDown(keyCode, event);
        }

        return isDeal;
    }
}
