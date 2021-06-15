package com.example.mvvm.home;

import com.google.gson.annotations.SerializedName;

public class Banners{

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private int type;

	@SerializedName("value")
	private String value;

	public String getImage(){
		return image;
	}

	public int getId(){
		return id;
	}

	public int getType(){
		return type;
	}

	public String getValue(){
		return value;
	}
}