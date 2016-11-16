<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Test Case Successful</title>
</head>
	
<body>
 <input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	<script type="text/javascript">
		window.onload=function(){
			window.opener.parent.form1.currPage.value =document.getElementById('currPage').value;
			window.opener.parent.form1.submit();
			window.close();
		}
	</script>
</body>
</html>