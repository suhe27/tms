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

import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.ResultTrack;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.dao.TestResultDao;
import com.intel.cid.utils.Utils;

public class TestResultDaoImpl implements TestResultDao {

	private static Logger logger = Logger.getLogger(TestResultDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int delTestResultsByTestPlan(int testPlanId) throws Exception {
		String sql = "delete from  testresult where testplanid='" + testPlanId
				+ "'";
		logger.info("delTestResultsByTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delTestResultsByTestPlan total records :" + result);
		return result;

	}

	public int delBatchTestResult(final List<SubTestPlan> subTestPlanList)
			throws Exception {

		String sql = "delete from testresult where subplanid =?";
		logger.info("delBatchTestResult method sql:" + sql);
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
		logger.info("delBatchTestResult method result:" + result.length);

		return result.length;
	}

	public int delTestResults(final String[] subTestPlans) throws Exception {
		String sql = "delete from testresult where subplanid =?";
		logger.info("delBatchTestResult method sql:" + sql);
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
		logger.info("delBatchTestResult method result:" + result.length);

		return result.length;

	}

	public int delTestResultsBySubTestPlan(int subPlanId) throws Exception {

		String sql = "delete from  testresult where subplanid='" + subPlanId
				+ "'";
		logger.info("delTestResultsBySubTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delTestResultsBySubTestPlan total records :" + result);
		return result;
	}

	public int addBatchResult(final List<TestResult> resultList)
			throws Exception {

		String sql = "insert into testresult(testplanid,subplanid,testcaseid,testcasename,resulttypeid,log,bugid,comments,modifydate)"
				+ "values(?,?,?,?,?,?,?,?,?)";
		logger.info("addBatchResult method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						TestResult testResult = resultList.get(i);
						mapTestResultToPs(ps, testResult);
					}

					public int getBatchSize() {

						return resultList.size();
					}
				});

		logger.info("addBatchResult method result:" + result.length);
		return result.length;
	}
	
	public int updateTestResultByExcel(final List<TestResult> resultList)
			throws Exception {
		String sql = "UPDATE `testresult` SET `RESULTTYPEID`=?, `LOG`=?, "
				+ "`BUGID`=?, `COMMENTS`=?,`MODIFYDATE`=?,`bugName`=? WHERE `RESULTID`=?;";
		logger.info("EditBatchResult method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						TestResult testResult = resultList.get(i);
						mapUpdateTestResultToPs(ps, testResult);
					}

					public int getBatchSize() {

						return resultList.size();
					}
				});

		logger.info("EditBatchResult method result:" + result.length);
		return result.length;
	}

	public int addTestResult(final TestResult testResult) throws Exception {

		String sql = "insert into testresult(testplanid,subplanid,testcaseid,testcasename,resulttypeid,log,bugid,comments,modifydate)"
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		logger.info("addTestResult method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTestResultToPs(ps, testResult);
					}
				});
		logger.info("addTestResult method result:" + result);
		return result;
	}

	public int updateBatchTestResult(final List<TestResult> resultList)
			throws Exception {
		String sql = "update testresult set testplanid=?,subplanid=?,testcaseid=?,testcasename =?,resulttypeid=?,log=?,bugid=?,comments=?,modifydate=?"
				+ " where resultid=?";
		logger.info("updateBatchTestResult method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						TestResult testResult = resultList.get(i);
						mapTestResultToPs(ps, testResult);
						ps.setInt(11, testResult.getResultId());
					}

					public int getBatchSize() {

						return resultList.size();
					}
				});
		logger.info("updateBatchTestResult method result:" + result.length);
		return result.length;
	}

	public int updateTestRusult(final TestResult testResult) throws Exception {

		String sql = "update testresult set testplanid=?,subplanid=?,testcaseid=?,testcasename =?,resulttypeid=?,log=?,"
				+ "bugid=?,comments=?,modifydate=?,resultTrackId=? where resultid=?";
		logger.info("updateTestResult method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						mapTestResultToPs(ps, testResult);
						ps.setInt(10, testResult.getResultTrackId());
						ps.setInt(11, testResult.getResultId());

					}
				});
		logger.info("updateTestResult method result:" + result);
		return result;

	}
	
	public int updateSingleTestRusult(final TestResult testResult) throws Exception {

		String sql = "update testresult set resulttypeid=?,log=?,bugid=?,comments=?,modifydate=? where resultid=?";
		logger.info("updateTestResult method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, testResult.getResultTypeId());
						ps.setString(2, testResult.getLog());
						ps.setString(3, testResult.getBugId());
						ps.setString(4, testResult.getComments());
						ps.setString(5, testResult.getModifyDate());
						ps.setInt(6, testResult.getResultId());

					}
				});
		logger.info("updateTestResult method result:" + result);
		return result;

	}
	
	public int updateTestRusultInSubExecution(final TestResult testResult) throws Exception {

		String sql = "update testresult set resulttypeid=?,log=?,bugid=?,comments=?,modifydate=?,resultTrackId=?,bugName=? where resultid=?";
		logger.info("updateTestRusultInSubExecution method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, testResult.getResultTypeId());
						ps.setString(2, testResult.getLog());
						ps.setString(3, testResult.getBugId());
						ps.setString(4, testResult.getComments());
						ps.setString(5, testResult.getModifyDate());
						ps.setInt(6, testResult.getResultTrackId());
						ps.setString(7, testResult.getBugName());
						ps.setInt(8, testResult.getResultId());
					}
				});
		logger.info("updateTestRusultInSubExecution method result:" + result);
		return result;

	}
	
	public void updateTestResultBySubPlanAndResult(final SubTestPlan subPlan ,final int result)
	{
		
		String sql = "update testresult set resulttypeid =? where subplanid =?";
		userJdbcTemplate.update(sql, new PreparedStatementSetter(){

		
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, result);
				ps.setInt(2, subPlan.getSubPlanId());
			}});
		
		
	}
	
	

	@SuppressWarnings("unchecked")
	public List<TestResult> listTestResultByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;

		String sql = "select * from testresult ";

		sql = filterTestResultCondition(sql, filterMap);
		sql = sql + " limit " + itemFrom + "," + count;

		logger.info("queryTestResultByPage method sql:" + sql);

		List<TestResult> testResultList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestResult result = new TestResult();

						mapTestResultFromRs(result, rs);

						return result;
					}
				});

		logger.info("queryTestResultByPage method result:"
				+ testResultList.size());
		return testResultList;
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> listTestResultsByExecutionId(int id) throws Exception {

		String sql = "select a.*,b.RESULTTYPENAME,c.OSNAME,d.PLATFORMNAME,e.TARGETNAME from testresult a left join resulttype b "
				+ "on a.RESULTTYPEID=b.RESULTTYPEID left join executionos c on a.OSID=c.OSID left join platform d on a.PLATFORMID=d.PLATFORMID "
				+ "left join target e on a.TARGETID=e.TARGETID where a.EXECUTIONID = '"+id+"'";

		logger.info("listTestResultsByExecutionId method sql:" + sql);

		List<TestResult> testResultList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestResult result = new TestResult();

						mapTestResultFromRs(result, rs);
						result.setTargetId(rs.getInt("targetId"));
						result.setOsId(rs.getInt("osId"));
						result.setPlatformId(rs.getInt("platformId"));
						result.setResultTypeName(rs.getString("resultTypeName"));
						result.setOsName(rs.getString("osName"));
						result.setPlatformName(rs.getString("platformName"));
						result.setTargetName(rs.getString("targetName"));
						
						return result;
					}
				});

		logger.info("listTestResultsByExecutionId method result:"
				+ testResultList.size());
		return testResultList;
	}
	
	@SuppressWarnings("unchecked")
	public TestResult listTestResultsByExecutionIdAndTestCaseId(int executionId, int testCaseId) throws Exception {

		String sql = "select a.*,b.RESULTTYPENAME,c.OSNAME,d.PLATFORMNAME,e.TARGETNAME from testresult a left join resulttype b "
				+ "on a.RESULTTYPEID=b.RESULTTYPEID left join executionos c on a.OSID=c.OSID left join platform d on a.PLATFORMID=d.PLATFORMID "
				+ "left join target e on a.TARGETID=e.TARGETID where a.EXECUTIONID = '"+executionId+"' and a.testcaseid = '"+testCaseId+"' limit 1";

		logger.info("listTestResultsByExecutionId method sql:" + sql);
		
		final TestResult result = new TestResult();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapTestResultFromRs(result, rs);
				result.setResultTypeName(rs.getString("resultTypeName"));
				result.setOsName(rs.getString("osName"));
				result.setPlatformName(rs.getString("platformName"));
				result.setTargetName(rs.getString("targetName"));
			}
		});

		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public TestResult listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(int executionId, int testCaseId, int targetId,int osId, int platformId) 
		throws Exception {
		String sql = "select a.*,b.RESULTTYPENAME,c.OSNAME,d.PLATFORMNAME,e.TARGETNAME from testresult a left join resulttype b "
				+ "on a.RESULTTYPEID=b.RESULTTYPEID left join executionos c on a.OSID=c.OSID left join platform d on a.PLATFORMID=d.PLATFORMID "
				+ "left join target e on a.TARGETID=e.TARGETID where a.EXECUTIONID = '"+executionId+"' and a.testcaseid = '"+testCaseId+"' "
						+ "and a.targetid='"+targetId+"' and a.osid = '"+osId+"' and a.platformid = '"+platformId+"' limit 1";

		logger.info("listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId method sql:" + sql);
		
		final TestResult result = new TestResult();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapTestResultFromRs(result, rs);
				result.setResultTypeName(rs.getString("resultTypeName"));
				result.setOsName(rs.getString("osName"));
				result.setPlatformName(rs.getString("platformName"));
				result.setTargetName(rs.getString("targetName"));
			}
		});

		return result;
		
	}
	
	public List<TestResult> listTestResultsByExecutionIds(String ids) throws Exception {

		String sql = "select a.*,b.RESULTTYPENAME,c.OSNAME,d.PLATFORMNAME,e.TARGETNAME from testresult a left join resulttype b "
				+ "on a.RESULTTYPEID=b.RESULTTYPEID left join executionos c on a.OSID=c.OSID left join platform d on a.PLATFORMID=d.PLATFORMID "
				+ "left join target e on a.TARGETID=e.TARGETID where a.EXECUTIONID in ("+ids+")";

		logger.info("listTestResultsByExecutionIds method sql:" + sql);

		@SuppressWarnings("unchecked")
		List<TestResult> testResultList = userJdbcTemplate.query(sql,new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestResult result = new TestResult();
						mapTestResultFromRs(result, rs);
						result.setResultTypeName(rs.getString("resultTypeName"));
						result.setOsName(rs.getString("osName"));
						result.setPlatformName(rs.getString("platformName"));
						result.setTargetName(rs.getString("targetName"));
						return result;
					}
				});

		logger.info("listTestResultsByExecutionIds method result:"
				+ testResultList.size());
		return testResultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TestResult> listTestResultsBySubExecutionId(int id) throws Exception {

		String sql = "select a.*,b.RESULTTYPENAME,c.OSNAME,d.PLATFORMNAME,e.TARGETNAME from testresult a left join resulttype b "
				+ "on a.RESULTTYPEID=b.RESULTTYPEID left join executionos c on a.OSID=c.OSID left join platform d on a.PLATFORMID=d.PLATFORMID "
				+ "left join target e on a.TARGETID=e.TARGETID where a.SUBEXECUTIONID = '"+id+"'";

		logger.info("listTestResultsBySUBExecutionId method sql:" + sql);

		List<TestResult> testResultList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestResult result = new TestResult();

						mapTestResultFromRs(result, rs);
						result.setResultTypeName(rs.getString("resultTypeName"));
						result.setOsName(rs.getString("osName"));
						result.setPlatformName(rs.getString("platformName"));
						result.setTargetName(rs.getString("targetName"));
						
						return result;
					}
				});

		logger.info("listTestResultsBySUBExecutionId method result:"
				+ testResultList.size());
		return testResultList;
	}
	
	public int queryTestResultSize(Map<String, String> filterMap)
			throws Exception {

		String sql = "select count(*) from testresult ";

		sql = filterTestResultCondition(sql, filterMap);
		logger.info("queryTestResultSize method sql:" + sql);
		int res = userJdbcTemplate.queryForInt(sql);
		logger.info("queryTestResultSize method result:" + res);
		return res;
	}

	public TestResult queryTestResult(int resultId) throws Exception {

		String sql = "select * from testresult where resultid ='" + resultId
				+ "'";
		logger.info("queryTestResult method sql:" + sql);
		final TestResult testResult = new TestResult();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapTestResultFromRs(testResult, rs);
			}
		});
		//logger.info("queryTestResult method result:" + testResult.logInfo());
		return testResult;
	}	
	
	public ResultType queryResultTypeByName(String Name) throws Exception {
		String sql = "select * from resulttype where resulttypename ='" + Name
				+ "'";
		logger.info("queryResultTypeByName method sql:" + sql);
		final ResultType resultType = new ResultType();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				resultType.setResultTypeName(rs.getString("resulttypename"));
				resultType.setResultTypeId(rs.getInt("resulttypeid"));
			}
		});
		//logger.info("queryTestResult method result:" + testResult.logInfo());
		return resultType;
	}	
	
	public List< TestResult> queryTestResults(int testUnitId) throws Exception {

		String sql = "select * from testresult where testUnitid ='" + testUnitId
				+ "'";
		logger.info("queryTestResults method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 mapTestResultFromRs(result, rs);
				
				return result;
			}
		});
		return resultList;
	}	
	
	public int querySubExecutionCaseSize(Map<String, String> filterMap)
			throws Exception {
		String subExecutionId = filterMap.get("subExecutionId");
		String sql = "select count(*) from testresult where subexecutionid = '"+ subExecutionId + "'";
		logger.info("querySubExecutionCaseSize method sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("querySubExecutionCaseSize method result:" + result);
		return result;
	}
	
	public List<TestResult> listSubExecutionCaseByPage(PageBean pageBean,
			TestResult testresult) throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, b.RESULTTYPENAME, c.TARGETNAME, d.PLATFORMNAME, e.OSNAME from testresult a left join resulttype b "
				+ "on b.RESULTTYPEID = a.RESULTTYPEID left join target c on c.TARGETID=a.TARGETID left join platform d on d.PLATFORMID=a.PLATFORMID "
				+ "left join executionos e on e.OSID=a.OSID where subexecutionid = '" + testresult.getSubExecutionId() + "' ");
		sql.append("ORDER BY  MODIFYDATE DESC");
		sql.append(" limit " + itemFrom + "," + count);
		logger.info("querySubExecutionCaseByPage method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestResult> testResultList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestResult testResult = new TestResult();
						testResult.setResultId(rs.getInt("resultid"));
						testResult.setTestPlanId(rs.getInt("testplanid"));
						testResult.setSubPlanId(rs.getInt("subplanid"));		
						testResult.setTestCaseId(rs.getInt("testcaseid"));
						testResult.setTestCaseName(rs.getString("testcasename"));
						testResult.setResultTypeId(rs.getInt("resulttypeid"));
						testResult.setLog(rs.getString("log"));
						testResult.setBugId(rs.getString("bugid"));
						testResult.setComments(rs.getString("comments"));
						testResult.setModifyDate(rs.getString("modifydate"));
						testResult.setExecutionId(rs.getInt("executionId"));
						testResult.setSubExecutionId(rs.getInt("subExecutionId"));
						testResult.setResultTypeName(rs.getString("resultTypeName"));
						testResult.setTargetName(rs.getString("targetName"));
						testResult.setPlatformName(rs.getString("platformName"));
						testResult.setOsName(rs.getString("osName"));
						testResult.setBugName(rs.getString("bugName"));
						return testResult;
					}
				});
		return testResultList;
	}
	
	public List< TestResult> listTestResultByPlanId(int id) throws Exception {

		String sql = "select b.TESTCASENAME from testresult a left join testcase b on a.TESTCASEID=b.TESTCASEID where TESTPLANID = '"+id+"'";
		logger.info("listTestResultByPlanId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setTestCaseName(rs.getString("testCaseName"));
				return result;
			}
		});
		return resultList;
	}
	
	public List<TestResult> listFailedCaseInExecution(int id) throws Exception {

		String sql = "select a.*, b.RESULTTYPENAME, c.TARGETNAME, d.PLATFORMNAME, e.OSNAME, f.SUBEXECUTIONNAME from testresult a left join resulttype b"
				+ " on b.RESULTTYPEID = a.RESULTTYPEID left join target c on c.TARGETID=a.TARGETID left join platform d on d.PLATFORMID=a.PLATFORMID"
				+ " left join executionos e on e.OSID=a.OSID left join subexecution f on f.SUBEXECUTIONID=a.SUBEXECUTIONID "
				+ "where a.EXECUTIONID = '"+id+"' and a.resulttypeid = '2'";
		logger.info("listFailedCaseInExecution method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestResult> testResultList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestResult testResult = new TestResult();
						testResult.setResultId(rs.getInt("resultid"));
						testResult.setTestPlanId(rs.getInt("testplanid"));
						testResult.setSubPlanId(rs.getInt("subplanid"));		
						testResult.setTestCaseId(rs.getInt("testcaseid"));
						testResult.setTestCaseName(rs.getString("testcasename"));
						testResult.setResultTypeId(rs.getInt("resulttypeid"));
						testResult.setLog(rs.getString("log"));
						testResult.setBugId(rs.getString("bugid"));
						testResult.setComments(rs.getString("comments"));
						testResult.setModifyDate(rs.getString("modifydate"));
						testResult.setExecutionId(rs.getInt("executionId"));
						testResult.setSubExecutionId(rs.getInt("subExecutionId"));
						testResult.setResultTypeName(rs.getString("resultTypeName"));
						testResult.setTargetName(rs.getString("targetName"));
						testResult.setPlatformName(rs.getString("platformName"));
						testResult.setOsName(rs.getString("osName"));
						testResult.setSubExecutionName(rs.getString("subExecutionName"));
						testResult.setBugName(rs.getString("bugName"));
						return testResult;
					}
				});
		return testResultList;
	}	
	
	public List< TestResult> listTestUnitsBySubExecutionId(int id) throws Exception {

		String sql = "select a.*,b.SUBEXECUTIONID,c.TARGETNAME, d.TESTSUITENAME from testunit a left join subexecution b "
				+ "on a.SUBPLANID=b.SUBPLANID left join target c on a.TARGETID=c.TARGETID left join testsuite d "
				+ "on a.TESTSUITEID=d.TESTSUITEID where b.subexecutionid = '"+id+"' ";
		logger.info("listTestUnitsBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setSubExecutionId(rs.getInt("subExecutionId"));
				 result.setSubPlanId(rs.getInt("subPlanId"));
				 result.setTestunitId(rs.getInt("testunitId"));
				 result.setTargetName(rs.getString("targetName"));
				 result.setSuiteName(rs.getString("testSuiteName"));
				
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listCaseExecutionFlow(int id) throws Exception {

		String sql = "select count(*) as resultNum, substring(ModifyDate,1,10) as executeDay from testresult where ResultTypeId <> 1 and resulttypeid is not null "
				+ "and EXECUTIONID = '"+id+"' group by executeDay order by modifydate";
		logger.info("listCaseExecutionFlow method sql: " + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setResultNum(rs.getInt("resultNum"));
				 result.setExecuteDay(rs.getString("executeDay"));
				
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listTestResultBySubExecutionId(int id) throws Exception {

		String sql = "select c.TESTCASENAME,c.TESTCASEALIASID, c.timeout, c.CONFIGFILES, c.TESTFUNCTIONCALL, c.TESTSCRIPT , i.AUTONAME , j.TESTTYPENAME,a.* ,"
				+ " b.RESULTTYPENAME, d.TARGETNAME, e.PLATFORMNAME, f.OSNAME, g.executionname, h.projectname, c.EXECUTIONSTEPS, c.EXPECTEDRESULT, "
				+ "c.DESCRIPTION, c.CONFIGFILES, c.TESTSCRIPT from testresult a left join resulttype b on b.RESULTTYPEID = a.RESULTTYPEID "
				+ "left join testcase c on a.TESTCASEID=c.TESTCASEID left join target d on d.TARGETID=a.TARGETID left join platform e on "
				+ "e.PLATFORMID=a.PLATFORMID left join executionos f on a.OSID=f.OSID left join testexecution g on g.Id=a.EXECUTIONID "
				+ "left join project h on h.PROJECTID=g.PROJECTID left join automation i on i.autoid=c.AUTOID left join testtype j on "
				+ "j.TESTTYPEID=c.TESTTYPEID where subexecutionId ='"+id+"'";
		logger.info("listTestResultBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 mapTestResultSelectedFromRs(result, rs);
				 result.setTimeout(rs.getInt("timeout"));
				 result.setConfigFiles(rs.getString("configFiles"));
				 result.setTestfunctionCall(rs.getString("testfunctionCall"));
				 result.setTestScript(rs.getString("testScript"));
				 result.setAutoName(rs.getString("autoName"));
				 result.setTestTypeName(rs.getString("testTypeName"));
				 result.setTestCasealiasId(rs.getString("testCasealiasId"));
				 result.setTargetName(rs.getString("targetName"));
				 result.setPlatformName(rs.getString("platformName"));
				 result.setOsName(rs.getString("osName"));
				 result.setExecutionSteps(rs.getString("executionSteps"));
				 result.setExpectedResult(rs.getString("expectedResult"));
				 result.setBugName(rs.getString("bugName"));
				 result.setResultTrackId(rs.getInt("resultTrackId"));
				
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listTestResultBySubExecutionIdAndUnitId(int subId, int unitId) throws Exception {

		String sql = "select c.TESTCASENAME,c.TESTCASEALIASID, c.timeout, c.CONFIGFILES, c.TESTFUNCTIONCALL, c.TESTSCRIPT , i.AUTONAME , j.TESTTYPENAME,a.* ,"
				+ " b.RESULTTYPENAME, d.TARGETNAME, e.PLATFORMNAME, f.OSNAME, g.executionname, h.projectname, c.EXECUTIONSTEPS, c.EXPECTEDRESULT, "
				+ "c.DESCRIPTION, c.CONFIGFILES, c.TESTSCRIPT from testresult a left join resulttype b on b.RESULTTYPEID = a.RESULTTYPEID "
				+ "left join testcase c on a.TESTCASEID=c.TESTCASEID left join target d on d.TARGETID=a.TARGETID left join platform e on "
				+ "e.PLATFORMID=a.PLATFORMID left join executionos f on a.OSID=f.OSID left join testexecution g on g.Id=a.EXECUTIONID "
				+ "left join project h on h.PROJECTID=g.PROJECTID left join automation i on i.autoid=c.AUTOID left join testtype j on "
				+ "j.TESTTYPEID=c.TESTTYPEID where a.testunitid='"+unitId+"' and a.subexecutionId ='"+subId+"'";
		logger.info("listTestResultBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 mapTestResultSelectedFromRs(result, rs);
				 result.setTimeout(rs.getInt("timeout"));
				 result.setConfigFiles(rs.getString("configFiles"));
				 result.setTestfunctionCall(rs.getString("testfunctionCall"));
				 result.setTestScript(rs.getString("testScript"));
				 result.setAutoName(rs.getString("autoName"));
				 result.setTestTypeName(rs.getString("testTypeName"));
				 result.setTestCasealiasId(rs.getString("testCasealiasId"));
				
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listTestResultByTestExecutionId(int id) throws Exception {

		String sql = "select c.TESTCASENAME, a.* , b.RESULTTYPENAME, d.TARGETNAME, e.PLATFORMNAME, f.OSNAME, g.executionname,"
				+ " h.projectname, c.EXECUTIONSTEPS, c.EXPECTEDRESULT, c.DESCRIPTION, c.CONFIGFILES, c.TESTSCRIPT from testresult a "
				+ "left join resulttype b on b.RESULTTYPEID = a.RESULTTYPEID left join testcase c on a.TESTCASEID=c.TESTCASEID left join "
				+ "target d on d.TARGETID=a.TARGETID left join platform e on e.PLATFORMID=a.PLATFORMID left join executionos f "
				+ "on a.OSID=f.OSID left join testexecution g on g.Id=a.EXECUTIONID left join project h on h.PROJECTID=g.PROJECTID "
				+ "where executionId ='"+id+"'";
		logger.info("listTestResultByTestExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {

			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 mapTestResultSelectedFromRs(result, rs);
				
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listTestResultCountBySubExecutionId(int subExecutionId) throws Exception {

		String sql = "SELECT a.executionid, b.RESULTTYPENAME, a.RESULTTYPEID, count(*) as resultNum "
				+ "FROM testresult a left join resulttype b on a.RESULTTYPEID = b.RESULTTYPEID"
				+ " where subexecutionid = '"+ subExecutionId +"' GROUP BY RESULTTYPEID";
		logger.info("listTestResultCountBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setExecutionId(rs.getInt("executionid"));
				 result.setResultTypeName(rs.getString("resulttypename"));
				 result.setResultTypeId(rs.getInt("resulttypeid"));
				 result.setResultNum(rs.getInt("resultNum"));
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listTestResultCountByExecutionId(int executionId) throws Exception {

		String sql = "SELECT a.executionid, b.RESULTTYPENAME, a.RESULTTYPEID, count(*) as resultNum "
				+ "FROM testresult a left join resulttype b on a.RESULTTYPEID = b.RESULTTYPEID"
				+ " where executionid = '"+ executionId +"' GROUP BY RESULTTYPEID";
		logger.info("listTestResultCountBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setExecutionId(rs.getInt("executionid"));
				 result.setResultTypeName(rs.getString("resulttypename"));
				 result.setResultTypeId(rs.getInt("resulttypeid"));
				 result.setResultNum(rs.getInt("resultNum"));
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listPerformanceResultByExecutionId(int id) throws Exception {

		String sql = "select c.TESTCASENAME,d.SUBEXECUTIONNAME, e.RESULTTYPENAME,a.comments,a.bugid,a.LOG, b.* from testresult a left join "
				+ "performanceresult b on a.ResultTrackId=b.ResultTrackId left join testcase c on a.TESTCASEID=c.testcaseid left join "
				+ "subexecution d on a.SUBEXECUTIONID=d.SUBEXECUTIONID left join resulttype e on e.RESULTTYPEID=a.RESULTTYPEID "
				+ "where a.executionid = '"+id+"' and a.ResultTrackId is not null and b.ResultTrackId is not null  order by b.testresultid, b.elementindex";
		logger.info("listPerformanceResultByExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setResultTypeName(rs.getString("resulttypename"));
				 result.setResultId(rs.getInt("testResultId"));
				 result.setTestCaseName(rs.getString("testCaseName"));
				 result.setResultTrackId(rs.getInt("resultTrackId"));
				 result.setSubExecutionName(rs.getString("subExecutionName"));
				 result.setLog(rs.getString("log"));
				 result.setComments(rs.getString("comments"));
				 result.setBugId(rs.getString("bugId"));
				 result.setElementIndex(rs.getInt("elementIndex"));
				 result.setAttributeValue(rs.getString("attributeValue"));
				 result.setAttributeName(rs.getString("attributeName"));
				return result;
			}
		});
		return resultList;
	}
	
	public List< TestResult> listPerformanceResultBySubExecutionId(int id) throws Exception {

		String sql = "select c.TESTCASENAME,d.SUBEXECUTIONNAME, e.RESULTTYPENAME,a.comments,a.bugid,a.LOG, b.* from testresult a left join "
				+ "performanceresult b on a.ResultTrackId=b.ResultTrackId left join testcase c on a.TESTCASEID=c.testcaseid left join "
				+ "subexecution d on a.SUBEXECUTIONID=d.SUBEXECUTIONID left join resulttype e on e.RESULTTYPEID=a.RESULTTYPEID "
				+ "where a.subexecutionid = '"+id+"' and a.ResultTrackId is not null and b.ResultTrackId is not null  order by b.testresultid, b.elementindex";
		logger.info("listPerformanceResultBySubExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setResultTypeName(rs.getString("resulttypename"));
				 result.setResultId(rs.getInt("testResultId"));
				 result.setTestCaseName(rs.getString("testCaseName"));
				 result.setResultTrackId(rs.getInt("resultTrackId"));
				 result.setSubExecutionName(rs.getString("subExecutionName"));
				 result.setLog(rs.getString("log"));
				 result.setComments(rs.getString("comments"));
				 result.setBugId(rs.getString("bugId"));
				 result.setElementIndex(rs.getInt("elementIndex"));
				 result.setAttributeValue(rs.getString("attributeValue"));
				 result.setAttributeName(rs.getString("attributeName"));
				return result;
			}
		});
		return resultList;
	}
	
	public int addBatchTestResult(final List<TestCase> testcaselists)
			throws Exception {

		String sql = "INSERT INTO `testresult` (`TESTUNITID`, `TESTPLANID`, `SUBPLANID`, `TESTCASEID`, `TESTCASENAME`, `MODIFYDATE`, `TARGETID`,`SUBEXECUTIONID`,`EXECUTIONID`,`PLATFORMID`,`OSID`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?);";
		logger.info("addBatchTestResult method sql:" + sql);

		int result[] = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {  
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TestCase subtestplan = testcaselists.get(i);
				ps.setInt(1, subtestplan.getTestUnitId());
				ps.setInt(2, subtestplan.getTestPlanId());
				ps.setInt(3, subtestplan.getSubPlanId());
				ps.setInt(4, subtestplan.getTestCaseId());
				ps.setString(5, subtestplan.getTestCaseName());
				ps.setString(6, subtestplan.getModifyDate());
				ps.setInt(7, subtestplan.getTargetId() );
				ps.setInt(8, subtestplan.getSubExecutionId() );
				ps.setInt(9, subtestplan.getExecutionId() );
				ps.setInt(10, subtestplan.getPlatformId() );
				ps.setInt(11, subtestplan.getOsId() );
			}
					 
			@Override
			public int getBatchSize() {
				return testcaselists.size();
			}
		});				
		logger.info("delBatchSubTestPlan method result:" + result.length);
		return result.length;
	}
	
	public int addBatchTestResults(final List<TestResult> testcaselists)
			throws Exception {

		String sql = "INSERT INTO `testresult` (`TESTUNITID`, `TESTPLANID`, `SUBPLANID`, `TESTCASEID`, `TESTCASENAME`, `MODIFYDATE`, `TARGETID`,`SUBEXECUTIONID`,`EXECUTIONID`,`PLATFORMID`,`OSID`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?);";
		logger.info("addBatchTestResult method sql:" + sql);

		int result[] = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {  
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TestResult subtestplan = testcaselists.get(i);
				ps.setInt(1, subtestplan.getTestunitId());
				ps.setInt(2, subtestplan.getTestPlanId());
				ps.setInt(3, subtestplan.getSubPlanId());
				ps.setInt(4, subtestplan.getTestCaseId());
				ps.setString(5, subtestplan.getTestCaseName());
				ps.setString(6, subtestplan.getModifyDate());
				ps.setInt(7, subtestplan.getTargetId() );
				ps.setInt(8, subtestplan.getSubExecutionId() );
				ps.setInt(9, subtestplan.getExecutionId() );
				ps.setInt(10, subtestplan.getPlatformId() );
				ps.setInt(11, subtestplan.getOsId() );
			}
					 
			@Override
			public int getBatchSize() {
				return testcaselists.size();
			}
		});				
		logger.info("delBatchSubTestPlan method result:" + result.length);
		return result.length;
	}
	
	public int deleteBatchTestResultFromSuite(final List<TestCase> testcaselists)
			throws Exception {

		String sql = "delete from testresult where subplanid=? and subexecutionid = ? and testunitid = ? and platformid = ? and osid= ? and targetid = ? ";
		logger.info("deleteBatchTestResult method sql:" + sql);

		int result[] = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {  
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TestCase subtestplan = testcaselists.get(i);
				ps.setInt(1, subtestplan.getSubPlanId());
				ps.setInt(2, subtestplan.getSubExecutionId());
				ps.setInt(3, subtestplan.getTestUnitId());
				ps.setInt(4, subtestplan.getPlatformId());
				ps.setInt(5, subtestplan.getOsId());
				ps.setInt(6, subtestplan.getTargetId());
			}
					 
			@Override
			public int getBatchSize() {
				return testcaselists.size();
			}
		});				
		logger.info("deleteBatchTestResult method result:" + result.length);
		return result.length;
	}
	
	public int deleteTestResultFromSuite(final TestCase tcase)
			throws Exception {

		String sql = "delete from testresult where subplanid='"+tcase.getSubPlanId()+"' and subexecutionid = '"+tcase.getSubExecutionId()+"' "
				+ "and testunitid = '"+tcase.getTestUnitId()+"' and platformid = '"+tcase.getPlatformId()+"' "
				+ "and osid= '"+tcase.getOsId()+"' and targetid = '"+tcase.getTargetId()+"' and testcaseid = '"+tcase.getTestCaseId()+"'";
		logger.info("deleteTestResultFromSuite method sql:" + sql);

		int result = userJdbcTemplate.update(sql);
		logger.info("deleteTestResultFromSuite total records :" + result);
		return result;

	}
	
	public int deleteTestResultFromUnit(final TestCase tcase)
			throws Exception {

		String sql = "delete from testresult where subplanid='"+tcase.getSubPlanId()+"' and subexecutionid = '"+tcase.getSubExecutionId()+"' "
				+ "and testunitid = '"+tcase.getTestUnitId()+"' and platformid = '"+tcase.getPlatformId()+"' "
				+ "and osid= '"+tcase.getOsId()+"' and targetid = '"+tcase.getTargetId()+"'";
		logger.info("deleteTestResultFromUnit method sql:" + sql);

		int result = userJdbcTemplate.update(sql);
		logger.info("deleteTestResultFromUnit total records :" + result);
		return result;

	}
	
	public List<ResultType> listResultTypes() throws Exception {

		String sql = "select * from resulttype";
		logger.info("listResultTypes method sql  :" + sql);
		@SuppressWarnings("unchecked")
		List<ResultType> resultTypeList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						ResultType resultType = new ResultType();
						resultType.setResultTypeId(rs.getInt("resulttypeid"));
						resultType.setResultTypeName(rs
								.getString("resulttypename"));

						return resultType;
					}
				});

		logger.info("listResultTypes method result:" + resultTypeList.size());
		return resultTypeList;
	}

	public String filterTestResultCondition(String sql,
			Map<String, String> filterMap) {

		sql = sql + " %s %s ";

		//String testSuiteId = filterMap.get("testSuiteId");
		String resultTypeId = filterMap.get("resultTypeId");
		String testUnitId = filterMap.get("testUnitId");
		boolean flag = false;// "where" keywords already added or not

	

		if (Utils.isNullORWhiteSpace(resultTypeId)
				|| resultTypeId.trim().equals("-1")
				|| resultTypeId.trim().equals("0")) {

			sql = String.format(sql, "", "%s");

		} else {

			if (flag) {

				sql = String.format(sql, " and testresult.resulttypeid="
						+ resultTypeId, "%s");

			} else {
				sql = String.format(sql, "  where testresult.resulttypeid="
						+ resultTypeId, "%s");
				flag = true;
			}

		}

		if (Utils.isNullORWhiteSpace(testUnitId)
				|| testUnitId.trim().equals("-1")
				|| testUnitId.trim().equals("0")) {

			sql = String.format(sql, "");

		} else {

			if (flag) {

				sql = String.format(sql, " and testresult.testunitid="
						+ testUnitId);

			} else {
				sql = String.format(sql, "  where testresult.testunitid="
						+ testUnitId);
				// flag = true;
			}

		}

		return sql;

	}

	
	
	
	
	public static void mapTestResultFromRs(final TestResult testResult,
			ResultSet rs) throws SQLException {
		testResult.setResultId(rs.getInt("resultid"));
		testResult.setTestPlanId(rs.getInt("testplanid"));
		testResult.setSubPlanId(rs.getInt("subplanid"));
		testResult.setResultTrackId(rs.getInt("resultTrackId"));
		testResult.setTestCaseId(rs.getInt("testcaseid"));
		testResult.setTestCaseName(rs.getString("testcasename"));
		testResult.setResultTypeId(rs.getInt("resulttypeid"));
		testResult.setLog(rs.getString("log"));
		testResult.setBugId(rs.getString("bugid"));
		testResult.setBugName(rs.getString("bugName"));
		testResult.setComments(rs.getString("comments"));
		testResult.setModifyDate(rs.getString("modifydate"));
		testResult.setExecutionId(rs.getInt("executionId"));
		testResult.setSubExecutionId(rs.getInt("subExecutionId"));
	}

	public static void mapTestResultSelectedFromRs(final TestResult testResult,
			ResultSet rs) throws SQLException {
		testResult.setResultId(rs.getInt("resultid"));
		testResult.setTestCaseName(rs.getString("testcasename"));
		testResult.setResultTypeId(rs.getInt("resulttypeid"));
		testResult.setResultTypeName(rs.getString("resultTypeName"));
		testResult.setLog(rs.getString("log"));
		testResult.setBugId(rs.getString("bugid"));
		testResult.setComments(rs.getString("comments"));
		testResult.setTestPlanId(rs.getInt("testplanid"));
		testResult.setSubPlanId(rs.getInt("subplanid"));
		testResult.setTestCaseId(rs.getInt("testcaseid"));
		testResult.setTargetId(rs.getInt("targetid"));
		testResult.setExecutionId(rs.getInt("executionId"));
		testResult.setPlatformId(rs.getInt("platformid"));
		testResult.setOsId(rs.getInt("osid"));
		testResult.setTestunitId(rs.getInt("testunitid"));
		testResult.setTargetName(rs.getString("targetName"));
		testResult.setPlatformName(rs.getString("platformName"));
		testResult.setOsName(rs.getString("osName"));
		testResult.setDescription(rs.getString("description"));
		testResult.setExecutionSteps(rs.getString("executionSteps"));
		testResult.setExpectedResult(rs.getString("expectedResult"));
		testResult.setProjectName(rs.getString("projectName"));
		testResult.setExecutionName(rs.getString("executionName"));
	}
	
	public static void mapUpdateTestResultToPs(PreparedStatement ps,
			TestResult testResult) throws SQLException {

		if (testResult.getResultTypeId() == 0) {

			ps.setNull(1, Types.NULL);

		} else {
			ps.setInt(1, testResult.getResultTypeId());
		}
		if (Utils.isNullORWhiteSpace(testResult.getLog())) {

			ps.setNull(2, Types.NULL);

		} else {
			ps.setString(2, testResult.getLog());
		}
		if (Utils.isNullORWhiteSpace(testResult.getBugId())) {

			ps.setNull(3, Types.NULL);

		} else {
			ps.setString(3, testResult.getBugId());
		}

		if (Utils.isNullORWhiteSpace(testResult.getComments())) {

			ps.setNull(4, Types.NULL);

		} else {
			ps.setString(4, testResult.getComments());
		}

		if (Utils.isNullORWhiteSpace(testResult.getModifyDate())) {

			ps.setNull(5, Types.NULL);

		} else {
			ps.setString(5, testResult.getModifyDate());
		}
		
		if (Utils.isNullORWhiteSpace(testResult.getBugName())) {

			ps.setNull(6, Types.NULL);

		} else {
			ps.setString(6, testResult.getBugName());
		}
		
		if (Utils.isNullORWhiteSpace(testResult.getResultForExcel())) {

			ps.setNull(7, Types.NULL);

		} else {
			ps.setString(7, testResult.getResultForExcel());
		}

	}
	
	public static void mapTestResultToPs(PreparedStatement ps,
			TestResult testResult) throws SQLException {


		if (testResult.getTestPlanId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, testResult.getTestPlanId());
		}
		if (testResult.getSubPlanId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testResult.getSubPlanId());
		}
		
		if (testResult.getTestCaseId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testResult.getTestCaseId());
		}

		if (Utils.isNullORWhiteSpace(testResult.getTestCaseName())) {

			ps.setNull(4, Types.NULL);

		} else {
			ps.setString(4, testResult.getTestCaseName());
		}

		if (testResult.getResultTypeId() == 0) {

			ps.setNull(5, Types.NULL);

		} else {
			ps.setInt(5, testResult.getResultTypeId());
		}

		if (Utils.isNullORWhiteSpace(testResult.getLog())) {

			ps.setNull(6, Types.NULL);

		} else {
			ps.setString(6, testResult.getLog());
		}
		if (Utils.isNullORWhiteSpace(testResult.getBugId())) {

			ps.setNull(7, Types.NULL);

		} else {
			ps.setString(7, testResult.getBugId());
		}

		if (Utils.isNullORWhiteSpace(testResult.getComments())) {

			ps.setNull(8, Types.NULL);

		} else {
			ps.setString(8, testResult.getComments());
		}

		if (Utils.isNullORWhiteSpace(testResult.getModifyDate())) {

			ps.setNull(9, Types.NULL);

		} else {
			ps.setString(9, testResult.getModifyDate());
		}

	}

/**
 * ===============================================SQL Operation for table performanceResult==========================================================
 * @return
 */

	public int addBatchPerformanceResult(final List<PerformanceResult> lists)
			throws Exception {

		String sql = "INSERT INTO `performanceresult` (`ResultTrackId`, `TestResultId`, `ElementIndex`, `AttributeName`, `AttributeValue`, `CreateDate`,`ModifyDate`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?,?);";
		logger.info("addBatchPerformanceResult method sql:" + sql);

		int result[] = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {  
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PerformanceResult list = lists.get(i);
				ps.setInt(1, list.getResultTrackId());
				ps.setInt(2, list.getTestResultId());
				ps.setInt(3, list.getElementIndex());
				ps.setString(4, list.getAttributeName());
				ps.setString(5, list.getAttributeValue());
				ps.setString(6, list.getCreateDate());
				ps.setString(7, list.getModifyDate());
			}
					 
			@Override
			public int getBatchSize() {
				return lists.size();
			}
		});				
		logger.info("addBatchPerformanceResult method result:" + result.length);
		return result.length;
	}	
	
	public int addPerformanceResult(final PerformanceResult list) throws Exception {

		String sql = "INSERT INTO `performanceresult` (`ResultTrackId`, `TestResultId`, `ElementIndex`, `AttributeName`, `AttributeValue`, `CreateDate`,`ModifyDate`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		logger.info("addPerformanceResult method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, list.getResultTrackId());
						ps.setInt(2, list.getTestResultId());
						ps.setInt(3, list.getElementIndex());
						ps.setString(4, list.getAttributeName());
						ps.setString(5, list.getAttributeValue());
						ps.setString(6, list.getCreateDate());
						ps.setString(7, list.getModifyDate());
					}
				});
		logger.info("addPerformanceResult method result:" + result);
		return result;

	}

/**
 * This sql will get performance result like below format:
	'Item 1 -> Cores : 1S/1C/2T ; FrameSize : 84 ; Linerate : 99.998 ; Mpps : 12.019 ; RXPORTS : 1'
	'Item 2 -> Cores : 1S/2C/1T ; FrameSize : 84 ; Linerate : 99.998 ; Mpps : 12.019 ; RXPORTS : 1'
	'Item 3 -> Cores : 1S/4C/1T ; FrameSize : 84 ; Linerate : 99.998 ; Mpps : 24.038 ; RXPORTS : 2'
	'Item 4 -> Cores : 1S/2C/2T ; FrameSize : 84 ; Linerate : 99.998 ; Mpps : 24.038 ; RXPORTS : 2'
	'Item 5 -> Cores : 1S/3C/2T ; FrameSize : 84 ; Linerate : 99.998 ; Mpps : 24.038 ; RXPORTS : 2'	
 * @param id
 * @return
 * @throws Exception
 */
	
	public List< PerformanceResult > queryPerfResultByResultTrackId(int id) throws Exception {

		String sql = "SELECT a.ResultTrackId , a.TestResultId, CONCAT( 'Item ', a.ElementIndex, ' -> ' , "
				+ "GROUP_CONCAT( CONCAT( a.AttributeName, ' : ' , a.AttributeValue ) SEPARATOR ' ; '))  "
				+ "as Performance FROM performanceresult a where ResultTrackId = '"+id+"' group by a.elementindex ;";
		logger.info("queryPerfResultByResultTrackId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< PerformanceResult > lists = userJdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				PerformanceResult list = new  PerformanceResult();
				 list.setResultTrackId(rs.getInt("resultTrackId"));
				 list.setTestResultId(rs.getInt("testResultId"));
				 list.setPerformance(rs.getString("performance"));
				 
				return list;
			}
		});
		return lists;
	}
	
	public List< PerformanceResult > queryPerfResult2ByResultTrackId(int id) throws Exception {

		String sql = "SELECT a.ElementIndex, a.ResultTrackId , a.TestResultId, GROUP_CONCAT( CONCAT( a.AttributeName, ' : ' , a.AttributeValue ) SEPARATOR ' ; ') "
				+ "as Performance FROM performanceresult a where ResultTrackId = '"+id+"' group by a.elementindex ;";
		logger.info("queryPerfResult2ByResultTrackId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< PerformanceResult > lists = userJdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				PerformanceResult list = new  PerformanceResult();
				 list.setResultTrackId(rs.getInt("resultTrackId"));
				 list.setTestResultId(rs.getInt("testResultId"));
				 list.setPerformance(rs.getString("performance"));
				 list.setElementIndex(rs.getInt("elementIndex"));
				 
				return list;
			}
		});
		return lists;
	}	
/**
 * ===============================================SQL Operation for table resultTrack==========================================================
* @return
*/

	public int updateResultTrackById(final ResultTrack resultTrack)
			throws Exception {
		String sql = "update resulttrack set modifyDate='"+resultTrack.getModifyDate()+"' where resultTrackId='"+resultTrack.getResultTrackId()+"'";
		logger.info("updateResultTrackById method sql:" + sql);
	    int result = userJdbcTemplate.update(sql);
	    logger.info("result:" + result);
	    return result;
	}
	
	public ResultTrack queryResultTrackById(int id) throws Exception {

		String sql = "select * from resulttrack where resultTrackId ='" + id + "'";
		logger.info("queryResultTrack method sql:" + sql);
		final ResultTrack resultTrack = new ResultTrack();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				resultTrack.setResultTrackId(rs.getInt("resultTrackId"));
				resultTrack.setTestResultId(rs.getInt("testResultId"));
				resultTrack.setResultTypeId(rs.getInt("resultTypeId"));
				resultTrack.setDisplay(rs.getInt("display"));
				resultTrack.setComment(rs.getString("comment"));
				resultTrack.setCreateDate(rs.getString("createDate"));
				resultTrack.setModifyDate(rs.getString("modifyDate"));
			}
		});
		
		return resultTrack;
	}	
	
	public List< ResultTrack > queryResultTrachkByResultId(int id) throws Exception {

		String sql = "SELECT a.*, b.RESULTTYPENAME, d.testcasename from resulttrack a left join resulttype b on a.ResultTypeId=b.RESULTTYPEID "
				+ "left join testresult c on c.RESULTID=a.TestResultId left join testcase d on d.TESTCASEID=c.TESTCASEID "
				+ "where a.TestResultId = '"+id+"' order by a.modifydate desc";
		logger.info("queryResultTrachkByResultId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List< ResultTrack > lists = userJdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 ResultTrack list = new  ResultTrack();
				 list.setResultTrackId(rs.getInt("resultTrackId"));
				 list.setTestResultId(rs.getInt("testResultId"));
				 list.setResultTypeId(rs.getInt("resultTypeId"));
				 list.setDisplay(rs.getInt("display"));
				 list.setComment(rs.getString("comment"));
				 list.setCreateDate(rs.getString("createDate"));
				 list.setModifyDate(rs.getString("modifyDate"));
				 list.setResultTypeName(rs.getString("resultTypeName"));
				 list.setTestCaseName(rs.getString("testCaseName"));
				
				return list;
			}
		});
		return lists;
	}
	
	public int addResultTrack(final ResultTrack resultTrack) throws Exception {

		final String sql = "insert into `resulttrack` (`TestResultId`, `ResultTypeId` ,`Display`, `Comment` , `CreateDate`,`ModifyDate`) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		logger.info("addResultTrack method sql:" + sql);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		userJdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setInt(1, resultTrack.getTestResultId());
				ps.setInt(2, resultTrack.getResultTypeId());
				ps.setInt(3, resultTrack.getDisplay());
				ps.setString(4, resultTrack.getComment());
				ps.setString(5, resultTrack.getCreateDate());
				ps.setString(6, resultTrack.getModifyDate());
				return ps;
			}
		}, keyHolder);
		int result = keyHolder.getKey().intValue();
		logger.info("addResultTrack method result:" + result);
		return result;
	}
	
	public int addBatchResultTrack(final List<ResultTrack> lists)
			throws Exception {

		String sql = "insert into `resulttrack` (`TestResultId`, `ResultTypeId` ,`Display`, `Comment` , `CreateDate`,`ModifyDate`) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		logger.info("addBatchResultTrack method sql:" + sql);

		int result[] = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {  
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ResultTrack list = lists.get(i);
				ps.setInt(1, list.getTestResultId());
				ps.setInt(2, list.getResultTypeId());
				ps.setInt(3, list.getDisplay());
				ps.setString(4, list.getComment());
				ps.setString(5, list.getCreateDate());
				ps.setString(6, list.getModifyDate());
			}
					 
			@Override
			public int getBatchSize() {
				return lists.size();
			}
		});				
		logger.info("addBatchResultTrack method result:" + result.length);
		return result.length;
	}
	
/**
 * ========================================Common function======================================================	
 * @return
 */
	
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
