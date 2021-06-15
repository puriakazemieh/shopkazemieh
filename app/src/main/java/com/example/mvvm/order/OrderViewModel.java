package com.example.mvvm.order;

import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class OrderViewModel {
   private ApiService apiService= ApiServiceProvider.providerApiService();
    public Single<List<OrderHistory>> getOrderHistory(){
        return apiService.orderHistory();
    }
}
