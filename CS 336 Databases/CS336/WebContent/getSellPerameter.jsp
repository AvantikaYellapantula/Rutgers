<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*, com.cs336.pkg.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%Users user= (Users)session.getAttribute("loggedInUser");
 //System.out.println("Username !!!!!!!!!!! ="+ user.getUserName());
  String ItemNum = UUID.randomUUID().toString();
  //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String EndDate = request.getParameter("EndDate");
  String Namee = request.getParameter("Namee"); 
  String Typee  =request.getParameter("Typee");
  String Color=request.getParameter("Color");
  String Size=request.getParameter("Size");
  String Conditionn=request.getParameter("Conditionn");
  String BrandName=request.getParameter("BrandName");
  String MinPrice=request.getParameter("Minprice");
  boolean IsClosed= false;
  //float IntPrice=Float.parseFloat(request.getParameter("Intprice"));
  
  try{
         Class.forName("com.mysql.jdbc.Driver");
         //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "root");

           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "TheWizLL83v");
           Statement st=con.createStatement();
           String query1 = "insert into Footwear(ItemNum,Typee,Namee,Conditionn,Color,Size,BrandName) values('"+ItemNum+"','"+Typee+"','"+Namee+"','"+Conditionn+"','"+Color+"','"+Size+"','"+BrandName+"')";
           System.out.println(query1);
           int i=st.executeUpdate(query1);
        System.out.println("Data is successfully inserted!");
        }
        catch(Exception e){
        System.out.print(e);
        e.printStackTrace();
        }
  
  try{
      Class.forName("com.mysql.jdbc.Driver");
      //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "root");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "TheWizLL83v");
        Statement st=con.createStatement();
        String query2 = "insert into Sells(EndDate,MinPrice,UserName,IsClosed,ItemNum) values('"+EndDate+"','"+MinPrice+"','"+user.getUserName()+"',"+IsClosed+",'"+ItemNum+"')";
        System.out.println(query2);
        int i=st.executeUpdate(query2);
     System.out.println("Data is successfully inserted!");
     response.sendRedirect("Home.jsp");
     }
     catch(Exception e){
     System.out.print(e);
     e.printStackTrace();

     }        
       %>


</body>
</html>