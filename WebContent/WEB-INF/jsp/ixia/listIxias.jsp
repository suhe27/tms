<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Port List</title>
		<script type="text/javascript" src="<%=basePath %>scripts/ixia.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="boardform" id="boardform">
		<input type="hidden" name="currPage" value="${currPage }"></input>
		<display:table name="boardList" class="mobile" id="row"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="listIxias.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
				<th style="width: 200px;">Port Name</th>
				<th style="width: 100px;">State</th>
				<th style="width: 150px;"nowrap>Connected Board</th>
				<th style="width: 100px;">Owner</th>
				<s:if test="#session.user.level== 9 || #session.user.level>= 2"><th  width="100">Operation</th></s:if>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column class="cid_left">
			<a href="javascript:showIxiaClick(${row.boardId });" style="color:#0059fd;" >${row.boardName }</a>	
			</display:column>
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

			<s:iterator value="#boardArrays" id="b" status="sx">
					<c:if test="${row.siliconNum == b.boardId}">${b.boardName}</c:if>
			</s:iterator>

	</display:column>
			<display:column>
				${row.user.userName }
			</display:column>
			
			
			<display:column>
			<s:if test="#session.user.level== 9 ||(#session.user.level>= 2 ) ">
				<a href="javascript:updateIxia('${row.boardId }');" style="color:#0059fd;">Update</a>&nbsp;&nbsp;&nbsp;	
			</s:if>
			<s:if test="#session.user.level== 9 ">
				<a href="javascript:deleteIxia('${row.boardId }');" style="color:#0059fd;">Delete</a>
			</s:if>
			</display:column>
		
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;">
			<p:page	action="fuzzyQueryBoards.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="target1" value="#target"/>	
				<p:param name="boardType" value="1"/>
			</p:page>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function updateIxia(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateIxia.action?boardId=" + id,'detail',600,800);
	}
	function deleteIxia(boardId) {
		if(confirm('Confirm delete Ixia \''+boardId+'\'')){
			window.location.href="delIxia.action?boardId="+boardId+"&boardType=1"+"&target1=";
		}
		
	}
	function showIxiaClick(id) {
		MyWindow.winOpenFullScreen("showIxia.action?boardId=" + id,'boardDetail',600,800);
	}
	</script>
</html>