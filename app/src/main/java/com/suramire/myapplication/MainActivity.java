package com.suramire.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Update;
import com.suramire.myapplication.util.GsonUtil;
import com.suramire.myapplication.util.HTTPUtil;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        findViewById(R.id.imageView11).setOnClickListener(this);
        findViewById(R.id.imageView12).setOnClickListener(this);
        findViewById(R.id.img_rent).setOnClickListener(this);
        findViewById(R.id.img_management).setOnClickListener(this);
        findViewById(R.id.img_58).setOnClickListener(this);
        findViewById(R.id.img_hardware).setOnClickListener(this);
        findViewById(R.id.img_notification).setOnClickListener(this);
        findViewById(R.id.img_help).setOnClickListener(this);
        findViewById(R.id.img_shop).setOnClickListener(this);
        Log.d("MainActivity", "check version");
        //先检查版本更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTPUtil.getPost("http://fd.hhz360.com/static/ra/updateApp/version.js", null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        try{
                            final Update updateInfo = (Update) GsonUtil.jsonToObject(string,Update.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("检查更新")
                                            .setMessage(Html.fromHtml(updateInfo.getUpdateInfo()))
                                            .setPositiveButton("立即更新", null)
                                            .setNegativeButton("下次更新", null)
                                            .setCancelable(false)
                                            .show();
                                }
                            });
                        }catch (Exception e){

                        }


                    }
                });
            }
        }).start();

        updateInfo();

    }

    /**
     * 更新首页数据
     */
    private void updateInfo() {
        float sum1 = 0;
        float sum2 = 0;
        int adminid = (int) SPUtils.get("adminid", 0);
        //先获取当前房东所拥有的房间
        MyDataBase myDataBase = new MyDataBase(MainActivity.this, "test.db", null, 1);
        Cursor cursor = myDataBase.selectAllRoomGotMoney(adminid,1);
        Cursor cursor1 = myDataBase.selectAllRoomGotMoney(adminid,0);
        if (cursor.getCount() != 0) {

            while (cursor.moveToNext()) {
                sum1 +=cursor.getFloat(cursor.getColumnIndex("money"));
            }
            cursor.close();

        }

        if (cursor1.getCount() != 0) {

            while (cursor1.moveToNext()) {
                sum2 +=cursor1.getFloat(cursor1.getColumnIndex("money"));
            }
            cursor1.close();

        }
        myDataBase.close();

        TextView textView = (TextView) findViewById(R.id.textView24);
        TextView textView1 = (TextView) findViewById(R.id.textView25);
        Button button = (Button) findViewById(R.id.btn_5);
        textView.setText("已收租金："+sum1+"元");
        textView1.setText("待收租金："+sum2+"元");
        float percent = sum1 / (sum1 + sum2) * 100;
        button.setText("已完成："+Math.round((percent*100)/100)+"%\n查看详情>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView11:
                startActivity( ProfileActivity.class);
                finish();
                break;
            case R.id.imageView12:
                startActivity( NotificationActivity.class);
                break;
            case R.id.img_notification:
                startActivity( WorkNotificationActivity.class);
                break;
            case R.id.img_help:
                startActivity( HelpActivity.class);
                break;
            case R.id.img_58:
                startActivity( FEInfoActivity.class);
                break;
            case R.id.img_hardware:
                startActivity( HardwareActivity.class);
                break;
            case R.id.img_shop:
                startActivity( ShopActivity.class);
                break;
            case R.id.img_management:
                startActivity(  ManagementActivity.class);
                break;
            case R.id.img_rent:
                startActivity(  GetRentsActivity.class);
                break;
        }
    }
}
