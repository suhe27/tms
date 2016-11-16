package com.intel.cid.common.bean;

import java.util.ArrayList;
import java.util.List;

public class TestPlan {

	private int testPlanId;
	private int projectId;
	private int owner;
	private String planName;
	private int phaseId;
	private String createDate;
	private String modifyDate;
	private int totalCases;
	private String startDate;
	private String endDate;
	private String description;
	private Phase Phase;
	private SubTestPlan subPlan;
	private User user;
	private Project project;
	private List<SubTestPlan> subPlanList = new ArrayList<SubTestPlan>();
	private List<TestResult> testResult = new ArrayList<TestResult>();

	public int getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
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

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	
	public Phase getPhase() {
		return Phase;
	}

	public void setPhase(Phase phase) {
		Phase = phase;
	}

	public SubTestPlan getSubPlan() {
		return subPlan;
	}

	public void setSubPlan(SubTestPlan subPlan) {
		this.subPlan = subPlan;
	}

	public List<SubTestPlan> getSubPlanList() {
		return subPlanList;
	}

	public void setSubPlanList(List<SubTestPlan> subPlanList) {
		this.subPlanList = subPlanList;
	}



	public List<TestResult> getTestResult() {
		return testResult;
	}

	public void setTestResult(List<TestResult> testResult) {
		this.testResult = testResult;
	}

	public String logInfo() {
		return "TestPlan [ tesplanId=" + testPlanId + ", createDate="
				+ createDate + ", planName=" + planName + ", modifyDate="
				+ modifyDate + ", projectId=" + projectId + ", phaseId="
				+ phaseId + ", testPlanId=" + testPlanId + ", Owner="
				+ owner + ", TotalCases=" + totalCases + ", Project=" + project
				+ "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
