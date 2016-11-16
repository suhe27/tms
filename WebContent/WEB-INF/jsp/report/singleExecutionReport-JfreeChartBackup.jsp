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
		<style type="text/css">
			.thead {	font: bold;}
		</style>
	</head>
	<body class="mobile">
		<h6  align="center">${testExecution.executionName}</h6>
		<div align="center">
		<table class="mobile" style="width:90%;">
			<thead>
			<tr  class="pageHeader">
		  		<th colspan="3">Summary</th>
			</tr>
			</thead>
			<tbody>
			<tr class="odd">
				<td class="thead" style="width:25%;">Execution Name</td>
				<td style="width:25%;">${testExecution.executionName}</td>
				<td rowspan="13" style="width:50%;"><img alt="jfreechart" src="testExecutionPie.action?executionId=${testExecution.id}"/></td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Release Cycle</td>
				<td style="width:25%;">${testExecution.releaseCycle}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Test Plan</td>
				<td style="width:25%;">${testExecution.testPlanName}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Execution Type</td>
				<td style="width:25%;">${testExecution.phaseName}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Build Type</td>
				<td style="width:25%;">${testExecution.buildTypeName}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Pass</td>
				<td style="width:25%;">${testExecution.pass}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Fail</td>
				<td style="width:25%;">${testExecution.fail}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Block</td>
				<td style="width:25%;">${testExecution.block}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Not Run</td>
				<td style="width:25%;">${testExecution.notRun}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Total Cases</td>
				<td style="width:25%;">${testExecution.totalCases}</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Pass Rate</td>
				<td style="width:25%;"><fmt:formatNumber value="${testExecution.passrate*100}" maxFractionDigits="0"/>%</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Executed Rate</td>
				<td style="width:25%;"><fmt:formatNumber value="${testExecution.executeRate*100}" maxFractionDigits="0"/>%</td>
			</tr>
			<tr class="odd">
				<td class="thead" style="width:25%;">Description</td>
				<td style="width:25%;">${testExecution.desc}</td>
			</tr>
			</tbody>
		</table>
		<!-- Execution bar chart summary -->
		<br>
		<table class="mobile" style="width:90%;">
			<thead>
			<tr  class="pageHeader">
		  		<th colspan="3">Execution summary chart</th>
			</tr>
			</thead>
			<tbody>
			<tr class="odd">
				<td><img alt="jfreechart" src="testExecutionBar.action?executionId=${testExecution.id}"/></td>
			</tr>
			</tbody>
		</table>
		<!-- Execution status in line chart -->
		<br>
		<table class="mobile" style="width:90%;">
			<thead>
			<tr  class="pageHeader">
		  		<th colspan="3">Execution status</th>
			</tr>
			</thead>
			<tbody>
			<tr class="odd">
				<td><img alt="jfreechart" src="executeStatusLine.action?executionId=${testExecution.id}"/></td>
			</tr>
			</tbody>
		</table>		
		<!-- Sub execution list in table -->
		<br>
		<display:table name="testexecutionlists" class="mobile" id="row" style="width:90%;" cellpadding="0" cellspacing="0">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 10%;">Sub Execution Name</th>
				<th style="width: 10%;">Sub Plan Name</th>
				<th style="width: 10%;">Platform</th>
				<th style="width: 10%;">ExecutionOS</th>
				<th style="width: 5%;">Pass</th>
				<th style="width: 5%;">Fail</th>
				<th style="width: 5%;">Not Run</th>
				<th style="width: 5%;">Block</th>
				<th style="width: 10%;">Total Cases</th>
				<th style="width: 10%;">Pass Rate</th>
				<th style="width: 10%;">Executed Rate</th>
				<th style="width: 10%;">DueDate</th>
			</tr>
			</thead></display:caption>		
			<display:column  property="subExecutionName" />
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
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>		
		<br>
		<!-- Failed test case in execution -->
		<display:table name="testresultlists" class="mobile" id="row" style="width:90%;" cellpadding="0" cellspacing="0">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 10%;">Sub Execution Name</th>
				<th style="width: 15%;">Test Case Name</th>
				<th style="width: 10%;">Result</th>
				<th style="width: 10%;">Log</th>
				<th style="width: 15%;">Bug Id</th>
				<th style="width: 10%;">Comments</th>
				<th style="width: 10%;">Target</th>
				<th style="width: 10%;">Platform</th>
				<th style="width: 10%;">ExecutionOS</th>
			</tr>
			</thead></display:caption>
			<display:column  property="subExecutionName" />
			<display:column  property="testCaseName" />
			<display:column style="background:red;" property="resultTypeName" />
			<display:column  property="log" />
			<display:column  >
				<a href= "javascript:toOpenBugWindow('${row.bugId}');" style="text-decoration:underline;color:#0059fd;">${row.bugId}</a>
			</display:column>
			<display:column  property="comments" />
			<display:column  property="targetName" />
			<display:column  property="platformName" />
			<display:column  property="osName" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<br>
		</div>
		<br>
	</body>
			
	</html>
