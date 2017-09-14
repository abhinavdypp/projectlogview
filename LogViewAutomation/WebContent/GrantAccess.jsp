<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
body { 
    background-color: transparent;
}
body
{
font-family:Arial;
font-size: 12px;
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script type="text/javascript">
 
 
 function CloseMe()
 {
 parent.unload();
 }
 
 
 function formSubmit(){

 $.ajax({
 	type:'POST',
     url:"UserController?action=grantAccess",
     data: $("#userAccessform").serialize(),
     success: function (data) {
		//  $('#result').html(data);
							alert(' Data Saved succssfully');
							parent.unload();
						}
					});
					
		}
 
</script>
<title>Insert title here</title>


</head>
<body>
<center>


<h2>Grant Access to User</h2>

<!-- <div id="errorMsg" name="errorMsg" align="left" style="width: 729px; color:red; padding-top:100px;  padding-left: 390px;"> -->

<!-- </div> -->

		<div align="center" style="width: 729px; height: 247px; padding-top: 20px; ">
			<form method="POST" action=""  onsubmit="" name="userAccessform" id="userAccessform">
				<table width="50%" height="150px;" id="NewServerDetails"><tr>
				<td> User Name :
				</td>
				<td align="right">
				<select name='userNameList' id="userNameList" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="userName" items="${usernames}">
						<option value="${userName.key}">
							<c:out value="${userName.value}" />
						</option>
					</c:forEach>
				</select>
				
				</td>
				</tr>
				<tr>
				<td>
				Group Name : 
				</td>
				<td align="right">
				<select
					name="groupNameList" id="groupNameList" onchange="" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="groupname" items="${groupnames}">
						<option value="${groupname.key}">
							<c:out value="${groupname.value}" />
						</option>
					</c:forEach>
				</select>
				</td></tr>
				
				<tr>
				<td  align="center">
				<input type="button"  name="action" style="width:150px;"
					value="Grant Access" onclick="formSubmit();">
				</td>
				<td   align="center">
				<input type="button"  style="width:150px;"
					value="Cancel" onclick="CloseMe();">
				</td>
				</tr>
				</table>
				
				<br> <br> 
			</form>
		</div>
	</center>
</body>
</html>