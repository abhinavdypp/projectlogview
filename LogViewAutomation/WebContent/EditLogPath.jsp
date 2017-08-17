<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jq.js"></script>
 <script type="text/javascript">
 
 $(document).ready(function() {


        
        var serverid =  window.opener.document.getElementById('serverIdForLogpath').value;
      //  document.getElementById('idForLogpath').value = serverid;
        document.getElementById('serverId').value = serverid;
         var listval =  window.opener.document.getElementById('logpathList' + serverid).value;
        
        
         if (listval.substring(0, 1) == '[') { 
       
 			 listval = listval.substring(1);
			}
			
			if (listval.substring((listval.length-1),(listval.length)) == ']') { 
 			 
 			 listval = listval.substring(0,(listval.length-1));
			}
		
       
        var ids = new Array(); 
         ids =  listval.toString().split(",");
   for(var i = 0; i<ids.length; i++){
   	alert(ids[i]);
   }
        
         var counter = ids.length;
		alert('counter ' + counter);         
        var j= 0;
        document.getElementById('logpath1').value =  ids[j];
        for(var i = 2; i<= counter; i++){
         j++;
        if(j<ids.length){
        var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + i);

	newTextBoxDiv.after().html('<input type="text" name="logpath' + i +
	      '" id="logpath' + i + '" value="" style="width: 100%; margin:3px; margin-left:0px;">');
	    

	newTextBoxDiv.appendTo("#TextBoxesGroup");
	
	document.getElementById('logpath' + i).value = 	ids[j]; 
	
	
	
	}

   //document.getElementById('countForLogpath').value = counter;
	   
        }
        document.getElementById('countForNewLogpath').value = counter;
        
          $("#addLogPath").click(function () {
counter++;
	if(counter>10){
            alert("Only 10 textboxes allow");
            return false;
	}

	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);

	newTextBoxDiv.after().html('<input type="text" name="logpath' + counter +
	      '" id="logpath' + counter + '" value="" style="width: 100%; margin:3px; margin-left:0px;">');

	newTextBoxDiv.appendTo("#TextBoxesGroup");

   document.getElementById('countForNewLogpath').value = counter;
	
	 
     });
        
});


 function saveLogPath()
{
  // var names = $("select#appNameList" + count).val();
   var serverid = 2;
  // if(null!= names && 'none' != names){
           $.post('ServerDetailsController?action="save"', {
                serverId : serverid
        }, function(response) {
		 alert('hi');
       /* var select = $('#subAppNameList' + count);
         $('#subAppNameList' + count).prop("disabled", false);
       
        select.find('option').remove();
          $.each(response, function(index, value) {
          $('<option>').val(index).text(value).appendTo(select); 
      });*/
        }); 
      //  }
       /* else
        {
          alert("Please select the application name");
        } */
} 

 
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
 
 function closeMe()
 {
 //document.getElementById('serverform"').submit();
 window.close();
 }
 
function formSubmit(){

 $.ajax({
 	type:'POST',
     url:"ServerDetailsController?action=Save",
     data: $("#serverform").serialize(),
     success: function (data) {
		//  $('#result').html(data);
							alert(' Data Saved succssfully');

							var serverid = document.getElementById('serverId').value;
							if (null != serverid) {
							var inputcount = document.getElementById('countForNewLogpath').value;

								var ids = new Array();
								var j = 0;
								for ( var i = 1; i <= inputcount; i++) {
									ids[j] = document.getElementById('logpath'
											+ i).value;
									j++;
								}
								window.opener.document.getElementById('logpathList'+ serverid).value = ids.toString();
							}
							window.opener.location.href = window.opener.location.href;
							window.close();
						}
					});
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
		<div style="width: 729px;  padding-top:0px;"> Logpath details </div>
		<div align="left" style="width: 729px; height: 247px; padding-top: 20px; padding-left: 390px;">
			<form method="POST" action="ServerDetailsController"  onsubmit=""  name="serverform" id="serverform">
			
				<table width="50%" height="200px;" id="NewServerDetails">
				
				 

				
				
				<tr >
                	<td align="right" colspan="2">
				<div id='TextBoxesGroup'>
				 <div id="TextBoxDiv1">
					<input type='textbox' id='logpath1' name="logpath1" style="width: 100%; margin:3px; margin-left:0px;" >
				 </div>
			   </div>
<!-- 				 <input type="text" name="logpath" style="width: 100%"> -->
				 <a href="#" id="addLogPath">Add another Logpath</a>
				  
				</td>
                	 
                	</tr>
                	<tr>
				<td  align="left">
<!-- 				<input type="submit" name="action" style="width:150px;" -->
<!-- 					value="Save"> -->
					<input type="button" name="action" style="width:150px;"
					value="Save" onclick="formSubmit();">
				</td>
				<td   align="right">
				<input type="button"  style="width:150px;"
					value="Cancel" onclick="closeMe();">
				</td>
				</tr>
				
				</table>
			<input type="hidden" id="countForNewLogpath"  name = "countForNewLogpath"  value="1"/>
			<input type="hidden" id="serverId"  name = "serverId"  value=""/>
			</form>
		</div>
	</center>
</body>
</html>