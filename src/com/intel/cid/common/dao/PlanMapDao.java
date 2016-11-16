package com.intel.cid.common.dao;

import com.intel.cid.common.bean.PlanMap;

public interface PlanMapDao {

    int addSubTestPlanMap(PlanMap planMap) throws Exception;

    int delBatchTestPlan(String[] testplans) throws Exception;

    int delSubTestPlan(int subPlanId, int testPlanId) throws Exception;

    
}
