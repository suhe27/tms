<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/performance.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>Execution</title>
        <script src="<%=basePath %>DataTables-1.10.4/media/js/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="<%=basePath %>DataTables-1.10.4/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="<%=basePath %>DataTables-1.10.4/media/js/jquery.dataTables.rowGrouping.js" type="text/javascript"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				$('#example').dataTable({ "bLengthChange": false, "bPaginate": false , "bInfo":false}).rowGrouping();
			} );		
		</script>
		
	</head>

<body class="mobile">
	<h3  align="center">${subExecution.subExecutionName}</h3>
	<div align="center">

<table class="mobile" id="example" style="width:95%;">
		<thead>
			<tr  class="pageHeader">
     			<c:forEach items="${head}" var="keyword" varStatus="id">
     				<th>${keyword}</th>
     			</c:forEach>
     		</tr>
		</thead>
  		<tbody>
     	<c:forEach items="${lists}" var="rows" varStatus="id">
     		<tr>
     		<c:forEach items="${rows}" var="keyword" varStatus="ids">
     			<td>${keyword}</td>
     		</c:forEach>
     		</tr>
     	</c:forEach>
		</tbody>   
</table>

</div>
</body>	
</html>

