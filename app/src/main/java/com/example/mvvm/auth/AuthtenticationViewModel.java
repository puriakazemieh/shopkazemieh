package com.example.mvvm.auth;

import android.content.Context;

import com.example.mvvm.model.ApiService;
import com.example.mvvm.model.ApiServiceProvider;
import com.example.mvvm.model.TokenContainer;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class AuthtenticationViewModel {
    private ApiService apiService = ApiServiceProvider.providerApiService();

    private BehaviorSubject<Boolean> isInLoginMode = BehaviorSubject.create();
    private BehaviorSubject<Boolean> progressVisibility = BehaviorSubject.create();

    public BehaviorSubject<Boolean> getProgressVisibility() {
        return progressVisibility;
    }

    public Completable authenticate(Context context, String email, String password) {
        progressVisibility.onNext(true);
        Single<Token> tokenSingle;
        if (isInLoginMode.getValue() == null || isInLoginMode.getValue()) {
            tokenSingle = apiService.getLoginUserToken(email, password);
        } else {

            tokenSingle = apiService.getRegisterUser(email, password).flatMap(successResponse -> apiService.getLoginUserToken(email, password));
        }
        UserInfoManager userInfoManager = new UserInfoManager(context);

        return tokenSingle.doOnSuccess(new Consumer<Token>() {
            @Override
            public void accept(Token token) throws Exception {
                userInfoManager.saveToken(token.getMessage());
                userInfoManager.saveEmail(token.getEmail());
                TokenContainer.updateToken(token.getMessage());
            }
        })
                .doOnEvent((token, throwable) -> progressVisibility.onNext(false))
                .toCompletable();

    }

    public void onChangeModeButton() {
        isInLoginMode.onNext(isInLoginMode.getValue() != null && !isInLoginMode.getValue());
    }

    public BehaviorSubject<Boolean> getIsInLoginMode() {
        return isInLoginMode;
    }

}
