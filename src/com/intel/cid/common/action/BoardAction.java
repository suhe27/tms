package com.intel.cid.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.Dispatcher;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class BoardAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BoardAction.class);

	private String boardId;

	private int stateId;

	private int teamId;

	private Board board;

	private List<Board> boardList;

	private String multiBoard;

	private String operation;

	private String startDate;

	private String endDate;

	private int itemSize;

	private int linkSize;

	private int currPage;
	
	private int boardType;

	private String target1;
	
	private String siliconNum;
	
	public String index() throws Exception {
		return SUCCESS;
	}

	public String formBoard() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<BoardState> boardStateList = boardStateService
				.listAllBoardStates();

		context.put("stateId", stateId);
		context.put("teamId", teamId);

		context.put("teamList", teamList);
		context.put("boardStateList", boardStateList);
		return SUCCESS;

	}

	
	public String  fuzzyQueryBoards() throws Exception{
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		int rowNumber = boardService.fuzzyQueryBoardSize(target1);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		boardList = boardService.fuzzyQueryBoard(pageBean,target1);
		List<Board> boardArrays = boardService.listAllBoardsByTeam(3);
		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		for (Board b : boardList) {

			User owner = userService.queryUserById(b.getUserId());
			BoardState state = boardStateService.queryBoardStateById(b
					.getStateId());
			b.setUser(owner);
			b.setBoardState(state);

		}
		context.put("pageBean", pageBean);
		context.put("target", target1);
		context.put("userList", userList);
		context.put("boardArrays", boardArrays);
		return SUCCESS;
	}
	
	
	public String listBoards() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("stateId", String.valueOf(stateId));
		filterMap.put("userId", String.valueOf(user.getUserId()));

		filterMap.put("teamId", String.valueOf(teamId));

		filterMap.put("boardType", String.valueOf(boardType));
		int rowNumber = boardService.queryBoardSizeByFilter(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		boardList = boardService.listBoardsByPage(pageBean, filterMap);
		List<Board> boardArrays = boardService.listAllBoardsByUser(user.getUserId());
		for (Board b : boardList) {

			User owner = userService.queryUserById(b.getUserId());
			BoardState state = boardStateService.queryBoardStateById(b
					.getStateId());
			b.setUser(owner);
			b.setBoardState(state);

		}
		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<BoardState> boardStateList = boardStateService
				.listAllBoardStates();

		context.put("boardArrays", boardArrays);
		context.put("stateId", stateId);
		context.put("teamId", teamId);
		context.put("pageBean", pageBean);
		context.put("teamList", teamList);
		context.put("boardStateList", boardStateList);
		return SUCCESS;
	}

	public String toAllocateBoards() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("stateId", String.valueOf(stateId));
		filterMap.put("userId", String.valueOf(user.getUserId()));
		filterMap.put("teamId", String.valueOf(teamId));
		filterMap.put("boardType", String.valueOf(boardType));
		int rowNumber = boardService.queryBoardSizeByFilter(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<Board> boardArrays = boardService.listAllBoardsByTeam(3);
		boardList = boardService.listBoardsByPage(pageBean, filterMap);
		for (Board b : boardList) {

			User owner = userService.queryUserById(b.getUserId());
			BoardState state = boardStateService.queryBoardStateById(b
					.getStateId());
			b.setUser(owner);
			b.setBoardState(state);

		}
		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<BoardState> boardStateList = boardStateService
				.listAllBoardStates();
		context.put("stateId", stateId);
		context.put("teamId", teamId);
		context.put("pageBean", pageBean);
		context.put("teamList", teamList);
		context.put("boardStateList", boardStateList);
		context.put("userList", userList);

		context.put("boardArrays", boardArrays);
		return SUCCESS;

	}

	public String allocateBoards() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (Utils.isNullORWhiteSpace(operation)
				|| !operation.equalsIgnoreCase("apply")) {
			context.put(Constant.ERRORMSG,
					"operation type error,should be apply!");
			return ERROR;
		}

		String[] boards = multiBoard.split(",");
		List<Board> allocateBoardList = new ArrayList<Board>();
		for (int i = 0; i < boards.length; i++) {

			for (int j = 0; j < boardList.size(); j++) {

				if (Integer.parseInt(boards[i].trim()) == boardList.get(j)
						.getBoardId()) {
					allocateBoardList.add(boardList.get(j));
				}
			}
		}

		// validate board(s) state.
		for (Board board : allocateBoardList) {
			Board newBoard = boardService.queryBoardById(board.getBoardId());
			if (newBoard.getStateId() != Constant.BOARD_STATUS_IDLE) {
				if(boardType ==1){
					context.put(Constant.ERRORMSG,
					"ixia port status error,port have been applied!");
				}
				else{
					context.put(Constant.ERRORMSG,
					"ixia status error,ixia have been applied!");
				}
				
				context.put("url", "");
				return ERROR;
			}
		}

		boardService.upateBatchAllocateBoards(allocateBoardList);
		// MailGenerator mailGenerator = new MailGenerator();
		// String[] ret = mailGenerator.getContentByBoardAllocate(this, user,
		// operation, boards);
		// MailHelper.sendHtmlMail(ret[0], ret[1], user.getEmail(), user
		// .getEmail());
		
		if(boardType ==1){
			context.put("url", "Successful!  you have applied " + "  "
					+ allocateBoardList.size() + " ixia ports!");
		}
		else{
			context.put("url", "Successful!  you have applied "  + "  "
					+ allocateBoardList.size() + " board(s)!");
		}
		
		return SUCCESS;

	}

	public String cancelBoards() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (Utils.isNullORWhiteSpace(operation)
				|| !operation.equalsIgnoreCase("release")) {
			context.put(Constant.ERRORMSG,
					"operation type error,should  be release!");
			return ERROR;
		}
		String[] boards = multiBoard.split(",");
		List<Board> cancelBoardList = new ArrayList<Board>();
		for (int i = 0; i < boards.length; i++) {

			for (int j = 0; j < boardList.size(); j++) {
				if (Integer.parseInt(boards[i].trim()) == boardList.get(j)
						.getBoardId()) {
					cancelBoardList.add(boardList.get(j));
				}
			}
		}
		// validate board(s) state.
		for (Board board : cancelBoardList) {
			Board newBoard = boardService.queryBoardById(board.getBoardId());
			if (newBoard.getStateId() != Constant.BOARD_STATUS_INUSE) {
				
				if(boardType ==1){
					context.put(Constant.ERRORMSG,
					"ixia prots status error,ixia prots  status must be in use!");
				}
				else{
					context.put(Constant.ERRORMSG,
					"ixia prots status error,board  status must be in use!");
				}
				
				return ERROR;
			}
		}
		boardService.upateBatchCancelBoards(cancelBoardList);
		// MailGenerator mailGenerator = new MailGenerator();
		// String[] ret = mailGenerator.getContentByBoardAllocate(this, user,
		// operation, boards);
		// MailHelper.sendHtmlMail(ret[0], ret[1], user.getEmail(), user
		// .getEmail());
		
		if(boardType ==1){
			context.put("url", "Successful!  you have realeased"  + "  "
					+ cancelBoardList.size() + " ixia ports!");
		}
		else{
			context.put("url", "Successful!  you have realeased "  + "  "
					+ cancelBoardList.size() + " baord(s)!");
		}
		
		return SUCCESS;

	}

	public String delBoard() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (!boardId.matches(Constant.POSITIVE_INTEGER_REGEX)) {
			
			if(boardType ==1){
				context.put(Constant.ERRORMSG, "invalide parameter!");
				context.put("url", "<br/><a href='fuzzyQueryBoards.action'>return</a>");
			}
			else{
				context.put(Constant.ERRORMSG, "invalide parameter!");
				context.put("url", "<br/><a href='listBoards.action'>return</a>");
			}
			
			return ERROR;
		}

		boardService.delBoardById(Integer.parseInt(boardId));

		return SUCCESS;
	}


	
	public String toUpdateBoard() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Team> teamList = teamService.listTeams();
		List<BoardState> boardStateList = boardStateService
				.listAllBoardStates();
		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		context.put("teamList", teamList);
		if (!boardId.matches(Constant.POSITIVE_INTEGER_REGEX)) {
			context.put(Constant.ERRORMSG, "invalide parameter!");
			//context.put("url", "<br/><a href='listBoards.action'>return</a>");
			return ERROR;
		}
		board = boardService.queryBoardById(Integer.parseInt(boardId));
		boardList = boardService.listAllBoardsByTeam(3);
		context.put("board", board);
		context.put("teamList", teamList);
		context.put("boardStateList", boardStateList);
		context.put("userList", userList);
//		Dispatcher.getInstance().getConfigurationManager().getConfiguration()
//		.getRuntimeConfiguration().getActionConfigs().
//		get("").get("indexUpdateBoard").getResults().get("success");
		return SUCCESS;

	}

	public String updateBoard() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if(boardType == 0)
		{
			if (Utils.isNullORWhiteSpace(startDate)
					&& Utils.isNullORWhiteSpace(endDate)) {
				board.setStateId(Constant.BOARD_STATUS_IDLE);
				board.setUserId(0);
				board.setComments("");
			}
			
			
		}
		if(boardType == 1){
			board.setSiliconNum(Integer.parseInt(siliconNum));
			
		}
		
		board.setStartDate(startDate);
		board.setEndDate(endDate);
		boardService.updateBoard(board);
		return SUCCESS;

	}

	public String toAddBoard() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		context.put("teamList", teamList);
		return SUCCESS;

	}

	public String addBoard() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		logger.info("addBoard menthod :" + board.logInfo());
		board.setStartDate(startDate);
		board.setEndDate(endDate);
		board.setStateId(Constant.BOARD_STATUS_IDLE);
		boardService.addBoard(board);

		return SUCCESS;

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public String getMultiBoard() {
		return multiBoard;
	}

	public void setMultiBoard(String multiBoard) {
		this.multiBoard = multiBoard;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	public int getLinkSize() {
		return linkSize;
	}

	public void setLinkSize(int linkSize) {
		this.linkSize = linkSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getTarget1() {
		return target1;
	}

	public void setTarget1(String target1) {
		this.target1 = target1;
	}

	public String getSiliconNum() {
		return siliconNum;
	}

	public void setSiliconNum(String siliconNum) {
		this.siliconNum = siliconNum;
	}

	

}
