package com.intel.cid.common.dao.impl.test;

import org.junit.Test;

import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.test.AbstractTestCase;

public class TestFeatureDaoImpl extends AbstractTestCase {

    @Test
    public void addSubComponent() throws Exception {
        
        Feature feature = new Feature();
        feature.setFeatureId(1);
        feature.setFeatureName("xxx");
        feature.setPlatformId(1);
      
        
    }

}
