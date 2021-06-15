package com.example.mvvm.model;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTon {
    private static Retrofit retrofit;


    public static Retrofit getInstance() {

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request oldRequest = chain.request();
                            Request.Builder newRequest = oldRequest.newBuilder();
                            if (TokenContainer.getToken() != null) {
                                newRequest.addHeader("Authorization", TokenContainer.getToken());
                            }
                            newRequest.addHeader("Content-Type", "application/json");
                            newRequest.addHeader("Accept", "application/json");
                            newRequest.method(oldRequest.method(), oldRequest.body());
                            return chain.proceed(newRequest.build());
                        }
                    }).build();

            retrofit = new Retrofit.Builder()
                    // .baseUrl("http://mvvm.novindevelopers.ir/")
                    .baseUrl("http://kazemiehtest.ir/kazemieh/mvvm/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    private RetrofitSingleTon() {

    }
}
