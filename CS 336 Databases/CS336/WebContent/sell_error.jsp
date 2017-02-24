<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERROR </title>
</head>
<body>
Item has not been added because an error has occurred. Please try again.<br>
<a href="sell.jsp"><button>Try again</button></a><br>
<a href="Home.jsp"><button>Home</button></a>

 <script type="text/javascript">
var Msg ='<%=session.getAttribute("ERROR")%>';  
 alert(Msg);
 </script> 
</body>
</html>