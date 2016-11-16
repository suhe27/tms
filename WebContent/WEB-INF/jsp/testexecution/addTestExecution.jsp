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
<form action="addTestExecution.action" method="post" onsubmit="return checkTestExecution()">
	
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add Test Execution</th>
	</tr>
	<tr>
		<td>Execution Name</td>
		<td><input name="testexecution.executionName"  id="ExecutionName"></input></input><span style="color: red;">*</span></td>
	</tr>
	
		<tr>
		<td width="150">Project</td>
		<td><select id="project" name="testexecution.projectId" onchange="serReleaseCycle(this.value)">
			<option value="">- Select a value-</option>
				<s:iterator value="#listProject" id="projectList">
						<option value="${projectList.projectId}" >${projectList.projectName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
	
	<tr>
		<td width="150">Test Plan</td>
		<td>
		<select id="testPlan" name="testexecution.testPlanId" onchange="setDuration()">
		<option value="">- Select a value-</option>
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td width="150">Test Plan Duration</td>
		<td>
				<input name="PlanStartDate"  id="PlanStartDate" disabled="disabled" size="10"/>->
				<input name="PlanEndDate"  id="PlanEndDate" disabled="disabled" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150">Execution Type</td>
		<td><select id="phase" name="testexecution.phaseId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#phaseList" id="phaseList">
						<option value="${phaseList.phaseId}" >${phaseList.phaseName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
	
		<tr>
		<td width="150">Build Type</td>
		<td><select id="buildId" name="testexecution.buildId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#buildList" id="build">
						<option value="${build.buildId}" >${build.buildType}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>

	<tr>	
		<td>Release Cycle</td>
		<td><input name="testexecution.releaseCycle"  id="releaseCycle"></input>
		<span style="color: red;"> *</span></td>
	</tr>
	
	

	<tr>
		<td>Duration</td>
		<td><input name="testexecution.startDate" id="startDate"
			readonly="readonly"></input>-> <input name="testexecution.endDate"
			id="endDate" readonly="readonly"></input>
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
		<!--
		<input name="PlanStartDate"  id="PlanStartDate" style="display:none"></input>
		<input name="PlanEndDate"  id="PlanEndDate" style="display:none"></input>
		-->
		<td class="operation"><input type="submit" value=" Add "
			class="btn1" /></td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
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
	
	
	
	function serReleaseCycle(pro){
		
		var tpl = document.getElementById("testPlan");
		tpl.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "All";
		tpl.add(o);
		
		jQuery.post("getListTestPlans.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].testPlanId;
					op.text = data[i].planName;
					tpl.add(op);
				}
			}
		},"json");
		
		var phase = document.getElementById("phase");
		phase.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "All";
		phase.add(o);
		
		jQuery.post("getPhaseByProjectIdJSON.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].phaseid;
					op.text = data[i].phasename;
					phase.add(op);
				}
			}
		},"json");
		
		var build = document.getElementById("buildId");
		build.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "All";
		build.add(o);
		
		jQuery.post("getBuildTypeByProjectIdJSON.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].buildid;
					op.text = data[i].buildtype;
					build.add(op);
				}
			}
		},"json");
		
	}
	
	function setDuration(){
		var start = document.getElementById('PlanStartDate');
		var end = document.getElementById('PlanEndDate');
		var id = document.getElementById('testPlan').value;
		jQuery.post("getTestPlanDurationJSON.action",{projectId:id},function(data){
			start.value = data[0].startdate;
			end.value = data[0].enddate;
		},"json");
		
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