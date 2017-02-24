<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*, java.io.*, com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% ApplicationDAO dao = new ApplicationDAO();%>

<%String FName=request.getParameter("FName"); 
  String LName=request.getParameter("LName");
  String Email=request.getParameter("Email");
  String State=request.getParameter("State");
  String Street=request.getParameter("Street");
  String City=request.getParameter("City");
  String UserName=request.getParameter("UserName");
  String Password=request.getParameter("Password");
  boolean iscustomerrep = false;
  boolean isadmin = false;
 %> 
  <%
  if(dao.CheckUser(UserName) == false)  {
	  session.setAttribute("loggedInUser", UserName);
  
  	try{
  		int ZipCode=Integer.parseInt(request.getParameter("ZipCode"));
           Connection con = dao.getConnection();
           Statement st=con.createStatement();
           String query = "insert into Users(UserName,Password,Email,FName,LName,State,Street,City,ZipCode,Iscustomerrep,Isadmin) values('"+UserName+"','"+Password+"','"+Email+"','"+FName+"','"+LName+"','"+State+"','"+Street+"','"+City+"','"+ZipCode+"',"+iscustomerrep+","+isadmin+")";
           System.out.println(query);
           int i=st.executeUpdate(query);
           if (i!=1){
        	   session.setAttribute("ERROR", "error inserting user");
        	   response.sendRedirect("Errorreg.jsp");
           }
        System.out.println("Data is successfully inserted!");
       
        response.sendRedirect("register_creditcard.jsp");
        }
        catch(Exception e){System.out.print(e);session.setAttribute("ERROR", e.getMessage());e.printStackTrace();response.sendRedirect("register_error.jsp");}
  }
%>
</body>
</html>
