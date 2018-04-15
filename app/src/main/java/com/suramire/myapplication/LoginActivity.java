package com.suramire.myapplication;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Admin;
import com.suramire.myapplication.model.Error;
import com.suramire.myapplication.util.GsonUtil;
import com.suramire.myapplication.util.HTTPUtil;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {

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
                startActivity(RegActivity.class);
            }
        });
        findViewById(R.id.textView26).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GetServiceActivity.class);
            }
        });
        findViewById(R.id.textView27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NewPasswordActivity.class);
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        //获取文本内容
        final String password = mText_password.getText().toString().trim();
        final String name = mText_username.getText().toString().trim();
        //判断文本
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请将登录信息填写完整",
                    Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog.show();//显示加载等待对话框

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);//模拟耗时操作
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //新建数据库连接
//                            HTTPUtil.getPost("http://fd.hhz360.com/ra/landlord/landlordUser.login.html?" +
//                                    "name=" + name + "&password=" + password + "&isDa=false&_resultCode=%24json", null, new Callback() {
//                                @Override
//                                public void onFailure(Call call, final IOException e) {
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mProgressDialog.dismiss();
//                                            Toast.makeText(mContext, "登录失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    String string = response.body().string();
//                                    if("\"/index.html\"".equals(string)){
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mProgressDialog.dismiss();
//                                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                                                MyDataBase myDataBase = new MyDataBase(LoginActivity.this,"test.db",null,1);
//                                                Cursor cursor = myDataBase.selectByName(name);
//                                                Cursor cursor2 = null;
//                                                if(cursor.getCount()<=0){
//                                                    //用户名在本地不存在时新建一个用户
//                                                    // TODO: 2017/11/21 保存cookie以获取用户信息
//                                                    Admin admin = new Admin(name,password,"default");
//                                                    long insert = myDataBase.addAdmin(admin);
//                                                    cursor2 = myDataBase.selectByName(name);
//                                                    if(cursor2.getCount()>0){
//                                                        if(cursor.moveToNext()){
//                                                            //登录成功后保存用户名密码
//                                                            SPUtils.put("name", name);
//                                                            SPUtils.put("password", password);
//                                                            SPUtils.put("adminid", cursor2.getInt(0));
//                                                            SPUtils.put("autologin", 1);
//                                                        }
//
//
//                                                    }
//
//                                                }else{
//                                                    if(cursor.moveToNext()){
//                                                        SPUtils.put("name", name);
//                                                        SPUtils.put("password", password);
//                                                        SPUtils.put("adminid", cursor.getInt(0));
//                                                        SPUtils.put("autologin", 1);
//                                                    }
//
//
//                                                }
//                                                    cursor.close();
//                                                    if(cursor2!=null){
//                                                        cursor2.close();
//                                                    }
//
//                                                    myDataBase.close();
//                                                    startActivity(MainActivity.class);
//                                                    finish();
//                                                }
//
//                                            });
//                                        } else{
//                                        final Error error = (Error) GsonUtil.jsonToObject(string, Error.class);
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(mContext, "登录失败：" + error.getError(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//
//                                }
//                            });                           、、                  //
                        //获取数据库对象
                        myDataBase = new MyDataBase(LoginActivity.this,
                                "test.db", null, 1);
                        //获取名字对应的记录
                        Cursor cursor = myDataBase.selectByName(name);

                        cursor.moveToFirst();//将指针移到结果集的第一行
                        if (cursor.getCount() < 1) {//判断用户是否存在
                            Toast.makeText(LoginActivity.this,
                                    "该用户不存在", Toast.LENGTH_SHORT).show();
                        } else if (password.equals(cursor.getString(2))) {
                            Toast.makeText(LoginActivity.this,
                                    "登录成功", Toast.LENGTH_SHORT).show();
                            //登录成功后保存用户名密码
                            SPUtils.put("name", name);
                            SPUtils.put("password", password);
                            SPUtils.put("adminid", cursor.getInt(0));
                            SPUtils.put("autologin", 1);
                            cursor.close();//关闭结果集
                            if (myDataBase != null)
                                myDataBase.close();//关闭数据库连接
                            startActivity(MainActivity.class);//进入主界面
                            finish();//关闭本页面
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "密码错误，请重试", Toast.LENGTH_SHORT).show();
                        }

                        }
                    });
                }
            }).start();
        }
    }
}
