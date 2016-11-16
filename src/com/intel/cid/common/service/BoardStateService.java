package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.dao.impl.BoardStateDaoImpl;

public class BoardStateService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BoardStateService.class);
	
	private BoardStateDaoImpl boardStateDaoImpl;

	public int addBoardState(final BoardState boardState) throws Exception 
	{	
		return boardStateDaoImpl.addBoardState(boardState);
	}
	
	public int queryBoardStateSize(BoardState boardState) throws Exception {
		return boardStateDaoImpl.queryBoardStateSize(boardState);
	}
	
	public int delBoardState(BoardState boardState) throws Exception {
       return boardStateDaoImpl.delBoardState(boardState);
	}
	
	public int delBoardStateById(int id) throws Exception {
	       return boardStateDaoImpl.delBoardStateById(id);
		}
	public List<BoardState> listAllBoardStates() throws Exception {
		
		return boardStateDaoImpl.queryAllBoardStates();
	}
	public BoardState queryBoardStateById(int id ) throws Exception
	{
		
	   return 	boardStateDaoImpl.queryBoardStateById(id);
		
	}
	
	public int updateBoardState(final BoardState boardState) throws Exception {
		
		return boardStateDaoImpl.updateBoardState(boardState);
	}
	public BoardStateDaoImpl getBoardStateDaoImpl() {
		return boardStateDaoImpl;
	}

	public void setBoardStateDaoImpl(BoardStateDaoImpl boardStateDaoImpl) {
		this.boardStateDaoImpl = boardStateDaoImpl;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
