<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item details</title>
</head>
<body>

<%String ID=request.getParameter("IDnum"); 
	System.out.println("||"+ID+"||");
	//use ID to find the current item the user selected
%>


</body>
<a href="AutoBid.jsp"><button>Automatic Biddinng</button></a>
<a href="ManualBid.jsp"><button>Manual Biddinng</button></a>
</html>
