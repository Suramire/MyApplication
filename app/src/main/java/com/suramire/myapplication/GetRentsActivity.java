package com.suramire.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.model.RentItem;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/21.
 */

public class GetRentsActivity extends AppCompatActivity {

    private String roomname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getrents);
        ListView listView = (ListView) findViewById(R.id.rents_list);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);
        TabLayout.Tab tab = tabLayout.newTab().setText("未收租");
        TabLayout.Tab tab1 = tabLayout.newTab().setText("已收租");
        TextView tv_empty = (TextView) findViewById(R.id.textView23);
        tv_empty.setVisibility(View.VISIBLE);
        tv_empty.setText("暂无可收租房间");
        listView.setEmptyView(tv_empty);
        tabLayout.addTab(tab);
        tabLayout.addTab(tab1);
        View header = LayoutInflater.from(this).inflate(R.layout.header_rents, null);
        final MyDataBase myDataBase = new MyDataBase(GetRentsActivity.this, "test.db", null, 1);

        //未支付
        Cursor cursor = myDataBase.selectRenterInfoByPayed(0);
        List<RentItem> rentItemList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                int roomid = cursor.getInt(cursor.getColumnIndex("roomid"));
                int rentInfoId = cursor.getInt(0);
                Cursor cursor1 = myDataBase.selectRoomByRoomid(roomid);
                if(cursor1.getCount()>0){
                    while (cursor1.moveToNext()){
                        roomname = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                    RentItem rentItem = new RentItem(rentInfoId,rentername, roomname, money + "");
                    rentItemList.add(rentItem);
                }
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(header);
                }
                listView.setAdapter(new CommonAdapter<RentItem>(this, R.layout.item_rents, rentItemList) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final RentItem item, int position) {
                        helper.setText(R.id.rents_rentername, item.getRenterName())
                                .setText(R.id.rents_rentroom, item.getRentRoomName())
                                .setText(R.id.rents_money, item.getMoney());
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GetRentsActivity.this);
                                builder.setTitle("提示")
                                        .setMessage("应收租"+item.getMoney()+"元");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // TODO: 2017/9/21 执行收租事件
                                        int i1 = myDataBase.updateRentInfoPayed(item.getRentInfoId());
                                        if(i1!=0){
                                            Toast.makeText(GetRentsActivity.this, "收租成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder.setNegativeButton("取消", null);
                                builder.show();
                            }
                        };
                        helper.setOnClickListener(R.id.rents_money, listener)
                                .setOnClickListener(R.id.rents_rentername, listener)
                                .setOnClickListener(R.id.rents_rentroom, listener);
                    }
                });
            }

        }
    }
}
