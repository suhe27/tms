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

</head>
<body>
<form action="addTestExecution.action" method="post"" onsubmit="return checkForm()">
	
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
		<td width="150">Seelct TestPlan</td>
		<td>
		<select id="testPlan" name="testexecution.testPlanId">
		<option value="">- Select a value-</option>
		
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
	<tr>
		<td width="150">Seelct PlatForm</td>
		<td>
		<select id="platform" name="testexecution.platformId">
			<option value="">- Select a value-</option>
		</select>
		<span style="color: red;">*</span></td>
	</tr>
		<tr>
		<td width="150">Seelct Testenv</td>
		<td>
		<select id="testenv" name="testexecution.tesId">
			<option value="">- Select a value-</option>
	
		</select>
		</td>
	</tr>
	
	<tr>
	<tr>
		<td width="150">Select ExecutionOs</td>
		<td><select id="os" name="testexecution.osId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#osList" id="os">
						<option value="${os.osId}" >${os.osName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
	<tr>
		<td width="150">Select ExecutionPhase</td>
		<td><select id="exePhaseId" name="testexecution.exePhaseId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#phaseList" id="phaseList">
						<option value="${phaseList.phaseId}" >${phaseList.phaseName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
	
		<tr>
		<td width="150">Build Type</td>
		<td><select id="buildType" name="testexecution.buildId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#buildList" id="build">
						<option value="${build.buildId}" >${build.buildType}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	
		<td>ReleaseCycle</td>
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
		<td>Descrption</td>
		<td>
		<textarea name="testexecution.desc"  id="description"  cols=35 rows=2 ></textarea>
		</td>
	</tr>
	 <tr>
		<td>Select Base Testexecution </td>
		<td>
			<select id="textPlanId" name="testPlanId" >
			<option value="">- Select a value -</option>
			</select>
		</td>
	</tr> 
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="Copy "
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
		
		

		var fea = document.getElementById("testenv");
		fea.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.title= "";
		o.text = "All";
		fea.add(o);
		
		jQuery.post("getListTestenvs.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].id;
					op.title= data[i].desc;
					op.text = data[i].envname;
					fea.add(op);
				}
			}
		},"json");
		
		
		var platform = document.getElementById("platform");
		platform.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "All";
		platform.add(o);
		
		jQuery.post("getListPlatforms.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].platformid
					op.text = data[i].platformname;
					platform.add(op);
				}
			}
		},"json");
		
		var textexecution = document.getElementById("textPlanId");
		textexecution.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "All";
		textexecution.add(o);
		jQuery.post("getListTestExecution.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].testPlanId;
					op.text = data[i].executionName;
					textexecution.add(op);
				}
			}
		},"json");
		
	}
		
	
	function checkForm(){
		
		if($("#ExecutionName").val().length<1){
			alert("ExecutionName can not null .");
			return false ;
		}
		if($("#project").val()==''){
			alert("Project can not null . ");
			return false ;
		}
		if($("#testPlan").val()==''){
			alert("TestPlan can not null .");
			return false;
		}
		
		if($("#platform").val()==''){
			alert("Platform can not null .");
			return false;
		}
		
		if($("#os").val()==''){
			alert("Os can not null .");
			return false;
		}
		
	
		
		/* if($("#testenv").val()==''){
			alert("Testenv can not null .");
			return false;
		} */
		if($("#releaseCycle").val()==''){
			alert("ReleaseCycle can not null .");
			return false;
		}
		if($("#textPlanId").val()==''){
			alert("Testexecution can not null .");
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