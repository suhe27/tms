package com.intel.cid.common.dao;

import java.util.List;
import java.util.Map;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.TestPlan;

public interface TestPlanDao {

	TestPlan queryTestPlan(int testPlanId) throws Exception;

	public TestPlan queryTestPlanBySubPlan(int subPlanId) throws Exception ;
	int delTestPlan(TestPlan testPlan) throws Exception;

	int delTestPlanById(int planId) throws Exception;

	public int delBatchTestPlan(final String[] testplans) throws Exception;

	int updateTestPlan(TestPlan testPlan) throws Exception;

	int addTestPlan(TestPlan testPlan) throws Exception;

	public List<TestPlan> listTestPlanByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception;

	public int queryTestPlanSize(Map<String, String> filterMap)
		throws Exception;
	
}
