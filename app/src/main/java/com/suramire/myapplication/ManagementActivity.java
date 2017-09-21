package com.suramire.myapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
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
        final TextView textView = (TextView) findViewById(R.id.text_empty);
        final ListView listView = (ListView) findViewById(R.id.list_room);
        listView.setEmptyView(textView);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("房源管理");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        TabLayout layout = (TabLayout) findViewById(R.id.tab_room);
        final TabLayout.Tab mTab = layout.newTab().setText("已出租");
        final TabLayout.Tab mTab1 = layout.newTab().setText("未出租");
        final TabLayout.Tab mTab2 = layout.newTab().setText("即将到期");
        headerView = LayoutInflater.from(this).inflate(R.layout.header_room, null);
        myDataBase = new MyDataBase(ManagementActivity.this,"test.db",null,1);
        layout.addTab(mTab);
        layout.addTab(mTab1);
        layout.addTab(mTab2);
        layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listView.setAdapter(null);
                if(tab == mTab){
                    textView.setText("暂无出租房源");
                    selectRoomLend(listView);
                    textView.setVisibility(View.VISIBLE);
                }
                if(tab == mTab1){

                    textView.setText("暂无未出租房源");
                    selectRoomNotLend(listView);
                    textView.setVisibility(View.VISIBLE);

                }
                if(tab == mTab2){

                    textView.setText("暂无过期房源");
                    textView.setVisibility(View.VISIBLE);
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

    private void setupListview(ListView listView, final List<Room> rooms, final String state) {
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(new CommonAdapter<Room>(ManagementActivity.this, R.layout.item_room,rooms) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, Room item, final int position) {
                helper.setText(R.id.item_roomname, item.getName())
                        .setText(R.id.item_roomstate, state)
                        .setText(R.id.item_roomprice, item.getPrice() + "");
                helper.setOnClickListener(R.id.op_roominfo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ManagementActivity.this, EditRoomActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("room", rooms.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                        .setOnClickListener(R.id.op_rent, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(rooms.get(position).getIsLended() ==1){
                                    Toast.makeText(ManagementActivity.this, "该房间已被租用,请选择其他房间", Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent intent = new Intent(ManagementActivity.this, NewRentActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("room", rooms.get(position));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }



                            }
                        })
                        .setOnClickListener(R.id.op_up, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(ManagementActivity.this, "提租", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnClickListener(R.id.op_renterinfo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Cursor cursor = myDataBase.selectRenterInfo(rooms.get(position).getId());
                                if(cursor.getCount()>0){
                                    if (cursor.moveToLast()){
                                        String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                                        String renterphone = cursor.getString(cursor.getColumnIndex("renterphone"));
                                        String margin = cursor.getString(cursor.getColumnIndex("margin"));
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ManagementActivity.this);
                                        builder.setTitle("租客信息")
                                                .setMessage("姓名：" + rentername + "\n\n电话：" + renterphone+"\n\n已交押金："+margin);
                                        builder.show();
                                        // TODO: 2017/9/21 已出租的房子不能再出租
                                    }
                                }

                            }
                        });
            }
        });
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
            }break;
            case android.R.id.home:finish();break;
        }

        return super.onOptionsItemSelected(item);
    }
}
