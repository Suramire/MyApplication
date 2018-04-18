package com.suramire.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.RentItem;
import com.suramire.myapplication.util.MyDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 收租详情页
 */

public class RentDetailActivity extends BaseActivity {

    private SimpleDateFormat simpleDateFormat;
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentdetail);
//        获取从上个页面传过来的出租信息
        final RentItem rentinfo = (RentItem) getIntent().getSerializableExtra("rentinfo");

        //从出租信息里获取详细字段
        TextView tv_total = (TextView) findViewById(R.id.textView59);
        final String margin = rentinfo.getMargin();//押金
        final String money = rentinfo.getMoney();//应付租金
        final float total = Float.parseFloat(margin) + Float.parseFloat(money);//押金+租金
        String date_begin = rentinfo.getDate_begin();//起始时间
        String date_end = rentinfo.getDate_end();//结束时间
        final String rentRoomName = rentinfo.getRentRoomName();//房间名
        String renterName = rentinfo.getRenterName();
        setTitle(rentRoomName+"("+renterName+")");

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format_begin = simpleDateFormat.format(new Date(date_begin));
        String format_end = simpleDateFormat.format(new Date(date_end));
        EditText tx_total = (EditText) findViewById(R.id.editText3);
        EditText tx_begin = (EditText) findViewById(R.id.editText6);
        EditText tx_end = (EditText) findViewById(R.id.editText7);
        EditText tx_money = (EditText) findViewById(R.id.editText8);
        EditText tx_margin = (EditText) findViewById(R.id.editText131);
//        显示信息
        tv_total.setText(total+"");
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
                            intent.putExtra("money", (total)+"");
                            intent.putExtra("roomname", rentRoomName);
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
//        加载顶部菜单
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
