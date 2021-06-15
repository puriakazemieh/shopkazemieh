package com.example.mvvm.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.home.HomeActivity;
import com.example.mvvm.model.MvvmCompletableObserver;
import com.example.mvvm.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationActivity extends BaseActivity {

    private EditText edtUser, edtPassword;
    private Button btnisModeLogin, btnIsModeRegister;
    AuthtenticationViewModel viewModel = new AuthtenticationViewModel();
    private ProgressBar progressBar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setUpViews();
        observe();
    }

    private void setUpViews() {
        progressBar = findViewById(R.id.pr_Auth);
        edtPassword = findViewById(R.id.edt_password);
        edtUser = findViewById(R.id.edt_username);
        btnisModeLogin = findViewById(R.id.btn_isModelLogin);
        btnIsModeRegister = findViewById(R.id.btn_isModelRegister);
        btnIsModeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onChangeModeButton();
            }
        });
        btnisModeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.authenticate(AuthenticationActivity.this, edtUser.getText().toString(), edtPassword.getText().toString())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MvvmCompletableObserver(AuthenticationActivity.this) {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onComplete() {

                                EventBus.getDefault().post(new OnuserAuthnticate());

                                Toast.makeText(AuthenticationActivity.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
                                finish();
                             /*   Toast.makeText(AuthenticationActivity.this, "welcomen", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent( AuthenticationActivity.this, HomeActivity.class));*/
                            }
                        });

            }
        });
    }

    private void observe() {
        Disposable disposableIsMode = viewModel.getIsInLoginMode().subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            btnIsModeRegister.setText(R.string.register);
                            btnisModeLogin.setText(R.string.login);
                        } else {
                            btnIsModeRegister.setText(R.string.login);
                            btnisModeLogin.setText(R.string.register);
                        }
                    }
                });
        compositeDisposable.add(disposableIsMode);

        Disposable disposablepr = viewModel.getProgressVisibility().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        progressBar.setVisibility(View.VISIBLE);
                        btnisModeLogin.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnisModeLogin.setVisibility(View.VISIBLE);

                    }
                });
        compositeDisposable.add(disposablepr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public int getLayout() {
        return R.id.coordinatorlayout;
    }
}