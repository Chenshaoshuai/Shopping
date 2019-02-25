package com.example.asus.shopping.Fragment.fragment_order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.shopping.R;

//全部订单
public class Fragment_AllOrders extends Fragment {
    @Nullable
    @Override//a
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allorders_fragment,container,false);
        return view;
    }
}
