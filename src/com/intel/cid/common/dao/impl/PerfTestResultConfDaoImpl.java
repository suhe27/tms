package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.intel.cid.common.bean.PerfTestResultConf;
import com.intel.cid.common.dao.PerfTestResultConfDao;
import com.intel.cid.utils.Utils;

public class PerfTestResultConfDaoImpl implements PerfTestResultConfDao {

	private static Logger logger = Logger
			.getLogger(PerfTestResultConfDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public int addPerfTestResultConf(final PerfTestResultConf PerfTestResultConf)
			throws Exception {

		String sql = "insert into  perf_testresult_conf(testcaseid,projectid,field_name1,field_name2,field_name3,field_name4,field_name5,field_name6,"
				+ "field_name7,field_name8,field_name9,field_name10,field_name11,field_name12,field_name13,field_name14,field_name15,field_name16,"
				+ "createdate,`desc`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		logger.info("sql:" + sql);

		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapPerfTestResutConfToPs(PerfTestResultConf, ps);

			}
		});

		return res;
	}

	public int delPerfTestResultConf(PerfTestResultConf PerfTestResultConf)
			throws Exception {

		return delPerfTestResultConfById(PerfTestResultConf.getId());
	}

	public int delPerfTestResultConfById(int id) throws Exception {

		String sql = "delete from perf_testresult_conf where id=" + id;
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql);

		return res;
	}

	public PerfTestResultConf queryPerfTestResultConfById(int id)
			throws Exception {

		String sql = "select * from perf_testresult_conf where id =" + id;
		logger.info("sql:" + sql);
		final PerfTestResultConf perfTestResultConf = new PerfTestResultConf();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapPerfTestResultFromRs(perfTestResultConf, rs);

			}
		});

		return perfTestResultConf;
	}

	public PerfTestResultConf queryPerfTestResultConfByCaseId(int id)
			throws Exception {

		String sql = "select * from perf_testresult_conf where testcaseid ="
				+ id;
		logger.info("sql:" + sql);
		final PerfTestResultConf perfTestResultConf = new PerfTestResultConf();

		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapPerfTestResultFromRs(perfTestResultConf, rs);

			}
		});

		return perfTestResultConf;
	}

	public int updatePerfTestResultConf(
			final PerfTestResultConf PerfTestResultConf) throws Exception {

		String sql = "update perf_testresult_conf set testcaseid =? ,projectid =? ,field_name1 =? ,field_name2 =? ,field_name3 =? ,field_name4 =? ,field_name5 =? ,field_name6 =? ,"
				+ "field_name7 =? ,field_name8 =? ,field_name9 =? ,field_name10 =? ,field_name11 =? ,field_name12 =? ,field_name13 =? ,field_name14 =? ,field_name15 =? ,field_name16 =? ," +
						" ceatedate=?, `desc`=? where id="
				+ PerfTestResultConf.getId();
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapPerfTestResutConfToPs(PerfTestResultConf, ps);
			}
		});

		return res;
	}

	public List<String> getTestCaseColumnNames(int testcaseid,
			final int occupy_fields) {

		String sql = "select * from perf_testresult_conf where testcaseid ="
				+ testcaseid;
		logger.info("sql:" + sql);

		final List<String> testCaseColumnList = new ArrayList<String>();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnNum = rsmd.getColumnCount();
				String endColumnName = "field_name" + occupy_fields;
				for (int i = 1; i <= columnNum; i++) {
					String columnName = rsmd.getColumnName(i);
					if (columnName.startsWith("field_name")) {
						testCaseColumnList.add(columnName);
						if (columnName.equalsIgnoreCase(endColumnName)) {
							break;
						}

					}

				}
			}
		});

		return testCaseColumnList;
	}

	/***
	 * 
	 * @param testcaseid
	 * @param occupy_fields
	 * @return Map key is perf testcase db cloumn name, value is testcase column name.
	 */
	public Map<String, String> getPerfTestResultConfMap(int testcaseid,
			final int occupy_fields) {

		String sql = "select * from perf_testresult_conf where testcaseid ="
				+ testcaseid;
		logger.info("sql:" + sql);
		
		final Map<String, String> testReustConfMap = new LinkedHashMap<String, String>();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnNum = rsmd.getColumnCount();
				String endColumnName = "field_name" + occupy_fields;
				for (int i = 1; i <= columnNum; i++) {
					String columnName = rsmd.getColumnName(i);
					String value = rs.getString(columnName);
					if (columnName.startsWith("field_name")) {
						testReustConfMap.put(columnName, value);
						if (columnName.equalsIgnoreCase(endColumnName)) {
							break;
						}

					}

				}

			}
		});

		return testReustConfMap;
	}

	public void mapPerfTestResutConfToPs(
			final PerfTestResultConf PerfTestResultConf, PreparedStatement ps)
			throws SQLException {
		if (PerfTestResultConf.getTestCaseId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, PerfTestResultConf.getTestCaseId());
		}
		if (PerfTestResultConf.getProjectId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, PerfTestResultConf.getProjectId());
		}

		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name1())) {
			ps.setString(3, null);
		} else {
			ps.setString(3, PerfTestResultConf.getField_name1());
		}

		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name2())) {
			ps.setString(4, null);
		} else {
			ps.setString(4, PerfTestResultConf.getField_name2());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name3())) {
			ps.setString(5, null);
		} else {
			ps.setString(5, PerfTestResultConf.getField_name3());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name4())) {
			ps.setString(6, null);
		} else {
			ps.setString(6, PerfTestResultConf.getField_name4());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name5())) {
			ps.setString(7, null);
		} else {
			ps.setString(7, PerfTestResultConf.getField_name5());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name6())) {
			ps.setString(8, null);
		} else {
			ps.setString(8, PerfTestResultConf.getField_name6());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name7())) {
			ps.setString(9, null);
		} else {
			ps.setString(9, PerfTestResultConf.getField_name7());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name8())) {
			ps.setString(10, null);
		} else {
			ps.setString(10, PerfTestResultConf.getField_name8());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name9())) {
			ps.setString(11, null);
		} else {
			ps.setString(11, PerfTestResultConf.getField_name9());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name10())) {
			ps.setString(12, null);
		} else {
			ps.setString(12, PerfTestResultConf.getField_name10());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name11())) {
			ps.setString(13, null);
		} else {
			ps.setString(13, PerfTestResultConf.getField_name11());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name12())) {
			ps.setString(14, null);
		} else {
			ps.setString(14, PerfTestResultConf.getField_name12());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name13())) {
			ps.setString(15, null);
		} else {
			ps.setString(15, PerfTestResultConf.getField_name13());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name14())) {
			ps.setString(16, null);
		} else {
			ps.setString(16, PerfTestResultConf.getField_name14());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name15())) {
			ps.setString(17, null);
		} else {
			ps.setString(17, PerfTestResultConf.getField_name15());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getField_name16())) {
			ps.setString(18, null);
		} else {
			ps.setString(18, PerfTestResultConf.getField_name16());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getCreateDate())) {
			ps.setString(19, null);
		} else {
			ps.setString(19, PerfTestResultConf.getCreateDate());
		}
		if (Utils.isNullORWhiteSpace(PerfTestResultConf.getDesc())) {
			ps.setString(20, null);
		} else {
			ps.setString(20, PerfTestResultConf.getDesc());
		}

	}

	public void mapPerfTestResultFromRs(
			final PerfTestResultConf perfTestResultConf, ResultSet rs)
			throws SQLException {
		perfTestResultConf.setId(rs.getInt("id"));
		perfTestResultConf.setTestCaseId(rs.getInt("testcaseid"));
		perfTestResultConf.setProjectId(rs.getInt("projectid"));
		perfTestResultConf.setField_name1(rs.getString("field_name1"));
		perfTestResultConf.setField_name2(rs.getString("field_name2"));
		perfTestResultConf.setField_name3(rs.getString("field_name3"));
		perfTestResultConf.setField_name4(rs.getString("field_name4"));
		perfTestResultConf.setField_name5(rs.getString("field_name5"));
		perfTestResultConf.setField_name6(rs.getString("field_name6"));
		perfTestResultConf.setField_name7(rs.getString("field_name7"));
		perfTestResultConf.setField_name8(rs.getString("field_name8"));
		perfTestResultConf.setField_name9(rs.getString("field_name9"));
		perfTestResultConf.setField_name10(rs.getString("field_name10"));
		perfTestResultConf.setField_name11(rs.getString("field_name11"));
		perfTestResultConf.setField_name12(rs.getString("field_name12"));
		perfTestResultConf.setField_name13(rs.getString("field_name13"));
		perfTestResultConf.setField_name14(rs.getString("field_name14"));
		perfTestResultConf.setField_name15(rs.getString("field_name15"));
		perfTestResultConf.setField_name16(rs.getString("field_name16"));
		perfTestResultConf.setCreateDate(rs.getString("createdate"));
		perfTestResultConf.setDesc(rs.getString("desc"));
	}

}
