<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>      
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home</title>

<style>
body
{
font-family:Arial;
}
.container {
    overflow: hidden;
    background-color: #117ac8;
    font-family: Arial;
}

.container a {
    float: left;
    font-size: 14px;
    color: white;
    text-align: center;
    padding: 10px 16px;
    text-decoration: none;
    height:20px;
}

.dropdown {
    float: left;
    overflow: hidden;
}

.dropdown .dropbtn {
    font-size: 14px; 
    font-weight: bold;   
    border: none;
    outline: none;
    color: white;
    padding: 10px 16px;
    background-color: inherit; 
}

.container a:hover, .dropdown:hover .dropbtn {
    background-color: #085a97;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    float: none;
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    text-align: left;
}

.dropdown-content a:hover {
    background-color: #ddd;
}

.dropdown:hover .dropdown-content {
    display: block;
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script src="Scripts/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function UpdateUser()
{
          $.get("<%=request.getContextPath()%>/AdminController?action=updateUserDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "UpdateUser.jsp";
      });
}    
function GrantAccess()
{
          $.get("<%=request.getContextPath()%>/UserController?action=grantAccess", {
                }, function(response) {
				  document.getElementById("frameload").src = "GrantAccess.jsp";
      });
} 
function AddServerDetails()
{
          $.get("<%=request.getContextPath()%>/ServerDetailsController?action=addNewServerdetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "AddServerDetails.jsp";
      });
}       
function EditServerDetails()
{
          $.get("<%=request.getContextPath()%>/ServerDetailsController?action=updateServerDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "EditServerDetails.jsp";
      });
}       
   
function AddApplications()
{
          $.get("<%=request.getContextPath()%>/ApplicationsDetailsController?action=addAppDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "ApplicationDetails.jsp";
      });
}
function AddSubApplications()
{
          $.get("<%=request.getContextPath()%>/ApplicationsDetailsController?action=addSubAppDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "SubApplicationDetails.jsp";
      });
}      
function DeleteApplications()
{
          $.get("<%=request.getContextPath()%>/ApplicationsDetailsController?action=deleteAppDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "ApplicationSummary.jsp";
      });
}     
function DeleteSubApplications()
{
          $.get("<%=request.getContextPath()%>/ApplicationsDetailsController?action=deleteSubAppDetails", {
                }, function(response) {
				  document.getElementById("frameload").src = "SubApplicationSummary.jsp";
      });
}  

function unload()
{
document.getElementById("frameload").src = "";
}    
 
 </script>

</head>
<body background="http://labcenter.com.br/wp-content/uploads/2016/08/gradiente-azul-claro-fundo-1024x768.jpg" >
<!-- ***** SECURITY CHECK -->
<%
 if (session.getAttribute("username")==null) 
{ 
 response.sendRedirect("UserLoginView.jsp"); 
} 
 %> 
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
		<td align="center" width="10500" height="70" style="font-size:30px; color:#0c69c2; text-shadow: 2px 2px 4px #8ec9db;"><b>LogView Application</b></td>
		<td width="150" height="70"><img src="http://csadmissions.gcu.edu.pk/DCS/img/default1.png" width="50" height="50"></td>
		<td><h3><%=userName %></h3></td>
	</tr>	
	
</tbody></table></div>

<div align="center" style="position:relative;top:70px;">
<div class="container">
<!--   <a href="#" onclick="UpdateUser()"><b>Accounts</b></a> -->
  <div class="dropdown">
    <button class="dropbtn">Accounts</button>
    <div class="dropdown-content">
      <a href="#" onclick="UpdateUser()">Update Accounts</a>
      <a href="#" onclick="GrantAccess()">Grant Access</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">Servers</button>
    <div class="dropdown-content">
      <a href="#" onclick="AddServerDetails()">Add New Server Details</a>
      <a href="#" onclick="EditServerDetails()">Edit Server Details</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">Applications</button>
    <div class="dropdown-content">
      <a href="#" onclick="AddApplications()">Add Applications</a>
      <a href="#" onclick="AddSubApplications()">Add Sub Applications</a>      
      <a href="#" onclick="DeleteApplications()">Delete Applications</a>
      <a href="#" onclick="DeleteSubApplications()">Delete Sub Applications</a>
    </div>
  </div> 
<%-- 	<a class="active" href="<%=request.getContextPath()%>/LogSearchCriteriaController" name="logViewer" id="logViewer"><b>LogViewer</b></a> --%>
  	  <a href="#"><b>About</b></a>
    
</div>

<div align="center" style="position:relative; height:500px">
<iframe id="frameload" style="width:100%; height:100%; border:0px" id="divAppDetails" >

</iframe>
</div>

<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right "><b><%=timeStamp %></b></div>


</body>
</html>