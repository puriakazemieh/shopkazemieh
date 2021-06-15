package com.example.mvvm.model;

import android.util.Log;
import android.widget.Toast;

import com.example.mvvm.exeption.ExceptionMessageFactory;
import com.example.mvvm.utils.App;
import com.example.mvvm.view.BaseActivity;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

public abstract class MvvmSingleObserve<T> implements SingleObserver<T> {
    private BaseActivity baseActivity;

    @Override
    public void onError(@NonNull Throwable e) {
       // Log.e("jflsdkjfklsdf   ",e.toString());
       // baseActivity.showSnackBarMessage(e.getMessage());
        Toast.makeText(App.getContext(), ExceptionMessageFactory.getMessage(e)+"", Toast.LENGTH_SHORT).show();
    }
}
