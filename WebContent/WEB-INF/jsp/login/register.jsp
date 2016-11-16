<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<style type="text/css">
@import
"css/index.css"
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TMS Login Page</title>
<style type="text/css">
body {
	text-align: center;
}

body h3 {
	text-align: center;
}

.main {
	width: 80%;
	margin-left: auto;
	margin-right: auto;
}

#logintb {
	margin-top: 5%;
}

.alignr {
	text-align: right;
}

.alignl {
	text-align: left;
}
</style>
<script type="text/javascript" src="scripts/login.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div class="div_main">
<h3>Welcome to STV TMS</h3>
<label class="title">User Register</label> <br>
<form action="register.action" method="post"
	onsubmit="return checkRegister();">
<table align="center" id="logintb">
	<tr>
		<td class="alignr">Username:</td>
		<td class="alignl"><input type="text" name="userName"
			id="username" /></td>
	</tr>
	<tr>
		<td class="alignr">Team:</td>
		<td class="alignl"><select name="teamId">
			<s:iterator value="#teamList" id="team" status="st">
				<option value="${team.teamId }">${team.teamName }</option>
			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td class="alignr">Password:</td>
		<td class="alignl"><input type="password" name="password"
			id="password" /></td>
	</tr>
	<tr>
		<td class="alignr">Email:</td>
		<td class="alignl"><input type="text" name="email" id="email" /></td>
	</tr>
	<tr>
		<td class="alignr">&nbsp;</td>
		<td class="alignl"><input type="submit" value="register" />
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="reset"></td>
	</tr>

	<tr>
		<td colspan="2" style="text-align: center; color: red;"><br />
		${errorMsg }</td>
	</tr>
</table>
</form>

</div>






</body>
</html>