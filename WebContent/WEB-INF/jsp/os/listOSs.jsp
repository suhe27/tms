
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
<title>OS</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body class="mobile">

	<display:table name="osList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listOss.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 45%">OS Name</th>
					<th style="width: 50%" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="osName" title="日期" class="fixTitle1"/>
		
		
			<display:column  title="备注" >
				<a class="j_title"
				href="javascript:deleteOs(${row.osId },'${row.osName }')">Delete</a>
			<a class="j_title"
				href="javascript:updateOs(${row.osId })">Update</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
<div style="float: left; width: 45%; height: 25px;">&nbsp;<input
	type="button" value=" add " class="btn1" onclick="addOs();"
></input>
</div>

</div>


</body>
<script type="text/javascript">
	function deleteOs(id, name) {
		if (confirm("Confirm delete Os '" + name + "'")) {
			window.location.href = "delOS.action?osId=" + id;
		}
	}
	function addOs() {
		MyWindow.OpenCenterWindowScroll('toAddOS.action', 'detail', 400,
				800);		
	}

	function updateOs(id) {
		MyWindow.OpenCenterWindowScroll(
				'toUpdateOS.action?osId=' + id, 'detail', 400, 800);
	}
</script>
</html>
