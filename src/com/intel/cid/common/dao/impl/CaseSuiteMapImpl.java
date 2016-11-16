package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.dao.CaseSuiteMapDao;

public class CaseSuiteMapImpl implements CaseSuiteMapDao {

	private static Logger logger = Logger.getLogger(CaseSuiteMapImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int delTestCaseSuitBySuitId(int id) throws Exception

	{
		//String sql = "delete from casesuitemap where testsuiteid='" + id + "'";
		String sql = "UPDATE `casesuitemap` SET `STATUS`='1' WHERE testsuiteId ='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<TestSuite> listTestSuiteByTestCase(int testCaseId)
			throws Exception {

		String sql = "select * from testsuite as tu  WHERE EXISTS (SELECT * FROM casesuitemap as map WHERE tu.testsuiteid = map.testsuiteid and  map.TESTCASEID ="
				+ testCaseId + ")";

		logger.info("listTestSuiteByTestCase sql : "+sql);
		
		
		List<TestSuite> testSuiteList = userJdbcTemplate.query(sql, new RowMapper(){

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				TestSuite testSuite = new TestSuite();
				TestSuiteDaoImpl.mapTestSuiteFromRs(testSuite, rs);
				
				return testSuite;
			}});
		
		return testSuiteList;
		
	}

	public int delTestCaseSuitBySuitIdAndCaseId(int suitId, int caseId)
			throws Exception

	{
		String sql = "delete from casesuitemap where testsuiteid='" + suitId
				+ "'" + "and testcaseid='" + caseId + "'";

		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public int delBatchTestCases(final int suitId, final String[] testcases)
			throws Exception {
		//String sql = "delete from casesuitemap where testsuiteid=? and testcaseid=?";
		String sql = "UPDATE `casesuitemap` SET `STATUS`='1' WHERE testsuiteid=? and testcaseid=?";
		logger.info("delBatchTestCases method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, suitId);
						ps.setInt(2, Integer.parseInt(testcases[i].trim()));
					}

					public int getBatchSize() {

						return testcases.length;
					}
				});
		logger.info("delBatchTestCases method result:" + result.length);
		return result.length;
	}

	public int addTestCaseBatchByStringArray(final String[] testcases,
			final int suitId) throws Exception {

		String sql = "insert into casesuitemap(testsuiteid,testcaseid) values(?,?)";
		logger.info("sql:" + sql);

		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, suitId);
						ps.setInt(2, Integer.parseInt(testcases[i].trim()));

					}

					public int getBatchSize() {

						return testcases.length;
					}
				});

		logger.info("add total tesecases:" + result.length);
		return result.length;
	}

	
	
	
	public int addTestCaseBatchByArrayList(final List<TestCase> testcaseList,
			final int suitId) throws Exception {

		String sql = "insert into casesuitemap(testsuiteid,testcaseid) values(?,?)";
		logger.info("sql:" + sql);

		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, suitId);
						ps.setInt(2, testcaseList.get(i).getTestCaseId());

					}

					public int getBatchSize() {

						return testcaseList.size();
					}
				});

		logger.info("add total tesecases:" + result.length);
		return result.length;
	}

	@SuppressWarnings("unchecked")
	public List<TestCase> queryTestCasesBySuitId(int suitId) throws Exception {

		String sql = "select tc.* from testcase as tc where EXISTS ( select * from casesuitemap as map  WHERE (map.status <> 1 or map.status is null) and map.testcaseid = tc.testcaseid   AND map.testsuiteid="
				+ suitId + ")" +" order by tc.testcasealiasid";

		logger.info("sql:" + sql);
		List<TestCase> testcaseList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testcase = new TestCase();
						TestCaseDaoImpl.mapTestCaseFromRs(rs, testcase);
						return testcase;
					}
				});

		logger.info("result:" + testcaseList);
		return testcaseList;
	}

	public List<TestCase> queryTestCaseByPageAndSuitId(PageBean pageBean,
			int suitId) throws Exception {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = "select tc.* from testcase  as tc where EXISTS ( select * from casesuitemap as map  WHERE (map.status <> 1 or map.status is null) and map.testcaseid = tc.testcaseid   AND map.testsuiteid=" +suitId+")"
			
				+ " order by tc.testcasealiasid  limit "
				+ itemFrom + "," + count;
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						TestCase testCase = new TestCase();
						TestCaseDaoImpl.mapTestCaseFromRs(rs, testCase);
						return testCase;

					}
				});
		logger.info("result:" + testCaseList.size());
		return testCaseList;
	}

	public int queryTestCaseSizeBySuitId(int suitId) throws Exception {
		String sql = "select count(*)  from casesuitemap where testsuiteid='"
				+ suitId + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("result:" + result);
		return result;
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
