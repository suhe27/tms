<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/subplan.js"></script>
</head>
<body>
<span style="color: red;">${errorMsg }</span>
<form action="addSubTestPlan.action" method="post"
	onsubmit="return checkSubTestPlan('${testPlan.project.projectName}','${testPlan.endDate }');">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add SubTest Plan</th>
	</tr>
	<tr>
		<td>SubPlan Name:</td>
		<td><input type="text" name="subPlan.subPlanName"
			id="subPlanName" /><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td>TestPlan Info:</td>
		<td><input type="radio" name="testPlanId"
			value="${testPlan.testPlanId }" disabled="disabled" checked="checked" />
		<input type="hidden" name="testPlanId" value="${testPlan.testPlanId }" /><label>${testPlan.planName
		}</label>
	</tr>

	<tr>
		<td>Select TestSuites:</td>
		<td>
			<select name="testSuiteId">
				<s:iterator value="#testSuiteList" id="testSuite" status="st">
					<option value="${testSuite.testSuiteId }">${testSuite.testSuiteName}</option>
				</s:iterator>
			</select>
		<span style="color: red;">*</span>
		</td>
	</tr>
	<s:if test="#testPlan.project.projectName=='DPDK'">

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
					<!--
					<td>DueDate:<input name="testUnitList[0].dueDate" id="dueDates" value="${testPlan.endDate }"></input></td>
					-->
					<td><input type="button" onclick="addTarget('${testPlan.endDate}');" value="Add Target" class="btn1" id="addTargetButton" /></td>
				</tr>
			</table>
			</td>
		</tr>

	</s:if>
	<s:else>
		<input name="testUnitList[0].testUnitName" id="unitNames" value="AutoUnit" style="display:none"></input>
	<!-- 
		<tr>

			<td>Add TestUnit :</td>
			<td>
			<table id="table_board">
				<tr id="tr_board">
					<td>UnitName:<input name="testUnitList[0].testUnitName"
						id="unitNames"></input></td>
					<td>DueDate:<input name="testUnitList[0].dueDate"
						id="dueDates"></input></td>
				</tr>
			</table>
			</td>
		</tr>
	-->
	</s:else>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="submit"
			class="btn1" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
	$(function() {
		$("#dueDates").datepicker( {
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			minDate : new Date(),
			//maxDate: new Date(2015,12-1,28),
			//gotoCurrent:true,
			showButtonPanel : true,
			currentText : "Today"
		});
	});
	$(function() {
		$("#dueDate").datepicker( {
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			minDate : new Date(),
			//maxDate: new Date(2015,12-1,28),
			//gotoCurrent:true,
			showButtonPanel : true,
			currentText : "Today"
		});
	});
</script>
</html>