package com.intel.cid.common.dao.impl.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.test.AbstractTestCase;

public class TestTestCaseDaoImpl extends AbstractTestCase {

    
    
    @Test
    public void listTestCases() throws Exception {
        
        List<TestCase> testCaseList = testCaseDaoImpl.queryAllTestCases();
        
        Assert.assertTrue(testCaseList.size()>0);
        
        
        
    }
}
