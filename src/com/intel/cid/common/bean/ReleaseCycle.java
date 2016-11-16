package com.intel.cid.common.bean;


public class ReleaseCycle {

	private String projectName;
	private String releaseCycle = "";
	private String duration = "";
	private String apiVersion;
	private String packageInfo;
	private int totalCases;
	private int pass;
	private int fail;
	private int block;
	private int notRun;
	private float passRate;
	private String status;
	private int caseNum;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getReleaseCycle() {
		return releaseCycle;
	}
	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
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
	public int getBlock() {
		return block;
	}
	public void setBlock(int block) {
		this.block = block;
	}
	public int getNotRun() {
		return notRun;
	}
	public void setNotRun(int notRun) {
		this.notRun = notRun;
	}
	public float getPassRate() {
		return passRate;
	}
	public void setPassRate(float passRate) {
		this.passRate = passRate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(int caseNum) {
		this.caseNum = caseNum;
	}
	
}