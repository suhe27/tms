package com.intel.cid.common.bean;

public class CaseSuiteMap {

    private int caseSuiteMapId;

    private int testCaseSuiteId;

    private int testCaseId;

    public int getCaseSuiteMapId() {
        return caseSuiteMapId;
    }

    public void setCaseSuiteMapId(int caseSuiteMapId) {
        this.caseSuiteMapId = caseSuiteMapId;
    }

    public int getTestCaseSuiteId() {
        return testCaseSuiteId;
    }

    public void setTestCaseSuiteId(int testCaseSuiteId) {
        this.testCaseSuiteId = testCaseSuiteId;
    }

    public int getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(int testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String logInfo() {
        return "CaseSuiteMap [caseSuiteMapId=" + caseSuiteMapId + ", testCaseId=" + testCaseId + ", testCaseSuiteId="
                + testCaseSuiteId + "]";
    }

}
