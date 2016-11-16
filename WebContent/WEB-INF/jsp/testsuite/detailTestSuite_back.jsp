<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Suite</title>
<script type="text/javascript">
</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">TestSuite
Detail</label>
<hr />
<form action="updateTestSuite.action" method="post" name="testsuiteform"><input
	type="hidden" name="testsuite.testSuiteId" id="testSuiteId"
	value="${testsuite.testSuiteId} " />
	<input type="hidden" name="testSuiteId"
	value="${testSuiteId} " />
<table id="sub_table">
	
	<tr>
		<td width="180px">OWNER:</td>
		<td class="alignl"><select name="testsuite.userId" id="userId"
			style="width: 100px">
			<s:iterator value="#userList" id="u">
			<s:if test="#testsuite.userId == #u.userId">
			<option value="${u.userId }" selected="selected">${u.userName }</option>
			</s:if>
			<s:else>
			<option value="${u.userId }">${u.userName }</option>
			</s:else>		
			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td width="180px">TESTCASENAME:</td>
		<td class="alignl"><input type="text" name="testsuite.testSuiteName"
			id="testSuiteName" value="${testsuite.testSuiteName}" /></td>
	</tr>
	<tr>
		<td width="180px">PROJECT:</td>
		<td class="alignl"><input type="text" name="testsuite.project" id="project"
			value="${testsuite.project}"readonly="readonly" /></td>
	</tr>
	<tr>
		<td width="180px">FEATURES:</td>
		<td class="alignl"><input type="text" name="testsuite.features" id="features"
			value="${testsuite.features}" readonly="readonly" /></td>
	</tr>
	<tr>
		<td width="180px">OSES:</td>
		<td class="alignl"><input type="text" name="testsuite.oses" id="oses"
			value="${testsuite.oses}" readonly="readonly" /></td>
	</tr>
	<tr>
		<td width="180px">TOTALCASES:</td>
		<td class="alignl"><input type="text" name="testsuite.totalCases"
			id="totalCases" value="${testsuite.totalCases}"
			readonly="readonly" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>

	<tr>
		<td colspan="2">Contained TestCases:<br />
		<hr />
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<table id="common_table" border="1">
			<tr style="background-color: #EEEEEE;">
				<th><input  name="isSelected" type="checkbox"
					onclick="selectTestCasesInOneSuite(this);"></input></th>
				<th>TESTCASEID</th>
				<th>TESTCASENAME</th>
				<th>PROJECT</th>
				<th>STATE</th>
				<th>VERSION</th>
			</tr>
			<s:iterator value="#testCaseList" id="testcase" status="st">
				<tr onmouseover="selectLine(true, '${testcase.testCaseId }', this)"
					onmouseout="selectLine(false, '${testcase.testCaseId }', this)">
					<td><input type="checkbox" name="multiCaseId"
						value="${testcase.testCaseId}" /></td>
					<td>${testcase.testCasealiasId}</td>
					<td>${testcase.testCaseName }</td>
					<td>${testcase.project }</td>
					<td>${testcase.state }</td>
					<td>${testcase.version }</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="11"><span style="float: left;"><input
					type="button" value="Delete"
					onclick="delTestCaseInTestSuite();" /></span> <span
					style="float: right; display: inline"> <p:page
					action="toUpdateTestSuite.action" beanName="#pageBean"
					classForId="pagination-digg" activeClass="active"
					linkOffClass="link-off" totalPageClass="total-page">
					<p:param name="testSuiteId" value="#testSuiteId"></p:param>
				</p:page></span></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>

<div id="light" class="white_content2">Please wait while submit
result and creating testsuite ...</div>
</div>
</body>
</html>