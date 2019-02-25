package com.example.asus.shopping.prestent;

import com.example.asus.shopping.callback.MyCallBack;
import com.example.asus.shopping.model.IModelImpl;
import com.example.asus.shopping.view.IView;

import java.util.Map;

public class IPresentImpl implements IPresent {
    private IView iView;
    private IModelImpl iModel;

    public IPresentImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void RequestData(String url, Map<String, String> params, Class clazz) {
        iModel.requestData(url, params, clazz, new MyCallBack() {


            @Override
            public void onSuccess(Object data) {
                iView.onSuccess(data);
            }
        });

    }

    @Override
    public void getRequest(String Url, Class clazz) {
        iModel.getRequest(Url, clazz,   new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccess(data);
            }
        });
    }

    @Override
    public void putRequest(String url, Map<String, String> params, Class clazz) {
         iModel.putRequest(url, params, clazz, new MyCallBack() {
             @Override
             public void onSuccess(Object data) {
                 iView.onSuccess(data);
             }
         });
    }

    public void onDetach(){
        if(iModel!=null){
            iModel =null;
        }else if(iView!=null){
            iView=null;
        }
     }
}
