<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Feature</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/testenv.js"></script>
</head>
<body>

<form action="addTestenv.action" method="post"
	onsubmit="return checktestenv()">

<table id="common_table"  class="form_table">
	<tr>
		<td class="180px">Testenv  Name:</td>
		<td class="alignl"><input type="text" name="testenv.envName"
			id="envName" /><span style="color:red;">*</span></td>
			
		<tr>	
			<td width="180px">Project:</td>
		<td class="alignl"><select name="testenv.projectId" id="projectId">
			<s:iterator value="#projectList" id="project">
				<option value="${project.projectId }">${project.projectName }</option>
			</s:iterator>
		</select></td>
	</tr>
	
	
	
	<tr>
		<td>Descrption</td>
		<td>
		<textarea name="testenv.desc"  id="desc" cols=35 rows=2 ></textarea>
		</td>
	</tr>
	
	
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td><input type="submit" value="add" class="btn1" /></input></td>
	</tr>
</table>
</form>
</body>
</html>