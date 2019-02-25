package com.example.asus.shopping.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.asus.shopping.Apis;
import com.example.asus.shopping.R;
import com.example.asus.shopping.adapter.HomePageFragment_Binner;
import com.example.asus.shopping.adapter.HomePageLiveAdapter;
import com.example.asus.shopping.adapter.HomePagerFashionAdapter;
import com.example.asus.shopping.adapter.NewProductAdapter;
import com.example.asus.shopping.bean.HomeBean;
import com.example.asus.shopping.bean.HomePageBannerBean;
import com.example.asus.shopping.prestent.IPresentImpl;
import com.example.asus.shopping.view.IView;
import com.example.asus.shopping.view.homepage_activity.DetailPage_Activity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


//首页
public class Fragment_HomePage extends Fragment implements IView,View.OnClickListener {
     private IPresentImpl iPresent;
     private NewProductAdapter newProductAdapter;
     private HomePagerFashionAdapter fashionAdapter;
     private HomePageFragment_Binner binnerAdapter;
     private HomePageLiveAdapter liveAdapter;
     @BindView(R.id.recycler_newproduct)
     RecyclerView recycler_newproduct;
     @BindView(R.id.recycler_fashion)
     RecyclerView recycler_fashion;
     @BindView(R.id.recycler_live)
     RecyclerView recycle_live;
     @BindView(R.id.home_binner)
     ViewPager home_binner;
     @BindView(R.id.edit_search)
      EditText edit_search;

    public  static  final  int BINNER_MSG = 0;
    public  static  final  int BINNER_RUNTIME =2000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment,container,false);

        initView(view);

        requestData();

        initData();
        return view;
    }

    private void initData() {
        //热销新品a
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler_newproduct.setLayoutManager(gridLayoutManager);
        newProductAdapter = new NewProductAdapter(getContext());
        recycler_newproduct.setAdapter(newProductAdapter);
        newProductAdapter.setOnClicklayout(new NewProductAdapter.OnClicklayout() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().postSticky(position+"");
                startActivity(new Intent(getActivity(),DetailPage_Activity.class));
            }
        });

        //魔力时尚
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler_fashion.setLayoutManager(linearLayoutManager);
        fashionAdapter = new HomePagerFashionAdapter(getContext());
        recycler_fashion.setAdapter(fashionAdapter);
        fashionAdapter.setOnClicklayout(new HomePagerFashionAdapter.OnClicklayout() {
            @Override
            public void onClick(int position) {

                EventBus.getDefault().postSticky(position+"");
                startActivity(new Intent(getActivity(),DetailPage_Activity.class));
            }
        });
        //品质生活
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycle_live.setLayoutManager(gridLayoutManager1);
        liveAdapter = new HomePageLiveAdapter(getContext());
        recycle_live.setAdapter(liveAdapter);
        liveAdapter.setOnClicklayout(new HomePageLiveAdapter.OnClicklayout() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().postSticky(position+"");
                startActivity(new Intent(getActivity(),DetailPage_Activity.class));
            }
        });


        //banner
         binnerAdapter = new HomePageFragment_Binner(getContext());


    }



    private void initView(View view) {
        ButterKnife.bind(this,view);
        iPresent= new IPresentImpl(this);
        requestData();
    }


    private void requestData() {
        iPresent.getRequest(Apis.HOMEPAGE_LIST,HomeBean.class);
        iPresent.getRequest(Apis.HOMEPAGE_BANNER,HomePageBannerBean.class);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof HomeBean){
          HomeBean homeBean = (HomeBean) data;
          newProductAdapter.setData(homeBean.getResult().getRxxp().get(0).getCommodityList());
          fashionAdapter.setData(homeBean.getResult().getMlss().get(0).getCommodityList());
          liveAdapter.setData(homeBean.getResult().getPzsh().get(0).getCommodityList());



        }else if (data instanceof HomePageBannerBean){
            HomePageBannerBean bannerBean = (HomePageBannerBean) data;

            binnerAdapter.setData(bannerBean.getResult());
            home_binner.setOffscreenPageLimit(3);
            home_binner.setPageMargin(24);
            home_binner.setAdapter(binnerAdapter);
            home_binner.setCurrentItem(bannerBean.getResult().size() * 1000);
            handler.removeMessages(BINNER_MSG);
            handler.sendEmptyMessageDelayed(BINNER_MSG,BINNER_RUNTIME);
            setTouchHome_binnerTouch();
        }
    }


        //handler进行发送
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (BINNER_MSG == msg.what){
                int currentItem = home_binner.getCurrentItem();
                currentItem++;
                home_binner.setCurrentItem(currentItem);
                handler.removeMessages(BINNER_MSG);
                handler.sendEmptyMessageDelayed(BINNER_MSG,BINNER_RUNTIME);
            }
            return false;
        }
    });

    //home_binner的touch的事件
    @SuppressLint("ClickableViewAccessibility")
    public void setTouchHome_binnerTouch(){
        home_binner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                         handler.removeMessages(BINNER_MSG);
                    break;

                    case MotionEvent.ACTION_MOVE:
                        handler.removeMessages(BINNER_MSG);
                        break;

                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(BINNER_MSG,BINNER_RUNTIME);
                        break;
                        default:
                            break;
                }
                return false;
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(BINNER_MSG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(BINNER_MSG);
    }
}
