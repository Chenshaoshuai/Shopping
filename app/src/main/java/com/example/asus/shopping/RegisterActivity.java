package com.example.asus.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.shopping.bean.RegisterBean;
import com.example.asus.shopping.prestent.IPresentImpl;
import com.example.asus.shopping.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements IView,View.OnClickListener {
    IPresentImpl iPresent;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.edit_rname)
    EditText edit_rname;
    @BindView(R.id.edit_rpass)
    EditText edit_rpass;
    private String name;
    private String pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        iPresent = new IPresentImpl(this);
        initView();
    }

    private void initView() {
        edit_rpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  btn_register.setEnabled(s.length()>=6);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_rname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    btn_register.setEnabled(s.length()>=11);



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @OnClick(R.id.btn_register)
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btn_register:
             name = edit_rname.getText().toString();
             pass = edit_rpass.getText().toString();
             loadData();
             break;
             default:
     }
    }//a

    private void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("phone",name);
        map.put("pwd",pass);
       iPresent.RequestData(Apis.DATA_REGISTER,map,RegisterBean.class);
    }

    @Override
    public void onSuccess(Object data) {
       RegisterBean registerBean = (RegisterBean) data;
       if (registerBean.getStatus().equals("0000")){
           Toast.makeText(RegisterActivity.this,registerBean.getMessage()+"",Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
           startActivity(intent);
       }else if(registerBean.getStatus().equals("1001")){
           Toast.makeText(RegisterActivity.this,registerBean.getMessage()+"",Toast.LENGTH_SHORT).show();
       }else {
           Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
       }
    }
}
