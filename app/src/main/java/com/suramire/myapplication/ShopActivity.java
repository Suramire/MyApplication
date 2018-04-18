package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城页
 */

public class ShopActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setTitle("商城");
        ListView listView = (ListView) findViewById(R.id.shop_list);
        List<Integer> images = new ArrayList<>();
//        添加数据项
        images.add(R.drawable.item1);
        images.add(R.drawable.item2);
        images.add(R.drawable.item3);
        images.add(R.drawable.item4);
        images.add(R.drawable.item5);
        images.add(R.drawable.item6);
        images.add(R.drawable.item7);
        images.add(R.drawable.item8);
//        显示列表
        listView.setAdapter(new CommonAdapter<Integer>(this,R.layout.item_shop,images) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, Integer item, int position) {
                Picasso.with(ShopActivity.this)
                        .load(item)
                        .into((ImageView) helper.getView(R.id.shop_item));
                helper.setOnClickListener(R.id.shop_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(ShopItemDetailActivity.class);
                            }
                        });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        加载菜单
        getMenuInflater().inflate(R.menu.menu_shop,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        返回键点击事件
        if(item.getItemId() == android.R.id.home){
            finish();
        }
//        "我的订单"点击事件
        if (item.getItemId() == R.id.menu_order) {
            startActivity(OrderActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
