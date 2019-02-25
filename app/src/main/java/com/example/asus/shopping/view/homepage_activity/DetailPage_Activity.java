package com.example.asus.shopping.view.homepage_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.shopping.Apis;
import com.example.asus.shopping.R;
import com.example.asus.shopping.adapter.DetailPageBannerAdapter;
import com.example.asus.shopping.bean.DetailPageBean;
import com.example.asus.shopping.bean.FindShoppingCarBean;
import com.example.asus.shopping.bean.ShoppingResultBean;
import com.example.asus.shopping.bean.SyncShoppingCarBean;
import com.example.asus.shopping.network.BaseApis;
import com.example.asus.shopping.prestent.IPresentImpl;
import com.example.asus.shopping.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.http.Url;

import static java.util.regex.Pattern.*;

public class DetailPage_Activity extends AppCompatActivity implements IView ,View.OnClickListener{
    @BindView(R.id.banner)
    ViewPager banner;
    private IPresentImpl iPresent;
    private String id;
    private int num;
    @BindView(R.id.detail_title)
    TextView deteil_title;
    @BindView(R.id.detail_price)
    TextView detail_price;
    @BindView(R.id.detail_kg)
    TextView detail_kg;
    @BindView(R.id.detail_salenum)
    TextView detail_salenum;
    @BindView(R.id.detail_img_car)
    ImageView detail_img_car;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);

        Unbinder bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);



        iPresent = new IPresentImpl(this);
        iPresent.getRequest(String.format(Apis.DETAIL_PAGE,id),DetailPageBean.class);

    }

    @Subscribe(sticky = true)
    public void gain(String uid){
        id = uid;
    }

    @OnClick(R.id.detail_img_car)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_img_car:

                SynshoppingCar();
                break;
            default:
                break;
        }
    }

    private void SynshoppingCar() {

        iPresent.getRequest(Apis.QUERY_CAR,FindShoppingCarBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof DetailPageBean){ //详情
            DetailPageBean detailPageBean= (DetailPageBean) data;
            DetailPageBean.ResultBean result = detailPageBean.getResult();
            String picture = result.getPicture();
            Pattern compile = Pattern.compile("\\,");
            String[] split = compile.split(picture);
            deteil_title.setText(result.getCommodityName());
            detail_price.setText("￥"+result.getPrice()+"");
            detail_kg.setText(result.getWeight()+"kg");
            detail_salenum.setText("已售"+result.getSaleNum()+"件");
            Toast.makeText(DetailPage_Activity.this,split[0],Toast.LENGTH_SHORT).show();
            DetailPageBannerAdapter bannerAdapter = new DetailPageBannerAdapter(split,DetailPage_Activity.this);
            banner.setAdapter(bannerAdapter);
        }else if(data instanceof FindShoppingCarBean){  //查询购物车
            FindShoppingCarBean findShoppingCarBean = (FindShoppingCarBean) data;
            if(findShoppingCarBean.getMessage().equals("查询成功")){
                List<ShoppingResultBean> list = new ArrayList<>();
                List<FindShoppingCarBean.ResultBean> resultBean = findShoppingCarBean.getResult();
                for(FindShoppingCarBean.ResultBean resultBeans:resultBean){
                    list.add(new ShoppingResultBean(resultBeans.getCommodityId(),resultBeans.getCount()));
                }
                addShoppingCar(list);
            }


        }else if(data instanceof SyncShoppingCarBean){
            SyncShoppingCarBean syncShoppingCarBean = (SyncShoppingCarBean) data;
            if(syncShoppingCarBean.getMessage().equals("同步成功")){
                Toast.makeText(this, "已加入购物车,可点击查看", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void addShoppingCar(List<ShoppingResultBean> list) {
       if(list.size()==0){
            list.add(new ShoppingResultBean(Integer.valueOf(id),1));
       }else {
           for(int i=0;i<list.size();i++){
               if(Integer.valueOf(id)==list.get(i).getCommodityId()){
                   int count =list.get(i).getCount();
                   count++;
                   list.get(i).setCount(count);
                   break;
               }else if(i==list.size()-1){
                  list.add(new ShoppingResultBean(Integer.valueOf(id),1));
                  break;
               }
           }
       }
        String s = new Gson().toJson(list);
       Map<String,String> map = new HashMap<>();
       map.put("data",s);
       iPresent.putRequest(Apis.ADD_CAR,map,SyncShoppingCarBean.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        iPresent.onDetach();
    }
}
