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
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath %>scripts/common.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Target</title>

</head>
<body>

<div id="div_main"><label class="statictittle">Add Target</label>
<hr />
<form action="addTarget.action" method="post" onsubmit="return checkTarget();">

<table id="common_table" class="form_table">

	<tr>
		<td class="alignr">Target Name:</td>
		<td class="alignl"><input type="text" name="targetName" id="targetName" /></td>
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
	</tr>
</table>

</form>


</div>
</body>
</html>