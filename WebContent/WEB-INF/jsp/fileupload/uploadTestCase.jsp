<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import TestCase</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"> 

window.onload=function(){
	window.opener.parent.form1.submit();
}

var i = 1;
function creNode(){
	i++;
    if(i>9)
	{
		return;
	}
	var tab = document.getElementById("form_table");
	//var tr = "<tr><td>file_"+i+":</td><td><input type='file' name='upload' id='fileArea"+i+"'/></td></tr>";

	var tbody =  createEl("tbody");
	var tr = createEl("tr");

	var td_label = createEl("td");

	var ll = createEl("label");
	ll.innerHTML = "file_"+i+":";
	td_label.appendChild(ll);

	tr.appendChild(td_label);
	
	var td_file = createEl("td");
	td_file.appendChild(createFile(i));

	tr.appendChild(td_label);
	tr.appendChild(td_file);
	tbody.appendChild(tr);
	tab.appendChild(tbody);
}
function createEl(el){
	return document.createElement(el);
}
function createFile(id){		
    var _newInput = document.createElement("input");
    _newInput.setAttribute("type", "file");
    _newInput.setAttribute("name", "upload");
	_newInput.setAttribute("id", "fileArea"+id);
	return _newInput;
}


	//window.opener.location.reload();

</script>
</head>
<body>
<form action="importTestCase.action" enctype="multipart/form-data"	method="post" id="fileform">
<table class="form_table" id="form_table">
	<tr>
		<th colspan="2" class="form_head">Import Test Case</th>
	</tr>
	<tr id="tr_temp">
		<td>File:</td>
		<td>
			<input	type="file" name="upload" id="fileArea"></input> &nbsp;
			<!--  
			<input type="button" value="Add File" onclick="creNode();" id="addButton" class="btn1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			-->
			<a href="downloadCaseTemplate.action">Download Template File</a>
		</td>
	</tr>
</table>

<table class="form_table">
	<tr>
		<td style="border-right:none;">
		</td>
		<td>
			<input type="submit" value="submit" class="btn1"></input>
		</td>
	</tr>
</table>
</form>
<h3 style="color:blue;">${successMsg }</h3>
<h3 style="color:red;">${errorMsg }</h3>
</body>
</html>