<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Booking Boards</title>
<script type="text/javascript">
	function selectBoardClick(id) {
		window.location.href = "toUpdateBoard.action?boardId=" + id;
	}		
</script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="div_main"><label class="statictittle">Board <img src="images/board.png"></img>Booking</label>
<hr />
<form method="post" name="boardform">
<div>
<table id="filter_table">
	<tr>
	<td><span style="float: left; font-size: 15px; margin-right: 50px;">Filters:</span></td>
	<s:if test="#session.user == null || #session.user.level ==9 || #session.user.level ==1">
	<td width="180px">Team:<select onchange="filterAllocateBoard();"
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
		<td width="180px">State:<select onchange="filterAllocateBoard();"
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
		<th></th>
		<th>BOARDNAME</th>
		<th>IPADDR</th>
		<th>STARTDATE</th>
		<th>ENDDATE</th>
		<th>OWNER</th>
		<th>STATE</th>
		<th>COMMENTS</th>
	</tr>
	<s:iterator value="boardList" id="board" status="st">
		<input name="boardList[<s:property value='#st.index'/>].boardId"
			value="<s:property value='boardList[#st.index].boardId'/>"
			type="hidden"></input>
		<tr onmouseover="selectLine(true, '${board.boardId }', this)"
			onmouseout="selectLine(false, '${board.boardId }', this)">
			<td>
			<s:if test="#session.user.level ==9 || #session.user.level ==6"><input type="checkbox" name="multiBoard"
				value="<s:property value='boardList[#st.index].boardId'/>" /></s:if>	
			<s:else>
			<s:if test="#board.userId == 0 ">
			<input type="checkbox" name="multiBoard"
				value="<s:property value='boardList[#st.index].boardId'/>" />
			</s:if>
			<s:if test="#session.user.userId == #board.userId">
			<input type="checkbox" name="multiBoard"
				value="<s:property value='boardList[#st.index].boardId'/>" />
			</s:if>
			<s:if test="#session.user.userId != #board.userId && #board.userId!=0 ">
			<input type="checkbox" name="multiBoard"
				value="<s:property value='boardList[#st.index].boardId'/>" disabled="disabled"/>
			</s:if>			
			</s:else>
			</td>
			<td>${board.boardName}</td>
			<td>${board.ipAddr }</td>
			<td >
			<s:if test="#board.stateId == 1">
			
			<input
				name="boardList[<s:property value='#st.index'/>].startDate"
				type="text" id="startdate_<s:property value='boardList[#st.index].boardId'/>" 
				onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);" size="20"
				maxlength="20" readonly="readonly"
				value="<s:property value='boardList[#st.index].startDate'/>">
			</s:if>
			<s:if test="#board.stateId == 2" >
				${board.startDate}
			</s:if>
			
			</td>
			<td>
			
			<s:if test="#board.stateId == 1">
			
			<input
				name="boardList[<s:property value='#st.index'/>].endDate"
				type="text" id="enddate_<s:property value='boardList[#st.index].boardId'/>"
				onfocus="SelectDate(this,'yyyy-MM-dd hh:mm:ss',0,0);autoCompleteTime('startdate_<s:property value='boardList[#st.index].boardId'/>');" size="20"
				maxlength="20" readonly="readonly"
				value="<s:property value='boardList[#st.index].endDate'/>">
			</s:if>
			<s:if test="#board.stateId == 2" >
				${board.endDate}
			</s:if>
			</td>
			<td>
			
			<s:if test="#board.stateId == 1">
			<select name="boardList[<s:property value='#st.index'/>].userId" id="users_<s:property value='boardList[#st.index].boardId'/>">
			<s:if test="#board.userId == 0">
			<option value="0">none</option>
			</s:if>
			<s:iterator value="#userList" id="u" status="sx">
					<option value="${u.userId }">${u.userName}</option>
			</s:iterator>	
			</select>
		
			</s:if>
			<s:if test="#board.stateId == 2" >
			${board.user.userName }
			</s:if>
			
			</td>
			
			<s:if test="#board.stateId == 1">
			<td  style="background:green;width:100px;" id="states_<s:property value='boardList[#st.index].boardId'/>">
			${board.boardState.stateName }
			</td>			
			</s:if>
			<s:if test="#board.stateId == 2" >
				<td style="background:yellow;width:100px;" id="states_<s:property value='boardList[#st.index].boardId'/>">
				${board.boardState.stateName }
				</td>
			</s:if>
		
			<td><input size="50"
				name="boardList[<s:property value='#st.index'/>].comments"
				value="<s:property value='boardList[#st.index].comments'/>"
				type="text"></input></td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="8"><s:if test="#session.user.level >= 2">
		<span style="float:left;"><input type="button" value="Release Boards" onclick="releaseBoards();"></input></span>
		<span style="float:left;"><input type="button" value="back" onclick="location='listBoards.action'"></input></span>
		</s:if>	
		<span
			style="float: right; display: inline"><p:page
			action="toAllocateBoards.action" beanName="#pageBean"
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