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
<a href="javascript:closepopup()">Close this Window</a>
<script type="text/javascript">
	window.onload = closepopup();
	window.onload = function() {
		var par = window.opener.parent.parent;
		
	/* 	var pro = par.document.getElementById("projectId").value;
		if (null != pro && "" != pro) {
			par.serReleaseCycle(pro);
		} */
		window.opener.location.reload();
		window.opener.parent.frames.planlist.location.reload();
		window.close();opener.window.focus();
		this.focus();
		self.opener=this;
		self.close();
		window.open('','_self').close();
	}
	   function closepopup()
	   {
		   
		   window.close();
		   opener.window.focus();
	   }
</script>


</body>
</html>