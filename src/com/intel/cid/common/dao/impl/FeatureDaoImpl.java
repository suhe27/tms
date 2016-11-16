package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.FeatureDao;
import com.intel.cid.utils.Utils;

public class FeatureDaoImpl implements FeatureDao {

	private static Logger logger = Logger.getLogger(FeatureDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addFeature(final Feature feature) throws Exception {

		String sql = "insert into feature(featurename) values(?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapFeatureToPs(feature, ps);

					}
				});
		logger.info("result:" + result);
		return result;
	}

	public int addFeatureWithKey(final Feature feature) throws Exception {

		final String sql = "insert into feature(featurename,projectId) values(?,?)";
		logger.info("sql:" + sql);

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		userJdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapFeatureToPs(feature, ps);

				return ps;
			}
		}, keyHolder);
		int result = keyHolder.getKey().intValue();

		return result;
	}

	/*
	 * public int addFeatureWithKeys(final Feature feature) throws Exception {
	 * 
	 * final String sql =
	 * "insert into feature(featurename,projectId) values('"+feature
	 * .getFeatureName()+"','"+feature.getProjectId()+"')"; logger.info("sql:" +
	 * sql); int result =userJdbcTemplate.update(sql); return result; }
	 */
	public int deleteBatchFeatureComp(final String featureId,
			final String[] comps) throws Exception {

		String sql = "delete from component_feature where featureid=? and compid=? ";
		logger.info("sql:" + sql);

		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(comps[i].trim()));
					}

					@Override
					public int getBatchSize() {

						return comps.length;
					}
				});

		return results.length;

	}

	public int deleteBatchFeatureComp(final String featureId,
			final List<String> comps) throws Exception {

		//String sql = "delete from component_feature where featureid=? and compid=? ";
		String sql = "UPDATE `component_feature` SET `STATUS`='1' WHERE featureid=? and compid=? ";
		logger.info("update component_feature sql:" + sql);

		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(comps.get(i)));
					}

					@Override
					public int getBatchSize() {

						return comps.size();
					}
				});

		return results.length;

	}

	/*
	 * public int deleteBatchFeatureoject(final String featureId) throws
	 * Exception { String sql = " delete from feature_proejct where featureid='"
	 * + featureId + "' "; logger.info("sql:" + sql); int results =
	 * userJdbcTemplate.update(sql); return results;
	 * 
	 * }
	 */
	public int addBatchFeatureComp(final String featureId, final String[] comps)
			throws Exception {

		String sql = "insert into component_feature(featureid,compid) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(comps[i].trim()));
					}

					public int getBatchSize() {

						return comps.length;
					}
				});
		logger.info(results.length);
		return results.length;

	}

	public int addBatchFeaturePlatform(final String featureId,
			final String[] projects) throws Exception {
		String sql = "insert into  feature_project (featureid,projectId) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(projects[i].trim()));

					}

					public int getBatchSize() {

						return projects.length;
					}
				});

		return results.length;

	}

	public int addBatchFeaturePlatform(final String featureId,
			final List<String> platforms) throws Exception {
		String sql = "insert into feature_platform(featureid,platformid) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(platforms.get(i).trim()));

					}

					public int getBatchSize() {

						return platforms.size();
					}
				});

		return results.length;

	}

	@SuppressWarnings("unchecked")
	public List<Feature> listFeaturesByProject(String projectName)
			throws Exception {
		String sql = "select c.FEATURENAME,c.FEATUREID,p.PROJECTID from feature c,project p where  (c.status <> 1 or c.status is null) and p.projectId=c.projectId ";
		if (null != projectName && !"-1".equals(projectName)) {
			sql += " and projectName = '" + projectName + "' ";
		}

		List<Feature> featureList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {

						Feature feature = new Feature();
						mapFeatureFormRs(rs, feature);

						return feature;
					}
				});

		logger.info(sql);
		return featureList;

	}

	public int addBatchFeatureComp(final String featureId,
			final List<String> comps) throws Exception {
		String sql = "insert into component_feature(featureid,compid) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(featureId));
						ps.setInt(2, Integer.parseInt(comps.get(i)));
					}

					public int getBatchSize() {

						return comps.size();
					}
				});

		return results.length;

	}

	public int delFeature(Feature feature) throws Exception {

		return delFeatureById(feature.getFeatureId());
	}

	public int delFeatureById(int id) throws Exception {

		//String sql = "delete from feature where featureId='" + id + "'";
		String sql = "UPDATE `feature` SET `STATUS`='1' WHERE featureid='" + id + "'";
		logger.info("delFeatureById method sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("delFeatureById method result:" + result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Feature> listAllFeatures() throws Exception {
		//String sql = "select * from feature";
		String sql = "SELECT a.*, b.PROJECTNAME as projectName, GROUP_CONCAT(d.COMPNAME SEPARATOR ' , ') as CompNames FROM feature a "
				+ "left join project b on b.PROJECTID = a.PROJECTID left join component_feature c on c.FEATUREID = a.FEATUREID left join component d "
				+ "on d.COMPID = c.COMPID where (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and "
				+ "(c.status <> 1 or c.status is null) and (d.status <> 1 or d.status is null) group by a.FEATUREID";
		logger.info("queryAllFeatures method sql:" + sql);

		List<Feature> featureList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						Feature feature = new Feature();
						feature.setFeatureId(rs.getInt("featureid"));
						feature.setFeatureName(rs.getString("featurename"));
						feature.setProjectId(rs.getInt("projectId"));
						feature.setProjectName(rs.getString("projectName"));
						feature.setCompNames(rs.getString("compNames"));
						return feature;
					}
				});

		logger.info("queryAllFeatures method result:" + featureList);
		return featureList;
	}

	@SuppressWarnings("unchecked")
	public List listAllFeatures(int projectId) throws Exception {
		String sql = "select a.* from feature a,project b where (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and a.projectId = b.projectiD and b.projectId = "
				+ projectId;

		List featureList = userJdbcTemplate.queryForList(sql);

		return featureList;
	}

	public Feature queryFeatureById(int id) throws Exception {

		String sql = "select * from feature where featureid='" + id + "'";

		logger.info("sql:" + sql);
		final Feature feature = new Feature();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapFeatureFormRs(rs, feature);
			}
		});

		logger.info("result:" + feature);
		return feature;
	}

	public int queryFeatureSize(Feature feature) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != feature.getFeatureId()) {
			sql = "select count(*) from feature where (status <> 1 or status is null) and projectId = '"+ feature.getProjectId() +"' "
					+ "and featureName = '"+ feature.getFeatureName() + "'  and featureId !='"
			+ feature.getFeatureId() + "'";
			logger.info("queryByFeatureNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from feature where (status <> 1 or status is null) and projectId = '"+ feature.getProjectId() +"'  "
					+ "and featureName ='"+ feature.getFeatureName() + "'  ";
			logger.info("queryByFeatureNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByFeatureNameSize method result:" + result);
		return size;
	}
	
	public int updateFeature(final Feature feature) throws Exception {
		String sql = "update feature set featurename=? ,  projectId=? where featureid="
				+ feature.getFeatureId();
		logger.info("Update feature sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapFeatureToPs(feature, ps);
					}
				});
		logger.info("result:" + result);
		return result;
	}

	public static void mapFeatureFormRs(ResultSet rs, Feature feature)
			throws SQLException {
		feature.setFeatureId(rs.getInt("featureid"));
		feature.setFeatureName(rs.getString("featurename"));
		feature.setProjectId(rs.getInt("projectId"));

	}

	public static void mapFeatureToPs(final Feature feature,
			PreparedStatement ps) throws SQLException {
		if (Utils.isNullORWhiteSpace(feature.getFeatureName())) {
			ps.setString(1, null);
		} else {
			ps.setString(1, feature.getFeatureName());
		}
		if (Utils.isNullORWhiteSpace(String.valueOf(feature.getProjectId()))) {
			ps.setString(2, null);
		} else {
			ps.setInt(2, feature.getProjectId());
		}

	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userjJdbcTemplate) {
		this.userJdbcTemplate = userjJdbcTemplate;
	}

	public List<?> queryFeatureList(int projectId) {
		String sql = "select b.FEATURENAME,b.FEATUREID  from project a ,feature b where a.projectId = b .projectId and a.projectId='"
				+ projectId + "' and (b.status <> 1 or b.status is null)";

		logger.info(sql);
		return userJdbcTemplate.queryForList(sql);
	}

	public List<?> queryComponentList(String featureID) {
		String sql = "select a.* from component a,component_feature b where a.compid = b.compid and (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null)";
		if (null != featureID && !"".equals(featureID)) {
			sql += " and b.featureid = " + featureID;
		}
		logger.info(sql);
		return userJdbcTemplate.queryForList(sql);
	}

}
