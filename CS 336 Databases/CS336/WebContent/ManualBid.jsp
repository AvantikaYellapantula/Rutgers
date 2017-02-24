<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Man Bid</title>
</head>
<body>
<h1><center>Manual Bidding</center></h1>
<form action="GetManPara.jsp" method="post">
	<table>
	<tr>
	<td>Item Number</td>
	<td><input type = "text" name = "itemnum"/></td>
	</tr>
	<tr>
	<td>Bidding Price</td>
	<td><input type = "text" name = "intprice"/></td>
	</tr>
	<tr>
	<td><button type = "submit">Submit</button></td>
	</tr>
</table>


</body>
</html>
