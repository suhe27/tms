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
		<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
		<script type="text/javascript">
				function action(nodeid,title,url){
					window.parent.parent.parent.parent.frames.right.addTab(nodeid,url,title);
				}
		</script>
	</head>
	<body class="mobile">
		<h6  align="center">${subExecution.subExecutionName}</h6>
		<div style="width:100%;height:25px;text-align:right">
				&nbsp;<input type="button" class="btn" value="Export performance result" onclick="exportPerformanceResult('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Import performance result" onclick="importPerformanceResultByExcel('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Export selected case to XML" onclick="exportSelectedToXML('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Run selected case" onclick="runSelectedCase(${subExecution.subExecutionId});"/>
				<input type="button" class="btn" value="Bulk edit result" onclick="bulkEditResult(${subExecution.subExecutionId});"/>
				&nbsp;
		</div>
		<form method="post" name="testexecutionForm" id="testexecutionForm">
		<display:table name="testresultlists" class="mobile" id="row" style="width:100%;">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 2%;" >No.</th>
		  		<th style="width: 3%;" title="Select All"><input type="checkbox" onclick="selectAllTestExecution(this);"></th>
				<th style="width: 21%;">Test Case Name</th>
				<th style="width: 5%;">Result</th>
				<th style="width: 15%;">Log</th>
				<th style="width: 10%;">Bug Id</th>
				<th style="width: 10%;">Comments</th>
				<th style="width: 10%;">Target</th>
				<th style="width: 7%;">Platform</th>
				<th style="width: 7%;">ExecutionOS</th>
				<th style="width: 10%;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestExecution" value="${row.resultId }" />
			</display:column>
			<display:column  property="testCaseName"/>
			<s:if test="#attr.row.resultTypeId == 3">
					<display:column style="background:green;">${row.resultTypeName}</display:column>
			</s:if>
			<s:if test="#attr.row.resultTypeId == 4">
						<display:column style="background:yellow;">${row.resultTypeName}</display:column>
			</s:if>
			<s:if test="#attr.row.resultTypeId == 2">
						<display:column style="background:red;">${row.resultTypeName}</display:column>
			</s:if>
			<s:if test="#attr.row.resultTypeId == 1">
						<display:column style="background:none;">${row.resultTypeName}</display:column>
			</s:if>
			<s:if test="#attr.row.resultTypeId == ''">
						<display:column style="background:none;" value="Not Run" />
			</s:if>
			
			<display:column property="log"/>
			<display:column  >
				<a href= "javascript:toOpenBugWindow('${row.bugId}');" style="text-decoration:underline;color:#0059fd;">
					<s:if test="#attr.row.bugName == null ">${row.bugId}</s:if>
					<s:else>${row.bugName}</s:else>
				</a>
			</display:column>
			<display:column  property="comments" />
			<display:column  property="targetName" />
			<display:column  property="platformName" />
			<display:column  property="osName" />
			<display:column>
				<a href="javascript:updateTestCase(${row.resultId});" style="color:#0059fd;">Update </a>&nbsp;
				<a href="javascript:executionHistory(${row.resultId});" style="color:#0059fd;">History </a>&nbsp;
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:30px;border:1px gray dotted;">
			<div style="float:left;width:60%;height:25px;">
				&nbsp;<input type="button" class="btn" value="Export to Excel" onclick="exportByExcel('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Import by Excel" onclick="importByExcel('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Export to XML" onclick="exportByXML('${subExecution.subExecutionId}');"/>
				<input type="button" class="btn" value="Import by XML" onclick="importByXML('${subExecution.subExecutionId}');"/>
				<!-- 
				<input type="button" class="btn" value="Performance Report" onclick="perfReport('${subExecution.subExecutionId}','${subExecution.subExecutionName}');"/>
				-->
			</div>
			<div style="float:right;width:38%;height:25px;">
				<p:page	action="toDetailSubExecution.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="subExecutionId" value="#subExecutionId"/>
				</p:page>
			</div>
		</div>
		</form>
	</body>	
	
	<script type="text/javascript">
 	function exportPerformanceResult(id) {
 		MyWindow.OpenCenterWindowScroll("exportPerformanceResultToExcel.action?subExecutionId=" + id,'dataRemark',350,700);
 	}
 	function importPerformanceResultByExcel(id) {
 		MyWindow.OpenCenterWindowScroll("toImportPerformanceResultByExcel.action?subExecutionId=" + id,'dataRemark',350,700);
 	}
	function perfReport(id,name) {
		action('300'+id,'Perf-'+name,'<%=basePath%>listPerformanceResultBySubExecutionId.action?subExecutionId='+id);
	}
	function exportByExcel(id) {
		document.testexecutionForm.action = "exportSubExecutionToExcel.action?subExecutionId=" + id;
		document.testexecutionForm.submit();
	}
	function exportByXML(id) {
		//alert("Feature in developing");
		document.testexecutionForm.action = "exportSubExecutionToXML.action?subExecutionId=" + id;
		document.testexecutionForm.submit();
	}
	function exportSelectedToXML(subId) {
		var checkboxs = $('input:checkbox:checked');
		
		if(checkboxs.length <= 0)
		{
			alert("Please select test cases to export!");
			return;
		}

        var cboxes = document.getElementsByName('multiTestExecution');
        var len = cboxes.length;
     
        for (var i=0; i<len; i++) {
        	if(cboxes[i].checked) {
        		if(i == 0) {
        			var id = cboxes[i].value;
        		}
        		else {
        			var id = id +','+cboxes[i].value;
        		}
        	}
        }
		MyWindow.OpenCenterWindowScroll("exportSelectedCaseToXML.action?subExecutionId="+subId+"&multiTestExecution=" + id,'detail',700,900);
	}
	function runSelectedCase(subId) {
		var checkboxs = $('input:checkbox:checked');
		
		if(checkboxs.length <= 0)
		{
			alert("Please select test cases to run!");
			return;
		}

        var cboxes = document.getElementsByName('multiTestExecution');
        var len = cboxes.length;
        var j = 0;
        for (var i=0; i<len; i++) {
        	if(cboxes[i].checked) {
        		if(j == 0) {
        			var id = cboxes[i].value;
        		}
        		else {
        			var id = id +','+cboxes[i].value;
        		}
        	 j=j+1;	
        	}
        }
	//	MyWindow.OpenCenterWindowScroll("toRunSelectedCase.action?subExecutionId="+subId+"&multiTestExecution="+id,'detail',700,900);
        MyWindow.OpenCenterWindowScroll("toRunSelectedCase.action?subExecutionId="+subId+"&multiTestExecution="+id,'dataRemark',300,400);
        
	}
 	function importByExcel(id) {
 		MyWindow.OpenCenterWindowScroll("toImportTestResult.action?subExecutionId=" + id,'dataRemark',350,700);
 	}
 	function importByXML(id) {
 		MyWindow.OpenCenterWindowScroll("toImportTestResultByXML.action?subExecutionId=" + id,'dataRemark',350,700);
 	}
 	function toOpenBugWindow(url) {
 		window.open(url);
 	}
 	function updateTestCase(id) {
 		MyWindow.OpenCenterWindowScroll("toUpdateSingleTestResult.action?resultId=" + id,'dataRemark',350,700);
 	}
 	function executionHistory(id) {
 		MyWindow.OpenCenterWindowScroll("testCaseExecutionHistory.action?resultId=" + id,'dataRemark',700,700);
 	}
 	function bulkEditResult(id) {
 		//MyWindow.OpenCenterWindowScroll("toEditCaseListOfSubExecution.action?subExecutionId=" + id,'dataRemark',350,700);
 		window.location.href = "toEditCaseListOfSubExecution.action?subExecutionId=" + id;
 	}
	</script>

	</html>