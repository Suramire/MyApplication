package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;


public class GetMoneyResultActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoneyresult);
        setTitle("提示");
        setDisplayHomeAsUpEnabled(false);
        String money = getIntent().getStringExtra("money");
        String roomname = getIntent().getStringExtra("roomname");
        TextView textView = (TextView) findViewById(R.id.textView73);
        TextView textView2 = (TextView) findViewById(R.id.textView79);
        textView.setText(textView.getText()+money);
        textView2.setText("缴费房源：" + roomname);
        Button button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button button1 = (Button) findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GetMoneyResultActivity.this, "未现实", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
