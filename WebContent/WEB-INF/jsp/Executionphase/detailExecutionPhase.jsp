<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loPhasee.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Execution Type</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/phase.js"></script>
</head>
<body>

<div id="div_main"><label class="statictittle">Execution Type Detail</label>
<hr />
<form action="updateExecutionPhase.action" method="post"
	onsubmit="return checkExecutionPhase();">
	<input type="text" name="phaseId" size="35" readonly="readonly" value="${phase.phaseId}" style="display:none"/>
<table id="common_table" class="form_table">
	<tr>
		<td width="180px">ExecutionType Name:</td>
		<td class="alignl"><input type="text" name="phaseName" id="phaseName" size="35"
			value="${phase.phaseName}" /></td>
	</tr>
	<tr>
		<td width="180px">Project:</td>
		<td class="alignl"><select name="projectId" id="projectId">
			<s:iterator value="#projectList" id="project">
			<s:if test="#project.projectId == #phase.projectId">
				<option value="${project.projectId }" selected="selected">${project.projectName }</option>
			</s:if>
			<s:else>
				<option value="${project.projectId }">${project.projectName }</option>
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