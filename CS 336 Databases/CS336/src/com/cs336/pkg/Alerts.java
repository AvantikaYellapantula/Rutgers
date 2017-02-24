package com.cs336.pkg;

public class Alerts {
	
	private String AlertID;
	private String Alert;
	private String UserName;
	private String ItemNum;
	
	
	public Alerts(String alertid,String alert, String username, String itemnum) {
		super();
		this.AlertID = alertid;
		Alert = alert;
		UserName = username;
		ItemNum = itemnum;
		
	}

	public Alerts(){
		
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

	public String getAlertId() {
		return AlertID;
	}

	public void setIntPrice(String alertid) {
		AlertID = alertid;
	}

	public String getAlert() {
		return Alert;
	}

	public void setBidID(String alert) {
		Alert = alert;
	}
	
	

}
