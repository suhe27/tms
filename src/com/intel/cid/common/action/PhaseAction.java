package com.intel.cid.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class PhaseAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(PhaseAction.class);

	private int phaseId;

	private int projectId;
	
	private String phaseName;

	public String listPhases() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Phase> phaseList = phaseService.listAllPhases();

		context.put("phaseList", phaseList);

		return SUCCESS;

	}

	public String delPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		phaseService.delPhaseById(phaseId);

		return SUCCESS;
	}

	public String toUpdatePhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Phase phase = phaseService.queryPhaseById(phaseId);
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("projectList", projectList);
		context.put("phase", phase);

		return SUCCESS;

	}

	public String updatePhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Phase phase = new Phase();
		phase.setPhaseId(phaseId);
		phase.setPhaseName(phaseName);
		phase.setProjectId(projectId);

		int result = phaseService.queryPhaseSize(phase);
		if(result==1){
			Project project = projectSerivce.queryProjectById(phase.getProjectId());
			context.put(Constant.ERRORMSG, "Phase name '" + phase.getPhaseName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		phaseService.updatePhase(phase);
		logger.info(phase);
		return SUCCESS;

	}

	public String toAddPhase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("projectList", projectList);
		return SUCCESS;
	}

	public String addPhase() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Phase phase = new Phase();
		phase.setPhaseId(phaseId);
		phase.setPhaseName(phaseName);
		phase.setProjectId(projectId);

		int result = phaseService.queryPhaseSize(phase);
		if(result==1){
			Project project = projectSerivce.queryProjectById(phase.getProjectId());
			context.put(Constant.ERRORMSG, "Phase name '" + phase.getPhaseName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		phaseService.addPhase(phase);

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
