package com.example.mvvm.auth;

import com.google.gson.annotations.SerializedName;

public class Token {

	@SerializedName("message")
	private String message;

	@SerializedName("email")
	private String email;

	public String getMessage(){
		return message;
	}

	public String getEmail(){
		return email;
	}
}