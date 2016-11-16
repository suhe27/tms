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
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/login.js"></script>
</head>
<body>

<div align="center">
<div id="div_main" style="width:60%"><label class="statictittle">User Detail</label>
<hr />
</div>
<form action="updateUserNameAndPassword.action" method="post" onsubmit="return checkUpdate();">
	<input type="text" name="userId" size="35" readonly="readonly" value="${user1.userId}" style="display:none"/>
<table style="width:60%"  id="common_table" class="form_table">
	<tr>
		<td width="180px">User Name:</td>
		<td class="alignl"><input type="text" name="userName" size="35"
			id="userName" value="${user1.userName}" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td class="alignl"><input type="text" name="password" size="35"
			id="password" value="${user1.password}" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td width="180px">User Email:</td>
		<td class="alignl">${user1.email}</td>
	</tr>
	<tr>
		<td>User Type:</td>
		<td>${user1.userType}</td>
	</tr>
	<tr>
		<td width="180px">Select Teams:</td>
		<td class="alignl"><s:iterator id="team" value="#teamList"
			status="st">
			<s:set name="index" value="#st.index + 1" />
			<s:set name="modVal" value="#index % 2" />
			<s:set name="flag" value="false" />	
			<s:iterator id="t" value="#ownerTeamList" status="sx">

				<s:if test="#team.teamId == #t.teamId ">
						<label>${team.teamName} &nbsp;&nbsp;</label>
				<s:set name="flag" value="true" />			
				</s:if>
				
				

			</s:iterator>
			<!-- 
			<s:if test="#flag ==false">
				<input id="input_${team.teamId }" type="checkbox" name="multiTeam"
						value="${team.teamId }" style="width: 30px;" />
						<label for="input_${team.teamId }">${team.teamName}</label>
			</s:if>
			-->
			<s:if test="#modVal ==0">
			<br/>
			</s:if>
		</s:iterator>
		</td>
	</tr>
	<tr>
		<td style="border-right: none;"></td>
		<td><input type="submit" value="update" class="btn1" /></input></td>
	</tr>
</table>

</form>

</div>

</body>
</html>