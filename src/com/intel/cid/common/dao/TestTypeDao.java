package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.TestType;

public interface TestTypeDao {

	TestType queryTestTypeById(int id) throws Exception;

	int delTestType(TestType TestType) throws Exception;

	int delTestTypeById(int id) throws Exception;

	int updateTestType(TestType TestType) throws Exception;

	int addTestType(TestType testType) throws Exception;

	public List<TestType> queryAllTestTypes() throws Exception;
}
