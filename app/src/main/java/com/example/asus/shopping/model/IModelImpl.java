package com.example.asus.shopping.model;

import com.example.asus.shopping.callback.MyCallBack;
import com.example.asus.shopping.network.RetrofitMessage;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.http.Url;

public class IModelImpl implements IModel {
    @Override
    public void requestData(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {

        RetrofitMessage.getmRetrofitMessage().post(url,params,new RetrofitMessage.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                callBack.onSuccess(o);
            }

            @Override
            public void onFail(String erroe) {
               callBack.onSuccess(erroe);
            }
        });
    }

    @Override
    public void getRequest(String Url, final Class clazz, final MyCallBack callBack) {
        RetrofitMessage.getmRetrofitMessage().get(Url,new RetrofitMessage.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                callBack.onSuccess(o);
            }
//a
            @Override
            public void onFail(String erroe) {
               callBack.onSuccess(erroe);
            }
        });
    }

    @Override
    public void putRequest(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
       RetrofitMessage.getmRetrofitMessage().put(url, params, new RetrofitMessage.HttpListener() {
           @Override
           public void onSuccess(String data) {
               Object o = new Gson().fromJson(data,clazz);
               callBack.onSuccess(o);

           }

           @Override
           public void onFail(String erroe) {
               callBack.onSuccess(erroe);
           }
       });
    }

}

