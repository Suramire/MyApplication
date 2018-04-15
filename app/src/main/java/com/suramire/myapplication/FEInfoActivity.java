package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.suramire.myapplication.base.BaseActivity;


public class FEInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feinfo);
        setTitle("58品牌馆");

        ImageView imageView = (ImageView) findViewById(R.id.imageView31);
        Picasso.with(this).load(R.drawable.fe_info).placeholder(R.drawable.loading).into(imageView);
    }


}
