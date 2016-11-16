<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Plan</title>
<script type="text/javascript">
	function selectSubPlanClick(subPlanId,testPlanId) {

		window.location.href="toUpdateSubTestPlan.action?subPlanId="+subPlanId+"&testPlanId="+testPlanId;
	}
</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">TestPlan
Detail</label>
<hr />
<form method="post" name="testplanform" action="updateTestPlan.action">
<input type="hidden" name="testPlan.testPlanId"
	value="${testPlan.testPlanId}"></input> <input type="hidden"
	name="testPlanId" value="${testPlan.testPlanId}"></input>
<table id="sub_table">
	<tr>
		<td width="180px">PROJECT</td>
		<td class="alignl"><input name="testPlan.project"
			value="${testPlan.project}" id="project" readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">RELEASECYCLE</td>
		<td class="alignl"><input name="testPlan.releaseCycle"
			value="${testPlan.releaseCycle}" id="releaseCycle"></input></td>
	</tr>
	<tr>
		<td width="180px">RELEASEVERSION</td>
		<td class="alignl"><input name="testPlan.releaseVersion"
			value="${testPlan.apiVersion}" id="releaseVersion"></input></td>
	</tr>
	<tr>
		<td width="180px">DURATION</td>
		<td class="alignl"><input name="testPlan.duration"
			value="${testPlan.duration}" id="duration"></input></td>
	</tr>
	<tr>
		<td width="180px">PACKAGEINFOS</td>
		<td class="alignl"><input name="testPlan.packageInfos"
			value="${testPlan.packageInfos}" id="packageInfos"></input></td>
	</tr>
	<tr>
		<td width="180px">TESTER</td>
		<td class="alignl"><input name="testPlan.testers"
			value="${testPlan.testers}" id="testers" readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">PASS</td>
		<td class="alignl" style="background-color: green;"><input
			name="testPlan.pass" value="${testPlan.pass}" id="pass"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">FAIL</td>
		<td class="alignl" style="background-color: red;"><input
			name="testPlan.fail" value="${testPlan.fail}" id="fail"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">NOTRUN</td>
		<td class="alignl" style="background-color: white;"><input
			name="testPlan.notRun" value="${testPlan.notRun}" id="notRun"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">BLOCK</td>
		<td class="alignl" style="background-color: yellow;"><input
			name="testPlan.block" value="${testPlan.block}" id="block"
			readonly="readonly"></input></td>
	</tr>
	<tr>
		<td width="180px">PASSRATE</td>
		<td class="alignl"><input name="passRate"
			value="${testPlan.passRate}" id="passRate" readonly="readonly"></input><span
			style="float: right;">%</span></td>
	</tr>
	<tr>
		<td width="180px">TOTALCASES</td>
		<td class="alignl"><input name="testPlan.totalCases"
			value="${testPlan.totalCases}" id="totalCases" readonly="readonly"></input>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" /></td>
	</tr>
</table>
<label class="statictittle">Contains SubTestPlan</label>
<hr />
<table id="common_table">
	<tr>
		<th><input type="checkbox" onclick="selectAllSubTestPlan(this);"></input></th>
		<th>SUBPLANNAME</th>
		<th>PROJECT</th>
		<th>RELEASECYCLE</th>
		<th>APIVERSION</th>
		<th>DURATION</th>
		<th>PACKAGEINFOS</th>
		<th>PASS</th>
		<th>FAIL</th>
		<th>NOTRUN</th>
		<th>BLOCK</th>
		<th>PASSRATE</th>
		<th>TOTALCASES</th>
		<th>EXPORT</th>
	</tr>
	<s:iterator value="#subPlanList" id="subPlan" status="st">
		<tr onmouseover="selectLine(true, '${subPlan.subPlanId }', this)"
			onmouseout="selectLine(false, '${subPlan.subPlanId }', this)">

			<td><input type="checkbox" name="multiSubPlan"
				value="${subPlan.subPlanId }"></input></td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				class="td_id">${subPlan.subPlanName}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				class="td_id">${subPlan.project}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.releaseCycle
			}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.apiVersion
			}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.duration
			}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.packageInfos
			}</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				style="background: green;">${subPlan.pass }</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				style="background: red;">${subPlan.fail }</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				style="background: white;">${subPlan.notRun }</td>

			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')"
				style="background: yellow;">${subPlan.block }</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.passRate
			}%</td>
			<td onclick="selectSubPlanClick('${subPlan.subPlanId }','${testPlanId}')">${subPlan.totalCases
			}</td>
			<td><input type="button" value="detail export " onclick="location='toExportSubTestPlanToXml.action?subPlanId=${subPlan.subPlanId}&testPlanId=${testPlanId}'"/></td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="11"><span style="float: left;"><input
			type="button" value="Delete" onclick="delSubplan();" /></span> 		
			<span
			style="float: left;"><input type="button"
			value="Create SubTestPlan"
			onclick="location='toAddSubTestPlan.action?testPlanId=${testPlan.testPlanId}'" /></span>
			<span style="float: left;"><input
			type="button" value="Return" onclick="location='listTestPlans.action';" /></span> 
			</td>
	</tr>
</table>
</form>
</div>
</body>
</html>