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
<form action="addSubExecution.action" method="post" onsubmit="return checkSubExecutionForm()">
	
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add Sub Test Execution</th>
	</tr>
	<tr>
		<td>Sub Execution Name</td>
		<td><input name="subexecution.subExecutionName"  id="subExecutionName"></input><span style="color: red;">*</span></td>
	</tr>

	<tr>
		<td width="150">Paired Test Plan</td>
		<td><input type="radio" name="subexecution.testPlanId" value="${subexecution.testPlanId}" disabled="disabled" checked="checked" />
			<label>${subexecution.testPlanName}</label>
		</td>
	</tr>
	<tr>
		<td width="150">Sub Plan</td>
		<td><select id="subPlanId" name="subexecution.subPlanId">
			<option value="">- Select a value-</option>
				<s:iterator value="#subTestPlanList" id="subTestPlan">
						<option value="${subTestPlan.subPlanId}" >${subTestPlan.subPlanName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td width="150">Select Platform</td>
		<td>
		<select id="platform" name="subexecution.platformId">
			<option value="">- Select a value-</option>
				<s:iterator value="#platformList" id="platform">
						<option value="${platform.platformId}" >${platform.platformName}</option>
				</s:iterator>
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td width="150">Select ExecutionOs</td>
		<td><select id="os" name="subexecution.osId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#osList" id="os">
						<option value="${os.osId}" >${os.osName}</option>
				</s:iterator>
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
	<td width="150">Tester</td>
		<td><select id="tester" name="subexecution.tester" >
			<option value="">- Select a value-</option>
				<s:iterator value="#userList" id="user">
						<option value="${user.userId}" >${user.userName}</option>
				</s:iterator>
	
		</select>
		<span style="color: red;">*</span></td>
	</tr>
	<tr>
	<tr>
		<td width="150">Test Execution Duration</td>
		<td>
				<input name="ExeStartDate"  id="ExeStartDate" value="${testExecution.startDate}" disabled="disabled" size="10"/>->
				<input name="ExeEndDate"  id="ExeEndDate" value="${testExecution.endDate}" disabled="disabled" size="10"/>
		</td>
	</tr>	
	<tr>
		<td>Due Date</td>
		<td><input name="subexecution.dueDate"
			id="dueDate" readonly="readonly"></input>
			<span style="color: red;"> *</span>
		</td>
	</tr>
	<tr>
		<td>Description</td>
		<td>
		<textarea name="subexecution.subExecutionDesc"  id="desc"  cols=35 rows=2 ></textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" Add " class="btn1" /></td>
	</tr>
</table>
		<input name="subexecution.id" id="id" value="${subexecution.id}" style="display:none"/>
</form>
</body>
<script type="text/javascript">
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
		o.value= 0;
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
		
	}
	
</script>

</html>