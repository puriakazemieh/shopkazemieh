package com.example.mvvm.orderDetails;

import com.example.mvvm.listcart.ListCartActivity;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class OrderDetilasViewModel {
    private ApiService apiService= ApiServiceProvider.providerApiService();
    public Single<List<OrderDetials>> getOrderDetails(String refId){
        return apiService.getOrderDetails(refId);
    }
}
