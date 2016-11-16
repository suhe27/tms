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
import org.springframework.jdbc.support.GeneratedKeyHolder;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.dao.SubTestPlanDao;
import com.intel.cid.utils.Utils;

public class SubTestPlanDaoImpl implements SubTestPlanDao {

	private static Logger logger = Logger.getLogger(SubTestPlanDaoImpl.class);

	private JdbcTemplate userJdbcTemplate; 
 
	public int addSubTestPlan(final SubTestPlan testPlan) throws Exception {
		final String sql = "insert into subtestplan(subplanname,projectId,owner,duedate,totalcases,createdate,modifydate)"
				+ "values(?,?,?,?,?,?,?)";
		logger.info("addSubTestPlan method sql:" + sql);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		int result = userJdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {

				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapSubTestPlanToPs(testPlan, ps);
				return ps;
			}
		}, keyHolder);

		logger.info("addSubTestPlan method result:" + result);

		return keyHolder.getKey().intValue();
	}

	
	
	public int delSubTestPlan(SubTestPlan testPlan) throws Exception {

		return delSubTestPlanById(testPlan.getSubPlanId());
	}

	
	public int delSubTestPlanById(int planId) throws Exception {

		String sql = "delete from subtestplan where subplanid='" + planId + "'";
		logger.info("delSubTestPlanById method sql:" + sql);

		int result = userJdbcTemplate.update(sql);
		logger.info("delSubTestPlanById method  result:" + result);
		return result;
	}

	public SubTestPlan querySubTestPlanById(int subPlanId) throws Exception {

		String sql = "select * from subtestplan where subPlanId='" + subPlanId
				+ "'";
		logger.info("querySubTestPlanById method sql :" + sql);
		final SubTestPlan subTestPlan = new SubTestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {

				mapSubTestPlanFromRs(subTestPlan, rs);

			}
		});

		logger.info("querySubTestPlanById method result:"
				+ subTestPlan.logInfo());
		return subTestPlan;
	}


	public void refreshSubTestPlan(final SubTestPlan subPlan) throws Exception {

		String sql = "select sum(totalcases) as totalcases from testunit where (status <> 1 or status is null) and testunit.subplanid = " + subPlan.getSubPlanId() ;
		logger.info("refreshSubTestPlan sql:" + sql);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				subPlan.setTotalCases(rs.getInt("totalcases"));

			}
		});

		updateSubTestPlan(subPlan);
	}
	
	
	public int updateSubTestPlan(final SubTestPlan testPlan) throws Exception {

		String sql = "update subtestplan set subplanname=?,projectId=?,owner=?,duedate=?,"
				+ " totalcases=?,createdate=?,modifydate=?"
				+ " where subplanid='" + testPlan.getSubPlanId() + "'";
		logger.info("updateSubTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						mapSubTestPlanToPs(testPlan, ps);

					}
				});
		logger.info("updateSubTestPlan method result:" + result);
		return result;
	}

	public int delBatchSubTestPlan(final String[] subTestPlans)
			throws Exception {

		//String sql = "delete from subtestplan where subplanid =?";
		String sql = "UPDATE `subtestplan` SET `STATUS`='1' WHERE subplanid=?";
		logger.info("delBatchSubTestPlan method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(subTestPlans[i].trim()));

					}

					public int getBatchSize() {

						return subTestPlans.length;
					}
				});
		logger.info("delBatchSubTestPlan method result:" + result.length);

		return result.length;
	}

	public int delBatchSubTestPlan(final List<SubTestPlan> subTestPlanList)
			throws Exception {

		//String sql = "delete from subtestplan where subplanid =?";
		String sql = "UPDATE `subtestplan` SET `STATUS`='1' WHERE subplanid =?";
		logger.info("delBatchSubTestPlan method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, subTestPlanList.get(i).getSubPlanId());

					}

					public int getBatchSize() {

						return subTestPlanList.size();
					}
				});
		logger.info("delBatchSubTestPlan method result:" + result.length);

		return result.length;
	}

	@SuppressWarnings("unchecked")
	public List<SubTestPlan> listSubTestPlan(int testPlanId) throws Exception {
		String sql = "select subtestplan.* from planmap left join subtestplan on planmap.subplanid = subtestplan.subplanid"
				+ " where (planmap.status <> 1 or planmap.status is null) and planmap.testplanid ='" + testPlanId + "'";
		logger.info("querySubTestPlan method sql:" + sql);

		List<SubTestPlan> subTestPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						SubTestPlan subTestPlan = new SubTestPlan();
						SubTestPlanDaoImpl
								.mapSubTestPlanFromRs(subTestPlan, rs);
						return subTestPlan;
					}
				});

		return subTestPlanList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubTestPlan> listTestCasesInSubTestPlan(int testPlanId) throws Exception {
		String sql = "select a.testcasename, a.testcaseid, c.targetid, c.subplanid, c.testunitid from testcase a right join "
				+ "casesuitemap b on b.testcaseid = a.TESTCASEID right join testunit c on b.testsuiteid = c.testsuiteid"
				+ " where c.subplanid = '" + testPlanId + "'";
		logger.info("listTestCasesInSubTestPlan method sql:" + sql);

		List<SubTestPlan> subTestPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						SubTestPlan subTestPlan = new SubTestPlan();
						SubTestPlanDaoImpl
								.mapSubPlanTestCaseFromRs(subTestPlan, rs);
						return subTestPlan;
					}
				});

		return subTestPlanList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TestCase> listTestCasesInSubTestPlan1(int testPlanId) throws Exception {
		String sql = "select a.testcasename, a.testcaseid, c.targetid, c.subplanid, c.testunitid from testcase a right join "
				+ "casesuitemap b on b.testcaseid = a.TESTCASEID right join testunit c on b.testsuiteid = c.testsuiteid"
				+ " where (c.status <> 1 or c.status is null) and (b.status <> 1 or b.status is null) and  c.subplanid = '" + testPlanId + "'";
		logger.info("listTestCasesInSubTestPlan method sql:" + sql);

		List<TestCase> subTestPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestCase subTestPlan = new TestCase();
						subTestPlan.setTestCaseName(rs.getString("testcasename"));
						subTestPlan.setTestCaseId(rs.getInt("testcaseid"));		
						subTestPlan.setTargetId(rs.getInt("targetid"));		
						subTestPlan.setSubPlanId(rs.getInt("subplanid"));
						subTestPlan.setTestUnitId(rs.getInt("testunitid"));
						return subTestPlan;
					}
				});

		return subTestPlanList;
	}

	public List<TestCase> ListTestCaseBySuiteId(PageBean pageBean, int suiteId)
			throws Exception {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = "	select  t.testcasealiasid,t.testcasename ,t.testcaseid from casesuitemap c,testcase t where  c.testcaseid= t.testcaseid  and  c.testsuiteid ='"
			+ suiteId
			+ "'"
			+ " limit "
			+ itemFrom + "," + count;
		logger.info("querySubTestPlan method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseResultList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestCase  testCase = new TestCase();
						mapTestResultFromRs(testCase, rs);
						return testCase;
					}
				});
		return testCaseResultList;
	}
	
	public int ListTestcaseSizeBySuiteId(int suiteId) throws Exception {
		String sql = "select count(*)  from casesuitemap c,testcase t where  c.testcaseid= t.testcaseid  and  c.testsuiteid ='"
				+ suiteId + "'";
		logger.info("countResultsql:" + sql);
		int size = userJdbcTemplate.queryForInt(sql);

		return size;
	}
	
	public static void mapSubTestPlanToPs(final SubTestPlan subPlan,
			PreparedStatement ps) throws SQLException {

		if (Utils.isNullORWhiteSpace(subPlan.getSubPlanName())) {
			ps.setString(1, null);
		} else {
			ps.setString(1, subPlan.getSubPlanName());
		}

		if (subPlan.getProjectId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, subPlan.getProjectId());
		}
			
		if (subPlan.getOwner() == 0) {
			ps.setNull(3, Types.NULL);

		} else {
			ps.setInt(3, subPlan.getOwner());
		}

		if (Utils.isNullORWhiteSpace(subPlan.getDueDate())) {
			ps.setString(4, null);
		} else {
			ps.setString(4, subPlan.getDueDate());

		}

		if (subPlan.getTotalCases() ==0) {
			
			ps.setNull(5, Types.NULL);
		} else {
			ps.setInt(5, subPlan.getTotalCases());
		}
		
		if (Utils.isNullORWhiteSpace(subPlan.getCreateDate())) {
			ps.setString(6, null);
		} else {
			ps.setString(6, subPlan.getCreateDate());
		}
		if (Utils.isNullORWhiteSpace(subPlan.getModifyDate())) {
			ps.setString(7, null);
		} else {
			ps.setString(7, subPlan.getModifyDate());
		}

		
	}

	public static void mapTestResultFromRs(final TestCase testCaseResult,
			ResultSet rs) throws SQLException {
		testCaseResult.setTestCaseId(rs.getInt("testcaseid"));
		testCaseResult.setTestCasealiasId("testcasealiasid");
		testCaseResult.setTestCaseName(rs.getString("testCasename"));
		/*testCaseResult.setDugId(rs.getString("dugid"));
		testCaseResult.setLog(rs.getString("log"));
		testCaseResult.setResuleType(rs.getString("resuletype"));*/
		
	}
	
	

	
	public static void mapSubTestPlanFromRs(final SubTestPlan subTestPlan,
			ResultSet rs) throws SQLException {
		subTestPlan.setSubPlanId(rs.getInt("subplanid"));
		subTestPlan.setSubPlanName(rs.getString("subplanname"));
		subTestPlan.setProjectId(rs.getInt("projectid"));		
		subTestPlan.setDueDate(rs.getString("duedate"));	
		subTestPlan.setOwner(rs.getInt("owner"));		
		subTestPlan.setTotalCases(rs.getInt("totalcases"));
		subTestPlan.setCreateDate(rs.getString("createdate"));
		subTestPlan.setModifyDate(rs.getString("modifydate"));
	
	}

	public static void mapSubPlanTestCaseFromRs(final SubTestPlan subTestPlan,
			ResultSet rs) throws SQLException {
		subTestPlan.setTestCaseName(rs.getString("testcasename"));
		subTestPlan.setTestCaseId(rs.getInt("testcaseid"));		
		subTestPlan.setTargetId(rs.getInt("targetid"));		
		subTestPlan.setSubPlanId(rs.getInt("subplanid"));
		subTestPlan.setTestUnitId(rs.getInt("testunitid"));
	}

	
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
