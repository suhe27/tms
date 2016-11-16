package com.intel.cid.common.dao;

import com.intel.cid.common.bean.PerfTestResultConf;

public interface PerfTestResultConfDao {

	
	
	PerfTestResultConf queryPerfTestResultConfById(int id) throws Exception;

	int delPerfTestResultConf(PerfTestResultConf PerfTestResultConf) throws Exception;

	int delPerfTestResultConfById(int id) throws Exception;

	int updatePerfTestResultConf(PerfTestResultConf PerfTestResultConf) throws Exception;

	int addPerfTestResultConf(PerfTestResultConf PerfTestResultConf) throws Exception;
}
