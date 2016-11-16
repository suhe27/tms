package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.intel.cid.common.bean.PerfTestCaseConf;
import com.intel.cid.common.dao.PerfTestCaseConfDao;
import com.intel.cid.utils.Utils;

public class PerfTestCaseConfDaoImpl implements PerfTestCaseConfDao {

	private static Logger logger = Logger
			.getLogger(PerfTestCaseConfDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public int addPerfTestCaseConf(final PerfTestCaseConf PerfTestCaseConf)
			throws Exception {

		String sql = "insert into perf_testcase_conf(caseid,occupy_fields,createdate) values(?,?,?)";
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapPerfTestCaseConfToPs(PerfTestCaseConf, ps);

			}
		});

		return res;
	}

	public int delPerfTestCaseConf(PerfTestCaseConf PerfTestCaseConf)
			throws Exception {

		return delPerfTestCaseConfById(PerfTestCaseConf.getId());
	}

	public int delPerfTestCaseConfByCaseId(int id) throws Exception {
		String sql = "delete from perf_testcase_conf where caseid =" + id;
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql);

		return res;
	}

	public PerfTestCaseConf queryPerfTestCaseConfById(int id) throws Exception {
		String sql = "select * from perf_testcase_conf where id =" + id;
		logger.info("sql:" + sql);
		final PerfTestCaseConf perfTestCaseConf = new PerfTestCaseConf();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapPerfTestCaseConfFromRs(perfTestCaseConf, rs);

			}
		});
		logger.info("sql:" + sql);
		return perfTestCaseConf;
	}

	public PerfTestCaseConf queryPerfTestCaseConfByCaseId(int id)
			throws Exception {
		String sql = "select * from perf_testcase_conf where caseid =" + id;
		logger.info("sql:" + sql);
		final PerfTestCaseConf perfTestCaseConf = new PerfTestCaseConf();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapPerfTestCaseConfFromRs(perfTestCaseConf, rs);

			}
		});
		logger.info("sql:" + sql);
		return perfTestCaseConf;
	}

	public int updatePerfTestCaseConf(final PerfTestCaseConf PerfTestCaseConf)
			throws Exception {

		String sql = "update perf_testcase_conf set caseid =?,occupy_fields =?,createdate=? where id="
				+ PerfTestCaseConf.getId();

		logger.info("sql:" + sql);

		userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				mapPerfTestCaseConfToPs(PerfTestCaseConf, ps);

			}
		});
		return 0;
	}

	public int delPerfTestCaseConfById(int id) throws Exception {
		String sql = "delete from perf_testcase_conf where id =" + id;
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql);

		return res;
	}

	
	
	
	
	
	
	public void mapPerfTestCaseConfToPs(
			final PerfTestCaseConf PerfTestCaseConf, PreparedStatement ps)
			throws SQLException {

		if (PerfTestCaseConf.getTestCaseId() == 0) {
			ps.setNull(1, Types.NULL);
		} else {
			ps.setInt(1, PerfTestCaseConf.getTestCaseId());
		}
		if (PerfTestCaseConf.getOccupy_fields() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, PerfTestCaseConf.getOccupy_fields());
		}

		if (Utils.isNullORWhiteSpace(PerfTestCaseConf.getCreateDate())) {

			ps.setString(3, null);
		} else {
			ps.setString(3, PerfTestCaseConf.getCreateDate());
		}

	}

	public void mapPerfTestCaseConfFromRs(
			final PerfTestCaseConf perfTestCaseConf, ResultSet rs)
			throws SQLException {
		perfTestCaseConf.setId(rs.getInt("id"));
		perfTestCaseConf.setTestCaseId(rs.getInt("caseid"));
		perfTestCaseConf.setOccupy_fields(rs.getInt("occupy_fields"));
		perfTestCaseConf.setCreateDate(rs.getString("createdate"));
	}

}
