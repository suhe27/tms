<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Feature</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/feature.js"></script>
</head>
<body>

<form action="addFeature.action" method="post"
	onsubmit="return checkFeature();">

<table id="common_table"  class="form_table">


	<tr>
		<td class="180px">Feature Name:</td>
		<td class="alignl"><input type="text" name="featureName"
			id="featureName" /><span style="color:red;">*</span></td>
			
		<tr>	
			<td width="180px">Project:</td>
		<td class="alignl"><select name="projectId" id="projectId">
			<s:iterator value="#projectList" id="project">
				<option value="${project.projectId }">${project.projectName }</option>
			</s:iterator>
		</select></td>
	</tr>
	
	<tr>
		<td width="180px">Select Component(s):</td>
		<td class="alignl" >
		<s:iterator id="comp" value="#compList">
				<div style="width:150px;float:left;"><input id="input_${comp.compId }" type="checkbox" name="multiComps" value="${comp.compId }" style="width:30px;"/>
				<label for="input_${comp.compId }">${comp.compName}</label></div>		
		</s:iterator>



	</tr>
	
	<%-- <tr>
		<td width="180px">Select Project(s):</td>
		<td class="alignl" >
		
		<s:iterator id="project" value="#projectList">
				<div style="width:150px;float:left;"><input id="input_${project.projectId }" type="checkbox" name="multiProjectforms" value="${project.projectId }" style="width:30px;"/>
				<label for="input_${project.projectId }">${project.projectName }</label></div>	
		</s:iterator>

	</tr> --%>
	
	
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