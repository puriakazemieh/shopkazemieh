package com.example.mvvm.details;

import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.home.Products;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;

public class DetailsViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<Products> productsSingle(int idProduct) {
        return apiService.getSingleProduct(idProduct);
    }

    public Single<List<Colors>> colors(int idProduct) {
        return apiService.getColor(idProduct);
    }

    public Single<List<CommentProduct>> commentProduct(int idProduct) {
        return apiService.getCommentProduct(idProduct);
    }
    public Single<SuccessRepose> addToCar(int idProduct,String color){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("product_id",idProduct);
        jsonObject.addProperty("color_id",color);
        return apiService.addToCart(jsonObject);
    }
}
