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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OS</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/os.js"></script>
</head>
<body>

<div id="div_main"><label class="statictittle">OS Detail</label>
<hr />
<form action="updateOS.action" method="post"
	onsubmit="return checkTCOs();">
	<input type="text" name="osId" size="35" readonly="readonly" value="${os.osId}" style="display:none" />
<table id="common_table" class="form_table">
	<tr>
		<td width="180px">OS Name:</td>
		<td class="alignl"><input type="text" name="osName" id="osName" size="35"
			value="${os.osName}" /></td>
	</tr>
	
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>