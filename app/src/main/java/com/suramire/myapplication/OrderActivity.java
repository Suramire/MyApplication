package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.suramire.myapplication.base.BaseActivity;

/**
 * Created by Suramire on 2017/10/4.
 */

public class OrderActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("我的订单");

    }


}
