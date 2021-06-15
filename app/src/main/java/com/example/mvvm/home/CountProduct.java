package com.example.mvvm.home;

import com.google.gson.annotations.SerializedName;

public class CountProduct{

	@SerializedName("count")
	private int count;

	public int getCount(){
		return count;
	}
}