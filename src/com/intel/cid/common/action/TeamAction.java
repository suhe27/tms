package com.intel.cid.common.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TeamAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TeamAction.class);

	private int teamId;

	private String teamName;

	
	public String tranformTestCase() throws  Exception{
		
		ActionContext context = ActionContext.getContext();
		
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		List<File> fileList = new ArrayList<File>();
		
		String desDir = Utils.getProjectPath(context)+"origin";
		File desFile = new File(desDir);
		if (!desFile.exists()) {
			
			desFile.mkdirs();
		}
		
		File files[] = desFile.listFiles();
		for (File file : files) {
			fileList.add(file);
		}
		
		transformService.updateBatchTestCaseForXLS(context, fileList);
		
		return SUCCESS;
	}
	
	
	
	
	public String listTeams() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		List<Team> teamList = teamService.listTeams();
		context.put("teamList", teamList);

		return SUCCESS;

	}

	public String delTeam() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		teamService.delTeamById(teamId);

		return SUCCESS;
	}

	public String toUpdateTeam() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		};

		Team team = teamService.queryTeamById(teamId);
		context.put("team", team);

		return SUCCESS;

	}

	public String updateTeam() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Team team = new Team();
		team.setTeamId(teamId);
		team.setTeamName(teamName);

		int result = teamService.queryTeamSize(team);
		if(result==1){
			context.put(Constant.ERRORMSG, "Team name '" + team.getTeamName() + "' already exist!");
			return ERROR;
		}
		
		teamService.updateTeam(team);
		return SUCCESS;

	}

	public String toAddTeam() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		return SUCCESS;

	}

	public String addTeam() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Team team = new Team();
		team.setTeamId(teamId);
		team.setTeamName(teamName);
		
		int result = teamService.queryTeamSize(team);
		if(result==1){
			context.put(Constant.ERRORMSG, "Team name '" + team.getTeamName() + "' already exist!");
			return ERROR;
		}
		
		teamService.addTeam(team);

		return SUCCESS;

	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
