package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.dao.TestCaseDao;
import com.intel.cid.utils.Utils;

public class TestCaseDaoImpl implements TestCaseDao {

	private static Logger logger = Logger.getLogger(TestCaseDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addTestCase(final TestCase testcase) throws Exception {

		String sql = "insert into testcase(projectId,teamid,osid,featureid,"
				+ "compid,testtypeid,userid,testcasealiasid"
				+ ",testcasename,requirementid,configfiles"
				+ ",executionsteps,expectedresult,autoid,testscript,"
				+ "testfunctioncall,packetsizerange,timeout,subcompid,versionid,"
				+ "casestateid,description,createdate,modifydate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTestCaseToPs(ps, testcase);

					}

				});
		logger.info("result:" + result);

		return result;
	}

	public int delTestCase(TestCase testcase) throws Exception {

		return delTestCaseById(testcase.getTestCaseId());
	}

	public int delTestCaseById(int id) throws Exception {
		String sql = "delete from testcase where testcaseid ='" + id + "'";
		logger.info("delTestCaseById sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delTestCaseById result:" + result);
		return result;
	}

	public int delBatchTestCases(final String[] testcases) throws Exception {
		//String sql = "delete from testcase where testcaseid =?";
		String sql = "UPDATE `testcase` SET `STATUS`='1' WHERE testcaseId=?";
		logger.info("delBatchTestCase method sql:" + sql);
		int[] result = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(testcases[i].trim()));

					}

					public int getBatchSize() {

						return testcases.length;
					}
				});
		logger.info("delBatchTestCase method result:" + result.length);

		return result.length;
	}

	public List<TestCase> queryAllTestCases() throws Exception {

		//String sql = " select * from testcase";
		String sql = " select * from testcase where (status <> 1 or status is null)";
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testcaseList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						TestCase testcase = new TestCase();
						mapTestCaseFromRs(rs, testcase);
						return testcase;
					}

				});

		logger.info("result:" + testcaseList);
		return testcaseList;
	}

	public TestCase queryTestCaseById(int id) throws Exception {

		String sql = "SELECT ts.*, a.FEATURENAME, b.COMPNAME, c.TESTTYPENAME, d.OSNAME, e.AUTONAME,"
				+ " f.CASESTATENAME, g.VERSIONNAME, h.PROJECTNAME, i.SUBCOMPNAME, j.USERNAME, k.TEAMNAME from "
				+ "testcase as ts left join feature a on a.FEATUREID = ts.FEATUREID left join component b on b.COMPID =ts.COMPID "
				+ "left join testtype c on c.TESTTYPEID = ts.TESTTYPEID left join os d on d.osid = ts.osid "
				+ "left join automation e on e.AUTOID = ts.AUTOID left join casestate f on f.CASESTATEID =ts.CASESTATEID"
				+ " left join caseversion g on g.VERSIONID = ts.VERSIONID left join project h on h.PROJECTID = ts.PROJECTID"
				+ " left join subcomponent i on i.SUBCOMPID =ts.SUBCOMPID left join user j on j.USERID = ts.USERID left join team k on k.TEAMID =ts.TEAMID "
				+ "WHERE testcaseid='" + id + "'";
		
		logger.info("sql:" + sql);
		final TestCase testcase = new TestCase();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTestCaseAllFromRs(rs, testcase);

			}
		});

		logger.info("result:" + testcase);
		return testcase;
	}

	public TestCase queryTestCase(String testCaseAliasId,String project) throws Exception {

		String sql = "select * from testcase where testcasealiasid='"
				+ testCaseAliasId + "'" +" and project='"+project+"'";
		logger.info("sql:" + sql);
		final TestCase testcase = new TestCase();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTestCaseFromRs(rs, testcase);

			}
		});

		logger.info("result:" + testcase);
		return testcase;
	}

	public int updateTestCase(final TestCase testcase) throws Exception {
		String sql = "update testcase set projectId=?, teamid=?, osid=?, featureid=?,"
				+ " compid=? ,testtypeid=? , userid=? , testcasealiasid=?"
				+ ",testcasename=?, requirementid=? , configfiles=?"
				+ ", executionsteps=?, expectedresult=?, autoid=? ,testscript=?,"
				+ "testfunctioncall=? , packetsizerange=?, timeout=?, subcompid=?,versionid=?,"
				+ "casestateid=?, description=?, modifydate=? where testcaseid='"
				+ testcase.getTestCaseId() + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTestCaseToUpdatePs(ps, testcase);

					}
				});

		logger.info("result:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<VersionState> queryAllVersions() throws Exception {

		String sql = "select * from caseversion";
		logger.info("queryAllVersions method sql:" + sql);
		List<VersionState> versionList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						VersionState version = new VersionState();

						mapVersionFromRs(rs, version);

						return version;
					}
				});

		return versionList;
	}

	@SuppressWarnings("unchecked")
	public List<CaseState> queryAllCaseStates() throws Exception {

		String sql = "select * from  casestate";
		List<CaseState> stateList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						CaseState state = new CaseState();

						mapCaseStateFromRs(rs, state);
						return state;
					}
				});

		return stateList;

	}

	public VersionState queryVersionState(int versionId) throws Exception {
		String sql = "select * from caseversion where versionid=" + versionId;
		final VersionState version = new VersionState();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapVersionFromRs(rs, version);

			}
		});

		return version;

	}
	
	public VersionState queryVersionStateByNameExcelImport(String name) throws Exception {
		String sql = "select * from caseversion where (status <> 1 or status is null) and versionname='"+ name +"'";
		final VersionState version = new VersionState();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapVersionFromRs(rs, version);

			}
		});

		return version;

	}
	
	/**
	 * Test case import by Excel function: check if input project/feature/component value in excel is matched and get team Id in the same time.
	 * @param project
	 * @param feature
	 * @param component
	 * @return
	 * @throws Exception
	 */
	
	public TestCase verifyProjectFeatureComponentExcelImport(String project, String feature, String component) throws Exception {
		String sql = "select a.COMPID, c.FEATUREID, d.PROJECTID, e.TEAMID from component a left join component_feature b on a.COMPID = b.COMPID "
				+ "left join feature c on c.FEATUREID = b.FEATUREID left join project d on c.PROJECTID = d.PROJECTID left join team e "
				+ "on e.TEAMID = d.TEAMID where a.COMPNAME = '"+component+"' and c.featurename = '"+feature+"' and d.projectname = '"+project+"' "
				+ "and (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and (c.status <> 1 or c.status is null)"
				+ " and (d.status <> 1 or d.status is null)";
		logger.info("verifyProjectFeatureComponentExcelImport method sql:" + sql);
		final TestCase testcase = new TestCase();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				testcase.setTeamId(rs.getInt("teamId"));
				testcase.setFeatureId(rs.getInt("featureid"));
				testcase.setCompId(rs.getInt("compid"));
				testcase.setProjectId(rs.getInt("projectId"));
			}
		});
		return testcase;
	}
	
	/**
	 * Test case import by Excel function: check if input subcomponent/component value in excel is matched.
	 * @param project
	 * @param feature
	 * @param component
	 * @return
	 * @throws Exception
	 */
	
	public TestCase verifySubCompAndComponentExcelImport(String subcomp, String component) throws Exception {
		String sql = "select a.SUBCOMPID from subcomponent a left join component_subcomponent b on a.SUBCOMPID = b.SUBCOMPID left join component c"
				+ " on b.COMPID = c.COMPID where a.SUBCOMPNAME = '"+subcomp+"' and c.compname = '"+component+"'  and "
				+ "(a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and (c.status <> 1 or c.status is null)";
		logger.info("verifySubCompAndComponentExcelImport method sql:" + sql);
		final TestCase testcase = new TestCase();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				testcase.setSubCompId(rs.getInt("subCompId"));
			}
		});
		return testcase;
	}

	public CaseState queryCaseState(int caseStateId) throws Exception {
		String sql = "select * from  casestate where casestateid="
				+ caseStateId;
		final CaseState caseState = new CaseState();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapCaseStateFromRs(rs, caseState);
			}
		});
		return caseState;
	}

	public CaseState queryCaseStateByNameExcelImport(String name) throws Exception {
		String sql = "select * from  casestate where (status <> 1 or status is null) and casestatename= '"+ name +"'";
		final CaseState caseState = new CaseState();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapCaseStateFromRs(rs, caseState);
			}
		});
		return caseState;
	}
	
	public List<TestCase> queryTestCasesByFilter(Map<String, String> filterMap) {

		String userId = filterMap.get("userId");

		String sql = "SELECT * from testcase as ts  WHERE EXISTS (SELECT * from user_team as map WHERE map.TEAMID = ts.TEAMID and map.USERID="
				+ userId + ")" ;
		
		sql = filterQueryTestCaseCondition(sql, filterMap);
		// append other sort condition
		sql = sql + " ORDER BY  ts.TESTCASEALIASID  ";
		logger.info("queryTestCasesByFilter method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						TestCase testCase = new TestCase();
						mapTestCaseFromRs(rs, testCase);
						return testCase;

					}
				});
		logger.info("queryTestCasesByFilter method result:"
				+ testCaseList.size());
		return testCaseList;

	}
	
	public List<TestCase> queryTestCasesByCondition(Map<String, String> filterMap) {
		String caseName = filterMap.get("caseName");
		String sql = "SELECT ts.*, a.FEATURENAME, b.COMPNAME, c.TESTTYPENAME, d.OSNAME, e.AUTONAME,"
				+ " f.CASESTATENAME, g.VERSIONNAME, h.PROJECTNAME, i.SUBCOMPNAME, j.USERNAME, k.TEAMNAME from "
				+ "testcase as ts left join feature a on a.FEATUREID = ts.FEATUREID left join component b on b.COMPID =ts.COMPID "
				+ "left join testtype c on c.TESTTYPEID = ts.TESTTYPEID left join os d on d.osid = ts.osid "
				+ "left join automation e on e.AUTOID = ts.AUTOID left join casestate f on f.CASESTATEID =ts.CASESTATEID"
				+ " left join caseversion g on g.VERSIONID = ts.VERSIONID left join project h on h.PROJECTID = ts.PROJECTID"
				+ " left join subcomponent i on i.SUBCOMPID =ts.SUBCOMPID left join user j on j.USERID = ts.USERID left join team k on k.TEAMID =ts.TEAMID WHERE (ts.status <> 1 or ts.status is null) ";
		sql = filterQueryTestCaseCondition(sql, filterMap);
		sql = sql + "AND ts.testcasename like '" +"%" + caseName +"%'";
		
		logger.info("queryTestCasesByFilter method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						TestCase testCase = new TestCase();
						mapTestCaseAllFromRs(rs, testCase);
						return testCase;

					}
				});
		logger.info("queryTestCasesByFilter method result:"
				+ testCaseList.size());
		return testCaseList;

	}
	
	//Reserved for code redesign, not used so far.
	public String filterTestCaseCondition(String sql, Map<String, String> filterMap) {
		sql = sql + " %s %s %s %s %s ";
		String projectId = filterMap.get("projectId");
		String featureId = filterMap.get("featureId");
		String compId = filterMap.get("compId");
		String testTypeId = filterMap.get("testTypeId");
		String autoid = filterMap.get("autoId");
		
		if (Utils.isNullORWhiteSpace(projectId) || projectId.trim().equals("-1") || projectId.trim().equals("0")) {
			sql = String.format(sql, "", "%s", "%s", "%s", "%s");
		} else {
				sql = String.format(sql, "  and ts.projectId='" + projectId + "'", "%s", "%s", "%s", "%s");
		}
		return sql;
	}

	public String filterQueryTestCaseCondition(String sql,
			Map<String, String> filterMap) {
		sql = sql + " %s %s %s %s %s %s %s %s ";
		String projectId = filterMap.get("projectId");
		String featureId = filterMap.get("featureId");
		String compId = filterMap.get("compId");
		String testTypeId = filterMap.get("testTypeId");
		String osId = filterMap.get("osId");
		String autoid = filterMap.get("autoId");
		String version = filterMap.get("ver");
		String teamId = filterMap.get("teamId");
		
		boolean flag = true;// "where" keywords already added or not
		
		/*if (Utils.isNullORWhiteSpace(projectId) || projectId.trim().equals("-1")) {

			sql = String.format(sql, "", "%s", "%s", "%s", "%s", "%s", "%s",
					"%s");

		} else {
			sql = String.format(sql, "  and ts.projectId='" + projectId + "'",
					"%s", "%s", "%s", "%s", "%s", "%s", "%s");
			flag = true;

		}
*/
		
		if (Utils.isNullORWhiteSpace(projectId) || projectId.trim().equals("-1")
				|| projectId.trim().equals("0")) {
			sql = String.format(sql, "", "%s", "%s", "%s", "%s", "%s", "%s",
					"%s");

		} else {

			if (flag) {
				sql = String.format(sql, "  and ts.projectId='" + projectId + "'",
						"%s", "%s", "%s", "%s", "%s", "%s", "%s");
			} else {
				sql = String.format(sql, "  and ts.projectId='" + projectId + "'",
						"%s", "%s", "%s", "%s", "%s", "%s", "%s");
				flag = true;
			}
		}
		
		
		
		if (Utils.isNullORWhiteSpace(featureId)
				|| featureId.trim().equals("-1")
				|| featureId.trim().equals("0")) {

			sql = String.format(sql, "", "%s", "%s", "%s", "%s", "%s", "%s");

		} else {

			if (flag) {

				sql = String.format(sql, " and ts.featureId=" + featureId,
						"%s", "%s", "%s", "%s", "%s", "%s");

			} else {
				sql = String.format(sql, "  where ts.featureId=" + featureId,
						"%s", "%s", "%s", "%s", "%s", "%s");
				flag = true;
			}

		}

		if (Utils.isNullORWhiteSpace(compId) || compId.trim().equals("-1")
				|| compId.trim().equals("0")) {
			sql = String.format(sql, "", "%s", "%s", "%s", "%s", "%s");

		} else {

			if (flag) {

				sql = String.format(sql, " and ts.compid=" + compId, "%s",
						"%s", "%s", "%s", "%s");

			} else {
				sql = String.format(sql, "  where ts.compid=" + compId, "%s",
						"%s", "%s", "%s", "%s");
				flag = true;
			}
		}

		if (Utils.isNullORWhiteSpace(testTypeId)
				|| testTypeId.trim().equals("-1")
				|| testTypeId.trim().equals("0")) {

			sql = String.format(sql, "", "%s", "%s", "%s", "%s");
		} else {

			if (flag) {
				sql = String.format(sql, "  and ts.testtypeid =" + testTypeId,
						"%s", "%s", "%s", "%s");

			} else {
				sql = String.format(sql, " where ts.testtypeid =" + testTypeId,
						"%s", "%s", "%s", "%s");
				flag = true;
			}
		}
		if (Utils.isNullORWhiteSpace(osId) || osId.trim().equals("-1")
				|| osId.trim().equals("0")) {

			sql = String.format(sql, "", "%s", "%s", "%s");
		} else {

			if (flag) {
				sql = String.format(sql, " and ts.osid =" + osId, "%s", "%s",
						"%s");

			} else {
				sql = String.format(sql, " where ts.osid =" + osId, "%s", "%s",
						"%s");
				flag = true;
			}
		}
		if (Utils.isNullORWhiteSpace(autoid) || autoid.trim().equals("-1")
				|| autoid.trim().equals("0")) {

			sql = String.format(sql, "", "%s", "%s");

		} else {
			if (flag) {
				sql = String.format(sql, "  and ts.autoid='" + autoid + "'",
						"%s", "%s");
			} else {

				sql = String.format(sql, "  where ts.autoid='" + autoid + "'",
						"%s", "%s");
				flag = true;
			}
		}
		if (Utils.isNullORWhiteSpace(version) || version.trim().equals("-1")
				|| version.trim().equals("0")) {

			sql = String.format(sql, "", "%s");

		} else {
			if (flag) {
				sql = String.format(sql, " and ts.versionid='" + version + "'",
						"%s");
			} else {

				sql = String.format(sql, "  where ts.versionid='" + version
						+ "'", "%s");
				flag = true;
			}
		}

		if (Utils.isNullORWhiteSpace(teamId) || teamId.trim().equals("-1")
				|| teamId.trim().equals("0")) {

			sql = String.format(sql, "");

		} else {
			if (flag) {
				sql = String.format(sql, " and ts.teamid='" + teamId + "'");
			} else {

				sql = String.format(sql, "  where ts.teamid='" + teamId + "'");
				flag = true;
			}

		}
		return sql;
	}

	public List<TestCase> queryTestCaseByPage(PageBean pageBean,
			Map<String, String> filterMap) {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;
		String userId = filterMap.get("userId");
		String caseName = filterMap.get("caseName");

		String sql = "SELECT ts.*, a.FEATURENAME, b.COMPNAME, c.TESTTYPENAME, d.OSNAME, e.AUTONAME, f.CASESTATENAME, g.VERSIONNAME,"
				+ " h.PROJECTNAME, i.SUBCOMPNAME, j.USERNAME, k.TEAMNAME from "
				+ "testcase as ts left join feature a on a.FEATUREID = ts.FEATUREID left join component b on b.COMPID =ts.COMPID "
				+ "left join testtype c on c.TESTTYPEID = ts.TESTTYPEID left join os d on d.osid = ts.osid "
				+ "left join automation e on e.AUTOID = ts.AUTOID left join casestate f on f.CASESTATEID =ts.CASESTATEID"
				+ " left join caseversion g on g.VERSIONID = ts.VERSIONID left join project h on h.PROJECTID = ts.PROJECTID "
				+ "left join subcomponent i on i.SUBCOMPID =ts.SUBCOMPID left join user j on j.USERID = ts.USERID left join team k on k.TEAMID =ts.TEAMID "
				+ "WHERE EXISTS (SELECT * from user_team as map WHERE (map.status <> 1 or map.status is null) and map.TEAMID = ts.TEAMID and map.USERID="
				+ userId + ")";
		sql = filterQueryTestCaseCondition(sql, filterMap);
		sql = sql + "AND ts.testcasename like '" +"%" + caseName +"%' and (ts.status <> 1 or ts.status is null)";
		sql = sql + " ORDER BY h.PROJECTNAME,a.FEATURENAME,b.COMPNAME,ts.testcasename,c.TESTTYPENAME, e.AUTONAME, ts.TESTCASEALIASID  "+" limit " + itemFrom + "," + count;
		logger.info("queryTestCaseByPage method sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> testCaseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestCase testCase = new TestCase();
						mapTestCaseAllFromRs(rs, testCase);
						return testCase;

					}
				});
		logger.info("queryTestCaseByPage method result:" + testCaseList.size());
		return testCaseList;
	}

	public int queryTestCaseSizeByFilter(Map<String, String> filterMap) {

		String userId = filterMap.get("userId");
		String caseName = filterMap.get("caseName");

		String sql = "SELECT count(*) from testcase as ts  WHERE EXISTS (SELECT * from user_team as map WHERE (map.status <> 1 or map.status is null) and "
				+ "map.TEAMID = ts.TEAMID and map.USERID=" + userId + ") ";
		sql = filterQueryTestCaseCondition(sql, filterMap);
		sql = sql + "AND ts.testcasename like '" +"%" + caseName +"%' and (ts.status <> 1 or ts.status is null)";

		logger.info("queryTestCaseSizeByFilter menthod  sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("queryTestCaseSizeByFilter menthod  result:" + result);
		return result;

	}
	
	public int queryAllTestCaseSizeByFilter(Map<String, String> filterMap) {

		String sql = "SELECT count(*) from testcase as ts  WHERE (ts.status <> 1 or ts.status is null)";
		sql = filterQueryTestCaseCondition(sql, filterMap);
		//String subComp = filterMap.get("subComp");
		if( filterMap.containsKey("subComp") && Integer.parseInt(filterMap.get("subComp")) > 0) {
			sql = sql + "AND ts.subCompId = '"+filterMap.get("subComp")+"' ";
		}
		logger.info("queryAllTestCaseSizeByFilter menthod  sql:" + sql);
		int result = userJdbcTemplate.queryForInt(sql);
		logger.info("queryAllTestCaseSizeByFilter menthod  result:" + result);
		return result;

	}
	
	public int queryByNameCaseIdSize(TestCase testcase) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != testcase.getTestCaseId()) {
			sql = "select count(*) from testcase where (status <> 1 or status is null) and testCaseName  ='"
					+ testcase.getTestCaseName() + "'  and testCaseId!='"
					+ testcase.getTestCaseId() + "' ";
			logger.info("queryByNameCaseIdSize menthod  sql:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			if (result == 0) {
				sql = "select count(*) from testcase where (status <> 1 or status is null) and testCasealiasId ='"
						+ testcase.getTestCasealiasId()
						+ "'  and testCaseId!='"
						+ testcase.getTestCaseId() + "' ";
				logger.info("queryByNameCaseIdSize menthod  sql:" + sql);
				result = userJdbcTemplate.queryForInt(sql);
				if(result!=0){
					size = 2;
				}
			}

		} else {
			sql = "select count(*) from testcase where (status <> 1 or status is null) and testCaseName ='"
					+ testcase.getTestCaseName() + "'  ";
			logger.info("queryByNameCaseIdSize menthod  sql:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
			
			if (result == 0) {
				sql = "select count(*) from testcase where (status <> 1 or status is null) and  testCasealiasId ='"
						+ testcase.getTestCasealiasId() + "'   ";
				logger.info("queryByNameCaseIdSize menthod  sql:" + sql);
				result = userJdbcTemplate.queryForInt(sql);
				if(result!=0){
					size = 2;
				}
				
			}
		}

		logger.info("queryByNameCaseIdSize method result:" + result);
		return size;
	}
	

	public List<Automation> queryAllAutomations() {

		String sql = "select * from automation";
		logger.info("queryAllAutomations sql:" + sql);
		@SuppressWarnings("unchecked")
		List<Automation> automationList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						Automation automation = new Automation();
						mapAutoMationFromRs(rs, automation);
						return automation;
					}

				});

		return automationList;

	}
	
	public List<TestCase> listTestCasesByIds(String ids) {

		String sql = "SELECT * from testcase where testcaseid in ("+ids+")";
		logger.info("listTestCasesByIds sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> caseList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						TestCase testCase = new TestCase();
						testCase.setTestCaseId(rs.getInt("testcaseid"));
						testCase.setTestCaseName(rs.getString("testCaseName"));
						return testCase;
					}

				});
		return caseList;
	}
	
	public List<TestCase> listTestCaseNumInProjects(int id) {

		String sql = "select b.PROJECTNAME, count(*) as Num from testcase a left join project b on a.projectid=b.PROJECTID left join user_team c on"
				+ " b.TEAMID=c.TEAMID where (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and "
				+ "(c.status <> 1 or c.status is null) and c.userid='"+id+"' group by a.PROJECTID";
		logger.info("listTestCaseNumInProjects sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> caseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testCase = new TestCase();
						testCase.setProjectName(rs.getString("projectName"));
						testCase.setNum(rs.getInt("Num"));
						return testCase;
					}
				});
		return caseList;
	}
	
	public List<TestCase> listTestCaseNumInProjectByFeature(int id) {
		String sql = "select b.FEATURENAME, count(*) as Num from testcase a left join feature b on a.featureid=b.FEATUREID "
				+ "where a.PROJECTID='"+id+"' group by a.FEATUREID";
		logger.info("listTestCaseNumInProjectByFeature sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> caseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testCase = new TestCase();
						testCase.setFeatureName(rs.getString("featureName"));
						testCase.setNum(rs.getInt("Num"));
						return testCase;
					}
				});
		return caseList;
	}
	
	public List<TestCase> listTestCaseNumInProjectByAuto(int id) {
		String sql = "select b.AUTONAME, count(*) as Num from testcase a left join automation b on a.autoid=b.autoid "
				+ "where a.PROJECTID='"+id+"' group by a.autoid";
		logger.info("listTestCaseNumInProjectByAuto sql:" + sql);
		@SuppressWarnings("unchecked")
		List<TestCase> caseList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						TestCase testCase = new TestCase();
						testCase.setAutoName(rs.getString("autoName"));
						testCase.setNum(rs.getInt("Num"));
						return testCase;
					}
				});
		return caseList;
	}

	public Automation queryAutomation(int autoId) {

		String sql = "select * from automation where autoid=" + autoId;
		logger.info("sql:" + sql);
		final Automation automation = new Automation();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapAutoMationFromRs(rs, automation);
			}
		});

		return automation;

	}
	
    public Automation queryAutomationByNameExcelImport(String name) throws Exception {

        String sql = "select * from automation where (status <> 1 or status is null) and autoName='" + name + "'";
        logger.info("sql:" + sql);
		final Automation automation = new Automation();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapAutoMationFromRs(rs, automation);
			}
		});

		return automation;
    }

	public static void mapTestCaseToPs(PreparedStatement ps,
			final TestCase testcase) throws SQLException {

		if (testcase.getProjectId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, testcase.getProjectId());
		}
		if (testcase.getTeamId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testcase.getTeamId());
		}

		if (testcase.getOsId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testcase.getOsId());
		}
		if (testcase.getFeatureId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, testcase.getFeatureId());
		}
		if (testcase.getCompId() == 0) {
			ps.setNull(5, Types.NULL);
		} else {
			ps.setInt(5, testcase.getCompId());
		}
		if (testcase.getTestTypeId() == 0) {
			ps.setNull(6, Types.NULL);
		} else {
			ps.setInt(6, testcase.getTestTypeId());
		}
		if (testcase.getUserId() == 0) {
			ps.setNull(7, Types.NULL);
		} else {
			ps.setInt(7, testcase.getUserId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getTestCasealiasId())) {

			ps.setString(8, null);
		} else {
			ps.setString(8, testcase.getTestCasealiasId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getTestCaseName())) {

			ps.setString(9, null);
		} else {
			ps.setString(9, testcase.getTestCaseName());
		}

		if (Utils.isNullORWhiteSpace(testcase.getRequirementId())) {

			ps.setString(10, null);
		} else {
			ps.setString(10, testcase.getRequirementId());
		}

		
		if (Utils.isNullORWhiteSpace(testcase.getConfigFiles())) {

			ps.setString(11, null);
		} else {
			ps.setString(11, testcase.getConfigFiles());
		}
		if (Utils.isNullORWhiteSpace(testcase.getExecutionSteps())) {

			ps.setString(12, null);
		} else {
			ps.setString(12, testcase.getExecutionSteps());
		}
		if (Utils.isNullORWhiteSpace(testcase.getExpectedResult())) {

			ps.setString(13, null);
		} else {
			ps.setString(13, testcase.getExpectedResult());
		}

		if (testcase.getAutoId() == 0) {

			ps.setNull(14, Types.NULL);
		} else {
			ps.setInt(14, testcase.getAutoId());
		}
		if (Utils.isNullORWhiteSpace(testcase.getTestScript())) {

			ps.setString(15, null);
		} else {
			ps.setString(15, testcase.getTestScript());
		}
		if (Utils.isNullORWhiteSpace(testcase.getTestfunctionCall())) {

			ps.setString(16, null);
		} else {
			ps.setString(16, testcase.getTestfunctionCall());
		}
		if (Utils.isNullORWhiteSpace(testcase.getPackagesizeRange())) {

			ps.setString(17, null);
		} else {
			ps.setString(17, testcase.getPackagesizeRange());
		}
		if (testcase.getTimeout() == 0) {
			ps.setNull(18, Types.NULL);
		} else {
			ps.setInt(18, testcase.getTimeout());
		}

		if (testcase.getSubCompId() == 0) {

			ps.setInt(19, Types.NULL);
		} else {
			ps.setInt(19, testcase.getSubCompId());
		}
		if (testcase.getVersionId() == 0) {
			ps.setInt(20, Types.NULL);
		} else {
			ps.setInt(20, testcase.getVersionId());
		}

		if (testcase.getCaseStateId() == 0) {

			ps.setInt(21, Types.NULL);
		} else {
			ps.setInt(21, testcase.getCaseStateId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getDescription())) {

			ps.setString(22, null);
		} else {
			ps.setString(22, testcase.getDescription());
		}

		if (Utils.isNullORWhiteSpace(testcase.getCreateDate())) {

			ps.setString(23, null);
		} else {
			ps.setString(23, testcase.getCreateDate());
		}

		if (Utils.isNullORWhiteSpace(testcase.getModifyDate())) {

			ps.setString(24, null);
		} else {
			ps.setString(24 ,testcase.getModifyDate());
		}

	}
	
	public static void mapTestCaseToUpdatePs(PreparedStatement ps,
			final TestCase testcase) throws SQLException {

		if (testcase.getProjectId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, testcase.getProjectId());
		}
		if (testcase.getTeamId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, testcase.getTeamId());
		}

		if (testcase.getOsId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, testcase.getOsId());
		}
		if (testcase.getFeatureId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, testcase.getFeatureId());
		}
		if (testcase.getCompId() == 0) {
			ps.setNull(5, Types.NULL);
		} else {
			ps.setInt(5, testcase.getCompId());
		}
		if (testcase.getTestTypeId() == 0) {
			ps.setNull(6, Types.NULL);
		} else {
			ps.setInt(6, testcase.getTestTypeId());
		}
		if (testcase.getUserId() == 0) {
			ps.setNull(7, Types.NULL);
		} else {
			ps.setInt(7, testcase.getUserId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getTestCasealiasId())) {

			ps.setString(8, null);
		} else {
			ps.setString(8, testcase.getTestCasealiasId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getTestCaseName())) {

			ps.setString(9, null);
		} else {
			ps.setString(9, testcase.getTestCaseName());
		}

		if (Utils.isNullORWhiteSpace(testcase.getRequirementId())) {

			ps.setString(10, null);
		} else {
			ps.setString(10, testcase.getRequirementId());
		}

		
		if (Utils.isNullORWhiteSpace(testcase.getConfigFiles())) {

			ps.setString(11, null);
		} else {
			ps.setString(11, testcase.getConfigFiles());
		}
		if (Utils.isNullORWhiteSpace(testcase.getExecutionSteps())) {

			ps.setString(12, null);
		} else {
			ps.setString(12, testcase.getExecutionSteps());
		}
		if (Utils.isNullORWhiteSpace(testcase.getExpectedResult())) {

			ps.setString(13, null);
		} else {
			ps.setString(13, testcase.getExpectedResult());
		}

		if (testcase.getAutoId() == 0) {

			ps.setNull(14, Types.NULL);
		} else {
			ps.setInt(14, testcase.getAutoId());
		}
		if (Utils.isNullORWhiteSpace(testcase.getTestScript())) {

			ps.setString(15, null);
		} else {
			ps.setString(15, testcase.getTestScript());
		}
		if (Utils.isNullORWhiteSpace(testcase.getTestfunctionCall())) {

			ps.setString(16, null);
		} else {
			ps.setString(16, testcase.getTestfunctionCall());
		}
		if (Utils.isNullORWhiteSpace(testcase.getPackagesizeRange())) {

			ps.setString(17, null);
		} else {
			ps.setString(17, testcase.getPackagesizeRange());
		}
		if (testcase.getTimeout() == 0) {
			ps.setNull(18, Types.NULL);
		} else {
			ps.setInt(18, testcase.getTimeout());
		}

		if (testcase.getSubCompId() == 0) {

			ps.setInt(19, Types.NULL);
		} else {
			ps.setInt(19, testcase.getSubCompId());
		}
		if (testcase.getVersionId() == 0) {
			ps.setInt(20, Types.NULL);
		} else {
			ps.setInt(20, testcase.getVersionId());
		}

		if (testcase.getCaseStateId() == 0) {

			ps.setInt(21, Types.NULL);
		} else {
			ps.setInt(21, testcase.getCaseStateId());
		}

		if (Utils.isNullORWhiteSpace(testcase.getDescription())) {

			ps.setString(22, null);
		} else {
			ps.setString(22, testcase.getDescription());
		}

		if (Utils.isNullORWhiteSpace(testcase.getModifyDate())) {

			ps.setString(23, null);
		} else {
			ps.setString(23 ,testcase.getModifyDate());
		}

	}

	public static void mapTestCaseFromRs(ResultSet rs, TestCase testcase)
			throws SQLException {
		testcase.setTestCaseId(rs.getInt("testcaseid"));
		testcase.setTeamId(rs.getInt("teamid"));
		testcase.setOsId(rs.getInt("osid"));
		testcase.setFeatureId(rs.getInt("featureid"));
		testcase.setCompId(rs.getInt("compid"));
		testcase.setUserId(rs.getInt("userid"));
		testcase.setTestCasealiasId(rs.getString("testcasealiasid"));
		testcase.setTestCaseName(rs.getString("testcasename"));
		testcase.setRequirementId(rs.getString("requirementid"));
		testcase.setTestTypeId(rs.getInt("testtypeid"));
		testcase.setProjectId(rs.getInt("projectId"));
		testcase.setConfigFiles(rs.getString("configfiles"));
		testcase.setExecutionSteps(rs.getString("executionsteps"));
		testcase.setExpectedResult(rs.getString("expectedresult"));
		testcase.setAutoId(rs.getInt("autoid"));
		testcase.setTestScript(rs.getString("testscript"));
		testcase.setTestfunctionCall(rs.getString("testfunctioncall"));
		testcase.setPackagesizeRange(rs.getString("packetsizerange"));
		testcase.setTimeout(rs.getInt("timeout"));
		testcase.setSubCompId(rs.getInt("subcompid"));
		testcase.setCaseStateId(rs.getInt("casestateid"));
		testcase.setVersionId(rs.getInt("versionid"));
		testcase.setDescription(rs.getString("description"));
		testcase.setCreateDate(rs.getString("createdate"));
		testcase.setModifyDate(rs.getString("modifydate"));
	}
	
	public static void mapTestCaseAllFromRs(ResultSet rs, TestCase testcase)
			throws SQLException {
		testcase.setTestCaseId(rs.getInt("testcaseid"));
		testcase.setTeamId(rs.getInt("teamid"));
		testcase.setOsId(rs.getInt("osid"));
		testcase.setFeatureId(rs.getInt("featureid"));
		testcase.setCompId(rs.getInt("compid"));
		testcase.setUserId(rs.getInt("userid"));
		testcase.setTestCasealiasId(rs.getString("testcasealiasid"));
		testcase.setTestCaseName(rs.getString("testcasename"));
		testcase.setRequirementId(rs.getString("requirementid"));
		testcase.setTestTypeId(rs.getInt("testtypeid"));
		testcase.setProjectId(rs.getInt("projectId"));
		testcase.setConfigFiles(rs.getString("configfiles"));
		testcase.setExecutionSteps(rs.getString("executionsteps"));
		testcase.setExpectedResult(rs.getString("expectedresult"));
		testcase.setAutoId(rs.getInt("autoid"));
		testcase.setTestScript(rs.getString("testscript"));
		testcase.setTestfunctionCall(rs.getString("testfunctioncall"));
		testcase.setPackagesizeRange(rs.getString("packetsizerange"));
		testcase.setTimeout(rs.getInt("timeout"));
		testcase.setSubCompId(rs.getInt("subcompid"));
		testcase.setCaseStateId(rs.getInt("casestateid"));
		testcase.setVersionId(rs.getInt("versionid"));
		testcase.setDescription(rs.getString("description"));
		testcase.setCreateDate(rs.getString("createdate"));
		testcase.setModifyDate(rs.getString("modifydate"));
		testcase.setFeatureName(rs.getString("featureName"));
		testcase.setCompName(rs.getString("compName"));
		testcase.setTestTypeName(rs.getString("testTypeName"));
		testcase.setOSName(rs.getString("osName"));
		testcase.setAutoName(rs.getString("autoName"));
		testcase.setCaseStateName(rs.getString("caseStateName"));
		testcase.setVersionName(rs.getString("versionName"));
		testcase.setProjectName(rs.getString("projectName"));
		testcase.setSubCompName(rs.getString("subCompName"));
		testcase.setUserName(rs.getString("userName"));
		testcase.setTeamName(rs.getString("teamName"));
	}


	public static void mapAutoMationFromRs(ResultSet rs, Automation automation)
			throws SQLException {
		automation.setAutoId(rs.getInt("autoid"));
		automation.setAutoName(rs.getString("autoname"));
	}

	public static void mapVersionFromRs(ResultSet rs, VersionState version)
			throws SQLException {
		version.setVersionId(rs.getInt("versionid"));

		version.setVersionName(rs.getString("versionname"));
	}

	public static void mapCaseStateFromRs(ResultSet rs, CaseState state)
			throws SQLException {
		state.setCaseStateId(rs.getInt("casestateid"));
		state.setCaseStateName(rs.getString("casestatename"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
