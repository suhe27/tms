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
		<th colspan="2" class="form_head">Port Detail</th>
	</tr>
	<tr>
		<td class="tdl" width="150">Port Id:</td>
		<td>${board.boardId}</td>
	</tr>
	<tr>
		<td class="tdl">Port Name:</td>
		<td>${board.boardName}</td>
	</tr>
	<tr>
		<td class="tdl">Connected Board:</td>
		<td>
		<s:iterator value="boardList" id="b" status="st">
				<s:if test="#b.boardId == #board.siliconNum">
				${b.boardName}
				</s:if>				
			</s:iterator>
		</td>
	</tr>
	
	<tr>
		<td class="tdl">Team:</td>
		<td>
			<s:iterator value="#teamList" id="team" status="st">
				<s:if test="#team.teamId == #board.teamId">
				${team.teamName }
				</s:if>				
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td class="tdl">User:</td>
		<td >
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