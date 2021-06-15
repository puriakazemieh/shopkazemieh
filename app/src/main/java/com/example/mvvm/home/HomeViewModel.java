package com.example.mvvm.home;

import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class HomeViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<List<Products>> latestProduct() {
        return apiService.getLatestProducts(Products.SORT_LATEST);
    }

    public Single<List<Products>> populareProducts() {
        return apiService.getPopularProducts(Products.SORT_POPULAR);
    }

    public Single<List<Banners>> banners() {
        return apiService.getBanners();
    }

    public Single<List<Categories>> categores() {
        return apiService.getCategory();
    }

    public Single<CountProduct> getCountProduct() {
        return apiService.countProduct();
    }
}
