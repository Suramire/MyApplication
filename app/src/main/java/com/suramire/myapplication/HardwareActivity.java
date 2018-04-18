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

/**
 * 智能硬件页 仅界面
 */

public class HardwareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        setupBanner();
        setTitle("智能硬件");
    }

    private void setupBanner() {
//        第三方框架 轮播图
        Banner banner = (Banner) findViewById(R.id.banner);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.smart_0);
        images.add(R.drawable.smart_1);
        images.add(R.drawable.smart_2);
        banner.setImageLoader(new GlideImageLoader()).setImages(images).start();
    }


//    轮播图的图片加载器
    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((int)path).into(imageView);
        }
    }

}
