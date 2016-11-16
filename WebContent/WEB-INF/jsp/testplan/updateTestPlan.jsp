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
<title>Test Plan</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
@import url("<%=basePath%>css/ui/jquery.ui.all.css");
@import url("<%=basePath%>css/ui/demos.css");
</style>
<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testplan.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>
</head>
<body>
<form method="post" name="testplanform" action="updateTestPlan.action" onsubmit="return checkTestPlan();">
<input type="hidden" name="testPlan.projectId" value="${testPlan.projectId}"></input> 
<input type="hidden" name="testPlan.createDate" value="${testPlan.createDate}"></input> 
<input type="hidden" name="testPlan.testPlanId" value="${testPlan.testPlanId}"></input> 

<table id="sub_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Test Plan Detail</th>
	</tr>
	<tr>
		<td class="tdl">Plan Name</td>
		<td><input name="testPlan.planName" value="${testPlan.planName}" id="planName"></input><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td class="tdl">Project:</td>
		<td>${testPlan.project.projectName}
		<input name="testPlan.project.projectId" value="${testPlan.project.projectId }" id="projectId" type="hidden"></input>
		</td>
			
	</tr>
	
	<tr>
		<td class="tdl">Phase:</td>
		<td>
		<select id="phaseId" name="testPlan.phaseId">
		<s:iterator value="#phaseList" id="phase">
		<s:if test="#testPlan.phaseId == #phase.phaseId">
		<option value="${phase.phaseId }" selected="selected">${phase.phaseName}</option>
		</s:if>
			<s:else>
				<option value="${phase.phaseId }">${phase.phaseName}</option>
			</s:else>
		</s:iterator>
		</select>
		<span style="color: red;">*</span>
	</td>
	</tr>
	<tr>
		<td class="tdl">Duration:</td>
		<td>			
			<input name="testPlan.startDate" value="${testPlan.startDate }" id="startDate" ></input>->
			<input name="testPlan.endDate"  value="${testPlan.endDate}" id="endDate"></input><span style="color:red;">*</span>			
		</td>
	</tr>
	
	
	<tr>
		<td class="tdl">Description:</td>
		<td><input name="testPlan.description"
			value="${testPlan.description}" id="description" ></input>
		</td>				  
	</tr>
	<tr>
		<td class="tdl">Total Cases:</td>
		<td>${testPlan.totalCases}<input name="testPlan.totalCases"
			value="${testPlan.totalCases}" id="totalCases" readonly="readonly" type="hidden"></input>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" class="btn1"/></td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript"><!--
$(function() {
	$( "#startDate" ).datepicker({
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
$(function() {
	$( "#endDate" ).datepicker({
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