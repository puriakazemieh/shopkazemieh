package com.example.mvvm.listproduct;

import com.example.mvvm.home.Products;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class SortListViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<List<Products>> products(Integer sortType) {
        return apiService.getLatestProducts(sortType);
    }
}
