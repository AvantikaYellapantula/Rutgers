<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Someone has your credit card already in the system or someone has your account already in the system. Please try again. </h1>

</body>
<a href="register_creditcard.jsp"><button>Try again</button></a>

 <script type="text/javascript">
var Msg ='<%=session.getAttribute("ERROR")%>';  
 alert(Msg);
 </script> 
</body>
</html>
