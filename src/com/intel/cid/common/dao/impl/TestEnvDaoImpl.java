package com.intel.cid.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.TestEnv;
import com.intel.cid.common.dao.TestEnvDao;

public class TestEnvDaoImpl implements TestEnvDao {

	private static Logger logger = Logger.getLogger(TestEnvDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addTestenv(final TestEnv testenv) throws Exception {

		String sql = "insert into testenv(envName,projectId,`desc`) values('"
				+ testenv.getEnvName() + "','" + testenv.getProjectId() + "','"
				+ testenv.getDesc() + "')";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		return result;
	}

	public int delTestenvById(int id) throws Exception {
		//String sql = "delete from testenv where id ='" + id + "'";
		String sql = "UPDATE `testenv` SET `STATUS`='1' WHERE id='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public List<TestEnv> queryAlltestenvs(int id) throws Exception {

		String sql = " select * from testenv t";
		if (id != 0) {
			sql += "where t.proejctId='" + id + "' ";
		}
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestEnv> testenvList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestEnv testenv = new TestEnv();
						mapTestenvFromRs(rs, testenv);

						return testenv;
					}
				});

		logger.info("result:" + testenvList);
		return testenvList;
	}

	public List<TestEnv> queryAllEnvs() throws Exception {

		//String sql = " select * from testenv t";
		String sql = "select a.* , b.PROJECTNAME from testenv a left join project b on a.PROJECTID = b.PROJECTID where (a.status <> 1 or a.status is null)";
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestEnv> testenvList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestEnv testenv = new TestEnv();
						testenv.setEnvName(rs.getString("envName"));
						testenv.setProjectId(rs.getInt("projectid"));
						testenv.setDesc(rs.getString("desc"));
						testenv.setId(rs.getInt("id"));
						testenv.setProjectName(rs.getString("PROJECTNAME"));

						return testenv;
					}
				});

		logger.info("result:" + testenvList);
		return testenvList;
	}
	
	public TestEnv querytestenvById(int id) throws Exception {
		String sql = "select * from testenv t where t.id='" + id + "'";
		logger.info("sql:" + sql);
		final TestEnv testenv = new TestEnv();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapTestenvFromRs(rs, testenv);

			}
		});

		logger.info("result:" + testenv);
		return testenv;
	}

	public int updateTestenv(final TestEnv testenv) throws Exception {
		String sql = "update testenv set envname='" + testenv.getEnvName()
				+ "' , projectid='" + testenv.getProjectId() + "' , `desc`='"
				+ testenv.getDesc() + "' where id='" + testenv.getId() + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public static void mapTestenvFromRs(ResultSet rs, TestEnv testenv)
			throws SQLException {
		testenv.setEnvName(rs.getString("envName"));
		testenv.setProjectId(rs.getInt("projectid"));
		testenv.setDesc(rs.getString("desc"));
		testenv.setId(rs.getInt("id"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
