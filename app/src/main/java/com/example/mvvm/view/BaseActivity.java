package com.example.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getLayout();
    public void showSnackBarMessage(String message) {
        Snackbar.make(findViewById(getLayout()),message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
