<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>Execution</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
		<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
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
				<th style="width: 115px;">Sub Execution Name</th>
				<th style="width: 110px;">Sub Plan Name</th>
				<th style="width: 100px;">Platform</th>
				<th style="width: 100px;">ExecutionOS</th>
				<th style="width: 50px;">Pass</th>
				<th style="width: 50px;">Fail</th>
				<th style="width: 70px;">Not Run</th>
				<th style="width: 50px;">Block</th>
				<th style="width: 70px;">Total Cases</th>
				<th style="width: 70px;">Pass Rate</th>
				<th style="width: 85px;">Executed Rate</th>
				<th style="width: 85px;">DueDate</th>
				<th style="width: 100px;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestExecution" value="${row.subExecutionId }" />
			</display:column>			
			<display:column> 
			<a href="javascript:toDetailSubExecution(${row.subExecutionId });" style="color:#005fc0;cursor: pointer;">${row.subExecutionName}</a>
			</display:column>
			<display:column  property="subPlanName" />
			<display:column  property="platFormName" />
			<display:column  property="osName" />
			<display:column  style="background:green;" property="pass" />
			<display:column  style="background:red;" property="fail" />
			<display:column  style="background:white;" property="notRun" />
			<display:column  style="background:yellow;" property="block" />
			<display:column  property="totalCases" />
			<display:column>
				<fmt:formatNumber value="${row.passrate*100}" maxFractionDigits="0"/>% 
			</display:column>
			<display:column><fmt:formatNumber value="${row.executeRate*100}" maxFractionDigits="0"/>%</display:column>
			<display:column  property="dueDate" />
			<display:column>
			<a href="javascript:toUpdateSubExecution(${row.subExecutionId});" style="color:#0059fd;">Update </a>&nbsp;
			<a href="javascript:runSubExecution(${row.subExecutionId});" style="color:#0059fd;">Run </a>&nbsp;
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
			<div style="width:100%;height:30px;border:1px gray dotted;">
				<div style="float:left;width:45%;height:25px;">
				 &nbsp;<input type="button" class="btn" value="Delete" onclick="delSubExecution();"/>
				<input type="button" class="btn" value="Add Sub Execution" onclick="createSubExecution('${executionId}');"/>
				<input type="button" class="btn" value="Assign Task" onclick="assignTask('${executionId}');"/>
				<input type="button" class="btn" value="Generate Report" onclick="report('${executionId}','${executionName}');"/>
				<!--
				<input type="button" class="btn" value="Performance Report" onclick="perfReport('${executionId}','${executionName}');"/>
				-->
			</div>
				<div style="float:right;width:45%;height:25px;">
				<p:page	action="toDetailTestExecution.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="executionId" value="#executionId"/>
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
	function runSubExecution(id) {
		MyWindow.OpenCenterWindowScroll("toRunSubExecution.action?subExecutionId=" + id,'detail',300,400);
	}
	function assignTask(id) {
		MyWindow.OpenCenterWindowScroll("assignTask.action?executionId=" + id,'detail',600,800);
	}
	function toDetailSubExecution(id) {
		MyWindow.winOpenFullScreen("toDetailSubExecution.action?subExecutionId=" + id,'detail');
		//MyWindow.OpenCenterWindowScroll("toDetailSubExecution.action?subExecutionId=" + id,'detail',800,1000);
		//window.open("toDetailSubExecution.action?subExecutionId=" + id,'detail');
	}
	function report(id,name) {
		action('100'+id,'Report-'+name,'<%=basePath%>singleExecutionReport.action?executionId='+id);
		//MyWindow.winOpenFullScreen("singleExecutionReport.action?executionId=" + id,'detail');
		//MyWindow.OpenCenterWindowScroll("toDetailSubExecution.action?subExecutionId=" + id,'detail',800,1000);
		//window.open("toDetailSubExecution.action?subExecutionId=" + id,'detail');
	}
	function perfReport(id,name) {
		action('200'+id,'Perf-'+name,'<%=basePath%>listPerformanceResult.action?executionId='+id);
		//MyWindow.winOpenFullScreen("listPerformanceResult.action?executionId=" + id,'detail');
	}
	</script>
	

	
	
	
	
	</html>