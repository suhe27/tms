<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Boards</title>
<script type="text/javascript">
	function selectBoardClick(id) {
		window.location.href = "toUpdateBoard.action?boardId=" + id;
	}
</script>
</head>
<body onload="timerRefresh(30);">
<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">Board <img src="images/board.png"></img>List</label>
<hr />
<form method="post" name="boardform">
<input type="hidden" name="currPage" value="${currPage }"></input>
<div>
<table id="filter_table">
	<tr>
	
	<td><span style="float: left; font-size: 15px; margin-right: 50px;">Filters:</span></td>
	<s:if test="#session.user == null || #session.user.level ==9 || #session.user.level ==1">
	<td width="180px">Team:<select onchange="filterBoard();"
			id="teamId" name="teamId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#teamList" id="team">
				<s:if test="#team.teamId== #teamId">
					<option value="${team.teamId }" selected="selected">${team.teamName}</option>
				</s:if>
				<s:else>
					<option value="${team.teamId }">${team.teamName}</option>
				</s:else>
			</s:iterator>
		</select></td>
	</s:if>
		<td width="180px">State:<select onchange="filterBoard();"
			id="stateId" name="stateId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>	
			<s:iterator value="#boardStateList" id="boardstate">	
			<s:if test="#boardstate.stateId == #stateId">
			<option value="${boardstate.stateId}" selected="selected">${boardstate.stateName }</option>
			</s:if>
			<s:else>
			<option value="${boardstate.stateId}">${boardstate.stateName }</option>
			</s:else>
			</s:iterator>
		</select></td>
         
</table>
</div>
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th width="20%">BOARDNAME</th>
		<th  width="20%">IPADDR</th>
		<th width="10%">STATE</th>
		<th width="10%">OWNER</th>
		<th width="15%">STARTDATE</th>
		<th width="15%">ENDDATE</th>
		<s:if test="#session.user.level== 9 || #session.user.level== 3"><th  width="10%">OPERATION</th></s:if>
	</tr>
	<s:iterator value="boardList" id="board" status="st">
		<tr onmouseover="selectLine(true, '${board.boardId }', this)"
			onmouseout="selectLine(false, '${board.boardId }', this)">
			<td onclick="selectBoardClick('${board.boardId }')">${board.boardName
			}</td>
			<td onclick="selectBoardClick('${board.boardId }')">${board.ipAddr
			}</td>
			
			<s:if test="#board.stateId == 1">		
				<td onclick="selectBoardClick('${board.boardId }')" style="background:green">${board.boardState.stateName
			}</td>	
			</s:if>
			<s:if test="#board.stateId == 2">
			
			<td onclick="selectBoardClick('${board.boardId }')" style="background:yellow">${board.boardState.stateName
			}</td>		
			</s:if>
			<td onclick="selectBoardClick('${board.boardId }')">${board.user.userName
			}</td>
			<td onclick="selectBoardClick('${board.boardId }')">${board.startDate
			}</td>
			<td onclick="selectBoardClick('${board.boardId }')">${board.endDate
			}</td>	
			<s:if test="#session.user.level== 9 ||(#session.user.level>= 3 ) ">
			<td style="text-align: center;"><a
				href="delBoard.action?boardId=${board.boardId }"
				onclick="return confirm('Confirm delete board \'${board.boardName}\'');">Delete</a></td>
			
			</s:if>
			
		</tr>
	</s:iterator>
	<tr>

	<td colspan="7">
		<s:if test="#session.user.level== 9 || #session.user.level== 3 ">
		<span style="float: left;">
		<input type="button" value="add" onclick="location= 'toAddBoard.action'"></input></span>
		</s:if>
		<span
			style="float: right; display: inline"><p:page
			action="listBoards.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
			<p:param name="stateId" value="#stateId"/>
			<p:param name="teamId" value="#teamId"/>
			</p:page></span>
		</td>
	
	</tr>
</table>
</form>
</div>
</body>
</html>