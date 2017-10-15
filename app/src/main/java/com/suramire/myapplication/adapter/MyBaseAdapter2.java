package com.suramire.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suramire.myapplication.R;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.util.KeyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/9/25.
 * 实现选中点击的item
 */

public class MyBaseAdapter2 extends BaseAdapter {


    Context mContext;
    private int mSelectedItem;

    public List<Ammeter> getData() {
        return mData;
    }

    public void setData(List<Ammeter> data) {
        for(Ammeter ammeter:data){
            Ammeter ammeter1 = new Ammeter(ammeter.getId(),ammeter.getRoomid(),ammeter.getCount());
            ammeter1.setRoomName(ammeter.getRoomName());
            mData.add(ammeter1);
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
//        KeyUtils.getInstance(mContext,mCurrentEditText);
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
            view.setTag(viewHolder);
        }
        if(view !=null){
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextViewRoomId.setText(mData.get(i).getRoomName());
        viewHolder.mTextView2.setText("0");
        viewHolder.mTextView.setText(mData.get(i).getCount()+"");
        viewHolder.mEditText.setText(mData.get(i).getCount()+"");

        if(mSelectedItem == i){
            Log.d("MyBaseAdapter2", mData.get(i).getCount() + "is selected");
            viewHolder.mTextView.setVisibility(View.GONE);
            viewHolder.mEditText.setVisibility(View.VISIBLE);

            mKeyUtils = KeyUtils.getInstance(mContext, viewHolder.mEditText);
            mKeyUtils.setOnTextCompleteListener(new KeyUtils.onTextChangeListener() {
                @Override
                public void onTextCompleted(EditText editText) {
                    String text = editText.getText().toString();
                    Log.d("MyBaseAdapter2", text + "is selected 3");
                    if("".equals(text)){
                        text = "0";
                    }
                    viewHolder.mEditText.setText(text);
                    mData.get(i).setCount(Integer.parseInt(text));
                }

//                @Override
//                public void onNextLine() {
//                    if(mSelectedItem<mData.size()-1){
//                        setSelectedItem(mSelectedItem+1);
//                        notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onPreviousLine() {
//                    if(mSelectedItem>0){
//                        setSelectedItem(mSelectedItem-1);
//                        notifyDataSetChanged();
//                    }
//                }
            });

            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.bg_selected);

        }else{
            viewHolder.mTextView.setVisibility(View.VISIBLE);
            viewHolder.mEditText.setVisibility(View.GONE);
            viewHolder.mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

    class ViewHolder{
        TextView mTextView,mTextViewRoomId,mTextView2;
        ImageView mImageView;
        EditText mEditText;
        LinearLayout mLinearLayout;
    }
}
