package com.intel.cid.common.bean;

public class PerfTestCaseConf {

	
	/**
	 *
	 * 
	 * describe testcase column&vlaue numbers 
	 * configurated in  perf_testresult_conf and 
	 * perf_testresult table
	 */
	private int id;
	
	private int testCaseId;
	
	private int occupy_fields;
	
	private String createDate;

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

	public int getOccupy_fields() {
		return occupy_fields;
	}

	public void setOccupy_fields(int occupyFields) {
		occupy_fields = occupyFields;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
	
	
	
}
