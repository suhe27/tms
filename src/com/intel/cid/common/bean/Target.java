package com.intel.cid.common.bean;

import org.apache.log4j.Logger;

public class Target {

    
    private static Logger logger = Logger.getLogger(Target.class);

    
    
    private int targetId;
    
    private String  targetName;
    
    private String  projectName;
    
    private int projectId;
    
    private Project project;
    
  
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void logInfo() {
        logger.info("target [targetId=" + targetId + ", targetName=" + targetName +"]");
    }
}
