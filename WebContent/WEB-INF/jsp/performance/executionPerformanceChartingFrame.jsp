<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Performance</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile"> 
		<form id="form1" name="form1" method="post" action="executionPerformance1DChartingDetail.action" target="listExecution">
			<table class="mobile" height="10%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Performance Analysis</TD>
				</tr>
				<tr class="pagesearch">
					<td align="center">
					Project:
					<select onchange="serReleaseCycle(this.value)"  id="projectId" name="projectId">
							<option value="-1">All</option>
							<s:iterator value="#projectList" id="project">	
										<option value="${project.projectId}" >${project.projectName}</option>
							</s:iterator>
					</select>
					TestPlan
					<select id="testPlanId" name="testPlanId">
						<option value="-1">All</option>
					</select>
					ReleaseCycle:
					<select id="releaseCycle" name="releaseCycle">
					<option value="-1">All</option>
					</select>
		Platform:
			<select id="platform" name="platformId">
				<option value="-1">All</option>
			</select>
		  	
		ExecutionOS:
			<select id="os" name="osId" >
				<option value="-1">All</option>
			</select>
		ExecutionType:
		<select id="phaseId" name="phaseId" >
			<option value="-1">All</option>
		</select>
		BuildType
		<select id="buildId" name="buildId" >
			<option value="-1">All</option>
		</select>
		<br>
		ExecutionName:
					<input type="text" size="10" name="executionName">		
		MaxNum:
			<select id="maxId" name="maxId" >
				<option value="20">20</option>
				<option value="15">15</option>
				<option value="10" selected="selected">10</option>
				<option value="5">5</option>			
			</select>
		DataType:
			<select id="dataTyped" name="dataTypeId" >
				<option value="1" selected="selected">1D Data</option>
				<option value="2">2D Data</option>		
			</select>
		<input type="button" class="btn" value="Search" onClick="sch();" />					
					</td>
				</tr>
			</table>
			<iframe  id="listExecution" name="listExecution" frameborder="0" height="89%" width="100%" scrolling="yes"></iframe>
		</form>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		form1.target='listExecution';
		form1.submit();
	});
	function serReleaseCycle(pro){
		var fea = document.getElementById("releaseCycle");
		fea.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
		o.text = "All";
		fea.add(o);
		jQuery.post("getReleaseCyList.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].releasecycle;
					op.text = data[i].releasecycle;
					fea.add(op);
				}
			}
		},"json");
		
		var plan = document.getElementById("testPlanId");
		plan.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
		o.text = "All";
		plan.add(o);
		jQuery.post("getListTestPlans.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].testPlanId;
					op.text = data[i].planName;
					plan.add(op);
				}
			}
		},"json");
		
		var os = document.getElementById("os");
		os.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
		o.text = "All";
		os.add(o);
		
		jQuery.post("getListOSByProjectId.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].osid
					op.text = data[i].osname;
					os.add(op);
				}
			}
		},"json");
		
		var platform = document.getElementById("platform");
		platform.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
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
		
		var phase = document.getElementById("phaseId");
		phase.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
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
		o.value= "-1";
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
	function sch() {
		var type = $("#dataTyped").val();
		if(type == 1) {
			var url = "executionPerformance1DChartingDetail.action";
		} else if(type == 2){
			var url = "executionPerformance2DChartingDetail.action";
		}
		$("#form1").attr("action", url);

		form1.target='listExecution';
		form1.submit();
	}
	</script>
</html>