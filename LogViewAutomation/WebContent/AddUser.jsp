<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 
 <script type="text/javascript">
 
 
  function validateForm() {
      
    var x = document.forms["Loginform"]["name"].value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "Please enter user name";
        return false;
    }
    
    var x = document.forms["Loginform"]["password"].value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "Please enter password";
        return false;
    }
    
    var x = document.forms["Loginform"]["role"].value;
    if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select role";
        return false;
    }
} 
 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 
</script>
<title>AddUser</title>

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
<body background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg" link="black" alink="black" vlink="black">asdf
<div>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="1050" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table></div>
<div align="center" style="position:relative; top:100px;">
<ul>
  <li><a class="active" href="<%=request.getContextPath()%>/AdminController?action=updateUserDetails" name="updateUserDetails" id="updateUserDetails"><b>Accounts</b></a></li>
  <li><a  href="#" onclick="changeStyle()"><b>Servers</b></a>
    </li>
  
  <li><a href="<%=request.getContextPath()%>/ApplicationsDetailsController" name="getUserDetails" id="getUserDetails"><b>Applications</b></a></li>
  <li><a href="#"><b>About</b></a></li>
  
  <li style=" position:absolute;right:0; text-align:right "><a href="#"><b>Logout</b></a></li>
</ul>
</div>

<div align="center" style="position:relative; padding-top:100px">
<b><h1>Add Account</h1></b></div>
<div id="errorMsg" name="errorMsg" align="center" style="position:relative; color:red;  height:30px;">

</div>
<div align="center" style="position:relative;">
<form method="Post"  action="AdminController" name="Loginform" onsubmit="return validateForm()" >
<table border=0>
	<tbody>	
	<tr>
		<td><b>UserName:</b></td>
		<td><input type="text" name="name"></td>
	</tr>	
	<tr>
		<td><b>Password:</b></td>
		<td><input type="password" name="password"></td>
	</tr>	
	<tr>
				<td><b>Role:</b></td>
				<td><select name="role"
						id="role" onchange="" style="width:100%;" >										
						<option value="Admin">Admin</option>
						<option value="User">User</option>
					</select> 
                </td><tr>	
		
	</tbody></table><br><br>
	<input type="submit" name="action" value="Add" style="height:30px; width:80px;"  >
	<input type="reset" name="reset" value="Clear" style="height:30px; width:80px;">
	<input type="button" name="Cancel"  id="Cancel" style="height:30px; width:80px;"
					value="Cancel" onclick="openPage('AdminHome.jsp')">
	</form>
</center></div>

 			

</body>
</html>