package com.intel.cid.common.dao;

import java.util.ArrayList;
import java.util.List;

import com.intel.cid.common.bean.DBInfo;

public interface RequirementDao {
	
	public List<?> getDBAliasList();
	
	public DBInfo getDBInfo(int id);
	
	public ArrayList<String> getAllTestSuites(DBInfo dbinfo);
	
	public String batchInsertRequirement();
	
	public List getXTPProjectList();
	
	public int deleteXTPProject(int id);
	
	public int addXtp_requirement_info();
	
	public List getXtp_requirement_info();

}
