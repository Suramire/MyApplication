package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;

/**
 * Created by Suramire on 2017/9/28.
 */

public class GetMoneyResultActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoneyresult);
        setTitle("提示");
        setDisplayHomeAsUpEnabled(false);
        String money = getIntent().getStringExtra("money");
        TextView textView = (TextView) findViewById(R.id.textView73);
        textView.setText(textView.getText()+money);
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
