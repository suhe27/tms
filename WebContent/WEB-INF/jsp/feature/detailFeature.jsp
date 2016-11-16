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
<title>Detail Feature</title>

<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/feature.js"></script>
</head>
<body >


<form action="updateFeature.action" method="post"
	onsubmit="return checkFeature();" >
	<input type="text" name="featureId" size="35" readonly="readonly" value="${feature.featureId}" style="display:none"/>
<table id="common_table"  class="form_table">

	<tr>
		<td width="180px">Feature Name:</td>
		<td class="alignl"><input type="text" name="featureName" size="35" id="featureName"
			value="${feature.featureName}" /></td>
	</tr>
	

	
	<tr>
		<td width="180px">Project:</td>
		<td class="alignl"><select name="projectId" id="ProjectId">
			<s:iterator value="#projectList" id="project">
			<s:if test="#project.projectId == #feature.projectId">
				<option value="${project.projectId }" selected="selected">${project.projectName }</option>
			</s:if>
			<s:else>
				<option value="${project.projectId }">${project.projectName }</option>
			</s:else>
			</s:iterator>
		</select></td>
	</tr>
	
	
	<tr>
		<td width="180px">Select Component(s):</td>
		<td class="alignl"><s:iterator id="comp" value="#compList"
			status="st">
			<s:set name="flag" value="false" />	
			<s:iterator id="c" value="#ownerCompList" status="sx">

				<s:if test="#comp.compId == #c.compId ">
					<div style="width:150px;float:left;"><input id="input_${comp.compId }" type="checkbox" name="multiComps"
						value="${comp.compId }" checked="checked" style="width: 30px;" />
						<label for="input_${comp.compId }">${comp.compName}</label></div>
				<s:set name="flag" value="true" />			
				</s:if>
			</s:iterator>
			
			<s:if test="#flag ==false">
				<div style="width:150px;float:left;"><input id="input_${comp.compId }" type="checkbox" name="multiComps"
						value="${comp.compId }" style="width: 30px;" />
						<label for="input_${comp.compId }">${comp.compName}</label></div>
			</s:if>		
		</s:iterator>
		</td>
	</tr>
</table>
<table class="form_table">
	<tr>
		<td style="border-right: none;"></td>
		<td><input type="submit" value="update" class="btn1" /></input></td>
	</tr>
</table>
</form>



</body>
</html>