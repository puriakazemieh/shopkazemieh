package com.example.mvvm.addcomment;

import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;
import com.google.gson.JsonObject;

import io.reactivex.Single;

public class DialogViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<SuccessRepose> addComment(String title, String content, int idProduct) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("content", content);
        jsonObject.addProperty("product_id", idProduct);
        return apiService.addComment(jsonObject);
    }
}
