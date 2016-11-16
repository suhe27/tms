package com.intel.cid.common.dao;

import java.util.List;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.TestExecution;

public interface TestExecutionDao {
	public List<TestExecution> listTestExecutionByPage(PageBean pageBean,
			TestExecution testexecution) throws Exception;
	TestExecution queryTestExecutionById(int id)throws Exception;
	int deleTestexecution( String[] testplans)throws Exception;;
	int updateTestexecution(TestExecution testexecution)throws Exception;;
	int  addTestexecution (TestExecution testexecution)throws Exception;;
}
