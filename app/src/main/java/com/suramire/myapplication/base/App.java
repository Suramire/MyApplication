package com.suramire.myapplication.base;

import android.app.Application;

/**
 * Created by Suramire on 2017/10/28.
 */

public class App extends Application {

    public static App getContext() {
        return mApp;
    }

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
