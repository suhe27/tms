package com.intel.cid.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.ResultTrack;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.dao.impl.SubTestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.utils.Utils;

public class TestResultService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TestResultService.class);

	private TestResultDaoImpl testResultDaoImpl;

	private SubTestPlanDaoImpl subTestPlanDaoImpl;

	private TestPlanDaoImpl testPlanDaoImpl;

	private TestUnitDaoImpl testUnitDaoImpl;

	/* query testresults with filter conditions : result */
	public List<TestResult> listTestResultByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		return testResultDaoImpl.listTestResultByPage(pageBean, filterMap);
	}

	public int queryTestResultSize(Map<String, String> filterMap)
			throws Exception {
		return testResultDaoImpl.queryTestResultSize(filterMap);
	}

	public List<ResultType> listResultTypes() throws Exception {

		return testResultDaoImpl.listResultTypes();
	}
	
	public int querySubExecutionCaseSize(Map<String, String> filterMap)
			throws Exception {
		return testResultDaoImpl.querySubExecutionCaseSize(filterMap);
	}
	
	public List<TestResult> listSubExecutionCaseByPage(PageBean pageBean,
			TestResult testresult) throws Exception {
		return testResultDaoImpl.listSubExecutionCaseByPage(pageBean, testresult);
	}
	
	public List<TestResult> listCaseExecutionFlow(int id) throws Exception {
		return testResultDaoImpl.listCaseExecutionFlow(id);
	}
	
	public List<TestResult> listTestResultsByExecutionId(int id) throws Exception {
		return testResultDaoImpl.listTestResultsByExecutionId(id);
	}
	
	public TestResult listTestResultsByExecutionIdAndTestCaseId(int executionId, int testCaseId) throws Exception {
		return testResultDaoImpl.listTestResultsByExecutionIdAndTestCaseId(executionId, testCaseId);
	}
	
	public TestResult listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(int executionId, int testCaseId, int targetId,int osId, int platformId) throws Exception {
		return testResultDaoImpl.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(executionId,testCaseId,targetId,osId,platformId);
	}

	public List<TestResult> listTestResultsByExecutionIds(String ids) throws Exception {
		return testResultDaoImpl.listTestResultsByExecutionIds(ids);
	}
	
	public List<TestResult> listTestResultsBySubExecutionId(int id) throws Exception {
		return testResultDaoImpl.listTestResultsBySubExecutionId(id);
	}
	
	public List<TestResult> listTestResultByPlanId(int id) throws Exception {
		return testResultDaoImpl.listTestResultByPlanId(id);
	}
	
	public List<TestResult> listFailedCaseInExecution(int id) throws Exception {
		return testResultDaoImpl.listFailedCaseInExecution(id);
	}
	
	public List<TestResult> listTestResultBySubExecutionId(int id) throws Exception {

		return testResultDaoImpl.listTestResultBySubExecutionId(id);
	}
	
	public List<TestResult> listTestResultByTestExecutionId(int id) throws Exception {

		return testResultDaoImpl.listTestResultByTestExecutionId(id);
	}
	
	public List<TestResult> listPerformanceResultByExecutionId(int id) throws Exception {

		return testResultDaoImpl.listPerformanceResultByExecutionId(id);
	}
	
	public List<TestResult> listPerformanceResultBySubExecutionId(int id) throws Exception {

		return testResultDaoImpl.listPerformanceResultBySubExecutionId(id);
	}
	
	public int addBatchTestResult(List<TestCase> testcaselists)
			throws Exception {
		return testResultDaoImpl.addBatchTestResult(testcaselists);
	}
	
	public TestResult queryTestResult(int resultId) throws Exception {
		
		return testResultDaoImpl.queryTestResult(resultId);
	}
	
	public ResultTrack queryResultTrack(int id) throws Exception {
		
		return testResultDaoImpl.queryResultTrackById(id);
	}

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}
	public int updateTestResult(TestResult testResult)
			throws Exception {
		return testResultDaoImpl.updateTestRusult(testResult);
	}
	
	public int updateTestRusultInSubExecution(TestResult testResult)
			throws Exception {
		return testResultDaoImpl.updateTestRusultInSubExecution(testResult);
	}
	
	public int updateResultTrackById(ResultTrack resultTrack)
			throws Exception {
		return testResultDaoImpl.updateResultTrackById(resultTrack);
	}
	
	public List<TestResult> listTestResultCountByExecutionId(int id) throws Exception {
		return testResultDaoImpl.listTestResultCountByExecutionId(id);
	}

	public List<ResultTrack> queryResultTrachkByResultId(int id) throws Exception {
		return testResultDaoImpl.queryResultTrachkByResultId(id);
	}
	
	public List<PerformanceResult> queryPerfResultByResultTrackId(int id) throws Exception {
		return testResultDaoImpl.queryPerfResultByResultTrackId(id);
	}
	
	public List<PerformanceResult> queryPerfResult2ByResultTrackId(int id) throws Exception {
		return testResultDaoImpl.queryPerfResult2ByResultTrackId(id);
	}
	
	public List<TestResult> listTestResultCountBySubExecutionId(int id) throws Exception {
		return testResultDaoImpl.listTestResultCountBySubExecutionId(id);
	}
	
	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public SubTestPlanDaoImpl getSubTestPlanDaoImpl() {
		return subTestPlanDaoImpl;
	}

	public void setSubTestPlanDaoImpl(SubTestPlanDaoImpl subTestPlanDaoImpl) {
		this.subTestPlanDaoImpl = subTestPlanDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
	}

	public TestUnitDaoImpl getTestUnitDaoImpl() {
		return testUnitDaoImpl;
	}

	public void setTestUnitDaoImpl(TestUnitDaoImpl testUnitDaoImpl) {
		this.testUnitDaoImpl = testUnitDaoImpl;
	}
	
	public int addBatchPerformanceResult(List<PerformanceResult> lists) throws Exception {

		return testResultDaoImpl.addBatchPerformanceResult(lists);
	}
	
	public int addBatchResultTrack(List<ResultTrack> lists) throws Exception {

		return testResultDaoImpl.addBatchResultTrack(lists);
	}

	public int updateTestResultByExcel(List<TestResult> resultList) throws Exception {
		return testResultDaoImpl.updateTestResultByExcel(resultList);
		
	}
}
