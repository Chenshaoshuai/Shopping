package com.example.asus.shopping.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.shopping.R;
import com.example.asus.shopping.bean.CircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleRecycleAdapter extends RecyclerView.Adapter<CircleRecycleAdapter.ViewHolder> {

    private Context mContext;
    private List<CircleBean.ResultBean> mData;

    public CircleRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.item_circle,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override//a
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String images = mData.get(i).getImage();
        String replac = images.split("\\|")[0].replace("https","http");
        viewHolder.image.setImageURI(Uri.parse(replac));
        viewHolder.hedimage.setImageURI(Uri.parse(mData.get(i).getHeadPic()));
        viewHolder.likenum.setText(mData.get(i).getGreatNum()+"");
        viewHolder.name.setText(mData.get(i).getNickName());
        viewHolder.time.setText(mData.get(i).getCreateTime()+"");
        viewHolder.title.setText(mData.get(i).getContent());
        final int whetherGreat = mData.get(i).getWhetherGreat();
        if(whetherGreat==1){
            viewHolder.like.setBackgroundResource(R.mipmap.common_btn_prise_s);
        }else if(whetherGreat==2){
            viewHolder.like.setBackgroundResource(R.mipmap.common_btn_prise_n);
        }

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAnimator.setAnimator(viewHolder.like,whetherGreat,mData.get(i).getId(),viewHolder.likenum,Integer.valueOf(
                        viewHolder.likenum.getText().toString().trim()
                ),i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<CircleBean.ResultBean> result) {
        this.mData = result;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_headimage)
        SimpleDraweeView hedimage;
        @BindView(R.id.circle_name)
        TextView name;
        @BindView(R.id.circle_time)
        TextView time;
        @BindView(R.id.circle_title)
        TextView title;
        @BindView(R.id.circle_image)
        SimpleDraweeView image;
        @BindView(R.id.circle_likenum)
        TextView likenum;
        @BindView(R.id.circle_btn_like)
        ImageView like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface onAnimator{


        void setAnimator(ImageView like, int whetherGreat, int id, TextView likenum, Integer integer, int i);
    }

    private onAnimator mOnAnimator;
    public void setResultBeans(onAnimator onAnimator){
        mOnAnimator  = onAnimator;
    }

}
