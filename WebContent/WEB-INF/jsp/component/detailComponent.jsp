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
<title>Component</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/component.js"></script>
</head>
<body>
<form action="updateComponent.action" method="post"
	onsubmit="return checkComponent();">
	<input type="text" name="compId" size="35" readonly="readonly" value="${comp.compId}" style="display:none"/>
<table id="common_table"  class="form_table">

	<tr>
		<td width="180px">Component Name:</td>
		<td class="alignl"><input type="text" name="compName" size="35" id="compName"
			value="${comp.compName}" /></td>
	</tr>
	<tr>
		<td width="180px">Select SubComponent(s):</td>
		<td class="alignl"><s:iterator id="com" value="#subCompList"
			status="st">
			<s:set name="index" value="#st.index + 1" />
			<s:set name="modVal" value="#index % 2" />
			<s:set name="flag" value="false" />	
			<s:iterator id="c" value="#ownerSubCompList" status="sx">

				<s:if test="#com.subCompId == #c.subCompId ">
					<div style="width:150px;float:right;"><input id="input_${com.subCompId }" type="checkbox" name="multiSubCompId"
						value="${com.subCompId }" checked="checked" style="width: 30px;" />
						<label for="input_${com.subCompId}">${com.subCompName}</label></div>
				<s:set name="flag" value="true" />			
				</s:if>
			</s:iterator>
			
			<s:if test="#flag ==false">
				<div style="width:150px;float:right;"><input id="input_${com.subCompId }" type="checkbox" name="multiSubCompId"
						value="${com.subCompId }" style="width: 30px;" />
						<label for="input_${com.subCompId }">${com.subCompName}</label></div>
			</s:if>		
			<s:if test="#modVal ==0">
			<br/>
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