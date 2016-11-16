package com.intel.cid.common.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.JsonUtil;
import com.opensymphony.xwork2.ActionContext;

public class ExecutionPhaseAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(ExecutionPhaseAction.class);

	private int phaseId;
	private String phaseName;
	private int projectId;
	
	public String listExecutionPhases() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Phase> phaseList = executionPhaseService.listAllPhases();

		context.put("phaseList", phaseList);

		return SUCCESS;

	}


	public String delExecutionPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		executionPhaseService.delPhaseById(phaseId);

		return SUCCESS;
	}

	public String toUpdateExecutionPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Phase phase = executionPhaseService.queryPhaseById(phaseId);
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		context.put("phase", phase);

		return SUCCESS;

	}

	public String updateExecutionPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Phase phase = new Phase();
		phase.setPhaseId(phaseId);
		phase.setPhaseName(phaseName);
		phase.setProjectId(projectId);

		int result = executionPhaseService.queryEphaseSize(phase);
		if(result==1){
			Project project = projectSerivce.queryProjectById(phase.getProjectId());
			context.put(Constant.ERRORMSG, "Execution type name '" + phase.getPhaseName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		executionPhaseService.updatePhase(phase);
		logger.info(phase);
		return SUCCESS;

	}

	public String toAddExecutionPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addExecutionPhase() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Phase phase = new Phase();
		phase.setPhaseId(phaseId);
		phase.setPhaseName(phaseName);
		phase.setProjectId(projectId);

		int result = executionPhaseService.queryEphaseSize(phase);
		if(result==1){
			Project project = projectSerivce.queryProjectById(phase.getProjectId());
			context.put(Constant.ERRORMSG, "Execution type name '" + phase.getPhaseName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		executionPhaseService.addPhase(phase);

		return SUCCESS;

	}

	public int getphaseId() {
		return phaseId;
	}

	public void setphaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public String getphaseName() {
		return phaseName;
	}

	public void setphaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
