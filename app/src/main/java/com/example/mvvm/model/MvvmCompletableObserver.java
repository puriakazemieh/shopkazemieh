package com.example.mvvm.model;

import android.widget.Toast;

import com.example.mvvm.auth.AuthenticationActivity;
import com.example.mvvm.exeption.ExceptionMessageFactory;
import com.example.mvvm.utils.App;
import com.example.mvvm.view.BaseActivity;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

public abstract class MvvmCompletableObserver implements CompletableObserver {
    private BaseActivity baseActivity;

    public MvvmCompletableObserver(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onError(@NonNull Throwable e) {

        // Log.e("jflsdkjfklsdf   ",e.toString());

       // baseActivity.showSnackBarMessage(ExceptionMessageFactory.getMessage(e));

        Toast.makeText(App.getContext(), ExceptionMessageFactory.getMessage(e), Toast.LENGTH_SHORT).show();
    }
}
