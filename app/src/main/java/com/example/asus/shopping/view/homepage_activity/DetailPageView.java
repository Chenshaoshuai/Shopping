package com.example.asus.shopping.view.homepage_activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class DetailPageView extends ScrollView {
    public DetailPageView(Context context) {
        super(context,null);
    }
//
    public DetailPageView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public DetailPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null){
            scrollViewListener.onScrollChange(this,l,t,oldl,oldt);
        }
    }

    public interface ScrollViewListener{
        void onScrollChange(DetailPageView scrollView, int l, int t, int oldl, int oldt);
    }

    private ScrollViewListener scrollViewListener;

    public void setScrollViewListener(ScrollViewListener listener) {
        scrollViewListener = listener;
    }
}

