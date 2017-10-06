package com.suramire.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.model.RentItem;
import com.suramire.myapplication.util.MyDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Suramire on 2017/9/28.
 */

public class RentDetailActivity extends AppCompatActivity {

    private SimpleDateFormat simpleDateFormat;
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentdetail);
        final RentItem rentinfo = (RentItem) getIntent().getSerializableExtra("rentinfo");

        TextView tv_total = (TextView) findViewById(R.id.textView59);
        String margin = rentinfo.getMargin();
        final String money = rentinfo.getMoney();
        final float total = Float.parseFloat(margin) + Float.parseFloat(money);
        String date_begin = rentinfo.getDate_begin();
        String date_end = rentinfo.getDate_end();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format_begin = simpleDateFormat.format(new Date(date_begin));
        String format_end = simpleDateFormat.format(new Date(date_end));
        EditText tx_total = (EditText) findViewById(R.id.editText3);
        EditText tx_begin = (EditText) findViewById(R.id.editText6);
        EditText tx_end = (EditText) findViewById(R.id.editText7);
        EditText tx_money = (EditText) findViewById(R.id.editText8);
        EditText tx_margin = (EditText) findViewById(R.id.editText131);
        tv_total.setText(total+"");
        tx_total.setText(total+"");
        tx_begin.setText(format_begin);
        tx_end.setText(format_end);
        tx_money.setText(money + "");
        tx_margin.setText(margin+"");

        Button button = (Button) findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RentDetailActivity.this);
                builder.setTitle("收租确认");
                View view1 = LayoutInflater.from(RentDetailActivity.this).inflate(R.layout.dialog_pay, null);
                TextView tv_money = view1.findViewById(R.id.textView67);
                TextView tv_date = view1.findViewById(R.id.textView72);
                tv_money.setText(total+"");
                tv_date.setText(simpleDateFormat.format(new Date()));
                builder.setView(view1);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确认收租", new DialogInterface.OnClickListener() {



                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDataBase = new MyDataBase(RentDetailActivity.this, "test.db", null, 1);
                        int i1 = myDataBase.updateRentInfoPayed(rentinfo.getRentInfoId());
                        if(i1!=0){
                            finish();
                            Intent intent = new Intent(RentDetailActivity.this, GetMoneyResultActivity.class);
                            intent.putExtra("money", money+"");
                            startActivity(intent);
                        }else{
                            Toast.makeText(RentDetailActivity.this, "收租失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setCancelable(false).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rentdetail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myDataBase !=null)
            myDataBase.close();
    }
}
