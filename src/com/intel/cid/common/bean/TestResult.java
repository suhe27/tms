package com.intel.cid.common.bean;

public class TestResult {

	private int resultId;
	
	private int resultNum;
	
	private String featureName;
	
	private String subExecutionName;
	
	private String resultForExcel;

	private int subExecutionId;
	
	private int executionId;
	
	private int resultTrackId;
	
	private int testPlanId;
	
	private int osId;
	
	private int platformId;

	private int subPlanId;
	
	private  int testunitId;
	
	private String tag;
	
	private int targetId;

	private int testCaseId;

	private String testCaseName;
	
	private String targetName;
	
	private String suiteName;
	
	private String platformName;
	
	private String osName;
	
	private String resultTypeName;
	
	private String executionSteps;
	
	private String expectedResult;
	
	private String description;
	
	private int testtypeId;
	
	private int resultTypeId;

	private String log;
	
	private String executionName;
	
	private String projectName;

	private String bugId;
	
	private String bugName;

	private String comments;

	private String modifyDate;
	private String executeDay;
	private String configFiles;
	private String autoName;
	private String testScript;
	private String testfunctionCall;
	private int timeout;
	private String testTypeName;
	private String testCasealiasId;
	private int elementIndex;
	private String attributeName;
	private String attributeValue;
	
	public int getElementIndex() {
		return elementIndex;
	}
	public void setElementIndex(int elementIndex) {
		this.elementIndex = elementIndex;
	}
		
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getTestCasealiasId() {
		return testCasealiasId;
	}

	public void setTestCasealiasId(String testCasealiasId) {
		this.testCasealiasId = testCasealiasId;
	}
	
	public String getExecuteDay() {
		return executeDay;
	}
	public void setExecuteDay(String executeDay) {
		this.executeDay = executeDay;
	}
	
	public String getAutoName() {
		return autoName;
	}
	public void setAutoName(String autoName) {
		this.autoName = autoName;
	}
	
	public String getSubExecutionName() {
		return subExecutionName;
	}
	public void setSubExecutionName(String subExecutionName) {
		this.subExecutionName = subExecutionName;
	}
	
	public String getConfigFiles() {
		return configFiles;
	}
	public void setConfigFiles(String configFiles) {
		this.configFiles = configFiles;
	}
	public String getTestfunctionCall() {
		return testfunctionCall;
	}

	public void setTestfunctionCall(String testfunctionCall) {
		this.testfunctionCall = testfunctionCall;
	}
	public int getTimeout() {
		return timeout;
	}
	public String getTestTypeName() {
		return testTypeName;
	}

	public void setTestTypeName(String testTypeName) {
		this.testTypeName = testTypeName;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getTestScript() {
		return testScript;
	}

	public void setTestScript(String testScript) {
		this.testScript = testScript;
	}
	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	
	public int getResultTrackId() {
		return resultTrackId;
	}

	public void setResultTrackId(int resultTrackId) {
		this.resultTrackId = resultTrackId;
	}
	
	public int getResultNum() {
		return resultNum;
	}

	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	public int getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}

	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public int getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(int resultTypeId) {
		this.resultTypeId = resultTypeId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	public String getBugId() {
		return bugId;
	}
	
	public void setBugName(String bugName) {
		this.bugName = bugName;
	}
	public String getBugName() {
		return bugName;
	}
	
	public String getResultTypeName() {
		return resultTypeName;
	}

	public void setResultTypeName(String resultTypeName) {
		this.resultTypeName = resultTypeName;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getResultForExcel() {
		return resultForExcel;
	}

	public void setResultForExcel(String resultForExcel) {
		this.resultForExcel = resultForExcel;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	

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

	public int getTestunitId() {
		return testunitId;
	}

	public void setTestunitId(int testunitId) {
		this.testunitId = testunitId;
	}

	public String getTag() {
		return tag;
	}
	
	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getTesttypeId() {
		return testtypeId;
	}

	public void setTesttypeId(int testtypeId) {
		this.testtypeId = testtypeId;
	}
	public String getExecutionName() {
		return executionName;
	}

	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	
	
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
}
