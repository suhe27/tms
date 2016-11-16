<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add TestUnit Successful</title>
</head>
<body>
	<script type="text/javascript">
	window.onload=function(){
		alert("Successfully add Test Unit !");
		window.opener.location.reload();
		window.opener.opener.location.reload();
		window.opener.opener.parent.planlist.location.reload();
		window.close();
	}
	</script>
</body>
</html>