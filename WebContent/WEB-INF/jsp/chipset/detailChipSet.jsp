<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail ChipSets</title>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<script type="text/javascript" src="scripts/chipset.js"></script>
<style type="text/css">
@import
"css/index.css"
</style>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">ChipSet Detail</label>
<hr />
<form action="updateChipSet.action" method="post"
	onsubmit="return checkChipSet();">
<table id="common_table">

	<tr>
		<td width="180px">ChipSet Id:</td>
		<td><input type="text" name="chipId" size="35"
			readonly="readonly" value="${chip.chipId}" /></td>
	</tr>
	<tr>
		<td width="180px">ChipSetname:</td>
		<td><input type="text" name="chipName" id="chipName" size="35"
			value="${chip.chipName}" /></td>
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