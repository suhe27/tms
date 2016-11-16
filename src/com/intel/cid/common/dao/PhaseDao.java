package com.intel.cid.common.dao;

import com.intel.cid.common.bean.Phase;

public interface PhaseDao {
	Phase queryPhaseById(int id) throws Exception;

	int delPhase(Phase phase) throws Exception;

	int delPhaseById(int id) throws Exception;

	int updatePhase(Phase phase) throws Exception;
	
	int addPhase(Phase phase) throws Exception;
}
