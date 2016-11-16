package com.intel.cid.common.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.dao.impl.CaseSuiteMapImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.TestSuiteDaoImpl;

public class TestCaseService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TestCaseService.class);

	private TestCaseDaoImpl testCaseDaoImpl;

	private CaseSuiteMapImpl caseSuiteMapImpl;

	private TestSuiteDaoImpl testSuiteDaoImpl;

	public List<TestCase> listTestCases() throws Exception {
		return testCaseDaoImpl.queryAllTestCases();

	}
	public int queryByNameCaseIdSize(TestCase testcase)throws Exception{
		return testCaseDaoImpl.queryByNameCaseIdSize(testcase);
	}
	
	
	public List<TestCase> queryTestCasesByFilter(Map<String, String> filterMap) {

		return testCaseDaoImpl.queryTestCasesByFilter(filterMap);
	}
	
	public List<TestCase> queryTestCasesByCondition(Map<String, String> filterMap) {

		return testCaseDaoImpl.queryTestCasesByCondition(filterMap);
	}

	public VersionState queryVersionState(int versionId) throws Exception {

		return testCaseDaoImpl.queryVersionState(versionId);

	}

	public CaseState queryCaseState(int caseStateId) throws Exception {
		return testCaseDaoImpl.queryCaseState(caseStateId);
	}

	public List<VersionState> listAllVersions() throws Exception {

		return testCaseDaoImpl.queryAllVersions();
	}

	public List<CaseState> listAllCaseStates() throws Exception {

		return testCaseDaoImpl.queryAllCaseStates();
	}

	public List<Automation> listAllAutomations() throws Exception {

		return testCaseDaoImpl.queryAllAutomations();
	}

	public TestCase queryTestCaseById(int id) throws Exception {
		return testCaseDaoImpl.queryTestCaseById(id);
	}

	public void delTestCaseById(int id) throws Exception {

		testCaseDaoImpl.delTestCaseById(id);
	}

	public int delBatchTestCases(final String[] testcases) throws Exception {

		for (String testCaseId : testcases) {

			List<TestSuite> testSuiteList = caseSuiteMapImpl
					.listTestSuiteByTestCase(Integer
							.parseInt(testCaseId.trim()));

			for (TestSuite testSuite : testSuiteList) {
				int totalCases = testSuite.getTotalCases();
				totalCases = totalCases - 1;
				if (totalCases <= 0) {
					testSuiteDaoImpl.delTestSuite(testSuite);
				} else {
					testSuite.setTotalCases(totalCases);
					testSuiteDaoImpl.updateTestSuite(testSuite);
				}
			}

		}

		return testCaseDaoImpl.delBatchTestCases(testcases);
	}

	public void addTestCase(TestCase testCase) throws Exception {
		testCaseDaoImpl.addTestCase(testCase);

	}

	public void updateTestCase(TestCase testCase) throws Exception {

		testCaseDaoImpl.updateTestCase(testCase);

	}

	public List<TestCase> listTestCasesByIds(String ids) {
		return testCaseDaoImpl.listTestCasesByIds(ids);
	}
	
	public List<TestCase> queryTestCaseByPage(PageBean pageBean,
			Map<String, String> filterMap) {
		return testCaseDaoImpl.queryTestCaseByPage(pageBean, filterMap);
	}

	public List<TestCase> listTestCaseNumInProjects(int id) {
		return testCaseDaoImpl.listTestCaseNumInProjects(id);
	}
	
	public List<TestCase> listTestCaseNumInProjectByAuto(int id) {
		return testCaseDaoImpl.listTestCaseNumInProjectByAuto(id);
	}
	
	public List<TestCase> listTestCaseNumInProjectByFeature(int id) {
		return testCaseDaoImpl.listTestCaseNumInProjectByFeature(id);
	}
	
	public int queryTestCaseSizeByFilter(Map<String, String> filterMap) {

		return testCaseDaoImpl.queryTestCaseSizeByFilter(filterMap);
	}
	
	public int queryAllTestCaseSizeByFilter(Map<String, String> filterMap) {

		return testCaseDaoImpl.queryAllTestCaseSizeByFilter(filterMap);
	}
	
	public Automation queryAutomation(int autoId) {
		
		return testCaseDaoImpl.queryAutomation(autoId);
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

	public TestSuiteDaoImpl getTestSuiteDaoImpl() {
		return testSuiteDaoImpl;
	}

	public void setTestSuiteDaoImpl(TestSuiteDaoImpl testSuiteDaoImpl) {
		this.testSuiteDaoImpl = testSuiteDaoImpl;
	}

}
