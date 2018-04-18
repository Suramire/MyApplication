package com.suramire.myapplication;

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
import java.util.List;

/**
 * 房间排序页
 */

public class SortRoomActivity extends BaseActivity {

    private CommonAdapter<Ammeter> mAdapterAdd;
    private CommonAdapter<Ammeter> mAdapterRemove;
    private List<Ammeter> mTempList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortroom);
        setTitle("房源排序");
//        用于显示排序后的房间列表
        ListView mListViewAdd = (ListView) findViewById(R.id.list_add);
        //        用于显示排序前的房间列表
        ListView mListViewRemove = (ListView) findViewById(R.id.list_remove);
        //获取上个页面传送过来的电表数据集合
        final List<Ammeter> rooms = (List<Ammeter>) getIntent().getSerializableExtra("rooms");
        mTempList = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getSort()!=0){
                //若之前已排序直接显示在排序后的列表
                mTempList.add(rooms.get(i));
                rooms.remove(i);
                i--;
            }
        }

        mAdapterAdd = new CommonAdapter<Ammeter>(SortRoomActivity.this, R.layout.item_sort_add, rooms) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, final Ammeter item, int position) {
                helper.setText(R.id.textView42, item.getRoomName())
                        .setOnClickListener(R.id.rl_add, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //将当前项从本适配器移除
                                remove(item);
                                //并添加到另外一个适配器中
                                mAdapterRemove.add(item);
                                //通知数据更新
                                mAdapterRemove.notifyDataSetChanged();
                            }
                        });
            }
        };

        mAdapterRemove = new CommonAdapter<Ammeter>(SortRoomActivity.this, R.layout.item_sort_romove, mTempList) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, final Ammeter item, int position) {
                helper.setText(R.id.textView42, item.getRoomName())
                        .setOnClickListener(R.id.rl_remove, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //将当前项从本适配器移除
                                remove(item);
                                //并添加到另外一个适配器中
                                mAdapterAdd.add(item);
                                //通知数据更新
                                mAdapterAdd.notifyDataSetChanged();
                            }
                        });
            }
        };

        mListViewAdd.setAdapter(mAdapterAdd);
        mListViewRemove.setAdapter(mAdapterRemove);

        findViewById(R.id.button30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase myDataBase = new MyDataBase(SortRoomActivity.this,"test.db",null,1);
                //保存排序
                if(mAdapterRemove.getCount()>0){
                    for (int i = 0; i < mAdapterRemove.getCount(); i++) {
                        Ammeter ammeter = mAdapterRemove.getData().get(i);
                        ammeter.setSort(10000-i);
                        mTempList.add(ammeter);
                    }
                    for (Ammeter am: mTempList) {
                        myDataBase.updateAmmeter(am);
                    }

                }
                if(mAdapterAdd.getCount()>0){

                    for (int i = 0; i < mAdapterAdd.getCount(); i++) {
                        Ammeter ammeter = mAdapterAdd.getData().get(i);
                        ammeter.setSort(0);
                        mTempList.add(ammeter);
                    }
                    for (Ammeter am: mTempList) {
                        myDataBase.updateAmmeter(am);
                    }

                }
                myDataBase.close();
                Toast.makeText(SortRoomActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
