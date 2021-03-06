package com.suramire.myapplication;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Admin;
import com.suramire.myapplication.util.MyDataBase;

/**
 * 注册页
 */

public class RegActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        setTitle("注册");
        final EditText admin_name = (EditText) findViewById(R.id.admin_name);
        final EditText admin_password = (EditText) findViewById(R.id.admin_pasword);
        final EditText admin_password2 = (EditText) findViewById(R.id.admin_password2);
        final EditText admin_nickname = (EditText) findViewById(R.id.admin_nickname);

        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = admin_name.getText().toString().trim();
                String password = admin_password.getText().toString().trim();
                String password2 = admin_password2.getText().toString().trim();
                String nickname = admin_nickname.getText().toString().trim();
//                信息完整性判断
                if(name.length()==0||password.length()==0 ||password2.length()==0 || nickname.length()==0){
                    Toast.makeText(RegActivity.this, "请将信息补充完整", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(password2)){
                    Toast.makeText(RegActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }else{

                    Admin admin = new Admin(name, password,nickname);
                    MyDataBase myDataBase = new MyDataBase(RegActivity.this,"test.db",null,1);
//                    先查询是否有同名用户存在
                    Cursor cursor = myDataBase.selectByName(name);
                    if(cursor.getCount()>0){
                        Toast.makeText(RegActivity.this, "该用户名已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                    }else{
                        //没有同名用户时进行注册操作
                        long insert = myDataBase.addAdmin(admin);
                        if(insert>0){
                            Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });


        //以下是性别选择框代码 点击框的时候改变背景图片

        final RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
        final RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButton.setBackgroundResource(R.drawable.right_selected);
                }else{
                    radioButton.setBackgroundResource(R.drawable.right_unselected);
                }
            }
        });
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButton2.setBackgroundResource(R.drawable.left_selected);
                }else{
                    radioButton2.setBackgroundResource(R.drawable.left_unselected);
                }
            }
        });


    }

}
