
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
<title>Test Type</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body class="mobile">

	<display:table name="testTypeList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listTestTypes.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 35%">TestType Name</th>
					<th class="fixTitle" style="width: 30%">Project Name</th>
					<th style="width: 30%" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="typeName" title="日期" class="fixTitle1"/>
			<display:column  class="fixTitle1">${row.projectName}</display:column>
		
			<display:column  title="备注" >
				<a class="j_title"
				href="javascript:deleteTestType(${row.typeId },'${row.typeName }')">Delete</a>
			<a class="j_title"
				href="javascript:updateTestType(${row.typeId})">Update</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
<div style="float: left; width: 45%; height: 25px;">&nbsp;<input
	type="button" value=" add " class="btn1" onclick="addTestType();"
></input>
</div>

</div>

</body>
<script type="text/javascript">
	function deleteTestType(id, name) {
		if (confirm("Confirm delete TestType '" + name + "'")) {
			window.location.href = "delTestType.action?typeId=" + id;
		}
	}
	function addTestType() {
		MyWindow.OpenCenterWindowScroll('toAddTestType.action', 'detail', 400,
				800);
		
			
	}

	function updateTestType(id) {
		MyWindow.OpenCenterWindowScroll(
				'toUpdateTestType.action?typeId=' + id, 'detail', 400, 800);
	}
</script>
</html>
