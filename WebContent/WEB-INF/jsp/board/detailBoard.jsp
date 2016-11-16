<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail Board</title>
<script type="text/javascript">
function test()
{
alert($('#startdate').val());
alert($('#enddate').val());
}

</script>
<link href="css/index.css" rel="stylesheet" type="text/css"></link>
<link href="<%=basePath %>css/mobile_main.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath %>scripts/board.js"></script>
</head>
<body>
<form action="updateBoard.action" method="post"
	onsubmit="return checkBoard();"><input type="hidden"
	name="board.userId" size="35" readonly="readonly"
	value="${board.userId}" /> <input type="hidden" name="board.stateId"
	size="35" readonly="readonly" value="${board.stateId}" /> <input
	type="hidden" name="board.startDate" size="35" readonly="readonly"
	value="${board.startDate}" /> <input type="hidden"
	name="board.endDate" size="35" readonly="readonly"
	value="${board.endDate}" />
<table id="common_table" class="form_table">
	<tr>
		<td class="tdl" width="150">Boardid:</td>
		<td><input type="text" name="board.boardId" size="35"
			readonly="readonly" value="${board.boardId}" /></td>
	</tr>
	<tr>
		<td class="tdl">Board Name:</td>
		<td><input type="text" name="board.boardName"
			id="boardname" value="${board.boardName}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Crb Type:</td>
		<td><input type="text" name="board.crbType"
			id="crbType" value="${board.crbType}" size="35" /></td>
	</tr>

	<tr>
		<td class="tdl">ixano:</td>
		<td><input type="text" name="board.ixaNo"
			id="ixaNo" value="${board.ixaNo}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Location:</td>
		<td><input type="text" name="board.location"
			id="location" value="${board.location}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Ip Address:</td>
		<td><input type="text" name="board.ipAddr"
			id="ipAddr" value="${board.ipAddr}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Power Cycle:</td>
		<td><input type="text" name="board.powerCycle"
			id="powerCycle" value="${board.powerCycle}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">OS:</td>
		<td><input type="text" name="board.os" id="os"
			value="${board.os}" size="35" /></td>
	</tr>
	<tr>
		<td  class="tdl">Package Info:</td>
		<td><input type="text" name="board.packageInfo"
			id="packageInfo" value="${board.packageInfo}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Remote Host:</td>
		<td><input type="text" name="board.remoteHost"
			id="remoteHost" value="${board.remoteHost}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Smart Bit Or STC:</td>
		<td><input type="text" name="board.smartBitOrSTC"
			id="boardName" value="${board.smartBitOrSTC}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Bios Version:</td>
		<td><input type="text" name="board.biosVersion"
			id="biosVersion" value="${board.biosVersion}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Ear:</td>
		<td><input type="text" name="board.ear" id="ear"
			value="${board.ear}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Silicon Type:</td>
		<td><input type="text" name="board.siliconType"
			id="siliconType" value="${board.siliconType}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Silicon Number:</td>
		<td><input type="text" name="board.siliconNum"
			id="siliconNum" value="${board.siliconNum}" size="35" /></td>
	</tr>
	<tr>
		<td class="tdl">Start Date:</td>
		<td><input type="text" name="startDate"
			id="startdate" value="${board.startDate}"
			onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);" size="20"
			maxlength="20" readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="tdl">End Date:</td>
		<td><input type="text" name="endDate"
			id="enddate" value="${board.endDate}"
			onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);" size="20"
			maxlength="20" readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="tdl">Team:</td>
		<td class="alignl"><select name="board.teamId">
			<s:iterator value="#teamList" id="team" status="st">
				<s:if test="#team.teamId == #board.teamId">
					<option value="${team.teamId }" selected="selected">${team.teamName
					}</option>
				</s:if>
				<s:else>
					<option value="${team.teamId }">${team.teamName }</option>
				</s:else>

			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td class="tdl">Comments:</td>
		<td><input type="text" name="board.comments"
			id="comments" value="${board.comments}" /></td>
	</tr>
	<s:if test="#session.user.level== 9 || #session.user.level >=2 ">
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" class="btn1"/></td>
	</tr>
	</s:if>
</table>
</form>
</body>
</html>