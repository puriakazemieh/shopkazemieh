package com.example.mvvm.auth;

import com.google.gson.annotations.SerializedName;

public class SuccessRepose {

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}