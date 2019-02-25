package com.example.asus.shopping.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.shopping.Apis;
import com.example.asus.shopping.R;
import com.example.asus.shopping.adapter.CircleRecycleAdapter;
import com.example.asus.shopping.adapter.HomePagerFashionAdapter;
import com.example.asus.shopping.bean.CircleBean;
import com.example.asus.shopping.prestent.IPresentImpl;
import com.example.asus.shopping.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


//圈子
public class Fragment_Circle extends Fragment implements IView {
     private CircleRecycleAdapter circleRecycleAdapter;
     private IPresentImpl iPresent;
     private List<CircleBean.ResultBean> mData;
     @BindView(R.id.recycler_circle)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.circle_fragment,container,false);
        initView(view);
        requestData();
        initData();
        return view;
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        circleRecycleAdapter = new CircleRecycleAdapter(getContext());
        recyclerView.setAdapter(circleRecycleAdapter);
        circleRecycleAdapter.setResultBeans(new CircleRecycleAdapter.onAnimator() {
            @Override
            public void setAnimator(ImageView like, int whetherGreat, int id, TextView likenum, Integer integer, int i) {
                Map<String,String> map = new HashMap<>();
                map.put("circleId",String.valueOf(id));
                if(whetherGreat==2){
                    like.setBackgroundResource(R.mipmap.common_btn_prise_s);
                    mData.get(i).setWhetherGreat(1);
                    mData.get(i).setGreatNum(++integer);
                }else if(whetherGreat==1){
                    like.setBackgroundResource(R.mipmap.common_btn_prise_n);
                    mData.get(i).setWhetherGreat(2);
                    mData.get(i).setGreatNum(--integer);
                }
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(like, "scaleX", new float[]{1f, 2f, 3f, 4f, 5f, 6f, 1f});
                scaleX.setDuration(1000);
                scaleX.setRepeatMode(ObjectAnimator.RESTART);
                scaleX.start();
//a
                if (whetherGreat == 2){

                }else if (whetherGreat == 1){

                }

            }
        });
    }

    private void requestData() {
      iPresent.getRequest(Apis.CIRCLE_LIST,CircleBean.class);
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);
        iPresent= new IPresentImpl(this);
        requestData();
    }

    @Override
    public void onSuccess(Object data) {
      CircleBean bean = (CircleBean) data;
      circleRecycleAdapter.setData(bean.getResult());

    }
}
