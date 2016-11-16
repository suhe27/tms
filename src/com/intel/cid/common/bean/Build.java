package com.intel.cid.common.bean;

import org.apache.log4j.Logger;

public class Build {
    
    private static Logger logger = Logger.getLogger(Build.class);
    
    private int buildId;
    private String buildType;
	private String  projectName;
	private  int projectId;  
  


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
        logger.info("BuildType [buildId=" + buildId + ", buildType=" + buildType +"]");
    }
}
