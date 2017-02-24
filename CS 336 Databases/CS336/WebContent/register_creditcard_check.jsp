<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*, com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>check credit card</title>
</head>
<body>
<% ApplicationDAO dao = new ApplicationDAO();
boolean error=false;%>

<%String Name=request.getParameter("Name"); 
  String CardNumber=request.getParameter("CardNumber");
  String Provider=request.getParameter("Provider");
  String CVS=request.getParameter("CVS");
  String Date=request.getParameter("Date");
  String AccountNumber=request.getParameter("AccountNumber");
  String RoutingNumber=request.getParameter("RoutingNumber");
  
  //inserting the credit card
  if (dao.CheckCreditCard(CardNumber)==true){
	  session.setAttribute("ERROR", "Credit Card exists");error=true;
  }
  else if (dao.CheckAccount(AccountNumber)==true){
	  session.setAttribute("ERROR", "Account Number exists");error=true;
  }
  else {
  	try{
           Connection con = dao.getConnection();
           Statement st=con.createStatement();
           String query = "INSERT INTO `mydb`.`creditcard` (`CreditcardNumber`, `ExpiryDate`, `CVV`, `Name`, `Provider`) "+
           "values('"+CardNumber+"','"+Date+"','"+CVS+"','"+Name+"','"+Provider+"')";
           System.out.println(query);
           int i=st.executeUpdate(query);
           if (i!=1){
        	   session.setAttribute("ERROR", "error inserting creditcard");error=true;
           }
        System.out.println("Data is successfully inserted!"+i+"|");
        
  //inserting the account
	 
	           con = dao.getConnection();
	           st=con.createStatement();
	           query = "INSERT INTO `mydb`.`bankinfo` (`RoutingNum`, `AccountNum`) "+
	           "values('"+RoutingNumber+"','"+AccountNumber+"')";
	           System.out.println(query);
	           i=st.executeUpdate(query);
	           if (i!=1){
	        	   session.setAttribute("ERROR", "error inserting account");error=true;
	           }
	        System.out.println("Data is successfully inserted!"+i);
	        
  //setting Provides
      con = dao.getConnection();
      st=con.createStatement();
      //query = "INSERT INTO `mydb`.`provides` (`Whenn`, `CreditcardNumber`, `AccountNum`, `UserName`) "+
      query = "INSERT INTO `mydb`.`provides` (`CreditcardNumber`, `AccountNum`, `UserName`) "+
      "values('"+CardNumber+"','"+AccountNumber+"','"+session.getAttribute("loggedInUser")+"')";
      System.out.println(query);
      i=st.executeUpdate(query);
      if (i!=1){
   	   session.setAttribute("ERROR", "error inserting relation");error=true;
      }
   System.out.println("Data is successfully inserted!"+i);
   }
   catch(Exception e){e.printStackTrace();session.setAttribute("ERROR", e.getMessage());}}
  
  if (error==true)
	  response.sendRedirect("register_error_card.jsp");
  else 
	  response.sendRedirect("index.jsp");
   %>
</body>
</html>
