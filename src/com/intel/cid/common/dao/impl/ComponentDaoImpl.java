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
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.dao.ComponentDao;
import com.intel.cid.utils.Utils;

public class ComponentDaoImpl implements ComponentDao {

	private static Logger logger = Logger.getLogger(ComponentDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addComponent(final Component component) throws Exception {

		String sql = "insert into component(compname) values(?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapComponentToPs(component, ps);

					}
				});
		logger.info("result:" + result);
		return result;
	}

	public int addComponentWithKey(final Component component) throws Exception {

		final String sql = "insert into component(compname) values(?)";
		logger.info("sql:" + sql);
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		
		
		
		userJdbcTemplate.update(new PreparedStatementCreator() {
			
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				mapComponentToPs(component, ps);
				return ps;
			}
		}, keyHolder);
		
		int result = keyHolder.getKey().intValue();

		return result;
	}
	
	
	public int delComponent(Component component) throws Exception {

		return delComponentById(component.getCompId());
	}

	public int delComponentById(int id) throws Exception {

		//String sql = "delete from component where compid='" + id + "'";
		String sql = "UPDATE `component` SET `STATUS`='1' WHERE compid='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Component> queryAllComponents() throws Exception {
		//String sql = "select * from component";
		String sql = "SELECT a.*, GROUP_CONCAT(c.SUBCOMPNAME SEPARATOR ' , ') as SubCompNames FROM component a left join component_subcomponent b "
				+ "on b.COMPID = a.COMPID left join subcomponent c on c.SUBCOMPID = b.SUBCOMPID where "
				+ "(a.status <> 1 or a.status is null) and (c.status <> 1 or c.status is null) group by a.COMPID";
		logger.info("Query all components sql:" + sql);

		List<Component> componentList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						Component component = new Component();
						component.setCompId(rs.getInt("compid"));
						component.setCompName(rs.getString("compname"));
						component.setSubCompNames(rs.getString("subCompNames"));
						return component;
					}
				});

		logger.info("result:" + componentList);
		return componentList;
	}

	public List<Component> listComponentsForFeature() throws Exception {
		//String sql = "select * from component";
		String sql = "SELECT * FROM component where (status <> 1 or status is null) ";
		logger.info("listComponentsForFeature sql:" + sql);

		@SuppressWarnings("unchecked")
		List<Component> componentList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						Component component = new Component();
						component.setCompId(rs.getInt("compid"));
						component.setCompName(rs.getString("compname"));
						return component;
					}
				});

		logger.info("result:" + componentList);
		return componentList;
	}
	
	public int queryComponentSize(Component component) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != component.getCompId()) {
			sql = "select count(*) from component where (status <> 1 or status is null) and compName = "
					+ "'"+ component.getCompName() + "'  and compId !='" + component.getCompId() + "'";
			logger.info("queryByComponentNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from component where (status <> 1 or status is null) and compName ='"+ component.getCompName() + "'  ";
			logger.info("queryByComponentNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByComponentNameSize method result:" + result);
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public List<Component> listComponentsByFeatureId(String featureId)
			throws Exception {
		//String sql = "SELECT * from component as comp WHERE EXISTS (SELECT * from component_feature as map WHERE map.compid = comp.COMPID and map.featureid="
			//	+ featureId + ")";
		String sql = "select a.* from component as a left join component_feature b on a.COMPID = b.COMPID where"
				+ " (b.status <> 1 or b.status is null) and b.FEATUREID = '"+ featureId +"'";
		logger.info("sql:" + sql);

		List<Component> componentList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {
						Component component = new Component();
						component.setCompId(rs.getInt("compid"));
						component.setCompName(rs.getString("compname"));
						return component;
					}
				});

		logger.info("result:" + componentList);
		return componentList;
	}

	public Component queryComponentById(int id) throws Exception {

		String sql = "select * from component where compid='" + id + "'";

		logger.info("sql:" + sql);
		final Component component = new Component();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapComponentFromRs(component, rs);

			}
		});

		logger.info("result:" + component);
		return component;
	}

	public int updateComponent(final Component component) throws Exception {
		String sql = "update component set compname=? where compid="
				+ component.getCompId();
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapComponentToPs(component, ps);
					}
				});
		logger.info("result:" + result);
		return result;
	}

	public List<?> querySubComponentList(String compId) {
		String sql = "select a.* from subcomponent a,component_subcomponent b where a.subcompid = b.subcompid and (a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) ";
		if (null != compId && !"-1".equals(compId) && !"".equals(compId)) {
			sql += " and b.compid = " + compId;
		}
		logger.info(sql);
		return userJdbcTemplate.queryForList(sql);
	}

	public static void mapComponentFromRs(final Component component,
			ResultSet rs) throws SQLException {
		component.setCompId(rs.getInt("compid"));
		component.setCompName(rs.getString("compname"));
	}

	public int deleteBatchCompSubComp(final String compId,
			final String[] subComps) throws Exception {

		String sql = "delete from component_subcomponent where compid=? and subcompid=? ";
		logger.info("sql:" + sql);

		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(compId));
						ps.setInt(2, Integer.parseInt(subComps[i].trim()));
					}

					@Override
					public int getBatchSize() {

						return subComps.length;
					}
				});

		return results.length;

	}

	public int deleteBatchCompSubComp(final String compId,
			final List<String> subComps) throws Exception {

		String sql = "delete from component_subcomponent where compid=? and subcompid=? ";
		logger.info("sql:" + sql);

		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(compId));
						ps.setInt(2, Integer.parseInt(subComps.get(i)));
					}

					@Override
					public int getBatchSize() {

						return subComps.size();
					}
				});

		return results.length;

	}

	public int addBatchCompSubComp(final String compId,
			final List<String> subComps) throws Exception {
		String sql = "insert into component_subcomponent(compid,subcompid) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(compId));
						ps.setInt(2, Integer.parseInt(subComps.get(i)));

					}

					public int getBatchSize() {

						return subComps.size();
					}
				});

		return results.length;

	}

	public int addBatchCompSubComp(final String compId, final String[] subComps)
			throws Exception {
		String sql = "insert into component_subcomponent(compid,subcompid) values(?,?)";
		logger.info("sql:" + sql);
		int[] results = userJdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						ps.setInt(1, Integer.parseInt(compId));
						ps.setInt(2, Integer.parseInt(subComps[i].trim()));

					}

					public int getBatchSize() {

						return subComps.length;
					}
				});

		return results.length;

	}

	public static void mapComponentToPs(final Component component,
			PreparedStatement ps) throws SQLException {

		if (Utils.isNullORWhiteSpace(component.getCompName())) {

			ps.setArray(1, null);
		} else {
			ps.setString(1, component.getCompName());
		}

	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
