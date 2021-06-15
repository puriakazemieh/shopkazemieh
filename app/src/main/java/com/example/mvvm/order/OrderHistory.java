package com.example.mvvm.order;

import com.google.gson.annotations.SerializedName;

public class OrderHistory{

	@SerializedName("ref_id")
	private String refId;

	@SerializedName("datetime")
	private String datetime;

	@SerializedName("address")
	private String address;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("family")
	private String family;

	@SerializedName("postal_code")
	private String postalCode;

	public String getRefId(){
		return refId;
	}

	public String getDatetime(){
		return datetime;
	}

	public String getAddress(){
		return address;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getFamily(){
		return family;
	}

	public String getPostalCode(){
		return postalCode;
	}
}