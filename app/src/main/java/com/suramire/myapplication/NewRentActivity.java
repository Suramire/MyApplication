package com.suramire.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.model.RentInfo;
import com.suramire.myapplication.model.Room;
import com.suramire.myapplication.util.MyDataBase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Suramire on 2017/9/21.
 */

public class NewRentActivity extends AppCompatActivity {
    int year_begin,year_end;
    int month_begin,month_end;
    int day_begin,day_end;
    long time_begin,time_end;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrent);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        final Calendar calendar = Calendar.getInstance();
        Bundle extras = getIntent().getExtras();
        final Room room = (Room) extras.getSerializable("room");
        calendar.setTime(new Date());
        year_begin = calendar.get(Calendar.YEAR);
        month_begin = calendar.get(Calendar.MONTH);
        day_begin = calendar.get(Calendar.DAY_OF_MONTH);
        final EditText rent_name = (EditText) findViewById(R.id.rent_name);
        final EditText rent_phone = (EditText) findViewById(R.id.rent_phone);
        final EditText rent_money = (EditText) findViewById(R.id.rent_money);
        final EditText rent_margin = (EditText) findViewById(R.id.rent_margin);
        Button button = (Button) findViewById(R.id.btn_rent);
        final EditText rent_begin = (EditText) findViewById(R.id.rent_begin);
        final EditText rent_end = (EditText) findViewById(R.id.rent_end);
        rent_begin.setText(year_begin+"年"+(month_begin+1)+"月"+day_begin+"日");
        rent_end.setText(year_begin+"年"+(month_begin+1)+"月"+day_begin+"日");
        time_begin = calendar.getTimeInMillis();
        time_end = calendar.getTimeInMillis();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_end<=time_begin){
                    Toast.makeText(NewRentActivity.this, "出租时长不能小于1天", Toast.LENGTH_SHORT).show();
                }else{
                    String name = rent_name.getText().toString().trim();
                    String phone = rent_phone.getText().toString().trim();
                    Float money = Float.valueOf(rent_money.getText().toString().trim());
                    Float margin = Float.valueOf(rent_margin.getText().toString().trim());
                    long day = Math.abs((time_end - time_begin) / 1000 / 60 / 60 / 24);
                    Date date_begin = new Date(time_begin);
                    Date date_end = new Date(time_end);
                    RentInfo rentInfo = new RentInfo(name, phone, room.getId(), money, margin, date_begin.toString(), date_end.toString());
                    MyDataBase myDataBase = new MyDataBase(NewRentActivity.this, "test.db", null, 1);
                    long l = myDataBase.addRentInfo(rentInfo);
                    if(l!=0){
                        int i = myDataBase.updateRoomLend(room.getId());
                        Toast.makeText(NewRentActivity.this, "出租成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NewRentActivity.this, "出租失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        rent_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRentActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        rent_begin.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                        calendar.set(year, monthOfYear, dayOfMonth);
                        time_begin = calendar.getTimeInMillis();
                    }
                }, year_begin,month_begin,day_begin);
                datePickerDialog.show();
            }
        });

        rent_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRentActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        rent_end.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                        calendar.set(year, monthOfYear, dayOfMonth);
                        time_end = calendar.getTimeInMillis();
                        if(time_end<=time_begin){

                        }else{
                            long day = Math.abs((time_end - time_begin) / 1000 / 60 / 60 / 24);
                            double ceil = Math.floor(day / 30);//月份部分
                            float v = (day % 30) * (room.getPrice() / 30);//多余的天数
                            double v1 = ceil * room.getPrice() + v;

                            rent_money.setText((int)v1+"");

                        }
                    }
                }, year_begin,month_begin,day_begin);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
