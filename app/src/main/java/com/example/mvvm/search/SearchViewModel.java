package com.example.mvvm.search;


import com.example.mvvm.home.Products;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;

import java.util.List;

import io.reactivex.Single;

public class SearchViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();
    public Single<List<Products>> search(String search)
    {
        return apiService.getResultProduct(search);
    }
}
