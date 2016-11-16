package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.dao.impl.TestTypeDaoImpl;

public class TestTypeService {

    
	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(TestTypeService.class);  
    
    private TestTypeDaoImpl testTypeDaoImpl;

    
    public List<TestType> listAllTestTypes() throws Exception {
        
        List<TestType> testTypeList= testTypeDaoImpl.queryAllTestTypes();
         return testTypeList; 
     }
    
	public int queryTestTypeSize(TestType testType) throws Exception {
		   return testTypeDaoImpl.queryTestTypeSize(testType);
	}
    
    public List<TestType> listTestTypeByProjectId(int id) throws Exception {
        
        List<TestType> testTypeList= testTypeDaoImpl.listTestTypeByProjectId(id);
         return testTypeList;
          
     }
    
     public TestType queryTestTypeById(int id) throws Exception{
         
         return testTypeDaoImpl.queryTestTypeById(id);
         
     }
     
     public void delTestTypeById(int id )  throws Exception{
         
         testTypeDaoImpl.delTestTypeById(id);
     }
     
     public void addTestType(TestType testType) throws Exception
     {
        testTypeDaoImpl.addTestType(testType);
     }
     
     
     public void updateTestType(TestType testType)throws Exception {
         testTypeDaoImpl.updateTestType(testType);   
     }
    
    public TestTypeDaoImpl getTestTypeDaoImpl() {
        return testTypeDaoImpl;
    }

    public void setTestTypeDaoImpl(TestTypeDaoImpl testTypeDaoImpl) {
        this.testTypeDaoImpl = testTypeDaoImpl;
    }
    
    
    
    
}
