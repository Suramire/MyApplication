package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.suramire.myapplication.base.BaseActivity;


public class ShopItemDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        setTitle("商品详情");
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

}
