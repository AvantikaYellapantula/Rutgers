package resources;

import java.io.Serializable;

public class user implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;

	public user() {
		
		userName = "";
		password = "";
		
	}
	
	public user(String userNameIn, String passwordIn, boolean adminIn) {
	
		userName = userNameIn;
		password = passwordIn;

	}
	
	public String getUserName()
	{
		
		return userName;
		
	}
	
	public String getPassword()
	{
		
		return password;
	
	}
	
	public void setUserName(String userNameIn)
	{
		
		userName = userNameIn;
	}
	
	public void setPassword(String passwordIn)
	{
		
		password = passwordIn;
		
	}
	
	public String toString()
	{
		
		return userName;
		
	}
}
