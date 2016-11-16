package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.BoardState;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.dao.BoardStateDao;
import com.intel.cid.utils.Utils;

public class BoardStateDaoImpl implements BoardStateDao {

	private static Logger logger = Logger.getLogger(BoardStateDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addBoardState(final BoardState boardState) throws Exception {
		  
		 String sql = "insert into boardstate(statename) values(?)";
	        logger.info("sql:" + sql);
	        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

	            public void setValues(PreparedStatement ps) throws SQLException {

	                mapBoardStateToPs(boardState, ps);

	            }
	        });
	        logger.info("result:" + result);

	        return result;
	}

	public int queryBoardStateSize(BoardState boardState) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != boardState.getStateId()) {
			sql = "select count(*) from boardstate where statename = '"+ boardState.getStateName() + "' and stateid ='"+ boardState.getStateId() + "'  ";
			logger.info("queryBoardStateSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from boardstate where statename = '"+ boardState.getStateName() + "'";
			logger.info("queryBoardStateSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryBoardStateSize method result:" + result);
		return size;
	}

	
	public int delBoardState(BoardState boardState) throws Exception {
		  
		return delBoardStateById(boardState.getStateId());
	}

	@Override
	public int delBoardStateById(int id) throws Exception {
		 String sql = "delete from boardstate where stateid ='" + id + "'";
	        logger.info("sql:" + sql);
	        int result = userJdbcTemplate.update(sql);
	        logger.info("result:" + result);
	        return result;
	}

	   
	public List<BoardState> queryAllBoardStates() throws Exception {
		  
		  String sql = " select * from boardstate";
	        logger.info("sql:" + sql);
	        @SuppressWarnings("unchecked")
	        List<BoardState> boardStateList = userJdbcTemplate.query(sql, new RowMapper() {

	            public Object mapRow(ResultSet rs, int count) throws SQLException {

	                BoardState boardState = new BoardState();
	                mapBoardStateFromRs(rs, boardState);

	                return boardState;
	            }
	        });

	        logger.info("result:" + boardStateList);
	        return boardStateList;
	}

	   
	

	   
	public int updateBoardState(final BoardState boardState) throws Exception {
		
		 String sql = "update boardstate set statename=? where stateid='" + boardState.getStateId() + "'";
	        logger.info("sql:" + sql);
	        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
	            public void setValues(PreparedStatement ps) throws SQLException {

	                mapBoardStateToPs(boardState, ps);
	            }
	        });

	        logger.info("result:" + result);
	        return result;
		
		
	}

	public static void mapBoardStateFromRs(ResultSet rs,BoardState boardState) throws SQLException {
		boardState.setStateId(rs.getInt("stateid"));
		boardState.setStateName(rs.getString("statename"));
    }

    public static void mapBoardStateToPs(final BoardState boardState, PreparedStatement ps) throws SQLException {
        if (Utils.isNullORWhiteSpace(boardState.getStateName())) {
            ps.setNull(1, Types.NULL);
        } else {
            ps.setString(1, boardState.getStateName());
        }

    }
	
    
	public BoardState queryBoardStateById(int id) throws Exception {

        String sql = "select * from boardstate where stateid='" + id + "'";
        logger.info("sql:" + sql);
        final BoardState boardState = new BoardState();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
                mapBoardStateFromRs(rs, boardState);
            }
        });

        logger.info("result:" + boardState);
        return boardState;
	}

	
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}


	
	
}
