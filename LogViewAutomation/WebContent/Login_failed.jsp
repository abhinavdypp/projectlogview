<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<style>

body
{
font-family:Arial;
font-size: 12px;
}
</style>

</head>
<body background="http://labcenter.com.br/wp-content/uploads/2016/08/gradiente-azul-claro-fundo-1024x768.jpg" >
<div>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="70"><img src="http://i2mag.com/wp-content/uploads/2012/01/IBM.jpg" width="120" height="40"></td></b>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#0c69c2; text-shadow: 2px 2px 4px #8ec9db;"><b>LogView Application</b></td>
		
	</tr>	
	
</tbody></table></div><br><br><br><br><br><br><br>
<center>
<h4><p style="color:red;">Authentication Failed</p></h4>
<h2>!!!Login Here!!!</h2>
<form method="POST" action="UserLoginController">
 <b>Username:  </b><input type="text" name="uname"><br><br>
 <b>Password:   </b><input type="password" name="pass"><br><br>

	<p>
		<input type="submit" name="submit" value="    Login    ">
	</p></form></center>
	<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right ";><b><%=timeStamp %></b></div>
</body>
</html>