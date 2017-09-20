<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page import="java.util.Date" %> 
 <%@ page import="java.text.SimpleDateFormat" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>LogPath Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
table, td,th
{
boarder: 1px solid blue;
font-family:sans-serif;
}
th
{
background-color:#117ac8;
color:white;
}
body
{

font-family:Arial;

text-align: center;
}

</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script type="text/javascript">
 
 $(document).ready(function() {

$('#appNameList').click(function(event) {
        var names = $("select#appNameList").val();
        $.get('UserController?action=getSubApps', {
                appName : names
        }, function(response) {
		
        var select = $('#subAppNameList');
        select.find('option').remove();
          $.each(response, function(index, value) {
          $('<option>').val(index).text(value).appendTo(select);
      });
        });
        // alert($('#subAppNameList').val());   
     if ($('#subAppNameList').val() != null) {
    
        $.get('UserController?action=getappEnvironment', {
                appName : names
        }, function(response) {
		//alert("HI");
        var select = $('#environmentList');
        select.find('option').remove();
     	//alert(response);   
          $.each(response, function(index, value) {
          $('<option>').val(value).text(value).appendTo(select);
      });
        }); 
    }        
         });
      
         
   $('#subAppNameList').click(function(event) {
        var snames = $("select#subAppNameList").val();
        $.get('UserController?action=getsubappEnvironment', {
                subAppname : snames
                
        }, function(response) {
		
        var select = $('#environmentList');
        select.find('option').remove();
          $.each(response, function(index, value) {
          $('<option>').val(value).text(value).appendTo(select);
      });
        });
        });     
        
     $('#environmentList').click(function(event) {
        var envName = $("select#environmentList").val();
        var appname = $("select#appNameList").val();
        var snames =  $("select#subAppNameList").val();
        $.get('UserController?action=getServerNames', {
                subAppname : snames,
                appName : appname,
                Environment : envName
                
        }, function(response) {
		
        var select = $('#serverNameList');
        select.find('option').remove();
	
          $.each(response, function(index, value) {
            
            
          $('<option>').val(index).text(value).appendTo(select);
      });
        });
        });  

 	$('#serverNameList').click(function(event) {
        var snames = $("select#serverNameList").val();
        $.get('UserController?action=getLogPaths', {
                serverId : snames
               
        }, function(response) {
		
        var select = $('#logPathList');
        select.find('option').remove();
	
          $.each(response, function(index, value) {
          $('<option>').val(value).text(value).appendTo(select);
      });
        });
        });
  
 	});
 
 function validateForm() {
  var x = document.forms["userSettingform"]["appNameList"].value;
    if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select application name";
        return false;
    }
    
     var x = document.forms["userSettingform"]["subAppNameList"].value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select Sub application name";
        return false;
    }
    
     var x = document.forms["userSettingform"]["enviormentList"].value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select enviorment name";
        return false;
    }   
} 

 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 
</script>
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
</tbody></table></div>

<div id="errorMsg" name="errorMsg" align="left" style="width: 729px; color:red; padding-top:100px;  padding-left: 390px;">

</div>

		<div>
			<form method="POST" action="UserController" id="userSettingform" onsubmit="return validateForm()" name="userSettingform">
				<table  border=1  style="width: 70%";><tr>
			    <th style="padding-right: 20px;">Application Name :</th>
			    <th style="padding-right: 20px;">Sub Application Name :</th> 
			    <th style="padding-rightt: 20px;">Environment :</th>
			  </tr>
			  <tr>
				
				<td >
				<select name='appNameList' id="appNameList" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="appName" items="${appnames}">
						<option value="${appName.key}">
							<c:out value="${appName.value}" />
						</option>
					</c:forEach>
				</select>
				</td>
				
				<td>
				<select
					name="subAppNameList" id="subAppNameList" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="subAppname" items="${subAppnames}">
						<option value="${subAppname.key}">
							<c:out value="${subAppname.value}" />
						</option>
					</c:forEach>
				</select>
				</td>
				
				
				<td >
				<select name="environmentList"
						id="environmentList" style="width: 100%">
						<option value="none">Select</option>
						<c:forEach var="Environment" items="${environments}">
						<option value="${Environment.value}"></option>
					</c:forEach>

					</select>
				</td>
				</tr>
				<tr>
				
				<td >
				<select name="serverNameList"
						id="serverNameList" style="width: 100%">
						<option value="none">Select</option>
					</select>
				</td>
				
					<td >
				<select name="logPathList"
						id="logPathList" style="width: 100%">
						<option value="none">Select</option>

					</select>
				</td>
				
				<td  align="center">
				<button type="submit"  name="action" style="width: 100%;"
					value="Search" id="Search">Search</button>
				</td>
				</tr></table></form></div></center>
	<% Date today = new Date(); %>
<%SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String timeStamp= Date_format.format(today);
 			%>
 			
<div style="font-size:20px; color:Black; position:absolute; bottom: 10px; right:0; text-align:right "><b><%=timeStamp %></b></div>
	
	</body>
	</html>