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
<title>Detail Port</title>
<script type="text/javascript">

</script>
<link href="css/index.css" rel="stylesheet" type="text/css"></link>
<link href="<%=basePath %>css/mobile_main.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath %>scripts/ixia.js"></script>
</head>
<body>
<form action="updateIxia.action" method="post"
	onsubmit="return checkIxia();"><input type="hidden"
	name="board.userId" size="35" readonly="readonly"
	value="${board.userId}" /> <input type="hidden" name="board.stateId"
	size="35" readonly="readonly" value="${board.stateId}" /> <input
	type="hidden" name="board.startDate" size="35" readonly="readonly"
	value="${board.startDate}" /> <input type="hidden"
	name="board.endDate" size="35" readonly="readonly"
	value="${board.endDate}" />
	<input type="hidden" name="boardType" value="1"></input>
	<input type="hidden"
	name="board.teamId" size="35" readonly="readonly"
	value="${board.teamId}" />
<table id="common_table" class="form_table">
	<tr>
		<td class="tdl" width="150">Port Id:</td>
		<td><input type="text" name="board.boardId" size="35"
			readonly="readonly" value="${board.boardId}" /></td>
	</tr>
	<tr>
		<td class="tdl">Port Name:</td>
		<td><input type="text" name="board.boardName"
			id="boardname" value="${board.boardName}" size="35" /></td>
	</tr>
	

	<tr>
		<td class="tdl">Connected Board:</td>
		<td class="alignl"><select name="siliconNum">
			<option value="0" >none</option>
			<s:iterator value="boardList" id="b" status="st">
			
				<s:if test="#b.boardId == #board.siliconNum">
					<option value="${b.boardId }" selected="selected">${b.boardName
					}</option>
				</s:if>
				<s:else>
						<option value="${b.boardId}">${b.boardName}</option>
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