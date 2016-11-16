package com.intel.cid.common.dao;

import java.util.List;
import java.util.Map;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestResult;

public interface TestResultDao {

	public int addBatchResult(final List<TestResult> resultList)
			throws Exception;

	public int delTestResults(final String[] subTestPlans) throws Exception;

	public int delBatchTestResult(final List<SubTestPlan> subTestPlanList)
	throws Exception;
	int updateBatchTestResult(List<TestResult> resultList) throws Exception;

	List<TestResult> listTestResultByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception;

	int queryTestResultSize(Map<String, String> filterMap) throws Exception;

	TestResult queryTestResult(int resultId) throws Exception;

	public List<ResultType> listResultTypes() throws Exception;


}
