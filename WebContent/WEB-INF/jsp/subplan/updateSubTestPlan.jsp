<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.intel.cid.common.bean.User"%>
	
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sub Test Plan</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
@import url("<%=basePath%>css/ui/jquery.ui.all.css");
@import url("<%=basePath%>css/ui/demos.css");
</style>
<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
</head>
<body>
<span style="color:red;">${errorMsg }</span>
<form action="updateSubTestPlan.action" method="post" onsubmit="return updateSubTestPlan('${testPlan.endDate }');">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Update SubTest Plan</th>
	</tr>
	<tr>
		<td class="tdl">SubPlan Name:</td>
		<td> 
			<input type="text" name="subPlanName"  value="${subPlan.subPlanName}" id="subPlanName"/>
			<span style="color: red;">*</span>
		</td>
	</tr>
	<tr>
		<td class="tdl">TestPlan Info:</td>
		<td><input type="radio" name="testPlanId"
			value="${testPlan.testPlanId }" disabled="disabled" checked="checked" />
		<input type="hidden" name="testPlanId" value="${testPlan.testPlanId }" /><label>${testPlan.planName}</label>
			<input type="hidden" name="subPlanId" value="${subPlan.subPlanId }" />
	

	</tr>

		<input name="dueDate" value="${subPlan.dueDate}" id="dueDate" type="hidden"></input>
	
	<tr>
		<td>&nbsp;</td>
		<td class="operation">
			<input type="submit" value="submit" class="btn1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
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