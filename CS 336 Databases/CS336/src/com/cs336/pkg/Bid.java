package com.cs336.pkg;

public class Bid {
	
	private String ItemNum;
	private float IntPrice;
	private String BidID;
	private float Increment;
	private float maxPrice;
	private String UserName;
	private int IsManual;
	private int IsOld;
	
	
	public Bid(String itemnum,float intprice, String bidid, float increment, float maxprice,String username, int ismanual,int isold) {
		super();
		this.ItemNum = itemnum;
		IntPrice = intprice;
		BidID = bidid;
		Increment = increment;
		maxPrice = maxprice;
		UserName = username;
		IsManual = ismanual;
		IsOld = isold;
	}


	public Bid(){
		
	}
	
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String username) {
		UserName = username;
	}
	
	public String getItemNum() {
		return ItemNum;
	}

	public void setItemNum(String itemnum) {
		ItemNum = itemnum;
	}

	public float getIntPrice() {
		return IntPrice;
	}

	public void setIntPrice(float intprice) {
		IntPrice = intprice;
	}

	public String getBidID() {
		return BidID;
	}

	public void setBidID(String bidid) {
		BidID = bidid;
	}
	
	public float getIncrement() {
		return Increment;
	}

	public void setIncrement(float increment) {
		Increment = increment;
	}
	
	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxprice) {
		maxPrice = maxprice;
	}
	
	public int getIsManual() {
		return IsManual;
	}

	public void setIsManual(int ismanual) {
		IsManual = ismanual;
	}
	
	public int getIsOld() {
		return IsOld;
	}

	public void setIsOld(int isold) {
		IsOld=isold;
	}
	
	
}
