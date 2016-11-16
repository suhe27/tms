<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Sub Test Plan</title>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="subplanform" id="subplanform" >
		<display:table name="testUnitList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="toDetailSubTestPlan.action?subPlanId=${subPlan.subPlanId}" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No. </th>	
		  			<th style="width: 32px;" title="Select All"><input type="checkbox" onclick="selectAllSubTestPlan(this);"></input></th>		
				<th style="width: 300px;">Test Unit Name </th>	
				<th style="width: 300px;">Test Suite Name </th>	
				<th style="width: 300px;">Target </th>											
				<th style="width: 80px;">Total Cases</th>			
				<th style="width: 80px;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestUnits" value="${row.testUnitId }" />
			</display:column>
			<display:column>
				<a href="javascript:selectTestSuiteClick(${row.testSuiteId });" style="color:#005fc0;">
					${row.testUnitName}
				</a>
			</display:column>
					<display:column>${row.testSuite.testSuiteName}</display:column>	
					<display:column>${row.target.targetName}</display:column>		
			<display:column  property="totalCases" />				
			<display:column>			
					<a href="javascript:toUpdateTestUnit(${row.testUnitId });" style="color:#005fc0;">Update Unit</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
	</form>
		<div style="width:100%;height:25px;border:1px gray dotted;">
		<s:if test="#project.projectName.contains('DPDK')">
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
	function selectTestSuiteClick(id) {
		//MyWindow.OpenCenterWindowScroll("toDetailTestSuite.action?testSuiteId=" + id,'detail',700,900);
		MyWindow.winOpenFullScreen("toDetailTestUnit.action?testSuiteId=" + id,'unitDetail');
	}
	function toUpdateTestUnit(id) {
		//MyWindow.winOpenFullScreen("toUpdateTestUnit.action?testUnitId=" + id,'unitDetail');
		MyWindow.OpenCenterWindowScroll("toUpdateTestUnit.action?testUnitId=" + id,'detail',400,400);
		
	}
	function toAddTestUnit(subPlanId){
		
		MyWindow.winOpenFullScreen("toAddTestUnit.action?subPlanId=" + subPlanId,'addTestUnit');
		}	
	</script>
</html>