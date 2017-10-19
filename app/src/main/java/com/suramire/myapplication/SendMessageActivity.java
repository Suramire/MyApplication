package com.suramire.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by Suramire on 2017/10/18.
 */

public class SendMessageActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentmessage);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("催租");
        findViewById(R.id.textView48).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SendMessageActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                View inflate = getLayoutInflater().inflate(R.layout.popup_sendmessage, null);
                PopupWindow popupWindow = new PopupWindow(inflate, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                setBackgroundAlpha(0.5f);
//                popupWindow.showAsDropDown(v);
                popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        // popupWindow隐藏时恢复屏幕正常透明度
                        setBackgroundAlpha(1.0f);
                    }
                });
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
