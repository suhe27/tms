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
<title>Platform</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/platform.js"></script>
</head>
<body>

<div id="div_main"><label class="statictittle">Add Platform</label>
<hr />
<form action="addPlatform.action" method="post"
	onsubmit="return checkPlatform();">

<table id="common_table" class="form_table">

	<tr>
		<td width="180px">Platform Name:</td>
		<td class="alignl"><input type="text" name="platformName"
			id="platformName" /></td>
	</tr>
	<tr>
		<td width="180px">Project:</td>
		<td class="alignl"><select name="projectId" id="projectId">
			<s:iterator value="#projectList" id="project">
				<option value="${project.projectId }">${project.projectName }</option>
			</s:iterator>
		</select></td>
	</tr>
	
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
			<td class="operation"><input type="submit" value="add" /></td>
</table>
</form>


</div>
</body>
</html>