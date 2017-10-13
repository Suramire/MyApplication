package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Suramire on 2017/10/3.
 */

public class FEInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feinfo);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("58品牌馆");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.imageView31);
        Picasso.with(this).load(R.drawable.fe_info).placeholder(R.drawable.loading).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
