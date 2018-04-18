package com.suramire.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Notification;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 新增工作提醒页
 */

public class NewNotificationActivity extends BaseActivity {

    private long time_begin;
    private long time_end;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnotification);
        setTitle("新增备忘");
        initView();

    }

    private void initView() {
        final EditText notification_date = (EditText) findViewById(R.id.notification_date);
        final EditText notification_content = (EditText) findViewById(R.id.notification_content);
        ImageView notification_newphoto = (ImageView) findViewById(R.id.notification_newphoto);
        Button button = (Button) findViewById(R.id.btn_newnotification);
//        获取日历对象
        final Calendar calendar = Calendar.getInstance();
//        获取日期对象 时间为当前时间
        Date date = new Date();
//        设置日历对象的日期
        calendar.setTime(date);
//        根据日历对象得到当前时间的毫秒数
        time_begin = calendar.getTimeInMillis();
//        获取当前年、月、日
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//        简单日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        格式化当前日期
        String format = simpleDateFormat.format(date);
//        显示当前日期
        notification_date.setText(format);
//        设置日期文本框点击事件
        notification_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                新建日期选择框
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewNotificationActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        当选择日期后 设置日期文本框的日期
                        notification_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
//                        设置日历对象的时间为所选日期
                        calendar.set(year, monthOfYear, dayOfMonth);
//                        选择的时间毫秒数
                        time_end = calendar.getTimeInMillis();
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        notification_newphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewNotificationActivity.this, "图片选择功能待实现", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = notification_content.getText().toString().trim();
//                时间间隔判断
                if(time_end<time_begin){
                    Toast.makeText(NewNotificationActivity.this, "提醒时间不能早于今天", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDataBase myDataBase = new MyDataBase(NewNotificationActivity.this,"test.db",null,1);
                int adminid = (int) SPUtils.get("adminid", 0);
                String date = notification_date.getText().toString().trim();
//                添加一条新通知到数据库
                long l = myDataBase.addNotification(new Notification(content, date, adminid));
                if (l != 0) {
                    Toast.makeText(NewNotificationActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(NewNotificationActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
                myDataBase.close();

            }
        });


    }

}
