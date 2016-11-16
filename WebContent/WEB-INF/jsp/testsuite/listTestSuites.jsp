<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Suite</title>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="testsuiteform" id="testsuiteform">
		<input type="hidden" name="currPage" value="${currPage}"></input>
		<input type="hidden" name="project" value="${project}"></input>
		<input type="hidden" name="isSelectedAllSuite" id="isSelectedAllSuite" value="${isSelectedAllSuite}"></input>
		<input type="hidden" name="selectedSuiteIds" id="selectedSuiteIds" value="${selectedSuiteIds}"></input>
		<display:table name="testSuiteList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="listTestSuites.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 5%;" >No.</th>
				<th style="width: 5%;" title="Select All"><input type="checkbox" id="chk_selectedAllSuite" onclick="selectTestSuitesCurrentPage(this);"></th>
				<th style="width: 15%;">Test Suite Name</th>
				<th style="width: 15%;">Project</th>
				<th style="width: 20%;">Features Name</th>
				<th style="width: 8%;">OS Name</th>
				<th style="width: 7%;">Owner</th>
				<th style="width: 10%;">Total Cases</th>
				<th style="width: 15%;">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiSuiteId" onclick="multiSuiteId_clicked(this)" value="${row.testSuiteId }" />
			</display:column>
			<display:column>
				<a href="javascript:selectTestSuiteClick(${row.testSuiteId });" style="color:#0059fd;" title="${row.testSuiteName }">
				${row.testSuiteName }
				</a>
			</display:column>
		<display:column>
		<span id="project_${row_rowNum}">${row.project }</span>
		</display:column>


			<display:column  property="features" />
			<display:column  property="oses" />
			<display:column  property="user.userName" />
			<display:column  property="totalCases" />
			<display:column>
				<a href="javascript:updateTestSuiteClick('${row.testSuiteId }');" style="color:#0059fd;">Update</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:preAddTestCaseToSuite(${row.testSuiteId });" style="color:#0059fd;">Add Test Case</a>
				<!--<a href="javascript:selectTestSuiteClick(${row.testSuiteId });" style="color:#0059fd;">Detail</a>
			--></display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;border:1px gray dotted;">
			<div style="float:left;width:45%;height:25px;">
				&nbsp;<input type="button" class="btn" value="Delete" onclick="delTestSuite();"/>
				&nbsp;<input type="button" class="btn" value="Copy Test Suite" onclick="copyTestSuite();"/>
				<input type="button" class="btn" value="Create Test Suite" onclick="javascript:createTestSuite();"/>
			</div>
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="listTestSuites.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"></p:param>
				<p:param name="isSelectedAllSuite" value="#isSelectedAllSuite"></p:param>
				<p:param name="selectedSuiteIds" value="#selectedSuiteIds"></p:param>
				</p:page>
			</div>
		</div>
		</form>
		
	</body>
	<script type="text/javascript">
	function updateLinks(ids) {
		var form = document.getElementById("testsuiteform");
		var pageUL = document.getElementById("pagination-digg");
		//var pageUL = form.pagination-digg;
		var tagAs = pageUL.getElementsByTagName("a");
		for (var index = 0; index < tagAs.length; index++) {
			var a = tagAs[index];
			var link = a.href;
			
			link = link.replace(/selectedSuiteIds=[^&]{0,}/,"selectedSuiteIds=".concat(ids));
			a.href=link;
		}
	}
	
	function createTestSuite() {
		MyWindow.OpenCenterWindowScroll("toAddTestSuite.action",'detail',600,800);
	}
	
	function multiSuiteId_clicked(object) {
		var self = object;
		var form = document.getElementById("testsuiteform");
		var chkbox_selectedAll = form.chk_selectedAllSuite;
		var selectedIds = form.selectedSuiteIds;
		// if selected all, skip update links
		if (chkbox_selectedAll && chkbox_selectedAll.checked)
			return;
		var ids = selectedIds.value;
		if (self.checked) {
			ids = ids.concat(self.value,",");
		} else {
			if (ids.indexOf("," + self.value + ",") > 0) {
				ids = ids.replace("," + self.value + ",",",");
			} else if (ids.indexOf(self.value + ",") === 0) {
				ids = ids.replace(self.value + ",","");
			}
		}
		selectedIds.value = ids;
		updateLinks(ids);
	}
	
    (function (ids){
    	if (!ids)
    		return;
    	//var form = document.getElementById("casesuiteform");
    	var chk_list = document.getElementsByName("multiSuiteId");

    	for(var index = 0; index < chk_list.length; index++) {
    		var chkbox = chk_list[index];
    		var id = chkbox.value;

    		if (ids.indexOf("," + id + ",") > 0)
    			chkbox.checked = true;
    		else if (ids.indexOf(id + ",") === 0)
    			chkbox.checked = true;
    	}
    	
    })("${selectedSuiteIds}");
	function updateTestSuiteClick(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateTestSuite.action?testSuiteId=" + id,'detail',400,800);
	}
	function selectTestSuiteClick(id) {
		MyWindow.OpenCenterWindowScroll("toDetailTestSuite.action?testSuiteId=" + id,'detail',700,900);
	}
	function preAddTestCaseToSuite (id) {
		MyWindow.OpenCenterWindowScroll("preAddTestCaseToSuite.action?testSuiteId=" + id,'detail',700,1100);
	}
	</script>
</html>