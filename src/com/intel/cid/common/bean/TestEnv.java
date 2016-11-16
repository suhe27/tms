package com.intel.cid.common.bean;

import org.apache.log4j.Logger;

public class TestEnv {

	private static Logger logger = Logger.getLogger(TestEnv.class);

	private int id;
	private int projectId;
	private String envName;
	private String  projectName;
	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void logInfo() {
		logger.info("Testenv [Id=" + id + ", desc=" + desc + ", projectId=" + projectId + ", envName=" + envName + "]");
	}
}
