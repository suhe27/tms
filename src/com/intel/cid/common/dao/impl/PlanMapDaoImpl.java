package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.python.antlr.PythonParser.else_clause_return;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.PlanMap;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.dao.PlanMapDao;

public class PlanMapDaoImpl implements PlanMapDao {

	private static Logger logger = Logger.getLogger(PlanMapDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addSubTestPlanMap(final PlanMap planMap) throws Exception {

		String sql = "insert into planmap(subplanid,testplanid) values(?,?) ";
		logger.info("addSubTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapPlanMapToPs(planMap, ps);
					}
				});
		logger.info("addSubTestPlan method result:" + result);
		return result;
	}

	public int delBatchTestPlan(final String[] testplans) throws Exception {

		//String sql = "delete from planmap where testplanid=?";
		String sql = "UPDATE `planmap` SET `STATUS`='1' WHERE testplanid=?";
		logger.info("delBatchTestPlan method sql" + sql);
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

		logger.info("delBatchTestPlan method result:" + result.length);
		return result.length;
	}

	public int delBatchSubTestPlan(final String[] subTestplans)
			throws Exception {

		//String sql = "delete from planmap where subplanid=?";
		String sql = "UPDATE `planmap` SET `STATUS`='1' WHERE subplanid=?";
		logger.info("delBatchSubTestPlan method sql" + sql);
		int result[] = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(subTestplans[i].trim()));
					}

					public int getBatchSize() {

						return subTestplans.length;
					}
				});

		logger.info("delBatchSubTestPlan method result:" + result.length);
		return result.length;
	}

	public int delSubTestPlan(int subPlanId, int testPlanId) throws Exception {

		String sql = "delete from planmap where subplanid ='" + subPlanId
				+ " and testplanid='" + testPlanId + "'";
		logger.info("delSubTestPlan method sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delSubTestPlan method  result:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SubTestPlan> listSubTestPlan(int testPlanId) throws Exception {

		//String sql = "select subtestplan.* from planmap left join subtestplan on planmap.subplanid = subtestplan.subplanid where planmap.testplanid ='" + testPlanId + "'";
		String sql = "select b.* from planmap a left join subtestplan b on a.subplanid = b.subplanid where"
				+ " (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and a.testplanid ='" + testPlanId + "'";
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

	public boolean isUniqueSubTestPlan(int testPlanId, int subPlanId,
			String subPlanName) throws Exception {

		String sql = "select count(*) from planmap left join subtestplan on planmap.subplanid = subtestplan.subplanid"
				+ " where planmap.testplanid ='"
				+ testPlanId
				+ "' and subtestplan.subplanname='"
				+ subPlanName
				+ "'"
				+ " and subtestplan.subplanid !=" + subPlanId;

		int result = userJdbcTemplate.queryForInt(sql);
		if (result > 0) {
			return false;
		} else {

			return true;
		}

	}
	
	public int querySubPlanSize(SubTestPlan subTestPlan) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != subTestPlan.getSubPlanId()) {
			sql = "select count(*) from planmap a left join subtestplan b on a.subplanid = b.subplanid where "
					+ "(a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and "
					+ "a.testplanid = '"+subTestPlan.getTestPlanId()+"' and b.subplanname = '"+subTestPlan.getSubPlanName()+"'"
					+ " and b.subplanid != '"+subTestPlan.getSubPlanId()+"'";
			logger.info("queryBySubTestPlanNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from planmap a left join subtestplan b on a.subplanid = b.subplanid where "
					+ "(a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and "
					+ "a.testplanid = '"+subTestPlan.getTestPlanId()+"' and b.subplanname = '"+subTestPlan.getSubPlanName()+"'";
			logger.info("queryBySubTestPlanNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryBySubTestPlanNameSize method result:" + result);
		return size;
	}

	public int querySuiteInSubPlanSize(SubTestPlan subTestPlan) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		
		sql = "select count(*) from subtestplan a left join testunit b on a.SUBPLANID=b.SUBPLANID where (a.status <> 1 or a.status is null) and"
				+ " (b.status <> 1 or b.status is null) and b.TESTPLANID = "
				+ "'"+subTestPlan.getTestPlanId()+"' and b.testsuiteid = '"+subTestPlan.getTestSuiteId()+"'";
		
		logger.info("querySuiteInSubPlanSize method result:" + sql);
		result = userJdbcTemplate.queryForInt(sql);
		if(result!=0){
			size = 1;
		}	
		
		logger.info("querySuiteInSubPlanSize method result:" + result);
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubTestPlan> listSubTestPlanByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {
		String testPlanId = filterMap.get("testPlanId");
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - pageBean.getItemFrom() + 1;
		String sql = "select b.*,d.TESTSUITENAME,e.USERNAME,f.PROJECTNAME from planmap a left join subtestplan b "
				+ "on a.subplanid = b.subplanid left join testunit c on c.SUBPLANID=b.SUBPLANID left join testsuite d "
				+ "on d.TESTSUITEID=c.TESTSUITEID left join user e on e.USERID=b.`owner` left join project f "
				+ "on f.projectid=b.PROJECTID where (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) "
				+ "and a.testplanid ='"+testPlanId+"' group by b.subplanid"
				+ " limit "
				+ itemFrom + "," + count;
		logger.info("listSubTestPlanByPage method sql:" + sql);
		List<SubTestPlan> subTestPlanList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						SubTestPlan subTestPlan = new SubTestPlan();
						SubTestPlanDaoImpl.mapSubTestPlanFromRs(subTestPlan, rs);
						subTestPlan.setDueDate(rs.getString("duedate"));	
						subTestPlan.setUserName(rs.getString("username"));
						subTestPlan.setProjectName(rs.getString("projectName"));
						subTestPlan.setSuiteName(rs.getString("testSuiteName"));
						return subTestPlan;
					}
				});

		logger.info("listSubTestPlanByPage method records:");

		return subTestPlanList;
	}

	public int querySubTestPlanSize(Map<String, String> filterMap)
			throws Exception {
		String testplanid = filterMap.get("testPlanId");
		String sql = "select count(*) from planmap where testplanid ='"
				+ testplanid + "'";
		logger.info("querySubTestPlanSize method sql:" + sql);
		int res = userJdbcTemplate.queryForInt(sql);
		logger.info("querySubTestPlanSize method result:" + res);
		return res;
	}

	public TestPlan queryTestPlan(int subPlanId) throws Exception {

		String sql = "select distinct map.testplanid ,testplan.*  from planmap as map  left join testplan on map.testplanid = testplan.testplanid "
				+ "where planmap.subplanid ='" + subPlanId + "'";
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

	public SubTestPlan querySubTestPlan(int subPlanId) throws Exception {

		String sql = "select distinct map.testplanid ,testplan.*  from planmap as map  left join testplan on map.testplanid = testplan.testplanid "
				+ "where planmap.subplanid ='" + subPlanId + "'";
		;
		logger.info("queryTestPlan method sql:" + sql);
		final TestPlan testPlan = new TestPlan();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				TestPlanDaoImpl.mapTestPlanFromRs(testPlan, rs);

			}
		});
		logger.info("queryTestPlan method result:" + testPlan.logInfo());
		return null;
	}

	public void mapPlanMapToPs(final PlanMap planMap, PreparedStatement ps)
			throws SQLException {

		if (planMap.getSubPlanId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, planMap.getSubPlanId());
		}
		if (planMap.getTestPlanId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, planMap.getTestPlanId());
		}

	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
