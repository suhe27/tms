<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Plan</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testplan.js"></script>
		<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
	</head>
	<body class="mobile">
		<form method="post" name="testplanform" id="testplanform">
		<display:table name="testPlanList" class="mobile" id="row" style="width:100%;">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 5%;" >No.</th>
				<th style="width: 5%;" title="Select All"><input type="checkbox" onclick="selectAllTestPlan(this);"></th>
				<th style="width: 15%;">PlanName</th>
				<th style="width: 10%;">Project</th>
				<th style="width: 10%;">Phase</th>
				<th style="width: 20%;">Duration</th>
					<th style="width: 15%;">Description</th>
				<th style="width: 10%;">Total Cases</th>
				<th style="width: 10%;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Numlistcasesber" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			
			<display:column>
				<input type="checkbox" name="multiPlanId" value="${row.testPlanId }" />
			</display:column>
			<display:column> 
				<a href="javascript:detailTestPlan(${row.testPlanId });" style="color:#005fc0;cursor: pointer;" title="${row.planName }">${row.planName} </a>
			</display:column>
			<display:column>
				${row.project.projectName }
			</display:column>
		 <display:column>
		   ${row.phase.phaseName }
		   </display:column>
			<display:column>
			${row.startDate }->${row.endDate}
			</display:column>
		
			<display:column  property="description" />
			<display:column  property="totalCases" />
			<display:column>
				<a href="javascript:updateTestPlan(${row.testPlanId });" style="color:#0059fd;">Update</a>&nbsp;&nbsp;&nbsp;
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
			<div style="width:100%;height:30px;border:1px gray dotted;">
				<div style="float:left;width:45%;height:25px;">
				&nbsp;<input type="button" class="btn" value="Delete" onclick="delTestPlan();"/>
				<input type="button" class="btn" value="Create Test Plan" onclick="createTestPlan();"/>
				<input type="button" class="btn" value="Copy Test Plan" onclick="copyTestPlan();"/>
			</div>
				<div style="float:right;width:45%;height:25px;">
				<p:page	action="listTestPlans.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"></p:param>
				<p:param name="phaseId" value="#phaseId"></p:param>
				<p:param name="planName" value="#planName"></p:param>
				</p:page>
			</div>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function detailTestPlan(id) {
		MyWindow.winOpenFullScreen("toDetailTestPlan.action?testPlanId=" + id,'detail');
	}
	function updateTestPlan(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateTestPlan.action?testPlanId=" + id,'detail',600,800);
	}
	function createTestPlan() {
		MyWindow.OpenCenterWindowScroll("toAddTestPlan.action",'detail',600,800);
	}
	function copyTestPlan() {
		var checkboxs = $('input:checkbox:checked');
		
		if(checkboxs.length <= 0)
		{
			alert("Please choose one test plan for copy!");
			return;
		}
		if(checkboxs.length > 1)
		{
			alert("Only support copy single test plan!");
			return;
		}
		//document.testplanform.action = "copyTestPlan.action";
		//document.testplanform.submit();

        var cboxes = document.getElementsByName('multiPlanId');
        var len = cboxes.length;
     
        for (var i=0; i<len; i++) {
        	if(cboxes[i].checked) {
        		var id = cboxes[i].value;
        		//alert(id);
        	}
        }
        //alert(id);
		MyWindow.OpenCenterWindowScroll("copyTestPlan.action?multiPlanId=" + id,'detail',700,900);
	}
	
	</script>
</html>