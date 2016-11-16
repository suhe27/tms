package com.intel.cid.common.dao;

import com.intel.cid.common.bean.TestCase;

public interface TestCaseDao {
	TestCase queryTestCaseById(int id) throws Exception;

	int delTestCase(TestCase TestCase) throws Exception;

	int delTestCaseById(int id) throws Exception;

	int updateTestCase(TestCase TestCase) throws Exception;

	int addTestCase(TestCase TestCase) throws Exception;
}
