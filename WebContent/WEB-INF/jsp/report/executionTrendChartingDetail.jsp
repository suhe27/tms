<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>Execution</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
	<h6  align="center">Execution Trend</h6>
	<div align="center">
		<table  class="mobile" id="row" style="width:100%;">
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