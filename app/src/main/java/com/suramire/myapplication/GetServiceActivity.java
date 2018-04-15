package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class GetServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getservice);
setTitle("联系客服");
        ListView listView = (ListView) findViewById(R.id.list_service);
        List<String> strings = new ArrayList<>();
        strings.add("官网：http://fangxun360.com");
        strings.add("好好租：http://hhz360.com");
        strings.add("邮箱：service@fangxun360.com");
        strings.add("热线：400-883-0288");
        strings.add("QQ：2621502604");
        listView.setAdapter(new CommonAdapter<String>(this,R.layout.item_service,strings) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.textView33, item);
            }
        });
    }

}
