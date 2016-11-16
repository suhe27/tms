package com.intel.cid.common.bean;

public class PlanMap {

    
    private int  planMapId;
    
    private int subPlanId;
    
    private int  testPlanId;

    public int getPlanMapId() {
        return planMapId;
    }

    public void setPlanMapId(int planMapId) {
        this.planMapId = planMapId;
    }

    public int getSubPlanId() {
        return subPlanId;
    }

    public void setSubPlanId(int subPlanId) {
        this.subPlanId = subPlanId;
    }

    public int getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(int testPlanId) {
        this.testPlanId = testPlanId;
    }

    
    public String logInfo() {
        return "PlanMap [planMapId=" + planMapId + ", subPlanId=" + subPlanId + ", testPlanId=" + testPlanId + "]";
    }
    
    
    
    
    
    
}
