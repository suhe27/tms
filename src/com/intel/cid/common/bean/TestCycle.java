package com.intel.cid.common.bean;

public class TestCycle {
	private String subPlanName;
	private int caseNumber;
	private int totalCase;
	private float caseRate;
	private int pass;
	private int fail;
	private int notRun;
	private int block;
	private float passRate;
	private String releaseCycle;
	public String getSubPlanName() {
		return subPlanName;
	}
	public void setSubPlanName(String subPlanName) {
		this.subPlanName = subPlanName;
	}
	public int getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}
	public int getTotalCase() {
		return totalCase;
	}
	public void setTotalCase(int totalCase) {
		this.totalCase = totalCase;
	}
	public float getCaseRate() {
		return caseRate;
	}
	public void setCaseRate(float caseRate) {
		this.caseRate = caseRate;
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
	public float getPassRate() {
		return passRate;
	}
	public void setPassRate(float passRate) {
		this.passRate = passRate;
	}
	public String getReleaseCycle() {
		return releaseCycle;
	}
	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}
}