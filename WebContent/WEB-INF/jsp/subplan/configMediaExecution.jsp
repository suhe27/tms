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
<title>Configuration ExecitonOS</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/os.js"></script>
</head>
<script>
	$(document).ready(function() {
		var osId = $("#osId").val();
		if (osId != '') {
			document.getElementById("detailosTable").style.display = "block";
		}

	});

	
	
	function checkOS() {
		var projectId = document.getElementById("projectName").value.trim();
		var hostName = document.getElementById("hostName").value.trim();
		var ip = document.getElementById("ip").value.trim();
		var loginName = document.getElementById("loginName").value.trim();
		var passwd = document.getElementById("passwd").value.trim();
		var port = document.getElementById("port").value.trim();
		var codePath = document.getElementById("codePath").value.trim();
		var cmds = document.getElementById("cmds").value.trim();
		var parameters = document.getElementById("parameters").value.trim();

	
		 if (projectId == '') {
			alert("Project can not null !");
			return false;

		} else if (hostName == '') {
			alert("HostName can not null !");
			return false;
		
		} else if (ip == '') {
			alert("Ip can not null !");
			return false;
		} else if (loginName == '') {
			alert("LoginName can not null !");
			return false;
		} else if (passwd == '') {
			alert("Passwd can not null !");
			return false;
		} else if (port == '') {
			alert("Port can not null !");
			return false;
		} else if (codePath == '') {
			alert("CodePath can not null !");
			return false;
		} else if (cmds == '') {
			alert("Cmds can not null !");
			return false;
		} else if (parameters == '') {
			alert("Parameters can not null !");
			return false;
		} 
		else {
			return true;
		}
	
	}
	function showOsDetail() {

		document.executionForm.submit();

	}
	function submitForm() {
		if(document.getElementById("osId").value.trim()==''){
			alert("ExecutionOs Name can not null");
			return false;
		}
 	
		if(checkOS()==true){
			document.executionForm.action = "runMediaConfiguration.action";
			document.executionForm.submit();
		} else{
			alert("dd");
			document.getElementById("detailosTable").style.display = "none";
			document.getElementById("addQuestion").style.display = "block";
		}
		
	
	}
	
	function showUpdatPage(){
		document.getElementById("detailosTable").style.display = "none";
		document.getElementById("addQuestion").style.display = "block";
	}
	
	
</script>
<!-- action="runMediaConfiguration.action" -->
<body>
	<form name="executionForm" method="post"
		action="toRunMediaConfiguration.action">
		<input type="hidden" name="subId"  value="${subId }"></input>
		
		
		
		<input type="hidden" name="msg"  value="1"></input>
	
		<table id="common_table" class="form_table">
			<tr>
				<th colspan="2" class="form_head">Configuration Page   </th>
			</tr>

			<tr align="center">
				<td align="center" width="180px">ExecutionOs:</td>
				<td class="alignl" align="center">
				
					<select name="osId" id="osId">
						<option value="" onclick="showOsDetail()">--Select--</option>
						<s:iterator value="#executionOSs" id="executionOs">
							<s:if test="#osId==#executionOs.osId">
								<option value="${executionOs.osId }" selected="selected"
									onclick="showOsDetail()">${executionOs.osName }</option>
									
							</s:if>
							<s:else>
								<option value="${executionOs.osId }" onclick="showOsDetail()">${executionOs.osName }</option>
								
							</s:else>
						
						</s:iterator>
				</select></td>


			</tr>
		</table>




		<div id="detailosTable" style="display: none">
			<table id="common_table" class="form_table">



				<tr>
					
					<td width="180px">Project</td>
					<td width="180px">HostName</td>
					<td width="180px">IP</td>
					<td width="180px">LoginName</td>
					<td width="180px">Passwd</td>
					<td width="180px">Port:</td>
					<td width="180px">CodePath</td>
					<td width="180px">Cmds</td>
					<td width="180px">Parameters</td>
					<td width="180px">Operation</td>

				</tr>


			<tr>
				
					<td width="180px">${os.projectName}</td>
					<td width="180px">${os.hostName}</td>
					<td width="180px">${os.ip}</td>
					<td width="180px">${os.loginName}</td>
					<td width="180px"> ${os.passwd}</td>
					<td width="180px">${os.port}</td>
					<td width="180px">${os.codePath}</td>
					<td width="180px">${os.cmds}</td>
					<td width="180px">${os.parameters}</td>
					<td width="180px"><a class="j_title"
				href="javascript:showUpdatPage()">Update</a></td>
        
				</tr>


			</table>
		</div>


		<div id="addQuestion" style="display: none">
			<table id="common_table" class="form_table">

				<tr>
					<td width="180px">Project:</td>
					<td class="alignl"><input type="text"
						name="executionOS.projectName" id="projectName"
						value="${os.projectName}" readonly="readonly" /></td>
				</tr>

				<tr>
					<td width="180px">HostName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.hostName" id="hostName" value="${os.hostName}" /><span
						style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">IP:</td>
					<td class="alignl"><input type="text" name="executionOS.ip"
						id="ip" value="${os.ip}" /><span style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">LoginName:</td>
					<td class="alignl"><input type="text"
						name="executionOS.loginName" id="loginName"
						value="${os.loginName}" /><span style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Passwd:</td>
					<td class="alignl"><input type="text"
						name="executionOS.passwd" id="passwd" value="${os.passwd}" /><span
						style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Port:</td>
					<td class="alignl"><input type="text" name="executionOS.port"
						id="port" value="${os.port}" /><span style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">CodePath:</td>
					<td class="alignl"><input type="text"
						name="executionOS.codePath" id="codePath" value="${os.codePath}" /><span
						style="color: red;"> *</span></td>
				</tr>
				<tr>
					<td width="180px">Cmds:</td>
					<td class="alignl"><input type="text" name="executionOS.cmds"
						id="cmds" value="${os.cmds}" /><span style="color: red;"> *</span></td>
				</tr>

				<tr>
					<td width="180px">Parameters:</td>
					<td class="alignl"><input type="text"
						name="executionOS.parameters" id="parameters"
						value="${os.parameters}" /><span style="color: red;"> *</span></td>
				</tr>


			</table>
		</div>


		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value=" Submit " onclick="submitForm()"
				class="btn" /></td>
		</tr>
		</table>
		<br />
	</form>
</body>
<script type="text/javascript">
	String.prototype.trim = function() {
		return Trim(this);
	};
	function LTrim(str) {
		var i;
		for (i = 0; i < str.length; i++) {
			if (str.charAt(i) != " " && str.charAt(i) != " ")
				break;
		}
		str = str.substring(i, str.length);
		return str;
	}
	function RTrim(str) {
		var i;
		for (i = str.length - 1; i >= 0; i--) {
			if (str.charAt(i) != " " && str.charAt(i) != " ")
				break;
		}
		str = str.substring(0, i + 1);
		return str;
	}
	function Trim(str) {
		return LTrim(RTrim(str));
	}
	function validation() {
		var port = document.getElementById("port").value.trim();
		var ipAddr = document.getElementById("ip").value.trim();
		var ipRegex = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;
		var siliconNumRegx = /^[1-9]|[1-9]\d*$/;
		if (ipAddr == '') {
			alert("Ip Address error ,please check!");
			return false;
		}
		if (port == '') {
			alert("port error ,please check!");
			return false;
		}
		if (!ipRegex.test(ipAddr)) {
			alert("Ip Address error ,please check!");
			return false;
		} else if (!siliconNumRegx.test(port)) {
			alert("invalide port value just only can positive integrety,please check!");
			return false;
		}

		else {
			return true;
		}

	}
</script>
</html>