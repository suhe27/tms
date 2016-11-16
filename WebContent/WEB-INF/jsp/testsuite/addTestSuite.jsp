<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Suite</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");

@import url("<%=basePath%>css/ui/jquery.ui.all.css");

@import url("<%=basePath%>css/ui/demos.css");
</style>
<script type="text/javascript" src="<%=basePath%>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>
</head>
<body>
<form action="addTestSuite.action" method="post" onsubmit="return checkAddSuiteForm();">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add Test Suite</th>
	</tr>
	<tr>
		<td>Suite Name</td>
		<td><input name="testsuite.testSuiteName" value="${testSuite.testSuiteName}"
			id="testSuiteName"><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td width="150">Project</td>
		<td><select id="projectId" name="testsuite.projectId">
               <option value="-1">- Select a value-</option>
			<s:iterator value="#projectList" id="project">
				<option value="${project.projectId }">${project.projectName}</option>
			</s:iterator>

		</select></td>
	</tr>     
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" Add "
			class="btn1" /></td>
	</tr>
</table>
</form>
</body>

</html>