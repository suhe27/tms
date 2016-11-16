<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail HardWares</title>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<script type="text/javascript" src="scripts/hardware.js"></script>
<style type="text/css">
@import
"css/index.css"
</style>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">HardWare
Detail</label>
<hr />
<form action="updateHardWare.action" method="post"
	onsubmit="return checkHardWare();">
<table id="common_table">

	<tr>
		<td width="180px">HardWare Id:</td>
		<td><input type="text" name="hardwareId" size="35"
			readonly="readonly" value="${hardWare.hardwareId}" /></td>
	</tr>
	<tr>
		<td width="180px">HardWareName:</td>
		<td><input type="text" name="hardwareName" id="hardwareName" size="35"
			value="${hardWare.hardwareName}" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>
</table>
</form>


</div>
</body>
</html>