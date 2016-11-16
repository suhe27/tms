package com.intel.cid.common.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.apache.commons.lang.StringEscapeUtils;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.dao.TestExecutionDao;
import com.intel.cid.utils.Utils;

import tjpu.page.bean.PageBean;

public class TestExecutionDaoImpl implements TestExecutionDao {
	private static Logger logger = Logger.getLogger(TestExecutionDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public List<TestExecution> listTestExecutionByPage(PageBean pageBean,
			TestExecution testexecution) throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from testexecution t where projectid in( SELECT a.projectid FROM project a, user_team b where a.teamid=b.teamid and b.userid='"
			//	+ testexecution.getUserId() + "')");
		sql.append("select a.*,b.PLANNAME,c.PROJECTNAME,d.USERNAME,e.PHASENAME,f.BUILDTYPE from testexecution a left join testplan b on "
				+ "b.TESTPLANID = a.TESTPLANID left join project c on c.PROJECTID=a.PROJECTID left join user d on d.USERID=a.OWNNER"
				+ " left join executionphase e on e.PHASEID=a.PHASEID left join build f on f.buildid=a.BUILDID where EXISTS ("
				+ "SELECT * from user_team as map left join project pro on pro.teamid=map.TEAMID WHERE (map.status <> 1 or map.status is null)"
				+ " and pro.projectid = a.projectid and map.USERID='"+testexecution.getUserId()+"')");

		if (testexecution != null) {
			sql.append(queryTextuctionSql(testexecution));
		}
		sql.append("ORDER BY  CREATEDATE DESC");
		sql.append(" limit " + itemFrom + "," + count);
		logger.info("listTestExecutionsByPage method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestExecution> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestExecution testexecution = new TestExecution();
						mapFullExecutionResultFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return TestexecutionList;
	}
	
/**
 * num is for return number	
 * @param testexecution
 * @param num
 * @return
 * @throws Exception
 */
	
	public List<TestExecution> listTestExecutions(TestExecution testexecution, int num) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.PLANNAME,c.PROJECTNAME,d.USERNAME,e.PHASENAME,f.BUILDTYPE from testexecution a left join testplan b on "
				+ "b.TESTPLANID = a.TESTPLANID left join project c on c.PROJECTID=a.PROJECTID left join user d on d.USERID=a.OWNNER"
				+ " left join executionphase e on e.PHASEID=a.PHASEID left join build f on f.buildid=a.BUILDID where EXISTS ("
				+ "SELECT * from user_team as map left join project pro on pro.teamid=map.TEAMID WHERE (map.status <> 1 or map.status is null)"
				+ " and pro.projectid = a.projectid and map.USERID='"+testexecution.getUserId()+"')");

		if (testexecution != null) {
			sql.append(queryTextuctionSql(testexecution));
		}
		sql.append("ORDER BY  CREATEDATE DESC");
		sql.append(" limit "+ num);
		logger.info("listTestExecutions method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestExecution> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestExecution testexecution = new TestExecution();
						mapFullExecutionResultFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return TestexecutionList;
	}

	public List<TestExecution> listTestExecutionsByIds(String ids) throws Exception {

		String sql = "select * from testexecution where id in ("+ids+")";
		logger.info("listTestExecutionsByIds method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestExecution> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestExecution testexecution = new TestExecution();
						testexecution.setExecutionName(rs.getString("executionname"));
						testexecution.setId(rs.getInt("id"));
						return testexecution;
					}
				});
		return TestexecutionList;
	}
	
	public List<TestResult> listTestCaseInSuiteBySubPlanAndTarget(TestExecution testexecution) throws Exception {

		String sql = null;
		if(testexecution.getTargetId() != 0 && testexecution.getTargetId() != -1) {
			sql = "select n.TESTCASENAME from casesuitemap m left join testcase n on m.TESTCASEID=n.TESTCASEID "
					+ "left join testsuite a on m.TESTSUITEID=a.TESTSUITEID left join testunit b "
				+ "on a.TESTSUITEID=b.TESTSUITEID left join subtestplan c on c.SUBPLANID=b.SUBPLANID "
				+ "where (m.status <> 1 or m.status is null) and c.SUBPLANID='"+testexecution.getSubPlanId()+"' and b.targetid='"+testexecution.getTargetId()+"'";
		} else {
			sql = "select n.TESTCASENAME from casesuitemap m left join testcase n on m.TESTCASEID=n.TESTCASEID "
					+ "left join testsuite a on m.TESTSUITEID=a.TESTSUITEID left join testunit b "
					+ "on a.TESTSUITEID=b.TESTSUITEID left join subtestplan c on c.SUBPLANID=b.SUBPLANID "
					+ "where (m.status <> 1 or m.status is null) and c.SUBPLANID='"+testexecution.getSubPlanId()+"'";
		}
		logger.info("listTestCaseInSuiteBySubPlanAndTarget method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestResult> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestResult testexecution = new TestResult();
						testexecution.setTestCaseName(rs.getString("testCaseName"));
						return testexecution;
					}
				});
		return TestexecutionList;
	}

	public List<TestResult> listResultInTestExecutionsByCsaeId(TestExecution testexecution) throws Exception {

		String sql = null;
		if(testexecution.getTargetId() != 0 && testexecution.getTargetId() != -1) {
			sql = "select b.TESTCASENAME,c.EXECUTIONNAME,d.RESULTTYPENAME from testresult a left join testcase b on a.TESTCASEID=b.TESTCASEID "
					+ "left join testexecution c on a.EXECUTIONID=c.ID left join resulttype d on a.RESULTTYPEID=d.RESULTTYPEID "
					+ " where a.SUBPLANID='"+testexecution.getSubPlanId()+"' and a.PLATFORMID='"+testexecution.getPlatformId()+"' "
					+ "and a.OSID='"+testexecution.getOsId()+"' and b.TESTCASENAME='"+testexecution.getTestCaseName()+"' and a.TARGETID = '"+testexecution.getTargetId()+"'";
		} else {
			sql = "select b.TESTCASENAME,c.EXECUTIONNAME,d.RESULTTYPENAME from testresult a left join testcase b on a.TESTCASEID=b.TESTCASEID "
					+ "left join testexecution c on a.EXECUTIONID=c.ID left join resulttype d on a.RESULTTYPEID=d.RESULTTYPEID "
					+ "where a.SUBPLANID='"+testexecution.getSubPlanId()+"' and a.PLATFORMID='"+testexecution.getPlatformId()+"' "
					+ "and a.OSID='"+testexecution.getOsId()+"' and b.TESTCASENAME='"+testexecution.getTestCaseName()+"'";
		}
		logger.info("listResultInTestExecutionsByCsaeId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestResult> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestResult testexecution = new TestResult();
						testexecution.setTestCaseName(rs.getString("testCaseName"));
						testexecution.setExecutionName(rs.getString("executionName"));
						testexecution.setResultTypeName(rs.getString("resultTypeName"));
						return testexecution;
					}
				});
		return TestexecutionList;
	}
	
/**
 * Get matched test execution list by test suite id and will not showing result when execution's execute rate is 1.00. 
 * This design is mean to not influence any execution's case list and result by suite change when it's finished execution.
 * @param Id
 * @return
 * @throws Exception
 */
	
	public List<TestExecution> listTestExecutionBySuiteId(int Id) throws Exception {

		String sql = "select distinct a.id as diff,a.* from testexecution a left join testplan b on b.TESTPLANID=a.TESTPLANID "
				+ "left join testunit c on c.TESTPLANID=b.TESTPLANID left join testsuite d on d.TESTSUITEID=c.TESTSUITEID "
				+ "where d.testsuiteid='"+Id+"' and a.executerate != '1.00'";
		logger.info("listTestExecutionBySuiteId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestExecution> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestExecution testexecution = new TestExecution();
						mapTestExecutionFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return TestexecutionList;
	}
	
/**
 * 	This function is used to search related test execution when unit have changed in sub test plan. Test execution will not influenced when it's execute rate is 1.00
 * @param Id
 * @return
 * @throws Exception
 */
	public List<TestExecution> listTestExecutionBySubPlanId(int Id) throws Exception {

		String sql = "select distinct a.id as diff,a.* from testexecution a left join testplan b on a.TESTPLANID=b.TESTPLANID "
				+ "left join planmap c on c.TESTPLANID=b.TESTPLANID where (c.status <> 1 or c.status is null) and "
				+ "c.SUBPLANID = '"+Id+"' and a.executerate != '1.00'";
		logger.info("listTestExecutionBySubPlanId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestExecution> TestexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestExecution testexecution = new TestExecution();
						mapTestExecutionFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return TestexecutionList;
	}
	
	public List<SubExecution> listSubExecutionsByExecutionIdAndSuiteId(int suiteId, int executionId) throws Exception {

		String sql = "SELECT distinct a.SUBEXECUTIONID as diff, a.* FROM subexecution a left join testunit b on a.SUBPLANID=b.SUBPLANID "
				+ "left join testsuite c on b.TESTSUITEID=c.TESTSUITEID where"
				+ " c.TESTSUITEID = '"+suiteId+"' and a.executionid = '"+executionId+"'";
		logger.info("listSubExecutionsByExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<SubExecution> SubexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution subexecution = new SubExecution();
						mapSubExecutionFromRs(subexecution, rs);
						return subexecution;
					}
				});
		return SubexecutionList;
	}
	
	public List<SubExecution> listSubExecutionsByExecutionIdAndSubPlanId(int subPlanId, int executionId) throws Exception {

		String sql = "select a.* from subexecution a where a.EXECUTIONID = '"+executionId+"' and a.subplanid = '"+subPlanId+"'";
		logger.info("listSubExecutionsByExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<SubExecution> SubexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution subexecution = new SubExecution();
						mapSubExecutionFromRs(subexecution, rs);
						return subexecution;
					}
				});
		return SubexecutionList;
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
	
	public List< TestResult> listFeatureByExecutionId(int id) throws Exception {

		String sql = "select c.FEATURENAME, count(c.FEATURENAME) as Num from testresult a left join testcase b on a.TESTCASEID=b.TESTCASEID"
				+ " left join feature c on b.FEATUREID=c.FEATUREID where a.executionid = '"+id+"' group by c.featurename";
		logger.info("listFeatureByExecutionId method sql:" + sql);
		
		@SuppressWarnings("unchecked")
		List< TestResult> resultList = userJdbcTemplate.query(sql, new RowMapper() {			
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				 TestResult result = new  TestResult();
				 result.setResultNum(rs.getInt("Num"));
				 result.setFeatureName(rs.getString("featureName"));
				return result;
			}
		});
		return resultList;
	}
	
	public int refreshSubExecution(SubExecution subExecution) throws Exception {

		List< TestResult> caculateNum =listTestResultCountBySubExecutionId(subExecution.getSubExecutionId());
		int totalCase = 0;
		int executed = 0;
		boolean passStatus = false;
		boolean failStatus = false; 
		boolean blockStatus = false; 
		for(TestResult tmp : caculateNum) {
			if(tmp.getResultTypeId() == 3) {
				subExecution.setPass(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				passStatus = true; 
			}
			if(tmp.getResultTypeId() == 2) {
				subExecution.setFail(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				failStatus = true; 
			}
			if(tmp.getResultTypeId() == 4) {
				subExecution.setBlock(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				blockStatus = true; 
			}
			if(!passStatus) {subExecution.setPass(0);}
			if(!failStatus) {subExecution.setFail(0);}
			if(!blockStatus) {subExecution.setBlock(0);}
			
			totalCase = totalCase + tmp.getResultNum();
		}
			int NotRun = totalCase - executed;
			subExecution.setNotRun(NotRun);
			subExecution.setTotalCases(totalCase);
			
			if(totalCase> 0 ){
			    double operation = (double)subExecution.getPass() / (double) totalCase;
		        BigDecimal big = new BigDecimal(operation);     
		        big = big.setScale(4, RoundingMode.HALF_UP);        
		        double passRate = big.doubleValue();
		        subExecution.setPassrate(passRate);
		        
			    double operation1 = (double) executed / (double) totalCase;
		        BigDecimal big1 = new BigDecimal(operation1);     
		        big1 = big1.setScale(4, RoundingMode.HALF_UP);        
		        double executeRate = big1.doubleValue();
		        subExecution.setExecuteRate(executeRate);
			} else {
				subExecution.setPassrate(0.00);
				subExecution.setExecuteRate(0.00);
			}
			
			int result = updateSubExecution(subExecution);
			return result;
	}
	
	public int refreshTestExecution(TestExecution testExecution) throws Exception {
		
		List< TestResult> caculateNum =listTestResultCountByExecutionId(testExecution.getId());
		int totalCase = 0;
		int executed = 0;
		
		boolean passStatus = false;
		boolean failStatus = false; 
		boolean blockStatus = false; 
		for(TestResult tmp : caculateNum) {
			if(tmp.getResultTypeId() == 3) {
				testExecution.setPass(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				passStatus = true; 
			}
			if(tmp.getResultTypeId() == 2) {
				testExecution.setFail(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				failStatus = true; 
			}
			if(tmp.getResultTypeId() == 4) {
				testExecution.setBlock(tmp.getResultNum()); executed = executed + tmp.getResultNum();
				blockStatus = true; 
			}
			if(!passStatus) {testExecution.setPass(0);}
			if(!failStatus) {testExecution.setFail(0);}
			if(!blockStatus) {testExecution.setBlock(0);}
			
			totalCase = totalCase + tmp.getResultNum();
		}
			int NotRun = totalCase - executed;
			testExecution.setNotRun(NotRun);
			testExecution.setTotalCases(totalCase);
			if(totalCase > 0) {
			    double operation = (double)testExecution.getPass() / (double) totalCase;
		        BigDecimal big = new BigDecimal(operation);     
		        big = big.setScale(4, RoundingMode.HALF_UP);        
		        double passRatee = big.doubleValue();
		        testExecution.setPassrate(passRatee);
	        } else {
	        	testExecution.setPassrate(0.00);
	        }
			if(totalCase > 0) {
			    double operation1 = (double) executed / (double) totalCase;
		        BigDecimal big1 = new BigDecimal(operation1);     
		        big1 = big1.setScale(4, RoundingMode.HALF_UP);        
		        double executeRatee = big1.doubleValue();
		        testExecution.setExecuteRate(executeRatee);
			} else {
				testExecution.setExecuteRate(0.00);
			}
			int result = updateTestexecution(testExecution);
			return result;
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
	
	public List<SubExecution> listSubExecutionByPage(PageBean pageBean,
			SubExecution subexecution) throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from subexecution where executionid = '" + subexecution.getExecutionId() + "' ");
		sql.append("select a.*,b.SUBPLANNAME,c.PLATFORMNAME,d.OSNAME,e.PROJECTNAME,f.PHASENAME,g.username from subexecution a left join subtestplan b"
				+ " on b.SUBPLANID = a.SUBPLANID left join platform c on c.PLATFORMID = a.PLATFORMID left join executionos d on d.OSID = a.OSID"
				+ " left join project e on e.PROJECTID = a.PROJECTID left join executionphase f on f.PHASEID = a.PHASEID left join user g on g.userid=a.tester "
				+ "where a.executionid = '" + subexecution.getExecutionId() + "' ");
		sql.append("ORDER BY  CREATEDATE DESC");
		sql.append(" limit " + itemFrom + "," + count);
		logger.info("querySubExecutionByPage method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<SubExecution> SubexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution subexecution = new SubExecution();
						mapFullSubExecutionResultFromRs(subexecution, rs);
						return subexecution;
					}
				});
		return SubexecutionList;
	}
	
	public List<SubExecution> listSubExecutionByExecutionId(int id) throws Exception {

		StringBuffer sql = new StringBuffer();

		sql.append("select a.*,b.SUBPLANNAME,c.PLATFORMNAME,d.OSNAME,e.PROJECTNAME,f.PHASENAME,g.username from subexecution a left join subtestplan b"
				+ " on b.SUBPLANID = a.SUBPLANID left join platform c on c.PLATFORMID = a.PLATFORMID left join executionos d on d.OSID = a.OSID"
				+ " left join project e on e.PROJECTID = a.PROJECTID left join executionphase f on f.PHASEID = a.PHASEID left join user g on g.userid=a.tester "
				+ "where a.executionid = '" + id + "' ");
		logger.info("listSubExecutionByExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<SubExecution> SubexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution subexecution = new SubExecution();
						mapFullSubExecutionResultFromRs(subexecution, rs);
						return subexecution;
					}
				});
		return SubexecutionList;
	}
	
	public List<SubExecution> listSubExecutionsByExecutionId(int id) throws Exception {

		String sql = "select * from subexecution where executionid = '"+id+"' ";
		logger.info("querySubExecutionByExecutionId method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<SubExecution> list = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution subexecution = new SubExecution();
						//mapFullSubExecutionResultFromRs(subexecution, rs);
						mapSubExecutionFromRs(subexecution, rs);
						return subexecution;
					}
				});
		return list;
	}
	public int querySubExecutionCountBySubPlanId(int subPlanId)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) ");
		sql.append("from subexecution a ");
		sql.append("left join executionphase b on b.PHASEID=a.PHASEID ");
		sql.append("left join testexecution c on c.ID=a.EXECUTIONID ");
		sql.append("left join build d on d.buildid=c.BUILDID ");
		sql.append("left join platform e on e.PLATFORMID = a.PLATFORMID ");
		sql.append("left join executionos f on f.OSID = a.OSID ");
		sql.append("where a.subplanid = ").append(subPlanId);

		logger.info("querySubExecutionCountBySubPlanId method sql:" + sql.toString());
		int result = userJdbcTemplate.queryForInt(sql.toString());
		logger.info("querySubExecutionCountBySubPlanId method result:" + result);
		return result;
	}
	public List<SubExecution> listSubExecutionPageBySubPlanId(
			PageBean pageBean, int subPlanId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.SUBEXECUTIONID, a.SUBEXECUTIONNAME, a.subplanid, a.RELEASECYCLE, a.PLATFORMID, a.OSID, a.PHASEID, b.PHASENAME, d.BUILDTYPE, e.PLATFORMNAME, f.OSNAME, a.PASS, a.FAIL, a.BLOCK, a.NOTRUN ");
		sql.append("from subexecution a ");
		sql.append("left join executionphase b on b.PHASEID=a.PHASEID ");
		sql.append("left join testexecution c on c.ID=a.EXECUTIONID ");
		sql.append("left join build d on d.buildid=c.BUILDID ");
		sql.append("left join platform e on e.PLATFORMID = a.PLATFORMID ");
		sql.append("left join executionos f on f.OSID = a.OSID ");
		sql.append("where a.subplanid = ").append(subPlanId);
		sql.append(" order by a.SUBEXECUTIONID ");
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		sql.append(" limit " + itemFrom + "," + count);
		logger.info("listSubExecutionPageBySubPlanId method sql:" + sql.toString());
		@SuppressWarnings("unchecked")
		List<SubExecution> subexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution testexecution = new SubExecution();
						mapSubExecutionBySubPlanIdFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return subexecutionList;
	}
	
	public List<SubExecution> listSubExecutionGroupByPlatform(int subPlanId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.RELEASECYCLE,  b.PLATFORMNAME, IFNULL(sum(a.PASS), 0) PASS, IFNULL(sum(a.TOTALCASES), 0) TOTALCASES, round((IFNULL(sum(a.PASS), 0)/IFNULL(sum(a.TOTALCASES), 0)*100),2) as passrate ");
		sql.append("from subexecution a ");
		sql.append("left join platform b on b.PLATFORMID = a.PLATFORMID  ");
		sql.append("where a.subplanid = ").append(subPlanId);
		sql.append(" group by b.PLATFORMNAME, a.RELEASECYCLE ");
		sql.append(" order by a.RELEASECYCLE ");
		logger.info("listSubExecutionGroupByPlatform method sql:" + sql.toString());
		@SuppressWarnings("unchecked")
		List<SubExecution> subexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution testexecution = new SubExecution();
						mapSubExecutionGroupByPlatformFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return subexecutionList;
	}
	
	public List<SubExecution> listSubExecutionGroupByOS(int subPlanId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.RELEASECYCLE,  b.OSNAME, IFNULL(sum(a.PASS), 0) PASS, IFNULL(sum(a.TOTALCASES), 0) TOTALCASES, round((IFNULL(sum(a.PASS), 0)/IFNULL(sum(a.TOTALCASES), 0)*100),2) as passrate ");
		sql.append("from subexecution a ");
		sql.append("left join executionos b on b.OSID = a.OSID   ");
		sql.append("where a.subplanid = ").append(subPlanId);
		sql.append(" group by b.OSNAME, a.RELEASECYCLE ");
		sql.append(" order by a.RELEASECYCLE ");
		logger.info("listSubExecutionGroupByPlatform method sql:" + sql.toString());
		@SuppressWarnings("unchecked")
		List<SubExecution> subexecutionList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubExecution testexecution = new SubExecution();
						mapSubExecutionGroupByOSFromRs(testexecution, rs);
						return testexecution;
					}
				});
		return subexecutionList;
	}
	public String queryTextuctionSql(TestExecution testexecution) {
		StringBuffer sql = new StringBuffer();
		if (testexecution.getProjectId() != -1) {
			sql.append(" and a.projectid ='" + testexecution.getProjectId() + "'");

		}
		if (!testexecution.getReleaseCycle().equals("-1")) {
			sql.append(" and a.releaseCycle ='" + testexecution.getReleaseCycle()
					+ "'");
		}
		if (testexecution.getOsId() != -1) {
			sql.append("and  a.id in (select cc.executionid from testexecution dd left join subexecution"
					+ " cc on dd.id=cc.EXECUTIONID where cc.osid = '"+testexecution.getOsId()+"' group by cc.executionid)");
		}
		if (testexecution.getPlatformId() != -1) {
			sql.append("and  a.id in (select cc.executionid from testexecution dd left join subexecution"
					+ " cc on dd.id=cc.EXECUTIONID where cc.platformid = '"+testexecution.getPlatformId()+"' group by cc.executionid)");
		}

		if (!"".equals(testexecution.getExecutionName().trim())) {
			sql.append(" and a.executionName like '%" + testexecution.getExecutionName() + "%'");
		}
		
		if (testexecution.getBuildId() != -1) {
			sql.append(" and a.buildId ='" + testexecution.getBuildId() + "'");
		}
		if (testexecution.getPhaseId() != -1) {
			sql.append(" and a.phaseId ='" + testexecution.getPhaseId()
					+ "' ");
		}
		
		if (testexecution.getTestPlanId() != -1 && testexecution.getTestPlanId() != 0) {
			sql.append(" and a.testPlanId ='" + testexecution.getTestPlanId()
					+ "' ");
		}
		
		return sql.toString();
	}

	public String updateTestExecutionSql(TestExecution testexecution) {
		StringBuffer sql = new StringBuffer();
		
		if (!"".equals(testexecution.getReleaseCycle().trim())) {
			sql.append(" `releaseCycle` ='" + testexecution.getReleaseCycle()
					+ "',");
		}
		if (!"".equals(testexecution.getExecutionName().trim())) {
			sql.append(" `executionName` ='"
					+ StringEscapeUtils.escapeSql(testexecution.getExecutionName().trim()) + "',");
		}
		if (!"".equals(testexecution.getStartDate().trim())) {
			sql.append(" `startDate` ='"
					+ testexecution.getStartDate().trim() + "',");
		}
		if (!"".equals(testexecution.getEndDate().trim())) {
			sql.append(" `endDate` ='"
					+ testexecution.getEndDate().trim() + "',");
		}
		if (!"".equals(testexecution.getDesc().trim())) {
			sql.append(" `desc` ='"
					+ StringEscapeUtils.escapeSql(testexecution.getDesc().trim()) + "',");
		}
		if (testexecution.getBuildId() >= 0) {
			sql.append(" `buildId` ='" + testexecution.getBuildId() + "',");
		}
		if (testexecution.getPhaseId() >= 0) {
			sql.append(" `phaseId` ='" + testexecution.getPhaseId() + "',");
		}
		if (testexecution.getNotRun() >= 0) {
			sql.append(" `notRun` ='" + testexecution.getNotRun() + "',");
		}
		if (testexecution.getPass() >= 0) {
			sql.append(" `pass` ='" + testexecution.getPass() + "',");
		}
		if (testexecution.getFail() >= 0) {
			sql.append(" `fail` ='" + testexecution.getFail() + "',");
		}
		if (testexecution.getBlock() >= 0) {
			sql.append(" `block` ='" + testexecution.getBlock() + "',");
		}
		if (testexecution.getTotalCases() >= 0) {
			sql.append(" `totalCases` ='" + testexecution.getTotalCases() + "',");
		}
		if (testexecution.getPassrate() != null) {
			sql.append(" `passrate` ='" + testexecution.getPassrate() + "',");
		}
		if (testexecution.getExecuteRate() != null) {
			sql.append(" `executerate` ='" + testexecution.getExecuteRate() + "',");
		}
		return sql.toString();
	}
	
	public String updateSubExecutionSql(SubExecution subexecution) {
		StringBuffer sql = new StringBuffer();
		if (!"".equals(subexecution.getSubExecutionName().trim())) {
			sql.append(" `subExecutionName` ='"
					+ StringEscapeUtils.escapeSql(subexecution.getSubExecutionName().trim()) + "',");
		}
		if (!"".equals(subexecution.getDueDate().trim())) {
			sql.append(" `dueDate` ='"
					+ subexecution.getDueDate().trim() + "',");
		}
		if (!"".equals(subexecution.getDesc().trim())) {
			sql.append(" `desc` ='"
					+ StringEscapeUtils.escapeSql(subexecution.getDesc().trim()) + "',");
		}
		if (subexecution.getTester() > 0) {
			sql.append(" `tester` ='" + subexecution.getTester() + "',");
		}
		if (subexecution.getNotRun() >= 0) {
			sql.append(" `notRun` ='" + subexecution.getNotRun() + "',");
		}
		if (subexecution.getPass() >= 0) {
			sql.append(" `pass` ='" + subexecution.getPass() + "',");
		}
		if (subexecution.getFail() >= 0) {
			sql.append(" `fail` ='" + subexecution.getFail() + "',");
		}
		if (subexecution.getBlock() >= 0) {
			sql.append(" `block` ='" + subexecution.getBlock() + "',");
		}
		if (subexecution.getTotalCases() >= 0) {
			sql.append(" `totalCases` ='" + subexecution.getTotalCases() + "',");
		}
		if (subexecution.getPassrate() != null) {
			sql.append(" `passrate` ='" + subexecution.getPassrate() + "',");
		}
		if (subexecution.getExecuteRate() != null) {
			sql.append(" `executerate` ='" + subexecution.getExecuteRate() + "',");
		}
		return sql.toString();
	}
	
	public int queryTestexecutionSize(TestExecution testexecution)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from testexecution a where EXISTS (SELECT * from user_team as map left join project  "
				+ "pro on pro.teamid=map.TEAMID WHERE (map.status <> 1 or map.status is null) and "
				+ "pro.projectid = a.projectid and map.USERID='"+testexecution.getUserId()+"')");
		sql.append(queryTextuctionSql(testexecution));
		logger.info("queryTestexecutionSize method sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql.toString());
		logger.info("queryTestexecutionSize method result:" + result);
		return result;
	}


	public int querySubExecutionSize(Map<String, String> filterMap)
			throws Exception {
		String executionId = filterMap.get("executionId");
		String sql = "select count(*) from subexecution where executionid = '"+ executionId + "'";
		logger.info("querySubExecutionSize method sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("querySubExecutionSize method result:" + result);
		return result;
	}
	
	public int listSubTestExecutionById(int executionId)
			throws Exception {

		String sql = "select * from subexecution where executionid = '"+ executionId + "'";
		logger.info("querySubExecutionSize method sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("querySubExecutionSize method result:" + result);
		return result;
	}
	
	public int queryByExeNameCycleSize(TestExecution testexecution)
			throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != testexecution.getExecutionId()) {
			sql = "select count(*) from testexecution where executionName  ='"
					+ testexecution.getExecutionName() + "'  and id!='"
					+ testexecution.getExecutionId() + "' ";
			logger.info("queryByExexecutionNameSize method:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			if (result == 0) {
				sql = "select count(*) from testexecution where  releaseCycle ='"
						+ testexecution.getReleaseCycle()
						+ "'  and id!='"
						+ testexecution.getExecutionId() + "' ";
				logger.info("queryCycleNameSize method:" + sql);
				result = userJdbcTemplate.queryForInt(sql);
				if(result!=0){
					size = 2;
				}
			}

		} else {
			sql = "select count(*) from testexecution where executionName ='"
					+ testexecution.getExecutionName() + "'  ";
			logger.info("queryByExexecutionNameSize method:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			
			if (result == 0) {
				sql = "select count(*) from testexecution where  releaseCycle ='"
						+ testexecution.getReleaseCycle() + "'   ";
				logger.info("queryCycleNameSize method:" + sql);
				result = userJdbcTemplate.queryForInt(sql);
				if(result!=0){
					size = 2;
				}
				
			}
		}

		logger.info("queryByExeNameCycleSize method result:" + result);
		return size;
	}

	public int queryBySubExecutionNameSize(SubExecution subexecution)
			throws Exception {
		String sql = "";
		int result;
			sql = "select count(*) from subexecution where subExecutionName  ='"
					+ subexecution.getSubExecutionName() + "' and executionId = '"+ subexecution.getId() +"'";
			result = userJdbcTemplate.queryForInt(sql);


		logger.info("queryBySubExecutionNameSize method result:" + result);
		return result;
	}
	
	public int queryByExecutionIdAndSubPlanId(SubExecution subexecution)
			throws Exception {
		String sql = "";
		int result;
			sql = "select count(*) from subexecution where executionId ='" + 
					subexecution.getExecutionId() + "' and subPlanId ='"+ subexecution.getSubPlanId() +"'" ;
			result = userJdbcTemplate.queryForInt(sql);


		logger.info("queryByExecutionIdAndSubPlanId method result:" + result);
		return result;
	}
	
	public int querySubExecutionNameSize(SubExecution subexecution) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != subexecution.getSubExecutionId()) {
			sql = "select count(*) from subexecution where subExecutionName = '"+ subexecution.getSubExecutionName() + "'  and subExecutionId !='"
			+ subexecution.getSubExecutionId() + "' and executionId = '"+subexecution.getExecutionId()+"'";
			logger.info("querySubExecutionNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from subexecution where subExecutionName = '"+ subexecution.getSubExecutionName() + 
					"' and executionId = '"+subexecution.getExecutionId()+"'";
			logger.info("querySubExecutionNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("querySubExecutionNameSize method result:" + result);
		return size;
	}
	
	public int verifyDuplicateSubExecution(SubExecution subexecution)
			throws Exception {
		int result = 0;
		int size = 0;
		//create new sub execution check.
		if (0 == subexecution.getSubExecutionId()) {
			String sql = "select count(*) from subexecution where executionId ='" + subexecution.getExecutionId() + "' and subPlanId ='"+ 
					subexecution.getSubPlanId() +"' and platformid = '"+subexecution.getPlatformId()+"' and osid = '"+subexecution.getOsId()+"'" ;
			logger.info("verifyDuplicateSubExecution method sql:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result > 0 ) {size = 1;}
		} else {
			String sql = "select count(*) from subexecution where executionId ='" + subexecution.getExecutionId() + "' and subPlanId ='"+ 
					subexecution.getSubPlanId() +"' and platformid = '"+subexecution.getPlatformId()+"' and osid = '"+subexecution.getOsId()+"' "
							+ "and subexecutionid != '"+subexecution.getSubExecutionId()+"'" ;
			logger.info("verifyDuplicateSubExecution method sql:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result > 0 ) {size = 1;}			
		}
		
		logger.info("queryByExecutionIdAndSubPlanId method result:" + result);
		return size;
	}
	
	public int addTestexecution(final TestExecution testexecution)
			throws Exception {

		final String sql = "insert into testexecution(testPlanId, ownner, pass, fail,  notRun, block, projectId, "
				+ "totalCases, tesId, executionName, releaseCycle, passrate, executerate, createDate, startDate, endDate, `desc`,osId,platformId,cycleId,buildId,phaseId)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("addTestexecution method sql:" + sql);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		userJdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapTestExecutionToPs(testexecution, ps);
				return ps;
			}
		}, keyHolder);
		int result = keyHolder.getKey().intValue();
		logger.info("addTestexecution method result:" + result);
		System.out.println(testexecution.logInfo());
		return result;

	}
	
	public int addSubExecution(final SubExecution subexecution)
			throws Exception {

		final String sql = "INSERT INTO subexecution (`PLATFORMID`, `ENVID`, `NICID`, `EXECUTIONID`, `PHASEID`, `OSID`, `SUBEXECUTIONNAME`, "
				+ "`PROJECTID`, `RELEASECYCLE`, `SUBPLANID`, `TESTER`, `PASS`, `FAIL`, `BLOCK`, `NOTRUN`, `TOTALCASES`, `PASSRATE`, `EXECUTERATE`, "
				+ "`DUEDATE`, `CREATEDATE`, `MODIFYDATE`, `DESC`) VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("addTestPlan method sql:" + sql);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		System.out.println("------------");
		userJdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapSubExecutionToPs(subexecution, ps);
				return ps;
			}
		}, keyHolder);
		int result = keyHolder.getKey().intValue();
		logger.info("addSubExecution method result:" + result);
		return result;
	}
	
	public int queryCaseInfoBySubPlanIdSize(int subExecutionId, int subplanId) {
		String sql = "select count(t1.testcaseid) "
				+ " from "
				+ " (SELECT ts.testcaseid,ts.testcasename, a.featurename, b.compname, c.testtypename, d.osname, e.autoname "
				+ " from  "
				+ " testcase as ts  "
				+ " left join feature a on a.FEATUREID = ts.FEATUREID  "
				+ " left join component b on b.COMPID =ts.COMPID  "
				+ " left join testtype c on c.TESTTYPEID = ts.TESTTYPEID  "
				+ " left join os d on d.osid = ts.osid  "
				+ " left join automation e on e.AUTOID = ts.AUTOID "
				+ " where ts.testcaseid in "
				+ " (select tr.testcaseid  "
				+ " from testresult tr "
				+ " where tr.subplanid= " + subplanId
				+ " and tr.subexecutionid= " + subExecutionId
				+ " ) "
				+ " order by ts.testcaseid ) t1, "
				+ " (select tr.resultid,tr.testcaseid "
				+ " from testresult tr "
				+ " where tr.subplanid= " + subplanId
				+ " and tr.subexecutionid= " + subExecutionId + ") t2 "
				+ " where t1.testcaseid = t2.testcaseid "
				;
		logger.info("sql:" + sql);
		
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("result:" + result);
		return result;

	}
	
	public List<?> listCaseInfoBySubPlanId(int subExecutionId, int subplanId,PageBean pageBean) {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = " select t1.*, t2.resultid "
				+ " from "
				+ " (SELECT ts.testcaseid,ts.testcasename, a.featurename, b.compname, c.testtypename, d.osname, e.autoname "
				+ " from  " + " testcase as ts  "
				+ " left join feature a on a.FEATUREID = ts.FEATUREID  "
				+ " left join component b on b.COMPID =ts.COMPID  "
				+ " left join testtype c on c.TESTTYPEID = ts.TESTTYPEID  "
				+ " left join os d on d.osid = ts.osid  "
				+ " left join automation e on e.AUTOID = ts.AUTOID "
				+ " where ts.testcaseid in " + " (select tr.testcaseid  "
				+ " from testresult tr " + " where tr.subplanid= " + subplanId
				+ " and tr.subexecutionid= " + subExecutionId + " ) "
				+ " order by ts.testcaseid ) t1, "
				+ " (select tr.resultid,tr.testcaseid "
				+ " from testresult tr " + " where tr.subplanid= " + subplanId
				+ " and tr.subexecutionid= " + subExecutionId + ") t2 "
				+ " where t1.testcaseid = t2.testcaseid "
				+ " order by t1.testcaseid ";
		sql = sql + " limit " + itemFrom + "," + count;
		logger.info("sql:" + sql);
		return userJdbcTemplate.queryForList(sql);
		
	}
	
	public List<TestResult> listCasePassRateHistory(int subExecutionId, int subplanId) {

		String sql = " select a.resulttrackid, a.testresultid,a.resulttypeid, b.resulttypename, c.resultid,c.testcaseid,c.testcasename "
				+ " from resulttrack a  "
				+ " left join resulttype b on a.ResultTypeId=b.RESULTTYPEID "
				+ " left join testresult c on c.RESULTID=a.TestResultId "
				+ " where c.subplanid= " + subplanId
				+ " and c.subexecutionid= "+ subExecutionId
				+ " order by c.testcaseid ";

		logger.info("listCasePassRateHistory method sql:" + sql.toString());
		@SuppressWarnings("unchecked")
		List<TestResult> resultList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestResult testResult = new TestResult();
						mapCasePassRateHistoryFromRs(testResult, rs);
						return testResult;
					}
				});
		return resultList;
		
	}
	
	public List<PerformanceResult> listPerformanceAnalysis(int subExecutionId) {

		String sql = " select se.subexecutionname,tr.testcaseid, tr.testcasename, pr.performanceResultId,pr.resulttrackid,pr.testresultid, "
				+ " pr.elementindex,pr.attributename,pr.attributevalue,pr.createdate  "
				+ " from testresult tr "
				+ " left join performanceresult pr on tr.RESULTID = pr.TestResultId "
				+ " left join subexecution se on tr.subexecutionid = se.subexecutionid " 
				+ " where tr.subexecutionid= "+ subExecutionId
				+ " order by pr.testresultid, pr.resulttrackid,pr.PerformanceResultId ";

		logger.info("listPerformanceAnalysis method sql:" + sql.toString());
		@SuppressWarnings("unchecked")
		List<PerformanceResult> resultList = userJdbcTemplate.query(
				sql.toString(), new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						PerformanceResult performanceResult = new PerformanceResult();
						mapPerformanceAnalysisFromRs(performanceResult, rs);
						return performanceResult;
					}
				});
		return resultList;
		
	}

	public int deleTestexecution(final String[] testplans) throws Exception {
		String sql = "delete from testexecution where id=?";
		logger.info("deleTestexecution method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testplans[i].trim()));
					}

					public int getBatchSize() {

						return testplans.length;
					}
				});

		logger.info("deleTestexecution method result:" + result.length);
		return result.length;
	}
	
	public int delSubExecutionByExecutionId(final String[] testplans) throws Exception {
		String sql = "delete from subexecution where executionid=?";
		logger.info("delSubExecutionByExecutionId method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testplans[i].trim()));
					}

					public int getBatchSize() {

						return testplans.length;
					}
				});

		logger.info("delSubExecutionByExecutionId method result:" + result.length);
		return result.length;
	}
	
	public int delSubExecutionBySubId(final String[] testplans) throws Exception {
		String sql = "delete from subexecution where subexecutionid=?";
		logger.info("delSubExecutionBySubId method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testplans[i].trim()));
					}

					public int getBatchSize() {

						return testplans.length;
					}
				});

		logger.info("delSubExecutionBySubId method result:" + result.length);
		return result.length;
	}
	
	public int delSubExecutionCaseListByExecutionId(final String[] testplans) throws Exception {
		String sql = "delete from testresult where executionid=?";
		logger.info("delSubExecutionCaseListByExecutionId method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testplans[i].trim()));
					}

					public int getBatchSize() {

						return testplans.length;
					}
				});

		logger.info("delSubExecutionCaseListByExecutionId method result:" + result.length);
		return result.length;
	}
	
	public int delSubExecutionCaseListBySubExecution(final String[] testplans) throws Exception {
		String sql = "delete from testresult where subexecutionid=?";
		logger.info("delSubExecutionCaseListBySubExecution method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testplans[i].trim()));
					}

					public int getBatchSize() {

						return testplans.length;
					}
				});

		logger.info("delSubExecutionCaseListBySubExecution method result:" + result.length);
		return result.length;
	}

	@Override
	public TestExecution queryTestExecutionById(int id) throws Exception {
		//String sql = "select *  from testexecution where id='" + id + "'";
		String sql = "select a.*,b.PLANNAME,c.PROJECTNAME,d.USERNAME,e.PHASENAME,f.BUILDTYPE from testexecution a left join testplan b"
				+ " on b.TESTPLANID = a.TESTPLANID left join project c on c.PROJECTID=a.PROJECTID left join user d on d.USERID=a.OWNNER "
				+ "left join executionphase e on e.PHASEID=a.PHASEID left join build f on f.buildid=a.BUILDID where id='" + id + "'";
		logger.info("queryTestExecutionById method sql" + sql);
		final TestExecution testexecution = new TestExecution();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapFullExecutionResultFromRs(testexecution, rs);
			}
		});

		logger.info("queryTestExecutionById method result:"
				+ testexecution.logInfo());
		return testexecution;
	}
	
	public SubExecution querySubExecutionById(int id) throws Exception {
		//String sql = "select * from subexecution where subExecutionId='" + id + "'";
		String sql = "select a.*,b.SUBPLANNAME,c.PLATFORMNAME,d.OSNAME,e.PROJECTNAME,f.PHASENAME,g.userName, h.executionname, h.releaseCycle from subexecution a left join subtestplan b "
				+ "on b.SUBPLANID = a.SUBPLANID left join platform c on c.PLATFORMID = a.PLATFORMID left join executionos d on d.OSID = a.OSID left join "
				+ "project e on e.PROJECTID = a.PROJECTID left join executionphase f on f.PHASEID = a.PHASEID left join user g on g.userid=a.tester"
				+ " left join testexecution h on h.id=a.executionid where a.subExecutionId='" + id + "'";
		logger.info("querySubExecutionById method sql" + sql);
		final SubExecution subexecution = new SubExecution();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapFullSubExecutionResultFromRs(subexecution, rs);
				subexecution.setReleaseCycle(rs.getString("releaseCycle"));
				subexecution.setExecutionName(rs.getString("executionName"));
				
			}
		});

		logger.info("querySubExecutionById method result:"
				+ subexecution.logInfo());
		return subexecution;
	}

	@Override
	public int updateTestexecution(final TestExecution testexecution)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update testexecution set ");
		sql.append(updateTestExecutionSql(testexecution));
		sql.append("modifyDate ='" + Utils.dateFormat(new Date(), null)+"' where `id` = '" +testexecution.getId()+"'");
		logger.info("Update test execution method sql:" + sql);
		int result = userJdbcTemplate.update(sql.toString());
		return result;
	}
	
	public int updateSubExecution(final SubExecution subexecution)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update subexecution set ");
		sql.append(updateSubExecutionSql(subexecution));
		sql.append("`modifyDate` ='" + Utils.dateFormat(new Date(), null)+"' where `subExecutionId` = '" +subexecution.getSubExecutionId()+"'");
		logger.info("Update sub execution method sql:" + sql);
		int result = userJdbcTemplate.update(sql.toString());
		return result;
	}

	public static void mapFullExecutionResultFromRs(TestExecution testexecution,
			ResultSet rs) throws SQLException {
		testexecution.setPass(rs.getInt("pass"));
		testexecution.setBlock(rs.getInt("block"));
		testexecution.setCreateDate(rs.getString("createdate"));
		testexecution.setDesc(rs.getString("desc"));
		testexecution.setStartDate(rs.getString("startdate"));
		testexecution.setEndDate(rs.getString("enddate"));
		testexecution.setExecuteRate(rs.getDouble("executerate"));
		testexecution.setExecutionName(rs.getString("executionname"));
		testexecution.setFail(rs.getInt("fail"));
		testexecution.setId(rs.getInt("id"));
		testexecution.setNotRun(rs.getInt("notrun"));
		testexecution.setOwnner(rs.getInt("ownner"));
		testexecution.setReleaseCycle(rs.getString("releasecycle"));
		testexecution.setPassrate(rs.getDouble("passrate"));
		testexecution.setProjectId(rs.getInt("projectid"));
		testexecution.setTester(rs.getInt("tester"));
		testexecution.setTotalCases(rs.getInt("totalcases"));
		testexecution.setTestPlanId(rs.getInt("testplanId"));
		testexecution.setTesId(rs.getInt("tesid"));
		testexecution.setFail(rs.getInt("fail"));
		testexecution.setOsId(rs.getInt("osid"));
		testexecution.setCycleId(rs.getInt("cycleid"));
		testexecution.setBuildId(rs.getInt("buildid"));
		testexecution.setPhaseId(rs.getInt("phaseid"));
		testexecution.setPlatformId(rs.getInt("platformid"));
		testexecution.setTestPlanName(rs.getString("planName"));
		testexecution.setProjectName(rs.getString("projectName"));
		testexecution.setUserName(rs.getString("userName"));
		testexecution.setPhaseName(rs.getString("phaseName"));
		testexecution.setBuildTypeName(rs.getString("buildType"));
		
	}
	
	public static void mapTestExecutionFromRs(TestExecution testexecution,
			ResultSet rs) throws SQLException {
		testexecution.setPass(rs.getInt("pass"));
		testexecution.setBlock(rs.getInt("block"));
		testexecution.setCreateDate(rs.getString("createdate"));
		testexecution.setDesc(rs.getString("desc"));
		testexecution.setStartDate(rs.getString("startdate"));
		testexecution.setEndDate(rs.getString("enddate"));
		testexecution.setExecuteRate(rs.getDouble("executerate"));
		testexecution.setExecutionName(rs.getString("executionname"));
		testexecution.setFail(rs.getInt("fail"));
		testexecution.setId(rs.getInt("id"));
		testexecution.setNotRun(rs.getInt("notrun"));
		testexecution.setOwnner(rs.getInt("ownner"));
		testexecution.setReleaseCycle(rs.getString("releasecycle"));
		testexecution.setPassrate(rs.getDouble("passrate"));
		testexecution.setProjectId(rs.getInt("projectid"));
		testexecution.setTester(rs.getInt("tester"));
		testexecution.setTotalCases(rs.getInt("totalcases"));
		testexecution.setTestPlanId(rs.getInt("testplanId"));
		testexecution.setTesId(rs.getInt("tesid"));
		testexecution.setFail(rs.getInt("fail"));
		testexecution.setOsId(rs.getInt("osid"));
		testexecution.setCycleId(rs.getInt("cycleid"));
		testexecution.setBuildId(rs.getInt("buildid"));
		testexecution.setPhaseId(rs.getInt("phaseid"));
		testexecution.setPlatformId(rs.getInt("platformid"));
	}
		
	public static void mapFullSubExecutionResultFromRs(SubExecution subexecution,
			ResultSet rs) throws SQLException {
		subexecution.setSubExecutionId(rs.getInt("subexecutionid"));
		subexecution.setEnvId(rs.getInt("envid"));
		subexecution.setNicId(rs.getInt("nicid"));
		subexecution.setExecutionId(rs.getInt("executionid"));
		subexecution.setSubExecutionName(rs.getString("subexecutionname"));
		subexecution.setSubPlanId(rs.getInt("subplanid"));
		subexecution.setDueDate(rs.getString("duedate"));
		subexecution.setModifyDate(rs.getString("modifydate"));
		subexecution.setPass(rs.getInt("pass"));
		subexecution.setBlock(rs.getInt("block"));
		subexecution.setCreateDate(rs.getString("createdate"));
		subexecution.setDesc(rs.getString("desc"));
		subexecution.setExecuteRate(rs.getDouble("executerate"));
		subexecution.setFail(rs.getInt("fail"));
		subexecution.setNotRun(rs.getInt("notrun"));
		subexecution.setReleaseCycle(rs.getString("releasecycle"));
		subexecution.setPassrate(rs.getDouble("passrate"));
		subexecution.setProjectId(rs.getInt("projectid"));
		subexecution.setTester(rs.getInt("tester"));
		subexecution.setTotalCases(rs.getInt("totalcases"));
		subexecution.setFail(rs.getInt("fail"));
		subexecution.setOsId(rs.getInt("osid"));
		subexecution.setPhaseId(rs.getInt("phaseid"));
		subexecution.setPlatFormName(rs.getString("platFormName"));
		subexecution.setPlatformId(rs.getInt("platformId"));
		subexecution.setSubPlanName(rs.getString("subPlanName"));
		subexecution.setOsName(rs.getString("osName"));
		subexecution.setProjectName(rs.getString("projectName"));
		subexecution.setTesterName(rs.getString("userName"));
	}
	
	public static void mapSubExecutionFromRs(SubExecution subexecution,
			ResultSet rs) throws SQLException {
		subexecution.setSubExecutionId(rs.getInt("subexecutionid"));
		subexecution.setEnvId(rs.getInt("envid"));
		subexecution.setNicId(rs.getInt("nicid"));
		subexecution.setExecutionId(rs.getInt("executionid"));
		subexecution.setSubExecutionName(rs.getString("subexecutionname"));
		subexecution.setSubPlanId(rs.getInt("subplanid"));
		subexecution.setDueDate(rs.getString("duedate"));
		subexecution.setModifyDate(rs.getString("modifydate"));
		subexecution.setPass(rs.getInt("pass"));
		subexecution.setBlock(rs.getInt("block"));
		subexecution.setCreateDate(rs.getString("createdate"));
		subexecution.setDesc(rs.getString("desc"));
		subexecution.setSubExecutionDesc(rs.getString("desc"));
		subexecution.setExecuteRate(rs.getDouble("executerate"));
		subexecution.setFail(rs.getInt("fail"));
		subexecution.setNotRun(rs.getInt("notrun"));
		subexecution.setReleaseCycle(rs.getString("releasecycle"));
		subexecution.setPassrate(rs.getDouble("passrate"));
		subexecution.setProjectId(rs.getInt("projectid"));
		subexecution.setTester(rs.getInt("tester"));
		subexecution.setTotalCases(rs.getInt("totalcases"));
		subexecution.setFail(rs.getInt("fail"));
		subexecution.setOsId(rs.getInt("osid"));
		subexecution.setPhaseId(rs.getInt("phaseid"));
		subexecution.setPlatformId(rs.getInt("platformId"));
	}
	public static void mapSubExecutionBySubPlanIdFromRs(SubExecution subexecution,
			ResultSet rs) throws SQLException {
		subexecution.setSubExecutionId(rs.getInt("subexecutionid"));
		subexecution.setSubExecutionName(rs.getString("subexecutionname"));
		subexecution.setReleaseCycle(rs.getString("releasecycle"));
		subexecution.setPhaseId(rs.getInt("phaseid"));
		subexecution.setPlatformId(rs.getInt("platformId"));
		subexecution.setOsId(rs.getInt("osId"));
		subexecution.setPhaseName(rs.getString("phaseName"));
		subexecution.setBuildTypeName(rs.getString("buildType"));
		subexecution.setPlatFormName(rs.getString("platformName"));
		subexecution.setOsName(rs.getString("osName"));
		subexecution.setPass(rs.getInt("pass"));
		subexecution.setFail(rs.getInt("fail"));
		subexecution.setBlock(rs.getInt("block"));
		subexecution.setNotRun(rs.getInt("notRun"));
		subexecution.setSubPlanId(rs.getInt("subPlanId"));
	}
	
	public static void mapSubExecutionGroupByPlatformFromRs(SubExecution subexecution,
			ResultSet rs) throws SQLException {
		subexecution.setReleaseCycle(rs.getString("releasecycle"));
		subexecution.setPlatFormName(rs.getString("platformName"));
		subexecution.setPass(rs.getInt("pass"));
		subexecution.setTotalCases(rs.getInt("totalcases"));
		subexecution.setPassrate(rs.getDouble("passrate"));
	}
	public static void mapCasePassRateHistoryFromRs(TestResult testResult,
			ResultSet rs) throws SQLException {
		testResult.setResultTypeName(rs.getString("resultTypeName"));
		testResult.setResultId(rs.getInt("resultId"));
		testResult.setTestCaseId(rs.getInt("testCaseId"));
		testResult.setTestCaseName(rs.getString("testCaseName"));
	}
	public static void mapPerformanceAnalysisFromRs(PerformanceResult performanceResult,
			ResultSet rs) throws SQLException {
		performanceResult.setSubExecutionName(rs.getString("subExecutionName"));
		performanceResult.setTestCaseId(rs.getInt("testCaseId"));
		performanceResult.setTestCaseName(rs.getString("testCaseName"));
		performanceResult.setPerformanceResultId(rs.getInt("performanceResultId"));
		performanceResult.setResultTrackId(rs.getInt("resultTrackId"));
		performanceResult.setTestResultId(rs.getInt("testResultId"));
		performanceResult.setElementIndex(rs.getInt("elementIndex"));
		performanceResult.setAttributeName(rs.getString("attributeName"));
		performanceResult.setAttributeValue(rs.getString("attributeValue"));
		performanceResult.setCreateDate(rs.getString("createDate"));
		
	}
	public static void mapSubExecutionGroupByOSFromRs(SubExecution subexecution,
			ResultSet rs) throws SQLException {
		subexecution.setReleaseCycle(rs.getString("releasecycle"));
		subexecution.setOsName(rs.getString("osName"));
		subexecution.setPass(rs.getInt("pass"));
		subexecution.setTotalCases(rs.getInt("totalcases"));
		subexecution.setPassrate(rs.getDouble("passrate"));
	}
	public static void mapTestExecutionToPs(final TestExecution testexecution,
			PreparedStatement ps) throws SQLException {
		if (testexecution.getTestPlanId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, testexecution.getTestPlanId());
		}

		if (testexecution.getOwnner() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testexecution.getOwnner());
		}
		if (testexecution.getPass() == 0) {
			ps.setNull(3, 0);
		} else {
			ps.setInt(3, testexecution.getPass());
		}

		if (testexecution.getFail() == 0) {
			ps.setNull(4, 0);
		} else {
			ps.setInt(4, testexecution.getFail());
		}

		if (testexecution.getNotRun() == 0) {
			ps.setNull(5, 0);
		} else {
			ps.setInt(5, testexecution.getNotRun());
		}
		if (testexecution.getBlock() == 0) {
			ps.setNull(6, 0);
		} else {
			ps.setInt(6, testexecution.getBlock());
		}

		if (testexecution.getProjectId() == 0) {
			ps.setNull(7, 0);
		} else {
			ps.setInt(7, testexecution.getProjectId());
		}

		if (testexecution.getTotalCases() == 0) {
			ps.setNull(8, 0);
		} else {
			ps.setInt(8, testexecution.getTotalCases());
		}

		if (testexecution.getTesId() == 0) {
			ps.setNull(9, 0);
		} else {
			ps.setInt(9, testexecution.getTesId());
		}
		if (testexecution.getExecutionName().trim() == "") {
			ps.setNull(10, Types.NULL);
		} else {
			ps.setString(10,testexecution.getExecutionName().trim());
		}
		if (testexecution.getReleaseCycle() == "") {
			ps.setNull(11, Types.NULL);
		} else {
			ps.setString(11, testexecution.getReleaseCycle());
		}

		if (testexecution.getPassrate() == null) {
			ps.setNull(12, Types.NULL);
		} else {
			ps.setDouble(12, testexecution.getPassrate());

		}
		if (testexecution.getExecuteRate() == null) {
			ps.setNull(13, Types.NULL);
		} else {
			ps.setDouble(13, testexecution.getExecuteRate());
		}
		if (testexecution.getCreateDate() == "") {
			ps.setNull(14, Types.NULL);
		} else {
			ps.setString(14, testexecution.getCreateDate());
		}

		if (testexecution.getStartDate() == null) {
			ps.setNull(15, Types.NULL);
		} else {
			ps.setString(15, testexecution.getStartDate());
		}
		if (testexecution.getEndDate() == "") {
			ps.setNull(16, Types.NULL);
		} else {
			ps.setString(16, testexecution.getEndDate());
		}

		if (testexecution.getDesc().trim() == "") {
			ps.setNull(17, Types.NULL);
		} else {
			ps.setString(17, testexecution.getDesc().trim());
		}
		if (testexecution.getOsId() == 0) {
			ps.setNull(18, 0);
		} else {
			ps.setInt(18, testexecution.getOsId());
		}

		if (testexecution.getPlatformId() == 0) {
			ps.setNull(19, 0);
		} else {
			ps.setInt(19, testexecution.getPlatformId());
		}

		if (testexecution.getCycleId() == 0) {
			ps.setNull(20, Types.NULL);
		} else {
			ps.setInt(20, testexecution.getCycleId());
		}

		if (testexecution.getBuildId() == 0) {
			ps.setNull(21, Types.NULL);
		} else {
			ps.setInt(21, testexecution.getBuildId());
		}
		if (testexecution.getPhaseId() == 0) {
			ps.setNull(22, 0);
		} else {
			ps.setInt(22, testexecution.getPhaseId());
		}

	}
	
	public static void mapSubExecutionToPs(final SubExecution subexecution,
			PreparedStatement ps) throws SQLException {
		if (subexecution.getPlatformId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, subexecution.getPlatformId());
		}
		if (subexecution.getEnvId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, subexecution.getEnvId());
		}
		if (subexecution.getNicId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, subexecution.getNicId());
		}
		if (subexecution.getExecutionId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, subexecution.getExecutionId());
		}
		if (subexecution.getPhaseId() == 0) {
			ps.setNull(5, 0);
		} else {
			ps.setInt(5, subexecution.getPhaseId());
		}
		if (subexecution.getOsId() == 0) {
			ps.setNull(6, 0);
		} else {
			ps.setInt(6, subexecution.getOsId());
		}
		if (subexecution.getSubExecutionName().trim() == "") {
			ps.setNull(7, 0);
		} else {
			ps.setString(7, subexecution.getSubExecutionName().trim());
		}
		if (subexecution.getProjectId() == 0) {
			ps.setNull(8, 0);
		} else {
			ps.setInt(8, subexecution.getProjectId());
		}
		if (subexecution.getReleaseCycle() == "") {
			ps.setNull(9, Types.NULL);
		} else {
			ps.setString(9, subexecution.getReleaseCycle());
		}
		if (subexecution.getSubPlanId() == 0) {
			ps.setNull(10, Types.NULL);
		} else {
			ps.setInt(10, subexecution.getSubPlanId());
		}
		if (subexecution.getTester() == 0) {
			ps.setNull(11, Types.NULL);
		} else {
			ps.setInt(11, subexecution.getTester());
		}
		if (subexecution.getPass() == 0) {
			ps.setNull(12, 0);
		} else {
			ps.setInt(12, subexecution.getPass());
		}
		if (subexecution.getFail() == 0) {
			ps.setNull(13, 0);
		} else {
			ps.setInt(13, subexecution.getFail());
		}
		if (subexecution.getBlock() == 0) {
			ps.setNull(14, 0);
		} else {
			ps.setInt(14, subexecution.getBlock());
		}
		if (subexecution.getNotRun() == 0) {
			ps.setNull(15, 0);
		} else {
			ps.setInt(15, subexecution.getNotRun());
		}
		if (subexecution.getTotalCases() == 0) {
			ps.setNull(16, 0);
		} else {
			ps.setInt(16, subexecution.getTotalCases());
		}
		if (subexecution.getPassrate() == null) {
			ps.setNull(17, Types.NULL);
		} else {
			ps.setDouble(17, subexecution.getPassrate());

		}
		if (subexecution.getExecuteRate() == null) {
			ps.setNull(18, Types.NULL);
		} else {
			ps.setDouble(18, subexecution.getExecuteRate());
		}
		if (subexecution.getDueDate() == "") {
			ps.setNull(19, Types.NULL);
		} else {
			ps.setString(19, subexecution.getDueDate());
		}
		if (subexecution.getCreateDate() == "") {
			ps.setNull(20, Types.NULL);
		} else {
			ps.setString(20, subexecution.getCreateDate());
		}
		if (subexecution.getModifyDate() == "") {
			ps.setNull(21, Types.NULL);
		} else {
			ps.setString(21, subexecution.getModifyDate());
		}
		if (subexecution.getSubExecutionDesc().trim() == "") {
			ps.setNull(22, Types.NULL);
		} else {
			ps.setString(22, subexecution.getSubExecutionDesc().trim());
		}
	}
	
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
