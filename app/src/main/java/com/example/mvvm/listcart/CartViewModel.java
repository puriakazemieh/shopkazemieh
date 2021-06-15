package com.example.mvvm.listcart;

import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class CartViewModel {

    private ApiService apiService = ApiServiceProvider.providerApiService();
    private BehaviorSubject<Boolean> progressVisibility = BehaviorSubject.create();

    public BehaviorSubject<Boolean> getProgressVisibility() {
        return progressVisibility;
    }

    public Single<CartItem> cartItem(boolean mustReloadData) {
        if (mustReloadData)
        progressVisibility.onNext(true);


        return apiService.getListCart().doOnEvent((cartItem, throwable) -> progressVisibility.onNext(false));
    }

    public Single<SuccessRepose> remove(CartItemsItem item) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cart_item_id", item.getId());
        return apiService.removeToCart(jsonObject);
    }

    public Single<ChangeModelItem> changeCount(CartItemsItem item, int newCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cart_item_id", item.getId());
        jsonObject.addProperty("count", newCount);
        return apiService.changeCountCart(jsonObject);
    }


}
