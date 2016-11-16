<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Execution</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");

@import url("<%=basePath%>css/ui/jquery.ui.all.css");

@import url("<%=basePath%>css/ui/demos.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
</head>
<body>
<form action="updateSingleTestResult.action" method="post" onsubmit="return checkForm()">
	

<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Update Test Result</th>
	</tr>
	<tr>
		<td>Test Case Name</td>
		<td>${testResult.testCaseName}  </td>
	</tr>
	<tr>
	<td width="150">Result</td>
		<td><select id="resultTypeId" name="testResult.resultTypeId" >
			<option value="">- Select a value-</option>
				<s:iterator value="#resulttypelist" id="resulttype">
					<s:if test="#resulttype.resultTypeId == #testResult.resultTypeId">
						<option value="${resulttype.resultTypeId }" selected="selected">${resulttype.resultTypeName}</option>
					</s:if>
					<s:else>
						<option value="${resulttype.resultTypeId }">${resulttype.resultTypeName}</option>
					</s:else>
				</s:iterator>
		</select>
		</td>	
	</tr>	
	<tr>
		<td>Log</td>
		<td><input name="testResult.log"  id="log" value="${testResult.log}"></input></td>
	</tr>
	<tr>
		<td>BugId/Name</td>
		<td>
			<input name="testResult.bugId"  id="bugId" value="${testResult.bugId}"></input>
			/<input name="testResult.bugName"  id="bugName" value="${testResult.bugName}"></input>
		</td>
	</tr>	
	<tr>	
		<td>Comments</td>
		<td>
		<textarea name="testResult.comments"  id="comments"  cols=35 rows=2 >${testResult.comments}</textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" Update "
			class="btn1" /></td>
	</tr>
</table>
		<input name="testResult.resultId" id="resultId" value="${testResult.resultId}" style="display:none"/>
		<input name="testResult.testCaseId" id="testCaseId" value="${testResult.testCaseId}" style="display:none"/>
</form>
</body>
<script type="text/javascript">
	function checkForm(){
		
		bugId = $("#bugId").val();
		bugName = $("#bugName").val();
		if(bugName.length > 0){
			if(bugId.length == 0){
				alert("BugId can't be null when BugName have value");
				return false;
			}
		}
		
		if(bugId.length>0){

			//bugId = bugId.match(/http:\/\/.+/); 
	        var RegexUrl = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
	        result = RegexUrl.test(bugId);
	        if (!result){
				alert('Invalid URL address for BugId'); 
				return false;
			}
		}
	}
	
</script>

</html>