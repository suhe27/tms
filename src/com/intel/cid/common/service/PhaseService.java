package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.dao.impl.PhaseDaoImpl;

public class PhaseService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PhaseService.class);

	private PhaseDaoImpl phaseDaoImpl;

	public List<Phase> listAllPhases() throws Exception {

		List<Phase> PhaseList = phaseDaoImpl.queryAllPhases();

		return PhaseList;

	}

	public int queryPhaseSize(Phase phase) throws Exception {
		   return phaseDaoImpl.queryPhaseSize(phase);
	}
	
	public Phase queryPhaseById(int id) throws Exception {

		return phaseDaoImpl.queryPhaseById(id);

	}

	public void delPhaseById(int id) throws Exception {

		phaseDaoImpl.delPhaseById(id);
	}

	public void addPhase(Phase phase) throws Exception {
		phaseDaoImpl.addPhase(phase);

	}

	public void updatePhase(Phase phase) throws Exception {

		phaseDaoImpl.updatePhase(phase);

	}

	public List<Phase> queryAllPhasesByProjectId(int projectId)
			throws Exception {

		return phaseDaoImpl.queryAllPhasesByProjectId(projectId);
	}

	public List<?> queryPhasesByProjectId(int projectId) throws Exception {

		return phaseDaoImpl.queryPhasesByProjectId(projectId);
	}

	public List<Phase> queryAllPhasesByUserId(int userId) throws Exception {

		return phaseDaoImpl.queryAllPhasesByUserId(userId);
	}

	public PhaseDaoImpl getPhaseDaoImpl() {
		return phaseDaoImpl;
	}

	public void setPhaseDaoImpl(PhaseDaoImpl PhaseDaoImpl) {
		this.phaseDaoImpl = PhaseDaoImpl;
	}
}
