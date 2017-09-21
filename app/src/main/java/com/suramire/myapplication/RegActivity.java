package com.suramire.myapplication;

import android.database.Cursor;
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

import com.suramire.myapplication.model.Admin;
import com.suramire.myapplication.util.MyDataBase;

/**
 * Created by Suramire on 2017/9/19.
 */

public class RegActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("房东注册");
        final EditText admin_name = (EditText) findViewById(R.id.admin_name);
        final EditText admin_password = (EditText) findViewById(R.id.admin_pasword);
        final EditText admin_password2 = (EditText) findViewById(R.id.admin_password2);

        Button reg = (Button) findViewById(R.id.btn_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = admin_name.getText().toString().trim();
                String password = admin_password.getText().toString().trim();
                String password2 = admin_password2.getText().toString().trim();
                if(name.length()==0||password.length()==0 ||password2.length()==0){
                    Toast.makeText(RegActivity.this, "请将信息补充完整", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(password2)){
                    Toast.makeText(RegActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else{

                    Admin admin = new Admin(name, password);
                    MyDataBase myDataBase = new MyDataBase(RegActivity.this,"test.db",null,1);
                    Cursor cursor = myDataBase.selectByName(name);
                    if(cursor.getCount()>0){
                        Toast.makeText(RegActivity.this, "该用户名已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                    }else{
                        long insert = myDataBase.addAdmin(admin);
                        if(insert>0){
                            Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
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
