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
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.dao.PlatformDao;
import com.intel.cid.utils.Utils;

public class PlatformDaoImpl implements PlatformDao {

    private static Logger logger = Logger.getLogger(PlatformDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addPlatform(final Platform platform) throws Exception {

        String sql = "insert into platform(platformname,projectid) values(?,?)";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

                mapPlatformToPs(platform, ps);

            }
        });
        logger.info("result:" + result);

        return result;
    }

    public int delPlatform(Platform platform) throws Exception {

        return delPlatformById(platform.getPlatformId());
    }

    public int delPlatformById(int id) throws Exception {
        //String sql = "delete from platform where platformid ='" + id + "'";
        String sql = "UPDATE `platform` SET `STATUS`='1' WHERE platformId='" + id + "'";
        logger.info("Delete platform sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }

    public List<Platform> listAllPlatforms() throws Exception {

        String sql = "select  a.* ,b.PROJECTNAME  from platform a left join project b on a.projectid = b.projectid where (a.status <> 1 or a.status is null)";
        logger.info("Select all platforms sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Platform> platformList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Platform platform = new Platform();
                platform.setPlatformId(rs.getInt("platformid"));
                platform.setPlatformName(rs.getString("platformname"));
                platform.setProjectId(rs.getInt("projectid"));
                platform.setProjectName(rs.getString("projectName"));
                return platform;
            }
        });

        logger.info("result:" + platformList);
        return platformList;
    }

/**
 * Check duplicate under same project
 * @param platform
 * @return
 * @throws Exception
 */ 
    
	public int queryPlatformSize(Platform platform) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != platform.getPlatformId()) {
			sql = "select count(*) from platform where (status <> 1 or status is null) and projectId = '"+ platform.getProjectId() +"' "
					+ "and platformName = '"+ platform.getPlatformName() + "'  and platformId !='"
			+ platform.getPlatformId() + "'";
			logger.info("queryByPlatformNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from platform where (status <> 1 or status is null) and projectId = '"+ platform.getProjectId() +"'  "
					+ "and platformName ='"+ platform.getPlatformName() + "'  ";
			logger.info("queryByPlatformNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByPlatformNameSize method result:" + result);
		return size;
	}
    
    public List<Platform> listPlatformsInTeams(int userId) throws Exception {

        String sql = " SELECT * from platform WHERE EXISTS ( SELECT * from user_team as map WHERE map.TEAMID = platform.TEAMID and map.USERID="+userId+")";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Platform> platformList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Platform platform = new Platform();
                mapTeamFromRs(rs, platform);
                return platform;
            }
        });

        logger.info("result:" + platformList);
        return platformList;
    }
    
    public List<Platform> queryPlatformByFeatureId(String featureId) throws Exception {

        String sql =  "select platform.* from platform, feature_platform" +
        " where platform.platformid = feature_platform.platformid"+
        " and feature_platform.featureid =" + featureId;
        logger.info("listPlatforms method sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Platform> platformList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Platform platform = new Platform();
                mapTeamFromRs(rs, platform);
                return platform;
            }
        });

        logger.info("listPlatforms method result:" + platformList);
        return platformList;
    }
    
    public List<Platform> listPlatforms(int id) throws Exception {

        String sql = " select * from platform where (status <> 1 or status is null) and projectid ='" + id + "'";
        logger.info("listPlatforms method sql:" + sql);
        @SuppressWarnings("unchecked")
        List<Platform> platformList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                Platform platform = new Platform();
                mapTeamFromRs(rs, platform);
                return platform;
            }
        });

        logger.info("listPlatforms method result:" + platformList);
        return platformList;
    }

    
    
    public List<?>queryPlatformList(String teamId){
        String sql = " select * from platform where teamid ='" + teamId + "'";	
        
        logger.info(sql);
        return userJdbcTemplate.queryForList(sql);
    	
    }
    
    public Platform queryPlatformById(int id) throws Exception {

        String sql = "select * from platform where platformid='" + id + "'";
        logger.info("sql:" + sql);
        final Platform platform = new Platform();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
                platform.setPlatformId(rs.getInt("platformid"));
                platform.setPlatformName(rs.getString("platformname"));
                platform.setProjectId(rs.getInt("projectid"));

            }
        });

        logger.info("result:" + platform);
        return platform;
    }

    public int updatePlatform(final Platform platform) throws Exception {
        String sql = "update platform set platformname=?,projectid =? where platformid='" + platform.getPlatformId() + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {

                mapPlatformToPs(platform, ps);

            }
        });

        logger.info("result:" + result);
        return result;
    }

    public static void mapPlatformToPs(final Platform platform, PreparedStatement ps) throws SQLException {
        if (Utils.isNullORWhiteSpace(platform.getPlatformName())) {

            ps.setString(1, null);

        } else {
            ps.setString(1, platform.getPlatformName());
        }

        if (platform.getProjectId() == 0) {
            ps.setNull(2, Types.NULL);
        } else {
            ps.setInt(2, platform.getProjectId());
        }

    }

    public static void mapTeamFromRs(ResultSet rs, Platform platform) throws SQLException {
        platform.setPlatformId(rs.getInt("platformid"));
        platform.setPlatformName(rs.getString("platformname"));
        platform.setProjectId(rs.getInt("projectid"));
    }

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

}
