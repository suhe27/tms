package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.dao.impl.SubComponentDaoImpl;

public class SubComponentService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SubComponentService.class);

	private SubComponentDaoImpl subComponentDaoImpl;

	public int addSubComponent(final SubComponent subComponent)
			throws Exception {

		return subComponentDaoImpl.addSubComponent(subComponent);
	}

	public int delSubComponentById(int id) throws Exception {
		return subComponentDaoImpl.delSubComponentById(id);
		
	}

	public int querySubComponentSize(SubComponent subComponent) throws Exception {
		   return subComponentDaoImpl.querySubComponentSize(subComponent);
	}
	
	public List<SubComponent> queryAllSubComponents() throws Exception {
		return subComponentDaoImpl.queryAllSubComponents();
	}

	public SubComponent querySubComponentById(int id) throws Exception {
		return subComponentDaoImpl.querySubComponentById(id);
	}

	public int updateSubComponent(final SubComponent subComponent)
			throws Exception {
		return subComponentDaoImpl.updateSubComponent(subComponent);
	}

	public List<SubComponent> listSubComponentsByCompId(String compId)
			throws Exception {
		return subComponentDaoImpl.listSubComponentsByCompId(compId);
	}

	public SubComponentDaoImpl getSubComponentDaoImpl() {
		return subComponentDaoImpl;
	}

	public void setSubComponentDaoImpl(SubComponentDaoImpl subComponentDaoImpl) {
		this.subComponentDaoImpl = subComponentDaoImpl;
	}

}
