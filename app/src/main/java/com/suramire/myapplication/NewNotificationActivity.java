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
 * Created by Suramire on 2017/10/7.
 */

public class NewNotificationActivity extends BaseActivity {

    private long time_begin;
    private long time_end;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnotification);
        setupActionBar();
        initView();

    }

    private void initView() {
        final EditText notification_date = (EditText) findViewById(R.id.notification_date);
        final EditText notification_content = (EditText) findViewById(R.id.notification_content);
        ImageView notification_newphoto = (ImageView) findViewById(R.id.notification_newphoto);
        Button button = (Button) findViewById(R.id.btn_newnotification);
        final Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        time_begin = calendar.getTimeInMillis();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        notification_date.setText(format);
        notification_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewNotificationActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        notification_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        calendar.set(year, monthOfYear, dayOfMonth);
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
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(NewNotificationActivity.this, "请填写备忘内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(time_end<time_begin){
                    Toast.makeText(NewNotificationActivity.this, "提醒时间不能早于今天", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDataBase myDataBase = new MyDataBase(NewNotificationActivity.this,"test.db",null,1);
                int adminid = (int) SPUtils.get("adminid", 0);
                String date = notification_date.getText().toString().trim();
                long l = myDataBase.addNotification(new Notification(content, date, adminid));
                if (l != 0) {
                    Toast.makeText(NewNotificationActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(NewNotificationActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
                if(myDataBase!=null){
                    myDataBase.close();
                }

            }
        });


    }

    private void setupActionBar() {
         setTitle("新增备忘");

    }


}
