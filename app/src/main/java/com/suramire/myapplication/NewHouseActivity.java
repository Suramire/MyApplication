package com.suramire.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.model.House;
import com.suramire.myapplication.util.MyDataBase;

/**
 * Created by Suramire on 2017/9/19.
 */

public class NewHouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhouse);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("新增房源");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        final EditText house_address = (EditText) findViewById(R.id.house_address);
        final EditText house_area = (EditText) findViewById(R.id.house_area);
        final EditText house_name = (EditText) findViewById(R.id.house_name);
        final EditText house_floor = (EditText) findViewById(R.id.house_floor);

        Button button = (Button) findViewById(R.id.btn_newhouse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String address = house_address.getText().toString().trim();
                final String area = house_area.getText().toString().trim();
                final String floor = house_floor.getText().toString().trim();
                final String name = house_name.getText().toString().trim();
                if(TextUtils.isEmpty(address)|| TextUtils.isEmpty(area)|| TextUtils.isEmpty(name)|| TextUtils.isEmpty(floor)){
                    Toast.makeText(NewHouseActivity.this, "清将房源信息填写完整", Toast.LENGTH_SHORT).show();
                }else{
                    MyDataBase myDataBase = new MyDataBase(NewHouseActivity.this,"test.db",null,1);
                    SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
                    int adminid = sharedPreferences.getInt("adminid", 0);
                    House house = new House(address,area,name,Integer.parseInt(floor),adminid);
                    long l = myDataBase.addHouse(house);
                    if(l>0){
                        Toast.makeText(NewHouseActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(NewHouseActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

