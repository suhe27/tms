package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.Target;
import com.intel.cid.common.dao.impl.TargetDaoImpl;

public class TargetService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TargetService.class);

	private TargetDaoImpl targetDaoImpl;

	public Target queryTargetById(int id) throws Exception {
		return targetDaoImpl.queryTargetById(id);
	}

	public int delTarget(Target target) throws Exception {
		return targetDaoImpl.delTarget(target);
	}

	public int delTargetById(int id) throws Exception {
		return targetDaoImpl.delTargetById(id);
	}
	
	public int queryTargetSize(Target target) throws Exception {
		   return targetDaoImpl.queryTargetSize(target);
	}

	public int updateTarget(Target target) throws Exception {
		return targetDaoImpl.updateTarget(target);
	}

	public int addTarget(Target target) throws Exception {
		return targetDaoImpl.addTarget(target);
	}

	public List<Target> queryAllTarget() throws Exception {
		return targetDaoImpl.queryAllTarget();
	}

	public List<Target> listTargetByProjectId(int projectId) throws Exception {

		return targetDaoImpl.listTargetByProjectId(projectId);
	}

	public TargetDaoImpl getTargetDaoImpl() {
		return targetDaoImpl;
	}

	public void setTargetDaoImpl(TargetDaoImpl targetDaoImpl) {
		this.targetDaoImpl = targetDaoImpl;
	}

}
