<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1><center>Automatic Bidding</center></h1>

<form action="getAutoParameter.jsp" method="post">
	<table>
	<tr>
	<td>ItemNum</td>
	<td><input type = "text" name = "itemnum"/></td>
	</tr>
	<tr>
	<td>Max Price</td>
	<td><input type = "text" name = "maxprice"/></td>
	</tr>
	<tr>
	<td>Increment</td>
	<td><input type = "text" name = "increment"/></td>
	</tr>
	<tr>
	<td>Initial Price</td>
	<td><input type = "text" name = "intprice"/></td>
	</tr>
	<tr>
	<td><button type = "submit">Submit</button></td>
	</tr>
</table>

</body>
</html>