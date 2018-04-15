package com.suramire.myapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.util.SPUtils;

import java.util.Calendar;
import java.util.Date;

import static com.suramire.myapplication.util.Common.SPLASHDELAY;



public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long now = calendar.getTimeInMillis();
        //记录上次启动的时间
        long lastTime = (long) SPUtils.get("lasttime",0L);
        //防止过程页频繁显示
        if(now -lastTime < SPLASHDELAY && lastTime!=0L){
            startActivity(LoginActivity.class);
            finish();
        }else{
            SPUtils.put("lasttime",now);
            setContentView(R.layout.activity_splash);
//            ImageView imageView = (ImageView) findViewById(R.id.imageView37);
//            Picasso.with(this).load(R.drawable.screen).placeholder(R.drawable.screen).into(imageView);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(3000);
                    startActivity(LoginActivity.class);
                    finish();
                }
            }).start();
        }

    }
}
