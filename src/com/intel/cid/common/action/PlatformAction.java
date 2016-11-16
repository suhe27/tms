package com.intel.cid.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class PlatformAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static  Logger logger = Logger.getLogger(PlatformAction.class);

	private int platformId;

	private String platformName;

	private int teamId;
	
	private int projectId;

	public String listPlatforms() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Platform> platformList = platformService.listAllPlatforms();
		context.put("platformList", platformList);

		return SUCCESS;

	}

	public String delPlatform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		platformService.delPlatformById(platformId);

		return SUCCESS;
	}

	public String toUpdatePlatform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Platform platform = platformService.queryPlatformById(platformId);
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		context.put("platform", platform);

		return SUCCESS;

	}

	public String updatePlatform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Platform platform = new Platform();
		platform.setPlatformId(platformId);
		platform.setPlatformName(platformName);
		platform.setProjectId(projectId);
		
		int result = platformService.queryPlatformSize(platform);
		if(result==1){
			Project project = projectSerivce.queryProjectById(platform.getProjectId());
			context.put(Constant.ERRORMSG, "Platform name '" + platform.getPlatformName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		platformService.updatePlatform(platform);
		logger.info(platform);
		return SUCCESS;

	}

	public String toAddPlatform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addPlatform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Platform platform = new Platform();
		platform.setPlatformId(platformId);
		platform.setPlatformName(platformName);
		platform.setProjectId(projectId);
		
		int result = platformService.queryPlatformSize(platform);
		if(result==1){
			Project project = projectSerivce.queryProjectById(platform.getProjectId());
			context.put(Constant.ERRORMSG, "Platform name '" + platform.getPlatformName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		platformService.addPlatform(platform);
		return SUCCESS;

	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
