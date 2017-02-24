<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% ApplicationDAO dao = new ApplicationDAO();%>	 

<%String username=request.getParameter("UserName"); 
	String password=request.getParameter("Password"); 
	
	//System.out.println(username);
	Users loggedinUser = dao.CheckUser(username, password);
	if(loggedinUser!=null){
		session.setAttribute("loggedInUser", loggedinUser);

		//for the buying query don't change
		session.setAttribute("buy_type", "");
		session.setAttribute("buy_name", "");
		session.setAttribute("buy_con", "");
		session.setAttribute("buy_color", "");
		session.setAttribute("buy_size", "");
		session.setAttribute("buy_price", "");
		
		response.sendRedirect("Home.jsp");
	}
	else{
		session.setAttribute("ERROR", "hi");
		response.sendRedirect("Error.jsp");
	}%>


</body>
</html>
