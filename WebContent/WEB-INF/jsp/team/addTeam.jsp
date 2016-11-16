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
<title>Team</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/team.js"></script>
</head>
<body>

<div id="div_main"><label class="statictittle">Add Team</label>
<hr />
<form action="addTeam.action" method="post"
	onsubmit="return checkTeam();">

<table id="common_table"  class="form_table">

	<tr>
		<td class="alignr">Team Name:</td>
		<td class="alignl"><input type="text" name="teamName"
			id="teamName" /></td>
	</tr>
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td class="operation"><input type="submit" value="add" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>