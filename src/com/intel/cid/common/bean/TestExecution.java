package com.intel.cid.common.bean;

import java.util.ArrayList;
import java.util.List;

public class TestExecution {
	private int executionId;
	private int id;
	private int projectId;
	private int userId;
	private int osId;
	private int platformId;
	private int buildId;
	private int phaseId;
	private int targetId;
	private String targetName;
	private String testPlanName;
	private int testPlanId;
	private String executionName;
	private String releaseCycle;
	private int ownner;
	private int pass;
	private int fail;
	private int notRun;
	private int block;
	private Double passrate;
	private Double executeRate;
	private int totalCases;
	private int tesId;
	private String testCaseName;
	private int testCaseId;
	private String createDate;
	private String modifyDate;
	private String startDate;
	private String endDate;
	private String userName;
	private String phaseName;
	private String buildTypeName;
	private String desc;
	private String subExecutionDesc;
	private String osName;
	private String platFormName;
	private String projectName;
	private String testenvName;
	private int tester;
	private int cycleId;
	private int envId;
	private int nicId;
	private String subExecutionName;
	private int subPlanId;
	private String dueDate;
	private int subExecutionId;
	private String reportPass;
	private String reportBlock;
	private String reportFail;
	private String reportNotrun;
	private String reportTotalCases;
	private String reportNames;
	private Project project;
	private User user;
	private TestPlan testPlan;
	private List<List<String>> table = new ArrayList<List<String>>();
	private List<String> tableHead = new ArrayList<String>();
	private String perfData;
	private String perfExecutions;
	private String perfAttributes;
	
	public String getPerfData() {
		return perfData;
	}
	public void setPerfData(String perfData) {
		this.perfData = perfData;
	}
	
	public String getPerfExecutions() {
		return perfExecutions;
	}
	public void setPerfExecutions(String perfExecutions) {
		this.perfExecutions = perfExecutions;
	}
	
	public String getPerfAttributes() {
		return perfAttributes;
	}
	public void setPerfAttributes(String perfAttributes) {
		this.perfAttributes = perfAttributes;
	}
	
	public List<List<String>> getTable() {
		return table;
	}
	public void setTable(List<List<String>> table) {
		this.table = table;
	}
	
	public List<String> getTableHead() {
		return tableHead;
	}
	public void setTableHead(List<String> tableHead) {
		this.tableHead = tableHead;
	}
	
	public String getReportPass() {
		return reportPass;
	}
	public void setReportPass(String reportPass) {
		this.reportPass = reportPass;
	}
	
	public String getReportFail() {
		return reportFail;
	}
	public void setReportFail(String reportFail) {
		this.reportFail = reportFail;
	}
	
	public String getReportBlock() {
		return reportBlock;
	}
	public void setReportBlock(String reportBlock) {
		this.reportBlock = reportBlock;
	}
	
	public String getReportTotalCases() {
		return reportTotalCases;
	}
	public void setReportTotalCases(String reportTotalCases) {
		this.reportTotalCases = reportTotalCases;
	}
	
	public String getReportNames() {
		return reportNames;
	}
	public void setReportNames(String reportNames) {
		this.reportNames = reportNames;
	}
	
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getReportNotrun() {
		return reportNotrun;
	}
	public void setReportNotrun(String reportNotrun) {
		this.reportNotrun = reportNotrun;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public int getSubExecutionId() {
		return subExecutionId;
	}

	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}
	
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getSubExecutionDesc() {
		return subExecutionDesc;
	}

	public void setSubExecutionDesc(String subExecutionDesc) {
		this.subExecutionDesc = subExecutionDesc;
	}

	
	public String getSubExecutionName() {
		return subExecutionName;
	}

	public void setSubExecutionName(String subExecutionName) {
		this.subExecutionName = subExecutionName;
	}
	

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}
	
	
	public int getEnvId() {
		return envId;
	}

	public void setEnvId(int envId) {
		this.envId = envId;
	}
	
	public int getNicId() {
		return nicId;
	}

	public void setNicId(int nicId) {
		this.nicId = nicId;
	}
	
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	
	public int getCycleId() {
		return cycleId;
	}

	public void setCycleId(int cycleId) {
		this.cycleId = cycleId;
	}
	
	public int getTester() {
		return tester;
	}

	public void setTester(int tester) {
		this.tester = tester;
	}
	
	public int getTesId() {
		return tesId;
	}

	public void setTesId(int tesId) {
		this.tesId = tesId;
	}
	
	
	public String getTestenvName() {
		return testenvName;
	}

	public void setTestenvName(String testenvName) {
		this.testenvName = testenvName;
	}
	
	public String getPlatFormName() {
		return platFormName;
	}

	public void setPlatFormName(String platFormName) {
		this.platFormName = platFormName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	
	public String getBuildTypeName() {
		return buildTypeName;
	}

	public void setBuildTypeName(String buildTypeName) {
		this.buildTypeName = buildTypeName;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getTestPlanName() {
		return testPlanName;
	}

	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}
	
	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}
	
	public int getBuildId() {
		return buildId;
	}

	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	
	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	
	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public int getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	public int getOwnner() {
		return ownner;
	}

	public void setOwnner(int ownner) {
		this.ownner = ownner;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getNotRun() {
		return notRun;
	}

	public void setNotRun(int notRun) {
		this.notRun = notRun;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public String getExecutionName() {
		return executionName;
	}

	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}

	public String getReleaseCycle() {
		return releaseCycle;
	}

	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}

	public Double getPassrate() {
		return passrate;
	}

	public void setPassrate(Double passrate) {
		this.passrate = passrate;
	}

	public Double getExecuteRate() {
		return executeRate;
	}

	public void setExecuteRate(Double executeRate) {
		this.executeRate = executeRate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getExecutionId() {
		return executionId;
	}

	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String logInfo() {
		return "TestExecution [duedate="+dueDate+ ",tester="+ tester +" , block=" + block + ", createDate=" + createDate
		+ ", desc=" + desc + ", endDate=" + endDate + ", executerate="
		+ executeRate + ", executionId=" + executionId
		+ ", executionName=" + executionName + ", fail=" + fail
		+ ", modifyDate=" + modifyDate + ", notRun=" + notRun
		+ ", ownner=" + ownner + ", pass=" + pass + ", passrate="
		+ passrate + ", projectId=" + projectId + ", releaseCycle="
		+ releaseCycle + ", startDate=" + startDate + ", testPlanId="
		+ testPlanId + ", totalCases=" + totalCases + ", phaseId=" + phaseId + "]";
	}
}
