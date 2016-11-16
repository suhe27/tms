<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/jsp/include/include.jsp" />
<title></title>
</head>
<body>

<div
	style="margin-top:0px;width:100%;height:55px;background:url('<%=basePath%>images/top_bg.gif');">
<div
	style="margin-left: 100px; margin-top: 15px; width: 500px; height: 35px; font-size: 26px; color: #ffffff; font-weight: bold;">iTMS</div>
<div style="clear: none; float: right;"><img
	src="<%=basePath%>/images/ad.jpg" /></div>
<div
	style="clear: none; float: right; width: 250px; height: 25px; margin-top: 25px; text-align: center; color: #000000;">
<s:if test="#session.user == null ">
	<a class="a_w" href="toLogin.action" target="_top"
		style="color: #000000;">login</a>
</s:if> <s:else>
			Welcome: ${session.user.userName}|
			<a href="<%=basePath %>logout.action" class="a_w" target="_top"
		style="color: #000000;">logout</a>
</s:else></div>
</div>
</body>
</html>