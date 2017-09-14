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

<style>
body { 
    background-color: transparent;
font-family:Arial;
font-size: 12px;
}
 th {
    background-color:#117ac8;
    color: white;
</style>

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script src="Scripts/jquery-1.12.4.min.js"></script>
 <script type="text/javascript">

function EnableDisableTextBox(chkId,count) {

         var selrole = document.getElementById("role" + count);
        selrole.disabled = chkId.checked ? false : true;
         if (!selrole.disabled) {
            selrole.focus();
        }
        
        
        var selstatus = document.getElementById("status" + count);
        selstatus.disabled = chkId.checked ? false : true;
        
        
    }
    
  function confimResponse()
{
if (confirm('Are you sure you want delete record?')) {
getCheckedValues('delete');
}
else {
    document.getElementById('selectedids').value = "";
    return;
}
  }  
    
function getCheckedValues(action)
{
var table = document.getElementById("tbUserDetails");
checkbox = table.getElementsByTagName("input"); 

var chkedshid = new Array();
var totlchk = new Array();   
var selectedChkArrayIndex = 0;
for(var indexCheckbox = 0; indexCheckbox<checkbox.length; indexCheckbox++){
 if(checkbox[indexCheckbox].checked){
	  chkedshid[selectedChkArrayIndex] = checkbox[indexCheckbox].value;
       selectedChkArrayIndex++;
        }
        document.getElementById('selectedids').value=chkedshid.join();
       
    }  
    
      formSubmit(action);
}

 
function validateForm() {

 var selectedIds = document.getElementById('selectedids').value;
 if(null== selectedIds || selectedIds=='' )
 {
 	document.getElementById("errorMsg").innerHTML = "<b>Please select record to change or delete </b>";
  return false;
 }
 var ids = new Array();
   ids =  selectedIds.toString().split(",");
   for(var i = 0; i<ids.length; i++){
  
 
      var x = document.getElementById('role' + ids[i]).value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select role";
        return false;
    }
    
      var x = document.getElementById('status' + ids[i]).value;
     if (x == 'none') {
       document.getElementById("errorMsg").innerHTML = "Please select status";
        return false;
    }
    return true;
    }
    }

    function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
/*function formSubmit(){
 alert(' Action completed succssfully');
 
	return true;
	}*/
	

function formSubmit(action){
if(action == 'update')
{
var submitData = $('#frmUserDetails').serialize();
                var btnName = $('#update').attr('name');
                var btnVal = $('#update').val();
                var btn = '&'+btnName+'='+btnVal ;
                 submitData = submitData + btn;
                 alert(submitData);
}	
if(action =='delete')
{
  var submitData = $('#frmUserDetails').serialize();
                var btnName = $('#delete').attr('name');
                var btnVal = $('#delete').val();
                var btn = '&'+btnName+'='+btnVal ;
                 submitData = submitData + btn;
                 alert(submitData);
}
 
 $.ajax({
 	type:'POST',
     url:"AdminController?action=Update",
     	
     data: submitData,
     success: function (data) {
		//  $('#result').html(data);
							alert(' Data Saved succssfully');	
							parent.UpdateUser();						
						},
	 error: function(jqXHR,error, errorThrown) {
					
						alert ('Unexpected error occured! Please try again');
						//parent.UpdateUser();
					}
					});
		}


</script>
 
<title>Update User</title>
</head>
<body >
<center>
<b><h2>Accounts</h2></b>

<div id="errorMsg" name="errorMsg" align="left" style="width: 450px; color:red; padding-left:130px; padding-bottom:10px;"> </div>

<form method="Post"  action="AddUser.jsp">
<input type="submit" name="action" value="Add Account" style="height:30px;" >
 </form><br>

<form method="POST" action="" name="UserDetails" id="frmUserDetails" onsubmit="return validateForm()">
<table border="1" id="tbUserDetails">
            <thead>
                <tr>
                	<th></th>
                    <th style="width:140px;">UserName</th>
                    
                    <th style="width:140px;">Role</th>
                    <th style="width:140px;">Availability Status</th>
                    
                </tr>
            </thead>
            <tbody>
            	
                <c:forEach items="${userlist}" var="user" varStatus="loopCounter">
                <tr>
                	<td><input type="checkbox" name="chkListItem" id="chkListItem${loopCounter.count}" value="${user.id}" onchange="EnableDisableTextBox(this,${user.id});"></td>
                	
                	<td>${user.username}</td>
				  
				    <td>
                  <select
					name="role${user.id}" id="role${user.id}" value="${user.role}" style="width:100%" onchange="" disabled >
					<option value="none">Select</option>
					<c:choose>
					<c:when test="${user.role == 'User'}">
					<option selected value="User">User</option>
					</c:when>
					
					<c:otherwise>
					<option value="User">User</option>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${user.role == 'Admin'}">
						<option selected value="Admin">Admin</option>
					</c:when>	
					<c:otherwise>
					<option value="Admin">Admin</option>
					</c:otherwise>
					</c:choose>
					</select> 
                </td>
				    
				    <td>
                  <select
					name="status${user.id}" id="status${user.id}" value="${user.status}" style="width:100%" onchange="" disabled>
					<option value="none">Select</option>
					<c:choose>
					<c:when test="${user.status == 'Yes'}">
					<option selected value="Yes">Yes</option>
					</c:when>
					
					<c:otherwise>
					<option value="Yes">Yes</option>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${user.status == 'No'}">
						<option selected value="No">No</option>
					</c:when>	
					<c:otherwise>
					<option value="No">No</option>
					</c:otherwise>
					</c:choose>
					</select> 
                </td>
				
				</tr>
                </c:forEach>
            </tbody>
        </table>    
				
      <br> <input type="submit" name="delete"  id="delete" style="height:30px;"
					value="Delete Selected records" onclick="confimResponse()">
					
					<input type="button" name="update"  id="update" style="height:30px;"
					value="Update Selected records" onclick="getCheckedValues('update')">
					
				   <input type="hidden" id="selectedids"  name = "selectedids"  value=""/>
<!-- 				   <input type="submit" name="update" id="update" style="width:150px;" -->
<!-- 					value="Update Selected records" onclick="getCheckedValues()"> -->
        
        </form>
</center>
</div>
</body>
</html>