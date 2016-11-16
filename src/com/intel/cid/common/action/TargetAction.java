package com.intel.cid.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.Target;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class TargetAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(TargetAction.class);

	private int targetId;

	private int projectId;
	
	private String targetName;

	public String listTarget() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Target> targetList = targetService.queryAllTarget();

		context.put("targetList", targetList);

		return SUCCESS;

	}

	public String delTargetById() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		targetService.delTargetById(targetId);

		return SUCCESS;
	}

	public String toUpdateTargetId() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Target target = targetService.queryTargetById(targetId);
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("projectList", projectList);
		context.put("target", target);

		return SUCCESS;

	}

	public String updateTargetId() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Target target = new Target();
		target.setTargetId(targetId);
		target.setTargetName(targetName);
		target.setProjectId(projectId);

		int result = targetService.queryTargetSize(target);
		if(result==1){
			Project project = projectSerivce.queryProjectById(target.getProjectId());
			context.put(Constant.ERRORMSG, "Target name '" + target.getTargetName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		targetService.updateTarget(target);
		logger.info(target);
		return SUCCESS;

	}

	public String toAddTarget() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("projectList", projectList);
		return SUCCESS;
	}

	public String addTarget() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Target target = new Target();
		target.setTargetName(targetName);
		target.setProjectId(projectId);
		
		int result = targetService.queryTargetSize(target);
		if(result==1){
			Project project = projectSerivce.queryProjectById(target.getProjectId());
			context.put(Constant.ERRORMSG, "Target name '" + target.getTargetName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		targetService.addTarget(target);

		return SUCCESS;

	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
}
