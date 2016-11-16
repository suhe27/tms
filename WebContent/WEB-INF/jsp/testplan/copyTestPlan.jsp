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
<title>Test Plan</title>
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
<script type="text/javascript" src="<%=basePath%>scripts/testplan.js"></script>

</head>
<body>
<form action="copyTestPlan.action" method="post"
	onsubmit="return copyTestPlan();">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Copy Test Plan</th>
	</tr>
	<tr>
		<td>Plan Name</td>
		<td><input name="testPlan.planName" value="${testPlan.planName}"
			id="planName"></input><span style="color: red;"> *</span></td>
	</tr>
<tr>
		<td width="150">Project</td>
		<td><select id="project" name="testPlan.projectId" onchange="getTestPlanList(this.value);getPhaseList(this.value)">
			<option value="-1">- Select a value-</option>
				<s:iterator value="#projectList" id="project">
						<option value="${project.projectId}" >${project.projectName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td width="150">Select Base TestPlan</td>
		<td><select id="basePlan" name="testPlanId"  >
			<option value="-1">- Select a value-</option>
         <!--<s:iterator value="#testPlanList" id="plan">
         <option value="plan.testPlanId">${ plan.planName}</option>
         </s:iterator>
		--></select> <span style="color: red;"> *</span></td>
	</tr>
	<tr>
		<td>Phase</td>
		<td><select id="phaseId" name="testPlan.phaseId">
			<option value="-1">- Select a value-</option>
			<!--<s:iterator value="#phaseList" id="phase">
		<option value="${phase.phaseId }" >${phase.phaseName}</option>
		</s:iterator>
		-->
		</select></td>
	</tr>

	<tr>
		<td>Duration</td>
		<td><input name="testPlan.startDate" id="startDate"
			readonly="readonly"></input>-> <input name="testPlan.endDate"
			id="endDate" readonly="readonly"></input><span style="color: red;">
		*</span></td>
	</tr>
	<tr>
		<td>Description</td>
		<td><textarea name="testPlan.description" id="description"
			cols=35 rows=2></textarea></td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" submit "
			class="btn1" /></td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
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
function getPhaseList(pro){
	var com = document.getElementById("phaseId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "-1";
	o.text = "- Select a value -";
	com.add(o);
	jQuery.post("getPhaseList.action",{projectId:pro},function(data){
		if(data.length>0){
			for(i =0;i<data.length;i++){
				var op = document.createElement("option");
				op.value= data[i].PHASEID;
				op.text = data[i].PHASENAME;
				com.add(op);
			}
		}
			
	},"json");
}

function getTestPlanList(pro){
	var tpl = document.getElementById("basePlan");
	tpl.options.length = 0;
	
	var o = document.createElement("option");  
	o.value= "-1";
	o.text = "- Select a value -";
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
	
}
</script>
</html>