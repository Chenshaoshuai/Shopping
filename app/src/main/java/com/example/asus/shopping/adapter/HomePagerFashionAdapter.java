package com.example.asus.shopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.shopping.R;
import com.example.asus.shopping.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//a
public class HomePagerFashionAdapter extends RecyclerView.Adapter<HomePagerFashionAdapter.ViewHolder> {
   private Context mContext;
   private List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> mData;

    public HomePagerFashionAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.item_fashion,viewGroup,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String image = mData.get(i).getMasterPic();
        String replace = image.split("\\|")[0].replace("https","http");
        viewHolder.image_fashion.setImageURI(Uri.parse(replace));
        viewHolder.price_fashion.setText("￥"+mData.get(i).getPrice()+"元");
        viewHolder.title_fashion.setText(mData.get(i).getCommodityName());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int commodityId = mData.get(i).getCommodityId();
                onClicklayout.onClick(commodityId);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList) {
        this.mData = commodityList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title_fashion)
        TextView title_fashion;
        @BindView(R.id.item_image_fashion)
        SimpleDraweeView image_fashion;
        @BindView(R.id.item_price_fashion)
        TextView price_fashion;
        @BindView(R.id.rlayout_fashion)
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    OnClicklayout onClicklayout;

    public void setOnClicklayout(OnClicklayout onClicklayout) {
        this.onClicklayout = onClicklayout;
    }

    public interface OnClicklayout{
        void onClick(int position);
    }
}
