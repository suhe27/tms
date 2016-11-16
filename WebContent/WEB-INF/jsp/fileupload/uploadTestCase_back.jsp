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
i = 1;
function newfileuploadarea() {
		     i++;
		     if(i>9)
				{
					return;
				}
		     var label = "file";
		     var fileId = "fileArea";
		     var btn = document.getElementById('addButton');	
		     var formObj = document.getElementById("fileform");
		     var _newLabel= document.createElement("label");
		      _newLabel.innerHTML=label+"_"+i+":";		
		      var _newInput = document.createElement("input");
		      _newInput.setAttribute("type", "file");
		      _newInput.setAttribute("name", "upload");
			  _newInput.setAttribute("id", fileId + i);
			 var _newBr = document.createElement("br");
			 formObj.insertBefore(_newBr,btn);
			 formObj.insertBefore(_newInput,_newBr);
			 formObj.insertBefore(_newLabel,_newInput);
}  
</script>
</head>
<body>
<div id="div_main"><label class="statictittle">Import
TestCase</label>
<hr />
<h3 style="color: red">${errorMsg}</h3>
<form action="importTestCase.action" enctype="multipart/form-data"
	method="post" id="fileform"><label>file_1:</label><input
	type="file" name="upload" id="fileArea"></input> <br />
<input type="button" value="new file" onclick="newfileuploadarea();"
	id="addButton"></input> <br />
<input type="submit" value="submit"></input></form>
</div>
</body>
</html>