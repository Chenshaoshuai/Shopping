package com.example.asus.shopping.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.asus.shopping.Fragment.fragment_order.Fragment_AllOrders;
import com.example.asus.shopping.Fragment.fragment_order.Fragment_Delivery;
import com.example.asus.shopping.Fragment.fragment_order.Fragment_Evaluated;
import com.example.asus.shopping.Fragment.fragment_order.Fragment_Finish;
import com.example.asus.shopping.Fragment.fragment_order.Fragment_Obligation;
import com.example.asus.shopping.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//订单a
public class Fragment_OrderForm extends Fragment {
    @BindView(R.id.order_viewpager)
    ViewPager viewPager;
    @BindView(R.id.group_order)
    RadioGroup group;
    private List<Fragment> mList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.orderform_fragment,container,false);
        ButterKnife.bind(this,view);
        mList = new ArrayList<>();
        mList.add(new Fragment_AllOrders());
        mList.add(new Fragment_Obligation());
        mList.add(new Fragment_Delivery());
        mList.add(new Fragment_Evaluated());
        mList.add(new Fragment_Finish());

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_oreder_allorders:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.btn_order_obligation:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.btn_order_delivery:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.btn_order_evaluated:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.btn_order_finish:
                        viewPager.setCurrentItem(4);
                        break;

                        default:
                            break;
                }
            }
        });

        return view;
    }

}
