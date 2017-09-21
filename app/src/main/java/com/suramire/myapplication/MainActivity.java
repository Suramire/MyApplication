package com.suramire.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout money = (LinearLayout) findViewById(R.id.f_money);
        LinearLayout management = (LinearLayout) findViewById(R.id.f_management);
        LinearLayout work = (LinearLayout) findViewById(R.id.f_work);
        LinearLayout help = (LinearLayout) findViewById(R.id.f_help);
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManagementActivity.class));
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetRentsActivity.class));
            }
        });

    }
}
