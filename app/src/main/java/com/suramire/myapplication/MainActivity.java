package com.suramire.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.util.MyDataBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imageView11);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView12);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView4);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView5);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NotificationActivity.class));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "这里响应提醒事件", Toast.LENGTH_SHORT).show();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "这里打开帮助页面", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout money = (LinearLayout) findViewById(R.id.f_money);
        LinearLayout management = (LinearLayout) findViewById(R.id.f_management);
//        LinearLayout work = (LinearLayout) findViewById(R.id.f_work);
//        LinearLayout help = (LinearLayout) findViewById(R.id.f_help);
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManagementActivity.class));
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetRentsActivity.class));
            }
        });
        float sum1 = 0;
        float sum2 = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        int adminid = sharedPreferences.getInt("adminid", 0);
        //先获取当前房东所拥有的房间
        MyDataBase myDataBase = new MyDataBase(MainActivity.this, "test.db", null, 1);
        Cursor cursor = myDataBase.selectAllRoomGotMoney(adminid,1);
        Cursor cursor1 = myDataBase.selectAllRoomGotMoney(adminid,0);
        if (cursor.getCount() != 0) {

            while (cursor.moveToNext()) {
                sum1 +=cursor.getFloat(cursor.getColumnIndex("money"));
            }
            cursor.close();

        }

        if (cursor1.getCount() != 0) {

            while (cursor1.moveToNext()) {
                sum2 +=cursor1.getFloat(cursor1.getColumnIndex("money"));
            }
            cursor1.close();

        }

        TextView textView = (TextView) findViewById(R.id.textView24);
        TextView textView1 = (TextView) findViewById(R.id.textView25);
        Button button = (Button) findViewById(R.id.btn_5);
        textView.setText("已收租金："+sum1+"元");
        textView1.setText("待收租金："+sum2+"元");
        float percent = sum1 / (sum1 + sum2) * 100;
        button.setText("租金进度："+Math.round((percent*100)/100)+"%");
        // TODO: 2017/9/22 关闭cursor

    }
}
