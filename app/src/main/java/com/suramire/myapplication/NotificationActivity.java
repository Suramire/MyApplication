package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.suramire.myapplication.base.BaseActivity;

/**
 * Created by Suramire on 2017/9/24.
 */

public class NotificationActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle("消息中心");
    }

}
