<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>${ testUnit.testUnitName}</title>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/testresult.js"></script>

</head>
<body topmargin="0" leftmargin="0" class="mobile" onload="selectShowTestResultsColor();">
<form method="post" name="testunitform" id="testunitform"
	action="updateTestUnit.action"><input type="hidden"
	value="${testUnit.testUnitId}" name="testUnit.testUnitId"></input> <input
	type="hidden" value="${testUnit.testUnitId}" name="testUnitId"></input>
<input type="hidden" value="${testUnit.testUnitName}"
	name="testUnit.testUnitName"></input> <input type="hidden"
	value="${testUnit.subPlanId}" name="testUnit.subPlanId" /> <input
	type="hidden" value="${testUnit.testPlanId}" name="testUnit.testPlanId" />
<input type="hidden" value="${testUnit.testSuiteId}"
	name="testUnit.testSuiteId" /> <input type="hidden"
	value="${testUnit.boardId}" name="testUnit.boardId" /> <input
	type="hidden" value="${testUnit.space}" name="testUnit.space" /> <input
	type="hidden" value="${testUnit.mode}" name="testUnit.mode" /> <input
	type="hidden" value="${testUnit.scriptFlag}" name="testUnit.scriptFlag" />
<input type="hidden" name="testUnit.totalCases"
	value="${testUnit.totalCases}" id="totalCases" readonly="readonly">
<table id="filter_table" class="form_table"
	style="width: 100%; height: 30px;">
	<tr>
		<td style="text-align: center;"><b>Filters:</b> <select
			onchange="filterShowTestResult();" id="resulttypeid" name="resultTypeId">
			<option value="0">All</option>
			<option disabled="disabled">------</option>
			<s:iterator value="#resultTypeList" id="result" status="sx">
				<s:if test="#resultTypeId == #result.resultTypeId">
					<option value="${result.resultTypeId }" selected="selected">${result.resultTypeName}</option>
				</s:if>
				<s:else>
					<option value="${result.resultTypeId }">${result.resultTypeName}</option>
				</s:else>
			</s:iterator>
		</select></td>
	</tr>
</table>
<display:table name="testResultList" class="mobile" id="row"
	style="width:100%;" cellpadding="0" cellspacing="0" pagesize="50"
	requestURI="listTestPlans.action" sort="external">
	<display:caption>
		<thead>
			<tr class="pageHeader">
				<th style="width: 32px;">No.</th>
				<th style="width: 500px;">Test Case Name</th>
				<th style="width: 100px;">Result</th>
				<th style="width: 150px;">Log</th>
				<th style="width: 150px;">Bug Id</th>
				<th style="width: 150px;">Comments</th>
			</tr>
		</thead>
	</display:caption>
	<display:column title="Row Number" class="fixTitle1">
		<c:out value="${row_rowNum}"></c:out>
		<input type="hidden" name="multiResult" value="${row.resultId }" />
	</display:column>
	<display:column>
		<a href="javascript:showTestCaseClick(${row.testCaseId });"
			style="color: #0059fd;">${row.testCaseName }</a>
	</display:column>
	<display:column class="result">

		<s:iterator value="#resultTypeList" id="res" status="sx">
			<c:if test="${row.resultTypeId == res.resultTypeId}">
						${res.resultTypeName}
					</c:if>

		</s:iterator>

	</display:column>
	<display:column>
			<a href="${row.log}" target="_blank">${row.log} </a>
		
	</display:column>
	<display:column>
			${row.bugId }
			</display:column>
	<display:column>
			${row.comments }
			</display:column>
	<display:setProperty name="basic.show.header" value="false" />
</display:table>
<div style="width: 100%; height: 25px;"><p:page
	action="showTestResult.action" beanName="#pageBean"
	classForId="pagination-digg" activeClass="active"
	linkOffClass="link-off" totalPageClass="total-page">
	<p:param name="resultTypeId" value="#resultTypeId"></p:param>
	<p:param name="testUnitId" value="#testUnitId"></p:param>
</p:page></div>
</form>
</body>
<script type="text/javascript">
	function showTestCaseClick(id) {
		MyWindow.winOpenFullScreen("showTestCase.action?testCaseId=" + id,
				'detail');
	}
</script>
</html>