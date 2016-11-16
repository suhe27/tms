package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.SubTestExecution;

public interface SubTestExecutionDao {

	int addSubTestExecution(SubTestExecution subExecution) throws Exception;

	public int delBatchSubTestExecution(final String[] subExecutions)
			throws Exception;

	int updateSubTestExecution(SubTestExecution subExecution) throws Exception;

	public List<SubTestExecution> listSubTestExecution(int executionId)
			throws Exception;

	SubTestExecution querySubTestExecutionById(int subExecutionId)
			throws Exception;

	int delSubTestExecution(SubTestExecution subExecution) throws Exception;

	int delSubTestExecutionById(int subExecutionId) throws Exception;

}
