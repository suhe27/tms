<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>User</title>
</head>
	<body class="mobile">
	<form action="listUsers.action" method="post">
		<input type="hidden" name="teamId" value="${teamId}"></input>
		<input type="hidden" name="userId" value="${userId}"></input>
		<display:table name="userList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" requestURI="listUsers.action">
			<display:caption><thead>
			<tr  class="pageHeader">
			  		<th class="fixTitle" style="width: 5%;" >Row</th>
					<th class="fixTitle" style="width: 15%">User Name</th>
					<th style="width: 30%" class="fixTitle">Belonged Team</th>
					<th style="width: 15%" class="fixTitle">Email</th>
					<th style="width: 15%" class="fixTitle">User Type</th>
					<th style="width: 20%x" class="fixTitle">Operation</th>
			</tr>
			</thead></display:caption>
			<display:column  title="序号" class="fixTitle1"> 
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column  property="userName" title="日期" class="fixTitle1"/>
		
			<display:column  property="teams" title="日期" class="fixTitle1"/>
			<display:column  property="email" title="日期" class="fixTitle1"/>
			<display:column  property="userType" title="日期" class="fixTitle1"/>
			<display:column  title="备注" >
				<a class="j_title" href="javascript:updateUser(${row.userId });">Update</a>
				<a class="j_title" href="javascript:deleteUser(${row.userId },'${row.userName }');">Delete</a>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;border:1px gray dotted;">
			<div style="float:left;width:45%;height:25px;">
				&nbsp;<input type="button" value=" add " class="btn1" onclick="addUser();"></input>
			</div>
			
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="listUsers.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
					<p:param name="teamId" value="#teamId"></p:param>
					<p:param name="userId" value="#userId"></p:param>
				</p:page>
			</div>
			</div>
		</form>
	</body>
	<script type="text/javascript">
		function deleteUser(userId,userName){
			if(confirm("Confirm delete user '"+userName+"'")){
				window.location.href= "delUser.action?userId="+userId;
			}
		}
		function addUser(){
			MyWindow.OpenCenterWindowScroll('toAddUser.action','detail',400,800);
		}
		
		function updateUser(userId){
			MyWindow.OpenCenterWindowScroll('toUpdateUser.action?userId='+userId,'detail',400,800);
		}
	</script>
</html>