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
<title>UpLoad SubTestPlan</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body>


<form action="uploadSubTestPlan.action" enctype="multipart/form-data"
	method="post" id="fileform">

<table class="form_table">

	<tr>
		<th colspan="2" class="form_head">upload subTestPlan</th>
	</tr>
	<tr id="tr_temp">
		<td>filename:</td>
		<td><input type="file" name="upload" id="fileArea"></input>
		&nbsp;</td>
	</tr>
</table>
<table class="form_table">
	<tr>
		<td style="border-right:none;">
		</td>
		<td>
			<input type="submit" value="submit" class="btn1"></input>
		</td>
	</tr>
</table></form>
<h3 style="color: blue;">${successMsg }</h3>
<h3 style="color: red;">${errorMsg }</h3>

</body>
</html>