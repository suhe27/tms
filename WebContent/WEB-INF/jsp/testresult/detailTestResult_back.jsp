<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TestUnit Detail</title>
</head>
<body onload="selectDefaultColor();">
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>

<script type="text/javascript">
	//$(document).ready(function(){
	//	$("#common_table select").each(function(){
	//		var value = $(this).val();
	//		if(value=="1"){$(this).css({"background-color":"green"});}
	//		if(value=="2"){$(this).css({"background-color":"red"});}
	//		if(value=="3"){$(this).css({"background-color":"#red"});}
	//		if(value=="4"){$(this).css({"background-color":"yellow"});}
	//	});

	//});
</script>
<div id="div_main"><label class="statictittle">TestUnit
Detail</label>
<hr />
<form method="post" name="testunitform" action="updateTestUnit.action"><input
	type="hidden" value="${testUnit.testUnitId}" id="testunitid"
	name="testUnit.testUnitId"></input> <input type="hidden"
	value="${testUnit.testUnitId}" id="testunitid" name="testUnitId"></input>
	<input
	type="hidden" value="${testUnit.subPlanId}" 
	name="testUnit.subPlanId">
	<input
	type="hidden" value="${testUnit.testPlanId}" 
	name="testUnit.testPlanId">
	
	<input
	type="hidden" value="${testUnit.boardId}" 
	name="testUnit.boardId">
	<input
	type="hidden" value="${testUnit.space}" 
	name="testUnit.space">
	<input
	type="hidden" value="${testUnit.mode}" 
	name="testUnit.mode">
	<input
	type="hidden" value="${testUnit.scriptFlag}" 
	name="testUnit.scriptFlag">
<table id="sub_table">
	<tr>
		<td width="180px">UNITNAME</td>
		<td class="alignl">${subPlan.subPlanName}_${testUnit.board.boardName}_${testUnit.mode
		}_${testUnit.space }</td>
	</tr>

	<tr>
		<td width="180px">PASS</td>
		<td class="alignl" style="background: green;"><input
			name="testUnit.pass" value="${testUnit.pass}" id="pass"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">FAIL</td>
		<td class="alignl" style="background: red;"><input
			name="testUnit.fail" value="${testUnit.fail}" id="fail"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">NOTRUN</td>
		<td class="alignl" style="background: white;"><input
			name="testUnit.notRun" value="${testUnit.notRun}" id="notRun"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">BLOCK</td>
		<td class="alignl" style="background: yellow;"><input
			name="testUnit.block" value="${testUnit.block}" id="block"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">PASSRATE</td>
		<td class="alignl"><input name="passRate"
			value="${testUnit.passRate}" id="passRate" readonly="readonly"></input>
		<span style="float: right;">%</span></td>
	</tr>
	<tr>
		<td width="180px">TOTALCASES</td>
		<td class="alignl"><input name="testUnit.totalCases"
			value="${testUnit.totalCases}" id="totalCases" readonly="readonly"></input>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>
</table>
<label style="text-align: center;">Contains TestResult</label>
<hr />
<table id="filter_table">

	<tr>
		<td><span
			style="float: left; font-size: 15px; margin-right: 50px;">Filters:</span></td>

		<td width="180px">Result:<select onchange="filterTestResult();"
			id="resulttypeid" name="resultTypeId">
			<option value="0">none</option>
			<option disabled="disabled">------</option>
			<s:iterator value="#resultTypeList" id="result" status="sx">

				<s:if test="#resultTypeId == #result.resultTypeId">
					<option value="${result.resultTypeId }" selected="selected">${result.resultTypeName}</option>
				</s:if>
				<s:else>
					<option value="${result.resultTypeId }">${result.resultTypeName}</option>
				</s:else>

			</s:iterator>
		</select></td>

	</tr>
</table>

<table id="common_table">
	<tr>
		<th><input type="checkbox" onclick=selectAllTestResult(this);;></input></th>
		<th>TESTCASENAME</th>
		<th>RESUILT</th>
		<th>LOG</th>
		<th>BUGID</th>
		<th>COMMENTS</th>
	</tr>
	<s:iterator value="testResultList" id="testResult" status="st">
		<input name="testResultList[<s:property value='#st.index'/>].resultId"
			value="<s:property value='testResultList[#st.index].resultId'/>"
			type="hidden"></input>

		<input
			name="testResultList[<s:property value='#st.index'/>].testPlanId"
			value="<s:property value='testResultList[#st.index].testPlanId'/>"
			type="hidden"></input>
		<input
			name="testResultList[<s:property value='#st.index'/>].subPlanId"
			value="<s:property value='testResultList[#st.index].subPlanId'/>"
			type="hidden"></input>
		<input
			name="testResultList[<s:property value='#st.index'/>].testUnitId"
			value="<s:property value='testResultList[#st.index].testUnitId'/>"
			type="hidden"></input>
		<input
			name="testResultList[<s:property value='#st.index'/>].testSuiteId"
			value="<s:property value='testResultList[#st.index].testSuiteId'/>"
			type="hidden"></input>

		<input
			name="testResultList[<s:property value='#st.index'/>].testCaseId"
			value="<s:property value='testResultList[#st.index].testCaseId'/>"
			type="hidden"></input>

		<input type="hidden"
			name="testResultList[<s:property value='#st.index'/>].testCaseName"
			value="<s:property value='testResultList[#st.index].testCaseName'/>"></input>
		<tr onmouseover="selectLine(true, '${testResult.resultId }', this)"
			onmouseout="selectLine(false, '${testResult.resultId }', this)">

			<td><input type="checkbox" name="multiResult"
				value="<s:property value='testResultList[#st.index].resultId'/>" /></td>
			<td>${testResult.testCaseName}</td>
			<td><select
				name="testResultList[<s:property value='#st.index'/>].resultTypeId"
				class="testResult">

				<option value="0">none</option>
				<option disabled="disabled">------</option>
				<s:iterator value="#resultTypeList" id="type" status="so">

					<s:if test="#testResult.resultTypeId == #type.resultTypeId">
						<option value="${type.resultTypeId }" selected="selected">${type.resultTypeName}</option>
					</s:if>
					<s:else>
						<option value="${type.resultTypeId }">${type.resultTypeName}</option>
					</s:else>

				</s:iterator>
			</select></td>
			<td><input
				name="testResultList[<s:property value='#st.index'/>].log"
				value="<s:property value='testResultList[#st.index].log'/>"></input></td>
			<td><input
				name="testResultList[<s:property value='#st.index'/>].bugId"
				value="<s:property value='testResultList[#st.index].bugId'/>"></input></td>
			<td><input
				name="testResultList[<s:property value='#st.index'/>].comments"
				value="<s:property value='testResultList[#st.index].comments'/>"></input></td>
		</tr>

	</s:iterator>
	<tr>
		<td colspan="6"><span style="float: left;"><input
			type="button" value="BatchUpdate" onclick="updateBatchTestResults();" /></span>
			
			<span style="float: left;"><input
			type="button" value="Return" onclick="location='toUpdateSubTestPlan.action?subPlanId=${testUnit.subPlanId}&testPlanId=${testUnit.testPlanId}'" /></span>
		<span style="float: right; display: inline"><p:page
			action="toUpdateTestUnit.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
			<p:param name="resultTypeId" value="#resultTypeId"></p:param>
			<p:param name="testUnitId" value="#testUnitId"></p:param>
		</p:page></span></td>
	</tr>
</table>
</form>


</div>
</body>
</html>