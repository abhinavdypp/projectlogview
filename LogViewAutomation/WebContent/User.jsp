<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
   <%@ page import="javax.servlet.http.Cookie"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
table, td,th
{
boarder: 1px solid blue;
font-family:sans-serif;
}
div{
font-family:sans-serif;
}
</style>


<title>User Page</title>


</head>
<body  background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg" link="black" alink="black" vlink="black">
<center>
<%
String userName = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("user")) userName = cookie.getValue();
}
}
%>

<div>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#0c69c2; text-shadow: 2px 2px 4px #8ec9db;"><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://t4.ftcdn.net/jpg/01/07/85/89/240_F_107858989_SJogeLthvc6WZ6lP6EsuLlxIRNtsz4JH.jpg" width="70" height="70"></td>
		<td><h3><%=userName %></h3></td>
	</tr>	
	
</tbody></table></div><br><br><br><br><br><br><br>


<br>
<a href="<%=request.getContextPath()%>/UserController?action=getlogdetails" name="getlogdetails" id="getlogdetails">Get Log details </a></div>
</center><br><br></body></html>