package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.Component;

public interface ComponentDao {
	List<Component> queryAllComponents() throws Exception;
    
    Component queryComponentById( int id) throws Exception;
    
    int delComponent(Component component) throws Exception;
    
    int delComponentById(int id) throws Exception ;
    
    int updateComponent(Component component) throws Exception;
    
    int addComponent(Component component) throws Exception;
}
