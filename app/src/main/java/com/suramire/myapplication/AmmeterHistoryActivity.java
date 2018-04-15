package com.suramire.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;



public class AmmeterHistoryActivity extends BaseActivity {

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiity_ammeterhistory);
        setTitle("历史读表记录");
        int roomid = getIntent().getIntExtra("roomid", 0);
        final MyDataBase myDataBase = new MyDataBase(AmmeterHistoryActivity.this, "test.db", null, 1);
        mListView = (ListView) findViewById(R.id.list_amhistory);
        if(roomid!=0){
            ArrayList<Ammeter> list = new ArrayList<>();
            Cursor cursor = myDataBase.selectAmHistory(roomid);
            if(cursor.getCount()!=0){
                while (cursor.moveToNext()){
                    Ammeter ammeter = new Ammeter(roomid,cursor.getInt(cursor.getColumnIndex("count")));
                    ammeter.setTime(cursor.getString(cursor.getColumnIndex("time")));
                    ammeter.setId(cursor.getInt(0));
                    list.add(ammeter);
                }
                cursor.close();

            }

            mListView.setAdapter(new CommonAdapter<Ammeter>(this,R.layout.item_amhistory,list) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, final Ammeter item, int position) {
                    helper.setText(R.id.textView82, item.getCount() + "")
                            .setText(R.id.textView43,item.getTime());
                    helper.setOnClickListener(R.id.imageView53, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDataBase.deleteAmHistory(item.getId());
                            Toast.makeText(AmmeterHistoryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            remove(item);
                        }
                    });
                }
            });
        }

    }

}
