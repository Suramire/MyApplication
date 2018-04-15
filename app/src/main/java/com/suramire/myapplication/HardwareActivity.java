package com.suramire.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.suramire.myapplication.base.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class HardwareActivity extends BaseActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        setupBanner();
        setTitle("智能硬件");
    }

    private void setupBanner() {
        Banner banner = (Banner) findViewById(R.id.banner);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.smart_0);
        images.add(R.drawable.smart_1);
        images.add(R.drawable.smart_2);
        banner.setImageLoader(new GlideImageLoader()).setImages(images).start();
    }



    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Picasso 加载图片简单用法
            Picasso.with(context).load((int)path).into(imageView);

        }
    }

}
