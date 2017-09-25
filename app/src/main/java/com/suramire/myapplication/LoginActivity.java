package com.suramire.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.util.CustomDialog;
import com.suramire.myapplication.util.MyDataBase;

/**
 * Created by Suramire on 2017/9/19.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_reg = (Button) findViewById(R.id.btn_reg);
        final EditText text_username = (EditText) findViewById(R.id.text_username);
        final EditText text_password = (EditText) findViewById(R.id.text_password);
        final SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        if ("".equals(name) || "".equals(password)) {
        }else{
            text_username.setText(name);
            text_password.setText(password);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password = text_password.getText().toString().trim();
                final String name = text_username.getText().toString().trim();
                if("".equals(name)||"".equals(password)){
                    Toast.makeText(LoginActivity.this, "请将登录信息填写完整", Toast.LENGTH_SHORT).show();
                }else{
                    final CustomDialog customDialog = new CustomDialog(LoginActivity.this,R.style.CustomDialog);
                    customDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SystemClock.sleep(1000);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyDataBase myDataBase = new MyDataBase(LoginActivity.this,"test.db",null,1);
                                    Cursor cursor = myDataBase.selectByName(name);
                                    cursor.moveToFirst();
                                    if(cursor.getCount()<1){
                                        Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                                    } else if(password.equals(cursor.getString(2))){
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        //登录成功后保存用户名密码
                                        SharedPreferences.Editor edit = sharedPreferences.edit();
                                        edit.putString("name", name);
                                        edit.putString("password", password);
                                        edit.putInt("adminid", cursor.getInt(0));
                                        edit.commit();
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "密码错误，请重试", Toast.LENGTH_SHORT).show();
                                    }
                                    customDialog.dismiss();


                                }
                            });
                        }
                    }).start();
                }


            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegActivity.class));
            }
        });

        TextView view = (TextView) findViewById(R.id.textView26);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, GetServiceActivity.class));
            }
        });
        TextView view1 = (TextView) findViewById(R.id.textView27);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, NewPasswordActivity.class));
            }
        });
    }
}
