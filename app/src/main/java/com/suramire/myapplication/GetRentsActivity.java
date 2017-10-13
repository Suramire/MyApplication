package com.suramire.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.model.RentItem;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/21.
 */

public class GetRentsActivity extends AppCompatActivity {

    private String roomname;
    private View header;
    private TabLayout tabLayout;
    private ListView listView;
    private TextView tv_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getrents);
        setupActionBar();
        findViewById(R.id.button20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetRentsActivity.this, AmmeterActivity.class));
            }
        });
        header = LayoutInflater.from(this).inflate(R.layout.header_rents, null);
        tv_state = header.findViewById(R.id.item_roomstate);
        listView = (ListView) findViewById(R.id.rents_list);
        tabLayout = (TabLayout) findViewById(R.id.tab2);
//        为tablayout添加分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_divider));
        linearLayout.setDividerPadding(40);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listView.setAdapter(null);
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        showListNotPayed();
                        break;
                    case 1:showListPayed();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        RelativeLayout tv_empty = (RelativeLayout) findViewById(R.id.empty_layout);

        tv_empty.setVisibility(View.VISIBLE);
//        tv_empty.setText("暂无可收租房间");
        listView.setEmptyView(tv_empty);

        showListNotPayed();
    }

    private void setupActionBar() {
        //以下代码用于去除actionbar阴影
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("收租信息");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        supportActionBar.setTitle(null);
        String[] titles = {"收租管理"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,titles);
        supportActionBar.setListNavigationCallbacks(adapter,null);
        supportActionBar.setElevation(0);

    }

    private void showListNotPayed() {
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(header);
        }
        final MyDataBase myDataBase = new MyDataBase(GetRentsActivity.this, "test.db", null, 1);

        //未支付
        Cursor cursor = myDataBase.selectRenterInfoByPayed(0);
        final List<RentItem> rentItemList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                int roomid = cursor.getInt(cursor.getColumnIndex("roomid"));
                int rentInfoId = cursor.getInt(0);
                String datebegin = cursor.getString(cursor.getColumnIndex("datebegin"));
                String dateend = cursor.getString(cursor.getColumnIndex("dateend"));
                Float margin = cursor.getFloat(cursor.getColumnIndex("margin"));
                Cursor cursor1 = myDataBase.selectRoomByRoomid(roomid);
                if(cursor1.getCount()>0){
                    while (cursor1.moveToNext()){
                        roomname = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                    RentItem rentItem = new RentItem(rentInfoId, rentername, roomname, money + "", margin + "",datebegin,dateend);
                    rentItemList.add(rentItem);
                }
                tv_state.setText("待收租金(元)");
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(header);
                }
                listView.setAdapter(new CommonAdapter<RentItem>(this, R.layout.item_rents, rentItemList) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final RentItem item, final int position) {
                        helper.setText(R.id.rents_rentername, item.getRenterName())
                                .setText(R.id.rents_rentroom, item.getRentRoomName())
                                .setText(R.id.rents_money, item.getMoney());
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                          Intent intent = new Intent(GetRentsActivity.this, RentDetailActivity.class);
                                intent.putExtra("rentinfo", rentItemList.get(position));
                                startActivity(intent);

                            }
                        };
                        helper.setOnClickListener(R.id.rents_money, listener)
                                .setOnClickListener(R.id.rents_rentername, listener)
                                .setOnClickListener(R.id.rents_rentroom, listener);
                    }
                });
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.getTabAt(0).select();
        showListNotPayed();

    }



    private void showListPayed() {
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(header);
        }
        final MyDataBase myDataBase = new MyDataBase(GetRentsActivity.this, "test.db", null, 1);

        //已支付
        Cursor cursor = myDataBase.selectRenterInfoByPayed(1);
        List<RentItem> rentItemList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                int roomid = cursor.getInt(cursor.getColumnIndex("roomid"));
                int rentInfoId = cursor.getInt(0);
                String datebegin = cursor.getString(cursor.getColumnIndex("datebegin"));
                String dateend = cursor.getString(cursor.getColumnIndex("dateend"));
                Float margin = cursor.getFloat(cursor.getColumnIndex("margin"));
                Cursor cursor1 = myDataBase.selectRoomByRoomid(roomid);
                if(cursor1.getCount()>0){
                    while (cursor1.moveToNext()){
                        roomname = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                    RentItem rentItem = new RentItem(rentInfoId, rentername, roomname, money + "", margin + "",datebegin,dateend);
                    rentItemList.add(rentItem);
                }
                tv_state.setText("已收租金(元)");
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(header);
                }
                listView.setAdapter(new CommonAdapter<RentItem>(this, R.layout.item_rents, rentItemList) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final RentItem item, int position) {
                        helper.setText(R.id.rents_rentername, item.getRenterName())
                                .setText(R.id.rents_rentroom, item.getRentRoomName())
                                .setText(R.id.rents_money, item.getMoney());
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        };
                        helper.setOnClickListener(R.id.rents_money, listener)
                                .setOnClickListener(R.id.rents_rentername, listener)
                                .setOnClickListener(R.id.rents_rentroom, listener);
                    }
                });
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
