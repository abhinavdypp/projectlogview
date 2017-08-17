<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table></div>

<body  background="C:/ARCHANA/LogView/Demo/LogViewAutomation/WebContent/bg.jpg" link="black" alink="black" vlink="black">
<center>
	<h2 style="margin-top:90px;"> Search Criteria</h2>
	<form method="POST" action="ServerDetailsController?action=modifyRcords" id="frmServerDetails" onsubmit="return validateForm()">
	<div id="errorMsg" name="errorMsg" align="left" style="width: 450px; color:red; padding-top:4px; padding-left:130px; padding-bottom:10px;"> </div>
	
<table style="border-spacing:20px; border-collapse: collapse;"><tr>
<td>PNR Locator</td>
<td><input type="text" name="pnrLocator" style="width: 100%">
</td>
</tr>
<tr>
<td>
Start TimeStamp(hh:mm:ss)
</td>	
<td>
<input type="text" name="startTimeStamp" style="width: 100%">
</td>
</tr>
<tr>
<td>
End TimeStamp (hh:mm:ss)
</td>
<td>
<input type="text" name="endTimeStamp" style="width: 100%">
</td>
</tr>
</table>
<br>
	<input type="submit" name="search"  id="search" style="height:30px;"
					value="Search">
					
					<input type="button" name="Cancel"  id="Cancel" style="height:30px;"
					value="Cancel" onclick="openPage('AdminHome.jsp')">
</form>
</center>
</body>
</html>