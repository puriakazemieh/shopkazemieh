package com.example.mvvm.details;

import com.google.gson.annotations.SerializedName;

public class Colors{

	@SerializedName("color")
	private String color;

	@SerializedName("name_colors")
	private String nameColors;

	@SerializedName("id")
	private String id;

	public String getColor(){
		return color;
	}

	public String getNameColors(){
		return nameColors;
	}

	public String getId(){
		return id;
	}
}