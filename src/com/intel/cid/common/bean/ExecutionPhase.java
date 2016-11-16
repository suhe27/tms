package com.intel.cid.common.bean;

public class ExecutionPhase {

	private int phaseId;

	private String phaseName;

	private int projectId;

	private Project project;

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String logInfo() {
		return "ExecutionPhase [phaseId=" + phaseId + ", phaseName="
				+ phaseName + ", project=" + project + ", projectId="
				+ projectId + "]";
	}

}
