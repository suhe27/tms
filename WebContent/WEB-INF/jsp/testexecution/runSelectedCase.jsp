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
<form action="runSelectedCase.action" method="post" onsubmit="return checkForm()">
	

<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Run Sub Execution</th>
	</tr>
	<tr>
		<td>Sub Execution Name</td>
		<td>${subExecution.subExecutionName}</td>
	</tr>
	<tr>
		<td>IP</td>
		<td><input name="ip"  id="ip"></input><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td>Port</td>
		<td><input name="port"  id="port"></input><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value=" Run "
			class="btn1" /></td>
	</tr>
</table>
		<input name="subExecutionId" id="subExecutionId" value="${subExecution.subExecutionId}" style="display:none"/>
		<input name="multiTestExecution" id="multiTestExecution" value="${multiTestExecution}" style="display:none"/>
</form>
<h3 style="color:red;">${ERRORMSG }</h3>
</body>
<script type="text/javascript">

	function checkForm(){
		
		var ip = document.getElementById("ip").value.trim();
		var port = document.getElementById("port").value.trim();
		var integer = /^[0-9]*[1-9][0-9]*$/;
		
		if ( check_IP(ip) == false ) {
			alert("Please input correct IP address");
			return false;
		}  else if (!integer.test(port)) {
			alert("Port can only be numbers !");
			return false;
		}
	}
	
	function check_IP(ip)    
	{  
		   var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式   
		   if(re.test(ip))   
		   {   
		       if(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) 
		       { return true; }
		       else {return false;}
		   }   
		   else { return false; }
	}
</script>

</html>