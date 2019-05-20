package com.example.nativesdk;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tuia.ad.Ad;


public class InsertActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Ad ad = null;
    private FrameLayout mFlAdWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mFlAdWrap = findViewById(R.id.fl_ad_wrap);
        TextView tvId = findViewById(R.id.tv_id);
        tvId.setText("当前页面的广告位id:285742");
        ad = new Ad("3qKwty87tP6VxztdZB3CWnT5aNty", "285742");

        ad.init(this, mFlAdWrap);
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
