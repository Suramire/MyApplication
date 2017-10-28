package com.suramire.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imageView11);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView12);
        ImageView rent = (ImageView) findViewById(R.id.img_rent);
        ImageView management = (ImageView) findViewById(R.id.img_management);
        ImageView fe = (ImageView) findViewById(R.id.img_58);
        ImageView hardware = (ImageView) findViewById(R.id.img_hardware);
        ImageView notification = (ImageView) findViewById(R.id.img_notification);
        ImageView help = (ImageView) findViewById(R.id.img_help);
        ImageView shop = (ImageView) findViewById(R.id.img_shop);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                finish();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NotificationActivity.class));
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WorkNotification.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,HelpActivity.class));
            }
        });

        fe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FEInfoActivity.class));

            }
        });
        hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,HardwareActivity.class));

            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ShopActivity.class));

            }
        });



//        LinearLayout work = (LinearLayout) findViewById(R.id.f_work);
//        LinearLayout help = (LinearLayout) findViewById(R.id.f_help);
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManagementActivity.class));
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetRentsActivity.class));
            }
        });
        updateInfo();
        // TODO: 2017/9/22 关闭cursor

    }

    /**
     * 更新首页数据
     */
    private void updateInfo() {
        float sum1 = 0;
        float sum2 = 0;
        int adminid = (int) SPUtils.get("adminid", 0);
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
        button.setText("已完成："+Math.round((percent*100)/100)+"%\n查看详情>>");
    }

    @Override
    protected void onResume() {
        updateInfo();
        super.onResume();
    }
}
