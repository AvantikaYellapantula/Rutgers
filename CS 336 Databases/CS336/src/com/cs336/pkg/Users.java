package com.cs336.pkg;

public class Users {

		private String UserName;
		private String Password;
		private String FirstName;
		private String LastName;
		private String Email;
		private String State;
		private String Street;
		private String City;
		private int ZipCode;
		private boolean Iscustomerrep;
		private boolean Isadmin;
		private boolean IsBuyer;
		private boolean IsSeller;
		
		public Users(String username, String password, String email, String firstname, String lastname, String state,String street, String city, int zipcode,boolean iscustomerrep,boolean isadmin, boolean isbuyer, boolean isseller) {
			super();
			this.UserName = username;
			Password = password;
			FirstName = firstname;
			LastName = lastname;
			State = state;
			Street = street;
			City = city;
			ZipCode = zipcode;
			Iscustomerrep = iscustomerrep;
			Isadmin=isadmin;
			IsBuyer=isbuyer;
			IsSeller=isseller;
			
		}


		public Users(){
			
		}
		
		
		public String getUserName() {
			return UserName;
		}

		public void setUserName(String username) {
			UserName = username;
		}
		
		public String getEmail() {
			return Email;
		}

		public void setEmail(String email) {
			Email = email;
		}
		
		public String getPassword() {
			return Password;
		}

		public void setPassword(String password) {
			FirstName = password;
		}

		public String getFirstName() {
			return FirstName;
		}

		public void setFirstName(String firstname) {
			FirstName = firstname;
		}

		public String getLastName() {
			return LastName;
		}

		public void setLastName(String lastname) {
			LastName = lastname;
		}
		
		public String getState() {
			return State;
		}

		public void setState(String state) {
			State = state;
		}
		
		public String getStreet() {
			return Street;
		}

		public void setStreet(String street) {
			Street = street;
		}
		
		public String getCity() {
			return City;
		}

		public void setCity(String city) {
			City = city;
		}
		
		public int getZipCode() {
			return ZipCode;
		}

		public void setZipCode(int zipcode) {
			ZipCode = zipcode;
		}
		
		public boolean getIscustomerrep() {
			return Iscustomerrep;
		}

		public void setIscustomerrep(boolean iscustomerrep) {
			Iscustomerrep = iscustomerrep;
		}
		
		public boolean getIsadmin() {
			return Iscustomerrep;
		}

		public void setIsadmin(boolean isadmin) {
			Isadmin = isadmin;
		}
		
		public boolean getIsBuyer() {
			return IsBuyer;
		}

		public void setIsBuyer(boolean isbuyer) {
			Isadmin = isbuyer;
		}
		
		public boolean getIsSeller() {
			return IsSeller;
		}

		public void setIsSeller(boolean isseller) {
			IsSeller = isseller;
		}


}
