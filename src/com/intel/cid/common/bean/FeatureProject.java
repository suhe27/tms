package com.intel.cid.common.bean;

public class FeatureProject {
	private int featureId;

	private int reId;

	private int projectId;

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public int getReId() {
		return reId;
	}

	public void setReId(int reId) {
		this.reId = reId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String logInfo() {
		return "FeaturePorject [featureId=" + featureId + ", projectId="
				+ projectId + ",reid=" + reId + "]";
	}

}
