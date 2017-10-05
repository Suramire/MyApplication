package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Suramire on 2017/10/3.
 */

public class WorkNotification extends AppCompatActivity {
    private TabLayout tabLayout;
    private RelativeLayout emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknotification);
        tabLayout = (TabLayout) findViewById(R.id.tab2);
//        为tablayout添加分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_divider));
        linearLayout.setDividerPadding(40);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("工作提醒");
        supportActionBar.setElevation(0);

        ListView listView = (ListView) findViewById(R.id.work_list);
        emptyView = (RelativeLayout) findViewById(R.id.empty_layout);
        Button button = emptyView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkNotification.this, "待实现", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setEmptyView(emptyView);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}