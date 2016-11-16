package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class ProjectAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ProjectAction.class);
	private String projectName;
	private int projectId;
	private int teamId;
	public String queryProject() throws Exception {
		ActionContext contex = ActionContext.getContext();
		User user = (User) contex.getSession().get("user");
		if (user == null)
			return LOGIN;
		List<Project> listProject = projectSerivce.queryProjectByUserId(user.getUserId());
		contex.put("listProject", listProject);
		return SUCCESS;
	}

	public String delProjectById() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("projectId", Integer.toString(projectId));
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current project have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			projectSerivce.delProjectById(projectId);
			return SUCCESS;
		}
	}

	public String detailProject() throws Exception {
		ActionContext contex = ActionContext.getContext();
		User user = (User) contex.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Project project=projectSerivce.queryProjectById(projectId);
		contex.put("projectForm",project);
		List<Team> teamList = teamService.listTeams();
		contex.put("teamList", teamList);
		return SUCCESS;
	}

	public String updateProjectForm() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectName(projectName);
		project.setTeamId(teamId);
		
		int result = projectSerivce.queryProjectSize(project);
		if(result==1){
			context.put(Constant.ERRORMSG, "Project name '" + project.getProjectName() + "' already exist!");
			return ERROR;
		}
		
		projectSerivce.updateProject(project);
		return SUCCESS;

	}

	public String toAddProjectform() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Team> teamList = teamService.listTeams();
		context.put("teamList", teamList);
		return SUCCESS;

	}

	public String addPorject() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Project project = new Project();
		project.setProjectName(projectName);
		project.setTeamId(teamId);
		
		int result = projectSerivce.queryProjectSize(project);
		if(result==1){
			context.put(Constant.ERRORMSG, "Project name '" + project.getProjectName() + "' already exist!");
			return ERROR;
		}
		
		projectSerivce.addProject(project);
		return SUCCESS;

	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

}
