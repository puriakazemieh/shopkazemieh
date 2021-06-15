package com.example.mvvm.listcart;

import com.google.gson.annotations.SerializedName;

public class ChangeModelItem{

	@SerializedName("product_id")
	private int productId;

	@SerializedName("count")
	private int count;

	@SerializedName("id")
	private int id;

	public int getProductId(){
		return productId;
	}

	public int getCount(){
		return count;
	}

	public int getId(){
		return id;
	}
}