<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Execution</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile"> 
		<form id="form1" name="form1" method="post" action="executionReportChartingDetail.action" target="listExecution">
			<table class="mobile" height="10%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Execution Report</TD>
				</tr>
				<tr class="pagesearch">
					<td align="center">
					Project:
					<select onchange="serReleaseCycle(this.value)"  id="projectId" name="projectId">
							<option value="-1">All</option>
							<s:iterator value="#projectList" id="project">	
										<option value="${project.projectId}" >${project.projectName}</option>
							</s:iterator>
					</select>
			
					</td>
				</tr>
			</table>
			<iframe  id="listExecution" name="listExecution" frameborder="0" height="89%" width="100%" scrolling="auto"></iframe>
		</form>
	</body>
	<script type="text/javascript">

	function serReleaseCycle(pro){
		form1.target='listExecution';
		form1.submit();
	}

	</script>
</html>