package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.dao.TestSuitDao;
import com.intel.cid.utils.Utils;

public class TestSuiteDaoImpl implements TestSuitDao {

	private static Logger logger = Logger.getLogger(TestSuiteDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addTestSuite(final TestSuite testsuite) throws Exception {

		final String sql = "insert into testsuite(testsuitename,teamid,projectid,features,oses,userid,totalcases,createdate,modifydate) values(?,?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		logger.info("sql:" + sql);

		int result = userJdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {

				PreparedStatement ps = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);

				mapTestSuiteToPs(testsuite, ps);
				return ps;
			}
		}, keyHolder);
		logger.info("result:" + result);

		return keyHolder.getKey().intValue();
	}

	public int delTestSuite(TestSuite testsuite) throws Exception {

		return delTestSuiteById(testsuite.getTestSuiteId());
	}

	public int delTestSuiteById(int id) throws Exception {
		//String sql = "delete from testsuite where testsuiteid ='" + id + "'";
		String sql = "UPDATE `testsuite` SET `STATUS`='1' WHERE testsuiteId ='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public int queryTestSuiteSize(Map<String, String> filterMap) {
		String userId = filterMap.get("userId");
		String sql = "SELECT count(*) FROM testsuite WHERE (status <> 1 or status is null) and EXISTS (SELECT * from user_team as map WHERE"
				+ " (map.status <> 1 or map.status is null) and map.TEAMID = testsuite.TEAMID and map.USERID=" + userId + ")";
		sql = filterQueryTestSuiteCondition(sql, filterMap);
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("result:" + result);
		return result;

	}
	
	
	public List<TestSuite> listTestSuites(Map<String, String> filterMap,
			PageBean pageBean) throws Exception {

		String userId = filterMap.get("userId");
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = "SELECT * FROM testsuite WHERE (status <> 1 or status is null) and EXISTS (SELECT * from user_team as map WHERE "
				+ "(map.status <> 1 or map.status is null) and map.TEAMID = testsuite.TEAMID and map.USERID=" + userId + ")";
		sql = filterQueryTestSuiteCondition(sql, filterMap);
		sql = sql + " limit " + itemFrom + "," + count;

		@SuppressWarnings("unchecked")
		List<TestSuite> testSuiteList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestSuite testsuite = new TestSuite();
						mapTestSuiteFromRs(testsuite, rs);
						return testsuite;
					}
				});

		return testSuiteList;
	}

	
	public List<TestSuite> listTestSuiteByProjectId(int projectId) throws Exception {
		
	    String sql =" SELECT * FROM testsuite WHERE (status <> 1 or status is null) and projectId = "+projectId;
	    logger.info("sql"+sql);
	    @SuppressWarnings("unchecked")
	    List<TestSuite> testSuiteList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestSuite testsuite = new TestSuite();
						mapTestSuiteFromRs(testsuite, rs);
						return testsuite;
					}
				});

		return testSuiteList;
	}
	
	public List<TestSuite> listTestSuites(int userId) throws Exception {

		String sql = "SELECT * FROM testsuite WHERE EXISTS(SELECT * from user_team as map WHERE map.TEAMID = testsuite.TEAMID and map.USERID="
				+ userId + ")";

		@SuppressWarnings("unchecked")
		List<TestSuite> testSuiteList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestSuite testsuite = new TestSuite();
						mapTestSuiteFromRs(testsuite, rs);
						return testsuite;
					}
				});

		return testSuiteList;
	}

	public String filterQueryTestSuiteCondition(String sql,
			Map<String, String> filterMap) {
		sql = sql + " %s  ";
		String projectId = filterMap.get("projectId");

		// boolean flag = true;// "where" keywords already added or not

		if (Utils.isNullORWhiteSpace(projectId) || projectId.trim().equals("-1")) {

			sql = String.format(sql, "");

		} else {
			sql = String.format(sql, "  and testsuite.projectId='" + projectId
					+ "'");

		}

		return sql;
	}

	public List<TestSuite> queryAllTestSuitesByPage(PageBean pageBean)
			throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = " select * from testsuite limit " + itemFrom + "," + count;
		;
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestSuite> testSuiteList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestSuite testsuite = new TestSuite();
						mapTestSuiteFromRs(testsuite, rs);
						return testsuite;
					}
				});

		logger.info("result:" + testSuiteList);
		return testSuiteList;
	}

	public List<TestSuite> listAllTestSuites() throws Exception {

		String sql = "select * from testsuite ";

		@SuppressWarnings("unchecked")
		List<TestSuite> testSuiteList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestSuite testsuite = new TestSuite();
						mapTestSuiteFromRs(testsuite, rs);
						return testsuite;
					}
				});

		return testSuiteList;
	}

	
	public int queryBySuitNameSize(TestSuite testsuite)
			throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != testsuite.getTestSuiteId()) {
			sql = "select count(*) from testsuite where (status <> 1 or status is null) and testSuiteName  ='"
					+ testsuite.getTestSuiteName() + "'  and testSuiteId!='"
					+ testsuite.getTestSuiteId() + "' ";
			logger.info("queryBySuitNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		

		} else {
			sql = "select count(*) from testsuite where (status <> 1 or status is null) and testSuiteName ='"
					+ testsuite.getTestSuiteName() + "'  ";
			logger.info("queryBySuitNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			
		}

		logger.info("queryBySuiteNameCycleSize method result:" + result);
		return size;
	}
	
	
	public TestSuite queryTestSuiteById(int id) throws Exception {

		String sql = "select * from testsuite where testsuiteid='" + id + "'";
		logger.info("sql:" + sql);
		final TestSuite testsuite = new TestSuite();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTestSuiteFromRs(testsuite, rs);

			}
		});

		logger.info("result:" + testsuite);
		return testsuite;
	}

	public int updateTestSuite(final TestSuite testsuite) throws Exception {
		String sql = "update testsuite set testsuitename =?,teamid=?,projectid=?,features=?,oses=?,userid=?,totalcases=?,createdate=?,modifydate=? where testsuiteid='"
				+ testsuite.getTestSuiteId() + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTestSuiteToPs(testsuite, ps);

					}
				});

		logger.info("result:" + result);
		return result;
	}

	public static void mapTestSuiteToPs(final TestSuite testsuite,
			PreparedStatement ps) throws SQLException {
		if (Utils.isNullORWhiteSpace(testsuite.getTestSuiteName())) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setString(1, testsuite.getTestSuiteName());
		}
		if (testsuite.getTeamId() == 0) {

			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testsuite.getTeamId());
		}

		
		if (testsuite.getProjectId() == 0) {

			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testsuite.getProjectId());
		}
		
		
		
		if (Utils.isNullORWhiteSpace(testsuite.getFeatures())) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setString(4, testsuite.getFeatures());
		}
		if (Utils.isNullORWhiteSpace(testsuite.getOses())) {
			ps.setNull(5, Types.NULL);
		} else {
			ps.setString(5, testsuite.getOses());
		}

		if (testsuite.getUserId() == 0) {
			ps.setNull(6, Types.NULL);
		} else {
			ps.setInt(6, testsuite.getUserId());
		}
		if (testsuite.getTotalCases() == 0) {
			ps.setNull(7, Types.NULL);
		} else {
			ps.setInt(7, testsuite.getTotalCases());
		}

		if (Utils.isNullORWhiteSpace(testsuite.getCreateDate())) {
			ps.setNull(8, Types.NULL);
		} else {
			ps.setString(8, testsuite.getCreateDate());
		}
		if (Utils.isNullORWhiteSpace(testsuite.getModifyDate())) {
			ps.setNull(9, Types.NULL);
		} else {
			ps.setString(9, testsuite.getModifyDate());
		}

	}

	public static void mapTestSuiteFromRs(final TestSuite testsuite,
			ResultSet rs) throws SQLException {
		testsuite.setTestSuiteId(rs.getInt("testsuiteid"));
		testsuite.setTestSuiteName(rs.getString("testsuitename"));
		testsuite.setTeamId(rs.getInt("teamid"));
		testsuite.setProjectId(rs.getInt("projectid"));
		testsuite.setUserId(rs.getInt("userid"));
		testsuite.setFeatures(rs.getString("features"));
		testsuite.setOses(rs.getString("oses"));
		testsuite.setTotalCases(rs.getInt("totalcases"));
		testsuite.setCreateDate(rs.getString("createdate"));
		testsuite.setModifyDate(rs.getString("modifydate"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
