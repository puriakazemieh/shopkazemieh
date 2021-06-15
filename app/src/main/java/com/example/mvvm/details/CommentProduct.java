package com.example.mvvm.details;

import com.google.gson.annotations.SerializedName;

public class CommentProduct{

	@SerializedName("date")
	private String date;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	@SerializedName("email")
	private String email;

	public String getDate(){
		return date;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getContent(){
		return content;
	}

	public String getEmail(){
		return email;
	}
}