package com.intel.cid.common.dao.impl.test;

import org.junit.Assert;
import org.junit.Test;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.test.AbstractTestCase;

public class TestOSDaoImpl extends AbstractTestCase{

    
    @Test
    public void addOS() throws Exception{
        OS os = new OS();
        os.setOsName("windows 7");
        int res = oSDaoImpl.addOS(os);      
        Assert.assertEquals(1, res);
        
    }
}
