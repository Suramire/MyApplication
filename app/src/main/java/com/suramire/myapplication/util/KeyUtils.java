package com.suramire.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.suramire.myapplication.R;

/**
 * Created by Suramire on 2017/10/10.
 */

public class KeyUtils implements  OnKeyboardActionListener{

    public static boolean isLightUp = false;
    private Camera m_Camera;
    private Camera.Parameters mParameters;

    //region Listener
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
            case 9:{
                String lastString = mEditText.getText().toString().trim();
                mEditText.append(i+"");
                String newString = mEditText.getText().toString().trim();
                mOnTextChangeListener.onTextCompleted(newString,lastString);
            }break;
            case 11:
            case -12:{hide();}break;
            case 12:{
                String lastString = mEditText.getText().toString().trim();
                if(mEditText.length()>1) {
                    mEditText.setText(mEditText.getText().subSequence(0, mEditText.length() - 1));
                }
                else{
                    mEditText.setText("0");
                }
                String newString = mEditText.getText().toString().trim();
                mOnTextChangeListener.onTextCompleted(newString,lastString);
            }break;
            case 100:{
                if(isLightUp){
                    try{
                        mParameters = m_Camera.getParameters();
                        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        m_Camera.setParameters(mParameters);
                        m_Camera.release();
                        isLightUp =false;
                    } catch(Exception ex){}

                }else{
                    try{
                        m_Camera = Camera.open();
                        mParameters = m_Camera.getParameters();
                        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        m_Camera.setParameters(mParameters);
                        isLightUp =true;
                    } catch(Exception ex){}

                }
            }break;
            case 101:{
                mOnTextChangeListener.onPreviousLine();
            }break;
            case 102:{
                mOnTextChangeListener.onStartSort();
            }break;
            case 103:{
                mOnTextChangeListener.onNextLine();
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
    //endregion

    public interface onTextChangeListener{
        void onTextCompleted(String newString,String lastString);
        void onNextLine();
        void onPreviousLine();
        void onStartSort();
    }

    private Context mContext;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private TextView mEditText;
    private static KeyUtils mKeyUtils;
    private onTextChangeListener mOnTextChangeListener;

    public void setOnTextCompleteListener(onTextChangeListener onTextCompleteListener){
        mOnTextChangeListener = onTextCompleteListener;
    }



    public static KeyUtils getInstance(Context context,TextView editText){
//        if(mKeyUtils ==null){
//            mKeyUtils = new KeyUtils(context,editText);
//        }
        Log.d("KeyUtils", editText.getText().toString() + "is selected 2");
        mKeyUtils = new KeyUtils(context,editText);
        mKeyUtils.show();
        return mKeyUtils;
    }

    private KeyUtils(Context context,TextView editText) {
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
