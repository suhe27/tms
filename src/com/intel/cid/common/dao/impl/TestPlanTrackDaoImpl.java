package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestPlanTrack;
import com.intel.cid.common.dao.TestPlanTrackDao;

public class TestPlanTrackDaoImpl implements TestPlanTrackDao {

	private static Logger logger = Logger.getLogger(TestPlanTrackDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addBatchTestPlanTrack(final List<TestPlanTrack> trackList)
			throws Exception {

		String sql = "insert into testplantrack(testplanid ,subplanid ,testunitid ,pass ,fail ,block ,notrun ,totalcases ,createdate )values(?,?,?,?,?,?,?,?,?) ";
		logger.info("sql:" + sql);
		int[] res = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						TestPlanTrack track = trackList.get(i);
						mapTestPlanTrackToPs(ps, track);

					}

					public int getBatchSize() {

						return trackList.size();
					}
				});

		return res.length;
	}

	public int addBatchTestPlanStatus(final List<TestPlan> testPlanList)
			throws Exception {

		String sql = "insert into testplanstatus(testplanid ,project ,releasecycle ,pass ,fail ,block ,notrun ,passrate,totalcases ,createdate )values(?,?,?,?,?,?,?,?,?,?) ";
		logger.info("sql:" + sql);
		int[] res = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						TestPlan testPlan = testPlanList.get(i);
						mapTestPlanStatusToPs(ps, testPlan);

					}

					

					public int getBatchSize() {

						return testPlanList.size();
					}
				});

		return res.length;
	}

	public static  void mapTestPlanStatusToPs(PreparedStatement ps,
			TestPlan testPlan) throws SQLException {
		
		ps.setInt(1, testPlan.getTestPlanId());
//		ps.setString(2, testPlan.getProject());
//		ps.setString(3, testPlan.getReleaseCycle());
//		ps.setInt(4, testPlan.getPass());
//		ps.setInt(5, testPlan.getFail());
//		ps.setInt(6, testPlan.getBlock());
//		ps.setInt(7, testPlan.getNotRun());
//		ps.setFloat(8, testPlan.getPassRate());
		ps.setInt(9, testPlan.getTotalCases());
		ps.setString(10, testPlan.getCreateDate());	
	}
	
	public static void mapTestPlanTrackToPs(PreparedStatement ps,
			TestPlanTrack track) throws SQLException {
		ps.setInt(1, track.getTestPlanId());
		ps.setInt(2, track.getSubPlanId());
		ps.setInt(3, track.getTestUnitId());
		ps.setInt(4, track.getPass());
		ps.setInt(5, track.getFail());
		ps.setInt(6, track.getBlock());
		ps.setInt(7, track.getNotRun());
		ps.setInt(8, track.getTotalCases());
		ps.setString(9, track.getCreateDate());
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
