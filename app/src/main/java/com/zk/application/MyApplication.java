package com.zk.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/6/29.
 */
public class MyApplication extends Application {

    String local_address ;

    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
    }
}
