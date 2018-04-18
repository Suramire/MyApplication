package com.suramire.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Room;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增房间页
 */

public class NewRoomActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newroom);
        setTitle("新增房间");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final EditText room_name = (EditText) findViewById(R.id.room_name);
        final EditText room_price = (EditText) findViewById(R.id.room_price);
        Button button = (Button) findViewById(R.id.btn_newroom);
//        连接数据库
        final MyDataBase myDataBase = new MyDataBase(NewRoomActivity.this, "test.db", null, 1);
        int adminid = (int) SPUtils.get("adminid", 0);
//        查询当前登录的房东拥有的所有房源
        Cursor cursor = myDataBase.selectAllHouseByAdminId(adminid);
        int count = cursor.getCount();
//        没有房源的情况
        if(count <1){
            Toast.makeText(this, "请先添加房源", Toast.LENGTH_SHORT).show();
            finish();
        }
        //存放房源名称
        List<String> strings = new ArrayList<>();
        //存放房源编号
        final List<Integer> houseIds = new ArrayList<>();
        while (cursor.moveToNext()){
            strings.add(cursor.getString(cursor.getColumnIndex("name")));
            houseIds.add(cursor.getInt(0));
        }
        final int[] position = new int[1];
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,strings);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                存放当前选择的房源下标
                position[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        保存房间按钮点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = room_name.getText().toString().trim();
                String price = room_price.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                    Toast.makeText(NewRoomActivity.this, "请将房间信息填写完整", Toast.LENGTH_SHORT).show();
                }else{
                    Float priceF = Float.parseFloat(price);
                    Room room = new Room(houseIds.get(position[0]),name,priceF);
                    long l = myDataBase.addRoom(room);
                    if(l>0){
                        Toast.makeText(NewRoomActivity.this, "房间添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(NewRoomActivity.this, "房间添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}
