package com.example.mvvm.orderDetails;

import com.google.gson.annotations.SerializedName;

public class OrderDetials{

	@SerializedName("seller")
	private String seller;

	@SerializedName("image")
	private String image;

	@SerializedName("color")
	private String color;

	@SerializedName("name_colors")
	private String nameColors;

	@SerializedName("previousPrice")
	private String previousPrice;

	@SerializedName("count")
	private String count;

	@SerializedName("discount")
	private String discount;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("newPrice")
	private String newPrice;

	public String getSeller(){
		return seller;
	}

	public String getImage(){
		return image;
	}

	public String getColor(){
		return color;
	}

	public String getNameColors(){
		return nameColors;
	}

	public String getPreviousPrice(){
		return previousPrice;
	}

	public String getCount(){
		return count;
	}

	public String getDiscount(){
		return discount;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getNewPrice(){
		return newPrice;
	}
}