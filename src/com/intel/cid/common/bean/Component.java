package com.intel.cid.common.bean;



public class Component {
	
	private int compId;

	private String compName;

	private String subCompNames;

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getSubCompNames() {
		return subCompNames;
	}

	public void setSubCompNames(String subCompNames) {
		this.subCompNames = subCompNames;
	}
	
	public String  logInfo() {
		return "Component [compId=" + compId + ", compName=" + compName
				+ "]";
	}

}
