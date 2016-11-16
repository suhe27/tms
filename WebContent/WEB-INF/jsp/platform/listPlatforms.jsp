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
<title>Platform</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body class="mobile">

	<display:table name="platformList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listPlatforms.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 35%">Platform Name</th>
					<th class="fixTitle" style="width: 30%">Project</th>
					<th style="width: 30%" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="platformName" title="日期" class="fixTitle1"/>
			<display:column  class="fixTitle1">${row.projectName}</display:column>
		
		
			<display:column  title="备注" >
				<a class="j_title"
				href="javascript:deletePlatform(${row.platformId },'${row.platformName}')">Delete</a>
			<a class="j_title"
				href="javascript:updatePlatform(${row.platformId })">Update</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
<div style="float: left; width: 45%; height: 25px;">&nbsp;<input
	type="button" value=" add " class="btn1" onclick="addPlatform();"
></input>
</div>

</div>


</body>
<script type="text/javascript">
	function deletePlatform(id, name) {
		if (confirm("Confirm delete Platform '" + name + "'")) {
			window.location.href = "delPlatform.action?platformId=" + id;
		}
	}
	function addPlatform() {
		MyWindow.OpenCenterWindowScroll('toAddPlatform.action', 'detail', 400,
				800);
		
			
	}

	function updatePlatform(id) {
		MyWindow.OpenCenterWindowScroll(
				'toUpdatePlatform.action?platformId=' + id, 'detail', 400, 800);
	}
</script>
</html>