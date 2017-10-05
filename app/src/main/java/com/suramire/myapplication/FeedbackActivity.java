package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Suramire on 2017/10/1.
 */

public class FeedbackActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        editText = (EditText) findViewById(R.id.editText15);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("帮助中心");
        supportActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }

        if(item.getItemId() == R.id.btn_send){
            String trim = editText.getText().toString().trim();
            if(!TextUtils.isEmpty(trim)){
                finish();
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
