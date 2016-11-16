package com.intel.cid.common.bean;


public class CaseMap {

    private  int caseMapId;
    
    private int oldTestcaseId;
    
    private int newTestCaseId;

    private String newVersion;
    public int getCaseMapId() {
        return caseMapId;
    }

    public void setCaseMapId(int caseMapId) {
        this.caseMapId = caseMapId;
    }

    public int getOldTestcaseId() {
        return oldTestcaseId;
    }

    public void setOldTestcaseId(int oldTestcaseId) {
        this.oldTestcaseId = oldTestcaseId;
    }

    public int getNewTestCaseId() {
        return newTestCaseId;
    }

    public void setNewTestCaseId(int newTestCaseId) {
        this.newTestCaseId = newTestCaseId;
    }


    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String logInfo() {
       return "CaseMap [caseMapId=" + caseMapId + ", newTestCaseId=" + newTestCaseId + ", oldTestcaseId="
                + oldTestcaseId + ", newVersion="
                + newVersion + "]" ;
    }
    
    
}
