<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>Port List</title>
<script type="text/javascript" src="<%=basePath%>scripts/ixia.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/newcalendar.js"></script>
</head>
<body topmargin="0" leftmargin="0" class="mobile">
<form method="post" name="boardform" id="boardform"><input
	type="hidden" name="currPage" value="${currPage }"></input>
	<input type="hidden" name="boardType" value="1"></input> <display:table
	name="boardList" class="mobile" id="row" 
	cellpadding="0" cellspacing="0" pagesize="50"
	requestURI="listIxias.action" sort="external">
	<display:caption>
		<thead>
			<tr class="pageHeader">
				<th style="width:32px;">No.</th>
				<th style="width:5%;"></th>
				<th style="width:200px;">Port Name</th>
				<th style="width:100px;">Owner</th>
				<th style="width:200px;" nowrap>Connected Board</th>
				<th style="width:150px;">State</th>
				<th style="width:100px;">Comments</th>
			</tr>
		</thead>
	</display:caption>
	<display:column title="Row Number" class="fixTitle1">
		<c:out value="${row_rowNum}"></c:out>
		<input name="boardList[${row_rowNum-1 }].boardId"
			value="${row.boardId }" type="hidden"></input>
	</display:column>
	<display:column>
		<c:if test="${row.userId ==0}">
			<input type="checkbox" name="multiBoard" value="${row.boardId }" />
		</c:if>
		<c:if test="${user.userId == row.userId}">
			<input type="checkbox" name="multiBoard" value="${row.boardId }"
				disabled="disabled" />
		</c:if>
		<c:if test="${user.userId != row.userId && row.userId!=0}">
			<input type="checkbox" name="multiBoard" value="${row.boardId }"
				disabled="disabled" />
		</c:if>
	</display:column>
	<display:column   class="cid_left">
				${row.boardName }
			</display:column>



	<display:column>
		<c:if test="${row.stateId == 1}">
			<select name="boardList[${row_rowNum-1 }].userId"
				id="users_${row_rowNum-1 }">
				<c:if test="${row.userId ==0}">
					<option value="0">none</option>
				</c:if>
				<s:iterator value="#userList" id="u" status="sx">
					<option value="${u.userId }">${u.userName}</option>
				</s:iterator>
			</select>
		</c:if>
		<c:if test="${row.stateId== 2}">
					${row.user.userName }
				</c:if>
	</display:column>
	
		<display:column>
		<c:if test="${row.stateId == 1}">
			<select name="boardList[${row_rowNum-1 }].siliconNum"
				id="users_${row_rowNum-1 }">
				<c:if test="${row.siliconNum ==0}">
					<option value="0">none</option>
				</c:if>
				<s:iterator value="#boardArrays" id="b" status="sx">
					<option value="${b.boardId}">${b.boardName}</option>
				</s:iterator>
			</select>
		</c:if>
		<c:if test="${row.stateId== 2}">
			<s:iterator value="#boardArrays" id="b" status="sx">
				<c:if test="${row.siliconNum == b.boardId}">${b.boardName}</c:if>

			</s:iterator>

		</c:if>
	</display:column>
	<c:if test="${row.stateId == 1}">
		<display:column style="background:green;"
			class="states_${row_rowNum-1}">
					${row.boardState.stateName }
				</display:column>
	</c:if>
	<c:if test="${row.stateId == 2}">
		<display:column style="background:yellow;"
			class="states_${row_rowNum-1}">
					${row.boardState.stateName }
				</display:column>
	</c:if>
	<display:column>
		<input size="100" name="boardList[${row_rowNum-1}].comments"
			value="${row.comments }" type="text"></input>
	</display:column>
	<display:setProperty name="basic.show.header" value="false" />
</display:table>
<div style="width: 100%; height: 25px;"><p:page	action="allocateIxiasList.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="target1" value="#target"/>	
				<p:param name="boardType" value="1"/>
			</p:page></div>
<div style="width: 100%; height: 25px; border: 1px gray dotted;">
&nbsp;<input type="button" class="btn" value="Book Ixia ports"
	onclick="applyIxias();"/></div>
</form>
</body>
<script type="text/javascript">
	function updateIxia(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateIxia.action?boardId=" + id,
				'detail', 600, 800);
	}
	function deleteIxia(boardId) {
		if (confirm('Confirm delete Ixia \'' + boardId + '\'')) {
			window.location.href = "delIxia.action?boardId=" + boardId;
		}

	}
</script>
</html>