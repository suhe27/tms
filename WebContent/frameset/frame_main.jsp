<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>iTMS</title>
	<link rel="icon" type="image/gif" href="<%=basePath%>/images/animated_favicon1.gif" >
</head>
<s:action namespace="" name="index" ></s:action>
<frameset rows="55,*" framespacing="0">
	<frame src="<%=basePath %>frameset/frame_top.jsp" frameborder=0 scrolling="no" name="top"   
		border="0" name="top" noresize="noresize" marginwidth="0" marginheight="0"></frame>
	<frameset cols="200,*" frameborder="0">
		<frame src="<%=basePath %>frameset/frame_left.jsp" name="left" frameborder=0 scrolling="no" 
			border="0"  noresize="noresize" marginwidth="0" marginheight="0"></frame>
		<frame src="<%=basePath %>frameset/frame_right.jsp" name="right" id="right" frameborder=0 
			scrolling="no" border="0"  noresize="noresize" marginwidth="0" marginheight="0"></frame>
	</frameset>
</frameset>
</html>