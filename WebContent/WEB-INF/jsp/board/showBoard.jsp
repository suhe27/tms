<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${board.boardName}</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body>

<table class="form_table">
	<tr>
		<th colspan="2" class="form_head">Board Detail</th>
	</tr>
	<tr>
		<td class="tdl" width="150">Board Id:</td>
		<td>${board.boardId}</td>
	</tr>
	<tr>
		<td class="tdl">Board Name:</td>
		<td>${board.boardName}</td>
	</tr>
	<tr>
		<td class="tdl">Crb Type:</td>
		<td>${board.crbType}</td>
	</tr>

	<tr>
		<td class="tdl">ixano:</td>
		<td>${board.ixaNo}</td>
	</tr>
	<tr>
		<td class="tdl">Location:</td>
		<td>${board.location}</td>
	</tr>
	<tr>
		<td class="tdl">Ip Address:</td>
		<td>${board.ipAddr}</td>
	</tr>
	<tr>
		<td class="tdl">Power Cycle:</td>
		<td>${board.powerCycle}</td>
	</tr>
	<tr>
		<td class="tdl">OS:</td>
		<td>${board.os}</td>
	</tr>
	<tr>
		<td  class="tdl">Package Info:</td>
		<td>${board.packageInfo}</td>
	</tr>
	<tr>
		<td class="tdl">Remote Host:</td>
		<td>${board.remoteHost}</td>
	</tr>
	<tr>
		<td class="tdl">Smart Bit Or STC:</td>
		<td>${board.smartBitOrSTC}</td>
	</tr>
	<tr>
		<td class="tdl">Bios Version:</td>
		<td>${board.biosVersion}</td>
	</tr>
	<tr>
		<td class="tdl">Ear:</td>
		<td>${board.ear}</td>
	</tr>
	<tr>
		<td class="tdl">Silicon Type:</td>
		<td>${board.siliconType}</td>
	</tr>
	<tr>
		<td class="tdl">Silicon Number:</td>
		<td>${board.siliconNum}</td>
	</tr>
	<tr>
		<td class="tdl">Start Date:</td>
		<td>${board.startDate}</td>
	</tr>
	<tr>
		<td class="tdl">End Date:</td>
		<td>${board.endDate}</td>
	</tr>
	<tr>
		<td class="tdl">Team:</td>
		<td class="alignl">
			<s:iterator value="#teamList" id="team" status="st">
				<s:if test="#team.teamId == #board.teamId">
				${team.teamName }
				</s:if>				
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td class="tdl">User:</td>
		<td class="alignl">
			<s:iterator value="#userList" id="u" status="st">
				<s:if test="#u.userId == #board.userId">
				${u.userName }
				</s:if>				
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td class="tdl">Board State:</td>
		<td class="alignl">
			<s:iterator value="#boardStateList" id="b" status="st">
				<s:if test="#b.stateId == #board.stateId">
				${b.stateName}
				</s:if>				
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td class="tdl">Comments:</td>
		<td>${board.comments}</td>
	</tr>
</table>

</body>
</html>