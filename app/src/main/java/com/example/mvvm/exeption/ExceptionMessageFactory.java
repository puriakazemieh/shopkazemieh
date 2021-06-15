package com.example.mvvm.exeption;

import android.util.Log;

import com.example.mvvm.auth.SuccessRepose;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.HttpException;

public class ExceptionMessageFactory {
    public static String getMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Gson gson = new Gson();
            switch (((HttpException) throwable).code()) {
                case 422: {
                    try {
                        SuccessRepose repose = gson.fromJson(((HttpException) throwable).response().errorBody().string(), SuccessRepose.class);
                    //    Log.e("jflsdkjfklsdf   ",repose.getMessage());
                           return repose.getMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 403: {
                    try {
                        SuccessRepose repose = gson.fromJson(((HttpException) throwable).response().errorBody().string(), SuccessRepose.class);
                        //    Log.e("jflsdkjfklsdf   ",repose.getMessage());
                        return repose.getMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return "ارور نا مشخص" + throwable.getMessage();
    }

    private ExceptionMessageFactory() {

    }
}

