package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.TestUnit;

public interface TestUnitDao {

	
	int addTestUnit(TestUnit testUnit) throws Exception;
	
	void addBatchTestUnits(List<TestUnit> testUnitList) throws Exception;

	int delBatchTestUnits(List<TestUnit> testUnitList) throws Exception;

	int updateBatchTestUnit(List<TestUnit> testUnitList) throws Exception;

	int updateTestUnit(TestUnit testUnit) throws Exception;

	TestUnit queryTestUnit(int testUnitId) throws Exception;

	List<TestUnit> listTestUnits(int subPlanId) throws Exception;

}
