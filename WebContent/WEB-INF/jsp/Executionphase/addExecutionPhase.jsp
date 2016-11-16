<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loPhasee.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>scripts/phase.js"></script>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<title>Execution Type</title>

</head>
<body>

<div id="div_main"><label class="statictittle">Add Execution Type</label>
<hr />
<form action="addExecutionPhase.action" method="post" onsubmit="return checkExecutionPhase();">

<table id="common_table" class="form_table">

	<tr>
		<td class="alignr">ExecutionType Name:</td>
		<td class="alignl"><input type="text" name="phaseName" id="phaseName" /></td>
	</tr>
	<tr>
		<td width="180px">Project:</td>
		<td class="alignl"><select name="projectId" id="projectId">
			<s:iterator value="#projectList" id="project">
				<option value="${project.projectId }">${project.projectName }</option>
			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="add" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>