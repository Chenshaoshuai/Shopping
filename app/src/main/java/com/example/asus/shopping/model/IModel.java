package com.example.asus.shopping.model;

import com.example.asus.shopping.callback.MyCallBack;

import java.util.Map;
//a
public interface IModel {
  void  requestData(String url, Map<String,String> params, Class clazz, MyCallBack callBack);
  void  getRequest(String Url,Class clazz,MyCallBack callBack);
  void  putRequest(String url,Map<String,String>params,Class clazz,MyCallBack callBack);
}
