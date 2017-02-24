package com.cs336.pkg;

import java.util.Date;

public class Sells {
	
	private Date EndDate;
	private float MinPrice;
	private String UserName;
	private int IsClosed;
	private String ItemNum;
	
	public Sells(Date enddate, float minprice, String username, int isclosed, String itemnum) {
		super();
		this.EndDate = enddate;
		MinPrice = minprice;
		UserName = username;
		IsClosed = isclosed;
		ItemNum = itemnum;
		
	}


	public Sells(){
		
	}
	
	
	public String getItemNum() {
		return ItemNum;
	}

	public void setItemNum(String itemnum) {
		this.ItemNum = itemnum;
	}
	
	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date enddate) {
		EndDate = enddate;
	}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String username) {
		UserName = username;
	}

	public int getIsClosed() {
		return IsClosed;
	}

	public void setIsClosed(int isclosed) {
		IsClosed = isclosed;
	}
	
	public float getMinPrice() {
		return MinPrice;
	}

	public void setMinPrice(float minprice) {
		MinPrice = minprice;
	}
	
}
