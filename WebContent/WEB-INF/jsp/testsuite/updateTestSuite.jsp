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
<title>Test Suite</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath%>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>

</head>
<body>
<form action="updateTestSuite.action" method="post" name="testsuiteform" onsubmit="return checkAddSuiteForm();">
	<input	type="hidden" name="testsuite.testSuiteId" id="testSuiteId"	value="${testsuite.testSuiteId} " />
	<input type="hidden" name="testSuiteId"	value="${testsuite.testSuiteId} " />
<table id="sub_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Update Test Suite</th>
	</tr>
	<!-- 
	<tr>
		<td>Project:</td>
		<td>
		<select name="testsuite.projectId" id="projectId" style="width: 150px" >
			<s:iterator value="#projectList" id="project">
				<s:if test="#testsuite.projectId == #project.projectId">
					<option value="${project.projectId }"  selected="selected">${project.projectName}</option>
				</s:if>
				<s:else>
				<option value="${project.projectId }">${project.projectName}</option>
				</s:else>
			</s:iterator>
		</select>
		<span style="color:red;">*</span>
		</td>
	</tr>
	-->
	<tr>
		<td width="150">Project:</td>
		<td><input type="radio" name="testsuite.projectId" value="${testsuite.projectId}"  checked="checked" />
			<label>${testsuite.project}</label>
		</td>
	</tr>
	<tr>
		<td>Owner:</td>
		<td>
			<select name="testsuite.userId" id="userId" style="width: 100px">
				<s:iterator value="#userList" id="uu">
				<s:if test="#testsuite.userId == #uu.userId">
				<option value="${uu.userId }" selected="selected">${uu.userName }</option>
				</s:if>
				<s:else>
				<option value="${uu.userId }">${uu.userName }</option>
				</s:else>		
				</s:iterator>
			</select><span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td>Test Suite Name:</td>
		<td>
			<input type="text" name="testsuite.testSuiteName" id="testSuiteName" value="${testsuite.testSuiteName}" /><span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td>Features:</td>
		<td>
			${testsuite.features}
		</td>
	</tr>
	<tr>
		<td>OS Name:</td>
		<td>
			${testsuite.oses}
		</td>
	</tr>
	<tr>
		<td>Total Cases:</td>
		<td>
			${testsuite.totalCases}
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<input type="submit" value="update" class="btn1"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>