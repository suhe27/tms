package com.intel.cid.common.dao;


import com.intel.cid.common.bean.ExecutionOS;

public interface ExecutionOSDao {
	ExecutionOS queryExecutionOSById(int id) throws Exception;

	int delExecutionOSById(int id) throws Exception;

	int updateExecutionOS(ExecutionOS executionOS) throws Exception;



	int addExecutionOS(ExecutionOS executionOS) throws Exception;
}
