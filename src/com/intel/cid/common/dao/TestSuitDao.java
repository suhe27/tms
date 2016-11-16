package com.intel.cid.common.dao;

import com.intel.cid.common.bean.TestSuite;

public interface TestSuitDao {

    TestSuite queryTestSuiteById(int id) throws Exception;

    int delTestSuite(TestSuite TestCaseSuit) throws Exception;

    int delTestSuiteById(int id) throws Exception;

    int updateTestSuite(TestSuite TestCaseSuit) throws Exception;

    int addTestSuite(TestSuite TestCaseSuit) throws Exception;
}
