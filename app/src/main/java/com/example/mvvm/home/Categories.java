package com.example.mvvm.home;

import com.google.gson.annotations.SerializedName;

public class Categories{

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	public String getImage(){
		return image;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}