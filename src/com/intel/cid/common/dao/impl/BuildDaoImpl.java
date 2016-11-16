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

import com.intel.cid.common.bean.Build;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.BuildDao;
import com.intel.cid.utils.Utils;

public class BuildDaoImpl implements BuildDao {
	private static Logger logger = Logger.getLogger(BuildDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addBuild(final Build build) throws Exception {

		String sql = "insert into build(buildType,projectid) values(?,?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setString(1, build.getBuildType());
						ps.setInt(2, build.getProjectId());

					}
				});
		logger.info("result:" + result);

		return result;
	}

/*	public int delbuild(Build build) throws Exception {

		return delbuildById(build.getBuildId());
	}*/

	public int delBuild(int id) throws Exception {
		//String sql = "delete from build where buildid ='" + id + "'";
		String sql = "UPDATE `build` SET `STATUS`='1' WHERE buildid='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public int queryBuildSize(Build build) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != build.getBuildId()) {
			sql = "select count(*) from build where (status <> 1 or status is null) and projectId = '"+ build.getProjectId() +"' "
					+ "and buildType = '"+ build.getBuildType() + "'  and buildId !='"
			+ build.getBuildId() + "'";
			logger.info("queryByBuildNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from build where (status <> 1 or status is null) and projectId = '"+ build.getProjectId() +"'  "
					+ "and buildType ='"+ build.getBuildType() + "'  ";
			logger.info("queryByBuildNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByBuildNameSize method result:" + result);
		return size;
	}
	
	public List<Build> queryAllbuilds() throws Exception {

		String sql = "select a.* , b.PROJECTNAME from build a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null)";
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<Build> buildList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {

				Build build = new Build();
				build.setBuildId(rs.getInt("buildid"));
				build.setBuildType(rs.getString("buildtype"));
				build.setProjectId(rs.getInt("projectId"));
				build.setProjectName(rs.getString("projectName"));

				return build;
			}
		});

		logger.info("result:" + buildList);
		return buildList;
	}

	public List<Build> queryBuildByProjectId(int id) throws Exception {

	    String sql = "select a.* , b.PROJECTNAME from build a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null) and a.projectid ='" + id + "'";
	       
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<Build> buildList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {

				Build build = new Build();
				build.setBuildId(rs.getInt("buildid"));
				build.setBuildType(rs.getString("buildtype"));
				build.setProjectId(rs.getInt("projectId"));
				build.setProjectName(rs.getString("projectName"));

				return build;
			}
		});

		logger.info("result:" + buildList);
		return buildList;
	}
	
	public Build queryBuildById(int id) throws Exception {

		String sql = "select * from build where buildid='" + id + "'";
		logger.info("sql:" + sql);
		final Build build = new Build();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapbuildFromRs(rs, build);

			}
		});

		logger.info("result:" + build);
		return build;
	}

	public int updateBuild(final Build build) throws Exception {
		String sql = "update build set buildtype=?,projectid =? where buildid='" + build.getBuildId() + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setString(1, build.getBuildType());
						ps.setInt(2, build.getProjectId());

					}
				});

		logger.info("result:" + result);
		return result;
	}

	public static void mapbuildToPs(final Build build, PreparedStatement ps)
			throws SQLException {

		if (Utils.isNullORWhiteSpace(build.getBuildType())) {

			ps.setNull(1, Types.NULL);

		} else {
			ps.setString(1, build.getBuildType());
		}

	}

	public static void mapbuildFromRs(ResultSet rs, Build build)
			throws SQLException {
		build.setBuildId(rs.getInt("buildid"));
		build.setBuildType(rs.getString("buildtype"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	

	
	
}
