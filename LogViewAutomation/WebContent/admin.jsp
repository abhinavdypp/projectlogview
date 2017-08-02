<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #117ac8;
}

li {
    float: left;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size:20px
}


li a:hover {
    background-color: #085a97;
}
</style>
</head>
<body background="C:\logview\TestingWorkspace\LogViewAutomation\LogViewAutomation\bg.jpg" link="black" alink="black" vlink="black">
<div >
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="1050" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table></div>

<div align="center" style="position:relative;top:100px;";>
<ul>
  <li><a class="active" href="<%=request.getContextPath()%>/AdminController?action=updateUserDetails" name="updateUserDetails" id="updateUserDetails"><b>Accounts</b></a></li>
  <li><a href="#"><b>Servers</b></a></li>
  <li><a href="<%=request.getContextPath()%>/ApplicationsDetailsController" name="getUserDetails" id="getUserDetails"><b>Applications</b></a></li>
  <li><a href="#"><b>About</b></a></li>
  
  <li style=" position:absolute;right:0; text-align:right "><a href="#"><b>Logout</b></a></li>
</ul>
</div>
<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right "><b><%=timeStamp %></b></div>
</body>
</html>