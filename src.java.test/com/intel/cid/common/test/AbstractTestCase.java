package com.intel.cid.common.test;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.intel.cid.common.dao.impl.BoardDaoImpl;
import com.intel.cid.common.dao.impl.ComponentDaoImpl;
import com.intel.cid.common.dao.impl.OSDaoImpl;
import com.intel.cid.common.dao.impl.PlatformDaoImpl;
import com.intel.cid.common.dao.impl.TeamDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;
import com.intel.cid.common.service.SubTestPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public abstract class AbstractTestCase extends AbstractJUnit4SpringContextTests {
	public String[] getConfigLocations() {  

		       String[] configLocations = { "file:WebContent/WEB-INF/applicationContext.xml" };  

		      return configLocations;  

		   }  

    @Resource(name = "userDaoImpl")
    protected UserDaoImpl userDaoImpl;

    @Resource(name = "boardDaoImpl")
    protected BoardDaoImpl boardDaoImpl;

    @Resource(name = "componentDaoImpl")
    protected ComponentDaoImpl componentDaoImpl;

    @Resource(name = "oSDaoImpl")
    protected OSDaoImpl oSDaoImpl;

    @Resource(name = "platformDaoImpl")
    protected PlatformDaoImpl platformDaoImpl;

    @Resource(name = "teamDaoImpl")
    protected TeamDaoImpl teamDaoImpl;

    @Resource(name = "testCaseDaoImpl")
    protected TestCaseDaoImpl testCaseDaoImpl;

    @Resource(name = "subTestPlanService")
    protected  SubTestPlanService subTestPlanService;
}
