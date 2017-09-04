<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<title>Insert title here</title>
<script type="text/javascript">

$(document).ready(function () {
function cancelForm() {
    var callAdmin = "AdminHome.jsp";
    window.location.href = callAdmin;
} 
        function saveTextAsFile() {
            // grab the content of the form field and place it into a variable
            var textToWrite = document.getElementById("logContent").value;
            //  create a new Blob (html5 magic) that conatins the data from your form feild
            var textFileAsBlob = new Blob([textToWrite], { type: 'text/plain' });
            // Specify the name of the file to be saved
            var fileNameToSaveAs = "myNewFile.txt";

            // Optionally allow the user to choose a file name by providing 
            // an imput field in the HTML and using the collected data here
            // var fileNameToSaveAs = txtFileName.text;

            // create a link for our script to 'click'
            var downloadLink = document.createElement("a");
            //  supply the name of the file (from the var above).
            // you could create the name here but using a var
            // allows more flexability later.
            downloadLink.download = fileNameToSaveAs;
            // provide text for the link. This will be hidden so you
            // can actually use anything you want.
            downloadLink.innerHTML = "My Hidden Link";

            // allow our code to work in webkit & Gecko based browsers
            // without the need for a if / else block.
            window.URL = window.URL || window.webkitURL;

            // Create the link Object.
            downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
            // when link is clicked call a function to remove it from
            // the DOM in case user wants to save a second file.
            downloadLink.onclick = destroyClickedElement;
            // make sure the link is hidden.
            downloadLink.style.display = "none";
            // add the link to the DOM
            document.body.appendChild(downloadLink);

            // click the new link
            downloadLink.click();
        }

        function destroyClickedElement(event) {
            // remove the link from the DOM
            document.body.removeChild(event.target);
        }



        $("#download").click(function (e) {

            e.preventDefault();
            saveTextAsFile();
        });
 });
</script>
</head>

<style>
</style>

<body
	background="http://piranahimpressions.com/img/seq-slider/bg-clouds.jpg"
	link="black" alink="black" vlink="black">

<div style="top: 0">
<table border=0>
	<tbody>
		<tr>
			<td width="150" height="80"><img
				src="http://www.bitpad.com/.a/6a00d834202e5653ef0133f0e26640970b-pi"
				width="120" height="80"></td>
			<td align="center" width="10500" height="80"
				style="font-size: 36px; color: #d50e21";><b>LogView 
			Application</b></td>
			<td width="150" height="80"><img
				src="https://fontmeme.com/images/IBM-Logo.jpg" width="120"
				height="100"></td>
			</b>
		</tr>
	</tbody>
</table>
</div>



<center>
<h2>Search Application Logs</h2>

<form method="POST" name="addApp" action="LogSearchCriteriaController">


<table border="1" cellpadding="5">
		<tr>
		<td>Select Time:</td>
		<td><input title="dd/mm/yyyy" type="text" name="fromDate" class="tooltip"
			id="fromDate" style=""> <span class="tooltiptext">Date Format:
		yyyy-mm-dd</span>
	</td>
	</tr>
	<tr>
		<td>Enter Search Text:</td>
		<td><input id="searchText" type="text" name="searchText">	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Time
		in hh:mm: <input type="text" name="timeSearch"></td>

	</tr>
	<tr>
		<td colspan="3"><input type="submit" name="submit" value="Submit"><input type="button" id="download" name="download" value="Download">
		<input type="button" name="cancel" onclick="cancelForm()"
			value="Cancel"></td>

	</tr>
	<tr>
		<td height="346"></td>
		<td height="346"><textarea id="logContent" rows="40" cols="200">${logInfo}</textarea></td>
	</tr>
</table>
</form>
</center>

</body>
</html>