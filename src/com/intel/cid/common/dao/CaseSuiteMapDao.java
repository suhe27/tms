package com.intel.cid.common.dao;

import java.util.List;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.TestCase;


public interface CaseSuiteMapDao {

    int queryTestCaseSizeBySuitId(int suitId) throws Exception;
    
    List<TestCase> queryTestCaseByPageAndSuitId(PageBean pageBean,int suitId)throws Exception;
    
    List<TestCase> queryTestCasesBySuitId(int suitId) throws Exception;
    
    int addTestCaseBatchByArrayList(final List<TestCase> testcaseList, final int suitId) throws Exception;
    
    int addTestCaseBatchByStringArray(final String [] testcases, final int suitId)throws Exception;
    
    int delTestCaseSuitBySuitIdAndCaseId(int suitId, int caseId)throws Exception;
    
    int delTestCaseSuitBySuitId(int id) throws Exception;
}
