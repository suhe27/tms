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
<title>Execution OS</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/os.js"></script>
</head>
<body>

<div id="div_main"><label class="statictittle">OS Detail</label>
<hr />
<form action="updateExecutionOS.action" method="post" onsubmit="return checkOS();">
	<input type="text" name="executionOS.osId" size="35" readonly="readonly" value="${os.osId}" style="display:none"/>
<table id="common_table" class="form_table">
	<tr>
		<td width="180px">OSName:</td>
		<td class="alignl"><input type="text" name="executionOS.osName" id="osName" size="35"
			value="${os.osName}" /></td>
	</tr>
	<tr>
					<td width="180px">Project:</td>
					<td class="alignl"><select name="executionOS.projectId" id="projectId">
							<s:iterator value="#projectList" id="project">
							<s:if test="#os.projectId==#project.projectId">
							<option value="${project.projectId }" selected="selected">${project.projectName }</option>
							</s:if>
								<s:else>
								<option value="${project.projectId }" >${project.projectName }</option>
								</s:else>
							</s:iterator>
					</select><span style="color:red;"> *</span></td>
	</tr>

<!-- 
				<tr>
					<td width="180px">HostName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.hostName" id="hostName"  value="${os.hostName}"/><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">IP:</td>
					<td class="alignl"><input type="text" name="executionOS.ip" id="ip"  value="${os.ip}"/><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">LoginName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.loginName" id="loginName" value="${os.loginName}"/><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Passwd:</td>
					<td class="alignl"><input type="text"
						name="executionOS.passwd" id="passwd" value="${os.passwd}"/><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Port:</td>
					<td class="alignl"><input type="text" name="executionOS.port"
						id="port" value="${os.port}"/><span style="color:red;"> *</span></td>
				</tr>
<tr>
					<td width="180px">CodePath:</td>
					<td class="alignl"><input type="text" name="executionOS.codePath"
						id="codePath" value="${os.codePath}"/><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Cmds:</td>
					<td class="alignl"><input type="text" name="executionOS.cmds"
						id="cmds" value="${os.cmds}"/><span style="color:red;"> *</span></td>
				</tr>

				<tr>
					<td width="180px">Parameters:</td>
					<td class="alignl"><input type="text"
						name="executionOS.parameters" id="parameters" value="${os.parameters}"/><span style="color:red;"> *</span></td>
				</tr>
-->
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