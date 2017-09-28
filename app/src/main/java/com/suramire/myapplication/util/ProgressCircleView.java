package com.suramire.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 水波纹进度球
 * */
@SuppressLint("AppCompatCustomView")
public class ProgressCircleView extends TextView {

    private int mHeight;    //控件宽高
    private int mWidth; //

    private int mStartX;    //起始位置坐标
    private int mStartY;    //

    private Paint mPaint;   // 边框画笔
    private Paint mQuadPaint;   // 水波纹画笔
    private Paint mForePaint;   // 前景色画笔

    private int[] mXs;
    private int[] mYs;

    private int mStrokeWidth = 4;   // 边框宽度
    private int mTranslationUnit;   // 位移单位

    private String mText;//中间显示的文本



    /** 水波纹总宽度 */
    private int mTotle;
    /** 当前进度值 */
    private float mProgress = 0.5f;
    /** 当前进度对应的颜色值 */
    private int mCurProgressColor = 0x58A1FB;
    /** 渐变颜色 */
    private int[] mProgressColors = new int[]{Color.RED, Color.BLUE, Color.CYAN, Color.GREEN};
    private Paint paint;

    public ProgressCircleView(Context context) {
        super(context);
    }

    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ProgressCircleView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 设置进度值
     * @param progress float 取值范围：0.1 - 1.0
     * */
    public void setProgress(float progress,String text) {
        mText = text;
        this.mProgress = 1 - progress;
        mCurProgressColor = interpRectColor(mProgressColors, progress); // 得到当前色值

        mHeight = 0;
        invalidate();
    }

    /**
     * 初始化 得到一些数据
     * */
    private void init() {
        mHeight = getHeight();
        mWidth = getWidth();

        mStartX = mStrokeWidth;
        mStartY = (int) (mHeight * mProgress);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStyle(Paint.Style.FILL);//设置为空心

        mQuadPaint = new Paint();
        mQuadPaint.setAntiAlias(true);
        mQuadPaint.setColor(mCurProgressColor);
        mQuadPaint.setStyle(Paint.Style.FILL);//设置实心

        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.argb(255,56,142,238));
        mForePaint.setStyle(Paint.Style.FILL);//设置实心

        mPaint.setStrokeWidth(mStrokeWidth);



        // 波纹坐标
        mTotle = 0;
        float scale = mProgress > 0.5f ? (1 - mProgress) * 2 : mProgress * 2;
        float[] xFs = new float[]{0.35f, 0.2f, 0.45f, 0.6f};
        float[] yFs = new float[]{0.15f, -0.12f, 0.2f, -0.25f};
        mXs = new int[xFs.length];
        mYs = new int[yFs.length];
        for (int i = 0 ; i < xFs.length ; i++) {
            mXs[i] = (int) (mWidth * xFs[i]);
            mYs[i] = (int) (mHeight * yFs[i] * scale);

            mTotle += mXs[i];
        }
        mTotle += mXs[0];
        mTotle += mXs[1];

        mTranslationUnit = mWidth / 50;
    }

    @SuppressLint("DrawAllocation") @Override
    protected void onDraw(Canvas canvas) {

        if (mHeight == 0) {
            if (mWidth == 0) {
                // 开始波动
                start();
            }
            // 初始化
            init();
        }

        if (android.os.Build.VERSION.SDK_INT > 10) {
            // 3.0 或更高版本
            drawForegroundCircle(canvas);
        } else {
            // 3.0以下版本
            clipCircle(canvas);
        }
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(36);

        canvas.drawText(mText,canvas.getWidth()/2,canvas.getHeight()/2,mPaint);
//        super.onDraw(canvas);
    }

    private void clipCircle(Canvas canvas) {
        canvas.save();
        Path path = new Path();
        path.addCircle(mWidth / 2, mHeight / 2, mWidth / 2 - (mStrokeWidth>>1), Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
        canvas.drawPath(dealPath(), mQuadPaint);
//      canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - mStrokeWidth, mPaint);
        canvas.restore();
    }

    private void drawForegroundCircle(Canvas canvas) {
        Path path = new Path();
        float radius = mWidth / 2f;
        float panding = mStrokeWidth / 2f;

        path = new Path();
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(mWidth, 0);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.lineTo(0, radius);
        //圆弧左边中间起点是180,旋转360度
        path.arcTo(new RectF(panding, panding, mWidth - panding, mHeight - panding), 180, -359, true);
        path.close();

        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
        canvas.drawPath(dealPath(), mQuadPaint);
//        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - mStrokeWidth, mPaint);
        canvas.drawPath(path, mForePaint);
    }

    /**
     * 画出水波线
     * */
    private Path dealPath() {
        Path path = new Path();
        path.moveTo(mStartX, mStartY);

        for (int i = 0 ; i < mXs.length ; i++) {
            path.rQuadTo(mXs[i], mYs[i], mXs[i]<<1, 0);

        }
        path.rQuadTo(mXs[0], mYs[0], mXs[0]<<1, 0);
        path.rQuadTo(mXs[1], mYs[1], mXs[1]<<1, 0);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.lineTo(mStartX, mStartY);
        path.close();

        return path;
    }

    /**
     * 开始波动效果
     * */
    private void start() {
        mStartX -= mTranslationUnit;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 20);
        invalidate();
        if (-mStartX >= mTotle + mWidth + 1.2f * mStrokeWidth) {
            mStartX = mStrokeWidth;
        }
    }

    /**
     * 获取渐变颜色
     * @param colors
     * @return
     */
    private int interpRectColor(int[] colors, float p) {
        if (p == 0) {
            return colors[0];
        }

        int size = colors.length - 1;
        float cp, up;
        int a, r, g, b, cur;
        up = 1f / size;
        cur = (int) (p / up);
        cp = (p - cur * up) / up;
        if (cp == 0) {
            cur--;
            cp = 1;
        }

        a = ave(Color.alpha(colors[cur]), Color.alpha(colors[cur+1]), cp);
//        r = ave(Color.red(colors[cur]), Color.red(colors[cur+1]), cp);
//        g = ave(Color.green(colors[cur]), Color.green(colors[cur+1]), cp);
//        b = ave(Color.blue(colors[cur]), Color.blue(colors[cur+1]), cp);
        return Color.argb(a, 88, 161, 251);
    }

    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = 0;
        invalidate();
    }

}