package com.example.mvvm.listcart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartItem{

	@SerializedName("total_price")
	private int totalPrice;

	@SerializedName("cart_items")
	private List<CartItemsItem> cartItems;

	@SerializedName("count")
	private int count;

	@SerializedName("payable_price")
	private int payablePrice;

	public int getTotalPrice(){
		return totalPrice;
	}

	public List<CartItemsItem> getCartItems(){
		return cartItems;
	}

	public int getCount(){
		return count;
	}

	public int getPayablePrice(){
		return payablePrice;
	}
}