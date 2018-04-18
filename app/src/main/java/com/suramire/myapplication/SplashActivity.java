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

/**
 * 启动界面
 */


public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long now = calendar.getTimeInMillis();
        //记录上次启动的时间
        long lastTime = (long) SPUtils.get("lasttime",0L);
        //防止过程页频繁显示 3分钟之内再次打开程序则直接进入登录页
        if(now -lastTime < SPLASHDELAY && lastTime!=0L){
            startActivity(LoginActivity.class);
            finish();
        }else{
//            3分钟后打开程序 先显示过场图片 在进入登录页
            SPUtils.put("lasttime",now);
            setContentView(R.layout.activity_splash);
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
