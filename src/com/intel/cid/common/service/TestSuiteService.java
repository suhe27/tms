package com.intel.cid.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.action.TestSuiteAction;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.dao.impl.CaseSuiteMapImpl;
import com.intel.cid.common.dao.impl.FeatureDaoImpl;
import com.intel.cid.common.dao.impl.OSDaoImpl;
import com.intel.cid.common.dao.impl.PlanMapDaoImpl;
import com.intel.cid.common.dao.impl.SubTestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestExecutionDaoImpl;
import com.intel.cid.common.dao.impl.TestSuiteDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.utils.Utils;

public class TestSuiteService {

	private static Logger logger = Logger.getLogger(TestSuiteService.class);

	private TestSuiteDaoImpl testSuiteDaoImpl;

	private TestCaseDaoImpl testCaseDaoImpl;
	
	private TestExecutionDaoImpl testExecutionDaoImpl;

	private CaseSuiteMapImpl caseSuiteMapImpl;

	private FeatureDaoImpl featureDaoImpl;

	private OSDaoImpl oSDaoImpl;

	private TestPlanDaoImpl testPlanDaoImpl;

	private PlanMapDaoImpl planMapDaoImpl;

	private SubTestPlanDaoImpl subTestPlanDaoImpl;
	
	private TestResultDaoImpl testResultDaoImpl;

	private TestUnitDaoImpl testUnitDaoImpl;

	/****
	 * when testsuite size changed , need to update testplan, subtestplan
	 * ,testunit "totalCases" field .
	 * 
	 * @param suiteId
	 * @param enabled
	 *            for enable or disable this function
	 */
	public void updateTestPlanAfterTestSuiteSizeChanged(int suiteId,
			boolean enabled) throws Exception {

		if (enabled) {

			TestSuite testSuite = testSuiteDaoImpl.queryTestSuiteById(suiteId);
			List<TestPlan> testPlanList = testPlanDaoImpl.ListTestPlanByTestSuiteId(suiteId);
			for (TestPlan testPlan : testPlanList) {
				List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnitByTestPlanId(testPlan.getTestPlanId());

				//fix bugs: just update the testunit which related to the test suite.
				for (TestUnit testUnit : testUnitList) {

				     if(testUnit.getTestSuiteId() == suiteId){
				    	 
				    	 testUnit.setTotalCases(testSuite.getTotalCases());
				     }
					
				}			
				
				testUnitDaoImpl.updateBatchTestUnit(testUnitList);
				
				List<SubTestPlan> subPlanList = subTestPlanDaoImpl.listSubTestPlan(testPlan.getTestPlanId());

				for (SubTestPlan subTestPlan : subPlanList) {
					subTestPlanDaoImpl.refreshSubTestPlan(subTestPlan);
				}

				testPlanDaoImpl.refreshTestPlan(testPlan);

			}

		}
	}
/**
 * Update test execution once test case in suite changed.
 * 	--Change matched sub execution testresult table list.(will only change test execution execute rate is not 100%)
 * 	--Update execution/sub execution total case/pass rate/execute rate/not run/block/pass/fail number.	
 * @param suiteId, type(only support add and del)
 * @throws Exception
 */
	public void updateTestExecutionAfterTestSuiteSizeChanged(int suiteId, List<TestCase> testcases, String type) throws Exception {
			List<TestExecution> executionList = testExecutionDaoImpl.listTestExecutionBySuiteId(suiteId);
			String currentDate = Utils.dateFormat(new Date(), null);	
			for (TestExecution execution : executionList) {
				List<SubExecution> subExecutionList = testExecutionDaoImpl.listSubExecutionsByExecutionIdAndSuiteId(suiteId,execution.getId());
				
				//subExecutionList only listed sub execution with changed suite
				for(SubExecution subexecution : subExecutionList) {
					
					List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnitBySubPlanId(subexecution.getSubPlanId());
					
					//Iterate each related test unit
					for(TestUnit unit : testUnitList) {
						
						//Operation for add test case to testresult table
						if("add".equalsIgnoreCase(type)){
							for (TestCase tcase : testcases) {
								tcase.setTestPlanId(subexecution.getTestPlanId());
								tcase.setModifyDate(currentDate);
								tcase.setSubExecutionId(subexecution.getSubExecutionId());
								tcase.setExecutionId(subexecution.getId());
								tcase.setPlatformId(subexecution.getPlatformId());
								tcase.setOsId(subexecution.getOsId());
								tcase.setTargetId(unit.getTargetId());
								tcase.setExecutionId(execution.getId());
								tcase.setSubPlanId(subexecution.getSubPlanId());
								tcase.setTestPlanId(execution.getTestPlanId());
								tcase.setTestUnitId(unit.getTestUnitId());
								
							}
							testResultDaoImpl.addBatchTestResult(testcases);
						}
						//Operation for delete test case from testresult table
						if("del".equalsIgnoreCase(type)){
							for (TestCase tcase : testcases) {
								tcase.setSubExecutionId(subexecution.getSubExecutionId());
								tcase.setOsId(subexecution.getOsId());
								tcase.setTargetId(unit.getTargetId());
								tcase.setSubPlanId(subexecution.getSubPlanId());
								tcase.setPlatformId(subexecution.getPlatformId());
								tcase.setTestUnitId(unit.getTestUnitId());
								testResultDaoImpl.deleteTestResultFromSuite(tcase);
								
							}
							//testResultDaoImpl.deleteBatchTestResult(testcases);
						}
					}
					testExecutionDaoImpl.refreshSubExecution(subexecution);
				}
				testExecutionDaoImpl.refreshTestExecution(execution);
			}
	}

	public List<TestSuite> listTestSuites(int userId) throws Exception {
		return testSuiteDaoImpl.listTestSuites(userId);
	}

	public List<TestSuite> listTestSuiteByProjectId(int projectId)
			throws Exception {

		return testSuiteDaoImpl.listTestSuiteByProjectId(projectId);

	}

	public List<TestSuite> listTestSuites(Map<String, String> filterMap,
			PageBean pageBean) throws Exception {

		return testSuiteDaoImpl.listTestSuites(filterMap, pageBean);
	}

	public List<TestSuite> listTestSuites(Map<String, String> filterMap)
			throws Exception {

		return testSuiteDaoImpl.listTestSuites(filterMap, null);
	}

	/**
	 * 
	 * query test suite by page
	 */
	public List<TestSuite> listTestSuitesByPage(PageBean pageBean)
			throws Exception {
		return testSuiteDaoImpl.queryAllTestSuitesByPage(pageBean);

	}

	/**
	 * 
	 * query all test cases belong to one test suite
	 */
	public List<TestCase> listTestCasesBySuiteId(int testsuitId)
			throws Exception {

		return caseSuiteMapImpl.queryTestCasesBySuitId(testsuitId);

	}

	/**
     * 
     */
	public List<TestCase> listTestCasesByPage(PageBean pageBean, int testSuiteId)
			throws Exception {

		return caseSuiteMapImpl.queryTestCaseByPageAndSuitId(pageBean,
				testSuiteId);
	}

	public List<TestSuite> listAllTestSuites() throws Exception {

		return testSuiteDaoImpl.listAllTestSuites();
	}

	/**
	 * 
	 * query all test suite size
	 */
	public int queryTestSuiteSize(Map<String, String> filterMap) {

		return testSuiteDaoImpl.queryTestSuiteSize(filterMap);
	}

	public TestSuite queryTestSuiteById(int id) throws Exception {
		return testSuiteDaoImpl.queryTestSuiteById(id);
	}

	public int queryBySuitNameSize(TestSuite TestSuite) throws Exception {
		return testSuiteDaoImpl.queryBySuitNameSize(TestSuite);
	}

	public void delTestSuite(int id) throws Exception {

		testSuiteDaoImpl.delTestSuiteById(id);
		caseSuiteMapImpl.delTestCaseSuitBySuitId(id);
	}

	public int addTestSuite(TestSuite TestSuite) throws Exception {
		return testSuiteDaoImpl.addTestSuite(TestSuite);

	}

	public void updateTestSuite(TestSuite testSuite) throws Exception {

		testSuiteDaoImpl.updateTestSuite(testSuite);
	}

	public void delBatchTestCases(final int suitId, final String[] testcases)
			throws Exception {

		logger.info("delBatchTestCases method start:");
		caseSuiteMapImpl.delBatchTestCases(suitId, testcases);
		TestSuite testSuite = queryTestSuiteById(suitId);
		List<OS> osList = oSDaoImpl.queryAllOSs();
		List<Feature> featureList = featureDaoImpl.listAllFeatures();
		List<TestCase> testcaseList = caseSuiteMapImpl.queryTestCasesBySuitId(suitId);
		String[] strs = TestSuiteAction.getFeatureAndOsNames(testcaseList, featureList, osList);
		testSuite.setFeatures(strs[0]);
		testSuite.setOses(strs[1]);
		testSuite.setTotalCases(testSuite.getTotalCases() - testcases.length);
		updateTestSuite(testSuite);
		logger.info("delBatchTestCases size:" + testcases.length);
	}

	/**
	 * create test suite from ArrayList
	 * 
	 */
	public void createTestSuite(TestSuite testsuite) throws Exception {
		logger
				.info("start to create testsuite:"
						+ testsuite.getTestSuiteName());
		int suiteRowNum = addTestSuite(testsuite);
		caseSuiteMapImpl.addTestCaseBatchByArrayList(testsuite
				.getTestcaseList(), suiteRowNum);
		logger.info("create testsuite:" + testsuite.getTestSuiteName()
				+ " success!");

	}

	/**
	 * create test suite from String [] test cases
	 * 
	 */
	public void createTestSuite(TestSuite testsuite, String[] testcases)
			throws Exception {

		logger
				.info("start to create testsuite:"
						+ testsuite.getTestSuiteName());
		int suiteRowNum = addTestSuite(testsuite);
		caseSuiteMapImpl.addTestCaseBatchByStringArray(testcases, suiteRowNum);
		logger.info("create testsuit:" + testsuite.getTestSuiteName()
				+ " success!");

	}

	public void addTestCaseToTestSuite(TestSuite testsuite, List<TestCase> testcaes) throws Exception {

		caseSuiteMapImpl.addTestCaseBatchByArrayList(testcaes, testsuite
				.getTestSuiteId());
		testSuiteDaoImpl.updateTestSuite(testsuite);

		logger.info("addTestCaseToTestSuite :" + testsuite.getTestSuiteName()
				+ " success!");

	}

	public List<TestSuite> listTestSuiteByTestCase(int testCaseId)
			throws Exception {

		return caseSuiteMapImpl.listTestSuiteByTestCase(testCaseId);
	}

	public TestSuiteDaoImpl getTestSuiteDaoImpl() {
		return testSuiteDaoImpl;
	}

	public void setTestSuiteDaoImpl(TestSuiteDaoImpl testSuiteDaoImpl) {
		this.testSuiteDaoImpl = testSuiteDaoImpl;
	}

	public TestCaseDaoImpl getTestCaseDaoImpl() {
		return testCaseDaoImpl;
	}

	public void setTestCaseDaoImpl(TestCaseDaoImpl testCaseDaoImpl) {
		this.testCaseDaoImpl = testCaseDaoImpl;
	}
	
	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public CaseSuiteMapImpl getCaseSuiteMapImpl() {
		return caseSuiteMapImpl;
	}

	public void setCaseSuiteMapImpl(CaseSuiteMapImpl caseSuiteMapImpl) {
		this.caseSuiteMapImpl = caseSuiteMapImpl;
	}

	public FeatureDaoImpl getFeatureDaoImpl() {
		return featureDaoImpl;
	}

	public void setFeatureDaoImpl(FeatureDaoImpl featureDaoImpl) {
		this.featureDaoImpl = featureDaoImpl;
	}

	public OSDaoImpl getoSDaoImpl() {
		return oSDaoImpl;
	}

	public void setoSDaoImpl(OSDaoImpl oSDaoImpl) {
		this.oSDaoImpl = oSDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
	}
	
	public TestExecutionDaoImpl getTestExecutionDaoImpl() {
		return testExecutionDaoImpl;
	}

	public void setTestExecutionDaoImpl(TestExecutionDaoImpl testExecutionDaoImpl) {
		this.testExecutionDaoImpl = testExecutionDaoImpl;
	}

	public PlanMapDaoImpl getPlanMapDaoImpl() {
		return planMapDaoImpl;
	}

	public void setPlanMapDaoImpl(PlanMapDaoImpl planMapDaoImpl) {
		this.planMapDaoImpl = planMapDaoImpl;
	}

	public SubTestPlanDaoImpl getSubTestPlanDaoImpl() {
		return subTestPlanDaoImpl;
	}

	public void setSubTestPlanDaoImpl(SubTestPlanDaoImpl subTestPlanDaoImpl) {
		this.subTestPlanDaoImpl = subTestPlanDaoImpl;
	}

	public TestUnitDaoImpl getTestUnitDaoImpl() {
		return testUnitDaoImpl;
	}

	public void setTestUnitDaoImpl(TestUnitDaoImpl testUnitDaoImpl) {
		this.testUnitDaoImpl = testUnitDaoImpl;
	}

}
