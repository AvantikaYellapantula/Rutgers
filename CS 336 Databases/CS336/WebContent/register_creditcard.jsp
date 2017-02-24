<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Credit Card</title>
</head>

<body>
<a href="index.jsp"><button>Home</button></a>
</body>

<body>
<form action="register_creditcard_check.jsp" method=post>
<center>
<table cellpadding=4 cellspacing=2 border=0>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Add Credit Card Form</font>
<br>
<font size=1><sup>*</sup> Required Fields</font>
</th>

<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Name on Card<sup>*</sup></b> 
<br>
<input type="text" name="Name" value="" size=15 maxlength=20></td>
<td valign=top> 
<b>Card Number<sup>*</sup></b> 
<br>
<input type="text" name="CardNumber" value="" size=15 maxlength=20></td>
</tr>

<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Provider<sup>*</sup></b> 
<br>
<input type="text" name="Provider" value="" size=15 maxlength=20></td>
<td valign=top> 
<b>CVS Number<sup>*</sup></b> 
<br>
<input type="text" name="CVS" value="" size=15 maxlength=20></td>
</tr>

<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Expiry Date<sup>*</sup></b> 
<br>
<input type="text" name="Date" value="" size=15 maxlength=20></td>
</tr>

<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Account Number<sup>*</sup></b> 
<br>
<input type="text" name="AccountNumber" value="" size=15 maxlength=20></td>
<td valign=top> 
<b>Routing Number<sup>*</sup></b> 
<br>
<input type="text" name="RoutingNumber" value="" size=15 maxlength=20></td>
</tr>

<tr bgcolor="#c8d8f8">
<td  align=center colspan=2>
<input type="submit" value="Submit"> <input type="reset"  
value="Reset">
</td>
</tr>
</table>
</center>
</form>
</body>
</html>