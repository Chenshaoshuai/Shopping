package com.example.asus.shopping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.shopping.bean.DetailPageBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class DetailPageBannerAdapter extends PagerAdapter {
    private String[] images;
    private Context context;

    public DetailPageBannerAdapter(String[] images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }//a

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SimpleDraweeView draweeView = new SimpleDraweeView(context);
        draweeView.setImageURI(images[position % images.length] );
        container.addView(draweeView);
        return draweeView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
