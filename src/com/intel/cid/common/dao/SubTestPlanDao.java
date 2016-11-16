package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.SubTestPlan;

public interface SubTestPlanDao {

	int addSubTestPlan(SubTestPlan testPlan) throws Exception;

	
	
	public int delBatchSubTestPlan(final String[] subTestPlans)
			throws Exception;

	int updateSubTestPlan(SubTestPlan testPlan) throws Exception;

	public List<SubTestPlan> listSubTestPlan(int testPlanId) throws Exception;

	
	SubTestPlan querySubTestPlanById(int planId) throws Exception;

	int delSubTestPlan(SubTestPlan testPlan) throws Exception;

	int delSubTestPlanById(int planId) throws Exception;
	
	
	

}
