package com.suramire.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.util.SPUtils;


public class MoreActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        setTitle("更多");

        findViewById(R.id.imageView34).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            startActivity(ProfileActivity.class);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView34:{
                SPUtils.put("autologin",0);
                Intent intent = new Intent(MoreActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(ProfileActivity.class);
        finish();
    }
}
