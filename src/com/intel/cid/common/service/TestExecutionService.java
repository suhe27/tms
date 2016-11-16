package com.intel.cid.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.dao.impl.TestExecutionDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.utils.Utils;

public class TestExecutionService {

	private static Logger logger = Logger.getLogger(TestExecutionService.class);


	private TestExecutionDaoImpl testExecutionDaoImpl;
	
	private TestResultDaoImpl testResultDaoImpl;

	public List<TestExecution> listTestExecutionByPage(PageBean pageBean,
			TestExecution testexecution) throws Exception {
		return testExecutionDaoImpl.listTestExecutionByPage(pageBean, testexecution);
	}
	
	public List<TestExecution> listTestExecutionsByIds(String ids) throws Exception {
		return testExecutionDaoImpl.listTestExecutionsByIds(ids);
	}
	
	public List<TestExecution> listTestExecutions(TestExecution testexecution, int num) throws Exception {
		return testExecutionDaoImpl.listTestExecutions(testexecution, num);
	}
	
	public List<TestResult> listTestCaseInSuiteBySubPlanAndTarget(TestExecution testexecution) throws Exception {
		return testExecutionDaoImpl.listTestCaseInSuiteBySubPlanAndTarget(testexecution);
	}
	
	public List<TestResult> listResultInTestExecutionsByCsaeId(TestExecution testexecution) throws Exception {
		return testExecutionDaoImpl.listResultInTestExecutionsByCsaeId(testexecution);
	}
	
	public List<TestExecution> listTestExecutionBySuiteId(int Id) throws Exception {
		return testExecutionDaoImpl.listTestExecutionBySuiteId(Id);
	}
	
	public List<TestResult> listFeatureByExecutionId(int Id) throws Exception {
		return testExecutionDaoImpl.listFeatureByExecutionId(Id);
	}
	
	public List<SubExecution> listSubExecutionByPage(PageBean pageBean,
			SubExecution subexecution) throws Exception {
		return testExecutionDaoImpl.listSubExecutionByPage(pageBean, subexecution);
	}
	
	public List<SubExecution> listSubExecutionByExecutionId(int id) throws Exception {
		return testExecutionDaoImpl.listSubExecutionByExecutionId(id);
	}
	
	public TestExecution queryTestExecutionById(int id) throws Exception {
		return testExecutionDaoImpl.queryTestExecutionById(id);
	}
	
	public SubExecution querySubExecutionById(int id) throws Exception {
		return testExecutionDaoImpl.querySubExecutionById(id);
	}
	
	public int querySubExecutionSize(Map<String, String> filterMap)
			throws Exception {
		return testExecutionDaoImpl.querySubExecutionSize(filterMap);
	}
	
	public int querySubExecutionCountBySubPlanId(int subPlanId)
			throws Exception {
		return testExecutionDaoImpl.querySubExecutionCountBySubPlanId(subPlanId);
	}
	
	public List<SubExecution> listSubExecutionPageBySubPlanId(
			PageBean pageBean, int subPlanId) throws Exception {
		return testExecutionDaoImpl.listSubExecutionPageBySubPlanId(pageBean, subPlanId);
	}
	
	public List<SubExecution> listSubExecutionGroupByPlatform(int subPlanId) throws Exception {
		return testExecutionDaoImpl.listSubExecutionGroupByPlatform(subPlanId);
	}
	
	public List<SubExecution> listSubExecutionGroupByOS(int subPlanId) throws Exception {
		return testExecutionDaoImpl.listSubExecutionGroupByOS(subPlanId);
	}
	
	public int queryCaseInfoBySubPlanIdSize(int subExecutionId, int subPlanId) {
		return testExecutionDaoImpl.queryCaseInfoBySubPlanIdSize(subExecutionId,subPlanId);
	}
	
	public List<?> listCaseInfoBySubPlanId(int subExecutionId, int subPlanId,PageBean pageBean) {
		return testExecutionDaoImpl.listCaseInfoBySubPlanId(subExecutionId, subPlanId,pageBean);

	}
	
	public List<TestResult> listCasePassRateHistory(int subExecutionId, int subPlanId) {
		return testExecutionDaoImpl.listCasePassRateHistory(subExecutionId, subPlanId);

	}
	
	public List<PerformanceResult> listPerformanceAnalysis(int subExecutionId) {
		return testExecutionDaoImpl.listPerformanceAnalysis(subExecutionId);

	}
	
	public int deleTestexecution( String[] ids ) throws Exception {

		testExecutionDaoImpl.delSubExecutionByExecutionId(ids);
		testExecutionDaoImpl.delSubExecutionCaseListByExecutionId(ids);
		return testExecutionDaoImpl.deleTestexecution(ids);
	}
	
	public int delSubExecution( String[] ids ) throws Exception {

		testExecutionDaoImpl.delSubExecutionCaseListBySubExecution(ids);
		return testExecutionDaoImpl.delSubExecutionBySubId(ids);
	}

	public int updateTestexecution(TestExecution testexecution)
			throws Exception {
		return testExecutionDaoImpl.updateTestexecution(testexecution);
	}
	
	public int refreshTestExecution(TestExecution testexecution)
			throws Exception {
		return testExecutionDaoImpl.refreshTestExecution(testexecution);
	}
	
	public int refreshSubExecution(SubExecution subExecution)
			throws Exception {
		return testExecutionDaoImpl.refreshSubExecution(subExecution);
	}
	
	public int updateSubExecution(SubExecution subexecution)
			throws Exception {
		return testExecutionDaoImpl.updateSubExecution(subexecution);
	}

	public int addTestexecution(TestExecution testexecution) throws Exception {
		return testExecutionDaoImpl.addTestexecution(testexecution);

	}
	
	public int addSubExecution(SubExecution subexecution) throws Exception {
		return testExecutionDaoImpl.addSubExecution(subexecution);

	}
	public int queryByExeNameCycleSize(TestExecution testexecution) throws Exception {
		return testExecutionDaoImpl.queryByExeNameCycleSize(testexecution);

	}
	
	public int queryByExecutionIdAndSubPlanId(SubExecution subexecution) throws Exception {
		return testExecutionDaoImpl.queryByExecutionIdAndSubPlanId(subexecution);

	}
	
	public int verifyDuplicateSubExecution(SubExecution subexecution) throws Exception {
		return testExecutionDaoImpl.verifyDuplicateSubExecution(subexecution);

	}
	
	public int querySubExecutionNameSize(SubExecution subexecution) throws Exception {
		   return testExecutionDaoImpl.querySubExecutionNameSize(subexecution);
	}
	
	public int queryBySubExecutionNameSize(SubExecution subexecution) throws Exception {
		return testExecutionDaoImpl.queryBySubExecutionNameSize(subexecution);

	}
	
	public void copyTestExecution(TestExecution oldTestExecution, TestExecution newTestExecution)
			throws Exception {

		int oldId = oldTestExecution.getId();
		int newId = newTestExecution.getId();
		List<SubExecution> list = testExecutionDaoImpl.listSubExecutionsByExecutionId(oldId);
		String createDate = Utils.dateFormat(new Date(), null);
		for (SubExecution sub : list) {
			sub.setCreateDate(createDate);
			sub.setModifyDate(createDate);
			sub.setId(newId);
			sub.setExecutionId(newId);
			sub.setPass(0);
			sub.setFail(0);
			sub.setNotRun(sub.getTotalCases());
			sub.setBlock(0);
			sub.setExecuteRate(null);
			sub.setPassrate(null);
			int subId = testExecutionDaoImpl.addSubExecution(sub);
			List<TestResult> results = testResultDaoImpl.listTestResultBySubExecutionId(sub.getSubExecutionId());
			for (TestResult result : results) {
				result.setModifyDate(createDate);
				result.setSubExecutionId(subId);
				result.setExecutionId(newId);
				}
			testResultDaoImpl.addBatchTestResults(results);
			}
	}
	
	
	public int queryTestexecutionSize(TestExecution testexecution) throws Exception{
		return testExecutionDaoImpl.queryTestexecutionSize(testexecution);
	}
	public TestExecutionDaoImpl getTestExecutionDaoImpl() {
		return testExecutionDaoImpl;
	}

	public void setTestExecutionDaoImpl(TestExecutionDaoImpl testExecutionDaoImpl) {
		this.testExecutionDaoImpl = testExecutionDaoImpl;
	}
	
	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}
	
	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}
}
