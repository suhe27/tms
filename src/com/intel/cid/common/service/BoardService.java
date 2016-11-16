package com.intel.cid.common.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.BoardDaoImpl;
import com.intel.cid.utils.MailGenerator;
import com.intel.cid.utils.MailHelper;
import com.intel.cid.utils.Utils;

public class BoardService {

	private static Logger logger = Logger.getLogger(BoardService.class);

	private BoardDaoImpl boardDaoImpl;

	public List<Board> listAllBoards() throws Exception {

		List<Board> boardList = boardDaoImpl.listAllBoards();

		return boardList;

	}

	 public List<Board> listAllBoardsByTeam(int teamId) throws Exception {
	
	 List<Board> boardList = boardDaoImpl.listAllBoardsByTeam(teamId);
	
	 return boardList;
	
	 }
	 public List<Board> listAllBoardsByUser(int userId) throws Exception {
		 return boardDaoImpl.listAllBoardsByUser(userId);
		 
	 }
	public Board queryBoardById(int id) throws Exception {

		return boardDaoImpl.queryBoardById(id);

	}

	public void updateBoard(Board board) throws Exception {

		boardDaoImpl.updateBoard(board);

	}

	public int delBatchBoards(final String[] boards) throws Exception {

		return boardDaoImpl.delBatchBoards(boards);
	}

	public void delBoardById(int id) throws Exception {

		boardDaoImpl.delBoardById(id);
	}

	public void addBoard(Board board) throws Exception {
		boardDaoImpl.addBoard(board);

	}

	public int upateBatchAllocateBoards(final List<Board> boardList)
			throws Exception {

		return boardDaoImpl.allocateBatchBoards(boardList);
	}

	public int upateBatchCancelBoards(final List<Board> boardList)
			throws Exception {

		return boardDaoImpl.cancelBatchBoards(boardList);
	}

	public List<Board> listOperationBoards(final String[] boards)
			throws Exception {

		return boardDaoImpl.listOperationBoards(boards);
	}

	public List<Board> listBoardsByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		return boardDaoImpl.listBoardsByPage(pageBean, filterMap);
	}

	public int queryBoardSizeByFilter(Map<String, String> filterMap) {
		return boardDaoImpl.queryBoardSizeByFilter(filterMap);
	}

	public void TimerReleaseExpireBoards() throws Exception {
		Runtime runTime = Runtime.getRuntime();
		long freememory = runTime.freeMemory();
		
		logger.info("start to scan board table....");
		List<Board> busyBoardList = boardDaoImpl
				.listAllBoardsByState(Constant.BOARD_STATUS_INUSE);
		List<Board> expireBoardList = new ArrayList<Board>();
		Map<String, List<Board>> boardMap = new HashMap<String, List<Board>>();
		long systemTime = System.currentTimeMillis();
		for (Board board : busyBoardList) {

			String endDate = board.getEndDate();
			if (!Utils.isNullORWhiteSpace(endDate)) {

				if (new SimpleDateFormat(Constant.DATE_FORMAT_DEFAULT).parse(
						endDate).getTime() <= systemTime) {
					expireBoardList.add(board);
				}
			}
		}

		if (expireBoardList.size() > 0) {

			int result = boardDaoImpl.cancelBatchBoards(expireBoardList);
			for (Board board : expireBoardList) {

				List<Board> ownBoardList = boardMap.get(board.getUser()
						.getEmail());

				if (ownBoardList == null) {

					List<Board> tempBoardList = new ArrayList<Board>();
					tempBoardList.add(board);
					boardMap.put(board.getUser().getEmail(), tempBoardList);
					tempBoardList =null;
					
				} else {
					ownBoardList.add(board);
				}

			}

			for (Entry<String, List<Board>> entry : boardMap.entrySet()) {

				MailGenerator mailGenerator = new MailGenerator();
				String[] ret = mailGenerator.getContentByBoardRelease(entry
						.getValue());
				MailHelper.sendHtmlMail(ret[0], ret[1], entry.getKey(), entry
						.getKey(),null);

			}

			logger.info("release boards:" + result);
			
		} else {
			logger.info("there is no board to release!");
			
		}
		long maxmemory = runTime.maxMemory();
		long totalmemory = runTime.totalMemory();
		logger.info("memorary info:Free Memory : "+freememory/1024/1024+"    Max Memory : "+maxmemory/1024/1024+"   Total Memory : "+totalmemory/1024/1024);
	}
	
	public  int   fuzzyQueryBoardSize(String target)  throws Exception{
		
		return boardDaoImpl.fuzzyQueryBoardSize(target);
	}
	public List<Board> fuzzyQueryBoard(PageBean pageBean ,String target) throws Exception
	{
		
		return boardDaoImpl.fuzzyQueryBoard(pageBean,target);
	}
	
	public void releaseBoard(){
		String currentTime = Utils.dateFormat(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
		Runtime runTime = Runtime.getRuntime();
		long freememory = runTime.freeMemory();
		long maxmemory = runTime.maxMemory();
		long totalmemory = runTime.totalMemory();
		//logger.info("memorary info:Free Memory : "+freememory/1024/1024+"    Max Memory : "+maxmemory/1024/1024+"   Total Memory : "+totalmemory/1024/1024);
		
		List expireBoardList = boardDaoImpl.getExpireBoardList(currentTime);
		Map<String, List<Board>> boardMap = new HashMap<String, List<Board>>();
		if(null != expireBoardList && expireBoardList.size() > 0){
			// t.boardname,t.ipaddr,b.username,c.statename,t.startdate,t.enddate,t.comments
			boardDaoImpl.releaseExpireBoard(currentTime);
			List<Board> list = new ArrayList<Board>();
			for(int i=0;i<expireBoardList.size();i++){
				LinkedHashMap<?, ?> lh = (LinkedHashMap)expireBoardList.get(i);
				Board board = new Board();
				board.setBoardName(lh.get("boardname")==null ? "" : lh.get("boardname").toString());
				board.setIpAddr(lh.get("ipaddr")==null ? "" : lh.get("ipaddr").toString());
				User user = new User();
				user.setUserName(lh.get("username")==null ? "" : lh.get("username").toString());
				board.setUser(user);
				BoardState bs = new BoardState();
				bs.setStateName(lh.get("statename")==null ? "" : lh.get("statename").toString());
				board.setBoardState(bs);
				board.setStartDate(lh.get("statename")==null ? "" : lh.get("statename").toString());
				board.setEndDate(lh.get("enddate")==null ? "" : lh.get("enddate").toString());
				board.setComments(lh.get("comments")==null ? "" : lh.get("comments").toString());
				String email = lh.get("email")==null ? "" : lh.get("email").toString();
				if(boardMap.get(email) == null){
					list.add(board);
					boardMap.put(email, list);
				}else{
					boardMap.get(email).add(board);
				}
			}
			for (Entry<String, List<Board>> entry : boardMap.entrySet()){
				try {
					MailGenerator mailGenerator = new MailGenerator();
					String[] ret = mailGenerator.getContentByBoardRelease(entry.getValue());
					MailHelper.sendHtmlMail(ret[0], ret[1], entry.getKey(), entry.getKey(),null);
				} catch (MessagingException e) {
					logger.info(e.getLocalizedMessage());
				} catch (Exception e) {
					logger.info(e.getLocalizedMessage());
				}
			}
		}
		// memorary info:Free Memory : 9    Max Memory : 63   Total Memory : 27
		// memorary info:Free Memory : 3    Max Memory : 63   Total Memory : 17
	}

	public BoardDaoImpl getBoardDaoImpl() {
		return boardDaoImpl;
	}

	public void setBoardDaoImpl(BoardDaoImpl boardDaoImpl) {
		this.boardDaoImpl = boardDaoImpl;
	}
}
