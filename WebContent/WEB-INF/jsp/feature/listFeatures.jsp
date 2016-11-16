<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Features</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body class="mobile">

	<display:table name="featureList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listFeatures.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 10%">Feature Name</th>
					<th class="fixTitle" style="width: 10%">Project Name</th>
					<th class="fixTitle" style="width: 65%">Component List</th>
					<th style="width: 10%" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="featureName" title="日期" class="fixTitle1"/>
			<display:column  property="projectName" title="日期" class="fixTitle1"/>
			<display:column  property="compNames" title="日期" class="fixTitle1"/>
		
			<display:column  title="备注" >
				<a class="j_title"
				href="javascript:deleteFeature(${row.featureId },'${row.featureName }')">Delete</a>
			<a class="j_title"
				href="javascript:updateFeature(${row.featureId })">Update</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
<div style="float: left; width: 45%; height: 25px;">&nbsp;<input
	type="button" value=" add " class="btn1" onclick="addFeature();"
></input>
</div>

</div>

</body>
<script type="text/javascript">
	function deleteFeature(id, name) {
		if (confirm("Confirm delete Feature '" + name + "'")) {
			window.location.href = "delFeature.action?featureId=" + id;
		}
	}
	function addFeature() {
		MyWindow.OpenCenterWindowScroll('toAddFeature.action', 'detail', 600,
				800);
		
			
	}

	function updateFeature(id) {
		MyWindow.OpenCenterWindowScroll(
				'toUpdateFeature.action?featureId=' + id, 'detail', 600, 800);
	}
</script>
</html>