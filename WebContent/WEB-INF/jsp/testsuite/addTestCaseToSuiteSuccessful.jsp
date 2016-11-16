<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add TestCase To  TestSuite Successful</title>
</head>
<body>
	<script type="text/javascript">
		window.onload=function(){
			//alert("Add TestCase To TestSuite Successful!");
			window.opener.parent.form1.submit();
			var frames = window.opener.parent.parent.window.document.all;
			for(i = 0;i<frames.length;i++){
				if(frames[i].tagName=="IFRAME"){
					var frame = frames[i];
					var src = frame.src;
					if(src.indexOf("listTestPlan_iframe.action")>-1){
						frame.contentWindow.document.location.reload();
					}
				}
			}
			window.close();
		}
	</script>
</body>
</html>