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
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.dao.ProjectDao;

public class ProjectDaoImpl implements ProjectDao {
	private static Logger logger = Logger.getLogger(ProjectDaoImpl.class);
	private JdbcTemplate userJdbcTemplate;

	public int addProject(final Project project) {
		String sql = "insert into project (projectname,teamid) values('"
				+ project.getProjectName() + "','" + project.getTeamId() + "')";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;

	}

	public int delProjectById(int id) throws Exception {
		//String sql = "delete from  project where projectid='" + id + "'";
		String sql = "UPDATE `project` SET `STATUS`='1' WHERE projectid='" + id + "'";
		logger.info("sql: " + sql);
		int result = userJdbcTemplate.update(sql);
		return result;
	}

	public int updateProject(final Project project) throws Exception {
		String sql = "update project set teamid=?,projectName=? "
				+ " where projectid='" + project.getProjectId() + "'";
		logger.info("updateProject sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapProjectToPs(project, ps);

					}
				});
		return result;
	}

	public Project queryProjectById(int id) throws Exception {

		String sql = "select  * from project where projectid=" + id + "";

		logger.info("sql:" + sql);
		final Project project = new Project();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapProjectFromRs(rs, project);

			}
		});
		logger.info("Result:" + project);
		return project;
	}

	public int queryProjectSize(Project project) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != project.getProjectId()) {
			sql = "select count(*) from project where (status <> 1 or status is null) and projectName = '"+ project.getProjectName() + "'  and projectId !='"
			+ project.getProjectId() + "'";
			logger.info("queryByProjectNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from project where (status <> 1 or status is null) and projectName ='"+ project.getProjectName() + "'  ";
			logger.info("queryByProjectNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByProjectNameSize method result:" + result);
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> queryAllProjects() throws Exception {
		//String sql = "select * from project";
		String sql = " select * from project where (status <> 1 or status is null)";
		logger.info("sql:" + sql);
		List<Project> proList = userJdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Project project = new Project();
				mapProjectFromRs(rs, project);
				return project;

			}
		});
		logger.info("Result:" + proList);
		return proList;

	}

	public List<Project> queryProjectByUserId(int userId) throws Exception {
		//String sql = " SELECT * from project WHERE EXISTS ( SELECT * from user_team as map WHERE map.TEAMID = project.TEAMID and map.USERID='"
		//		+ userId + "')";
		String sql = "SELECT a.*,b.TEAMNAME from project a left join team b on a.teamid=b.teamid WHERE EXISTS "
				+ "( SELECT * from user_team as map WHERE (map.status <> 1 or map.status is null) and map.TEAMID = a.TEAMID "
				+ "and map.USERID='"+userId+"') and (a.status <> 1 or a.status is null)";
		logger.info("queryProjectByUserId sql:" + sql);
		@SuppressWarnings("unchecked")
		List<Project> ProjectformList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int count)
							throws SQLException {
						Project project = new Project();
						project.setProjectId(rs.getInt("projectid"));
						project.setProjectName(rs.getString("projectname"));
						project.setTeamId(rs.getInt("teamId"));
						project.setTeamName(rs.getString("teamname"));
						return project;
					}
				});

		logger.info("result:" + ProjectformList);
		return ProjectformList;
	}

	public List<?> queryPorjectByTeamId(String teamId) {
		String sql = " select * from project where teamId ='" + teamId + "'";
		logger.info(sql);
		return userJdbcTemplate.queryForList(sql);

	}

	public static void mapProjectFromRs(ResultSet rs, Project project)
			throws SQLException {
		project.setProjectId(rs.getInt("projectid"));
		project.setProjectName(rs.getString("projectname"));
		project.setTeamId(rs.getInt("teamId"));
	}

	public void mapProjectToPs(final Project project, PreparedStatement ps)
			throws SQLException {
		ps.setInt(1, project.getTeamId());
		ps.setString(2, project.getProjectName());
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
