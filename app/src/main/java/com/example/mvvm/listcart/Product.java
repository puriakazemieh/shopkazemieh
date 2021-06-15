package com.example.mvvm.listcart;

import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("seller")
	private String seller;

	@SerializedName("image")
	private String image;

	@SerializedName("previousPrice")
	private String previousPrice;

	@SerializedName("discount")
	private int discount;

	@SerializedName("title")
	private String title;

	@SerializedName("newPrice")
	private int newPrice;

	public String getSeller(){
		return seller;
	}

	public String getImage(){
		return image;
	}

	public String getPreviousPrice(){
		return previousPrice;
	}

	public int getDiscount(){
		return discount;
	}

	public String getTitle(){
		return title;
	}

	public int getNewPrice(){
		return newPrice;
	}
}