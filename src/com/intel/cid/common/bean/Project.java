package com.intel.cid.common.bean;

public class Project {

	private int projectId;

	private String projectName;

	private int teamId;

	private Team team;
	private int reId;
	private int featureId;
	public String teamName;
	public String featureData;
	public String autoData;
	
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
	
	public String getFeatureData() {
		return featureData;
	}
	public void setFeatureData(String featureData) {
		this.featureData = featureData;
	}

	public String getAutoData() {
		return autoData;
	}
	public void setAutoData(String autoData) {
		this.autoData = autoData;
	}
	
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getReId() {
		return reId;
	}

	public void setReId(int reId) {
		this.reId = reId;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String logInfo() {
		return "Project [projectId=" + projectId + ", projectName="
				+ projectName + ",projectName=" + projectName + ",teamName="
				+ teamName + "]";
	}
}
