<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Execution</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile"> 
		<form id="form1" name="form1" method="post" action="executionTrendChartingDetail.action" target="listExecution">
			<table class="mobile" height="10%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Execution Trend</TD>
				</tr>
				<tr class="pagesearch">
					<td align="center">
					Project:
					<select onchange="listPlans(this.value)"  id="projectId" name="projectId">
							<option value="-1">All</option>
							<s:iterator value="#projectList" id="project">	
										<option value="${project.projectId}" >${project.projectName}</option>
							</s:iterator>
					</select>
					TestPlan
					<select onchange="listSubPlans(this.value)" id="testPlanId" name="testPlanId">
						<option value="-1">All</option>
					</select>
					SubTestPlan
					<select id="subPlanId" name="subPlanId">
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
					Target:
					<select id="target" name="targetId" >
						<option value="-1">All</option>
					</select>
					<input type="text" id="releaseCycle" name="releaseCycle" value="-1" style="display:none"/>
					<input type="text" id="phaseId" name="phaseId" value="-1" style="display:none"/>
					<input type="text" id="buildId" name="buildId" value="-1" style="display:none"/>
					<input type="text" id="maxId" name="maxId" value="10" style="display:none"/>
					<input type="text" name="executionName" style="display:none"/>
					<br>
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
	
	function listPlans(pro){
	
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
		
		var target = document.getElementById("target");
		target.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
		o.text = "All";
		target.add(o);
		
		jQuery.post("getListTargets.action",{projectId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].TARGETID
					op.text = data[i].TARGETNAME;
					target.add(op);
				}
			}
		},"json");
	}
	
	function listSubPlans(pro){
		var plan = document.getElementById("subPlanId");
		plan.options.length = 0;
		var o = document.createElement("option");
		o.value= -1;
		o.text = "All";
		plan.add(o);
		jQuery.post("getListSubTestPlans.action",{testPlanId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].SUBPLANID;
					op.text = data[i].SUBPLANNAME;
					plan.add(op);
				}
			}
		},"json");
	}
	
	function sch() {
		form1.target='listExecution';
		form1.submit();
	}
	</script>
</html>