<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/inc/includemobile2.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Component</title>
<script type="text/javascript">
	function selectComponentClick(id) {
		window.location.href = "toUpdateSubComponent.action?compId=" + id;
	}
</script>
</head>
<body class="mobile">

<display:table name="componentList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listComponents.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 20%">Component Name</th>
					<th class="fixTitle" style="width: 60%">Sub Component List</th>
					<th style="width: 15%" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="compName" title="日期" class="fixTitle1"/>
			<display:column  property="subCompNames" title="日期" class="fixTitle1"/>
		
			<display:column  title="备注" >
				<a class="j_title"
				href="javascript:deleteComponent(${row.compId },'${row.compName }')">Delete</a>
			<a class="j_title"
				href="javascript:updateComponent(${row.compId })">Update</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
<div style="float: left; width: 45%; height: 25px;">&nbsp;<input
	type="button" value=" add " class="btn1" onclick="addComponent();"
></input>
</div>

</div>

</body>
<script type="text/javascript">
	function deleteComponent(id, name) {
		if (confirm("Confirm delete Component '" + name + "'")) {
			window.location.href = "delComponent.action?compId=" + id;
		}
	}
	function addComponent() {
		MyWindow.OpenCenterWindowScroll('toAddComponent.action', 'detail', 400,
				800);
		
			
	}

	function updateComponent(id) {
		MyWindow.OpenCenterWindowScroll(
				'toUpdateComponent.action?compId=' + id, 'detail', 400, 800);
	}
</script>
</html>