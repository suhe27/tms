<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Execution</title>
</head>
<body>
	<script type="text/javascript">
		window.onload=function(){
			//window.parent.location = document.referrer;
			window.location.href = document.referrer;
			window.parent.form1.submit();
			window.parent.list.location="";
			window.parent.location.reload();
			window.parent.list.location.reload();
		}
	</script>
</body>
</html>