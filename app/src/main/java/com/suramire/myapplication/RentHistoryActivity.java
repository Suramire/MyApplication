package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Suramire on 2017/9/28.
 */

public class RentHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renthistory);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getIntent().getStringExtra("roomname"));
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ListView listView = (ListView) findViewById(R.id.list_history);
        ImageView imageView = (ImageView) findViewById(R.id.imageView20);
        listView.setEmptyView(imageView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
