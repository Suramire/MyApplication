package com.suramire.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.base.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/10/1.
 */

public class HelpActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("帮助中心");
        final View header = LayoutInflater.from(this).inflate(R.layout.header_help, null);
        final View footer = LayoutInflater.from(this).inflate(R.layout.footer_help, null);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this,FeedbackActivity.class));
            }
        });
        SearchView searchView = (SearchView) header.findViewById(R.id.help_search);
//        去除搜索框下划线
        Class<?> c=searchView.getClass();
        try {
            Field f=c.getDeclaredField("mSearchPlate");//通过反射，获得类对象的一个属性对象
            f.setAccessible(true);//设置此私有属性是可访问的
            View v=(View) f.get(searchView);//获得属性的值
            v.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView listView = (ListView) findViewById(R.id.help_list);


        listView.addHeaderView(header);
        listView.addFooterView(footer);

        List<String> titles = new ArrayList<>();
        titles.add("如何快速出租?");
        titles.add("智能电表如何配置?以及如何使用?");
        titles.add("如何快速打印所有租客的收费单据?");
        titles.add("如何使用收租功能?");
        titles.add("如何催租?让租客更快交租");
        listView.setAdapter(new CommonAdapter<String>(HelpActivity.this,R.layout.item_help,titles) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.help_title, item);
            }
        });
    }


}
