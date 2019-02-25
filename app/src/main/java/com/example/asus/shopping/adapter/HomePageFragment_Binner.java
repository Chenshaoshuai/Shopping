package com.example.asus.shopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.shopping.bean.HomePageBannerBean;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment_Binner extends PagerAdapter {
    private Context mContext;
    private List<HomePageBannerBean.ResultBean> mData;
//a
    public HomePageFragment_Binner(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
        String imageUrl = mData.get(position % mData.size()).getImageUrl();
        Uri parse = Uri.parse(imageUrl);
        simpleDraweeView.setMinimumWidth(50);
        GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(50f);
        GenericDraweeHierarchy build =
                genericDraweeHierarchyBuilder.setRoundingParams(roundingParams).build();
        simpleDraweeView.setHierarchy(build);
        simpleDraweeView.setImageURI(parse);
        container.addView(simpleDraweeView);
        //ok不ok  我知道了 ok
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<HomePageBannerBean.ResultBean> result) {
        this.mData = result;
        notifyDataSetChanged();
    }

}
