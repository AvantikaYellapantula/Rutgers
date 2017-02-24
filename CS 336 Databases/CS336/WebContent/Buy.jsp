<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*, com.cs336.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buy Item</title>
</head>

<body>
<a href="Home.jsp"><button>Home</button></a>
</body>

<body>
<form action="Buy_getPerameters.jsp" method=post>
<center>
<table cellpadding=4 cellspacing=2 border=0>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Buy Footwear: options</font>
<br>
<font size=1><sup>*</sup> Required Fields</font>
</th>

<tr>
<td>Type of Footwear:</td>
<td>
	<select name="Typee">
		<option>  </option>
		<option> Male </option>
		<option> Female </option>
		<option> Juniors </option>
    </select>
</td>
</tr>

<tr>
<td>Name of Footwear:</td>
<td>
	<select name="Name">
		<option>  </option>
		<option> Shoes </option>
		<option> Boots </option>
		<option> Slipper </option>
		<option> Sandle </option>
		<option> Flip Flops </option>
    </select>
</td>
</tr>

<tr>
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
	<option> US  8.5</option>
	<option> US 9 </option>
	<option> US  9.5</option>
	<option> US 10 </option>
	<option> US 10.5 </option>
	<option> US <11 /option>
	<option> US 5 1.5</option>
	<option> US 12 </option>
	</select>
</td>
</tr>
<tr>
<td>Color:</td>
<td><input type = "text" name = "Color"/></td>
</tr>

<tr>
<td>Condition:</td>
<td>
	<select name="Condition">
	<option>  </option>
	<option> Excellent </option>
	<option> Good </option>
	<option> poor </option>
	</select>
</td>
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

<body>
<center>
<table width="59%" border="1">

    <%
    ApplicationDAO dao=new ApplicationDAO();
    ResultSet rs=dao.query((String)session.getAttribute("buy_type"),(String)session.getAttribute("buy_name"),(String)session.getAttribute("buy_con"),
    		(String)session.getAttribute("buy_color"),(String)session.getAttribute("buy_size"));
        while(rs.next())
        {
            %>
            <tr bgcolor="#c8d8f8">
            	<form action="ItemDetails.jsp">
            		<td><input type = "text" name = "IDnum" value = <%= rs.getString("ItemNum")%>/></td>
            		
                    <td>
                    Product Name:  <%= rs.getString("Namee")%><br>
                    Band Name:  <%= rs.getString("BrandName")%></td>
                    
                    <td>Type:  <%= rs.getString("Typee")%><br>
                    Condition:  <%= rs.getString("Conditionn")%><br>
                    Color:  <%= rs.getString("Color")%><br>
                    Size:  <%= rs.getString("Size")%><br>                    
                    </td> 
                    
	                <td>                                     
	                <input type="submit" value="Bid">
	                </td>
                </form>
                </tr>
            <% 
        }
    %>
</table>
</body>
</html>
