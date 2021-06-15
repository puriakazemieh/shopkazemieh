package com.example.mvvm.home;

import com.google.gson.annotations.SerializedName;

public class Products{

	public static int SORT_LATEST=0;
	public static int SORT_POPULAR=1;
	public static int SORT_PRICE=2;

	@SerializedName("commentconut")
	private int commentconut;

	@SerializedName("image")
	private String image;

	@SerializedName("previousPrice")
	private int previousPrice;

	@SerializedName("discount")
	private int discount;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("newPrice")
	private int newPrice;

	public int getCommentconut(){
		return commentconut;
	}

	public String getImage(){
		return image;
	}

	public int getPreviousPrice(){
		return previousPrice;
	}

	public int getDiscount(){
		return discount;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public int getNewPrice(){
		return newPrice;
	}
}