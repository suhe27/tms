package com.intel.cid.common.action;

import java.util.List;

import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class BoardStateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int stateId;

	private String stateName;

	private List<BoardState> boardStateList;

	public String listBoardStates() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		boardStateList = boardStateService.listAllBoardStates();
		return SUCCESS;

	}

	public String delBoardState() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		boardStateService.delBoardStateById(stateId);

		return SUCCESS;
	}

	public String toUpdateBoardState() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		BoardState boardState = boardStateService.queryBoardStateById(stateId);
		context.put("boardState", boardState);
		return SUCCESS;

	}

	public String updateBoardState() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		BoardState boardState = new BoardState();
		boardState.setStateId(stateId);
		boardState.setStateName(stateName);
		
		int result = boardStateService.queryBoardStateSize(boardState);
		if(result==1){
			context.put(Constant.ERRORMSG, "Boardstate name '" + boardState.getStateName() + "' already exist!");
			return ERROR;
		}
		
		boardStateService.updateBoardState(boardState);

		return SUCCESS;

	}

	public String toAddBoardState() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		return SUCCESS;

	}

	public String addBoardState() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		BoardState boardState = new BoardState();
		boardState.setStateId(stateId);
		boardState.setStateName(stateName);
		
		int result = boardStateService.queryBoardStateSize(boardState);
		if(result==1){
			context.put(Constant.ERRORMSG, "Boardstate name '" + boardState.getStateName() + "' already exist!");
			return ERROR;
		}
		
		boardStateService.addBoardState(boardState);

		return SUCCESS;

	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<BoardState> getBoardStateList() {
		return boardStateList;
	}

	public void setBoardStateList(List<BoardState> boardStateList) {
		this.boardStateList = boardStateList;
	}

}
