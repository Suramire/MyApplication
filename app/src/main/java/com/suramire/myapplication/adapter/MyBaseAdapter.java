package com.suramire.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.myapplication.EditRoomActivity;
import com.suramire.myapplication.NewRentActivity;
import com.suramire.myapplication.R;
import com.suramire.myapplication.model.Room;
import com.suramire.myapplication.util.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/25.
 */

public class MyBaseAdapter extends BaseAdapter {


    Context context;
    int currentPosition = -1;
    List<Room> data = new ArrayList<>();
    private ViewHolder viewHolder;

    public MyBaseAdapter(Context context, List<Room> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_room, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.item_roomname =(TextView) view.findViewById(R.id.item_roomname);
            viewHolder.item_roomstate =(TextView) view.findViewById(R.id.item_roomstate);
            viewHolder.item_roomprice =(TextView) view.findViewById(R.id.item_roomprice);
            viewHolder.imageView_check =(ImageView) view.findViewById(R.id.imageView18);
            viewHolder.layout_hide = (LinearLayout)view.findViewById(R.id.layout_hide);
            viewHolder.op_renterinfo = view.findViewById(R.id.op_renterinfo);
            viewHolder.op_rent = view.findViewById(R.id.op_rent);
            viewHolder.op_roominfo = view.findViewById(R.id.op_roominfo);
            viewHolder.op_up = view.findViewById(R.id.op_up);
            view.setTag(viewHolder);
        }
        if(view !=null){
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_roomname.setText(data.get(i).getName());
        viewHolder.item_roomprice.setText(data.get(i).getPrice()+"");
        viewHolder.item_roomstate.setText("未出租");
        if(currentPosition == i){
            if(viewHolder.imageView_check.isSelected()){
                //所点击的item菜单已展开
                viewHolder.imageView_check.setImageResource(R.drawable.item_checked);
                viewHolder.imageView_check.setSelected(false);
                viewHolder.layout_hide.setVisibility(View.GONE);
                currentPosition = -1;
            }else{
                //展开当前item的菜单
                viewHolder.imageView_check.setImageResource(R.drawable.item_unchecked);
                viewHolder.imageView_check.setSelected(true);
                viewHolder.layout_hide.setVisibility(View.VISIBLE);
            }
        }else{
            //隐藏未被选中的item的菜单
            viewHolder.imageView_check.setSelected(false);
            viewHolder.layout_hide.setVisibility(View.GONE);
        }
        viewHolder.imageView_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = i;
                notifyDataSetChanged();
            }
        });

        viewHolder.op_roominfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditRoomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room", data.get(i));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.op_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "待实现", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.op_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(i).getIsLended() ==1){
                    Toast.makeText(context, "该房间已被租用,请选择其他房间", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(context, NewRentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("room", data.get(i));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.op_renterinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBase myDataBase = new MyDataBase(context,"test.db",null,1);
                Cursor cursor = myDataBase.selectRenterInfo(data.get(i).getId());
                if(cursor.getCount()>0){
                    if (cursor.moveToLast()){
                        String rentername = cursor.getString(cursor.getColumnIndex("rentername"));
                        String renterphone = cursor.getString(cursor.getColumnIndex("renterphone"));
                        String margin = cursor.getString(cursor.getColumnIndex("margin"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("租客信息")
                                .setMessage("姓名：" + rentername + "\n\n电话：" + renterphone+"\n\n已交押金："+margin);
                        builder.show();
                        // TODO: 2017/9/21 已出租的房子不能再出租
                    }
                    cursor.close();
                }
            }
        });



        return view;
    }

    class ViewHolder{
        TextView item_roomname,item_roomprice,item_roomstate;

        ImageView imageView_check;
        LinearLayout layout_hide,op_roominfo,op_rent,op_up,op_renterinfo;
    }
}
