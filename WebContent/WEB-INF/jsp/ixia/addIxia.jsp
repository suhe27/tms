<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add 	Ixia</title>
<link href="<%=basePath %>css/mobile_main.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath %>scripts/ixia.js"></script>

</head>
<body>
<form action="addIxia.action" method="post" onsubmit="return checkIxia();">

<table   id="common_table" class="form_table">
	<tr>
		<td  width="180px" >Port Name:</td>
		<td class="alignl"><input type="text" name="board.boardName"
			id="boardname" /><span style="color:red;">*</span></td>
	</tr>
	<tr> 
		<td width="180px">Team:</td>
		<td class="alignl"><select name="board.teamId">
			<s:iterator value="#teamList" id="team" status="st">
				<option value="${team.teamId}">${team.teamName }</option>
			</s:iterator>
		</select></td>
	</tr>
	<tr>
		<td width="180px">Comments:</td>
		<td class="alignl"><input type="text" name="board.comments"
			id="comments" /></td>
	</tr>
	
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td><input type="submit" value="add" class="btn1" /></input></td>
	</tr>
</table>
</form>
</body>
</html>