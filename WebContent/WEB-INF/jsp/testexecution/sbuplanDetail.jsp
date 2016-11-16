<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>${subPlan.subPlanName }</title>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="subplanform" id="subplanform" action="updateSubTestPlan.action">
		<display:table name="testUnitList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="toDetailSubTestPlan.action?subPlanId=${subPlan.subPlanId}" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No. </th>
				<th style="width: 32px;" title="Select All"><input type="checkbox" onclick="selectAllSubTestPlan(this);"></input></th>
				<th style="width: 300px;">Test Unit Name </th>
				
				<th style="width: 300px;">Board</th>
				<th style="width: 300px;">Target</th>
				
				<th style="width: 80px;">Total Cases</th>
				<th style="width: 80px;">DueDate</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestUnits" value="${row.testUnitId }" />
			</display:column>
			<display:column>
				<a href="javascript:toDetailSubPlan(${row.testSuiteId });" style="color:#005fc0;">
					${row.testUnitName}${row.testSuiteId }
				</a>
			</display:column>
			<display:column>${row.board.boardName}</display:column>
				<display:column>${row.target.targetName}</display:column>
				
				
				
			<display:column  property="totalCases" />
				<%--<display:column>${row.user.userName}</display:column>
					--%><display:column  property="dueDate" />
			<%-- <display:column>
				<a href="javascript:toUpdateTestUnitRs(${row.testUnitId });" style="color:#005fc0;">Update Result</a>
					<a href="javascript:toUpdateTestUnit(${row.testUnitId });" style="color:#005fc0;">Update Unit</a>
			</display:column> --%>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		</form>
		
		<div style="width:100%;height:25px;border:1px gray dotted;">
		<s:if test="!#projectName.contains('Media')">
			<div style="width:10%;height:100%;display:inline;">				
				&nbsp;<input type="button" class="btn" value="Delete" onclick="deleteTestUnits('${subPlan.subPlanId}');;"/>		
			</div>	
			<div style="height:100%;display:inline;">
				<input type="button" class="btn" value="Add Test Unit" onclick="toAddTestUnit('${subPlan.subPlanId}');"/>
			</div>		
			</s:if>
		</div>	
			
	</body>
	<script type="text/javascript">
	
	function toUpdateTestUnitRs(id) {
		MyWindow.winOpenFullScreen("toUpdateTestUnitRs.action?testUnitId=" + id,'unitDetail');
		//MyWindow.OpenCenterWindowScroll("toUpdateTestUnit.action?testUnitId=" + id,'unitDetail',800,950);
	}

	function toUpdateTestUnit(id) {
		MyWindow.winOpenFullScreen("toUpdateTestUnit.action?testUnitId=" + id,'unitDetail');
		//MyWindow.OpenCenterWindowScroll("toUpdateTestUnit.action?testUnitId=" + id,'unitDetail',800,950);
	}
	function toAddTestUnit(subPlanId){
		
		MyWindow.winOpenFullScreen("toAddTestUnit.action?subPlanId=" + subPlanId,'addTestUnit');
		}

	/* function showTestResultClick(id) {
		MyWindow.winOpenFullScreen("showTestResult.action?testUnitId=" + id,'showTestResults');
	} */
	
	function toDetailSubPlan(id) {
		
		MyWindow.OpenCenterWindowScroll("ListResultBySubplanId.action?subPlanId=" + id,'detail',700,900);
	}
	</script>
</html>