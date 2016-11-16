package com.intel.cid.common.dao.impl.test;

import org.junit.Assert;
import org.junit.Test;

import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.test.AbstractTestCase;

public class TestPlatformDaoImpl extends AbstractTestCase {

    
    @Test
    public void addPlatform() throws Exception{
        
        Platform platform = new Platform();
        platform.setPlatformName("XXX");
        int res= platformDaoImpl.addPlatform(platform);   
        Assert.assertEquals(1, res);
    }
}
