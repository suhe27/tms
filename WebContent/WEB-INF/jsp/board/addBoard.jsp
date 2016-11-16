<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Board</title>
<link href="<%=basePath %>css/mobile_main.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath %>scripts/board.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/newcalendar.js"></script>

</head>
<body>
<form action="addBoard.action" method="post" onsubmit="return checkBoard();">

<table class="form_table">
	<tr>
		<th colspan="2" class="form_head">Add Board</th>
	</tr>
	<tr>
		<td width="150" class="tdl">Board Name:</td>
		<td><input type="text" name="board.boardName"
			id="boardname" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td class="tdl">CRB Type:</td>
		<td><input type="text" name="board.crbType"
			id="crbType" /></td>
	</tr>

	<tr>
		<td class="tdl">IXA Number:</td>
		<td><input type="text" name="board.ixaNo"
			id="ixaNo" /></td>
	</tr>
	<tr>
		<td class="tdl">Location:</td>
		<td><input type="text" name="board.location"
			id="location" /></td>
	</tr>
	<tr>
		<td class="tdl">IP Address:</td>
		<td class="alignl"><input type="text" name="board.ipAddr"
			id="ipAddr" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td class="tdl">Power Cycle:</td>
		<td class="alignl"><input type="text" name="board.powerCycle"
			id="powerCycle" /></td>
	</tr>
	<tr>
		<td class="tdl">OS:</td>
		<td><input type="text" name="board.os" id="os" /></td>
	</tr>
	<tr>
		<td class="tdl">Package Info:</td>
		<td><input type="text" name="board.packageInfo"
			id="packageInfo" /></td>
	</tr>
	<tr>
		<td class="tdl">Remote Host:</td>
		<td><input type="text" name="board.remoteHost"
			id="remoteHost" /></td>
	</tr>
	<tr>
		<td class="tdl">Smart Bit or STC:</td>
		<td><input type="text" name="board.smartBitOrSTC"
			id="boardName" /></td>
	</tr>
	<tr>
		<td class="tdl">Bios Version:</td>
		<td><input type="text" name="board.biosVersion"
			id="biosVersion" /></td>
	</tr>
	<tr>
		<td class="tdl">Ear:</td>
		<td><input type="text" name="board.ear" id="ear" /></td>
	</tr>
	<tr>
		<td class="tdl">Silicon Type:</td>
		<td><input type="text" name="board.siliconType"
			id="siliconType" /></td>
	</tr>
	<tr>
		<td class="tdl">Silicon Number:</td>
		<td><input type="text" name="board.siliconNum"
			id="siliconNum" /></td>
	</tr>
	<tr>
		<td class="tdl">Start Date:</td>
		<td><input type="text" name="startDate"
			id="startdate" size="20" maxlength="20"
			onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);" size="20"
			maxlength="20" readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="tdl">End Date:</td>
		<td><input type="text" name="endDate"
			id="enddate" onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);"
			size="20" maxlength="20" readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="tdl">Team:</td>
		<td><select name="board.teamId">
			<s:iterator value="#teamList" id="team" status="st">
				<option value="${team.teamId }">${team.teamName }</option>
			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td class="tdl">Comments:</td>
		<td><input type="text" name="board.comments"
			id="comments" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="add" class="btn1"/></td>
	</tr>
</table>
</form>
</body>
</html>