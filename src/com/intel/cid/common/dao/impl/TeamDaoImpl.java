package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.dao.TeamDao;
import com.intel.cid.utils.Utils;

public class TeamDaoImpl implements TeamDao {

	private static Logger logger = Logger.getLogger(TeamDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public int addTeam(final Team team) throws Exception {

		String sql = "insert into team(teamname) values(?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTeamToPs(team, ps);

					}
				});
		logger.info("result:" + result);

		return result;
	}

	public int delTeam(Team team) throws Exception {

		return delTeamById(team.getTeamId());
	}

	public int delTeamById(int id) throws Exception {
		//String sql = "delete from team where teamid ='" + id + "'";
		String sql = "UPDATE `team` SET `STATUS`='1' WHERE teamid='" + id + "'";
		logger.info("Delete team sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result:" + result);
		return result;
	}

	public List<Team> queryAllTeams() throws Exception {

		String sql = " select * from team  where (status <> 1 or status is null)";
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<Team> teamList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {

				Team team = new Team();
				mapTeamFromRs(rs, team);
				return team;
			}
		});

		logger.info("result:" + teamList);
		return teamList;
	}

	public Team queryTeamById(int id) throws Exception {

		String sql = "select * from team where teamid='" + id + "'";
		logger.info("sql:" + sql);
		final Team team = new Team();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTeamFromRs(rs, team);

			}
		});

		logger.info("result:" + team);
		return team;
	}

	public int updateTeam(final Team team) throws Exception {
		String sql = "update team set teamname=? where teamid='"
				+ team.getTeamId() + "'";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapTeamToPs(team, ps);

					}
				});

		logger.info("result:" + result);
		return result;
	}
	
	public int queryTeamSize(Team team) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != team.getTeamId()) {
			sql = "select count(*) from team where (status <> 1 or status is null) and teamName = '"+ team.getTeamName() + "'  and teamId !='"
			+ team.getTeamId() + "'";
			logger.info("queryByTeamNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from team where (status <> 1 or status is null) and teamName ='"+ team.getTeamName() + "'  ";
			logger.info("queryByTeamNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByTeamNameSize method result:" + result);
		return size;
	}


	@SuppressWarnings("unchecked")
	public List<Team> listTeamByUserId(int userId) throws Exception {

		//String sql = "select * from team where exists( select * from user_team as map where map.teamid = team.teamid and map.userid="+ userId + ")";
		String sql = "select b.* from user_team a left join team b on b.TEAMID = a.TEAMID where (a.status <> 1 or a.status is null) and"
				+ " (b.status <> 1 or b.status is null) and userid = '"+userId+"'";
		logger.info("sql:" + sql);
		List<Team> teamList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int i) throws SQLException {
				Team team = new Team();
				mapTeamFromRs(rs, team);
				return team;
			}
		});

		return teamList;
	}

	/**
	public Team queryTeamByProject(int projectId) {

		String sql = " SELECT t.teamId,t.teamName from team t ,project where t.teamId=project.teamId and projectId='"
				+ projectId + "'";

		logger.info("sql:" + sql);
		final Team team = new Team();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTeamFromRs(rs, team);

			}
		});

		logger.info("result:" + team);
		return team;

	}
**/
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public static void mapTeamFromRs(ResultSet rs, Team team)
			throws SQLException {

		team.setTeamId(rs.getInt("teamid"));
		team.setTeamName(rs.getString("teamname"));
	}

	public static void mapTeamToPs(final Team team, PreparedStatement ps)
			throws SQLException {
		if (Utils.isNullORWhiteSpace(team.getTeamName())) {
			ps.setString(1, null);
		} else {
			ps.setString(1, team.getTeamName());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getUserTeam(String userName) {
		String sql = "select a.* from team a,user b where a.teamid = b.teamid and b.username = '"
				+ userName + "'";
		;
		logger.info("sql:" + sql);
		List<Team> teamList = userJdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				Team team = new Team();
				mapTeamFromRs(rs, team);
				return team;
			}
		});
		return teamList;
	}

	@Override
	public Team getTeam(String userName) {
		String sql = "select a.* from team a,user b where a.teamid = b.teamid and b.username = '"
				+ userName + "'";
		;
		List<?> list = userJdbcTemplate.queryForList(sql);
		Team team = new Team();
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<?, ?> lhm = (LinkedHashMap<?, ?>) list.get(i);
			team.setTeamId(Integer.parseInt(lhm.get("TEAMID").toString()));
			team.setTeamName(lhm.get("TEAMID").toString());
		}
		return team;
	}
}
