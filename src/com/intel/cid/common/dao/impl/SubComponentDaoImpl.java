package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.dao.SubComponentDao;
import com.intel.cid.utils.Utils;

public class SubComponentDaoImpl implements SubComponentDao {

	private static Logger logger = Logger.getLogger(ComponentDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addSubComponent(final SubComponent subComponent)
			throws Exception {

		String sql = "insert into subcomponent(subcompname) values(?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapSubComponentToPs(subComponent, ps);

					}
				});
		logger.info("result:" + result);
		return result;
	}

	public int delSubComponent(SubComponent subComponent) throws Exception {

		return delSubComponentById(subComponent.getSubCompId());
	}

	public int delSubComponentById(int id) throws Exception {
		//String sql = "delete from subcomponent where subcompid='" + id + "'";
		String sql = "UPDATE `subcomponent` SET `STATUS`='1' WHERE subcompid='" + id + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SubComponent> queryAllSubComponents() throws Exception {
		//String sql = "select * from subcomponent";
		String sql = " select * from subcomponent where (status <> 1 or status is null)";
		logger.info("listSubComponentList sql:" + sql);
		List<SubComponent> subComponentList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						SubComponent subComponent = new SubComponent();
						mapSubComponentFromRs(subComponent, rs);

						return subComponent;
					}
				});

		return subComponentList;
	}

	public int querySubComponentSize(SubComponent subComponent) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != subComponent.getSubCompId()) {
			sql = "select count(*) from subcomponent where (status <> 1 or status is null) and subCompName = "
					+ "'"+ subComponent.getSubCompName() + "'  and subCompId !='" + subComponent.getSubCompId() + "'";
			logger.info("queryBySubComponentNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from subcomponent where (status <> 1 or status is null) and subCompName ='"+ subComponent.getSubCompName() + "'  ";
			logger.info("queryBySubComponentNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryBySubComponentNameSize method result:" + result);
		return size;
	}
	
	public SubComponent querySubComponentById(int id) throws Exception {
		String sql = "select * from subcomponent where subcompid='" + id + "'";

		logger.info("sql:" + sql);
		final SubComponent component = new SubComponent();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapSubComponentFromRs(component, rs);

			}
		});

		logger.info("result:" + component);
		return component;
	}

	public int updateSubComponent(final SubComponent subComponent) throws Exception {
		String sql = "update subcomponent set subcompname=? where subcompid="
				+ subComponent.getSubCompId();
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapSubComponentToPs(subComponent, ps);
					}
				});
		logger.info("result:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SubComponent> listSubComponentsByCompId(String compId) throws Exception {
		String sql = "SELECT * from subcomponent as subcomp WHERE EXISTS (SELECT * from component_subcomponent as map WHERE map.subcompid = subcomp.SUBCOMPID and map.compid="+compId+")";
		logger.info("sql:" + sql);

		List<SubComponent> subComponentList = userJdbcTemplate.query(sql,
				new RowMapper() {

					public Object mapRow(ResultSet rs, int count)
							throws SQLException {

						SubComponent subComponent = new SubComponent();
						mapSubComponentFromRs(subComponent, rs);

						return subComponent;
					}
				});

		logger.info("result:" + subComponentList);
		return subComponentList;
	}
	
	
	public static void mapSubComponentFromRs(final SubComponent subComponent,
			ResultSet rs) throws SQLException {

		subComponent.setSubCompId(rs.getInt("subcompid"));

		subComponent.setSubCompName(rs.getString("subcompname"));

	}

	public static void mapSubComponentToPs(final SubComponent subcomponent,
			PreparedStatement ps) throws SQLException {

		if (Utils.isNullORWhiteSpace(subcomponent.getSubCompName())) {

			ps.setArray(1, null);
		} else {
			ps.setString(1, subcomponent.getSubCompName());
		}

	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
