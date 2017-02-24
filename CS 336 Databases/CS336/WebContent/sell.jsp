<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<a href="Home.jsp"><button>Home</button></a>
</body>

<body>
<form action="getSellPerameter.jsp" method=post>
<center>
<table cellpadding=4 cellspacing=2 border=0>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Buy Footwear: options</font>
<br>
<font size=1><sup>*</sup> Required Fields</font>
</th>

<tr bgcolor="#c8d8f8">
<td>Type of Footwear:</td>
<td>
	<select name="Typee">
		<option> Male </option>
		<option> Female </option>
		<option> Juniors </option>
    </select>
</td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Name of Footwear:</td>
<td>
	<select name="Namee">
		<option> Shoes </option>
		<option> Boots </option>
		<option> Slipper </option>
		<option> Sandle </option>
		<option> Flip Flops </option>
    </select>
</td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Size:</td>
<td>
	<select name="Size">
	<option>  </option>
	<option> US 4 </option>
	<option> US 4.5 </option>
	<option> US 5 </option>
	<option> US 5.5 </option>
	<option> US 6 </option>
	<option> US 6.5 </option>
	<option> US 7 </option>
	<option> US 7.5 </option>
	<option> US 8 </option>
	<option> US 8.5 </option>
	<option> US 9 </option>
	<option> US 9.5 </option>
	<option> US 10 </option>
	<option> US 10.5 </option>
	<option> US 11 </option>
	<option> US 11.5 </option>
	<option> US 12 </option>
	</select>
</td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Color:</td>
<td><input type = "text" name = "Color"/></td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Condition:</td>
<td>
	<select name="Conditionn">
	<option> Excellent </option>
	<option> Good </option>
	<option> poor </option>
	</select>
</td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Currency</td>
<td>
	<select name="Currency">
	<option>US $</option>
	<option>CAD $</option>
	<option>EURO</option>
	<option>POUND</option>
	</select>
</td>
</tr>
<tr bgcolor="#c8d8f8">
<td>Hidden Minimum Price:</td>
<td><input type = "text" name = "Minprice"/></td>
</tr>
<tr bgcolor="#c8d8f8">
<td>BrandName:</td>
<td><input type = "text" name = "BrandName"/></td>
</tr>
<tr bgcolor="#c8d8f8">
<td>EndDate (yyyy-MM-dd):</td>
<td><input type = "text" name = "EndDate"/></td>
</tr>
<tr bgcolor="#c8d8f8">
<td><button type = "submit">Submit</button></td>
</tr>
</table>
</form>
</body>
</html>
