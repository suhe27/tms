package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.dao.impl.BoardDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;

public class TestUnitService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(TestUnitService.class);

	private TestUnitDaoImpl testUnitDaoImpl;

	private BoardDaoImpl boardDaoImpl;

	private TestResultDaoImpl testResultDaoImpl;

	private UserDaoImpl userDaoImpl;

	public int delBatchTestUnitsByIds(final String[] testUnits)
			throws Exception {

		return testUnitDaoImpl.delBatchTestUnitsByIds(testUnits);
	}

	public TestUnit queryTestUnit(String testUnitName, int testplanId)
			throws Exception {

		return testUnitDaoImpl.queryTestUnit(testUnitName, testplanId);
	}
 
	public void addBatchTestUnits(final List<TestUnit> testUnitList)
			throws Exception {

		testUnitDaoImpl.addBatchTestUnits(testUnitList);
	}

	public int delBatchTestUnits(final List<TestUnit> testUnitList)
			throws Exception {

		return testUnitDaoImpl.delBatchTestUnits(testUnitList);
	}

	public List<TestUnit> listTestUnits(int subPlanId) throws Exception {

		return testUnitDaoImpl.listTestUnits(subPlanId);
	}

	public List<TestUnit> listFailedTestUnits(int subPlanId,String isUpdate) throws Exception {
		return testUnitDaoImpl.listFailedTestUnits(subPlanId,isUpdate);
	}

	 
	public boolean isUniqueTestUnit(int subPlanId, int testUnitId, String unitName)throws Exception {
          return testUnitDaoImpl.isUniqueTestUnit(subPlanId, testUnitId, unitName);
	}
	public TestUnit queryTestUnit(int testUnitId) throws Exception {

		return testUnitDaoImpl.queryTestUnit(testUnitId);
	}

	public int updateTestUnit(final TestUnit testUnit) throws Exception {

		return testUnitDaoImpl.updateTestUnit(testUnit);
	}

	public int updateBatchTestUnit(final List<TestUnit> testUnitList)
			throws Exception {
		return testUnitDaoImpl.updateBatchTestUnit(testUnitList);

	}

	public List<TestCase> getTestCasesFromTestUnit(int testUnitId)
			throws Exception {

		return testUnitDaoImpl.getTestCasesFromTestUnit(testUnitId);
	}

	public List<TestCase> getAllFailedTestCasesFromTestUnit(int testUnitId)
			throws Exception {

		return testUnitDaoImpl.getAllFailedTestCasesFromTestUnit(testUnitId);
	}

	public TestUnitDaoImpl getTestUnitDaoImpl() {
		return testUnitDaoImpl;
	}

	public void setTestUnitDaoImpl(TestUnitDaoImpl testUnitDaoImpl) {
		this.testUnitDaoImpl = testUnitDaoImpl;
	}

	public BoardDaoImpl getBoardDaoImpl() {
		return boardDaoImpl;
	}

	public void setBoardDaoImpl(BoardDaoImpl boardDaoImpl) {
		this.boardDaoImpl = boardDaoImpl;
	}

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

}
