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

import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.dao.TestPlanDao;
import com.intel.cid.utils.Utils;

public class TestPlanDaoImpl implements TestPlanDao {

	private static Logger logger = Logger.getLogger(TestPlanDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addTestPlan(final TestPlan testPlan) throws Exception {

		final String sql = "insert into testplan(projectId,owner,totalcases,phaseId,planName,description,createDate,modifydate,startDate,endDate)"
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		logger.info("addTestPlan method sql:" + sql);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		userJdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapTestPlanToPs(testPlan, ps);
				return ps;
			}
		}, keyHolder);
		int result = keyHolder.getKey().intValue();
		logger.info("addTestPlan method result:" + result);
		return result;
	}

	public int delTestPlan(TestPlan testPlan) throws Exception {

		return delTestPlanById(testPlan.getTestPlanId());
	}

	public int delBatchTestPlan(final String[] testplans) throws Exception {
		//String sql = "delete from testplan where testplanid=?";
		String sql = "UPDATE `testplan` SET `STATUS`='1' WHERE testplanid=?";
		logger.info("delTestPlanByStringArray method sql" + sql);
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

		logger.info("delTestPlanByStringArray method result:" + result.length);
		return result.length;
	}

	public int delTestPlanById(int planId) throws Exception {

		String sql = "delete from testplan where testplanid='" + planId + "'";
		logger.info("delTestPlanById method sql:" + sql);

		int result = userJdbcTemplate.update(sql);
		logger.info("delTestPlanById method  result:" + result);
		return result;

	}

	public TestPlan queryTestPlan(int testPlanId) throws Exception {

		String sql = "select * from testplan where testplanid='" + testPlanId
				+ "'";
		logger.info("queryTestPlanById method sql :" + sql);
		final TestPlan testPlan = new TestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {

				mapTestPlanFromRs(testPlan, rs);

			}
		});

		logger.info("queryTestPlanById method result:" + testPlan.logInfo());
		return testPlan;
	}

	public TestPlan queryTestPlan(String phaseId) throws Exception {

		String sql = "select * from testplan where phaseId='" + phaseId + "'";
		logger.info("queryTestPlanById method sql :" + sql);
		final TestPlan testPlan = new TestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {

				mapTestPlanFromRs(testPlan, rs);

			}
		});

		logger.info("queryTestPlanById method result:" + testPlan.logInfo());
		return testPlan;
	}

	public TestPlan queryTestPlan(String phaseId, int projectId)
			throws Exception {

		String sql = "select * from testplan where phaseId='" + phaseId
				+ "' and projectId='" + projectId + "'";
		logger.info("queryTestPlanById method sql :" + sql);
		final TestPlan testPlan = new TestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {

				mapTestPlanFromRs(testPlan, rs);
			}
		});

		logger.info("queryTestPlanById method result:" + testPlan.logInfo());
		return testPlan;
	}

	public int updateTestPlan(final TestPlan testPlan) throws Exception {

		String sql = "update testplan  set projectId=?,owner=?,totalcases=?,phaseId=?,planName=?,description=?,createdate=?,modifydate=?,startdate=?,enddate=?"
				+ " where testplanid='" + testPlan.getTestPlanId() + "'";
		logger.info("updateTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						mapTestPlanToPs(testPlan, ps);

					}
				});
		return result;

	}

	/***
	 * check one testplan is unique and return true.or return false.
	 * 
	 * @param testPlan
	 * @return
	 * @throws Exception
	 */
	public boolean isUniqueTestPlan(final TestPlan testPlan) throws Exception {

		String sql = "select count(*) from testplan where (status <> 1 or status is null) and projectid="
				+ testPlan.getProjectId() + " and planname='"
				+ testPlan.getPlanName() + "' and testplanid !="
				+ testPlan.getTestPlanId();
		logger.info("isUniqueTestPlan method result:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		if (result > 0) {
			return false;
		}
		return true;
	}

	public int queryByPlanNameSize(TestPlan testPlan)
			throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != testPlan.getTestPlanId()) {
			sql = "select count(*) from testplan where (status <> 1 or status is null) and planName  ='"
					+ testPlan.getPlanName() + "'  and testPlanId!='"
					+ testPlan.getTestPlanId() + "' ";
			logger.info("queryByPlanNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		

		} else {
			sql = "select count(*) from testplan where (status <> 1 or status is null) and planName ='"
					+ testPlan.getPlanName() + "'  ";
			logger.info("queryByPlanNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			
		}

		logger.info("queryByPlanCycleSize method result:" + result);
		return size;
	}

	
	/**
	 * update totalcases field in testplan
	 * 
	 * @param testPlan
	 * @throws Exception
	 */
	public void refreshTestPlan(final TestPlan testPlan) throws Exception {

		String sql = "select sum(totalcases) as totalcases from subtestplan where (status <> 1 or status is null) and exists( select * from "
				+ " planmap where subtestplan.subplanid = planmap.subplanid"
				+ " and planmap.testplanid = " + testPlan.getTestPlanId() + ")";
		logger.info("sql:" + sql);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				testPlan.setTotalCases(rs.getInt("totalcases"));

			}
		});

		updateTestPlan(testPlan);
	}

	public List<TestPlan> listTestPlanByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		String userId = filterMap.get("userId");

		String sql = "select * from testplan t where (status <> 1 or status is null) and EXISTS (SELECT * from user_team as map left join project  "
				+ "pro on pro.teamid=map.TEAMID WHERE (map.status <> 1 or map.status is null) and pro.projectid = t.projectid and map.USERID='"+userId+"')";
		sql = filterQueryTestPlanCondition(sql, filterMap);
		sql = sql + " ORDER BY STARTDATE DESC ";
		sql = sql + "limit " + itemFrom + "," + count;
		logger.info("queryTestPlanByPage method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestPlan> testPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestPlan testPlan = new TestPlan();
						mapTestPlanFromRs(testPlan, rs);
						return testPlan;
					}
				});
		return testPlanList;
	}

	public int queryTestPlanSize(Map<String, String> filterMap)
			throws Exception {
		String userId = filterMap.get("userId");
		String sql = "select count(*) from testplan t where (status <> 1 or status is null) and EXISTS (SELECT * from user_team as map left join project  "
				+ "pro on pro.teamid=map.TEAMID WHERE (map.status <> 1 or map.status is null) and pro.projectid = t.projectid and map.USERID='"+userId+"')";
		sql = filterQueryTestPlanCondition(sql, filterMap);

		logger.info("queryTestPlanSize method sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("queryTestPlanSize method result:" + result);
		return result;
	}

	public List<TestPlan> listTestPlans(Map<String, String> filterMap)
			throws Exception {

		String sql = "select * from  testplan ";
		sql = filterQueryTestPlanCondition(sql, filterMap);
		@SuppressWarnings("unchecked")
		List<TestPlan> testPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestPlan testPlan = new TestPlan();
						mapTestPlanFromRs(testPlan, rs);
						return testPlan;
					}
				});
		return testPlanList;

	}

	public List<TestPlan> listAllTestPlans(int userId) throws Exception {
		String sql = "";
		if (userId == 0) {
			sql = "select * from  testplan ";
		} else {
			sql = "select * from testplan t where  projectid in( SELECT a.projectid FROM project a, user_team b where "
					+ "a.teamid=b.teamid and b.userid='" + userId + "')";
		}

		@SuppressWarnings("unchecked")
		List<TestPlan> testPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestPlan testPlan = new TestPlan();
						mapTestPlanFromRs(testPlan, rs);
						return testPlan;
					}
				});
		return testPlanList;

	}

	public String filterQueryTestPlanCondition(String sql,
			Map<String, String> filterMap) {

		int projectId = Integer.parseInt(filterMap.get("projectId"));
		if (projectId != 0) {
			sql += " and projectId = '" + projectId + "'";
		}

		int phaseId = Integer.parseInt(filterMap.get("phaseId"));
		if (phaseId != 0) {
			sql += " and phaseId = '" + phaseId + "'";
		}

		String planName = filterMap.get("planName");
		if (!Utils.isNullORWhiteSpace(planName)) {
			sql += " and planName like '%" + planName + "%'";
		}

		return sql;
	}

	public TestPlan queryTestPlanBySubPlan(int subPlanId) throws Exception {

		String sql = "select distinct map.testplanid ,testplan.*  from planmap as map  left join testplan on map.testplanid = testplan.testplanid "
				+ "where map.subplanid ='" + subPlanId + "'";
		;
		logger.info("queryTestPlan method sql:" + sql);
		final TestPlan testPlan = new TestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				TestPlanDaoImpl.mapTestPlanFromRs(testPlan, rs);

			}
		});
		logger.info("queryTestPlan method result:" + testPlan.logInfo());
		return testPlan;
	}

	public List<?> queryTestPlanByProject(int projectId) {

		String sql = "select * from testplan where testplan.projectId='"
				+ projectId + "'";
		logger.info("sql:" + sql);
		return userJdbcTemplate.queryForList(sql);

	}
	
	public  List<TestPlan> ListTestPlanByTestSuiteId(int suiteId) throws Exception{
		
	String sql ="SELECT * FROM testplan WHERE (status <> 1 or status is null) and EXISTS (SELECT * from testunit WHERE testplan.TESTPLANID =" +
			" testunit.TESTPLANID AND testunit.TESTSUITEID = "+suiteId+" )";
	
	@SuppressWarnings("unchecked")
	List<TestPlan> testPlanList = userJdbcTemplate.query(sql,
			new RowMapper() {
				public Object mapRow(ResultSet rs, int i)
						throws SQLException {

					TestPlan testPlan = new TestPlan();
					mapTestPlanFromRs(testPlan, rs);
					return testPlan;
				}
			});
	return testPlanList;
	
	
	}
	public int queryTestPlanSize(int projectId) {
		String sql = "select count(*) "
				+ " from testplan tp "
				+ " left join planmap pm on tp.testplanid = pm.testplanid "
				+ " left join subtestplan stp on tp.projectid = stp.projectid and pm.subplanid = stp.subplanid "
				+ " left join phase p on tp.phaseid = p.phaseid and tp.projectid = p.projectid "
				+ " left join testunit tu on tp.testplanid = tu.testplanid and stp.subplanid = tu. subplanid "
				+ " left join target t on stp.projectid = t.projectid and tu.targetid = t.targetid "
				+ " where tp.projectid = " + projectId
				+ " and (tp.status <> 1 or tp.status is null) "
				+ " and (stp.status <> 1 or stp.status is null) "
				+ " and (pm.status <> 1 or pm.status is null) "
				+ " and (p.status <> 1 or p.status is null) "
				+ " and (tu.status <> 1 or tu.status is null) "
				+ " and (t.status <> 1 or t.status is null) "
				;
		logger.info("sql:" + sql);
		
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("result:" + result);
		return result;

	}

	public List<?> listTestPlanInfoByProjectId(int projectId,PageBean pageBean) {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = "select tp.projectid, tp.testplanid, tp.phaseid, stp.subplanid, tp.planname, p.phasename, stp.subplanname, t.targetname "
				+ " from testplan tp "
				+ " left join planmap pm on tp.testplanid = pm.testplanid "
				+ " left join subtestplan stp on tp.projectid = stp.projectid and pm.subplanid = stp.subplanid "
				+ " left join phase p on tp.phaseid = p.phaseid and tp.projectid = p.projectid "
				+ " left join testunit tu on tp.testplanid = tu.testplanid and stp.subplanid = tu. subplanid "
				+ " left join target t on stp.projectid = t.projectid and tu.targetid = t.targetid "
				+ " where tp.projectid = " + projectId 
				+ " and (tp.status <> 1 or tp.status is null) "
				+ " and (stp.status <> 1 or stp.status is null) "
				+ " and (pm.status <> 1 or pm.status is null) "
				+ " and (p.status <> 1 or p.status is null) "
				+ " and (tu.status <> 1 or tu.status is null) "
				+ " and (t.status <> 1 or t.status is null) "
				+ " order by tp.testplanid ";
		sql = sql + " limit " + itemFrom + "," + count;
		logger.info("sql:" + sql);
		return userJdbcTemplate.queryForList(sql);
		
	}
	
	public List<?> getTestPlanCountByPhaseList(int projectId) {

		String sql = "select temp.phasename, count(temp.phaseid) as phasecount from "
				+ " (select distinct tp.projectid, tp.testplanid, tp.phaseid, p.phasename "
				+ " from testplan tp "
				+ " left join phase p on  tp.phaseid = p.phaseid and tp.projectid = p.projectid  "
				+ " where  "
				+ " tp.projectid = " + projectId 
				+ " and (tp.status <> 1 or tp.status is null)  "
				+ " and (p.status <> 1 or p.status is null) "
				+ " ) temp "
				+ " group by temp.phasename ";

		logger.info("sql:" + sql);
		return userJdbcTemplate.queryForList(sql);
		
	}
	
	public List<?> getCaseNumberByTestPlanList(int projectId) {

		String sql = " select temp.planname, sum(temp.pass) as passNumber, sum(temp.fail) as failNumber, sum(temp.notrun) as notrunNumber, sum(temp.block) as blockNumber "
				+ " from "
				+ " (select tp.testplanid,tp.planname,te.executionname,te.pass,te.fail,te.notrun,te.block  "
				+ " from testplan tp "
				+ " inner join testexecution te on tp.testplanid = te.testplanid  "
				+ " left join project p on tp.projectid=p.projectid  "
				+ " left join user u on tp.owner=u.userid  "
				+ " left join executionphase ep on te.phaseid=ep.phaseid  "
				+ " left join build b on te.buildid=b.buildid  "
				+ " where tp.projectid = " + projectId 
				+ " and (tp.status <> 1 or tp.status is null)  "
				+ " ) temp  "
				+ " group by temp.testplanid "; 

		logger.info("sql:" + sql);
		return userJdbcTemplate.queryForList(sql);
		
	}
	
	public static void mapTestPlanToPs(final TestPlan testPlan,
			PreparedStatement ps) throws SQLException {
		if (testPlan.getProjectId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, testPlan.getProjectId());
		}

		if (testPlan.getOwner() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testPlan.getOwner());
		}
		if (testPlan.getTotalCases() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testPlan.getTotalCases());
		}

		if (testPlan.getPhaseId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, testPlan.getPhaseId());
		}

		if (Utils.isNullORWhiteSpace(testPlan.getPlanName())) {
			ps.setString(5, null);
		} else {
			ps.setString(5, testPlan.getPlanName());
		}

		if (Utils.isNullORWhiteSpace(testPlan.getDescription())) {
			ps.setString(6, null);
		} else {
			ps.setString(6, testPlan.getDescription());
		}

		if (Utils.isNullORWhiteSpace(testPlan.getCreateDate())) {
			ps.setString(7, null);
		} else {
			ps.setString(7, testPlan.getCreateDate());

		}
		if (Utils.isNullORWhiteSpace(testPlan.getModifyDate())) {
			ps.setString(8, null);
		} else {
			ps.setString(8, testPlan.getModifyDate());
		}
		if (Utils.isNullORWhiteSpace(testPlan.getStartDate())) {
			ps.setString(9, null);
		} else {
			ps.setString(9, testPlan.getStartDate());
		}
		if (Utils.isNullORWhiteSpace(testPlan.getEndDate())) {
			ps.setString(10, null);
		} else {
			ps.setString(10, testPlan.getEndDate());
		}

	}

	public static void mapTestPlanFromRs(TestPlan testPlan, ResultSet rs)
			throws SQLException {
		testPlan.setTestPlanId(rs.getInt("testplanid"));
		testPlan.setPlanName(rs.getString("planName"));
		testPlan.setTotalCases(rs.getInt("totalCases"));
		testPlan.setCreateDate(rs.getString("createdate"));
		testPlan.setModifyDate(rs.getString("modifydate"));
		testPlan.setStartDate(rs.getString("startdate"));
		testPlan.setEndDate(rs.getString("enddate"));
		testPlan.setDescription(rs.getString("description"));
		testPlan.setOwner(rs.getInt("owner"));
		testPlan.setProjectId(rs.getInt("projectId"));
		testPlan.setPhaseId(rs.getInt("phaseId"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
