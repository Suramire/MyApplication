package com.suramire.myapplication;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.suramire.myapplication.adapter.MyBaseAdapter2;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Suramire on 2017/10/12.
 */

public class AmmeterActivity extends AppCompatActivity {

    private List<Ammeter> mAmmeterList;
    private MyBaseAdapter2 mAdapter;
    private List<String> mRoomNameList;
    private boolean[] mChecks;
    private boolean isSaved;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammeter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.list_ammeter);
        findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase myDataBase = new MyDataBase(AmmeterActivity.this,"test.db",null,1);
                if(!isTheSame()){
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        //保存本次读表记录
                        Ammeter ammeter = mAdapter.getData().get(i);
                        myDataBase.updateAmmeter(ammeter);
                        // TODO: 2017/10/28 生成历史读表记录
                        myDataBase.addAmHistory(ammeter);
                        Log.d("AmmeterActivity", "mAdapter.getData().get(i).getLastcount():" + mAdapter.getData().get(i).getLastcount());
                    }
                }
                myDataBase.close();
                isSaved = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(AmmeterActivity.this);
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AmmeterActivity.this);
                final android.app.AlertDialog dialog = builder.setTitle("请选择抄表房间")
                        .setMultiChoiceItems(roomNames, mChecks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        }).setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AmmeterActivity.this, "没有查询到智能设备", Toast.LENGTH_SHORT).show();
                            }
                        })
//                        .setNeutralButton("全选",null)
                        .setCancelable(false).create();
                        dialog.show();
            }
        });
        getData(mListView);


    }

    private void getData(ListView listView) {
        //        先获取所有房间信息
        MyDataBase myDataBase = new MyDataBase(AmmeterActivity.this, "test.db", null, 1);
        int adminid = (int) SPUtils.get("adminid", 0);
        Cursor cursor = myDataBase.selectAllRoom(adminid);
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
                Cursor cursor1 = myDataBase.selectAmmeter(roomId);
                if(cursor1.getCount()!=0){
                    //该房间存在读表
                    while (cursor1.moveToNext()){
                        Ammeter ammeter = new Ammeter(cursor1.getInt(cursor1.getColumnIndex("_id")),cursor1.getInt(cursor1.getColumnIndex("roomid")),cursor1.getInt(cursor1.getColumnIndex("count")));
                        ammeter.setRoomName(roomName);
                        Log.d("ss", cursor1.getInt(cursor1.getColumnIndex("sort"))+"");
                        ammeter.setLastcount(cursor1.getInt(cursor1.getColumnIndex("lastcount")));
                        ammeter.setSort(cursor1.getInt(cursor1.getColumnIndex("sort")));
                        mAmmeterList.add(ammeter);
                    }
                }else{
                    //该房间不存在读表 新建读表
                    int id = (int) myDataBase.addAmmeter(new Ammeter(roomId, 0));
                    Ammeter ammeter = new Ammeter(id,roomId,0);
                    ammeter.setRoomName(roomName);
                    mAmmeterList.add(ammeter);
                }
                cursor1.close();
            }


            if(mAmmeterList.size()!=0){
                Collections.sort(mAmmeterList);
                mAdapter = new MyBaseAdapter2(AmmeterActivity.this, mAmmeterList);
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
        for (int i = 0; i < mChecks.length; i++) {
            mChecks[i] = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
//            判断数据是否修改

            if(!isTheSame()&& !isSaved){
                AlertDialog.Builder builder = new AlertDialog.Builder(AmmeterActivity.this);
                builder.setTitle("抄表提示")
                        .setMessage("您已经抄表，是否不保存？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
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
}
