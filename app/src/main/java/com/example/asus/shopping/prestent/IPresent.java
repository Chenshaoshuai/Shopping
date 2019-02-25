package com.example.asus.shopping.prestent;

import java.util.Map;

public interface IPresent {
    void RequestData(String url,Map<String,String> params,Class clazz);
    void getRequest(String Url,Class clazz);
    void putRequest(String url,Map<String,String> params,Class clazz);
}
