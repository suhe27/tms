package com.intel.cid.common.dao;

import java.util.List;

public interface ReportDao {
	
	public List getProjectList(String username);
	
	public List getWeekList(String project,String username);

	public List getReleaseCycle(String project,String cycleid);
	
	public List getTestRelease(String project,String releaseCycle);
	
	public List getSubplanForName(String project,String releaseCycle);
	
	public List getSubplanInfo(String project,String subplanname);
	
	public int getSubplanTotalCases(String project,String subplanname);
	
	public List getLastReleaseCycle();
	
	public List getSubplanForColumn(String project,String subplanname,String column);
	public List getTestResult(String project,String subplanname,int resultType);
	public int getTotalCases();
	public List getTestPlanStatus();
	
	public List getTestPlanDuration();
}