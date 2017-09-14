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
 

 
 

/* function formSubmit(){
 alert(' Action completed succssfully');
	return true;
	} */


 function formSubmit(){

 $.ajax({
 	type:'POST',
     url:"ApplicationsDetailsController?action=Add",
     data: $("#addApp").serialize(),
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
<title>Add application</title>
</head>

<style>
body { 
    background-color: transparent;
font-family:Arial;
font-size: 12px;
}
</style>

</head>
<body ></body>

<div align="center" style="position:relative;top:100px;";>

</div>

<body>
<center>
					<h2>Add Application Details</h2>
 	        <br>
<form method="POST" name="addApp" id="addApp" action="" onsubmit="return validateForm();">	


	        <table border="0" cellpadding="5">
            <tr>
                <td>Enter Application Name:</td>
                <td><input type="text" name="appName"></td>
            </tr>
            <tr>
                <td>Enter Sub Application Name:</td>
                <td><input type="text" name="subAppName"></td>

            </tr>
            <tr>
                <td>Group Name :</td>
                <td><select name='grpNameList' id="grpNameList" style="width: 100%">
					<option value="none">Select</option>
					<c:forEach var="groupName" items="${groupnames}">
						<option value="${groupName.key}">
							<c:out value="${groupName.value}" />
						</option>
					</c:forEach>
				</select></td>
            </tr>
            <tr>
                    </table><br>
                     <input type="button" name="submit" style="width:150px;" value="Add" onclick="formSubmit();">			   					
</form>      
</center>

<script type="text/javascript">
 
function validateForm() {
 	var x = document.getElementById("appName").value;
 	var y = document.getElementById("subAppName").value; 	
 	var z = document.getElementById("grpNameList").value;

    if (x == "") {
        alert("appName must be filled out!");
        return false;
    }
    
    if (y == "") {
        alert("subAppName must be filled out!");
        return false;
    }    
        
    if (z == "none") {
        alert("groupName must be selected!");
        return false;
    }    
    return true;
} 



</script>
	
</body>
</html>