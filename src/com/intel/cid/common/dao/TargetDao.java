package com.intel.cid.common.dao;

import com.intel.cid.common.bean.Target;


public interface TargetDao {
	Target queryTargetById(int id) throws Exception;

	int delTarget(Target target) throws Exception;

	int delTargetById(int id) throws Exception;

	int updateTarget(Target target) throws Exception;

	int addTarget(Target target ) throws Exception;
}
