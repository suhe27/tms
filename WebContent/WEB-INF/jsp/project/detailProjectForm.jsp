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
<title>Project</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/project.js"></script>
</head>
<body>
<div id="div_main"><label class="statictittle">Project Update</label>
<hr />
<form action="updateProjectForm.action" method="post"
	onsubmit="return checkProject();">
	<input type="text" name="projectId" id="projectId" size="35" readonly="readonly" value="${projectForm.projectId}" style="display:none"/>
<table id="common_table" class="form_table">

	<tr>
		<td width="180px">Project Name:</td>
		<td class="alignl"><input type="text" name="projectName" id="projectName" size="35"
			value="${projectForm.projectName}" /></td>
	</tr>
	<tr>
	
		<td width="180px">Team:</td>
		<td class="alignl"><select name="teamId" id="teamId">
			<s:iterator value="#teamList" id="team">
			<s:if test="#team.teamId == #projectForm.teamId">
				<option value="${team.teamId}" selected="selected">${team.teamName }</option>
			</s:if>
			<s:else>
					<option value="${team.teamId}" >${team.teamName }</option>
			</s:else>
			</s:iterator>
		</select></td>
	</tr>
	
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>