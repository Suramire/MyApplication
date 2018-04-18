package com.suramire.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.RentItem;
import com.suramire.myapplication.util.MyDataBase;
import com.suramire.myapplication.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 收租页
 */

public class GetRentsActivity extends BaseActivity implements View.OnClickListener {


    private String roomname;
    private View header;
    private TabLayout tabLayout;
    private ListView listView;
    private TextView tv_state;
    private int mAdminid;
    private MyDataBase mMyDataBase;
    private View mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getrents);
//        连接数据库
        mMyDataBase = new MyDataBase(GetRentsActivity.this,"test.db",null,1);
        setupActionBar();

        findViewById(R.id.ll_2).setOnClickListener(this);
        findViewById(R.id.ll_1).setOnClickListener(this);
        findViewById(R.id.ll_4).setOnClickListener(this);
        findViewById(R.id.imageView44).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.imageView45).setOnClickListener(this);
        findViewById(R.id.button25).setOnClickListener(this);
        findViewById(R.id.imageView46).setOnClickListener(this);
        findViewById(R.id.button24).setOnClickListener(this);
        findViewById(R.id.imageView47).setOnClickListener(this);
        findViewById(R.id.button23).setOnClickListener(this);
        findViewById(R.id.imageView54).setOnClickListener(this);
        mLinearLayout = (LinearLayout)findViewById(R.id.linearLayout);

//        加载标头的布局文件
        header = LayoutInflater.from(this).inflate(R.layout.header_rents, null);
        tv_state = header.findViewById(R.id.item_roomstate);
        listView = (ListView) findViewById(R.id.rents_list);
        tabLayout = (TabLayout) findViewById(R.id.tab2);
//        为tablayout添加分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_divider));
        linearLayout.setDividerPadding(40);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listView.setAdapter(null);
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        showListNotPayed();
                        break;
                    case 1:showListPayed();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        RelativeLayout tv_empty = (RelativeLayout) findViewById(R.id.empty_layout);
        tv_empty.setVisibility(View.VISIBLE);
        //为列表设置空白视图
        listView.setEmptyView(tv_empty);

        showListNotPayed();
    }

    private void setupActionBar() {
        setTitle("");
        ActionBar supportActionBar = getSupportActionBar();
        if(Build.VERSION.SDK_INT>=21){
            supportActionBar.setElevation(0);
        }
        //获取当前用户所有的房源信息
        int adminid = (int) SPUtils.get("adminid", 0);
        Cursor cursor = mMyDataBase.selectAllHouseByAdminId(adminid);
        int count = cursor.getCount();
        List<String> list = new ArrayList<>();
        if(count >0){
            while (cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor.close();
        }
        supportActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        supportActionBar.setTitle(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        supportActionBar.setListNavigationCallbacks(adapter,null);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        mAdminid = (int) SPUtils.get("adminid", 0);

    }

    /**
     * 显示已支付租金的出租记录
     */
    private void showListNotPayed() {
        listView.setAdapter(null);
//        当列表没有标头时，添加一个标头
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(header);
        }
        final MyDataBase myDataBase = new MyDataBase(GetRentsActivity.this, "test.db", null, 1);

        //未支付的出租记录
        Cursor cursor = myDataBase.selectAllRoomGotMoney(mAdminid,0);
        final List<RentItem> rentItemList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                int roomid = cursor.getInt(cursor.getColumnIndex("roomid"));
                int rentInfoId = cursor.getInt(0);
                String datebegin = cursor.getString(cursor.getColumnIndex("datebegin"));
                String dateend = cursor.getString(cursor.getColumnIndex("dateend"));
                Float margin = cursor.getFloat(cursor.getColumnIndex("margin"));
                Cursor cursor1 = myDataBase.selectRoomByRoomid(roomid);
                if(cursor1.getCount()>0){
                    while (cursor1.moveToNext()){
                        roomname = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                    RentItem rentItem = new RentItem(rentInfoId, rentername, roomname, money + "", margin + "",datebegin,dateend);
                    rentItemList.add(rentItem);
                }
                tv_state.setText("待收租金(元)");
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(header);
                }
                listView.setAdapter(new CommonAdapter<RentItem>(this, R.layout.item_rents, rentItemList) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final RentItem item, final int position) {
                        helper.setText(R.id.rents_rentername, item.getRenterName())
                                .setText(R.id.rents_rentroom, item.getRentRoomName())
                                .setText(R.id.rents_money, item.getMoney());
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                          Intent intent = new Intent(GetRentsActivity.this, RentDetailActivity.class);
                                intent.putExtra("rentinfo", rentItemList.get(position));
                                startActivity(intent);

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

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.getTabAt(0).select();
        showListNotPayed();

    }


    /**
     * 显示已支付租金的出租记录
     */
    private void showListPayed() {
        listView.setAdapter(null);
        if(listView.getHeaderViewsCount()==0){
            listView.addHeaderView(header);
        }
        final MyDataBase myDataBase = new MyDataBase(GetRentsActivity.this, "test.db", null, 1);

        //已支付的出租记录
        Cursor cursor = myDataBase.selectAllRoomGotMoney(mAdminid,1);
        List<RentItem> rentItemList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                int roomid = cursor.getInt(cursor.getColumnIndex("roomid"));
                int rentInfoId = cursor.getInt(0);
                String datebegin = cursor.getString(cursor.getColumnIndex("datebegin"));
                String dateend = cursor.getString(cursor.getColumnIndex("dateend"));
                Float margin = cursor.getFloat(cursor.getColumnIndex("margin"));
                Cursor cursor1 = myDataBase.selectRoomByRoomid(roomid);
                if(cursor1.getCount()>0){
                    while (cursor1.moveToNext()){
                        roomname = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                    RentItem rentItem = new RentItem(rentInfoId, rentername, roomname, money + "", margin + "",datebegin,dateend);
                    rentItemList.add(rentItem);
                }
                tv_state.setText("已收租金(元)");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyDataBase == null) {
            mMyDataBase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,10,0,"收租攻略")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
            case R.id.imageView44:
            case R.id.ll_1:{
                startActivity(AmmeterActivity.class);
            }break;
            case R.id.button25:
            case R.id.imageView45:
            case R.id.ll_2:{
                startActivity(SendMessageActivity.class);
            }break;
            case R.id.button23:
            case R.id.imageView47:
            case R.id.ll_4:{
                startActivity(RecordsActivity.class);
            }break;
            case R.id.imageView54:{
                if(mLinearLayout.getVisibility()==View.GONE){
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mLinearLayout.setAnimation(AnimationUtils.makeInAnimation(mContext, false));
                }else{
                    mLinearLayout.setVisibility(View.GONE);
                    mLinearLayout.setAnimation(AnimationUtils.makeOutAnimation(mContext, true));
                }
            }break;
        }
    }
}
