<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Form</title>
</head>
<body>
<h1>Please Fill Out The Registration Form</h1>
<form action = "getRegPerameter.jsp" method = "post">
<table>
<tr>
<td>First Name</td>
<td><input type = "text" name = "FName"/></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type = "text" name = "LName"/></td>
</tr>
<tr>
<td>Email Address</td>
<td><input type = "text" name = "Email"/></td>
</tr>
<tr>
<td>State</td>
<td><input type = "text" name = "State"/></td>
</tr>
<tr>
<td>Street</td>
<td><input type = "text" name = "Street"/></td>
</tr>
<tr>
<td>City</td>
<td><input type = "text" name = "City"/></td>
</tr>
<tr>
<td>Zip Code</td>
<td><input type = "text" name = "ZipCode"/></td>
</tr>
<tr>
<td>User Name</td>
<td><input type = "text" name = "UserName"/></td>
</tr>
<tr>
<td>Password</td>
<td><input type = "password" name = "Password"/></td>
</tr>
<tr>
<td><button type = "submit">Submit</button></td>
</tr>

</table>
</form>

</body>
</html>
