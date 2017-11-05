package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;

import com.suramire.myapplication.base.BaseActivity;

/**
 * Created by Suramire on 2017/9/28.
 */

public class RentHistoryActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renthistory);
        setTitle(getIntent().getStringExtra("roomname"));
        ListView listView = (ListView) findViewById(R.id.list_history);
        ImageView imageView = (ImageView) findViewById(R.id.imageView20);
        listView.setEmptyView(imageView);
    }

}
