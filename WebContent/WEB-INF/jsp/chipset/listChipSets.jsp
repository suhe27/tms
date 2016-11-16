<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List ChipSets</title>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>

<script type="text/javascript">
	function selectChipSetClick(id) {
		window.location.href = "toUpdateChipSet.action?chipId=" + id;
	}
</script>
<style type="text/css">
@import
"css/index.css"
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">Board List</label>
<hr />
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th width="100px;">ChipSetID</th>
		<th width="200px">ChipSetName</th>	
		<th width="200px">Operation</th>
	</tr>
	<s:iterator value="#chipSetList" id="chipSet" status="st">
		<tr onmouseover="selectLine(true, '${chipSet.chipId }', this)"
			onmouseout="selectLine(false, '${chipSet.chipId }', this)">
			<td onclick="selectChipSetClick('${chipSet.chipId }')" class="td_id">${chipSet.chipId}</td>
			<td onclick="selectChipSetClick('${chipSet.chipId }')">${chipSet.chipName
			}</td>
			<td style="text-align: center;"><a
				href="delChipSet.action?chipId=${chipSet.chipId }"
				onclick="return confirm('Confirm delete chipSet \'${chipSet.chipName}\'');">Delete</a></td>
		</tr>
	</s:iterator>
	<tr>
	<td colspan="3">
	<span style="float:left;"><input type="button" value="add" onclick="location='toAddChipSet.action'"></input></span>
	</td>
	</tr>
</table>
</div>



</body>
</html>