package com.example.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tuia.ad.Ad;
import com.tuia.ad.AdCallBack;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 从后台可以获取下面这两个字段
        // 3qKwty87tP6VxztdZB3CWnT5aNty 加密后的媒体id
        // 270000 广告位id，根据不同的id展示不同的h5活动(需后台配置)
        final Ad ad =  new Ad("3qKwty87tP6VxztdZB3CWnT5aNty","270000");
        ad.setCallBack(new AdCallBack() {
            @Override
            public void close() {
                Toast.makeText(getApplicationContext(),"这是一个关闭回调",Toast.LENGTH_LONG).show();
            }
        });
        ad.init(this);
        ad.hide();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
    }
}
