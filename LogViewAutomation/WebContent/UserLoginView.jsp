<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg" link="black" alink="black" vlink="black">
<div style="top:0">
<table border=0>
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="80"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table>

</div>
<center>
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