<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Delete TestUnit Successful</title>
</head>
<body>
	<script type="text/javascript">
		window.onload=function(){
			alert('Delete testUnit Success! ');
			window.opener.location.reload();//
			window.opener.parent.planlist.location.reload();
			window.location="toDetailSubTestPlan.action?subPlanId=${subPlanId}";
			window.reload();
		}
	</script>
</body>
</html>