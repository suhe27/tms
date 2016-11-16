package com.intel.cid.common.dao;

import com.intel.cid.common.bean.PerfTestResult;

public interface PerfTestResultDao {

	PerfTestResult queryPerfTestResultById(int id) throws Exception;

	int delPerfTestResult(PerfTestResult PerfTestResult) throws Exception;

	int delPerfTestResultById(int id) throws Exception;

	int updatePerfTestResult(PerfTestResult PerfTestResult) throws Exception;

	int addPerfTestResult(PerfTestResult PerfTestResult) throws Exception;
}
