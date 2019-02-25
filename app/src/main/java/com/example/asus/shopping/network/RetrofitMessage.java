package com.example.asus.shopping.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.asus.shopping.app.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitMessage<T> {
   private final String BASE_URL = "http://mobile.bwstudent.com/small/";
   private static RetrofitMessage mRetrofitMessage;
   private OkHttpClient client;
//a
   public static RetrofitMessage getmRetrofitMessage() {
      if(mRetrofitMessage==null){
         synchronized (RetrofitMessage.class){
            if(null==mRetrofitMessage){
               mRetrofitMessage = new RetrofitMessage();
            }
         }
      }
      return mRetrofitMessage;
   }
   private BaseApis baseApis;
 public RetrofitMessage(){
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    client = new OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request origial = chain.request();
                    SharedPreferences sharedPreferences = App.getApplication().getSharedPreferences("User",Context.MODE_PRIVATE);
                    String userId = sharedPreferences.getString("userId","");
                    String sessionId = sharedPreferences.getString("sessionId","");

                    Request.Builder rerequestBuilder = origial.newBuilder();
                    rerequestBuilder.method(origial.method(),origial.body());
                    if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                           rerequestBuilder.addHeader("userId",userId);
                           rerequestBuilder.addHeader("sessionId",sessionId);
                    }
                    Request request = rerequestBuilder.build();

                    return chain.proceed(request);
                }
            })
            .retryOnConnectionFailure(true)
            .build();
    Retrofit retrofit =new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build();
    baseApis = retrofit.create(BaseApis.class);

 }

   //get
   public RetrofitMessage get(String url,HttpListener listener){
          baseApis.get(url)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(getObserver(listener));

       return mRetrofitMessage;
   }
  //post
  public RetrofitMessage postFormBody(String url,Map<String,RequestBody> map,HttpListener listener){
      if(map==null){
          map = new HashMap<>();
      }
      baseApis.postFormBody(url, map)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(getObserver(listener));
      return mRetrofitMessage;
  }

    public RetrofitMessage post(String url,Map<String,RequestBody> map,HttpListener listener){
        if(map==null){
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitMessage;
    }

    public RetrofitMessage put(String url,Map<String,String> map,HttpListener listener){
       if(map==null){
            map = new HashMap<>();
       }
       baseApis.put(url,map)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(getObserver(listener));
        return mRetrofitMessage;
    }

    public Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(listener!=null){
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if(listener !=null){
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener!=null){
                        listener.onFail(e.getMessage());
                    }
                }
            }


        };
        return observer;
    }

   private HttpListener listener;

   public void result(HttpListener listener){
       this.listener = listener;
   }



    public interface HttpListener{
       void onSuccess(String data);
       void onFail(String erroe);
   }




}
