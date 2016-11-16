package com.intel.cid.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.PlanMap;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.PlanMapDaoImpl;
import com.intel.cid.common.dao.impl.SubTestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;
import com.intel.cid.utils.Utils;

public class TestPlanService {

	private static Logger logger = Logger.getLogger(TestPlanService.class);

	private TestPlanDaoImpl testPlanDaoImpl;

	private PlanMapDaoImpl planMapDaoImpl;

	private SubTestPlanDaoImpl subTestPlanDaoImpl;

	private TestUnitDaoImpl testUnitDaoImpl;

	private TestResultDaoImpl testResultDaoImpl;

	private UserDaoImpl userDaoImpl;

	public List<?> queryTestPlanByProject(int projectId) {
		return testPlanDaoImpl.queryTestPlanByProject(projectId);

	}

	public List<TestPlan> listAllTestPlans(int userId) throws Exception {
		return testPlanDaoImpl.listAllTestPlans(userId);
	}

	public TestPlan queryTestPlan(String phaseId, int projectId)
			throws Exception {

		return testPlanDaoImpl.queryTestPlan(phaseId, projectId);
	}

	public TestPlan queryTestPlan(String releaseCycle) throws Exception {

		return testPlanDaoImpl.queryTestPlan(releaseCycle);
	}

	public boolean isUniqueTestPlan(final TestPlan testPlan) throws Exception {

		return testPlanDaoImpl.isUniqueTestPlan(testPlan);
	}

	public int queryByPlanNameSize(TestPlan testPlan) throws Exception {
		return testPlanDaoImpl.queryByPlanNameSize(testPlan);
	}
	
	public void refreshTestPlan(final TestPlan testPlan) throws Exception {

		testPlanDaoImpl.refreshTestPlan(testPlan);

	}

	public int addTestPlan(final TestPlan testPlan) throws Exception {

		return testPlanDaoImpl.addTestPlan(testPlan);
	}

	public List<TestPlan> listTestPlanByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		return testPlanDaoImpl.listTestPlanByPage(pageBean, filterMap);
	}

	public void copyTestPlan(TestPlan desTestPlan, TestPlan newTestPlan)
			throws Exception {

		int copyTestPlanId = desTestPlan.getTestPlanId();
		int newTestPlanId = newTestPlan.getTestPlanId();
		List<SubTestPlan> subTestPlanList = subTestPlanDaoImpl.listSubTestPlan(copyTestPlanId);
		String createDate = Utils.dateFormat(new Date(), null);
		for (SubTestPlan subPlan : subTestPlanList) {
			
			subPlan.setOwner(newTestPlan.getOwner());
			subPlan.setDueDate(newTestPlan.getEndDate());					
			subPlan.setCreateDate(createDate);
			int newSubPlanId = subTestPlanDaoImpl.addSubTestPlan(subPlan);
			PlanMap planMap = new PlanMap();
			planMap.setTestPlanId(newTestPlanId);
			planMap.setSubPlanId(newSubPlanId);
			planMapDaoImpl.addSubTestPlanMap(planMap);
			List<TestUnit> testUnitList = testUnitDaoImpl.listTestUnits(subPlan.getSubPlanId());	
			for (TestUnit testUnit : testUnitList) {
				
				testUnit.setDueDate(newTestPlan.getEndDate());
				testUnit.setCreateDate(createDate);
				testUnit.setTestPlanId(newTestPlanId);
				testUnit.setSubPlanId(newSubPlanId);
			
			}
			testUnitDaoImpl.addBatchTestUnits(testUnitList);
			
		}

	}

	
	public void delTestPlan(String[] testplans) throws Exception {

		List<SubTestPlan> subTestPlanList = new ArrayList<SubTestPlan>();
		for (int i = 0; i < testplans.length; i++) {
			List<SubTestPlan> tempSubTestPlanList = planMapDaoImpl.listSubTestPlan(Integer.parseInt(testplans[i].trim()));
			subTestPlanList.addAll(tempSubTestPlanList);
		}

		List<TestUnit> testUnitList = new ArrayList<TestUnit>();
		for (SubTestPlan subPlan : subTestPlanList) {

			List<TestUnit> tempTestUnitList = testUnitDaoImpl.listTestUnits(subPlan.getSubPlanId());
			testUnitList.addAll(tempTestUnitList);
		} // first step: delete 

		testUnitDaoImpl.delBatchTestUnits(testUnitList);

		subTestPlanDaoImpl.delBatchSubTestPlan(subTestPlanList);

		planMapDaoImpl.delBatchTestPlan(testplans);
 
		testPlanDaoImpl.delBatchTestPlan(testplans);

	}

	public int queryTestPlanSize(Map<String, String> filterMap)
			throws Exception {
		return testPlanDaoImpl.queryTestPlanSize(filterMap);
	}

	public TestPlan queryTestPlan(int testPlanId) throws Exception {
		return testPlanDaoImpl.queryTestPlan(testPlanId);
	}

	public List<TestPlan> listTestPlans(Map<String, String> filterMap)
			throws Exception {
		return testPlanDaoImpl.listTestPlans(filterMap);
	}

	/*
	 * TestPlan queryTestPlanAfterSubPlanChanged(SubTestPlan subTestPlan) throws
	 * Exception {
	 * 
	 * TestPlan testPlan = testPlanDaoImpl.queryTestPlanBySubPlan(subTestPlan
	 * .getSubPlanId());
	 * 
	 * List<SubTestPlan> subTestPlanList = subTestPlanDaoImpl
	 * .listSubTestPlan(testPlan.getTestPlanId());
	 * 
	 * testPlan = reCalcTestPlanAfterSubTestPlanChanged(testPlan,
	 * subTestPlanList);
	 * 
	 * return testPlan; }
	 */

	public void updateTestPlan(TestPlan testPlan) throws Exception {

		List<SubTestPlan> subTestPlanList = subTestPlanDaoImpl
				.listSubTestPlan(testPlan.getTestPlanId());
		String endDate = testPlan.getEndDate();

		for (SubTestPlan subTestPlan : subTestPlanList) {

			// update dueDate

			String subDueDate = subTestPlan.getDueDate();
			if (Utils
					.dateCompare(subDueDate, endDate, Constant.DATE_FORMAT_YMD) > 0) {
				subTestPlan.setDueDate(endDate);
			}

			subTestPlan.setModifyDate(testPlan.getModifyDate());
			subTestPlanDaoImpl.updateSubTestPlan(subTestPlan);
			List<TestUnit> testUnitList = testUnitDaoImpl
					.listTestUnits(subTestPlan.getSubPlanId());
			String newSubDueDate = subTestPlan.getDueDate();
			for (TestUnit testUnit : testUnitList) {
				String unitDueDate = testUnit.getDueDate();
				if (Utils.isNullORWhiteSpace(unitDueDate)) {
					testUnit.setDueDate(newSubDueDate);
				}
				if (Utils.dateCompare(unitDueDate, newSubDueDate,
						Constant.DATE_FORMAT_YMD) > 0) {
					testUnit.setDueDate(newSubDueDate);
				}
				testUnit.setModifyDate(subTestPlan.getModifyDate());
				testUnitDaoImpl.updateTestUnit(testUnit);
			}

		}
		testPlanDaoImpl.updateTestPlan(testPlan);
	}

	public TestPlan queryTestPlanBySubPlan(int subPlanId) throws Exception {

		return testPlanDaoImpl.queryTestPlanBySubPlan(subPlanId);
	}

	public int queryTestPlanSize(int projectId) {

		return testPlanDaoImpl.queryTestPlanSize(projectId);
	}
	public List<?> listTestPlanInfoByProjectId(int projectId,PageBean pageBean) {
		return testPlanDaoImpl.listTestPlanInfoByProjectId(projectId,pageBean);

	}
	
	public List<?> getTestPlanCountByPhaseList(int projectId) {
		return testPlanDaoImpl.getTestPlanCountByPhaseList(projectId);

	}
	
	public List<?> getCaseNumberByTestPlanList(int projectId) {
		return testPlanDaoImpl.getCaseNumberByTestPlanList(projectId);

	}
	
	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
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

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public TestUnitDaoImpl getTestUnitDaoImpl() {
		return testUnitDaoImpl;
	}

	public void setTestUnitDaoImpl(TestUnitDaoImpl testUnitDaoImpl) {
		this.testUnitDaoImpl = testUnitDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

}
