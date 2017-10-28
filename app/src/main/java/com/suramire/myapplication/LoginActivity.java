package com.suramire.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

/**
 * Created by Suramire on 2017/9/19.
 */

public class LoginActivity extends AppCompatActivity {

    private MyDataBase myDataBase;
    private ProgressDialog mProgressDialog;
    private EditText mText_username;
    private EditText mText_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("登录中，请稍候……");
        mText_username = (EditText) findViewById(R.id.text_username);
        mText_password = (EditText) findViewById(R.id.text_password);
        int autologin = (int) SPUtils.get("autologin", 0);

        //自动登录
        if (autologin == 1) {
            mText_username.setText((String) SPUtils.get("name", ""));
            mText_password.setText((String) SPUtils.get("password", ""));
            login();
        }



        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });
        findViewById(R.id.textView26).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, GetServiceActivity.class));
            }
        });
        findViewById(R.id.textView27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, NewPasswordActivity.class));
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        final String password = mText_password.getText().toString().trim();
        final String name = mText_username.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请将登录信息填写完整", Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //模拟耗时操作
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myDataBase = new MyDataBase(LoginActivity.this, "test.db", null, 1);
                            Cursor cursor = myDataBase.selectByName(name);
                            cursor.moveToFirst();
                            if (cursor.getCount() < 1) {
                                Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                            } else if (password.equals(cursor.getString(2))) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                //登录成功后保存用户名密码
                                cursor.close();
                                if (myDataBase != null)
                                    myDataBase.close();
                                SPUtils.put("name", name);
                                SPUtils.put("password", password);
                                SPUtils.put("adminid", cursor.getInt(0));
                                SPUtils.put("autologin", 1);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "密码错误，请重试", Toast.LENGTH_SHORT).show();
                            }
                            mProgressDialog.dismiss();
                        }
                    });
                }
            }).start();
        }
    }
}
