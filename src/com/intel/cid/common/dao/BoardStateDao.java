package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.BoardState;

public interface BoardStateDao {

	
	
	BoardState queryBoardStateById(int id) throws Exception;

	int delBoardState(BoardState boardState) throws Exception;

	int delBoardStateById(int id) throws Exception;

	int updateBoardState(BoardState boardState) throws Exception;

	int addBoardState(BoardState boardState) throws Exception;

	public List<BoardState> queryAllBoardStates() throws Exception;
	
	
}
