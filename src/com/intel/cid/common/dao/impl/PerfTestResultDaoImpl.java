package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.PerfTestResult;
import com.intel.cid.common.dao.PerfTestResultDao;
import com.intel.cid.utils.Utils;

public class PerfTestResultDaoImpl implements PerfTestResultDao {

	private static Logger logger = Logger
			.getLogger(PerfTestResultDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public int addPerfTestResult(final PerfTestResult PerfTestResult)
			throws Exception {

		String sql = "insert into  perf_testresult(executionid,testplanid,subplanid,testcaseid,projectid,targetid,tag,sequeuece,field_name1,field_name2,field_name3,field_name4,field_name5,field_name6,"
				+ "field_name7,field_name8,field_name9,field_name10,field_name11,field_name12,field_name13,field_name14,field_name15,field_name16,"
				+ "createdate,`desc`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("sql:" + sql);

		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapPerfTestResutToPs(PerfTestResult, ps);

			}
		});

		return res;
	}

	public int delPerfTestResult(PerfTestResult PerfTestResult)
			throws Exception {

		return delPerfTestResultById(PerfTestResult.getId());
	}

	public int delPerfTestResultById(int id) throws Exception {

		String sql = "delete from perf_testresult where id=" + id;
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql);

		return res;
	}

	public PerfTestResult queryPerfTestResultById(int id) throws Exception {

		String sql = "select * from perf_testresult where id =" + id;
		logger.info("sql:" + sql);
		final PerfTestResult perfTestResult = new PerfTestResult();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapPerfTestResultFromRs(perfTestResult, rs);

			}
		});

		return perfTestResult;
	}

	public int updatePerfTestResult(final PerfTestResult PerfTestResult)
			throws Exception {

		String sql = "update perf_testresult set executionid =? ,testplanid=?,subplanid=?,testcaseid=?,projectid=?,targetid=?,tag=?,sequeuece=? ,field_name1 =? ,field_name2 =? ,field_name3 =? ,field_name4 =? ,field_name5 =? ,field_name6 =? ,"
				+ "field_name7 =? ,field_name8 =? ,field_name9 =? ,field_name10 =? ,field_name11 =? ,field_name12 =? ,field_name13 =? ,field_name14 =? ,field_name15 =? ,field_name16 =? ,ceatedate=?,`desc`=? where id="
				+ PerfTestResult.getId();
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapPerfTestResutToPs(PerfTestResult, ps);
			}
		});

		return res;
	}

	/****
	 * 
	 * @param testResultConfMap
	 * @param executionId
	 * @param caseid
	 * @param targetId
	 * @param tag
	 * @return perf data list , list[0] perf case title section
	 *         list[1]...list[n] perf case data section
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getPerfTestResult(
			 final Map<String, String> testResultConfMap, int executionId, int caseId,
			int targetId, String tag) throws Exception {

		StringBuffer sBuffer = new StringBuffer(
				"select * from perf_testresult  where executionid ="
						+ executionId + ",and caseid=" + caseId + ",and tag ="
						+ tag);

		if (targetId != 0) {
			sBuffer.append("and targetid =" + targetId);
		}
		final List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> columnNameList = new ArrayList(testResultConfMap.values());
		resultList.add(columnNameList);
		logger.info("sql:" + sBuffer.toString());

		List<List<String>> dataResultList =userJdbcTemplate.query(sBuffer.toString(), new RowMapper() {

			public Object mapRow(ResultSet rs, int i) throws SQLException {
				
				List<String>perfDataList = new ArrayList<String>();
				
				Set<String> columnSet = testResultConfMap.keySet();
				
				for(String colName: columnSet){
					
					//get column name mapped value
					String resultColumnName = colName.replace("_name", "");					
					perfDataList.add(rs.getString(resultColumnName));					
				}				
				return perfDataList;
			}
		});

		
		resultList.addAll(dataResultList);
		return resultList;
	}

	public int addBatchPerfTestResults(final List<PerfTestResult> resultList) {

		String sql = "insert into  perf_testresult(executionid,testplanid,subplanid,testcaseid,projectid,targetid,tag,sequeuece,field_name1,field_name2,field_name3,field_name4,field_name5,field_name6,"
				+ "field_name7,field_name8,field_name9,field_name10,field_name11,field_name12,field_name13,field_name14,field_name15,field_name16,"
				+ "createdate,`desc`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("sql:" + sql);
		int[] res = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public int getBatchSize() {

						return resultList.size();
					}

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						PerfTestResult result = resultList.get(i);

						mapPerfTestResutToPs(result, ps);

					}
				});

		return res.length;

	}

	public void mapPerfTestResutToPs(final PerfTestResult PerfTestResult,
			PreparedStatement ps) throws SQLException {

		if (PerfTestResult.getExecutionId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, PerfTestResult.getExecutionId());
		}
		if (PerfTestResult.getTestPlanId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, PerfTestResult.getTestPlanId());
		}
		if (PerfTestResult.getSubPlanId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, PerfTestResult.getSubPlanId());
		}

		if (PerfTestResult.getTestCaseId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, PerfTestResult.getTestCaseId());
		}
		if (PerfTestResult.getProjectId() == 0) {
			ps.setNull(5, Types.NULL);
		} else {
			ps.setInt(5, PerfTestResult.getProjectId());
		}
		if (PerfTestResult.getTargetId() == 0) {
			ps.setNull(6, Types.NULL);
		} else {
			ps.setInt(6, PerfTestResult.getTargetId());
		}

		if (Utils.isNullORWhiteSpace(PerfTestResult.getTag())) {
			ps.setString(7, null);
		} else {
			ps.setString(7, PerfTestResult.getTag());

		}

		if (Utils.isNullORWhiteSpace(PerfTestResult.getSequence())) {
			ps.setString(8, null);
		} else {
			ps.setString(8, PerfTestResult.getSequence());

		}

		if (Utils.isNullORWhiteSpace(PerfTestResult.getField1())) {
			ps.setString(9, null);
		} else {
			ps.setString(9, PerfTestResult.getField1());
		}

		if (Utils.isNullORWhiteSpace(PerfTestResult.getField2())) {
			ps.setString(10, null);
		} else {
			ps.setString(10, PerfTestResult.getField2());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField3())) {
			ps.setString(11, null);
		} else {
			ps.setString(11, PerfTestResult.getField3());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField4())) {
			ps.setString(12, null);
		} else {
			ps.setString(12, PerfTestResult.getField4());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField5())) {
			ps.setString(13, null);
		} else {
			ps.setString(13, PerfTestResult.getField5());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField6())) {
			ps.setString(14, null);
		} else {
			ps.setString(14, PerfTestResult.getField6());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField7())) {
			ps.setString(15, null);
		} else {
			ps.setString(15, PerfTestResult.getField7());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField8())) {
			ps.setString(16, null);
		} else {
			ps.setString(16, PerfTestResult.getField8());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField9())) {
			ps.setString(17, null);
		} else {
			ps.setString(17, PerfTestResult.getField9());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField10())) {
			ps.setString(18, null);
		} else {
			ps.setString(18, PerfTestResult.getField10());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField11())) {
			ps.setString(19, null);
		} else {
			ps.setString(19, PerfTestResult.getField11());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField12())) {
			ps.setString(20, null);
		} else {
			ps.setString(20, PerfTestResult.getField12());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField13())) {
			ps.setString(21, null);
		} else {
			ps.setString(21, PerfTestResult.getField13());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField14())) {
			ps.setString(22, null);
		} else {
			ps.setString(22, PerfTestResult.getField14());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField15())) {
			ps.setString(23, null);
		} else {
			ps.setString(23, PerfTestResult.getField15());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getField16())) {
			ps.setString(24, null);
		} else {
			ps.setString(24, PerfTestResult.getField16());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getCreateDate())) {
			ps.setString(25, null);
		} else {
			ps.setString(25, PerfTestResult.getCreateDate());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResult.getDesc())) {
			ps.setString(26, null);
		} else {
			ps.setString(26, PerfTestResult.getDesc());
		}

	}

	public void mapPerfTestResultFromRs(final PerfTestResult perfTestResult,
			ResultSet rs) throws SQLException {
		perfTestResult.setId(rs.getInt("id"));
		perfTestResult.setExecutionId(rs.getInt("executionid"));
		perfTestResult.setTestPlanId(rs.getInt("testplanid"));
		perfTestResult.setSubPlanId(rs.getInt("subplanid"));
		perfTestResult.setTestCaseId(rs.getInt("testcaseid"));
		perfTestResult.setProjectId(rs.getInt("projectid"));
		perfTestResult.setTargetId(rs.getInt("targetid"));
		perfTestResult.setTag(rs.getString("tag"));
		perfTestResult.setSequence(rs.getString("sequeuece"));
		perfTestResult.setField1(rs.getString("field1"));
		perfTestResult.setField2(rs.getString("field2"));
		perfTestResult.setField3(rs.getString("field3"));
		perfTestResult.setField4(rs.getString("field4"));
		perfTestResult.setField5(rs.getString("field5"));
		perfTestResult.setField6(rs.getString("field6"));
		perfTestResult.setField7(rs.getString("field7"));
		perfTestResult.setField8(rs.getString("field8"));
		perfTestResult.setField9(rs.getString("field9"));
		perfTestResult.setField10(rs.getString("field10"));
		perfTestResult.setField11(rs.getString("field11"));
		perfTestResult.setField12(rs.getString("field12"));
		perfTestResult.setField13(rs.getString("field13"));
		perfTestResult.setField14(rs.getString("field14"));
		perfTestResult.setField15(rs.getString("field15"));
		perfTestResult.setField16(rs.getString("field16"));
		perfTestResult.setCreateDate(rs.getString("createdate"));
		perfTestResult.setDesc(rs.getString("desc"));
	}

}
