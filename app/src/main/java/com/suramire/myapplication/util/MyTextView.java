package com.suramire.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Suramire on 2017/9/27.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(36);
        canvas.drawColor(Color.WHITE);
        canvas.drawText(getText().toString(),0,36+100,paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(getText().length()*18+5,5+100,15,paint);
        paint.setColor(Color.DKGRAY);
        canvas.drawText("1",getText().length()*18-5,12+100,paint);
//        super.onDraw(canvas);
    }
}
