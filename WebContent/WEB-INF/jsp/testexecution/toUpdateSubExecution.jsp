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
<form action="updateSubExecution.action" method="post" onsubmit="return checkSubExecutionForm()">
	

<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Update Sub Test Execution</th>
	</tr>
	<tr>
		<td>Sub Execution Name</td>
		<td><input name="subexecution.subExecutionName"  id="subExecutionName" value="${subexecution.subExecutionName}"></input><span style="color: red;">*</span></td>
	</tr>

	<tr>
		<td width="150">Paired Sub Test Plan</td>
		<td><input type="radio" name="subexecution.subPlanId" value="${subexecution.subPlanId}" disabled="disabled" checked="checked" />
			<label>${subexecution.subPlanName}</label>
		</td>
	</tr>
	<tr>
		<td width="150">Platform</td>
		<td><input type="radio" name="subexecution.platformId" value="${subexecution.platformId}" disabled="disabled" checked="checked" />
			<label>${subexecution.platFormName}</label>
		</td>
	</tr>
	<tr>
		<td width="150">ExecutionOS</td>
		<td><input type="radio" name="subexecution.osId" value="${subexecution.osId}" disabled="disabled" checked="checked" />
			<label>${subexecution.osName}</label>
		</td>
	</tr>
	<tr>
	<td width="150">Tester</td>
		<td><select id="tester" name="subexecution.tester" >
			<option value="">- Select a value-</option>
				<s:iterator value="#userList" id="user">
					<s:if test="#subexecution.tester == #user.userId">
						<option value="${user.userId }" selected="selected">${user.userName}</option>
					</s:if>
					<s:else>
						<option value="${user.userId }">${user.userName}</option>
					</s:else>
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
		<td>DueDate</td>
		<td><input name="subexecution.dueDate"
			id="dueDate" readonly="readonly" value="${subexecution.dueDate}"></input>
			<span style="color: red;"> *</span>
		</td>
	</tr>
	<tr>
		<td>Description</td>
		<td>
		<textarea name="subexecution.desc"  id="desc"  cols=35 rows=2 >${subexecution.desc}</textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" Update "
			class="btn1" /></td>
	</tr>
</table>
		<input name="subexecution.subExecutionId" id="subExecutionId" value="${subexecution.subExecutionId}" style="display:none"/>
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
	
</script>

</html>