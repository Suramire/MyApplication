package com.suramire.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.suramire.myapplication.AmmeterChangeActivity;
import com.suramire.myapplication.AmmeterHistoryActivity;
import com.suramire.myapplication.R;
import com.suramire.myapplication.SortRoomActivity;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.KeyUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/25.
 * 实现选中点击的item修改
 */

public class MyBaseAdapter2 extends BaseAdapter {


    Context mContext;
    private int mSelectedItem;
    List<Integer> tempCounts = new ArrayList<>();//存放上一次读数

    public List<Ammeter> getData() {
        return mData;
    }

    public void setData(List<Ammeter> data) {
        for(Ammeter ammeter:data){
            Ammeter ammeter1 = new Ammeter(ammeter.getId(),ammeter.getRoomid(),ammeter.getCount(),ammeter.getLastcount(),ammeter.getSort());
            ammeter1.setTime(ammeter.getTime());
            ammeter1.setRoomName(ammeter.getRoomName());
            ammeter1.setLastTime(ammeter.getLastTime());
            mData.add(ammeter1);
            tempCounts.add(ammeter.getCount());
        }
    }

    List<Ammeter> mData = new ArrayList<>();
    private ViewHolder viewHolder;
    private KeyUtils mKeyUtils;

    public MyBaseAdapter2(Context context,final List<Ammeter> data) {
        this.mContext = context;
        setData(data);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectedItem(int selectedItem) {
        mSelectedItem = selectedItem;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_ammeter, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = view.findViewById(R.id.imageView2);
            viewHolder.mTextView = view.findViewById(R.id.textView2);
            viewHolder.mTextViewRoomId = view.findViewById(R.id.textView19);
            viewHolder.mTextView2 = view.findViewById(R.id.textView12);
            viewHolder.mLinearLayout = view.findViewById(R.id.ll_item5);
            viewHolder.mEditText = view.findViewById(R.id.editText);
            viewHolder.mLinearLayoutHide = view.findViewById(R.id.ll_hide);
            viewHolder.mLinearLayoutShow = view.findViewById(R.id.ll_show);
            viewHolder.mTextView20 = view.findViewById(R.id.textView20);
            viewHolder.mTextView30 = view.findViewById(R.id.textview30);
            view.setTag(viewHolder);
        }
        if(view !=null){
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextViewRoomId.setText(mData.get(i).getRoomName());
        viewHolder.mTextView.setText(mData.get(i).getCount()+"");
        viewHolder.mEditText.setText(mData.get(i).getCount()+"");
        viewHolder.mTextView2.setText(mData.get(i).getLastcount()+"");
        viewHolder.mTextView20.setText(mData.get(i).getLastTime());
        viewHolder.mTextView30.setText(mData.get(i).getTime());

        //每间房 读表额外操作
        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = ((Activity)mContext).getLayoutInflater().inflate(R.layout.popup_sendmessage2, null);
                final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(((Activity)mContext).getResources(), (Bitmap) null));
                setBackgroundAlpha(0.5f);
//                popupWindow.showAsDropDown(v);
                popupWindow.showAtLocation(((Activity)mContext).findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        // popupWindow隐藏时恢复屏幕正常透明度
                        setBackgroundAlpha(1.0f);
                    }
                });
                //取消
                inflate.findViewById(R.id.button29).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                //抄表历史
                inflate.findViewById(R.id.button26).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AmmeterHistoryActivity.class);
                        intent.putExtra("roomid", getData().get(i).getRoomid());
                        mContext.startActivity(intent);
                        popupWindow.dismiss();
                    }
                });
                //换表
                inflate.findViewById(R.id.button28).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AmmeterChangeActivity.class);
                        intent.putExtra("roomid", getData().get(i).getRoomid());
                        intent.putExtra("ammeterid", getData().get(i).getId());
                        mContext.startActivity(intent);
                        popupWindow.dismiss();
                    }
                });
            }
        });
        if(mSelectedItem == i){
            viewHolder.mLinearLayoutShow.setVisibility(View.GONE);
            viewHolder.mLinearLayoutHide.setVisibility(View.VISIBLE);
            viewHolder.mTextView20.setVisibility(View.VISIBLE);
            mKeyUtils = KeyUtils.getInstance(mContext, viewHolder.mEditText);
            mKeyUtils.setOnTextCompleteListener(new KeyUtils.onTextChangeListener() {
                @Override
                public void onTextCompleted(String newString,String lastString) {
                    mData.get(mSelectedItem).setLastcount(tempCounts.get(i));
                    viewHolder.mEditText.setText(newString);
                    mData.get(mSelectedItem).setCount(Integer.parseInt(newString));
                }

                @Override
                public void onNextLine() {
                    if(mSelectedItem<mData.size()-1){
                        setSelectedItem(mSelectedItem+1);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onPreviousLine() {
                    if(mSelectedItem>0){
                        setSelectedItem(mSelectedItem-1);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onStartSort() {
                    Intent intent = new Intent((Activity) mContext, SortRoomActivity.class);
                    //传输将要排序的房间信息
                    intent.putExtra("rooms", (Serializable) getData());
                    mContext.startActivity(intent);
                }
            });
            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.bg_selected);

        }else{
            viewHolder.mLinearLayoutShow.setVisibility(View.VISIBLE);
            viewHolder.mLinearLayoutHide.setVisibility(View.GONE);
            viewHolder.mTextView20.setVisibility(View.GONE);
            viewHolder.mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

    class ViewHolder{
        TextView mTextView,mTextViewRoomId,mTextView2,mTextView20,mTextView30;
        ImageView mImageView;
        TextView mEditText;
        LinearLayout mLinearLayout,mLinearLayoutHide,mLinearLayoutShow;

    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

}
