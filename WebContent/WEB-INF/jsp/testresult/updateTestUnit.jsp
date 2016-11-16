<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TestUnit Detail</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
@import url("<%=basePath%>css/ui/jquery.ui.all.css");
@import url("<%=basePath%>css/ui/demos.css");
</style>
</head>
<body >
<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testresult.js"></script>

<form method="post" name="testunitform" action="updateTestUnit.action" onsubmit="return upadateTestUnit('${subPlan.dueDate }')"> <input type="hidden"
	value="${testUnit.testUnitId}" id="testunitid" name="testUnitId"></input>
	
<table id="sub_table" class="form_table">

<tr>
		<th colspan="2" class="form_head">TestUnit Detail</th>
	</tr>
<tr>
		<td class="tdl">UnitName</td>
		<td >
		<input type="text" name="unitName"  value="${testUnit.testUnitName}" id="testUnitName"/>
		</td>
	</tr>
	<!--
	<tr>
		<td class="tdl">DueDate:</td>
		<td><s:if test="#gap==-1">
		<input name="dueDate"
			value="${testUnit.dueDate}" id="dueDate" type="hidden"></input>${testUnit.dueDate}
		</s:if><s:else>
		<input name="dueDate"
			value="${testUnit.dueDate}" id="dueDate"></input><span style="color:red;">*</span>
		</s:else></td>
	</tr>
	-->		
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" class="btn1"/></td>
	</tr>
</table>
 
</form>

</body>
<script type="text/javascript">

$(function() {
	$( "#dueDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm-dd",
		minDate: new Date(),
		//maxDate: new Date(2015,12-1,28),
		//gotoCurrent:true,
		showButtonPanel: true,
		currentText:"Today"
	});
});

</script>
</html>