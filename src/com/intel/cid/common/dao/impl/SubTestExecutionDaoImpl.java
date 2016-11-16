package com.intel.cid.common.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.intel.cid.common.bean.SubTestExecution;
import com.intel.cid.common.dao.SubTestExecutionDao;

public class SubTestExecutionDaoImpl implements SubTestExecutionDao {


	private static Logger logger = Logger.getLogger(SubTestExecutionDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;
	
	public int addSubTestExecution(SubTestExecution subExecution)
			throws Exception {
		
		return 0;
	}

	
	public int delBatchSubTestExecution(String[] subExecutions)
			throws Exception {
		
		return 0;
	}

	
	public int delSubTestExecution(SubTestExecution subExecution)
			throws Exception {
		
		return 0;
	}

	
	public int delSubTestExecutionById(int subExecutionId) throws Exception {
		
		return 0;
	}

	
	public List<SubTestExecution> listSubTestExecution(int executionId)
			throws Exception {
		
		return null;
	}

	
	public SubTestExecution querySubTestExecutionById(int subExecutionId)
			throws Exception {
		
		return null;
	}

	
	public int updateSubTestExecution(SubTestExecution subExecution)
			throws Exception {
		
		return 0;
	}

}
