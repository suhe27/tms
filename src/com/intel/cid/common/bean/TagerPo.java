package com.intel.cid.common.bean;

public class TagerPo {

	private int id;
	private int executionId;
	private int testPlanId;
	private int subPlanId;
	private String action;
	private String tag;
	private String createDate;
	public String logInfo() {
		return "TagerPo [ id=" + id + ", executionId=" + executionId
				+ ", testPlanId=" + testPlanId + ", subPlanId=" + subPlanId + ", action="
				+ action + ", tag=" + tag + ", createDate=" + createDate + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExecutionId() {
		return executionId;
	}
	public void setExecutionId(int executionId) {
		this.executionId = executionId;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
