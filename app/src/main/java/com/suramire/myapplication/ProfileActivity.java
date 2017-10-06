package com.suramire.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.myapplication.util.MyDataBase;

/**
 * Created by Suramire on 2017/9/24.
 */

public class ProfileActivity extends AppCompatActivity {
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
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                finish();
            }
        });
        Button more = (Button) findViewById(R.id.button10);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MoreActivity.class));
                finish();
            }
        });
        TextView profile_nickname = (TextView) findViewById(R.id.profile_nickname);
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        int adminid = sharedPreferences.getInt("adminid", 0);
        MyDataBase myDataBase = new MyDataBase(ProfileActivity.this,"test.db",null,1);
        Cursor cursor = myDataBase.selectAdminById(adminid);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                profile_nickname.setText(nickname+"(房东)");
            }
            cursor.close();
        }
        if(myDataBase !=null)
            myDataBase.close();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
    }
}
