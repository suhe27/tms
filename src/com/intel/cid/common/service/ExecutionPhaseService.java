package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.impl.ExecutionPhaseDaoImpl;

public class ExecutionPhaseService {

	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(ExecutionPhaseService.class);

    private ExecutionPhaseDaoImpl executionPhaseDaoImpl;

    public List<Phase> listAllPhases() throws Exception {

        List<Phase> PhaseList = executionPhaseDaoImpl.queryAllPhases();
        return PhaseList;
    }
    
	public int queryEphaseSize(Phase phase) throws Exception {
		   return executionPhaseDaoImpl.queryEphaseSize(phase);
	}
    
    public List<Phase> queryPhaseByProjectId(int id) throws Exception {

        List<Phase> PhaseList = executionPhaseDaoImpl.queryPhaseByProjectId(id);
        return PhaseList;
    }
    
    public Phase queryPhaseById(int id) throws Exception {

        return executionPhaseDaoImpl.queryPhaseById(id);

    }

    public void delPhaseById(int id) throws Exception {

    	executionPhaseDaoImpl.delPhaseById(id);
    }

    public void addPhase(Phase phase) throws Exception {
    	executionPhaseDaoImpl.addPhase(phase);

    }

    
    public void updatePhase(Phase phase) throws Exception {
        
    	executionPhaseDaoImpl.updatePhase(phase);
        
    }

	public ExecutionPhaseDaoImpl getExecutionPhaseDaoImpl() {
		return executionPhaseDaoImpl;
	}

	public void setExecutionPhaseDaoImpl(ExecutionPhaseDaoImpl executionPhaseDaoImpl) {
		this.executionPhaseDaoImpl = executionPhaseDaoImpl;
	}
   
}
