package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Suramire on 2017/9/28.
 */

public class GetMoneyResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoneyresult);
        getActionBar().setTitle("提示");
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
