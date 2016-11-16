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

import com.intel.cid.common.bean.ExecutionOS;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.ExecutionOSDao;
import com.intel.cid.utils.Utils;

public class ExecutionOSDaoImpl implements ExecutionOSDao {

    private static Logger logger = Logger.getLogger(ExecutionOSDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addExecutionOS(final ExecutionOS executionOS) throws Exception {

    	// osName,projectId,hostName,ip,loginName,passwd, port,codePath, cmds,parameters, createDate
        String sql = "insert into executionos( osName,projectId,hostName,ip,loginName,passwd, port,codePath, cmds,parameters, createDate) values(?,?,?,?,?,?,?,?,?,?,?)";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

                mapOsToPs(executionOS, ps);

            }
        });
        logger.info("result:" + result);

        return result;
    }

    /**
     * Check duplicate under same project
     * @param platform
     * @return
     * @throws Exception
     */ 
        
    	public int queryExecutionOSSize(ExecutionOS executionOS) throws Exception {
    		String sql = "";
    		int result = 0;
    		int size = 0;
    		if (0 != executionOS.getOsId()) {
    			sql = "select count(*) from executionos where (status <> 1 or status is null) and projectId = '"+ executionOS.getProjectId() +"' "
    					+ "and osName = '"+ executionOS.getOsName() + "'  and osId !='"
    			+ executionOS.getOsId() + "'";
    			logger.info("queryByExecutionOSSize method result:" + sql);
    			result = userJdbcTemplate.queryForInt(sql);
    			if(result!=0){
    				size = 1;
    			}
    		} else {
    			sql = "select count(*) from executionos where (status <> 1 or status is null) and projectId = '"+ executionOS.getProjectId() +"'  "
    					+ "and osName ='"+ executionOS.getOsName() + "'  ";
    			logger.info("queryByExecutionOSSize method result:" + sql);
    			result = userJdbcTemplate.queryForInt(sql);
    			if(result!=0){
    				size = 1;
    			}
    		}

    		logger.info("queryByExecutionOSSize method result:" + result);
    		return size;
    	}

    public int delExecutionOSById(int id) throws Exception {
        //String sql = "delete from executionos where osid ='" + id + "'";
        String sql = "UPDATE `executionos` SET `STATUS`='1' WHERE osid='" + id + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }
    public List<ExecutionOS> queryExecutionOsByUserId(int userId) throws Exception {
        String sql = " SELECT * from executionos e WHERE e.projectId in (SELECT projectId from user_team as map,project as pro WHERE  map.TEAMID = pro.TEAMID and map.USERID='"+userId+"')";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<ExecutionOS> osList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

            	ExecutionOS os = new ExecutionOS();
                mapOsFromRs(rs, os);

                return os;
            }
        });

        logger.info("result:" + osList);
        return osList;
    }

    
    
    public List<ExecutionOS> queryExecutionOSAll() throws Exception {

        //String sql = " select * from executionos";
        String sql = "select a.* , b.PROJECTNAME from executionos a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null)";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<ExecutionOS> osList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

            	ExecutionOS os = new ExecutionOS();
                os.setOsId(rs.getInt("osid"));
                os.setOsName(rs.getString("osname"));
                os.setCmds(rs.getString("cmds"));
                os.setProjectId(rs.getInt("projectId"));
                os.setParameters(rs.getString("parameters"));
                os.setCodePath(rs.getString("codePath"));
                os.setHostName(rs.getString("hostName"));
                os.setIp(rs.getString("ip"));
                os.setLoginName(rs.getString("loginName"));
                os.setPort(rs.getString("port"));
                os.setPasswd(rs.getString("passwd"));
            	os.setCreateDate(rs.getString("createDate"));
            	os.setProjectName(rs.getString("PROJECTNAME"));
                return os;
            }
        });

        logger.info("result:" + osList);
        return osList;
    }

    public List<ExecutionOS> queryExecutionOSByProject(int id) throws Exception {

        //String sql = " select * from executionos";
        String sql = "select a.* , b.PROJECTNAME from executionos a left join project b on a.PROJECTID = b.PROJECTID "
        		+ "where (a.status <> 1 or a.status is null) and a.projectId = '"+id+"'";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<ExecutionOS> osList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

            	ExecutionOS os = new ExecutionOS();
                os.setOsId(rs.getInt("osid"));
                os.setOsName(rs.getString("osname"));
                os.setCmds(rs.getString("cmds"));
                os.setProjectId(rs.getInt("projectId"));
                os.setParameters(rs.getString("parameters"));
                os.setCodePath(rs.getString("codePath"));
                os.setHostName(rs.getString("hostName"));
                os.setIp(rs.getString("ip"));
                os.setLoginName(rs.getString("loginName"));
                os.setPort(rs.getString("port"));
                os.setPasswd(rs.getString("passwd"));
            	os.setCreateDate(rs.getString("createDate"));
            	os.setProjectName(rs.getString("PROJECTNAME"));
                return os;
            }
        });

        logger.info("result:" + osList);
        return osList;
    }
    
    public ExecutionOS queryExecutionOSById(int id) throws Exception {

        String sql = "select * from executionos where osid='" + id + "'";
        logger.info("sql:" + sql);
        final ExecutionOS os = new ExecutionOS();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
               mapOsFromRs(rs, os);

            }
        });

        logger.info("result:" + os);
        return os;
    }

    public int updateExecutionOS(final ExecutionOS os) throws Exception {
        String sql = "update executionos set osName=?,projectId=?,hostName=?,ip=?,loginName=?,passwd=?, port=?,codePath=?, cmds=?,parameters=?, createDate=? where osid='" + os.getOsId() + "'";
        logger.info("sql:" + sql);
      
    	int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapOsToPs(os, ps);

					}
				});

		logger.info("result:" + result);
		return result;
        
       
    }

    public static void mapOsToPs(final ExecutionOS executionOS, PreparedStatement ps) throws SQLException {

        if (Utils.isNullORWhiteSpace(executionOS.getOsName())) {

            ps.setNull(1, Types.NULL);

        } else {
            ps.setString(1, executionOS.getOsName());
        }
        if (0==executionOS.getProjectId()) {

            ps.setNull(2, Types.NULL);

        } else {
            ps.setInt(2, executionOS.getProjectId());
            
        } if (Utils.isNullORWhiteSpace(executionOS.getHostName())) {

            ps.setNull(3, Types.NULL);
           
        } else {
            ps.setString(3, executionOS.getHostName());
            
            
            
        } if (Utils.isNullORWhiteSpace(executionOS.getIp())) {

            ps.setNull(4, Types.NULL);

        } else {
            ps.setString(4, executionOS.getIp());
            
            
        } if (Utils.isNullORWhiteSpace(executionOS.getLoginName())) {

            ps.setNull(5, Types.NULL);

        } else {
            ps.setString(5, executionOS.getLoginName());
        } if (Utils.isNullORWhiteSpace(executionOS.getPasswd())) {

            ps.setNull(6, Types.NULL);
        
        } else {
            ps.setString(6, executionOS.getPasswd());
        } 
        if (Utils.isNullORWhiteSpace(executionOS.getPort())) {

            ps.setNull(7, Types.NULL);
        
        } else {
            ps.setString(7, executionOS.getPort());
        } 
        if (Utils.isNullORWhiteSpace(executionOS.getCodePath())) {

            ps.setNull(8, Types.NULL);
        
        } else {
            ps.setString(8, executionOS.getCodePath());
        } 
        if (Utils.isNullORWhiteSpace(executionOS.getCmds())) {

            ps.setNull(9, Types.NULL);
        
        } else {
            ps.setString(9, executionOS.getCmds());
        } 
        if (Utils.isNullORWhiteSpace(executionOS.getParameters())) {

            ps.setNull(10, Types.NULL);
        
        } else {
            ps.setString(10, executionOS.getParameters());
        } 
        if (Utils.isNullORWhiteSpace(executionOS.getCreateDate())) {

            ps.setNull(11, Types.NULL);
        
        } else {
            ps.setString(11, executionOS.getCreateDate());
        } 
      
    }

    public static void mapOsFromRs(ResultSet rs, ExecutionOS os) throws SQLException {
        os.setOsId(rs.getInt("osid"));
        os.setOsName(rs.getString("osname"));
        os.setCmds(rs.getString("cmds"));
        os.setProjectId(rs.getInt("projectId"));
        os.setParameters(rs.getString("parameters"));
        os.setCodePath(rs.getString("codePath"));
        os.setHostName(rs.getString("hostName"));
        os.setIp(rs.getString("ip"));
        os.setLoginName(rs.getString("loginName"));
        os.setPort(rs.getString("port"));
        os.setPasswd(rs.getString("passwd"));
    	os.setCreateDate(rs.getString("createDate"));
    	
    }

    
    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }



	


	
}
