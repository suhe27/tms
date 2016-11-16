<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Port form</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form id="form1" name="form1" method="post" action="allocateIxiasList.action" target="list">
			<table class="mobile" height="9%" width="100%">
				<tr class="pageheader" height="4%">
					<TD>Ixia Booking</TD>
				</tr>
				<tr class="pagesearch" height="5%">
					<td align="center">
					<input type="hidden" name="boardType" value="1"></input>
							<input type="text" name="target1" title="fuzzy query ixia ports, multiple query conditions splited with space " size="100" id="target1" ></input>
						<input type="button" class="btn" value="Search" onClick="sch();" />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			<iframe  id="list" name="list" frameborder="0" height="90%" width="100%" scrolling="yes"></iframe>
		</form>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		form1.target='list';
		form1.submit();
	});
	function sch() {
		form1.target='list';
		form1.submit();
	}
 
	</script>
</html>