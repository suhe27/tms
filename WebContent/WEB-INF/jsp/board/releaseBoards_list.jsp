<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Suite List</title>
		<script type="text/javascript" src="<%=basePath %>scripts/board.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/newcalendar.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form method="post" name="boardform" id="boardform">
		<input type="hidden" name="currPage" value="${currPage }"></input>
		
		<display:table name="boardList" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" pagesize="50" requestURI="releaseBoards_list.action" sort="external">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 32px;" >No.</th>
		  		<th style="width: 32px;" ></th>
				<th style="width: 200px;">Board Name</th>
				<th style="width: 100px;">IP Address</th>
				<th style="width: 100px;">Start Date</th>
				<th style="width: 100px;">End Date</th>
				<th style="width: 100px;">Owner</th>
				<th style="width: 100px;">State</th>
				<th style="width: 100px;">Comments</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
				<input name="boardList[${row_rowNum-1 }].boardId" value="${row.boardId }"
			type="hidden"></input>
			</display:column>
			<display:column>
			<c:choose>
			<c:when test="${user.level==9 || user.level ==6}">
			<input type="checkbox" name="multiBoard" value="${row.boardId }" />
			</c:when>	
			
			<c:when test="${row.userId ==0}">
					<input type="checkbox" name="multiBoard" value="${row.boardId }" />
				</c:when>
			<c:when test="${user.userId == row.userId}">
					<input type="checkbox" name="multiBoard" value="${row.boardId }" />
				</c:when>
				<c:otherwise>
				<c:if test="${user.userId != row.userId && row.userId!=0}">
					<input type="checkbox" name="multiBoard" value="${row.boardId }" disabled="disabled"/>
				</c:if>		
			
			</c:otherwise>
			</c:choose>
				
			</display:column>
			<display:column>
				${row.boardName }
			</display:column>
			<display:column  property="ipAddr" />
			<display:column>
				<c:if test="${row.stateId == 1}">
					<input name="boardList[${row_rowNum-1}].startDate" type="text" id="startdate_${row_rowNum-1 }" 
						onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);" size="20"
						maxlength="20" readonly="readonly"
						value="${row.startDate }" />
				</c:if>
				<c:if test="${row.stateId != 1}">
					${row.startDate}
				</c:if>
			</display:column>
			
			<display:column>
				<c:if test="${row.stateId == 1}">
					<input name="boardList[${row_rowNum-1}].endDate" type="text" id="endDate_${row_rowNum-1 }" 
						onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);autoCompleteTime('startdate_${row_rowNum-1 }');" size="20"
						maxlength="20" readonly="readonly"
						value="${row.endDate }" />
				</c:if>
				<c:if test="${row.stateId != 1}">
					${row.endDate}
				</c:if>
			</display:column>
			<display:column>
				<c:if test="${row.stateId == 1}">
					<select name="boardList[${row_rowNum-1 }].userId" id="users_${row_rowNum-1 }">
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
			<c:if test="${row.stateId == 1}">
				<display:column style="background:green;" class="states_${row_rowNum-1}">
					${row.boardState.stateName }
				</display:column>
			</c:if>	
			<c:if test="${row.stateId == 2}">
				<display:column style="background:yellow;" class="states_${row_rowNum-1}">
					${row.boardState.stateName }
				</display:column>
			</c:if>
			<display:column>
				<input size="50" name="boardList[${row_rowNum }].comments" value="${row.comments }"	type="text"></input>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:25px;">
			<p:page	action="releaseBoards_list.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="stateId" value="#stateId"/>
				<p:param name="teamId" value="#teamId"/>
			</p:page>
		</div>
		<div style="width:100%;height:25px;border:1px gray dotted;">
			&nbsp;<input type="button" class="btn" value="Release Boards" onclick="releaseBoards();"/>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	function updateBoard(id) {
		MyWindow.OpenCenterWindowScroll("toUpdateBoard.action?boardId=" + id,'detail',600,800);
	}
	function deleteBoard(boardId) {
		if(confirm('Confirm delete board \''+boardId+'\'')){
			window.location.href="delBoard.action?boardId="+boardId;
		}
	}
	</script>
</html>