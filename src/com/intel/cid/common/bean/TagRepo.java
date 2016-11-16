package com.intel.cid.common.bean;

public class TagRepo {

	private int id;
	private int executionId;
	private int testPlanId;
	private int subPlanId;
	private String tagFlags;
	private String tag;
	private String createDate;

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

	public String getTagFlags() {
		return tagFlags;
	}

	public void setTagFlags(String tagFlags) {
		this.tagFlags = tagFlags;
	}

}
