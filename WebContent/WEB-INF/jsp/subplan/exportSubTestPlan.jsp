<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Export SubTestPlan</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/subplan.js"></script>

</head>
<body>
<form action="exportSubTestPlanToXml.action" method="post"	onsubmit="return checkExportSubTestPlan();">
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">Test Case Detail</th>
	</tr>
	<tr>
		<td class="tdl" width="150">SubPlan Info:</td>
		<td class="td_alignl"><input type="radio" name="subPlanId"
			value="${subPlan.subPlanId }" disabled="disabled"  checked="checked"/> <input
			type="hidden" name="subPlanId" value="${subPlan.subPlanId }" />
		<label >${subPlan.subPlanName }</label></td>
	</tr>
	<tr>
		<td class="tdl"> TestUnits Info:</td>
		<td class="td_alignl">
		<s:iterator value="#testUnitList" id="testUnit" status="st">
			<input type="checkbox" name="multiTestUnits" value="${testUnit.testUnitId }" checked="checked" id="inp_<s:property value='#st.index'/>"/>
			<label for="inp_<s:property value='#st.index'/>">${testUnit.testUnitName }</label>
			<br />
		</s:iterator>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><span  style="float: left;"><input type="submit" value="Export" class="btn1"/></span>
		</td>
	</tr>
</table>
</form>
</body>
</html>