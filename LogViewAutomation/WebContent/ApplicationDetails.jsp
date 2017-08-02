<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>      
    
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script type="text/javascript">
 
 $(document).ready(function() {

$('#appNameList').change(function(event) {
        var names = $("select#userNameList").val();
        $.get('ServerDetailsController', {
                appName : names
        }, function(response) {		
        });
        });
});
 
 
 function getSubApp()
 {
 alert('Hi');
      $.ajax({
      type:"GET",
      url: "<%=request.getContextPath()%>/ApplicationsDetailsController",
      data: { country: $("#userNameList").val()}
      }).done(function(msg){
       $("#right #myDiv").html(msg);
      });
 }
</script>
<title>Insert title here</title>
</head>
<title>Login</title>

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

<!-- 

<div align="center" style="position:relative;top:10px;";>
<ul>
  <li><a class="active" href="#"><b>Accounts</b></a></li>
  <li><a href="#"><b>Servers</b></a></li>
  <li><a href="<%=request.getContextPath()%>/ApplicationsDetailsController" name="getUserDetails" id="getUserDetails"><b>Applications</b></a></li>
  <li><a href="#"><b>About</b></a></li>
  
  <li style=" position:absolute;right:0; text-align:right "><a href="#"><b>Logout</b></a></li>
</ul>
</div>

-->
 
<br><br><br><br>

<body>
<center>
					<h2>Add Application Details</h2>
 	        
<form method="POST" name="addApp" action="ApplicationsDetailsController" onsubmit="return validateForm()">	


	        <table border="1" cellpadding="5">
            <tr>
                <td>Enter Application Name:</td>
                <td><input type="text" name="appName"></td>
            </tr>
            <tr>
                <td>Enter Sub Application Name:</td>
                <td><input type="text" name="subAppName"></td>

            </tr>
            <tr>
                <td>User Name :</td>
                <td><select name='userNameList' id="userNameList">
					<option value="none">Select</option>
					<c:forEach var="userName" items="${usernames}">
						<option value="${userName.key}">
							<c:out value="${userName.value}" />
						</option>
					</c:forEach>
				</select></td>
            </tr>
            <tr>
                <td colspan="3">
                <input type="submit" name="submit" value="Submit">
                <input type="button" name="cancel" onclick="cancelForm()" value="Cancel">
                </td>
            </tr>
        </table>			   					
</form>      
</center>

<script type="text/javascript">
 
function validateForm() {
 	var x = document.getElementById("appName").value;
 	var y = document.getElementById("subAppName").value; 	
 	var z = document.getElementById("userNameList").value;

    if (x == "") {
        alert("appName must be filled out!");
        return false;
    }
    
    if (y == "") {
        alert("subAppName must be filled out!");
        return false;
    }    
        
    if (z == "none") {
        alert("userName must be selected!");
        return false;
    }    
} 

function cancelForm() {
    var callAdmin = "AdminHome.jsp";
    window.location.href = callAdmin;
} 

</script>

	
	
	<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right ";><b><%=timeStamp %></b></div>
	
</body>
</html>