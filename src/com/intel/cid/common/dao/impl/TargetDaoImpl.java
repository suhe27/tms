

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

import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.Target;
import com.intel.cid.common.dao.TargetDao;
import com.intel.cid.utils.Utils;

public class TargetDaoImpl implements TargetDao {

    private static Logger logger = Logger.getLogger(TargetDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addTarget(final Target target) throws Exception {

        String sql = "insert into target(targetname,projectid) values(?,?)";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

            	mapTargetToPs(target, ps);

            }
        });
        logger.info("result:" + result);

        return result;
    }

    public int delTarget(Target target) throws Exception {

        return delTargetById(target.getTargetId());
    }
 
    public int delTargetById(int id) throws Exception {
        //String sql = "delete from  target where  targetId ='" + id + "'";
        String sql = "UPDATE `target` SET `STATUS`='1' WHERE targetid='" + id + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }

    public List<Target> queryAllTarget() throws Exception {

        //String sql = " select * from target";
        String sql = "select a.* , b.PROJECTNAME from target a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null)";
        logger.info("Query all target sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Target> targetList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

            	Target target= new Target();
            	target.setTargetId(rs.getInt("targetid"));
            	target.setTargetName(rs.getString("targetname"));
            	target.setProjectId(rs.getInt("projectid"));
            	target.setProjectName(rs.getString("PROJECTNAME"));
                return target;
            }
        });

        logger.info("result:" + targetList);
        return targetList;
    }

    
    public List<Target> listTargetByProjectId(int projectId) throws Exception {

        String sql = " select * from target where (status <> 1 or status is null) and projectId="+projectId;
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Target> targetList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

            	Target target= new Target();
                mapTargetFromRs(rs, target);
                return target;
            }
        });

        logger.info("result:" + targetList);
        return targetList;
    }
    public Target queryTargetById(int id) throws Exception {

        String sql = "select * from target where targetId='" + id + "'";
        logger.info("sql:" + sql);
        final Target target= new Target();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
               mapTargetFromRs(rs, target);

            }
        });

        logger.info("result:" + target);
        return target;
    }

    public int updateTarget(final Target target) throws Exception {
        String sql = "update target set targetName=?,`PROJECTID`=?  where targetid='" + target.getTargetId() + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, target.getTargetName());
                ps.setInt(2,target.getProjectId());
            }
        });

        logger.info("result:" + result);
        return result;
    }

	public int queryTargetSize(Target target) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != target.getTargetId()) {
			sql = "select count(*) from target where (status <> 1 or status is null) and projectId = '"+ target.getProjectId() +"' "
					+ "and targetName = '"+ target.getTargetName() + "'  and targetId !='"
			+ target.getTargetId() + "'";
			logger.info("queryByTargetNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from target where (status <> 1 or status is null) and projectId = '"+ target.getProjectId() +"'  "
					+ "and targetName ='"+ target.getTargetName() + "'  ";
			logger.info("queryByTargetNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByTargetNameSize method result:" + result);
		return size;
	}    
    
    public static void mapTargetToPs(final Target target, PreparedStatement ps) throws SQLException {

        if (Utils.isNullORWhiteSpace(target.getTargetName())) {

          ps.setString(1, null);

        } else {
            ps.setString(1, target.getTargetName());
        }
        if (target.getProjectId() ==0) {
        	
        	  ps.setNull(2, Types.NULL);
			
		} else {
             ps.setInt(2, target.getProjectId());
		}

    }

    public static void mapTargetFromRs(ResultSet rs, Target target ) throws SQLException {
    	target.setTargetId(rs.getInt("targetid"));
    	target.setTargetName(rs.getString("targetname"));
    	target.setProjectId(rs.getInt("projectid"));
    }
 

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }


}
