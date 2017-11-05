package com.suramire.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ActivityLoginActivity extends Activity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView27;
    private TextView textView26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_reg).setOnClickListener(this);
        textView27 = (TextView) findViewById(R.id.textView27);
        textView26 = (TextView) findViewById(R.id.textView26);
    }

    private EditText getTextUsername(){
        return (EditText) findViewById(R.id.text_username);
    }

    private EditText getTextPassword(){
        return (EditText) findViewById(R.id.text_password);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //TODO implement
                break;
            case R.id.btn_reg:
                //TODO implement
                break;
        }
    }
}
