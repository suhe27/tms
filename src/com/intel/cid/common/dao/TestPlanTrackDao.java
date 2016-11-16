package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.TestPlanTrack;

public interface TestPlanTrackDao {

	
	public int addBatchTestPlanTrack(List<TestPlanTrack> trackList) throws Exception;
	
	
}
