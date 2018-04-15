package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.suramire.myapplication.base.BaseActivity;


public class RecordsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
    }
}
