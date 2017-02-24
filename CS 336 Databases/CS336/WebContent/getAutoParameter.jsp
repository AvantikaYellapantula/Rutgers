<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*, com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% ApplicationDAO dao = new ApplicationDAO();%>
<%Users user= (Users)session.getAttribute("loggedInUser");
  String BidID = UUID.randomUUID().toString();
  String ItemNum = request.getParameter("itemnum");
  String Increment = request.getParameter("increment");
  String IntPrice = request.getParameter("intprice"); 
  String MaxPrice=request.getParameter("maxprice");
  int ismanual = 0;
  int isold = 0;
  //float IntPrice=Float.parseFloat(request.getParameter("Intprice"));
  
  
  
  try{
         Class.forName("com.mysql.jdbc.Driver");
         //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "root");

          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?autoReconnect=true", "root", "TheWizLL83v");
           Statement st=con.createStatement();
           String query1 = "insert into Bid(ItemNum,IntPrice,BidID,Increment,maxPrice,UserName,IsManual,IsOld) values('"+ItemNum+"','"+IntPrice+"','"+BidID+"','"+Increment+"','"+MaxPrice+"','"+user.getUserName()+"','"+ismanual+"','"+isold+"')";
           System.out.println(query1);
           int i=st.executeUpdate(query1);
           dao.priceIncrement(); 
           dao.alertUser();
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
