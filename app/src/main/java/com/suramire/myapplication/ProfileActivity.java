package com.suramire.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;


public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.image_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
                finish();
            }
        });
        Button more = (Button) findViewById(R.id.button10);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MoreActivity.class);
                finish();
            }
        });
        TextView profile_nickname = (TextView) findViewById(R.id.profile_nickname);
        int adminid = (int) SPUtils.get("adminid", 0);
        MyDataBase myDataBase = new MyDataBase(ProfileActivity.this,"test.db",null,1);
        Cursor cursor = myDataBase.selectAdminById(adminid);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                profile_nickname.setText(nickname+"(房东)");
            }
            cursor.close();
        }
        myDataBase.close();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(MainActivity.class);
        finish();
    }
}
