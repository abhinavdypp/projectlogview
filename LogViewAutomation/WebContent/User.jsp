<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>  
   <%@ page import="javax.servlet.http.Cookie"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body
{

font-family:Arial;


}
</style>


<title>User Page</title>


</head>
<body  background="http://labcenter.com.br/wp-content/uploads/2016/08/gradiente-azul-claro-fundo-1024x768.jpg" link="black" alink="black" vlink="black">
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
		<td width="150" height="70"><img src="http://i2mag.com/wp-content/uploads/2012/01/IBM.jpg" width="120" height="40"></td></b>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#0c69c2; text-shadow: 2px 2px 4px #8ec9db;"><b>LogView Application</b></td>
		<td width="150" height="100"><img src="http://csadmissions.gcu.edu.pk/DCS/img/default1.png" width="50" height="50"></td>
		<td><h3><%=userName %></h3></td>
	</tr>	
	
</tbody></table></div><br><br><br><br><br><br><br>


<br>
<a href="<%=request.getContextPath()%>/UserController?action=getlogdetails" name="getlogdetails" id="getlogdetails">Get Log details </a></div>
</center><br><br>
<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right "><b><%=timeStamp %></b></div>
</body></html>