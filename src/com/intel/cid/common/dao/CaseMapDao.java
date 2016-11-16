package com.intel.cid.common.dao;

import com.intel.cid.common.bean.CaseMap;
import com.intel.cid.common.bean.TestCase;

public interface CaseMapDao {

   TestCase queryTestCaseByOldIdAndVersion(int testCaseId, String version) throws Exception;
   
   int delTestCaseByOldIdAndVersion(int testCaseId, String version) throws Exception;
   
   int addTestCaseIntoCaseMap(CaseMap caseMap)throws Exception;
   
   int delTestCaseFromCaseMap(CaseMap caseMap) throws Exception;
    
    
}
