package com.intel.cid.common.dao;

import com.intel.cid.common.bean.Build;

public interface BuildDao {
	Build queryBuildById(int id) throws Exception;

	int delBuild(int id) throws Exception;

	int updateBuild( final Build build) throws Exception;

	int addBuild(Build build) throws Exception;
}
