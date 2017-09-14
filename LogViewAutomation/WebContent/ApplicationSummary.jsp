<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

body { 
    background-color: transparent;
}


table#tbServerDetails tr:nth-child(even) {
    background-color: #eee;
}
table#tbServerDetails tr:nth-child(odd) {
   background-color:#fff;
}
/*table#tbServerDetails, th, td {
     background-color: #eee;
}*/

table#tbServerDetails th {
    background-color: #117ac8;
    color: white;
}
body
{
font-family:Arial;
font-size: 12px;
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
 <script src="Scripts/jquery-1.12.4.min.js"></script>
 <script type="text/javascript">
 
 function changeVal(count)
{
   var names = $("select#appNameList" + count).val();
   
   if(null!= names && 'none' != names){
           $.get('ServerDetailsController?action=getSubApps', {
                appName : names
        }, function(response) {
		
        var select = $('#subAppNameList' + count);
         $('#subAppNameList' + count).prop("disabled", false);
       
        select.find('option').remove();
          $.each(response, function(index, value) {
          $('<option>').val(index).text(value).appendTo(select);
      });
        });
        }
        else
        {
          alert("Please select the application name");
        }
} 

function EnableDisableTextBox(chkId,count) {

        var txtServerName = document.getElementById("servername" + count);
        txtServerName.disabled = chkId.checked ? false : true;
        if (!txtServerName.disabled) {
            txtServerName.focus();
        }
        
         var selAppNameList = document.getElementById("appNameList" + count);
        selAppNameList.disabled = chkId.checked ? false : true;
        
    }

function confimResponse()
{
if (confirm('Are you sure you want delete record?')) {
getCheckedValues();
formSubmit();
}
else {
    document.getElementById('selectedids').value = "";
    return;
}

}

function getCheckedValues()
{
//if (confirm('Are you sure you want delete record?')) {
    
    var table = document.getElementById("tbServerDetails");
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
//}

 
/* if(validateForm(id))
 {
 document.getElementById('frmServerDetails').submit();
 }*/
 
}

function validateForm() {
 var selectedIds = document.getElementById('selectedids').value;
 if(null== selectedIds || selectedIds=='' )
 {
 	document.getElementById("errorMsg").innerHTML = "<b>Please select record to change or delete </b>";
  return false;
 }
 return true;
//  var ids = new Array();
//    ids =  selectedIds.toString().split(",");
 
//    for(var i = 0; i<ids.length; i++){
             
//       var x = document.getElementById('appNameList' + ids[i]).value;
//      if (x == 'none') {
//         document.getElementById("errorMsg").innerHTML = "<b>Please select application name</b>";
       
//         return false;
    }
      
   
  
    
    function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 /*function formSubmit(){
 alert(' Action completed succssfully');
	return true;
	} */

function formSubmit(){

 $.ajax({
 	type:'POST',
     url:"ApplicationsDetailsController?action=deleteApp",
     data: $("#frmAppDetails").serialize(),
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

<title>Delete Application</title>
</head>



<body>

<center>

					<h2 > Application Details</h2>
	<form method="POST" action="" id="frmAppDetails" onsubmit="return validateForm();">

<!-- <div style="width: 729px;  padding-top:0px;">  Select the row which you want to delete </div> -->

<div id="errorMsg" name="errorMsg" align="left" style="width: 450px; color:red; padding-top:4px; padding-left:130px; padding-bottom:10px;"> </div>

<table id="tbServerDetails" style="border-spacing:0px;  border-collapse: collapse; border: 2px solid #117ac8;" >

            <thead>
                <tr>
                	<th></th>
                    <th>Application Name</th>                    
                </tr>
            </thead>
            <tbody>
              <c:forEach var="apps" items="${appslist}" varStatus="loopCounter">
                <tr>
                     
                 	<td><input type="checkbox" name="chkListItem" id="chkListItem${loopCounter.count}" value="${apps.applicationid}" onchange="EnableDisableTextBox(this,${apps.applicationid});"></td>
                	<td><input type="text" id="appname${apps.applicationid}" name="appname${apps.applicationid}" value="${apps.applicationname}" disabled ></td>
 		               	               
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
         <br> <br> <input type="button" name="delete"  id="delete" style="height:30px;"
					value="Delete Selected records" onclick="confimResponse()">
					
					
				   <input type="hidden" id="selectedids"  name = "selectedids"  value=""/>
        
        </form>
      </center> 
</body>
</html>