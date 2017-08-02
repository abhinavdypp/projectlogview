<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
        var names = $("select#appNameList").val();
        $.get('ServerDetailsController?action=getSubApps', {
                appName : names
        }, function(response) {
		
        var select = $('#subAppNameList');
        select.find('option').remove();
          $.each(response, function(index, value) {
          $('<option>').val(index).text(value).appendTo(select);
      });
        });
        });
});
 
 function validateForm() {
  var x = document.forms["serverform"]["appNameList"].value;
    if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select application name";
        return false;
    }
    
     var x = document.forms["serverform"]["subAppNameList"].value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select Sub application name";
        return false;
    }
    
     var x = document.forms["serverform"]["enviormentList"].value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select enviorment name";
        return false;
    }
    
    var x = document.forms["serverform"]["servername"].value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "Please enter server name";
        return false;
    }
    
    var x = document.forms["serverform"]["ipaddress"].value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "Please enter ipaddress of server";
        return false;
    }
    
    var x = document.forms["serverform"]["logpath"].value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "Please enter logpath";
        return false;
    }
} 

 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 
</script>
<title>Insert title here</title>


</head>
<body  background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg" link="black" alink="black" vlink="black">
<center>


<div>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table></div>

<div id="errorMsg" name="errorMsg" align="left" style="width: 729px; color:red; padding-top:100px;  padding-left: 390px;">

</div>

		<div align="left" style="width: 729px; height: 247px; padding-top: 20px; padding-left: 390px;">
			<form method="POST" action="ServerDetailsController"  onsubmit="return validateForm()" name="serverform">
				<table width="50%" height="200px;"><tr>
				<td> Application Name :
				</td>
				<td align="right">
				<select name='appNameList' id="appNameList" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="appName" items="${appnames}">
						<option value="${appName.key}">
							<c:out value="${appName.value}" />
						</option>
					</c:forEach>
				</select>
				
				</td>
				</tr>
				<tr>
				<td>
				Sub Application Name : 
				</td>
				<td align="right">
				<select
					name="subAppNameList" id="subAppNameList" onchange="" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="subAppname" items="${subAppnames}">
						<option value="${subAppname.key}">
							<c:out value="${subAppname.value}" />
						</option>
					</c:forEach>
				</select>
				</td></tr>
				<tr>
				<td>Environment :
				</td>
				<td align="right">
				<select name="enviormentList"
						id="enviormentList" onchange="" style="width: 100%">
						<option value="none">Select</option>
						<option value="DEV">DEV</option>
						<option value="INT">INT</option>
						<option value="CERT">CERT</option>
						<option value="PROD">PROD</option>
					</select>
				</td>
				</tr>
				<tr>
				<td>
				Enter Server Name:
				</td>
				<td align="right">
				<input type="text" name="servername" style="width: 100%">
				</td>
				<tr>
				<td>
				Enter ip address:
				</td>
				<td align="right">
				<input type="text" name="ipaddress" style="width: 100%">
				</td>
				</tr>
				<tr>
				<td>
				Enter LogPath:
				</td>
				<td align="right">
				 <input type="text" name="logpath" style="width: 100%">
				</td>
				</tr>	
				<tr><td></td></tr>
				<tr>
				<td  align="center">
				<input type="submit" name="action" style="width:150px;"
					value="Add">
				</td>
				<td   align="center">
				<input type="button"  style="width:150px;"
					value="Cancel" onclick="openPage('AdminHome.jsp')">
				</td>
				</tr>
				</table>
				<br> <br> 
			</form>
		</div>
	</center>
</body>
</html>