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

public class HomePageLiveAdapter extends RecyclerView.Adapter<HomePageLiveAdapter.ViewHolder> {
    private Context mContext;
    private List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> mData;
//v
    public HomePageLiveAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_live,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String images = mData.get(i).getMasterPic();
        String replac = images.split("\\|")[0].replace("https","http");
        viewHolder.image.setImageURI(Uri.parse(replac));
        viewHolder.price.setText("￥"+mData.get(i).getPrice()+"元");
        viewHolder.title.setText(mData.get(i).getCommodityName());
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

    public void setData(List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> commodityList) {
        this.mData = commodityList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title_live)
        TextView title;
        @BindView(R.id.item_price_live)
        TextView price;
        @BindView(R.id.item_image_live)
        SimpleDraweeView image;
        @BindView(R.id.rlayout_live)
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
