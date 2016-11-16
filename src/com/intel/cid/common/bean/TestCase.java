package com.intel.cid.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCase {
	/* testcase attributes */
	private int testCaseId;

	private int num;
	
	private int teamId;

	private int osId;
	
	private int platformId;

	private int featureId;

	private int compId;

	private int userId;

	private int testTypeId;

	private int subCompId;
	
	private int targetId;
	
	private int testUnitId;
	
	private int rowNumber;
	
	private int testPlanId;
	
	private int subExecutionId;

	private int executionId;
	
	private String testCasealiasId;

	private String testCaseName; 
	
	private String operation;

	private String requirementId;
	
	private String featureName;
	
	private String userName;
	
	private String teamName;

	private int projectId;

	private String configFiles;

	private String executionSteps;

	private String expectedResult;

	private int autoId;

	private String testScript;

	private String testfunctionCall;

	private String packagesizeRange;

	private int timeout;
	
	private String timeoutString;
	
	private String testCaseIdString;

	private int versionId;

	private int caseStateId;
	
	private int subPlanId;

	private String description;

	private String createDate;

	private String modifyDate;

	/** testcase relationship members */
	private Platform platform;

	private Team team;

	private OS os;

	private Component component;

	private SubComponent subComponent;

	private Feature feature;

	private User owner;

	private TestType testType;

	private Automation automation;

	private CaseState caseState;

	private VersionState versionState;
	
	private String projectName;
	
	private String compName;
	
	private String testTypeName;
	
	private String osName;
	
	private String autoName;
	
	private String caseStateName;
	
	private String versionName;
	
	private String subCompName;
	
	private Project project;
	
	private String resuleType="not run";
	
	private String dugId;
	
	private String log;
	
	private List<Map<String,String>> perfMap = new ArrayList<Map<String,String>>();
	
	public List<Map<String, String>> getPerfMap() {
		return perfMap;
	}

	public void setPerfMap(List<Map<String, String>> perfMap) {
		this.perfMap = perfMap;
	}

	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public int getTestPlanId() {
		return testPlanId;
	}
	
	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}
		
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


	public int getExecutionId() {
		return executionId;
	}

	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}
	
	public int getSubExecutionId() {
		return subExecutionId;
	}

	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}
	
	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}	

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getTestTypeId() {
		return testTypeId;
	}

	public void setTestTypeId(int testTypeId) {
		this.testTypeId = testTypeId;
	}

	public String getTimeoutString() {
		return timeoutString;
	}

	public void setTimeoutString(String timeoutString) {
		this.timeoutString = timeoutString;
	}
	
	public String getTestCaseIdString() {
		return testCaseIdString;
	}

	public void setTestCaseIdString(String testCaseIdString) {
		this.testCaseIdString = testCaseIdString;
	}
	
	public String getTestCasealiasId() {
		return testCasealiasId;
	}

	public void setTestCasealiasId(String testCasealiasId) {
		this.testCasealiasId = testCasealiasId;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getTestTypeName() {
		return testTypeName;
	}

	public void setTestTypeName(String testTypeName) {
		this.testTypeName = testTypeName;
	}
	
	public String getOSName() {
		return osName;
	}

	public void setOSName(String osName) {
		this.osName = osName;
	}
	
	public String getAutoName() {
		return autoName;
	}

	public void setAutoName(String autoName) {
		this.autoName = autoName;
	}
	
	public String getSubCompName() {
		return subCompName;
	}

	public void setSubCompName(String subCompName) {
		this.subCompName = subCompName;
	}
	
	public String getCaseStateName() {
		return caseStateName;
	}

	public void setCaseStateName(String caseStateName) {
		this.caseStateName = caseStateName;
	}
	
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getConfigFiles() {
		return configFiles;
	}

	public void setConfigFiles(String configFiles) {
		this.configFiles = configFiles;
	}

	public String getExecutionSteps() {
		return executionSteps;
	}

	public void setExecutionSteps(String executionSteps) {
		this.executionSteps = executionSteps;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public int getAutoId() {
		return autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public String getTestScript() {
		return testScript;
	}

	public void setTestScript(String testScript) {
		this.testScript = testScript;
	}
	
	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getTestfunctionCall() {
		return testfunctionCall;
	}

	public void setTestfunctionCall(String testfunctionCall) {
		this.testfunctionCall = testfunctionCall;
	}

	public String getPackagesizeRange() {
		return packagesizeRange;
	}

	public void setPackagesizeRange(String packagesizeRange) {
		this.packagesizeRange = packagesizeRange;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public int getCaseStateId() {
		return caseStateId;
	}

	public void setCaseStateId(int caseStateId) {
		this.caseStateId = caseStateId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public Automation getAutomation() {
		return automation;
	}

	public void setAutomation(Automation automation) {
		this.automation = automation;
	}

	public CaseState getCaseState() {
		return caseState;
	}

	public void setCaseState(CaseState caseState) {
		this.caseState = caseState;
	}

	public VersionState getVersionState() {
		return versionState;
	}

	public void setVersionState(VersionState versionState) {
		this.versionState = versionState;
	}

	public SubComponent getSubComponent() {
		return subComponent;
	}

	public void setSubComponent(SubComponent subComponent) {
		this.subComponent = subComponent;
	}

	public int getSubCompId() {
		return subCompId;
	}

	public void setSubCompId(int subCompId) {
		this.subCompId = subCompId;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String logInfo() {
		return "TestCase [automation=" + autoId + ", compId=" + compId
				+ ", component=" + component + ", createDate=" + createDate
				+ ", description=" + description + ", excutionSteps="
				+ executionSteps + ", expectedResult=" + expectedResult
				+ ", feature=" + feature + ", featureId=" + featureId
				+ ", modifyDate=" + modifyDate + ", os=" + os + ", osId="
				+ osId + ", owner=" + owner + ", packagesizeRange="
				+ packagesizeRange + ", platform=" + platform
				+ ",  configFiles=" + configFiles + ", projectId=" + projectId
				+ ", requirementId=" + requirementId + ", casestateid="
				+ caseStateId + ", team=" + team + ", teamId=" + teamId
				+ ", testCaseId=" + testCaseId + ", testScript=" + testScript
				+ ", testType=" + testType + ", testTypeId=" + testTypeId
				+ ", testcaseName=" + testCaseName + ", testcasealiasId="
				+ testCasealiasId + ", testfunctionCall=" + testfunctionCall
				+ ", timeout=" + timeout + ", userId=" + userId
				+ ", versionId=" + versionId + ", project=" + project + ", projectName=" + projectName + ","
			    + " resuleType=" + resuleType + ", dugid=" + dugId + ", log=" + log +"]";
	}



	
	public String getResuleType() {
		return resuleType;
	}

	public void setResuleType(String resuleType) {
		this.resuleType = resuleType;
	}

	public String getDugId() {
		return dugId;
	}

	public void setDugId(String dugId) {
		this.dugId = dugId;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
