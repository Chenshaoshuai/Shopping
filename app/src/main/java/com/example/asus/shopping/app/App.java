package com.example.asus.shopping.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {
    private static Context mContext;//a
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = getApplicationContext();

    }
    public static Context getApplication(){

        return mContext;
    }
}
