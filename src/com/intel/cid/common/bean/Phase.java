package com.intel.cid.common.bean;

import org.apache.log4j.Logger;

public class Phase {

	private static Logger logger = Logger.getLogger(Phase.class);

	private int phaseId;

	private String phaseName;
	
	private String  projectName;

	private  int projectId;
	
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

	public void logInfo() {
		logger.info("Phase [phaseId=" + phaseId + ", phaseName=" + phaseName + "]");
	}
}
