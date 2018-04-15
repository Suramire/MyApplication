package com.suramire.myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.suramire.myapplication.R;

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

    /*为Activity切换提供动画效果*/
    public void startActivity(Class<?> cls){
        startActivity(new Intent(mContext,cls));
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
    /*为Activity退出提供动画效果*/
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
}
