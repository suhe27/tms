<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style type="text/css">
@import url("<%=basePath%>css/login.css");
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TMS Login Page</title>

<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"> </script>
<script type="text/javascript" src="<%=basePath %>scripts/login.js"> </script>
<script type="text/javascript" src="<%=basePath %>js/jquery.placeholder.js"> </script>
</head>
<body onload="timer();">
<section class="container">
<div class="login">
<h1>Welcome to iTMS</h1>
<form action="login.action" method="post" onsubmit="return checkLogin();" >
	<p><input type="text" name="userName" id="username" value="${username }" placeholder="UserName"/></p>
	<p><input type="password" name="password" id="password" placeholder="Password"/></p>
	<p class="remember_me">
          <label>
            <!--  <input type="checkbox" name="remember_me" id="remember_me">
            Remember me on this computer -->
            
          </label>
    </p>
	<p class="submit1" style="text-align:center;"><input type="submit" name="commit" value="Login"></p>
	<p><label id="errorMsg" style="color:red;">${errorMsg}</label></p>
</form>
</div>
</section>
		<script>

			$(function() {
				// Invoke the plugin
				$('input, textarea').placeholder();
			});
		</script>
</body>
</html>