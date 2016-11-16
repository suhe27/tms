<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Suite List</title>
		
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form id="form1" name="form1" method="post" action="listTestSuites.action" target="suiteslist">
			<table class="mobile" height="7%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Test Suite List</TD>
				</tr>
				<tr class="pagesearch" height="3%">
					<td align="center">
						Project:
					<select id="projectId" name="projectId">
						<option value="-1">All</option>
					    <s:iterator value="#projectList" id="project">	
									<option value="${project.projectId}" >${project.projectName}</option>
								
							</s:iterator>
					</select>
						<input type="button" class="btn" value="Search" onClick="sch();" />
						
					</td>
				</tr>
			</table>
			<iframe  id="list" name="suiteslist" frameborder="0" height="88%" width="100%" scrolling="auto"></iframe>
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
 	function addTestCase() {
 		MyWindow.OpenCenterWindowScroll('toAddTestCase.action','dataRemark',600,800);
 	}
 	function importTestCase() {
 		MyWindow.OpenCenterWindowScroll('toImportTestCase.action','dataRemark',600,800);
 	}
	</script>
</html>