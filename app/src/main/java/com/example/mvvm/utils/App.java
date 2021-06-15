package com.example.mvvm.utils;

import android.app.Application;
import android.content.Context;

import com.example.mvvm.auth.UserInfoManager;
import com.example.mvvm.model.TokenContainer;

public class App extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        TokenContainer.updateToken(new UserInfoManager(this).getToken());
        context=this;
    }

    public static Context getContext() {
        return context;
    }
}
