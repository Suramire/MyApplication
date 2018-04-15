package com.suramire.myapplication;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.suramire.myapplication.adapter.MyBaseAdapter2;
import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AmmeterActivity2 extends BaseActivity {

    private List<Ammeter> mAmmeterList;
    private MyBaseAdapter2 mAdapter;
    private List<String> mRoomNameList;
    private boolean[] mChecks;
    private boolean isSaved;
    private ListView mListView;
    private MyDataBase mMyDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammeter);
        mMyDataBase = new MyDataBase(AmmeterActivity2.this,"test.db",null,1);
        setupActionBar();
        mListView = (ListView) findViewById(R.id.list_ammeter);
        findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(!isTheSame()){
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        //保存本次读表记录
                        Ammeter ammeter = mAdapter.getData().get(i);

                        mMyDataBase.updateAmmeter2(ammeter);
                        //生成历史读表记录
                        mMyDataBase.addAmHistory2(ammeter);
                    }
                }

                isSaved = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(AmmeterActivity2.this);
                builder.setTitle("保存提示")
                        .setMessage("保存成功！")
                        .setNegativeButton("去收租", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("确定", null).setCancelable(false)
                        .show();
            }
        });
        findViewById(R.id.button22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[]  roomNames = new String[mRoomNameList.size()];
                for (int i = 0; i < roomNames.length; i++) {
                    roomNames[i] = mRoomNameList.get(i);
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AmmeterActivity2.this);
                final android.app.AlertDialog dialog = builder.setTitle("请选择抄表房间")
                        .setMultiChoiceItems(roomNames, mChecks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        }).setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AmmeterActivity2.this, "没有查询到智能设备", Toast.LENGTH_SHORT).show();
                            }
                        })
//                        .setNeutralButton("全选",null)
                        .setCancelable(false).create();
                        dialog.show();
            }
        });
        getData(mListView);


    }

    private void setupActionBar() {
        setTitle("");
        ActionBar supportActionBar = getSupportActionBar();
        if(Build.VERSION.SDK_INT>=21){
            supportActionBar.setElevation(0);
        }
        //获取当前用户所有的房源信息
        int adminid = (int) SPUtils.get("adminid", 0);
        Cursor cursor = mMyDataBase.selectAllHouseByAdminId(adminid);
        int count = cursor.getCount();
        List<String> list = new ArrayList<>();
        if(count >0){
            while (cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor.close();
        }
        supportActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        supportActionBar.setTitle(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        supportActionBar.setListNavigationCallbacks(adapter,null);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getData(ListView listView) {
        //        先获取所有房间信息

        int adminid = (int) SPUtils.get("adminid", 0);
        Cursor cursor = mMyDataBase.selectAllRoom(adminid);
        List<Integer> roomIdList = new ArrayList<>();
        mRoomNameList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                roomIdList.add(cursor.getInt(0));
                mRoomNameList.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor.close();
//        根据roomid获取读表信息
            mAmmeterList = new ArrayList<>();

            for (int i = 0; i < roomIdList.size(); i++) {
                Integer roomId = roomIdList.get(i);
                String roomName = mRoomNameList.get(i);
                Cursor cursor1 = mMyDataBase.selectAmmeter2(roomId);
                if(cursor1.getCount()!=0){
                    //该房间存在读表
                    while (cursor1.moveToNext()){
                        //默认带id roomid 当前读数
                        Ammeter ammeter = new Ammeter(cursor1.getInt(cursor1.getColumnIndex("_id")),cursor1.getInt(cursor1.getColumnIndex("roomid")),cursor1.getInt(cursor1.getColumnIndex("count")));
                        //设置房间名
                        ammeter.setRoomName(roomName);
                        //设置上次读数
                        ammeter.setLastcount(cursor1.getInt(cursor1.getColumnIndex("lastcount")));
                        //上次读数
                        ammeter.setLastTime(cursor1.getString(cursor1.getColumnIndex("lasttime")));
                        //设置排序权重
                        ammeter.setSort(cursor1.getInt(cursor1.getColumnIndex("sort")));
                        //当前读数对应的日期
                        ammeter.setTime(cursor1.getString(cursor1.getColumnIndex("time")));
                        mAmmeterList.add(ammeter);
                    }
                }else{
                    //该房间不存在读表 新建读表
                    int id = (int) mMyDataBase.addAmmeter2(new Ammeter(roomId, 0));
                    Ammeter ammeter = new Ammeter(id,roomId,0);
                    ammeter.setRoomName(roomName);
                    mAmmeterList.add(ammeter);
                }
                cursor1.close();
            }


            if(mAmmeterList.size()!=0){
                Collections.sort(mAmmeterList);
                mAdapter = new MyBaseAdapter2(AmmeterActivity2.this, mAmmeterList,1);
                listView.setAdapter(mAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mAdapter.setSelectedItem(i);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                mAdapter.setSelectedItem(-1);//默认不选中任何项
                mAdapter.notifyDataSetChanged();

            }

        }

        mChecks = new boolean[100];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0x123,0,"水表").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,0x124,0,"电表").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0,0x125,0,"水表").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
//            判断数据是否修改

            if(!isTheSame()&& !isSaved){
                AlertDialog.Builder builder = new AlertDialog.Builder(AmmeterActivity2.this);
                builder.setTitle("抄表提示")
                        .setMessage("您已经抄表，是否不保存？")
                        .setPositiveButton("取消", null)
                        .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }else{
                finish();
            }


        }
        if(item.getItemId() == 0x124){
            startActivity(AmmeterActivity.class);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(mListView);
    }

    /**
     * 判断两个list是否相等（当前读数）
     */
    private boolean isTheSame() {
        if(mAmmeterList!=null){
            for (int i = 0; i < mAmmeterList.size(); i++) {
                if(mAmmeterList.get(i).getCount() != mAdapter.getData().get(i).getCount()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyDataBase == null) {
            mMyDataBase.close();
        }
    }
}
