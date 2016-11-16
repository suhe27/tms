package com.intel.cid.common.bean;


public class CaseState {

	private int caseStateId;

	private String caseStateName;

	public int getCaseStateId() {
		return caseStateId;
	}

	public void setCaseStateId(int caseStateId) {
		this.caseStateId = caseStateId;
	}

	public String getCaseStateName() {
		return caseStateName;
	}

	public void setCaseStateName(String caseStateName) {
		this.caseStateName = caseStateName;
	}

	public String logInfo() {
		return "CaseState [caseStateId=" + caseStateId + ", caseStateName="
				+ caseStateName + "]";
	}

}
