<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>${testsuite.testSuiteName }</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form action="delTestCaseInTestSuite.action" method="post" name="testsuiteform">
		<input	type="hidden" name="testsuite.testSuiteId" id="testSuiteId"	value="${testsuite.testSuiteId} " />
		<input type="hidden" name="testSuiteId"	value="${testSuiteId} " />
		<display:table name="testCaseList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="listTestSuites.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
				
				<th style="width: 100px;">Test Case Id</th>
				<th style="width: 200px;">Test Case Name</th>
				<th style="width: 100px;">Project</th>
				<th style="width: 50px;">State</th>
				<th style="width: 50px;">Version</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			
			<display:column>
			<a href="javascript:showTestCaseClick(${row.testCaseId });" style="color:#0059fd;">${row.testCasealiasId }</a>	
			</display:column>
			<display:column  property="testCaseName" style="text-align:left;"/>
			<display:column  property="project.projectName" />
			<display:column  property="caseState.caseStateName" />
			<display:column  property="versionState.versionName" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;border:1px gray dotted;">
			
			<div style="float:right;width:45%;height:25px;">
				<p:page action="toDetailTestSuite.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
					<p:param name="testSuiteId" value="#testSuiteId"></p:param>
				</p:page>
			</div>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function updateTestSuiteClick(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateTestSuite.action?testCaseId=" + id,'detail',600,800);
	}
	function selectTestSuiteClick(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateTestSuite.action?testSuiteId=" + id,'detail',700,900);
	}
	function showTestCaseClick(id) {
		MyWindow.winOpenFullScreen("showTestCase.action?testCaseId=" + id,'unitDetail');
	}
	</script>
</html>