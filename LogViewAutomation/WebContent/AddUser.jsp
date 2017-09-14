<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     
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
   return true;
} 
 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 
 
 function formSubmit(){

 $.ajax({
 	type:'POST',
     url:"AdminController?action=Add",
     data: $("#Loginform").serialize(),
     success: function (data) {
		//  $('#result').html(data);
							alert(' Data Saved succssfully');
							parent.unload();
						},
	error: function(jqXHR,error, errorThrown) {
					
						alert ('Unexpected error occured! Please try again');
						//parent.UpdateUser();
					}					
					});
					
		}
 
 
</script>
<title>AddUser</title>

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

</head>
<body >
<center>
<b><h2>Add Account</h2></b></div>
<div id="errorMsg" name="errorMsg" align="center" style="position:relative; color:red;  height:30px;">

</div>
<div align="center" style="position:relative;">
<form method="Post"  action="" name="Loginform" id="Loginform"  onsubmit="return validateForm();" >
<table border=0 cellpadding="5">
	<tbody>	
	<tr>
		<td>UserName:</td>
		<td><input type="text" name="name"></td>
	</tr>	
	<tr>
		<td>Password:</td>
		<td><input type="password" name="password"></td>
	</tr>	
	<tr>
				<td>Role:</td>
				<td><select name="role"
						id="role" onchange="" style="width:100%;" >										
						<option value="Admin">Admin</option>
						<option value="User">User</option>
					</select> 
                </td><tr>	
		
	</tbody></table><br><br>
	<input type="button" name="action" value="Add"  style="width:150px;" onclick="formSubmit();"  >
	
	
	</form>
</center></div>

 			

</body>
</html>