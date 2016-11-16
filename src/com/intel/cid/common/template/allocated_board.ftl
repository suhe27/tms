<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Build Start</title>

<style type="text/css">
#tbrequest {
	width: 80%;
	vertical-align: top;
	background-color: #EEEEEE;
}

#tbrequest tr td.alignr {
	text-align: right;
	vertical-align: top;
	width: 50px;
}
</style>
</head>
<body>

	Hi ${user.userName},
	<br />
	<font color="red"><a href="mailto:${user.email}">${user.email}</a></font> has ${operation} ${num} board(s).
	<br />
	<br />
	<table id="tbrequest" style="border-collapse: collapse;" border="1">
		
		<th>BOARDNAME</th>		
		<th>IPADDR</th>
		<th>USER</th>
		<th>STATE</th>
		<th>STARTDATE</th>
		<th>ENDDATE</th>
		<th>COMMENTS</th>
		<#list boardList as board>
 		<tr>
			<td>${board.boardName}</td>
			<td>${board.ipAddr}</td>
			<td>${board.user.userName}</td>
			<td>${board.boardState.stateName}</td>
			<td>${board.startDate}</td>
			<td>${board.endDate}</td>
			<td>${board.comments}</td>
		</tr>		
  </#list>	
	</table>
	<br />
	<br />
	<br />For more detailed information,
	<a href="${addr}/iTMS/listBoards.action">click
		here</a>.
	<br />
	<br /> Best regards,
	<br /> iTMS Server

</body>
</html>