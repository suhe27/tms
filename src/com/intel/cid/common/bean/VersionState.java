package com.intel.cid.common.bean;


public class VersionState {

	private int versionId;

	private String versionName;

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String logInfo() {
		return "VersionState [versionId=" + versionId + ", versionName="
				+ versionName + "]";
	}

}
