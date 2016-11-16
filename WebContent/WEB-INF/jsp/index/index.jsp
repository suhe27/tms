<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
@import
"css/index.css"
</style>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var obj = {
			one : 1,
			two : 2,
			three : 3,
			four : 4,
			five : 5,
			six : 6
		};
		$.each(obj, function(key, val) {
			$("#title_" + obj[key]).click(function() {
				$("#opt_" + obj[key]).toggle("slow");
				$("#title_" + obj[key]).toggleClass("title2");
			});
		});
	});

	document.location.href="listBoards.action";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iTMS Index Page</title>
</head>
<body><!--

<div id="nav">
<fieldset><legend>User Login</legend> <s:if
	test="#session.user == null">
	<form action="login.action" method="post"
		onsubmit="return checkLogin();">
	<table align="center" id="logintb">
		<tr>
			<td class="alignr">Username:</td>
			<td><input type="text" name="userName" id="name" /></td>
		</tr>

		<tr>
			<td class="alignr">Password:</td>
			<td><input type="password" name="password" id="pwd" /></td>
		</tr>
		<tr>
			<td class="alignr">&nbsp;</td>
			<td class="alignl"><input type="submit" value="login" />
			&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="reset"></td>
		</tr>

	</table>
	</form>
</s:if> <s:else>
	<table align="center" id="logintb">
		<tr>
			<td nowrap="nowrap">Welcome: ${session.user.userName}</td>
		</tr>
		<tr>
			<td><input type="button" value="logout" onclick=
	logout();;
></input></td>
		</tr>

	</table>
</s:else></fieldset>


<ul id="menu">


	<li class="item"><a href="listTestCases.action" class="title"
		name="2" onfocus="this.blur();" id="title_2">TestCase</a>
	<ul id="opt_2" class="optiton">
	
	</ul>
	</li>

	<li class="item"><a href="listTestCaseSuits.action" class="title"
		name="3" onfocus="this.blur();" id="title_3">Test Suite </a>
	<ul id="opt_3" class="optiton">
	</ul>
	</li>

	<li class="item"><a href="listTestCaseExecutionSuit.action" class="title"
		name="4" onfocus="this.blur();" id="title_4">Test Execution Suite </a>
	<ul id="opt_4" class="optiton">
			
	</ul>
	</li>
	<li class="item"><a href="######" class="title"
		name="5" onfocus="this.blur();" id="title_5">Test Execution Plan</a>
	<ul id="opt_5" class="optiton">
		
		
	</ul>
	</li>

	<li class="item"><a href="javascript:void(0)" class="title"
		name="6" onfocus="this.blur();" id="title_6">Configuration</a>
	<ul id="opt_6" class="optiton">
		<li><a href="listUsers.action">User</a></li>
		<li><a href="listBoards.action">Board</a></li>
		<li><a href="listChipSets.action">ChipSet</a></li>
		<li><a href="listOSs.action">OS</a></li>
		<li><a href="listTestTypes.action">TestType</a></li>
		<li><a href="listFeatures.action">Feature</a></li>
		<li><a href="listComponents.action">Component</a></li>
		<li><a href="listTeams.action">Team</a></li>
		<li><a href="listPlatforms.action">Platform</a></li>
		<li><a href="listHardWares.action">HardWare</a></li>
	</ul>
	</li>
</ul>
</div>

-->
<s:url action="/listBoards.action" ></s:url>
</body>
</html>