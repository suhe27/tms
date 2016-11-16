<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Execution</title>
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
<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
</head>
<body>
<!-- 
<form action="updateTestexecution.action" method="post"" onsubmit="return checkTestExecutionUpdate('${testPlan.startDate}','${testPlan.endDate}')">
-->
<form action="updateTestexecution.action" method="post" onsubmit="return checkTestExecution()">

	<input type="hidden" name="testexecution.id" value="${testExecution.id}">
	<input type="hidden" name="testexecution.executionId" value="${testExecution.id}">
	<input type="hidden" name="Descrption" id="Descrption" value="${testExecution.desc}">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Update Test Execution</th>
	</tr>
	<tr>
		<td>Execution Name </td>
		
		<td><input name="testexecution.executionName"  id="ExecutionName" value="${testExecution.executionName}"></input><span style="color: red;">*</span></td>
	</tr>
	
		<tr>
		<td width="150">Project</td>
		<td>${testExecution.projectName}</td>
	</tr>
	
		<tr>
		<td width="150">Test Plan</td>
		<td> ${testExecution.testPlanName}</td>
	</tr>
	<tr>
		<td width="150">Test Plan Duration</td>
		<td>
				<input name="PlanStartDate"  id="PlanStartDate" value="${testPlan.startDate}" disabled="disabled" size="10"/>->
				<input name="PlanEndDate"  id="PlanEndDate" value="${testPlan.endDate}" disabled="disabled" size="10"/>
		</td>
	</tr>
	<tr>
	<td width="150">Execution Type</td>
		<td><select id="phase" name="testexecution.phaseId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#phaseList" id="phaseList">
					<s:if test="#phaseList.phaseId == #testExecution.phaseId">
						<option value="${phaseList.phaseId}" selected="selected">${phaseList.phaseName}</option>
					</s:if>
					<s:else>
						<option value="${phaseList.phaseId}">${phaseList.phaseName}</option>
					</s:else>
				</s:iterator>
		</select>
		<span style="color: red;">*</span></td>	
	</tr>
	<tr>
	<td width="150">Build Type</td>
		<td><select id="buildId" name="testexecution.buildId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#buildList" id="build">
					<s:if test="#build.buildId == #testExecution.buildId">
						<option value="${build.buildId}" selected="selected">${build.buildType}</option>
					</s:if>
					<s:else>
						<option value="${build.buildId}">${build.buildType}</option>
					</s:else>
				</s:iterator>
		</select>
		<span style="color: red;">*</span></td>	
	</tr>
	<tr>
		<td>Release Cycle</td>
		<td><input name="testexecution.releaseCycle"  value=" ${testExecution.releaseCycle}"  id="releaseCycle"></input>
		<span style="color: red;"> *</span></td>
	</tr>
	<tr>
		<td>Duration</td>
		<td><input name="testexecution.startDate" id="startDate" value="${testExecution.startDate}"
			readonly="readonly"></input>-> <input name="testexecution.endDate"
			id="endDate" readonly="readonly" value=" ${testExecution.endDate}"></input>
			<span style="color: red;"> *</span>
		</td>
	</tr>
	 <tr>
		<td>Description</td>
		<td>
		<textarea name="testexecution.desc"  id="description"  cols=35 rows=2 ></textarea>
		</td>
	</tr> 
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" update "
			class="btn1" /></td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
var textobj=document.getElementById('description');
textobj.innerHTML=$("#Descrption").val();

	$(function() {
		$("#startDate").datepicker( {
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
		$("#endDate").datepicker( {
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			minDate : new Date(),
			//maxDate: '+1m',
			//gotoCurrent:true,
			showButtonPanel : true,
			currentText : "Today"
		});
	});
	
	
		
	function checkForm(){
		
		if($("#ExecutionName").val().length<1){
			alert("ExecutionName can not null .");
			return false ;
		}
		
		
		if($("#releaseCycle").val()==''){
			alert("ReleaseCycle can not null .");
			return false;
		}
		var  startDate= document.getElementById("startDate").value.trim();

		var  endDate= document.getElementById("endDate").value.trim();
		
		if (startDate.length<1) {
			alert("startDate can not null!");
			return false;
		}
		if (endDate.length<1) {
			alert("endDate can not null!");
			return false;
		}
		
		var gap = compareTime(startDate,endDate);
		if(gap < 0){
			alert("endDate must be later than startDate!please check!");
			return false;
		}else{
			var a = gap/(24*3600*1000.0);
			if(a >30){
				
				alert("the time gap of endDate and startDate can not more than 30 days !please check!");
				return false;
			}
			return true;
		}
		
		return true;
	}
	
	
	
	
	
	
	
	function compareTime(startdate, enddate) {

		if(startdate =='')
		{
			alert("startdate can not null!");
			return 0;
		}
		if(enddate =='')
		{
			alert("enddate can not null!");
			return 0;
		}
		
		var startdates = startdate.split('-');
		var enddates = enddate.split('-');

		startdate = startdates[1] + '/' + startdates[2] + '/' + startdates[0] ;
				
		enddate = enddates[1] + '/' + enddates[2] + '/' + enddates[0] ;

		// MM/dd/yyyy HH:mm:ss
		// alert(Date.parse(startdate));
		// alert(Date.parse(enddate));
		//var a = (Date.parse(enddate) - Date.parse(startdate)) / (24 * 3600 * 1000);
		 var a = Date.parse(enddate) - Date.parse(startdate) ;
		return a;

	}
</script>

</html>