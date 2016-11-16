package com.intel.cid.common.bean;

public class PerformanceResult {
	private int performanceResultId;
	private int resultTrackId;
	private int testResultId;
	private int elementIndex;
	private String attributeName;
	private String attributeValue;
	private String createDate;
	private String modifyDate;
	private String performance;
	private int testCaseId;
	private String subExecutionName;
	private String testCaseName;
	
	public String getSubExecutionName() {
		return subExecutionName;
	}
	public void setSubExecutionName(String subExecutionName) {
		this.subExecutionName = subExecutionName;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public int getPerformanceResultId() {
		return performanceResultId;
	}
	public void setPerformanceResultId(int performanceResultId) {
		this.performanceResultId = performanceResultId;
	}
	
	public int getResultTrackId() {
		return resultTrackId;
	}
	public void setResultTrackId(int resultTrackId) {
		this.resultTrackId = resultTrackId;
	}
	
	public int getTestResultId() {
		return testResultId;
	}
	public void setTestResultId(int testResultId) {
		this.testResultId = testResultId;
	}
	
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
	
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
}
