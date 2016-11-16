<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Case</title>
</head>
<body>

<div id="div_main"><label class="statictittle">Create
TestCase Suit</label>
<hr />
<form method="post" action="upLoadTestCases.action">

<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th><input type="checkbox" onclick=selectAllTestCases(this);;></input></th>
		<th>TestCaseID</th>
		<th>TestCaseName</th>
		<th>RequirementId</th>
		<th>Project</th>
		<th>AutoMationTest</th>
		<th>TestScript</th>
		<th>TestFunctionCall</th>
		<th>TimeOut</th>
		<th>State</th>
		<th>CreateDate</th>
	</tr>
	<s:iterator value="#testCaseList" id="testcase" status="st">
		<tr onmouseover="selectLine(true, '${testcase.testcaseId }', this)"
			onmouseout="selectLine(false, '${testcase.testcaseId }', this)">
			<td><input type="checkbox" name="multiCaseId"
				value="${testcase.testcaseId }" /></td>
			<td>${testcase.testcasealiasId}</td>
			<td>${testcase.testcaseName }</td>
			<td>${testcase.requirementId }</td>
			<td>${testcase.project }</td>
			<td>${testcase.automationTest }</td>
			<td>${testcase.testScript }</td>
			<td>${testcase.testfunctionCall }</td>
			<td>${testcase.timeout }</td>
			<td>${testcase.state }</td>
			<td>${testcase.createDate }</td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="11"><span style="float: left;"><input
			type="submit" value="create TestCaseSuit"
			onclick="return confirm('Confirm create Testcasesuit contains these selected testcases ?');" /></span><span
			style="float: right; display: inline"><p:page
			action="listTestCases.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
		</p:page></span></td>
	</tr>
</table>

</form>
</div>
</body>
</html>