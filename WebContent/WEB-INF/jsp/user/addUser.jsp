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
<title>User</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
	<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/login.js"></script>
</head>
<body>
<form action="addUser.action" method="post"
	onsubmit="return checkRegister();">

<table id="common_table" class="form_table">


	<tr>
		<td width="180px">User Name:</td>
		<td class="alignl"><input type="text" name="userName"
			id="username" /><span style="color:red;">*</span></td>
	</tr>

	<tr>
		<td width="180px">Password:</td>
		<td class="alignl"><input type="password" name="password"
			id="password" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td width="180px">Email:</td>
		<td class="alignl"><input type="text" name="email" id="email" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>User Type:</td>
		<td><select name="level" id="level"
			style="width: 100px">
			<s:iterator value="#userType" id="u">
				<s:if test="#u.level == 2">
					<option value="${u.level }" selected="selected">${u.userType}</option>
				</s:if>
				<s:else>
					<option value="${u.level }">${u.userType}</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td width="180px">Select Teams:</td>
		<td class="alignl">
		<table style="width:100%;">
		<s:iterator id="team" value="#teamList"	status="st">
			<s:set name="index" value="#st.index + 1" />
			<s:set name="modVal" value="#index % 2" />
				<s:if test="#modVal!=0">
					<tr>
				</s:if>
				<td>
				<input id="input_${team.teamId }" type="checkbox" name="multiTeam" value="${team.teamId }" style="width:30px;"/>
				<label for="input_${team.teamId }" style="float:left;">${team.teamName}</label>
				<s:if test="#modVal==0">
					</td></tr>
				</s:if>
				<s:else>
					</td>
				</s:else>
		</s:iterator>
		</table>
		</td>
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