<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

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
    background-color: black;
    color: white;
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
        
         var txtIpAddress = document.getElementById("ipaddress" + count);
        txtIpAddress.disabled = chkId.checked ? false : true;
        
         var selEnvList = document.getElementById("envList" + count);
        selEnvList.disabled = chkId.checked ? false : true;
        
        var txtLogPath = document.getElementById("logpath" + count);
        txtLogPath.disabled = chkId.checked ? false : true;
        
         var selAppNameList = document.getElementById("appNameList" + count);
        selAppNameList.disabled = chkId.checked ? false : true;
        
          var selSubAppNameList = document.getElementById("subAppNameList" + count);
        selSubAppNameList.disabled = chkId.checked ? false : true;
    }

function confimResponse()
{
if (confirm('Are you sure you want delete record?')) {
getCheckedValues();
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
 var ids = new Array();
   ids =  selectedIds.toString().split(",");
   for(var i = 0; i<ids.length; i++){
 
    var x = document.getElementById('servername' + ids[i]).value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "<b>Please enter server name</b>";
       
        return false;
    }
    
    var x = document.getElementById('ipaddress' + ids[i]).value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "<b>Please enter ipaddress of server</b>";
       
        return false;
    }
    
    var x = document.getElementById('logpath' + ids[i]).value;
     if (x.trim().length == 0 || x=='' || x==null) {
        document.getElementById("errorMsg").innerHTML = "<b>Please enter lopath of application</b>";
       
        return false;
    }
    
      var x = document.getElementById('envList' + ids[i]).value;
     if (x == 'none') {
        document.getElementById("errorMsg").innerHTML = "<b>Please select enviornment</b>";
       
        return false;
    }
    
      var x = document.getElementById('appNameList' + ids[i]).value;
     if (x == 'none') {
        document.getElementById("errorMsg").innerHTML = "<b>Please select application name</b>";
       
        return false;
    }
    
      var x = document.getElementById('subAppNameList' + ids[i]).value;
     if (x == 'none') {
        document.getElementById("errorMsg").innerHTML = "<b>Please select Sub application name</b>";
       
        return false;
    }
     
   
    }
    }
    
    function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
</script>

<title>Insert title here</title>
</head>


<div>
<table border=0 style="position:absolute; top:0px;">
<tbody>
	<tr>
		<td width="150" height="80"><img src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi" width="120" height="80"></td>
		<td align="center" width="10500" height="80" style="font-size:36px; color:#d50e21";><b>LogView Application</b></td>
		<td width="150" height="100"><img src="https://fontmeme.com/images/IBM-Logo.jpg" width="120" height="100"></td></b>
	</tr>	
</tbody></table></div>

<body  background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg" link="black" alink="black" vlink="black">
<center>

					<h2 style="margin-top:90px;"> Server Details</h2>
	<form method="POST" action="ServerDetailsController?action=modifyRcords" id="frmServerDetails" onsubmit="return validateForm()">
<div style="width: 729px;  padding-top:0px;">  Select the row which you want to change or delete </div>
<div id="errorMsg" name="errorMsg" align="left" style="width: 450px; color:red; padding-top:4px; padding-left:130px; padding-bottom:10px;"> </div>

<table id="tbServerDetails" style="border-spacing:0px;  border-collapse: collapse; border: 2px solid black;" >

            <thead>
                <tr>
                	<th></th>
                   <th>Server Name</th>
                    <th>Ip address</th>
                    <th>Enviornment</th>
                    <th>LogPath</th>
                    <th>Application Name</th>
                    <th>Sub Application Name</th>
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach var="server" items="${serverlist}" varStatus="loopCounter">
                <tr>
                	<td><input type="checkbox" name="chkListItem" id="chkListItem${loopCounter.count}" value="${server.id}" onchange="EnableDisableTextBox(this,${server.id});"></td>
                	<td><input type="text" id="servername${server.id}" name="servername${server.id}" value="${server.servername}" disabled ></td>

                    <td><input type="text" name="ipaddress${server.id}" id="ipaddress${server.id}" value="${server.ipaddress}" disabled></td>

				<td>
                  <select
					name="env${server.id}" id="envList${server.id}" value="${server.enviornment}" style="width:100%" onchange="" disabled>
					<option value="none">Select</option>
					<c:choose>
					<c:when test="${server.enviornment == 'DEV'}">
					<option selected value="DEV">DEV</option>
					</c:when>
					
					<c:otherwise>
					<option value="DEV">DEV</option>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${server.enviornment == 'INT'}">
						<option selected value="INT">INT</option>
					</c:when>	
					<c:otherwise>
					<option value="INT">INT</option>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${server.enviornment == 'CERT'}">
						<option selected value="CERT">CERT</option>
					</c:when>	
					<c:otherwise>
					<option value="CERT">CERT</option>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${server.enviornment == 'PROD'}">
						<option selected value="PROD">PROD</option>
					</c:when>	
					<c:otherwise>
					<option value="PROD">PROD</option>
					</c:otherwise>
					</c:choose>
					
				</select> 
                </td>

 				<td><input type="text" name="logpath${server.id}" id="logpath${server.id}" value="${server.logpath}" disabled></td>

				<td>
				 	<select name='appNameList${server.id}' id="appNameList${server.id}" onchange="changeVal(${server.id});" style="width:100%" disabled>
					<option value="none">Select</option>
					<c:forEach var="appName" items="${appnames}">
						<c:choose>
						<c:when test="${appName.key == server.application_id}">
						<option value="${appName.key}" selected>
							<c:out value="${appName.value}" />
						</option>
						</c:when>
						<c:otherwise>
						<option value="${appName.key}">
							<c:out value="${appName.value}" />
						</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>  
                </td>
                <td>
                   <select 
					name="subAppNameList${server.id}" id="subAppNameList${server.id}" style="width:100%" disabled>
					
					<c:forEach var="subAppname" items="${server.subapplist}">
					<c:choose>
						<c:when test="${subAppname.key == server.sub_app_id}">
						<option value="${subAppname.key}" selected>
							<c:out value="${subAppname.value}" />
						</option>
						</c:when>
						<c:otherwise>
						<option value="${subAppname.key}">
							<c:out value="${subAppname.value}" />
						</option>
						</c:otherwise>
						</c:choose>
						</c:forEach>
				</select> 
                </td>
               
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
         <br> <br> <input type="submit" name="delete"  id="delete" style="height:30px;"
					value="Delete Selected records" onclick="confimResponse()">
					
					<input type="submit" name="update"  id="update" style="height:30px;"
					value="Update Selected records" onclick="getCheckedValues()">
					
					<input type="button" name="Cancel"  id="Cancel" style="height:30px;"
					value="Cancel" onclick="openPage('AdminHome.jsp')">
					
				   <input type="hidden" id="selectedids"  name = "selectedids"  value=""/>
        
        </form>
      </center> 
</body>
</html>