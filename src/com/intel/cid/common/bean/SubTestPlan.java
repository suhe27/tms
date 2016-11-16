package com.intel.cid.common.bean;

import java.util.ArrayList;
import java.util.List;

public class SubTestPlan {
	private int subPlanId;

	private String subPlanName;
	
	private int projectId;

	private int testCaseId;
	
	private int subExecutionId;
	
	private int executionId;
	
	private int targetId;
	
	private int testUnitId;
	
	private int testSuiteId;
	
	private int testPlanId;
	
	private String dueDate;

	private String suiteName;
	
	private String projectName;
	
	private String userName;

	private int owner;

	
	private int totalCases;

	private String createDate;
	
	private String testCaseName;

	private String modifyDate;

	private TestSuite testSuite;
	
	private User user;
	
	private Project project;
	
	public int getSubExecutionId() {
		return subExecutionId;
	}

	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}

	public int getExecutionId() {
		return executionId;
	}

	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	private List<TestUnit> testUnitList = new ArrayList<TestUnit>();

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	
	public int getTestUnitId() {
		return testUnitId;
	}

	public void setTestUnitId(int testUnitId) {
		this.testUnitId = testUnitId;
	}

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	
	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}
	
	public int getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getSubPlanName() {
		return subPlanName;
	}

	public void setSubPlanName(String subPlanName) {
		this.subPlanName = subPlanName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	
	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	
	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}



	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
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

	

	public List<TestUnit> getTestUnitList() {
		return testUnitList;
	}

	public void setTestUnitList(List<TestUnit> testUnitList) {
		this.testUnitList = testUnitList;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String logInfo() {
		return "SubTestPlan [testCaseName="+testCaseName +" createDate=" + createDate + ", dueDate=" + dueDate
				+ ", modifyDate=" + modifyDate + ", owner=" + owner
				+ ", projectId=" + projectId + ", subPlanId=" + subPlanId
				+ ", subPlanName=" + subPlanName + ", testUnitList="
				+ testUnitList + ", totalCases=" + totalCases + ", user="
				+ user + "]";
	}

}
