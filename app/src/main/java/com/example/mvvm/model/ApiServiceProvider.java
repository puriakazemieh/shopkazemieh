package com.example.mvvm.model;

public class ApiServiceProvider {
    private static ApiService apiService;

    public static ApiService providerApiService() {
        if (apiService == null) {
            apiService = RetrofitSingleTon.getInstance().create(ApiService.class);
        }
        return apiService;
    }
}
