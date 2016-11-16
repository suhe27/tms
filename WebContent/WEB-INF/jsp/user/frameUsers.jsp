<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>User List</title>
		
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form id="form1" name="form1" method="post" action="listUsers.action" target="suiteslist">
			<table class="mobile" height="6%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>User List</TD>
				</tr>
				<tr class="pagesearch" height="3%">
						<td align="center">
					user:
					<select id="userId" name="userId">
						<option value="-1">All</option>
						<s:iterator value="#userList" id="tk">			
							<option value="${tk.userId}" >${tk.userName}</option>			
						</s:iterator>
					</select>			
						team:
					<select id="teamId" name="teamId">
						<option value="-1">All</option>
						<s:iterator value="#teamList" id="fk">			
							<option value="${fk.teamId}" >${fk.teamName}</option>			
						</s:iterator>
					</select>
						<input type="button" class="btn" value="Search" onClick="sch();" />
						
					</td>
					
				</tr>
			</table>
			<iframe  id="list" name="suiteslist" frameborder="0" height="93%" width="100%" scrolling="yes"></iframe>
		</form>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		form1.target='suiteslist';
		form1.submit();
	});
	function sch() {
		form1.target='suiteslist';
		form1.submit();
	}
 	
	</script>
</html>