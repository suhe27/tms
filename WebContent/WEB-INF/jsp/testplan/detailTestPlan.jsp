<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Plan</title>
		<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
	<h6  align="center">${testPlan.planName}</h6>
	<form method="post" name="testplanform" >
		<display:table name="subPlanList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50"  sort="external">
			<display:caption><thead>
				<tr style="background:#e2f3fc;">
		  		<th style="width: 5%;" >No.</th>
				<th style="width: 5%;" title="Select All"><input type="checkbox" onclick="selectAllSubTestPlan(this);"></th>
				<th style="width: 15%;">SubPlan Name</th>	
				<th style="width: 15%;">Project</th>
				<th style="width: 15%;">Suite</th>				
				<th style="width: 15%;">Owner</th>									
				<th style="width: 15%;">Total Cases</th>
				<th style="width: 15%;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiSubPlan" value="${row.subPlanId }" />
			</display:column>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<display:column>
				<a href="javascript:toDetailSubPlan(${row.subPlanId});" style="color:#0059fd;">${row.subPlanName }</a>
			</display:column>			
			<display:column > ${row.projectName}</display:column>
			<display:column > ${row.suiteName}</display:column>	
			<display:column > ${row.userName}</display:column>			
			<display:column  property="totalCases" />
			<display:column>
				<a href="javascript:toUpdateSubTestPlan(${row.subPlanId});" style="color:#0059fd;">Update</a>&nbsp;&nbsp;	
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		</form>
		<div style="width:100%;height:30px;border:1px gray dotted;">
			&nbsp;<input type="button" class="btn" value="Delete" onclick="delSubplan();"/>
			<input type="button" class="btn" value="Create Sub Plan" onclick="createSuperTestSuite('${testPlan.testPlanId}');"/>
		<div style="float:right;width:45%;height:25px;">
				<p:page	action="toDetailTestPlan.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="testPlanId" value="#testPlanId"></p:param>
				</p:page>
			</div>
		</div>
			
	</body>
	<script type="text/javascript">
	function toDetailSubPlan(id) {
		MyWindow.OpenCenterWindowScroll("toDetailSubTestPlan.action?msg=1&subPlanId=" + id,'detail',600,800);
	}
	
	
	function createSuperTestSuite(id){
		MyWindow.OpenCenterWindowScroll("toAddSubTestPlan.action?testPlanId=" + id,'detail',400,800);
	}


	function toUpdateSubTestPlan(id) {
		
		MyWindow.OpenCenterWindowScroll("toUpdateSubTestPlan.action?subPlanId=" + id,'detail',400,800);
	}
	function toExportSubTestPlan(id) {
		MyWindow.OpenCenterWindowScroll("toExportSubTestPlanToXml.action?subPlanId=" + id,'detail',600,800);
	}
	function toExportFailedSubTestPlan(id) {
		MyWindow.OpenCenterWindowScroll("toExportFailedSubTestPlanToXml.action?subPlanId=" + id,'detail',600,800);
	}
	function toUploadSubTestPlan(){

		MyWindow.OpenCenterWindowScroll("toUploadSubTestPlan.action",'detail',600,800);
		}
	</script>
</html>