package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.SubComponent;

public interface SubComponentDao {

	
List<SubComponent> queryAllSubComponents() throws Exception;
    
    SubComponent querySubComponentById( int id) throws Exception;
    
    int delSubComponent(SubComponent subComponent) throws Exception;
    
    int delSubComponentById(int id) throws Exception ;
    
    int updateSubComponent(SubComponent subComponent) throws Exception;
    
    int addSubComponent(SubComponent subComponent) throws Exception;
	
	
}
