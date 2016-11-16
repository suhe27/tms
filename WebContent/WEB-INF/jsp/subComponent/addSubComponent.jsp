<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=basePath %>scripts/subcomponent.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sub Component</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>

</head>
<body>


<form action="addSubComponent.action" method="post" onsubmit="return checkSubComponent();">
<table id="common_table"  class="form_table">
	<tr>
		<td width="180px">SubComponent Name:</td>
		<td class="alignl"><input type="text" name="compName"
			id="compName" /></td>
	</tr>

</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td><input type="submit" value="add" class="btn1" /></input></td>
	</tr>
</table>
</form>

</body>
</html>