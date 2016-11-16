package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.BoardDao;
import com.intel.cid.utils.Utils;

public class BoardDaoImpl implements BoardDao {

	private static Logger logger = Logger.getLogger(BoardDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addBoard(final Board board) throws Exception {
		String sql = "insert into board(userid,os,crbtype,ixano,powercycle,"
				+ "ipaddr,packageinfo,smartbitorstc,remotehost,ear,biosversion,silicontype,"
				+ "siliconnum,stateid,startdate,enddate,comments,location,teamid,boardname) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("addBoard sql:" + sql);

		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapBoardToPs(board, ps);
					}
				});
		logger.info("addBoard result:" + result);
		return result;
	}

	
	public int delBoard(Board board) throws Exception {

		return delBoardById(board.getBoardId());
	}

	public int delBoardById(int id) throws Exception {

		String sql = "delete from board where boardid='" + id + "'";

		logger.info("delete board sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delete board result:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Board> listAllBoards() throws Exception {

		String sql = " select board.*,user.* ,boardstate.* from board left join user on board.userid = user.userid  left join boardstate on board.stateid=boardstate.stateid";
		logger.info("sql:" + sql);
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();

				mapBoardFromRs(rs, board);
				if (board.getUserId() != 0) {
					User user = new User();
					UserDaoImpl.mapUserToRs(user, rs);
					board.setUser(user);
				} else {
					User user = new User();
					user.setUserName("");
					board.setUser(user);
				}

				if (board.getStateId() != 0) {
					BoardState boardState = new BoardState();
					BoardStateDaoImpl.mapBoardStateFromRs(rs, boardState);
					board.setBoardState(boardState);
				} else {
					BoardState boardState = new BoardState();
					boardState.setStateName("");
					board.setBoardState(boardState);
				}
				return board;
			}
		});
		logger.info("query all boards result:" + boardList.size());
		return boardList;
	}

	@SuppressWarnings("unchecked")
	public List<Board> listBoardsByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		String userId = filterMap.get("userId");
		String sql = "SELECT * from board WHERE EXISTS (SELECT * from user_team as map WHERE map.TEAMID = board.TEAMID  and map.USERID="+userId+")" ;
		sql  = filterBoardCondition(sql, filterMap);
		sql = sql + " order by board.stateid desc  limit " + itemFrom + "," + count;
		logger.info("sql:" + sql);
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();
				mapBoardFromRs(rs, board);
				return board;
			}
		});
		logger.info("query all boards result:" + boardList.size());
		return boardList;
	}

	@SuppressWarnings("unchecked")
	public List<Board> listAllBoardsByTeam(int teamId) throws Exception {

		String sql = " select board.*,user.* ,boardstate.* from board left join user on board.userid = user.userid  left join boardstate on board.stateid=boardstate.stateid"
				+ " where board.teamid=" + teamId +" and board.ipaddr is not null" ;
		logger.info("sql:" + sql);
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();

				mapBoardFromRs(rs, board);
				
				return board;
			}
		});
		logger.info("listAllBoardsByTeam result:" + boardList.size());
		return boardList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Board> listAllBoardsByUser(int userId) throws Exception {

		String sql = "select * from board WHERE EXISTS (SELECT * from user_team as map WHERE map.userid = "+userId +" and map.teamid = board.TEAMID) and board.IPADDR is not null ";
		logger.info("sql:" + sql);
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();

				mapBoardFromRs(rs, board);
				
				return board;
			}
		});
		logger.info("listAllBoardsByTeam result:" + boardList.size());
		return boardList;
	}

	public Board queryBoardById(int id) throws Exception {
		String sql = "select * from board where boardid ='" + id + "'";
		logger.info("query board sql:" + sql);
		final Board board = new Board();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapBoardFromRs(rs, board);

			}
		});

		logger.info("query board result:" + board.logInfo());
		return board;
	}

	
	public  int   fuzzyQueryBoardSize(String target)  throws Exception{
		target = target.replace("'", " ").replace("%", " ");
		String targets[] = target.split(" ");
		
		String obj = Utils.getStringFromArray(targets);
		targets = obj.split(",");
		String sql = "SELECT count(*) FROM " +
		"board b1  LEFT JOIN user u on b1.userid = u.userid " +
		" LEFT JOIN team t on b1.teamid = t.teamid" +
		" LEFT JOIN boardstate state on b1.stateid = state.stateid" +
		" LEFT JOIN board  b2 on b1.siliconnum = b2.boardid" +
		" WHERE b1.ipaddr is null %s ";

		
		if (targets.length==0) {
			sql = String .format(sql, "");
			
		}
			for(int i=0;i<targets.length;i++)
			{
				if (targets.length==1) {
					sql = String.format(sql, " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
					
				}else if(targets.length>1 ){
					if (i==0) {
						sql = sql.replace("%s", " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
						//sql = sql +  " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>";
						//sql = String.format(sql, " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
					}
					if (i== targets.length-1) {
						sql = sql.replace("<>", " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
						//sql = String.format(sql, " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
						break;
					}
					if (i>0 ) {
						sql = sql.replace("<>", " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
						//sql = String.format(sql, " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' %s");
					}
					
				}
			}
	
			 logger.info("sql:"+sql);
			return userJdbcTemplate.queryForInt(sql);
      
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Board> fuzzyQueryBoard( PageBean pageBean ,String target) throws Exception
	{
		logger.info("target:"+target);
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		target = target.replace("'", " ").replace("%", " ");
		String targets[] = target.split(" ");
		
		String obj = Utils.getStringFromArray(targets);
		targets = obj.split(",");
		String sql = "SELECT b1.*, u.* ,t.*, b2.*, state.* FROM " +
				"board b1  LEFT JOIN user u on b1.userid = u.userid " +
				" LEFT JOIN team t on b1.teamid = t.teamid" +
				" LEFT JOIN boardstate state on b1.stateid = state.stateid" +
				" LEFT JOIN board  b2 on b1.siliconnum = b2.boardid" +
				" WHERE b1.ipaddr is null %s";
		if (targets.length==0) {
			sql = String .format(sql, "");
			
		}
		for(int i=0;i<targets.length;i++)
		{
			
			if (targets.length==1) {
				
				sql = String.format(sql, " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
				
			}else if(targets.length>1 ){
				if (i==0) {
					sql = sql.replace("%s", " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
					//sql = sql +  " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>";
					//sql = String.format(sql, " AND ( CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
				}
				if (i== targets.length-1) {
					sql = sql.replace("<>", " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
					//sql = String.format(sql, " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' )");
					break;
				}
				if (i>0 ) {
					sql = sql.replace("<>", " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' <>");
					//sql = String.format(sql, " AND CONCAT_WS('',b1.boardname, b2.boardname,t.TEAMNAME,state.STATENAME ,u.username) LIKE '%"+targets[i]+"%' %s");
				}
				
				
			}
		}
			
		logger.info("sql:"+sql);
		sql = sql +"  order by b1.stateid desc,b1.boardname  limit " + itemFrom + "," + count;
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();
				mapBoardFromRs(rs, board);
				return board;
			}
		});
		logger.info(" result:" + boardList.size());
		return boardList;
		
		
	}
	
	public String filterBoardCondition(String sql, Map<String, String> filterMap) {

		sql = sql + " %s %s %s";

		String stateId = filterMap.get("stateId");
		String teamId = filterMap.get("teamId");
		String boardType = filterMap.get("boardType");
		boolean flag = true;// "where" keywords already added or not

		if (Utils.isNullORWhiteSpace(stateId) || stateId.trim().equals("-1")
				|| stateId.trim().equals("0")) {

			sql = String.format(sql, "", "%s","%s");

		} else {
			sql = String.format(sql,
					"  and  board.stateid ='" + stateId + "'", "%s" ,"%s");
			flag = true;

		}

		if (Utils.isNullORWhiteSpace(teamId) || teamId.trim().equals("-1")
				|| teamId.trim().equals("0")) {

			sql = String.format(sql, "","%s");

		} else {

			if (flag) {

				sql = String.format(sql, " and board.teamid=" + teamId,"%s");

			} else {
				sql = String.format(sql, "  where board.teamid=" + teamId,"%s");
				flag = true;
			}

		}

		
		if ( boardType.trim().equals("0")) {

			sql = String.format(sql, "and board.ipaddr is not null ");

		} else {
				sql = String.format(sql, " and board.ipaddr is  null" );
		}
		return sql;
	}

	public int updateBoard(final Board board) throws Exception {
		String sql = "update board set userid=?, os=?, crbtype=?, ixano=?, "
				+ "powercycle=?, ipaddr=?, packageinfo=?, smartbitorstc=?, remotehost=?,"
				+ " ear=?, biosversion=?, silicontype=?, siliconnum=?,stateid=?, startdate=?, enddate=?, comments=?, location=?,teamid=?,boardname=?"
				+ " where boardid='" + board.getBoardId() + "'";
		logger.info("update board sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						mapBoardToPs(board, ps);

					}
				});

		logger.info("update board result:" + result);

		return result;

	}

	public int allocateBatchBoards(final List<Board> boardList)
			throws Exception {

		String sql = "update board set userid =? , stateid =? ,startdate =?, enddate=? ,siliconnum=?,comments=? where  boardid=? ";
		logger.info("allocateBatchBoards method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Board board = boardList.get(i);
						ps.setInt(1, board.getUserId());
						ps.setInt(2, Constant.BOARD_STATUS_INUSE);

						if (Utils.isNullORWhiteSpace(board.getStartDate())) {
							ps.setString(3, null);

						} else {
							ps.setString(3, board.getStartDate());
						}
						if (Utils.isNullORWhiteSpace(board.getEndDate())) {
							ps.setString(4, null);

						} else {
							ps.setString(4, board.getEndDate());
						}

						ps.setInt(5, board.getSiliconNum());
						if (Utils.isNullORWhiteSpace(board.getComments())) {
							ps.setString(6, null);

						} else {
							ps.setString(6, board.getComments());
						}
						ps.setInt(7, board.getBoardId());
					}

					public int getBatchSize() {

						return boardList.size();
					}
				});
		logger.info("allocateBatchBoards method result:" + result.length);
		return result.length;
	}

	public int cancelBatchBoards(final List<Board> boardList) throws Exception {

		String sql = "update board set userid =? , stateid =? ,startdate =?, enddate=? ,comments=? where  boardid=? ";
		logger.info("cancelBatchBoards method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Board board = boardList.get(i);
						ps.setNull(1, Types.NULL);
						ps.setInt(2, Constant.BOARD_STATUS_IDLE);
						ps.setString(3, null);
						ps.setString(4, null);
						ps.setString(5, null);
						ps.setInt(6, board.getBoardId());
					}

					public int getBatchSize() {

						return boardList.size();
					}
				});
		logger.info("cancelBatchBoards method result:" + result.length);
		return result.length;
	}

	@SuppressWarnings("unchecked")
	public List<String> listDiffBoardCrbTypes() throws Exception {
		String sql = "select distinct(crbtype) from board";
		logger.info("listDiffBoardCrbTypes method sql :" + sql);
		List<String> crbTypes = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int i) throws SQLException {

				String crbType = rs.getString("crbtype");
				return crbType;
			}
		});
		logger.info("listDiffBoardCrbTypes method  result:" + crbTypes);
		return crbTypes;
	}

	public List<Board> listAllBoardsByState(int stateid) throws Exception {
		String sql = "select board.*,user.* ,boardstate.* from board left join user on board.userid = user.userid "
				+ " left join boardstate on board.stateid=boardstate.stateid where board.stateid='"
				+ stateid + "'";

		@SuppressWarnings("unchecked")
		List<Board> boardList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Board board = new Board();

				mapBoardFromRs(rs, board);
				if (board.getUserId() != 0) {
					User user = new User();
					UserDaoImpl.mapUserToRs(user, rs);
					board.setUser(user);
				} else {
					User user = new User();
					user.setUserName("");
					board.setUser(user);
				}

				if (board.getStateId() != 0) {
					BoardState boardState = new BoardState();
					BoardStateDaoImpl.mapBoardStateFromRs(rs, boardState);
					board.setBoardState(boardState);
				} else {
					BoardState boardState = new BoardState();
					boardState.setStateName("");
					board.setBoardState(boardState);
				}
				return board;
			}
		});

		return boardList;
	}

	public List<Board> listOperationBoards(final String[] boards)
			throws Exception {

		String sql = "select board.*,user.* ,boardstate.* from board left join user on board.userid = user.userid "
				+ " left join boardstate on board.stateid=boardstate.stateid where boardid in (%s)";

		StringBuffer paramBuffer = new StringBuffer();
		for (int i = 0; i < boards.length; i++) {
			paramBuffer.append("?");
			if (i != boards.length - 1) {
				paramBuffer.append(",");
			}

		}

		final String sql1 = String.format(sql, paramBuffer.toString());
		@SuppressWarnings("unchecked")
		List<Board> boardList = userJdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(
							Connection conn) throws SQLException {

						PreparedStatement ps = conn.prepareStatement(sql1);

						for (int i = 0; i < boards.length; i++) {

							ps
									.setInt(i + 1, Integer.parseInt(boards[i]
											.trim()));
						}
						return ps;
					}
				}, new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						Board board = new Board();

						mapBoardFromRs(rs, board);
						if (board.getUserId() != 0) {
							User user = new User();
							UserDaoImpl.mapUserToRs(user, rs);
							board.setUser(user);
						} else {
							User user = new User();
							user.setUserName("");
							board.setUser(user);
						}

						if (board.getStateId() != 0) {
							BoardState boardState = new BoardState();
							BoardStateDaoImpl.mapBoardStateFromRs(rs,
									boardState);
							board.setBoardState(boardState);
						} else {
							BoardState boardState = new BoardState();
							boardState.setStateName("");
							board.setBoardState(boardState);
						}
						return board;
					}
				});

		return boardList;

	}
	
	public int  queryBoardSizeByFilter(Map<String, String> filterMap)
	{
		String userId = filterMap.get("userId");
		String sql = "SELECT count(*) from board WHERE EXISTS (SELECT * from user_team as map WHERE map.TEAMID = board.TEAMID  and map.USERID="+userId+")" ;
		sql= filterBoardCondition(sql, filterMap);
		logger.info("queryBoardSizeByFilter sql:"+sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("queryBoardSizeByFilter result:"+result);
		return result;
		
	}
	

	
	public int delBatchBoards(final String[] boards) throws Exception {
		String sql = "delete from board where boardid =?";
		logger.info("delBatchBoards method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(boards[i].trim()));

					}

					public int getBatchSize() {

						return boards.length;
					}
				});
		logger.info("delBatchBoards method result:" + result.length);

		return result.length;
		
	}
	
	
	
	public static void mapBoardToPs(final Board board, PreparedStatement ps)
			throws SQLException {

		if (board.getUserId() == 0) {

			ps.setNull(1, Types.NULL);

		} else {
			ps.setInt(1, board.getUserId());
		}

		if (Utils.isNullORWhiteSpace(board.getOs())) {
			ps.setString(2, null);

		} else {
			ps.setString(2, board.getOs());
		}

		if (Utils.isNullORWhiteSpace(board.getCrbType())) {
			ps.setString(3, null);

		} else {
			ps.setString(3, board.getCrbType());
		}

		if (Utils.isNullORWhiteSpace(board.getIxaNo())) {
			ps.setString(4, null);

		} else {
			ps.setString(4, board.getIxaNo());
		}

		if (Utils.isNullORWhiteSpace(board.getPowerCycle())) {
			ps.setString(5, null);

		} else {
			ps.setString(5, board.getPowerCycle());
		}

		if (Utils.isNullORWhiteSpace(board.getIpAddr())) {
			ps.setString(6, null);

		} else {
			ps.setString(6, board.getIpAddr());
		}

		if (Utils.isNullORWhiteSpace(board.getPackageInfo())) {
			ps.setString(7, null);

		} else {
			ps.setString(7, board.getPackageInfo());
		}

		if (Utils.isNullORWhiteSpace(board.getSmartBitOrSTC())) {
			ps.setString(8, null);

		} else {
			ps.setString(8, board.getSmartBitOrSTC());
		}

		if (Utils.isNullORWhiteSpace(board.getRemoteHost())) {
			ps.setString(9, null);

		} else {
			ps.setString(9, board.getRemoteHost());
		}

		if (Utils.isNullORWhiteSpace(board.getEar())) {
			ps.setString(10, null);

		} else {
			ps.setString(10, board.getEar());
		}

		if (Utils.isNullORWhiteSpace(board.getBiosVersion())) {
			ps.setString(11, null);

		} else {
			ps.setString(11, board.getBiosVersion());
		}

		if (Utils.isNullORWhiteSpace(board.getSiliconType())) {
			ps.setString(12, null);

		} else {
			ps.setString(12, board.getSiliconType());
		}
		ps.setInt(13, board.getSiliconNum());

		if (board.getStateId() == 0) {

			ps.setInt(14, Types.NULL);

		} else {
			ps.setInt(14, board.getStateId());
		}
		if (Utils.isNullORWhiteSpace(board.getStartDate())) {

			ps.setString(15, null);

		} else {
			ps.setString(15, board.getStartDate());
		}
		if (Utils.isNullORWhiteSpace(board.getEndDate())) {

			ps.setString(16, null);

		} else {
			ps.setString(16, board.getEndDate());
		}

		if (Utils.isNullORWhiteSpace(board.getComments())) {
			ps.setString(17, null);

		} else {
			ps.setString(17, board.getComments());
		}
		if (Utils.isNullORWhiteSpace(board.getLocation())) {
			ps.setString(18, null);

		} else {
			ps.setString(18, board.getLocation());
		}

		if (board.getTeamId() == 0) {
			ps.setNull(19, Types.NULL);

		} else {
			ps.setInt(19, board.getTeamId());
		}

		if (Utils.isNullORWhiteSpace(board.getBoardName())) {
			ps.setString(20, null);

		} else {
			ps.setString(20, board.getBoardName());
		}

	}

	public static void mapBoardFromRs(ResultSet rs, Board board)
			throws SQLException {
		board.setBoardId(rs.getInt("boardid"));
		board.setUserId(rs.getInt("userid"));
		board.setOs(rs.getString("os"));
		board.setCrbType(rs.getString("crbtype"));
		board.setIxaNo(rs.getString("ixano"));
		board.setPowerCycle(rs.getString("powercycle"));
		board.setIpAddr(rs.getString("ipaddr"));
		board.setPackageInfo(rs.getString("packageinfo"));
		board.setSmartBitOrSTC(rs.getString("smartbitorstc"));
		board.setRemoteHost(rs.getString("remotehost"));
		board.setEar(rs.getString("ear"));
		board.setBiosVersion(rs.getString("biosversion"));
		board.setSiliconType(rs.getString("silicontype"));
		board.setSiliconNum(rs.getInt("siliconnum"));
		board.setStateId(rs.getInt("stateid"));
		board.setEndDate(rs.getString("enddate"));
		board.setStartDate(rs.getString("startdate"));
		board.setComments(rs.getString("comments"));
		board.setLocation(rs.getString("location"));
		board.setTeamId(rs.getInt("teamid"));
		board.setBoardName(rs.getString("boardname"));
	}
	
	@SuppressWarnings("unchecked")
	public List getExpireBoardList(final String currentTime){
		String sql = "select t.boardname,t.ipaddr,b.username,b.email,c.statename,t.startdate,t.enddate,t.comments from board t,user b,boardstate c  " +
				"where t.userid = b.userid and t.stateid = c.stateid and t.boardid in " +
				"(select a.boardid from board a where DATE_ADD(a.enddate,INTERVAL 0 SECOND) <= DATE_ADD('"+currentTime+"',INTERVAL 0 SECOND) and a.stateid = 2) " +
				"and t.stateid = 2";
		//logger.info(sql);
		return userJdbcTemplate.queryForList(sql);
	}
	
	public int releaseExpireBoard(final String currentTime){
		String sql = "update board t set t.userid = null , t.stateid = 1 ,t.startdate = null, t.enddate= null ,t.comments= null " +
				"where t.boardid in(select c.boardid from (select a.boardid from board as a where " +
				"DATE_ADD(a.enddate,INTERVAL 0 SECOND) <= DATE_ADD('"+currentTime+"',INTERVAL 0 SECOND) and a.stateid = 2) c) and t.stateid = 2";
		//logger.info(sql);
		return userJdbcTemplate.update(sql);
	}
	
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
