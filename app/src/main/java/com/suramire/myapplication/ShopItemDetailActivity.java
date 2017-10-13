package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Suramire on 2017/10/4.
 */

public class ShopItemDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("商品详情");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.imageView15);
        Picasso.with(this).load(R.drawable.printer).placeholder(R.drawable.loading).into(imageView);
        Button button = (Button) findViewById(R.id.button19);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShopItemDetailActivity.this, "待实现", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
