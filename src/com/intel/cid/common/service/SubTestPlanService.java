package com.intel.cid.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.PlanMap;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.PlanMapDaoImpl;
import com.intel.cid.common.dao.impl.SubTestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanTrackDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;
import com.intel.cid.common.dao.impl.TestExecutionDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.CaseSuiteMapImpl;
import com.intel.cid.utils.Utils;

public class SubTestPlanService {

	private static Logger logger = Logger.getLogger(SubTestPlanService.class);

	private PlanMapDaoImpl planMapDaoImpl;

	private SubTestPlanDaoImpl subTestPlanDaoImpl;
	
	private TestCaseDaoImpl testCaseDaoImpl;

	private TestUnitDaoImpl testUnitDaoImpl;

	private TestResultDaoImpl testResultDaoImpl;

	private TestPlanDaoImpl testPlanDaoImpl;

	private TestPlanTrackDaoImpl testPlanTrackDaoImpl;

	private UserDaoImpl userDaoImpl;
	
	private TestExecutionDaoImpl testExecutionDaoImpl;
	
	private CaseSuiteMapImpl caseSuiteMapImpl;

	public boolean isUniqueSubTestPlan(int testPlanId,int subPlanId, String subPlanName)
			throws Exception {

		return planMapDaoImpl.isUniqueSubTestPlan(testPlanId,  subPlanId,subPlanName);
	}

	public int querySubPlanSize(SubTestPlan subTestPlan) throws Exception {
		   return planMapDaoImpl.querySubPlanSize(subTestPlan);
	}
	
	public int querySuiteInSubPlanSize(SubTestPlan subTestPlan) throws Exception {
		   return planMapDaoImpl.querySuiteInSubPlanSize(subTestPlan);
	}
	
	public List<SubTestPlan> listSubTestPlanByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {
		return planMapDaoImpl.listSubTestPlanByPage(pageBean, filterMap);
	}

	public int querySubTestPlanSize(Map<String, String> filterMap)
			throws Exception {
		return planMapDaoImpl.querySubTestPlanSize(filterMap);
	}

	
	public List<SubTestPlan> listTestCasesInSubTestPlan(int testPlanId)
			throws Exception {

		return subTestPlanDaoImpl.listTestCasesInSubTestPlan(testPlanId);
	}
	
	public List<TestCase> listTestCasesInSubTestPlan1(int testPlanId)
			throws Exception {

		return subTestPlanDaoImpl.listTestCasesInSubTestPlan1(testPlanId);
	}


	public List<SubTestPlan> listSubTestPlansInOnePlan(int testPlanId)
			throws Exception {

		return planMapDaoImpl.listSubTestPlan(testPlanId);
	}
	
	public List<TestCase> ListTestCaseBySuiteId(PageBean pageBean, int suiteId)
			throws Exception {
		return subTestPlanDaoImpl.ListTestCaseBySuiteId(pageBean, suiteId);
	}

	/*SubTestPlan querySubTestPlanAfterTestUnitChanged(TestUnit testUnit)
			throws Exception {

		int subPlanId = testUnit.getSubPlanId();
		SubTestPlan subPlan = subTestPlanDaoImpl
				.querySubTestPlanById(subPlanId);
		List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnits(subPlanId);
		return reCalcSubTestPlanAfterTestUnitChanged(subPlan, testUnitList);
	}*/

	/*public static SubTestPlan reCalcSubTestPlanAfterTestUnitChanged(
			SubTestPlan subPlan, List<TestUnit> testUnitList) {
		int pass = 0;
		int fail = 0;
		int notRun = 0;
		int block = 0;
		int totalCases = 0;
		float passRate = 0;
		for (TestUnit testUnit : testUnitList) {
			pass += testUnit.getPass();
			fail += testUnit.getFail();
			notRun += testUnit.getNotRun();
			block += testUnit.getBlock();
			totalCases += testUnit.getTotalCases();
		}
		// only contains two precise.
		if (testUnitList.size() == 0) {
			passRate = 0;
		} else {
			passRate = (float) pass / totalCases;
		}

		passRate = new BigDecimal(Float.valueOf(passRate) * 100).setScale(2,
				BigDecimal.ROUND_HALF_DOWN).floatValue();
		subPlan.setPass(pass);
		subPlan.setFail(fail);
		subPlan.setNotRun(notRun);
		subPlan.setBlock(block);
		subPlan.setTotalCases(totalCases);
		subPlan.setPassRate(passRate);
		subPlan.setModifyDate(Utils.dateFormat(new Date(), null));
		logger.info("reCalcSubTestPlanAfterTestUnitChanged method result :"
				+ subPlan);
		return subPlan;
	}*/

	/*public void updateSubPlanGlobalResult(SubTestPlan subPlan, int result)
			throws Exception {
		String modifyDate = Utils.dateFormat(new Date(), null);
		testResultDaoImpl.updateTestResultBySubPlanAndResult(subPlan, result);
		List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnits(subPlan
				.getSubPlanId());

		for (TestUnit testUnit : testUnitList) {

			testUnit = testUnitDaoImpl
					.queryTestUnitAfterTestResultChanged(testUnit);
			testUnitDaoImpl.updateTestUnit(testUnit);
		}
		subPlan = SubTestPlanService.reCalcSubTestPlanAfterTestUnitChanged(
				subPlan, testUnitList);
		subTestPlanDaoImpl.updateSubTestPlan(subPlan);
		TestPlan testPlan = testPlanDaoImpl.queryTestPlanBySubPlan(subPlan
				.getSubPlanId());
		testPlan.setModifyDate(modifyDate);
		List<SubTestPlan> subPlanList = subTestPlanDaoImpl
				.listSubTestPlan(testPlan.getTestPlanId());

		testPlan = TestPlanService.reCalcTestPlanAfterSubTestPlanChanged(
				testPlan, subPlanList);

		testPlanDaoImpl.updateTestPlan(testPlan);
	}*/

	/*
	 * public int addSubTestPlan(TestPlan testPlan) throws Exception {
	 * 
	 * SubTestPlan subPlan = testPlan.getSubPlan(); List<TestUnit> testUnitList
	 * = subPlan.getTestUnitList(); int newSubPlanId =
	 * subTestPlanDaoImpl.addSubTestPlan(subPlan);
	 * subPlan.setSubPlanId(newSubPlanId); PlanMap planMap = new PlanMap(); int
	 * testPlanId = testPlan.getTestPlanId(); planMap.setTestPlanId(testPlanId);
	 * planMap.setSubPlanId(newSubPlanId);
	 * 
	 * planMapDaoImpl.addSubTestPlanMap(planMap);
	 * 
	 * List<TestResult> testResultList = new ArrayList<TestResult>(); for
	 * (TestUnit testUnit : testUnitList) { // set new subPlanId
	 * testUnit.setSubPlanId(newSubPlanId);
	 * 
	 * int newTestUnitId = testUnitDaoImpl.addTestUnit(testUnit);
	 * testUnit.setTestUnitId(newTestUnitId);
	 * 
	 * List<TestResult> resultList = testUnit.getTestResultList();
	 * 
	 * for (TestResult testResult : resultList) {
	 * 
	 * testResult.setSubPlanId(newSubPlanId);
	 * testResult.setTestUnitId(newTestUnitId);
	 * testResult.setResultTypeId(Constant.RESULT_NOTRUN); }
	 * 
	 * testResultList.addAll(resultList); }
	 * 
	 * testResultDaoImpl.addBatchResult(testResultList);
	 * 
	 * // update summary info
	 * 
	 * List<SubTestPlan> newSubPlanList = subTestPlanDaoImpl
	 * .listSubTestPlan(testPlan.getTestPlanId());
	 * 
	 * String testers = getTestersInfo(newSubPlanList); testPlan =
	 * TestPlanService.reCalcTestPlanAfterSubTestPlanChanged( testPlan,
	 * newSubPlanList); testPlan.setTesters(testers);
	 * testPlanDaoImpl.updateTestPlan(testPlan); return newSubPlanId; }
	 */

	public void addSubTestPlan(TestPlan testPlan) throws Exception {
		
		SubTestPlan subPlan = testPlan.getSubPlan();
		List<TestUnit> testUnitList = subPlan.getTestUnitList();
		int newSubPlanId = subTestPlanDaoImpl.addSubTestPlan(subPlan);
		subPlan.setSubPlanId(newSubPlanId);
		PlanMap planMap = new PlanMap();
		int testPlanId = testPlan.getTestPlanId();
		planMap.setTestPlanId(testPlanId);
		planMap.setSubPlanId(newSubPlanId);
		planMapDaoImpl.addSubTestPlanMap(planMap);		
		for (TestUnit testUnit : testUnitList) {
			// set new subPlanId
			testUnit.setSubPlanId(newSubPlanId);
			int newTestUnitId = testUnitDaoImpl.addTestUnit(testUnit);
			testUnit.setTestUnitId(newTestUnitId);
		}
		testPlanDaoImpl.refreshTestPlan(testPlan);		
	}

	
	public void addTestUnits(TestPlan testPlan) throws Exception {

		SubTestPlan subPlan = testPlan.getSubPlan();
		subTestPlanDaoImpl.updateSubTestPlan(subPlan);
		List<TestUnit> testUnitList = subPlan.getTestUnitList();
		String currentDate = Utils.dateFormat(new Date(), null);
		
		List<TestUnit> newUnitList = new ArrayList<TestUnit>();
		
		for (TestUnit testUnit : testUnitList) {
			//Verify no duplicate unit in sub test plan
			int result = testUnitDaoImpl.checkDuplicateUnitInSubPlan(testUnit);
			if(result == 0) {
				//Update test unit in related sub test plan
				int unitId = testUnitDaoImpl.addTestUnit(testUnit);
				testUnit.setTestUnitId(unitId);
				newUnitList.add(testUnit);
			}
		}
				
		// update summary info	
		refreshSubTestPlan(subPlan);
		testPlanDaoImpl.refreshTestPlan(testPlan);	
		
		//Update this sub test plan related test execution
		List<TestExecution> executionList = testExecutionDaoImpl.listTestExecutionBySubPlanId(subPlan.getSubPlanId());
		for (TestExecution execution : executionList) {
			List<SubExecution> subExecutionList = testExecutionDaoImpl.listSubExecutionsByExecutionIdAndSubPlanId(subPlan.getSubPlanId(),execution.getId());
			
			//subExecutionList only listed sub execution with changed suite
			for(SubExecution subexecution : subExecutionList) {
				
				//Iterate each related test unit
				for(TestUnit unit : newUnitList) {
					List<TestCase> testCaseList = caseSuiteMapImpl.queryTestCasesBySuitId(unit.getTestSuiteId());
				
					//Operation for add test case to testresult table
					for (TestCase tcase : testCaseList) {
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
					testResultDaoImpl.addBatchTestResult(testCaseList);
				}
				testExecutionDaoImpl.refreshSubExecution(subexecution);
			}
			testExecutionDaoImpl.refreshTestExecution(execution);
		}
	}

	public void deleteTestUnit(TestPlan testPlan, String[] testUnits)
			throws Exception {
		SubTestPlan subTestPlan = testPlan.getSubPlan();
		testUnitDaoImpl.delBatchTestUnitsByIds(testUnits);
		refreshSubTestPlan(subTestPlan);
		testPlanDaoImpl.refreshTestPlan(testPlan);
		
		String currentDate = Utils.dateFormat(new Date(), null);
		List<TestUnit> newUnitList = new ArrayList<TestUnit>();
		for (String ts : testUnits) {
			TestUnit tunit = testUnitDaoImpl.queryTestUnit(Integer.parseInt(ts.trim()));
			newUnitList.add(tunit);
		}
		System.out.println("---------------------------Main----------------------------------");
		//Update this sub test plan related test execution
		List<TestExecution> executionList = testExecutionDaoImpl.listTestExecutionBySubPlanId(subTestPlan.getSubPlanId());
		for (TestExecution execution : executionList) {
			List<SubExecution> subExecutionList = testExecutionDaoImpl.listSubExecutionsByExecutionIdAndSubPlanId(subTestPlan.getSubPlanId(),execution.getId());
			
			//subExecutionList only listed sub execution with changed suite
			for(SubExecution subexecution : subExecutionList) {
				
				//Iterate each related test unit
				for(TestUnit unit : newUnitList) {
					System.out.println("---------------------------Unit----------------------------------");
					TestCase tcase = new TestCase();
					tcase.setSubExecutionId(subexecution.getSubExecutionId());
					tcase.setOsId(subexecution.getOsId());
					tcase.setTargetId(unit.getTargetId());
					tcase.setSubPlanId(subexecution.getSubPlanId());
					tcase.setPlatformId(subexecution.getPlatformId());
					tcase.setTestUnitId(unit.getTestUnitId());
					testResultDaoImpl.deleteTestResultFromUnit(tcase);	
					
				}
				testExecutionDaoImpl.refreshSubExecution(subexecution);
			}
			testExecutionDaoImpl.refreshTestExecution(execution);
		}
		
	}

	public void refreshSubTestPlan(final SubTestPlan subPlan) throws Exception {
		
		 subTestPlanDaoImpl.refreshSubTestPlan(subPlan);
	}
	
	
	public void delBatchSubTestPlanInOneTestPlan(String[] subTestPlans)
			throws Exception {
		int subPlanId = Integer.parseInt(subTestPlans[0].trim());
		TestPlan testPlan = testPlanDaoImpl.queryTestPlanBySubPlan(subPlanId);
		//testResultDaoImpl.delTestResults(subTestPlans);
		testUnitDaoImpl.delBatchTestUnits(subTestPlans);
		subTestPlanDaoImpl.delBatchSubTestPlan(subTestPlans);
		planMapDaoImpl.delBatchSubTestPlan(subTestPlans);
		// update testplan
		testPlanDaoImpl.refreshTestPlan(testPlan);

	}
/*
	public String getTestersInfo(List<SubTestPlan> subTestPlanList)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		Set<String> userNames = new HashSet<String>();

		for (SubTestPlan subPlan : subTestPlanList) {

			String testers = subPlan.getTesters();
			if (!Utils.isNullORWhiteSpace(testers)) {

				String[] many = testers.split(",");
				for (String tester : many) {

					if (!Utils.isNullORWhiteSpace(tester)) {
						userNames.add(tester);
					}
				}

			}
		}
		int i = 0;
		for (String userName : userNames) {

			buffer.append(userName);
			if (i != userNames.size() - 1) {
				buffer.append(",");
			}
			i++;
		}

		return buffer.toString();

	}*/

	/*public String getSubTestersInfo(List<TestUnit> testUnitList)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		Set<String> userNames = new HashSet<String>();

		for (TestUnit unit : testUnitList) {

			int userId = unit.getTester();
			User user = userDaoImpl.queryUserById(userId);
			if (!Utils.isNullORWhiteSpace(user.getUserName())) {
				userNames.add(user.getUserName());
			}
		}
		int i = 0;
		for (String userName : userNames) {

			buffer.append(userName);
			if (i != userNames.size() - 1) {
				buffer.append(",");
			}
			i++;
		}

		return buffer.toString();

	} */

	/*public void updateSubTestPlanAfterTestResultsUpload(SubTestPlan subTestPlan)
			throws Exception {

		List<TestUnit> testUnitList = subTestPlan.getTestUnitList();
		List<TestPlanTrack> trackList = subTestPlan.getTrackList();
		for (TestUnit testUnit : testUnitList) {
			List<TestResult> tmpResultList = testUnit.getTestResultList();
			testResultDaoImpl.updateBatchTestResult(tmpResultList);
			TestUnit newTestUnit = testUnitDaoImpl
					.queryTestUnitAfterTestResultChanged(testUnit);
			testUnitDaoImpl.updateTestUnit(newTestUnit);
		}

		int subPlanId = subTestPlan.getSubPlanId();
		testUnitList = testUnitDaoImpl.listTestUnits(subPlanId);
		subTestPlan = reCalcSubTestPlanAfterTestUnitChanged(subTestPlan,
				testUnitList);
		subTestPlanDaoImpl.updateSubTestPlan(subTestPlan);
		TestPlan testPlan = testPlanDaoImpl.queryTestPlanBySubPlan(subPlanId);
		List<SubTestPlan> subTestPlanList = subTestPlanDaoImpl
				.listSubTestPlan(testPlan.getTestPlanId());
		testPlan = TestPlanService.reCalcTestPlanAfterSubTestPlanChanged(
				testPlan, subTestPlanList);

		testPlanDaoImpl.updateTestPlan(testPlan);
		testPlanTrackDaoImpl.addBatchTestPlanTrack(trackList);
	}*/

	public SubTestPlan querySubTestPlanById(int subPlanId) throws Exception {

		return subTestPlanDaoImpl.querySubTestPlanById(subPlanId);
	}
 
	public void updateSubTestPlan(final SubTestPlan subTestPlan)
			throws Exception {

		subTestPlanDaoImpl.updateSubTestPlan(subTestPlan);
		
		List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnits(subTestPlan
				.getSubPlanId());
		String newSubDueDate = subTestPlan.getDueDate();
		for (TestUnit testUnit : testUnitList) {
			String unitDueDate = testUnit.getDueDate();		
			testUnit.setDueDate(newSubDueDate);		
			if (Utils.dateCompare(unitDueDate, newSubDueDate,
					Constant.DATE_FORMAT_YMD) > 0) {
				testUnit.setDueDate(newSubDueDate);
			}
			
			testUnitDaoImpl.updateTestUnit(testUnit);

		}	
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

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
	}

	public TestPlanTrackDaoImpl getTestPlanTrackDaoImpl() {
		return testPlanTrackDaoImpl;
	}

	public void setTestPlanTrackDaoImpl(
			TestPlanTrackDaoImpl testPlanTrackDaoImpl) {
		this.testPlanTrackDaoImpl = testPlanTrackDaoImpl;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	public TestExecutionDaoImpl getTestExecutionDaoImpl() {
		return testExecutionDaoImpl;
	}

	public void setTestExecutionDaoImpl(TestExecutionDaoImpl testExecutionDaoImpl) {
		this.testExecutionDaoImpl = testExecutionDaoImpl;
	}
	public TestCaseDaoImpl getTestCaseDaoImpl() {
		return testCaseDaoImpl;
	}

	public void setTestCaseDaoImpl(TestCaseDaoImpl testCaseDaoImpl) {
		this.testCaseDaoImpl = testCaseDaoImpl;
	}
	
	public CaseSuiteMapImpl getCaseSuiteMapImpl() {
		return caseSuiteMapImpl;
	}

	public void setCaseSuiteMapImpl(CaseSuiteMapImpl caseSuiteMapImpl) {
		this.caseSuiteMapImpl = caseSuiteMapImpl;
	}

}
