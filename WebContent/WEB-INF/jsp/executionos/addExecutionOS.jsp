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
<script type="text/javascript" src="<%=basePath %>scripts/os.js"></script>
</head>
<body>

	<div id="div_main">
		<label class="statictittle">Add ExecutionOS</label>
		<hr />
		<form action="addExecutionOS.action" method="post"
			onsubmit="return checkOS();">

			<table id="common_table" class="form_table">

				<tr>
					<td width="180px">ExecutionOs Name:</td>
					<td class="alignl"><input type="text" name="executionOS.osName"
						id="osName" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Project:</td>
					<td class="alignl"><select name="executionOS.projectId" id="projectId">
							<s:iterator value="#projectList" id="project">
								<option value="${project.projectId }">${project.projectName }</option>
							</s:iterator>
					</select><span style="color:red;"> *</span></td>
				</tr>

<!-- 

				<tr>
					<td width="180px">HostName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.hostName" id="hostName" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">IP:</td>
					<td class="alignl"><input type="text" name="executionOS.ip" id="ip" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">LoginName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.loginName" id="loginName" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Passwd:</td>
					<td class="alignl"><input type="text"
						name="executionOS.passwd" id="passwd" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Port:</td>
					<td class="alignl"><input type="text" name="executionOS.port"
						id="port" /><span style="color:red;"> *</span></td>
				</tr>
<tr>
					<td width="180px">CodePath:</td>
					<td class="alignl"><input type="text" name="executionOS.codePath"
						id="codePath" /><span style="color:red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Cmds:</td>
					<td class="alignl"><input type="text" name="executionOS.cmds"
						id="cmds" /><span style="color:red;"> *</span></td>
				</tr>

				<tr>
					<td width="180px">Parameters:</td>
					<td class="alignl"><input type="text"
						name="executionOS.parameters" id="parameters" /><span style="color:red;"> *</span></td>
				</tr>

			</table>
-->
			<table class="form_table">
				<tr>
					<td style="border-right: none;"></td>
					<td class="operation"><input type="submit" value="add" /></td>
			</table>
		</form>


	</div>



</body>
</html>