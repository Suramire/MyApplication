package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;

/**
 * 收租结果页
 */

public class GetMoneyResultActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoneyresult);
        setTitle("提示");
//        顶部不显示返回
        setDisplayHomeAsUpEnabled(false);
//        获取上个页面传输过来的值
        String money = getIntent().getStringExtra("money");
        String roomname = getIntent().getStringExtra("roomname");
        TextView textView = (TextView) findViewById(R.id.textView73);
        TextView textView2 = (TextView) findViewById(R.id.textView79);
//        显示收租金额
        textView.setText(textView.getText()+money);
//        显示缴费房间名
        textView2.setText("缴费房源：" + roomname);
//        返回按钮点击事件
        Button button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        打印单据按钮 点击时间 无功能
        Button button1 = (Button) findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GetMoneyResultActivity.this, "未现实", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
