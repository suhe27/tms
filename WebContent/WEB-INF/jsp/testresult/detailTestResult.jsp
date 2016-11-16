<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>${ testUnit.testUnitName}</title>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/testresult.js"></script>
		
	</head>
	<body topmargin="0" leftmargin="0" class="mobile" onload="selectDefaultColor();">
		<form method="post" name="testunitform" id="testunitform" action="updateTestUnit.action">
		<input	type="hidden" value="${testUnit.testUnitId}"  name="testUnit.testUnitId"></input> 
		<input type="hidden" value="${testUnit.testUnitId}"  name="testUnitId"></input>
		<input type="hidden" value="${testUnit.testUnitName}" name="testUnit.testUnitName"></input>
		<input	type="hidden" value="${testUnit.subPlanId}" 	name="testUnit.subPlanId" />
		<input	type="hidden" value="${testUnit.testPlanId}" 	name="testUnit.testPlanId"/>
		<input	type="hidden" value="${testUnit.testSuiteId}" 	name="testUnit.testSuiteId"/>
		<input	type="hidden" value="${testUnit.boardId}" 	name="testUnit.boardId"/>
		<input	type="hidden" value="${testUnit.space}" 	name="testUnit.space"/>
		<input	type="hidden" value="${testUnit.mode}" 	name="testUnit.mode"/>
		<input	type="hidden" value="${testUnit.scriptFlag}" 	name="testUnit.scriptFlag"/>
		<input	type="hidden" value="${testUnit.tester}" 	name="testUnit.tester"/>
		<input	type="hidden" value="${testUnit.dueDate}" 	name="testUnit.dueDate"/>
		<input type="hidden" name="testUnit.totalCases" value="${testUnit.totalCases}" id="totalCases" readonly="readonly">
		<table id="filter_table" class="form_table" style="width:100%;height:30px;">
			<tr>
				<td style="text-align: center;">
					<b>Filters:</b>
					<select onchange="filterTestResult();" id="resulttypeid" name="resultTypeId">
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
					</select>
				</td>
			</tr>
		</table>
		<display:table name="testResultList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="listTestPlans.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
				<th style="width: 500px;">Test Case Name</th>
				<th style="width: 100px;">Result</th>
				<th style="width: 150px;">Log</th>
				<th style="width: 150px;">Bug Id</th>
				<th style="width: 150px;">Comments</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
				<input type="hidden" name="multiResult" value="${row.resultId }" />
			</display:column>
			<display:column>
					<a href="javascript:showTestCaseClick(${row.testCaseId });" style="color:#0059fd;">${row.testCaseName }</a>
			</display:column>
			<display:column>
				<select	name="testResultList[${row_rowNum-1}].resultTypeId"	class="testResult">
				<s:iterator value="#resultTypeList" id="res" status="sx">
					<c:if test="${row.resultTypeId == res.resultTypeId}">
						<option value="${res.resultTypeId }" selected="selected">${res.resultTypeName}</option>
					</c:if>
					<c:if test="${row.resultTypeId != res.resultTypeId}">
						<option value="${res.resultTypeId}">${res.resultTypeName}</option>
					</c:if>
				</s:iterator>
				</select>
			</display:column>
			<display:column>	
			
			<textarea name="testResultList[${row_rowNum-1}].log"
			style="overflow: auto;" rows="1" cols="15">${row.log}</textarea>
				
			</display:column>
			<display:column>
				<input name="testResultList[${row_rowNum-1}].bugId" value="${row.bugId }"></input>
				
				
				
			</display:column>
			<display:column>
				
				<textarea name="testResultList[${row_rowNum-1}].comments"
			style="overflow: auto;" rows="2" cols="15">${row.comments }</textarea>
				<input name="testResultList[${row_rowNum-1}].resultId" value="${row.resultId }" type="hidden"></input>
				<input name="testResultList[${row_rowNum-1}].testPlanId" value="${row.testPlanId }" type="hidden"></input>
				<input name="testResultList[${row_rowNum-1}].subPlanId"	value="${row.subPlanId}" type="hidden"></input>
				<input name="testResultList[${row_rowNum-1}].testUnitId" value="${row.testUnitId}" type="hidden"></input>
				<input name="testResultList[${row_rowNum-1}].testCaseId" value="${row.testCaseId}" type="hidden"></input>
				<input type="hidden" name="testResultList[${row_rowNum-1}].testCaseName" value="${row.testCaseName }" type="hidden"></input>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;">
			&nbsp;<input type="button" class="btn" value="Batch Update" onclick="updateBatchTestResults();"/>
			<p:page  action="toUpdateTestUnitRs.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="resultTypeId" value="#resultTypeId"></p:param>
				<p:param name="testUnitId" value="#testUnitId"></p:param>
			</p:page>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function toDetailSubPlan(id) {
		MyWindow.OpenCenterWindowScroll("toDetailSubTestPlan.action?subPlanId=" + id,'detail',700,900);
	}
	function toUpdateSubPlan(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateSubTestPlan.action?subPlanId=" + id,'detail',600,800);
	}
	function showTestCaseClick(id) {
		MyWindow.winOpenFullScreen("showTestCase.action?testCaseId=" + id,'detail',600,800);
	}
	</script>
</html>