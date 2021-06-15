package com.example.mvvm.categorylist;

import com.example.mvvm.home.Products;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class CategoryViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<List<Products>> category(int idCategory) {
        return apiService.getCategoryList(idCategory);
    }
}
