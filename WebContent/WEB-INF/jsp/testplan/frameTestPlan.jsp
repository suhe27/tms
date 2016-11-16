<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Plan</title>
	</head>
	<body class="mobile">
		<form id="form1" name="form1" method="post" action="listTestPlans.action" target="listcases">
			<table class="mobile" height="7%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Test Plan List</TD>
				</tr>
				<tr class="pagesearch" height="3%">
					<td align="center">
				
					Project:
				<select   id="projectId" name="projectId" onchange="getPhaseList(this.value);">
						<option value="-1">- Select a value-</option>
							<s:iterator value="#projectList" id="project"  >	
									<option value="${project.projectId}" >${project.projectName}</option>
									
						</s:iterator>
					</select>
				
				
				
			Phase:
		<select id="phaseId" name="phaseId">
		<option value="-1">- Select a value-</option>
		<!--<s:iterator value="#phaseList" id="phase">
		<option value="${phase.phaseId }" >${phase.phaseName}</option>
		</s:iterator>
		--></select>
					PlanName:
					<input type="text"  size="10" name="planName"/>	
					<input type="button" class="btn" value="Search" onClick="sch();" />
					</td>
				</tr>
			</table>
			<iframe  id="listcases" name="listcases" frameborder="0" height="88%" width="100%" scrolling="auto"></iframe>
		</form>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		form1.target='listcases';
		form1.submit();
	});
	
	function sch() {
		form1.target='listcases';
		form1.submit();
	}

	function getPhaseList(pro){
		var com = document.getElementById("phaseId");
		com.options.length = 0;
		var o = document.createElement("option");
		o.value= "";
		o.text = "- Select a value -";
		com.add(o);;
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
</script>
	
</html>