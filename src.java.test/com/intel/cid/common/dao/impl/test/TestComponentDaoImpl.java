package com.intel.cid.common.dao.impl.test;

import junit.framework.Assert;

import org.junit.Test;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.test.AbstractTestCase;

public class TestComponentDaoImpl extends AbstractTestCase {

    @Test
    public void addComponent() throws Exception {

        Component component = new Component();
        component.setCompName("accelaration");
        component.setFeatureId(1);
        int res = componentDaoImpl.addComponent(component);
        Assert.assertEquals(1, res);

    }

}
