
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test SubPlan List</title>
		<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="testplanform" id="testplanform" action="ListResultBySubplanId.action">
		<display:table name="testCasetList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="ListResultBySubplanId.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
		  		<th style="width: 32px;" title="Select All"><input type="checkbox" onclick="selectAllSubTestPlan(this);"></th>
				<th style="width: 32px;" >TestCasealiasId</th>
				<th style="width: 120px;">TestCaseName</th>		
				<th style="width: 120px;">Result Type</th>	
				<th style="width: 120px;">DugId</th>	
				<th style="width: 120px;">Log</th>			
				<th style="width: 125px;">Operation</th>
			</tr>
			</thead></display:caption>
			
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
				<display:column>
				<input type="checkbox" name="multiSubPlan" value="${row.testCasealiasId }" />
			</display:column>
			<display:column property="testCasealiasId"/>
			<display:column  property="testCaseName"/>
			<display:column  property="resuleType"/>
			<display:column  property="testCaseName"/>
			<display:column  property="resuleType"/>
			
			<display:column>
			<a href="javascript:toRunMediaConfiguration();" style="color:#0059fd;">Run</a>&nbsp;&nbsp;
				
			</display:column>
		<%-- 	<display:column  property=""/>
			<display:column  property=""/> --%>
			<display:setProperty name="basic.show.header" value="false"/></display:table>
		<div style="float:right;width:45%;height:25px;">
				<p:page	action="ListResultBySubplanId.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
					<p:param name="testcaseid" value="#testcaseid"></p:param>
				</p:page>
			</div>
	
		
		</form>
	</body>
	
	
</html>