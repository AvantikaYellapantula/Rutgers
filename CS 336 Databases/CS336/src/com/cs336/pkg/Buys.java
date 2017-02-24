package com.cs336.pkg;

import java.util.Date;

public class Buys {
	
	private float IntPrice;
	private String UserName;
	private String ItemNum;
	
	public Buys(float intprice, String username, String itemnum) {
		super();
		this.IntPrice = intprice;
		UserName = username;
		ItemNum = itemnum;
		
	}


	public Buys(){
		
	}
	
	
	public String getItemNum() {
		return ItemNum;
	}

	public void setItemNum(String itemnum) {
		this.ItemNum = itemnum;
	}
	
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String username) {
		UserName = username;
	}
	
	public float getIntPrice() {
		return IntPrice;
	}

	public void setMinPrice(float intprice) {
		IntPrice = intprice;
	}
	
}


