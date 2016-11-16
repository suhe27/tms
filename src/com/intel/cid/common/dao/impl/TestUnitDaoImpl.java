package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.dao.TestUnitDao;
import com.intel.cid.utils.Utils;
 
public class TestUnitDaoImpl implements TestUnitDao {

	private static final Logger logger = Logger
			.getLogger(TestUnitDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	@Override
	public int addTestUnit(final TestUnit testUnit) throws Exception {
		final String sql = " insert into  testunit(testunitname,testplanid,subplanid,targetid,testsuiteid,duedate,totalcases,createdate,modifydate)"
				+ " values(?,?,?,?,?,?,?,?,?)";

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		int res = userJdbcTemplate.update(new PreparedStatementCreator() {

		 	@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {

				PreparedStatement ps = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				mapTestUnitToPs(ps, testUnit);

				return ps;
			}
		}, keyHolder);

		res = keyHolder.getKey().intValue();
		return res;
	}
	
	public int checkDuplicateUnitInSubPlan(TestUnit unit) throws Exception {
		int result = 0;
		String	sql = "select count(*) from testunit where subplanid = '"+unit.getSubPlanId()+"' and targetid = '"+unit.getTargetId()+"'"
				+ " and testsuiteid = '"+unit.getTestSuiteId()+"'";
		result = userJdbcTemplate.queryForInt(sql);
 
		logger.info("checkDuplicateUnitInSubPlan method result:" + result);
		return result;
	}

	public void addBatchTestUnits(final List<TestUnit> testUnitList)
			throws Exception {

		for (TestUnit testUnit : testUnitList) {

			int testUnitId = addTestUnit(testUnit);

			testUnit.setTestUnitId(testUnitId);
		}

		logger.info("success:" + testUnitList.size()
				+ " testunit(s) have been added!");

	}

	public int delBatchTestUnits(final List<TestUnit> testUnitList)
			throws Exception {

		//String sql = "delete from testunit where testunitid=?  ";
		String sql = "UPDATE `testunit` SET `STATUS`='1' WHERE testunitid=?  ";

		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						TestUnit testUnit = testUnitList.get(i);

						ps.setInt(1, testUnit.getTestUnitId());

					}

					public int getBatchSize() {

						return testUnitList.size();
					}
				});

		return result.length;
	}

	public List<TestCase> getTestCasesFromTestUnit(int testUnitId)
			throws Exception {

		String sql = "SELECT ts.*  from testcase as ts,testresult rs WHERE ts.TESTCASEID = rs.TESTCASEID and rs.TESTUNITID ="
				+ testUnitId;
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testCase = new TestCase();
						TestCaseDaoImpl.mapTestCaseFromRs(rs, testCase);
						return testCase;
					}
				});

		return testCaseList;
	}

	public List<TestCase> getAllFailedTestCasesFromTestUnit(int testUnitId)
			throws Exception {

		String sql = "SELECT ts.*  from testcase as ts,testresult rs WHERE ts.TESTCASEID = rs.TESTCASEID and rs.TESTUNITID ="
				+ testUnitId + " and rs.resulttypeid =2";
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testCase = new TestCase();
						TestCaseDaoImpl.mapTestCaseFromRs(rs, testCase);
						return testCase;
					}
				});

		return testCaseList;
	}

	public int delBatchTestUnitsByIds(final String[] testUnits)
			throws Exception {

		//String sql = "delete from testunit where testunitid=?  ";
		String sql = "UPDATE `testunit` SET `STATUS`='1' WHERE testunitid=?";

		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(testUnits[i].trim()));

					}

					public int getBatchSize() {

						return testUnits.length;
					}
				});

		return result.length;
	}

	public int delBatchTestUnits(final String[] subTestPlans) throws Exception {

		//String sql = "delete from testunit where subplanid=?  ";
		String sql = "UPDATE `testunit` SET `STATUS`='1' WHERE subplanid=?";

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

		return result.length;
	}

	public List<TestUnit> listTestUnits(int subPlanId) throws Exception {

		//String sql = "select * from testunit where subplanid='" + subPlanId + "'";
		String sql = "select a.*, b.TARGETNAME from testunit a left join target b on a.TARGETID=b.TARGETID where"
				+ " (a.status <> 1 or a.status is null) and subplanid='"+subPlanId+"'";
		@SuppressWarnings("unchecked")
		List<TestUnit> testUnitList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestUnit testUnit = new TestUnit();
						mapTestUnitFromRs(rs, testUnit);
						testUnit.setTestUnitId(rs.getInt("testunitid"));
						testUnit.setTargetName(rs.getString("targetName"));
						return testUnit;
					}
				});

		return testUnitList;
	}

	
	public List<TestUnit> listTestUnitByTestPlanId(int testPlanId) throws Exception {

		String sql = "select * from testunit where (status <> 1 or status is null) and testplanid='" + testPlanId
				+ "'";
		logger.info("listTestUnitByTestPlanId method sql" + sql);
		@SuppressWarnings("unchecked")
		List<TestUnit> testUnitList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestUnit testUnit = new TestUnit();
						mapTestUnitFromRs(rs, testUnit);

						return testUnit;
					}
				});

		return testUnitList;
	}
	
	public List<TestUnit> listTestUnitBySubPlanId(int Id) throws Exception {

		String sql = "select * from testunit where (status <> 1 or status is null) and subplanid='" + Id + "'";
		logger.info("listTestUnitBySubPlanId method sql" + sql);
		@SuppressWarnings("unchecked")
		List<TestUnit> testUnitList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestUnit testUnit = new TestUnit();
						mapTestUnitFromRs(rs, testUnit);

						return testUnit;
					}
				});

		return testUnitList;
	}

	public List<TestUnit> listFailedTestUnits(int subPlanId,String isUpdate) throws Exception {

		String sql = "select * from testunit where subplanid='"+subPlanId+"' " ;
				if(isUpdate.equals("No")){
					sql+="and fail !=0";
				}
				

		@SuppressWarnings("unchecked")
		List<TestUnit> testUnitList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestUnit testUnit = new TestUnit();
						mapTestUnitFromRs(rs, testUnit);

						return testUnit;
					}
				});

		return testUnitList;
	}
	public TestUnit queryTestUnit(int testUnitId) throws Exception {

		String sql = "select * from testunit where testunitid ='" + testUnitId
				+ "'";
		final TestUnit testUnit = new TestUnit();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapTestUnitFromRs(rs, testUnit);
			}
		});

		return testUnit;
	}

	public TestUnit queryTestUnit(String testUnitName, int testplanId)
			throws Exception {

		String sql = "select * from testunit where testunitname ='"
				+ testUnitName + "'" + " and testplanid =" + testplanId;
		final TestUnit testUnit = new TestUnit();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapTestUnitFromRs(rs, testUnit);
			}
		});

		return testUnit;
	}

	public boolean isUniqueTestUnit(int subPlanId, int testUnitId, String unitName)throws Exception {
		
		
		String sql = "select count(*)  from testunit where testunitname ='"
			+ unitName + "'" + " and subplanid =" + subPlanId+" and testunitid !="+testUnitId;
		logger.info("sql:"+sql);
		int result = userJdbcTemplate.queryForInt(sql);
		if(result>0){
			
			return false;
		}
		
		return true;
	}
	
	
	
	//@SuppressWarnings("unchecked")
	/*public TestUnit queryTestUnitAfterTestResultChanged(final TestUnit testUnit)
			throws Exception {
		testUnit.setPass(0);
		testUnit.setFail(0);
		testUnit.setNotRun(0);
		testUnit.setBlock(0);
		String sql = "select resulttypeid,count(resulttypeid) as count from testresult where testunitid='"
				+ testUnit.getTestUnitId() + "'" + " group by resulttypeid";
		logger.info("queryPlanSuiteMapAfterUpated method sql:" + sql);

		userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int i) throws SQLException {
				int result = rs.getInt("resulttypeid");
				if (result == Constant.RESULT_PASS) {
					testUnit.setPass(rs.getInt("count"));

				} else if (result == Constant.RESULT_FAIL) {
					testUnit.setFail(rs.getInt("count"));

				} else if (result == Constant.RESULT_NOTRUN) {
					testUnit.setNotRun(rs.getInt("count"));

				} else if (result == Constant.RESULT_BLOCK) {
					testUnit.setBlock(rs.getInt("count"));
				}

				return result;
			}
		});

		int pass = testUnit.getPass();
		int totalCases = testUnit.getTotalCases();
		float passRate = (float) pass / totalCases;
		passRate = new BigDecimal(Float.valueOf(passRate) * 100).setScale(2,
				BigDecimal.ROUND_HALF_DOWN).floatValue();
		testUnit.setPassRate(passRate);
		return testUnit;
	}
	*/
       
	public int updateBatchTestUnit(final List<TestUnit> testUnitList)
			throws Exception {

		String sql = "update testunit set testunitname=?,testplanid=?,subplanid=?,targetid=?,testsuiteid=?,duedate=?,totalcases=?,createdate=?,modifydate=?"
				+ " where testunitid=? ";

		int[] res = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						TestUnit testUnit = testUnitList.get(i);

						mapTestUnitToPs(ps, testUnit);
						ps.setInt(10, testUnit.getTestUnitId());

					}

					public int getBatchSize() {

						return testUnitList.size();
					}
				});

		return res.length;

	}

	public int updateTestUnit(final TestUnit testUnit) throws Exception {
		String sql = "update testunit set testunitname=?,testplanid=?,subplanid=?,targetid=?,testsuiteid=?,duedate=?,totalcases=?,createdate=?,modifydate=?"
				+ " where testunitid=" + testUnit.getTestUnitId();

		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapTestUnitToPs(ps, testUnit);

			}
		});

		return res;
	}
	public static void mapTestUnitToPs(PreparedStatement ps, TestUnit testUnit)
			throws SQLException {

		if (Utils.isNullORWhiteSpace(testUnit.getTestUnitName())) {
			ps.setString(1, null);
		} else {
			ps.setString(1, testUnit.getTestUnitName());
		}

		
		if (testUnit.getTestPlanId() == 0) {

			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testUnit.getTestPlanId());
		}

		if (testUnit.getSubPlanId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testUnit.getSubPlanId());
		}

		if (testUnit.getTargetId()== 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, testUnit.getTargetId());
		}

		if (testUnit.getTestSuiteId() == 0) {
			ps.setInt(5, Types.NULL);
		} else {
			ps.setInt(5, testUnit.getTestSuiteId());
		}

		if (Utils.isNullORWhiteSpace(testUnit.getDueDate())) {

			ps.setString(6, null);
		} else {

			ps.setString(6, testUnit.getDueDate());
		}
		if (testUnit.getTotalCases() == 0) {
			ps.setInt(7, Types.NULL);
		} else {
			ps.setInt(7, testUnit.getTotalCases());
		}

		
		if (Utils.isNullORWhiteSpace(testUnit.getCreateDate())) {
			ps.setString(8, null);
		} else {
			ps.setString(8, testUnit.getCreateDate());
		}

		if (Utils.isNullORWhiteSpace(testUnit.getModifyDate())) {

			ps.setString(9, null);
		} else {

			ps.setString(9, testUnit.getModifyDate());
		}

		

		
		
	}

	public static void mapTestUnitFromRs(ResultSet rs, TestUnit testUnit)
			throws SQLException {
		testUnit.setTestUnitId(rs.getInt("testunitid"));
		testUnit.setTestUnitName(rs.getString("testunitname"));		
		testUnit.setTestPlanId(rs.getInt("testplanid"));
		testUnit.setSubPlanId(rs.getInt("subplanid"));
		testUnit.setTargetId(rs.getInt("targetId"));		
		testUnit.setTestSuiteId(rs.getInt("testsuiteid"));		
		testUnit.setTotalCases(rs.getInt("totalcases"));
		testUnit.setCreateDate(rs.getString("createdate"));
		testUnit.setModifyDate(rs.getString("modifydate"));	
		testUnit.setDueDate(rs.getString("duedate"));
		testUnit.setTargetId(rs.getInt("targetid"));
		testUnit.setTestSuiteId(rs.getInt("testsuiteid"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
