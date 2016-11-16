package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.ExecutionOS;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.impl.ExecutionOSDaoImpl;

public class ExecutionOSService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ExecutionOSService.class);

	private ExecutionOSDaoImpl executionOSDaoImpl;

	public List<ExecutionOS> listAllExecutionOSs() throws Exception {

		List<ExecutionOS> oSList = executionOSDaoImpl.queryExecutionOSAll();

		return oSList;

	}
	public List<ExecutionOS> listAllExecutionOSByProject(int id) throws Exception {

		List<ExecutionOS> oSList = executionOSDaoImpl.queryExecutionOSByProject(id);

		return oSList;

	}
	public List<ExecutionOS> queryExecutionOsByUserId(int userId) throws Exception {
		return executionOSDaoImpl.queryExecutionOsByUserId(userId);
	}
	public ExecutionOS queryOSById(int id) throws Exception {

		return executionOSDaoImpl.queryExecutionOSById(id);

	}

	public void delExecutionOSById(int id) throws Exception {

		executionOSDaoImpl.delExecutionOSById(id);
	}

	public void addOS(ExecutionOS os) throws Exception {
		executionOSDaoImpl.addExecutionOS(os);

	}

	public int queryExecutionOSSize(ExecutionOS executionOS) throws Exception {
		   return executionOSDaoImpl.queryExecutionOSSize(executionOS);
	}

	public void updateOS(ExecutionOS os) throws Exception {

		executionOSDaoImpl.updateExecutionOS(os);

	}

	public ExecutionOSDaoImpl getExecutionOSDaoImpl() {
		return executionOSDaoImpl;
	}

	public void setExecutionOSDaoImpl(ExecutionOSDaoImpl executionOSDaoImpl) {
		this.executionOSDaoImpl = executionOSDaoImpl;
	}

}
