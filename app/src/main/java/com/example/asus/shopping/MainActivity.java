package com.example.asus.shopping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.shopping.bean.LogBean;
import com.example.asus.shopping.prestent.IPresentImpl;
import com.example.asus.shopping.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {
     IPresentImpl iPresent;
     @BindView(R.id.btn_log)
     Button btn_log;
     @BindView(R.id.edit_name)
     EditText edit_name;
     @BindView(R.id.edit_pass)
     EditText edit_pass;
     @BindView(R.id.check_remember)
     CheckBox check_remember;
     @BindView(R.id.tv_register)
     TextView tv_register;
     @BindView(R.id.image_pass_eye)
     ImageView image_pass_eye;
    private String name;
    private String pass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iPresent = new IPresentImpl(this);
        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        boolean r_ischeck = sharedPreferences.getBoolean("r_ischeck",false);
        if(r_ischeck){
          String name = sharedPreferences.getString("name",null);
          String pass = sharedPreferences.getString("pass",null);
          edit_name.setText(name);
          edit_pass.setText(pass);
          check_remember.setChecked(true);
        }

        image_pass_eye.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if(event.getAction()==MotionEvent.ACTION_DOWN){
                     edit_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                     image_pass_eye.setBackgroundResource(R.mipmap.login_icon_eye);
                 }else if(event.getAction()==MotionEvent.ACTION_UP){
                     edit_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                     image_pass_eye.setBackgroundResource(R.mipmap.login_icon_eye);
                 }
                 return true;
             }
         });

    }


    @OnClick({R.id.btn_log,R.id.tv_register})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_log:
                if(check_remember.isChecked()){
                    name = edit_name.getText().toString();
                    pass = edit_pass.getText().toString();
                    editor.putString("name",name);
                    editor.putString("pass",pass);
                    editor.putBoolean("r_ischeck",true);
                    editor.commit();
                }else {
                    editor.clear();
                    editor.commit();
                }

                loadData();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
              break;
              default:
        }
    }//a

    private void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("phone",name);
        map.put("pwd",pass);
        iPresent.RequestData(Apis.DATA_LOG,map,LogBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        LogBean logBean = (LogBean) data;
        if(logBean.getStatus().equals("0000")){
            editor.putString("userId",logBean.getResult().getUserId()).putString("sessionId",logBean.getResult().getSessionId()).commit();
            Toast.makeText(MainActivity.this,logBean.getMessage()+"",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }else if(logBean.getStatus().equals("1001")){
            Toast.makeText(MainActivity.this,logBean.getMessage()+"",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresent.onDetach();
    }
}
