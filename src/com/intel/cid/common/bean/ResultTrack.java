package com.intel.cid.common.bean;

public class ResultTrack {
	private int resultTrackId;
	private int testResultId;
	private int resultTypeId;
	private String resultTypeName;
	private int display;
	private String comment;
	private String createDate;
	private String modifyDate;
	private String performance;
	private int testCaseId;
	private String testCaseName;
	
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
	
	public int getResultTypeId() {
		return resultTypeId;
	}
	public void setResultTypeId(int resultTypeId) {
		this.resultTypeId = resultTypeId;
	}
	
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
		
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getResultTypeName() {
		return resultTypeName;
	}
	public void setResultTypeName(String resultTypeName) {
		this.resultTypeName = resultTypeName;
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
	
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
}
