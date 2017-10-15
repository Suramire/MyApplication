package com.suramire.myapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.suramire.myapplication.adapter.MyBaseAdapter2;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/10/12.
 */

public class AmmeterActivity extends AppCompatActivity {

    private List<Ammeter> mAmmeterList;
    private MyBaseAdapter2 mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammeter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ListView listView = (ListView) findViewById(R.id.list_ammeter);
//        先获取所有房间信息
        MyDataBase myDataBase = new MyDataBase(AmmeterActivity.this, "test.db", null, 1);
        final SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        int adminid = sharedPreferences.getInt("adminid", 0);
        Cursor cursor = myDataBase.selectAllRoom(adminid);
        List<Integer> roomIdList = new ArrayList<>();
        List<String> roomNameList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                roomIdList.add(cursor.getInt(0));
                roomNameList.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor.close();
//        根据roomid获取读表信息
            mAmmeterList = new ArrayList<>();

            for (int i = 0; i < roomIdList.size(); i++) {
                Integer roomId = roomIdList.get(i);
                String roomName = roomNameList.get(i);
                Cursor cursor1 = myDataBase.selectAmmeter(roomId);
                if(cursor1.getCount()!=0){
                    //该房间存在读表
                    while (cursor1.moveToNext()){
                        Ammeter ammeter = new Ammeter(cursor1.getInt(cursor1.getColumnIndex("_id")),cursor1.getInt(cursor1.getColumnIndex("roomid")),cursor1.getInt(cursor1.getColumnIndex("count")));
                        ammeter.setRoomName(roomName);
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
            }

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
//            判断数据是否修改
            if(!isTheSame()){
                Toast.makeText(this, "弹出数据已修改是否保存", Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 判断两个list是否相等（当前读数）
     */
    private boolean isTheSame() {
        for (int i = 0; i < mAmmeterList.size(); i++) {
            if(mAmmeterList.get(i).getCount() != mAdapter.getData().get(i).getCount()){
                return false;
            }
        }
        return true;
    }
}
