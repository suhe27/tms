<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Board List</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="boardform" id="boardform">
		<input type="hidden" name="currPage" value="${currPage }"></input>
		<display:table name="boardList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="listBoards.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
				<th style="width: 200px;">Board Name</th>
				<th style="width: 100px;">IP Address</th>
				<th style="width: 100px;">State</th>
				<th style="width: 100px;">Owner</th>
				<th style="width: 100px;">Start Date</th>
				<th style="width: 100px;">End Date</th>
				<s:if test="#session.user.level== 9 || #session.user.level>= 2"><th  width="100">Operation</th></s:if>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
			<a href="javascript:showBoardClick(${row.boardId });" style="color:#0059fd;" >${row.boardName }</a>	
			</display:column>
			<display:column  property="ipAddr" />
			
			<c:if test="${row.stateId == 1}">
				<display:column style="background:#50b85f;">
					${row.boardState.stateName }
				</display:column>
			</c:if>
			<c:if test="${row.stateId == 2}">
				<display:column style="background:yellow">
					${row.boardState.stateName }
				</display:column>
			</c:if>
			<display:column>
				${row.user.userName }
			</display:column>
			<display:column  property="startDate" />
			<display:column  property="endDate" />
			
			<display:column>
			<s:if test="#session.user.level== 9 ||(#session.user.level>= 2 ) ">
				<a href="javascript:modifyBoard('${row.boardId }');" style="color:#0059fd;">Update</a>&nbsp;&nbsp;&nbsp;	
			</s:if>
			<s:if test="#session.user.level== 9 ">
				<a href="javascript:deleteBoard('${row.boardId }');" style="color:#0059fd;">Delete</a>
			</s:if>
			</display:column>
		
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;">
			<p:page	action="listBoards.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="stateId" value="#stateId"/>
				<p:param name="teamId" value="#teamId"/>
			</p:page>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function modifyBoard(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateBoard.action?boardId=" + id,'detail',600,800);
	}
	function deleteBoard(boardId) {
		if(confirm('Confirm delete board \''+boardId+'\'')){
			window.location.href="delBoard.action?boardId="+boardId;
		}
		
	}
	function showBoardClick(id) {
		MyWindow.winOpenFullScreen("showBoard.action?boardId=" + id,'boardDetail',600,800);
	}
	</script>
</html>