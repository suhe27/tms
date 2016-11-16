<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Execution</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<h6  align="center">${executionName}</h6>
		<form method="post" name="testexecutionForm" id="testexecutionForm">
		<display:table name="testexecutionlists" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="testExecutionAction.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
				<th style="width: 32px;" title="Select All"><input type="checkbox" onclick="selectAllTestExecution(this);"></th>
				<th style="width: 100px;">Sub Execution Name</th>
				<th style="width: 100px;">Phase</th>
				<th style="width: 50px;">Pass</th>
				<th style="width: 50px;">Fail</th>
				<th style="width: 100px;">Not Run</th>
				<th style="width: 50px;">Block</th>
				<th style="width: 100px;">Pass Rate</th>
				<th style="width: 100px;">Total Cases</th>
				<th style="width: 100px;">DueDate</th>
				<th style="width: 100px;">Testers</th>
				<th style="width: 50px;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestExecution" value="${row.subExecutionId }" />
			</display:column>			
			<display:column> 
				<a href="toDetailSubTestExecution.action?subExecutionId=${row.subExecutionId }" target="subexecution" style="color:#005fc0;">${row.subExecutionName} </a>
			</display:column>
			<display:column  property="phaseName" />
			<display:column  style="background:green;" property="pass" />
			<display:column  style="background:red;" property="fail" />
			<display:column  style="background:white;" property="notRun" />
			<display:column  style="background:yellow;" property="block" />
			<display:column  property="passrate" />
			<display:column  property="totalCases" />
			<display:column  property="dueDate" />
			<display:column  property="testerName" />
			<display:column>
			<a href="javascript:toUpdateSubExecution(${row.subExecutionId });" style="color:#0059fd;">Update </a>
			<!-- 	<a href="javascript:updateTestExecution(${row.executionId });" style="color:#0059fd;">Run </a>&nbsp;&nbsp;&nbsp;
				<a href="exportTestPlans.action?testPlanId=${row.testPlanId }" style="color:#0059fd;">Export Update</a>&nbsp;&nbsp;&nbsp;
			-->	
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
			<div style="width:100%;height:30px;border:1px gray dotted;">
				<div style="float:left;width:45%;height:25px;">
				<!-- &nbsp;<input type="button" class="btn" value="Delete" onclick="delTestExecution();"/> !-->
				<input type="button" class="btn" value="Add Sub Execution" onclick="createSubExecution('${executionId}');"/>
			</div>
				<div style="float:right;width:45%;height:25px;">
				<p:page	action="listTestPlans.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				</p:page>
			</div>
		</div>
		</form>
	</body>
		
<script type="text/javascript">
	function createSubExecution(id) {
		MyWindow.OpenCenterWindowScroll("toAddSubExecution.action?executionId=" + id,'detail',600,800);
	}	
	function toUpdateSubExecution(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateSubExecution.action?subExecutionId=" + id,'detail',600,800);
	}	
	</script>
	

	
	
	
	
	</html>