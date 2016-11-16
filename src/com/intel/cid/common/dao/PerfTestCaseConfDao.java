package com.intel.cid.common.dao;

import com.intel.cid.common.bean.PerfTestCaseConf;

public interface PerfTestCaseConfDao {

	
	PerfTestCaseConf queryPerfTestCaseConfByCaseId(int id) throws Exception;
	
	PerfTestCaseConf queryPerfTestCaseConfById(int id) throws Exception;

	int delPerfTestCaseConf(PerfTestCaseConf PerfTestCaseConf) throws Exception;

	int delPerfTestCaseConfById(int id) throws Exception;
	
	int delPerfTestCaseConfByCaseId(int id) throws Exception;

	int updatePerfTestCaseConf(PerfTestCaseConf PerfTestCaseConf) throws Exception;

	int addPerfTestCaseConf(PerfTestCaseConf PerfTestCaseConf) throws Exception;
	
	
}
