<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.intel.cid.common.bean.User"%>
	
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)session.getAttribute("user");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add TestUnits</title>
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
<script type="text/javascript" src="<%=basePath %>scripts/testresult.js"></script>
</head>
<body>
<span style="color:red;">${errorMsg }</span>
<form action="addTestUnit.action" method="post" onsubmit="return addTestUnit('${subPlan.dueDate }');">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add Test Unit</th>
	</tr>
	
	<tr>
		<td>SubPlan Info:</td>
		<td><input type="radio" name="subPlanId"
			value="${subPlan.subPlanId }" disabled="disabled" checked="checked" />
		<input type="hidden" name="subPlanId" value="${subPlan.subPlanId }" />
		<label>${subPlan.subPlanName }</label></td>

	</tr>
	
	<tr>
		<td>Select TestSuites:</td>
		<td><select name="testSuiteId" id="testSuiteId">

			<s:iterator value="#testSuiteList" id="testSuite" status="st">
			
			<s:if test="#testSuite.testSuiteId == #testSuiteId">
			<option value="${testSuite.testSuiteId }" selected="selected">${testSuite.testSuiteName}</option>
			</s:if><s:else>
			<option value="${testSuite.testSuiteId }">${testSuite.testSuiteName}</option>
			</s:else>
				
			</s:iterator>

		</select></td>
	</tr>
	<tr>
	
	<td>Contains TestUnits:</td>
	<td>
		<s:iterator value="#testUnitList" id="tu" status="st">
			<label id="flag">${tu.testUnitName } --> ${tu.targetName }</label><br/>
		</s:iterator>
	
	</td>
	
	</tr>
	<tr>

		<td>Add TestUnit :</td>

		<td>
		<table id="table_board">
				<tr id="tr_board">
					<td>UnitName:<input name="testUnitList[0].testUnitName"
						id="unitNames"></input></td>
					<td>Target:<select name="testUnitList[0].targetId"
						id="targetIds">
						<option value="-1">- Select a value-</option>
						<s:iterator value="#targetList" id="target">
						
							<option value="${target.targetId }">${target.targetName
							}</option>
						</s:iterator>
					</select></td>

					<td>DueDate:<input name="testUnitList[0].dueDate"
						id="dueDates" value="${subPlan.dueDate }"></input></td>
					<td><input type="button" onclick="addTarget('${subPlan.dueDate }');" value="Add Target" class="btn1" id="addTargetButton" /></td>
				</tr>
			</table>
		</td>
	</tr>
	
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
	$( "#dueDates" ).datepicker({
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