package com.intel.cid.common.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.TestEnv;
import com.intel.cid.common.dao.impl.OSDaoImpl;
import com.intel.cid.common.dao.impl.TestEnvDaoImpl;

/**
 * @author tanghaix
 *
 */
public class TestEnvService {

	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(TestEnvService.class);

    private TestEnvDaoImpl testEnvDaoImpl;
    
    public int addTestenv(final TestEnv testenv) throws Exception {
    	return testEnvDaoImpl.addTestenv(testenv);
     
    }

	public int delTestEnvById(int id) throws Exception {
     
        return testEnvDaoImpl.delTestenvById(id);
    }

    public List<TestEnv> queryAllEnvs() throws Exception {
 
        return testEnvDaoImpl.queryAllEnvs();
    }
    
    public List<TestEnv> queryAlltestEnvs( int id) throws Exception {
    	 
        return testEnvDaoImpl.queryAlltestenvs(id);
    }
    
	public TestEnvDaoImpl getTestEnvDaoImpl() {
		return testEnvDaoImpl;
	}

	public void setTestEnvDaoImpl(TestEnvDaoImpl testEnvDaoImpl) {
		this.testEnvDaoImpl = testEnvDaoImpl;
	}

	public TestEnv querytestEnvById(int id) throws Exception {
       
        return testEnvDaoImpl.querytestenvById(id);
    }

    public int updateTestenv(final TestEnv testenv) throws Exception {
       
        return testEnvDaoImpl.updateTestenv(testenv);
    }

  
  

}
