package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.Board;

public interface BoardDao {
    
    
	List<Board> listAllBoards() throws Exception;

	Board queryBoardById(int id) throws Exception;

	int delBoard(Board board) throws Exception;

	int delBoardById(int id) throws Exception;

	int updateBoard(Board board) throws Exception;


	int addBoard(Board board) throws Exception;
	
	public List getExpireBoardList(final String currentTime);
	
	public int releaseExpireBoard(final String currentTime);
}
