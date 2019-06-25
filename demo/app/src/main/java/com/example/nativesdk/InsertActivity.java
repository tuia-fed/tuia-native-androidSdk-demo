package com.example.nativesdk;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tuia.ad.Ad;
import com.tuia.ad.DefaultAdCallBack;


public class InsertActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Ad ad = null;
    private FrameLayout mFlAdWrap;
    public static String slotId = "285731";
    public static String appkey = "3qKwty87tP6VxztdZB3CWnT5aNty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mFlAdWrap = findViewById(R.id.fl_ad_wrap);
        TextView tvId = findViewById(R.id.tv_id);
        tvId.setText("当前页面的广告位id:" + slotId);
        ad = new Ad(appkey, slotId);
        ad.init(this, mFlAdWrap, new DefaultAdCallBack() {
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
                LogUtils.vTag(TAG, "====onPrizeClose=====");
                ToastUtils.showShort("我的奖品弹窗关闭");
            }

            @Override
            public void onPrizeShow() {
                LogUtils.vTag(TAG, "====onPrizeShow=====");
                ToastUtils.showShort("我的奖品弹窗显示");
            }
        });
        ad.show();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //ad.resetAdSize(newConfig.orientation);
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
