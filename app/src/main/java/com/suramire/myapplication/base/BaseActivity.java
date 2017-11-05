package com.suramire.myapplication.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Suramire on 2017/10/29.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
    }
    public void setDisplayHomeAsUpEnabled(boolean flag){
        getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
