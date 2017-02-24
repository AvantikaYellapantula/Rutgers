package com.cs336.pkg;

public class Footwear {

	private String ItemNum;
	private String Typee;
	private String Namee;
	private String Condition;
	private String Color;
	private float Size;
	private String BrandName;
	
	public Footwear(String itemnum, String type, String name, String condition, String color, float size,String brandname) {
		super();
		this.ItemNum = itemnum;
		Typee = type;
		Namee = name;
		Condition = condition;
		Color = color;
		Size = size;
		BrandName = brandname;
		
	}


	public Footwear(){
		
	}
	
	
	public String getItemNum() {
		return ItemNum;
	}

	public void setItemNum(String itemnum) {
		this.ItemNum = itemnum;
	}
	
	public String getType() {
		return Typee;
	}

	public void setUserName(String type) {
		Typee = type;
	}
	
	public String getName() {
		return Namee;
	}

	public void setPassword(String name) {
		Namee = name;
	}

	public String getCondition() {
		return Condition;
	}

	public void setFirstName(String condition) {
		Condition = condition;
	}

	public String getColor() {
		return Color;
	}

	public void setLastName(String color) {
		Color = color;
	}
	
	public float getSize() {
		return Size;
	}

	public void setState(float size) {
		Size = size;
	}
	
	public String getBrandName() {
		return BrandName;
	}

	public void setStreet(String brandname) {
		BrandName = brandname;
	}
	
	
}
