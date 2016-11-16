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
	<body class="mobile">
		<form method="post" name="testexecutionForm" id="testexecutionForm">
		<display:table name="testexecutionlists" class="mobile" id="row" style="width:100%;">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 2%;" >No.</th>
				<th style="width: 2%;" title="Select All"><input type="checkbox" onclick="selectAllTestExecution(this);"></th>
				<th style="width: 12%;">Execution Name</th>
		<!-- 	<th style="width: 100px;">TestPlan Name</th> -->			
				<th style="width: 10%;">Release Cycle</th>
				<th style="width: 10%;">Execution Type</th>
				<th style="width: 8%;">Build Type</th>
				<th style="width: 5%;">Pass</th>
				<th style="width: 5%;">Fail</th>
				<th style="width: 5%;">Not Run</th>
				<th style="width: 5%;">Block</th>
				<th style="width: 8%;">Total Cases</th>
				<th style="width: 8%;">Pass Rate</th>
				<th style="width: 10%;">Executed Rate</th>
				<th style="width: 10%;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestExecution" value="${row.id }" />
			</display:column>		
			<display:column> 
			<a href="javascript:detailTestExecution(${row.id });" style="color:#005fc0;cursor: pointer;" title="${row.executionName }">${row.executionName}</a>
			</display:column>
			<display:column>${row.releaseCycle} </display:column>		
			<display:column>${row.phaseName}</display:column>
			<display:column>${row.buildTypeName}</display:column>
			<display:column  style="background:green;" property="pass" />
			<display:column  style="background:red;" property="fail" />
			<display:column  style="background:white;" property="notRun" />
			<display:column  style="background:yellow;" property="block" />
			<display:column  property="totalCases" />
			<display:column>
				<fmt:formatNumber value="${row.passrate*100}" maxFractionDigits="0"/>% 
			</display:column>
			<display:column><fmt:formatNumber value="${row.executeRate*100}" maxFractionDigits="0"/>%</display:column>
			<display:column>
				<a href="javascript:updateTestExecution(${row.id });" style="color:#0059fd;">Update </a>&nbsp;
				<a href="javascript:exportTestExecutionToExcel(${row.id });" style="color:#0059fd;">Export</a>&nbsp;
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
			<div style="width:100%;height:30px;border:1px gray dotted;">
				<div style="float:left;width:45%;height:25px;">
			 	<!-- &nbsp;<input type="button" class="btn" value="Delete" onclick="inDevelop();"/> -->
			 	  &nbsp;<input type="button" class="btn" value="Delete" onclick="delTestExecution();"/> 
		
				&nbsp;<input type="button" class="btn" value="Create Test Execution" onclick="createTestExecution();"/>
			 	<input type="button" class="btn" value="Copy Test Execution" onclick="copyTestExecution();"/> 
			
			</div>
				<div style="float:right;width:45%;height:25px;">
				<p:page	action="listTestTextExecution.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"/>
				<p:param name="releaseCycle" value="#releaseCycle"/>
				<p:param name="osId" value="#osId"/>
				<p:param name="platformId" value="#platformId"/>
				<p:param name="executionName" value="#executionName"/>
				<p:param name="buildId" value="#buildId"/>
				<p:param name="phaseId" value="#phaseId"/>
				</p:page>
			</div>
		</div>
		</form>
	</body>
		
<script type="text/javascript">
function detailTestExecution(id) {
	MyWindow.winOpenFullScreen("toDetailTestExecution.action?executionId=" + id,'detail');
}
	function createTestExecution() {
		MyWindow.OpenCenterWindowScroll("toAddTestExecution.action",'detail',600,800);
	}
	function updateTestExecution(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateExecution.action?executionId=" + id,'detail',600,800);
	}
	
	function inDevelop() {
		alert("Feature in in developing, not supported yet");
	}

	function copyTestExecution() {
		var checkboxs = $('input:checkbox:checked');
		
		if(checkboxs.length <= 0)
		{
			alert("Please choose one test execution for copy!");
			return;
		}
		else if(checkboxs.length != 1)
		{
			alert("Only support copy single test execution!");
			return;
		}
		//document.testexecutionForm.action = "copyTestExecution.action";
		//document.testexecutionForm.submit();
        var cboxes = document.getElementsByName('multiTestExecution');
        var len = cboxes.length;
     
        for (var i=0; i<len; i++) {
        	if(cboxes[i].checked) {
        		var id = cboxes[i].value;
        		//alert(id);
        	}
        }
        //alert(id);
		MyWindow.OpenCenterWindowScroll("copyTestExecution.action?multiTestExecution="+id,'detail',700,900);
	}
	
	function exportTestExecutionToExcel(id) {
		document.testexecutionForm.action = "exportTestExecutionToExcel.action?executionId="+id;
		document.testexecutionForm.submit();
	}
	
	</script>
	

	
	
	
	
	</html>