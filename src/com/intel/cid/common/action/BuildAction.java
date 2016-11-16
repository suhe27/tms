package com.intel.cid.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Build;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class BuildAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(BuildAction.class);

	private int buildId;
	private String buildType;
	private int projectId;

	public String listbuilds() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Build> buildList = buildService.listAllBuilds();

		context.put("buildList", buildList);

		return SUCCESS;

	}

	public String delBuild() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		buildService.delBuildById(buildId);;

		return SUCCESS;
	}

	public String toupdateBuild() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Build build = buildService.queryBuildById(buildId);
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		context.put("build", build);

		return SUCCESS;

	}

	public String updateBuild() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Build build = new Build();
		build.setBuildId(buildId);
		build.setBuildType(buildType);
		build.setProjectId(projectId);

		int result = buildService.queryBuildSize(build);
		if(result==1){
			Project project = projectSerivce.queryProjectById(build.getProjectId());
			context.put(Constant.ERRORMSG, "Build type name '" + build.getBuildType() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		buildService.updateBuild(build);
		logger.info(build);
		return SUCCESS;

	}

	public String toBuildAdd() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addBuild() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Build build = new Build();
		build.setBuildId(buildId);
		build.setBuildType(buildType);
		build.setProjectId(projectId);

		int result = buildService.queryBuildSize(build);
		if(result==1){
			Project project = projectSerivce.queryProjectById(build.getProjectId());
			context.put(Constant.ERRORMSG, "Build type name '" + build.getBuildType() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		buildService.addBuild(build);

		return SUCCESS;

	}

	public int getBuildId() {
		return buildId;
	}

	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}	

}
