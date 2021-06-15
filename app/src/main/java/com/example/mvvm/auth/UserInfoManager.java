package com.example.mvvm.auth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class UserInfoManager {
    private SharedPreferences sharedPreferences;

    public UserInfoManager(Context context) {
        sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }


    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }
    @Nullable
    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }

    @Nullable
    public String getToken() {
        return sharedPreferences.getString("token", null);
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
