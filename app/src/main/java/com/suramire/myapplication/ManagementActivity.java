package com.suramire.myapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.suramire.myapplication.adapter.MyBaseAdapter;
import com.suramire.myapplication.model.Room;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/19.
 */

public class ManagementActivity extends AppCompatActivity {


    private View headerView;
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        //以下代码用于去除actionbar阴影
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_layout);
        final ListView listView = (ListView) findViewById(R.id.list_room);
        Button button = emptyView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        listView.setEmptyView(emptyView);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        supportActionBar.setTitle(null);
        String[] titles = {"房源管理"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,titles);
        supportActionBar.setListNavigationCallbacks(adapter,null);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        //为tablayout添加分割线
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_room);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_divider));
        linearLayout.setDividerPadding(40);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_room, null);
        myDataBase = new MyDataBase(ManagementActivity.this,"test.db",null,1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listView.setAdapter(null);

                if(tab.getPosition() == 0){
//                    textView.setText("暂无出租房源");
                    selectRoomLend(listView);
//                    textView.setVisibility(View.VISIBLE);
                }
                if(tab.getPosition() == 1){

//                    textView.setText("暂无未出租房源");
                    selectRoomNotLend(listView);
//                    textView.setVisibility(View.VISIBLE);

                }
                if(tab.getPosition() == 2){

//                    textView.setText("暂无过期房源");
//                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        selectRoomLend(listView);

    }

    private void selectRoomNotLend(ListView listView) {


        SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        int adminid = sharedPreferences.getInt("adminid", 0);
        Cursor cursorHouse = myDataBase.selectAllHouseByAdminId(adminid);//先查询房东所有的房源
        //再根据房源找出房间
        List<Integer> houseIds = new ArrayList<>();
        if(cursorHouse.getCount()>0){
            while (cursorHouse.moveToNext()){
               houseIds.add(cursorHouse.getInt(0));
            }
        }
        if(houseIds.size()>0){
            //有房源
            final List<Room> rooms = new ArrayList<>();
            rooms.clear();
            for (int i = 0; i < houseIds.size(); i++) {
                Cursor cursor = myDataBase.selectAllRoomNotLend(houseIds.get(i));
                if(cursor.getCount()>0){
                    //有房间 未租
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                        int isLended = cursor.getInt(cursor.getColumnIndex("islend"));
                        int id = cursor.getInt(0);
                        Room room = new Room();
                        room.setId(id);
                        room.setIsLended(isLended);
                        room.setName(name);
                        room.setPrice(price);
                        rooms.add(room);

                    }
                    setupListview(listView, rooms,"未出租");

                }
            }
        }
    }

    public int currentPosition = -1;

    private void setupListview(ListView listView, final List<Room> rooms, final String state) {
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(new MyBaseAdapter(ManagementActivity.this,rooms));
    }

    private void selectRoomLend(ListView listView) {
        MyDataBase myDataBase = new MyDataBase(ManagementActivity.this,"test.db",null,1);
        SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        int adminid = sharedPreferences.getInt("adminid", 0);
        Cursor cursorHouse = myDataBase.selectAllHouseByAdminId(adminid);//先查询房东所有的房源
        //再根据房源找出房间
        List<Integer> houseIds = new ArrayList<>();
        if(cursorHouse.getCount()>0){
            while (cursorHouse.moveToNext()){
                houseIds.add(cursorHouse.getInt(0));
            }
        }
        if(houseIds.size()>0){
            //有房源
            final List<Room> rooms = new ArrayList<>();
            for (int i = 0; i < houseIds.size(); i++) {
                Cursor cursor = myDataBase.selectAllRoomLend(houseIds.get(i));
                if(cursor.getCount()>0){
                    rooms.clear();
                    //有房间 未租
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                        int isLended = cursor.getInt(cursor.getColumnIndex("islend"));
                        int id = cursor.getInt(0);
                        Room room = new Room();
                        room.setId(id);
                        room.setIsLended(isLended);
                        room.setName(name);
                        room.setPrice(price);
                        rooms.add(room);
                    }
                    setupListview(listView, rooms,"已出租");

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newhouse,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.newhouse:{
                showDialog();
            }break;
            case android.R.id.home:finish();break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        final String[] names = {"集中式房源","分散式房源","添加房间"};
        builder.setItems(names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                    case 1:{
                       startActivity(new Intent(ManagementActivity.this,NewHouseActivity.class));
                    }break;
                    case 2:{
                        startActivity(new Intent(ManagementActivity.this,NewRoomActivity.class));
                    }break;
                }

            }
        });
        builder.setTitle("新增房源");

        builder.setNegativeButton("取消", null
        );
        builder.setCancelable(false).show();
    }
}
