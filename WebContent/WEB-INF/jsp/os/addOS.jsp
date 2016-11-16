<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath %>scripts/os.js"></script>

<title>OS</title>

</head>
<body>

<div id="div_main"><label class="statictittle">Add OS</label>
<hr />
<form action="addOS.action" method="post" onsubmit="return checkTCOs();">

<table id="common_table" class="form_table">

	<tr>
		<td class="alignr">OS Name:</td>
		<td class="alignl"><input type="text" name="osName" id="osName" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="add" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>