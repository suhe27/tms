package com.intel.cid.common.dao;

import com.intel.cid.common.bean.TestEnv;

public interface TestEnvDao {

	

	int delTestenvById(int id) throws Exception;

	int updateTestenv(TestEnv testenv) throws Exception;

	int addTestenv(TestEnv testenv) throws Exception;
}
