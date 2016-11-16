package com.intel.cid.common.dao.impl.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intel.cid.common.bean.User;
import com.intel.cid.common.test.AbstractTestCase;

public class TestUserDaoImpl extends AbstractTestCase {

    

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testListUser() throws Exception {

        List<com.intel.cid.common.bean.User> userList = userDaoImpl.queryAllUsers();
        Assert.assertNotNull(userList.size() > 0);

    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Test
    public void testAddUser() throws Exception{
    	
        User user = new User();
        user.setUserName("ruanxu");
        user.setPassword("   ");
        user.setTeamId(0);
        user.setEmail("   ");
        int res= userDaoImpl.addUser(user);
        
        Assert.assertEquals(1, res);
        
        
    }
    
    
    
    @After
    public void tearDown() throws Exception {

    }

}
