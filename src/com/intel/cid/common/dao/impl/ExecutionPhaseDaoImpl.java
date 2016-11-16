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

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.PhaseDao;
import com.intel.cid.utils.Utils;

public class ExecutionPhaseDaoImpl implements PhaseDao {

    private static Logger logger = Logger.getLogger(ExecutionPhaseDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addPhase(final Phase phase) throws Exception {
    							  
        String sql = "insert into executionphase(phasename,projectid) values(?,?)";

        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, phase.getPhaseName());
                ps.setInt(2, phase.getProjectId());

            }
        });
        logger.info("result:" + result);

        return result;
    }

    public int delPhase(Phase phase) throws Exception {

        return delPhaseById(phase.getPhaseId());
    }

    public int delPhaseById(int id) throws Exception {
        //String sql = "delete from executionphase where phaseId ='" + id + "'";
        String sql = "UPDATE `executionphase` SET `STATUS`='1' WHERE phaseId='" + id + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }

    /**
     * Check duplicate under same project
     * @param platform
     * @return
     * @throws Exception
     */ 
        
    	public int queryEphaseSize(Phase phase) throws Exception {
    		String sql = "";
    		int result = 0;
    		int size = 0;
    		if (0 != phase.getPhaseId()) {
    			sql = "select count(*) from executionphase where (status <> 1 or status is null) and projectId = '"+ phase.getProjectId() +"' "
    					+ "and phaseName = '"+ phase.getPhaseName() + "'  and phaseId !='"
    			+ phase.getPhaseId() + "'";
    			logger.info("queryByPhaseNameSize method result:" + sql);
    			result = userJdbcTemplate.queryForInt(sql);
    			if(result!=0){
    				size = 1;
    			}
    		} else {
    			sql = "select count(*) from executionphase where (status <> 1 or status is null) and projectId = '"+ phase.getProjectId() +"'  "
    					+ "and phaseName ='"+ phase.getPhaseName() + "'  ";
    			logger.info("queryByPhaseNameSize method result:" + sql);
    			result = userJdbcTemplate.queryForInt(sql);
    			if(result!=0){
    				size = 1;
    			}
    		}

    		logger.info("queryByPhaseNameSize method result:" + result);
    		return size;
    	}
    
    public List<Phase> queryAllPhases() throws Exception {

        String sql = "select a.* , b.PROJECTNAME from executionphase a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null)";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Phase> phaseList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Phase phase = new Phase();
                mapPhaseFromRs(rs, phase);

                return phase;
            }
        });

        logger.info("result:" + phaseList);
        return phaseList;
    }
	
    public List<Phase> queryPhaseByProjectId(int id) throws Exception {

        String sql = " select * from executionphase where (status <> 1 or status is null) and projectid ='" + id + "'";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Phase> phaseList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Phase phase = new Phase();
            	phase.setPhaseId(rs.getInt("phaseid"));
            	phase.setPhaseName(rs.getString("phasename"));
            	phase.setProjectId(rs.getInt("projectId"));

                return phase;
            }
        });

        logger.info("result:" + phaseList);
        return phaseList;
    }
    
    public Phase queryPhaseById(int id) throws Exception {

        String sql = "select * from executionphase where phaseId='" + id + "'";
        logger.info("sql:" + sql);
        final Phase phase = new Phase();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
            	phase.setPhaseId(rs.getInt("phaseid"));
            	phase.setPhaseName(rs.getString("phasename"));
            	phase.setProjectId(rs.getInt("projectId"));

            }
        });

        logger.info("result:" + phase);
        return phase;
    }

    public int updatePhase(final Phase phase) throws Exception {
        String sql = "update executionphase set phasename=?,projectid =? where phaseId='" + phase.getPhaseId() + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, phase.getPhaseName());
                ps.setInt(2, phase.getProjectId());

            }
        });

        logger.info("result:" + result);
        return result;
    }

    public static void mapPhaseToPs(final Phase phase, PreparedStatement ps) throws SQLException {

        if (Utils.isNullORWhiteSpace(phase.getPhaseName())) {

            ps.setNull(1, Types.NULL);

        } else {
            ps.setString(1, phase.getPhaseName());
        }

    }

    public static void mapPhaseFromRs(ResultSet rs, Phase phase) throws SQLException {
    	phase.setPhaseId(rs.getInt("phaseid"));
    	phase.setPhaseName(rs.getString("phasename"));
    	phase.setProjectName(rs.getString("projectName"));
    	phase.setProjectId(rs.getInt("projectId"));
    }

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

}
