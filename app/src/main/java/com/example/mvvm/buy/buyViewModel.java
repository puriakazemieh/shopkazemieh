package com.example.mvvm.buy;

import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;
import com.google.gson.JsonObject;

import io.reactivex.Single;

public class buyViewModel {
    ApiService apiService = ApiServiceProvider.providerApiService();

    public Single<SuccessRepose> order(String name, String family,String address, String phone, String post, String price, String refId) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("family",family);
        jsonObject.addProperty("address",address);
        jsonObject.addProperty("phone",phone);
        jsonObject.addProperty("postal_code",post);
        jsonObject.addProperty("price",price);
        jsonObject.addProperty("ref_id",refId);
     return apiService.addOrder(jsonObject);
    }
}
