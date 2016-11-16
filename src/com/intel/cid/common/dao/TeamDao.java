package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.Team;

public interface TeamDao {
	Team queryTeamById(int id) throws Exception;

	int delTeam(Team Team) throws Exception;

	int delTeamById(int id) throws Exception;

	int updateTeam(Team Team) throws Exception;

	int addTeam(Team Team) throws Exception;
	
	public List<Team> getUserTeam(String userName);
	
	public Team getTeam(String userName);
}
