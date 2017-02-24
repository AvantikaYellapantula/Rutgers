<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In Page</title>
</head>
<body>
<h1><center>Welcome To login Page</center></h1>
<form action = "check.jsp" method = "post">
<table>
<tr>
<td>User Name</td>
<td><input type = "text" name = "UserName"/></td>
</tr>
<tr>
<td>Password</td>
<td><input type = "password" name = "Password"/></td>
</tr>
<tr>
<td><button type = "submit">Login</button></td>
</tr>

</table>
</form>
</body>
<a href="register.jsp"><button>Create An Account</button></a>
</html>