<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List HardWares</title>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>

<script type="text/javascript">
	function selectHardWareClick(id) {
		window.location.href = "toUpdateHardWare.action?hardwareId=" + id;
	}
</script>
<style type="text/css">
@import
"css/index.css"
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">HardWare List</label>
<hr />
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th>HardWareID</th>
		<th >HardWareName</th>	
		<th >Operation</th>
	</tr>
	<s:iterator value="#hardWareList" id="hardWare" status="st">
		<tr onmouseover="selectLine(true, '${hardWare.hardwareId }', this)"
			onmouseout="selectLine(false, '${hardWare.hardwareId }', this)">
			<td onclick="selectHardWareClick('${hardWare.hardwareId }')" class="td_id">${hardWare.hardwareId}</td>
			<td onclick="selectHardWareClick('${hardWare.hardwareId}')">${hardWare.hardwareName
			}</td>
			<td style="text-align: center;"><a
				href="delHardWare.action?hardwareId=${hardWare.hardwareId}"
				onclick="return confirm('Confirm delete hardware \'${hardWare.hardwareName}\'');">Delete</a></td>
		</tr>
	</s:iterator>
	<tr>
	<td colspan="3">
	<span style="float:left;"><input type="button" value="add" onclick="location='toAddHardWare.action'"></input></span>
	</td>
	</tr>
</table>
</div>
</body>
</html>