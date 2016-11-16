package com.intel.cid.common.bean;

public class TestType {

    private int typeId;
    
    private String typeName;
    
	private int projectId;

	private String projectName;
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    
    public String logInfo() {
        return "TestType [typeId=" + typeId + ", typeName=" + typeName + "]";
    }
    
    
    
    
    
}
