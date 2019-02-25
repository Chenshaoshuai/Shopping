package com.example.asus.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.asus.shopping.Fragment.Fragment_Circle;
import com.example.asus.shopping.Fragment.Fragment_HomePage;
import com.example.asus.shopping.Fragment.Fragment_MyProfile;
import com.example.asus.shopping.Fragment.Fragment_OrderForm;
import com.example.asus.shopping.Fragment.Fragment_ShoppingCart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_viewpager)
    ViewPager home_viewpager;
    @BindView(R.id.home_group)
    RadioGroup home_group;
    private List<Fragment> mList;//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        mList.add(new Fragment_HomePage());
        mList.add(new Fragment_Circle());
        mList.add(new Fragment_ShoppingCart());
        mList.add(new Fragment_OrderForm());
        mList.add(new Fragment_MyProfile());

        home_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        home_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_homepage:
                        home_viewpager.setCurrentItem(0);

                        break;
                    case R.id.btn_circle:
                        home_viewpager.setCurrentItem(1);
                        break;
                    case R.id.btn_shoppingcart:
                        home_viewpager.setCurrentItem(2);
                        break;
                    case R.id.btn_orderform:
                        home_viewpager.setCurrentItem(3);
                        break;
                    case R.id.btn_myprofile:
                        home_viewpager.setCurrentItem(4);
                        break;
                        default:
                            break;
                }
            }
        });
        home_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        home_group.check(R.id.btn_homepage);
                        break;
                    case 1:
                        home_group.check(R.id.btn_circle);
                        break;
                    case 2:
                        home_group.check(R.id.btn_shoppingcart);
                        break;
                    case 3:
                        home_group.check(R.id.btn_orderform);
                        break;
                    case 4:
                        home_group.check(R.id.btn_myprofile);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
