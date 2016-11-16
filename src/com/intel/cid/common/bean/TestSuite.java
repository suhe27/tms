package com.intel.cid.common.bean;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {

	/* testcasesuit attributes */
	private int testSuiteId;

	private String testSuiteName;

	private int teamId;

	private String project;

	private String features;

	private String oses;

	private int userId;

	private int totalCases;
	
	private String createDate;

	private String modifyDate;

	private int projectId;
	/* testcasesuit relationship members */

	private User user;

	List<TestCase> testcaseList = new ArrayList<TestCase>();

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}



	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getOses() {
		return oses;
	}

	public void setOses(String oses) {
		this.oses = oses;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TestCase> getTestcaseList() {
		return testcaseList;
	}

	public void setTestcaseList(List<TestCase> testcaseList) {
		this.testcaseList = testcaseList;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String logInfo() {
		return "TestSuite [createDate=" + createDate + ", features=" + features
				+ ", modifyDate=" + modifyDate + ", oses=" + oses
				+ ", project=" + project + ", testSuiteId=" + testSuiteId
				+ ", testSuiteName=" + testSuiteName + ", totalCases="
				+ totalCases + ", user=" + user + ", userId=" + userId + "]";
	}

}
