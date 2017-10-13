package com.suramire.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.suramire.myapplication.R;

/**
 * Created by Suramire on 2017/10/10.
 */

public class KeyUtils implements  OnKeyboardActionListener{
    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        switch (i){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:{mEditText.append(i+"");
                mOnTextChangeListener.onTextCompleted(mEditText);
            }break;
            case 11:
            case -12:{hide();}break;
            case 12:{
                if(mEditText.length()>0) {
                    mEditText.setText(mEditText.getText().subSequence(0, mEditText.length() - 1));
                }
                else{
                    mEditText.setText("0");
                }
                mOnTextChangeListener.onTextCompleted(mEditText);
            }break;
            case 101:{
//                mOnTextChangeListener.onPreviousLine();
            }break;
            case 103:{
//                mOnTextChangeListener.onNextLine();
            }break;

        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public interface onTextChangeListener{
        void onTextCompleted(EditText editText);
//        void onNextLine();
//        void onPreviousLine();
    }

    private Context mContext;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private EditText mEditText;
    private static KeyUtils mKeyUtils;
    private onTextChangeListener mOnTextChangeListener;

    public void setOnTextCompleteListener(onTextChangeListener onTextCompleteListener){
        mOnTextChangeListener = onTextCompleteListener;
    }



    public static KeyUtils getInstance(Context context,EditText editText){
//        if(mKeyUtils ==null){
//            mKeyUtils = new KeyUtils(context,editText);
//        }
        Log.d("KeyUtils", editText.getText().toString() + "is selected 2");
        mKeyUtils = new KeyUtils(context,editText);
        mKeyUtils.show();
        return mKeyUtils;
    }

    private KeyUtils(Context context,EditText editText) {
        mContext = context;
        mEditText = editText;
        mKeyboard = new Keyboard(mContext, R.xml.keyboard);
        mKeyboardView = ((Activity) mContext).findViewById(R.id.kkk);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(this);

    }




    public void hide(){
        if(mKeyboardView.getVisibility() == View.VISIBLE){
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    public void show(){
        if(mKeyboardView.getVisibility() == View.INVISIBLE ||mKeyboardView.getVisibility() ==View.GONE){
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

}
