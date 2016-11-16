<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>board form</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form id="form1" name="form1" method="post" action="releaseBoards_list.action" target="list">
			<table class="mobile" height="9%" width="100%">
				<tr class="pageheader" height="4%">
					<TD>Board Management</TD>
				</tr>
				<tr class="pagesearch" height="5%">
					<td align="center">
						
						Team:<select
								id="teamId" name="teamId">
								<option value="-1">All</option>
								<option disabled="disabled">--------</option>
								<s:iterator value="#teamList" id="team">
									<s:if test="#team.teamId== #teamId">
										<option value="${team.teamId }" selected="selected">${team.teamName}</option>
									</s:if>
									<s:else>
										<option value="${team.teamId }">${team.teamName}</option>
									</s:else>
								</s:iterator>
							</select>
					
						
						State:<select id="stateId" name="stateId">
						<option value="-1">All</option>
						<option disabled="disabled">--------</option>	
						<s:iterator value="#boardStateList" id="boardstate">	
						<s:if test="#boardstate.stateId == #stateId">
						<option value="${boardstate.stateId}" selected="selected">${boardstate.stateName }</option>
						</s:if>
						<s:else>
						<option value="${boardstate.stateId}">${boardstate.stateName }</option>
						</s:else>
						</s:iterator>
						</select>
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
 	function addBoard() {
 		MyWindow.OpenCenterWindowScroll('toAddBoard.action','dataRemark',600,800);
 	}
 	function importTestCase() {
 		MyWindow.OpenCenterWindowScroll('toImportTestCase.action','dataRemark',600,800);
 	}
	</script>
</html>