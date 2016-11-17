<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>scripts/testcase.js"></script>
<html>
	<head>
		<title>Test Case</title>
	</head>

	
	<body class="mobile" > 
		<form method="post" name="casesuiteform" id="casesuiteform">
		<display:table name="testCaseList" class="mobile" id="row" style="width:100%;">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 3%;" >No.</th>
				<th style="width: 3%;" title="Select All"><input type="checkbox" onclick="selectAllTestCases(this);"/></th>
				<th style="width: 24%;">用例名称 </th>
				<th style="width: 10%;">项目</th>
				<th style="width: 10%;">功能模块</th>
				<th style="width: 10%;">子模块</th>
				<th style="width: 10%;">用例类型</th>
				<th style="width: 10%;">自动化</th>
				<th style="width: 10%;">功能点</th>
				<th style="width: 10%;">操作</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiCaseId"  value="${row.testCaseId }" />
			</display:column>
			<display:column sortable="true" >
			<a href="javascript:showTestCaseClick(${row.testCaseId });" style="color:#0059fd;" title="${row.testCaseName }">${row.testCaseName }</a>
			</display:column>
			<display:column  property="projectName" />
			<display:column  property="featureName" />
			<display:column  property="compName" />
			<display:column  property="testTypeName" />
			<display:column  property="autoName" />
			<display:column  property="subCompName" />
			<display:column>
				<a href="javascript:selectTestCaseClick(${row.testCaseId },${currPage });" style="color:#0059fd;">编辑</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div class="fixTitle1" style="width:90%;padding-left:7px;padding-top:2px;padding-bottom:2px;font-size:11px;color:#9ACD32">
			当前搜索条件 : 
			<s:if test="#session.projectName != null"> ${session.projectName} </s:if>
			<s:if test="#session.featureName != null"> | ${session.featureName}  </s:if>
			<s:if test="#session.compName != null"> | ${session.compName} </s:if>
			<s:if test="#session.testTypeName != null"> | ${session.testTypeName} </s:if>
			<s:if test="#session.autoName != null"> | ${session.autoName} </s:if>
			<s:if test="#session.caseName != ''"> | ${session.caseName} </s:if>
		</div>
		<div style="width:100%;height:30px;border:1px gray dotted;">
			<div style="float:left;width:60%;height:25px;">&nbsp;
				<input type="button" class="btn" value="删除" onclick="delTestCases();"/>
				<!--  <input type="button" class="btn" value="导出(选中)"  onclick="exportTestCase();"/>-->
				<input type="button" class="btn" value="导出(搜索条件)"  onclick="exportSearchedCase('${session.projectName}');"/>
				<input type="button" class="btn" value="导入用例" onClick="importTestCase('${session.projectId}');" />
				<input type="button" class="btn" value="添加用例" onClick="addTestCase();" />&nbsp;&nbsp;
			</div>
			<div style="float:right;width:40%;height:25px;">
				<p:page	action="listTestCases.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"/>
				<p:param name="featureId" value="#featureId"/>
				<p:param name="compId" value="#compId"/>
				<p:param name="testTypeId" value="#testTypeId"/>
				<p:param name="osId" value="#osId"/>
				<p:param name="autoId" value="#autoId"/>
				<p:param name="versionId" value="#ver"/>
				<p:param name="caseName" value="#caseName"/>
				<p:param name="selectedIds" value="#selectedIds"/>
				</p:page>
			</div>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function updateLinks(ids) {
		var pageUL = document.getElementById("pagination-digg");
		//var pageUL = form.pagination-digg;
		var tagAs = pageUL.getElementsByTagName("a");
		for (var index = 0; index < tagAs.length; index++) {
			var a = tagAs[index];
			var link = a.href;
			
			link = link.replace(/selectedIds=[^&]{0,}/,"selectedIds=".concat(ids));
			a.href=link;
		}
	}
	/** 
	function multiCaseId_clicked(object) {
		var self = object;
		var form = document.getElementById("casesuiteform");
		var chkbox_selectedAll = form.chk_selectedAll;
		var selectedIds = form.selectedIds;
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
    	var chk_list = document.getElementsByName("multiCaseId");

    	for(var index = 0; index < chk_list.length; index++) {
    		var chkbox = chk_list[index];
    		var id = chkbox.value;

    		if (ids.indexOf("," + id + ",") > 0)
    			chkbox.checked = true;
    		else if (ids.indexOf(id + ",") === 0)
    			chkbox.checked = true;
    	}
    	
    })("${selectedIds}");
    **/
	function selectTestCaseClick(id,currPage) {
		MyWindow.OpenCenterWindowScroll("toUpdateTestCase.action?testCaseId=" + id+"&currPage="+currPage,'detail',600,800);
	}
	
	function showTestCaseClick(id) {
		//MyWindow.winOpenFullScreen("showTestCase.action?testCaseId=" + id,'unitDetail');
		MyWindow.OpenCenterWindowScroll("showTestCase.action?testCaseId=" + id,'detail',600,1100);
	}
	
	function exportSearchedCase(projectName) {
		//var project = window.parent.document.getElementById("projectId").value;
		if(projectName.trim() == '') {
			alert("Search session is too large, please at least choose a project for export");
		} else {	
			document.location.href="exportSearchedCase.action";
		}
	}
 	function importTestCase(projectId) {
			MyWindow.OpenCenterWindowScroll('toImportTestCase.action','dataRemark',700,1050);
 	}
	function addTestCase() {
 		MyWindow.OpenCenterWindowScroll('toAddTestCase.action','dataRemark',600,800);
 	}
	</script>
</html>