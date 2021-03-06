package com.suramire.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.MyDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 更换电表
 */

public class AmmeterChangeActivity extends BaseActivity {

    private MyDataBase mMyDataBase;
    private int mCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeammeter);
        setTitle("换表");
        EditText editText = (EditText) findViewById(R.id.old_count);
        EditText editText1 = (EditText) findViewById(R.id.old_time);
        final EditText editText2 = (EditText) findViewById(R.id.new_count);
        EditText editText3 = (EditText) findViewById(R.id.new_time);
//        获取上一个页面传过来的值
        final int roomid = getIntent().getIntExtra("roomid", 0);
        final int ammeterid = getIntent().getIntExtra("ammeterid", 0);
        mMyDataBase = new MyDataBase(AmmeterChangeActivity.this, "test.db", null, 1);
//        根据房间id获取对应电表度数
        Cursor cursor = mMyDataBase.selectAmmeter(roomid);
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                mCount = cursor.getInt(cursor.getColumnIndex("count"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                editText.setText(mCount + "");
                editText1.setText(time);
            }
            cursor.close();
        }
//简单日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        editText3.setText(format);
//保存新电表度数
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ammeter ammeter = new Ammeter(roomid,Integer.parseInt(editText2.getText().toString().trim()));
                ammeter.setLastcount(mCount);
                ammeter.setId(ammeterid);
                mMyDataBase.updateAmmeter(ammeter);
                Toast.makeText(AmmeterChangeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyDataBase!=null){
            mMyDataBase.close();
        }
    }
}
