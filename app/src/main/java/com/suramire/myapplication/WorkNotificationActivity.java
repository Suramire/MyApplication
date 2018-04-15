package com.suramire.myapplication;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Notification;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.util.ArrayList;
import java.util.List;


public class WorkNotificationActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknotification);
        setupTablayout();
        setTitle("工作提醒");
        initView();
        getData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.work_list);
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_layout);
        Button button = emptyView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NewNotificationActivity.class);
            }
        });
        listView.setEmptyView(emptyView);
    }


    private void setupTablayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);
//        为tablayout添加分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_divider));
        linearLayout.setDividerPadding(40);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0:{
                        getData();
                    }break;
                    case 1:{
                        listView.setAdapter(null);
                    }break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getData() {
        try {
            int adminid = (int) SPUtils.get("adminid", 0);
            MyDataBase myDataBase = new MyDataBase(WorkNotificationActivity.this,"test.db",null,1);
            Cursor cursor = myDataBase.selectNotification(adminid);
            List<Notification> notificationList = new ArrayList<>();
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String ncontent = cursor.getString(cursor.getColumnIndex("ncontent"));
                    String ndate = cursor.getString(cursor.getColumnIndex("ndate"));
                    notificationList.add(new Notification(ncontent, ndate, adminid));
                }

            }
            listView.setAdapter(new CommonAdapter<Notification>(this, R.layout.item_notification,notificationList) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, Notification item, int position) {
                    helper.setText(R.id.notification_content, item.getContent())
                            .setText(R.id.notification_date, item.getDate());
                    helper.setOnClickListener(R.id.notification_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(WorkNotificationActivity.this, "备注详情显示待实现", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            if(myDataBase!=null){
                myDataBase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_worknotification,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_new:
                startActivity(NewNotificationActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}