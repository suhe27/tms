package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.dao.impl.TeamDaoImpl;

public class TeamService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TeamService.class);
	
	private TeamDaoImpl teamDaoImpl;

	public List<Team> listTeams() throws Exception
	{
		return teamDaoImpl.queryAllTeams();
		
	}
	
	public List<Team> getUserTeam(String userName) throws Exception
	{
		return teamDaoImpl.getUserTeam(userName);
		
	}
	
	public Team getTeam(String userName) throws Exception
	{
		return teamDaoImpl.getTeam(userName);
		
	}
	
	/**
	public Team queryTeamByProject(int ProjectId){
		
		return teamDaoImpl.queryTeamByProject(ProjectId);
	}
	**/
	
	public Team queryTeamById(int id) throws Exception{
        return teamDaoImpl.queryTeamById(id);
    }
	
	
	  public void delTeamById(int id )  throws Exception{
          
	      teamDaoImpl.delTeamById(id);
      }
      
      public void addTeam(Team team) throws Exception
      {
          teamDaoImpl.addTeam(team);
      }
      
  	  public int queryTeamSize(Team team) throws Exception {
		   return teamDaoImpl.queryTeamSize(team);
	  }
      
      public void updateTeam(Team team)throws Exception {
        
          
          teamDaoImpl.updateTeam(team);
    }
      
      public List<Team> listTeamByUserId(int  userId)  throws Exception{
    	  
    	  
    	  return teamDaoImpl.listTeamByUserId(userId);
      }
	
	public TeamDaoImpl getTeamDaoImpl() {
		return teamDaoImpl;
	}

	public void setTeamDaoImpl(TeamDaoImpl teamDaoImpl) {
		this.teamDaoImpl = teamDaoImpl;
	}
}
