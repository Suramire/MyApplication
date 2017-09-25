package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/23.
 */

public class GetServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getservice);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("联系客服");
        ListView listView = (ListView) findViewById(R.id.list_service);
        List<String> strings = new ArrayList<>();
        strings.add("官网：http://fangxun360.com");
        strings.add("好好租：http://hhz360.com");
        strings.add("邮箱：service@fangxun360.com");
        strings.add("热线：400-883-0288");
        strings.add("QQ：2621502604");
        listView.setAdapter(new CommonAdapter<String>(this,R.layout.item_service,strings) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.textView33, item);
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
