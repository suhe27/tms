<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Suites</title>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<script type="text/javascript" src="scripts/testcasesuit.js"></script>

<script type="text/javascript">
	function selectTestSuiteClick(id) {
		window.location.href = "toUpdateTestSuite.action?testSuiteId=" + id;
	}
</script>
<style type="text/css">
@import "css/index.css";

@import "css/page.css";
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="pop" class="blackMask" style="height: 2000px; display: none;"></div>
<div id="div_main"><label class="statictittle">TestCaseSuites
List</label>
<hr />

<form method="post" name="testsuiteform">
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th><input type="checkbox"
			onclick="selectTestSuitesCurrentPage(this);"></input></th>
		<!--<th>TESTSUITEID</th>
		--><th>TESTSUITENAME</th>
		<th>PROJECT</th>
		<th>FEATURES NAME</th>
		<th>OSNAME</th>
		<th>OWNER</th>
		<th>TOTALCASES</th>

	</tr>
	<s:iterator value="#testSuiteList" id="testsuite" status="st">
		<tr
			onmouseover="selectLine(true, '${testsuite.testSuiteId }', this)"
			onmouseout="selectLine(false, '${testsuite.testSuiteId }', this)">
			<td><input type="checkbox" name="multiSuiteId"
				value="${testsuite.testSuiteId }" /></td>
			<!--<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')"
				class="td_id">${testsuite.testSuiteId}</td>
			--><td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.testSuiteName
			}</td>
			<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.project
			}</td>
			<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.features
			}</td>
			<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.oses
			}</td>
			<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.user.userName
			}</td>
			<td
				onclick="selectTestSuiteClick('${testsuite.testSuiteId }')">${testsuite.totalCases
			}</td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="11"><span style="float: left;"><input
			type="submit" value="Delete" onclick="delTestSuite();" /></span> <span
			style="float: left; display: inline"><input type="button"
			value="Create TestSuite" onclick="createSuperTestSuite();" /></span>
		<span style="float: right; display: inline"><p:page
			action="listTestSuites.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
			
		</p:page></span></td>
	</tr>
</table>
</form>
<div id="light" class="white_content2">Please wait while submit
result and creating testsuite ...</div>
</div>
</body>
</html>