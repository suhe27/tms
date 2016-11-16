package com.intel.cid.common.bean;

public class Feature {
    private int featureId;

    private int projectId;

    private String featureName;
    
    private String projectName;
    
    private String compNames;

    private Project project;
    
    
    

    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }  
    
    public String getCompNames() {
        return compNames;
    }

    public void setCompNames(String compNames) {
        this.compNames = compNames;
    } 

    public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String logInfo() {
        return "Feature [featureId=" + featureId + ", featureName=" + featureName 
               + "]";
    }

}
