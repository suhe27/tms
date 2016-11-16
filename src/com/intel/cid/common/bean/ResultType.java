package com.intel.cid.common.bean;

public class ResultType {

	private int resultTypeId;

	private String resultTypeName;

	public int getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(int resultTypeId) {
		this.resultTypeId = resultTypeId;
	}

	public String getResultTypeName() {
		return resultTypeName;
	}

	public void setResultTypeName(String resultTypeName) {
		this.resultTypeName = resultTypeName;
	}


	public String logInfos() {
		return "ResultType [resultTypeId=" + resultTypeId + ", resultTypeName="
				+ resultTypeName + "]";
	}

	
	
}
