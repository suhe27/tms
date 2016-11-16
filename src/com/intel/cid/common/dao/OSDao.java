package com.intel.cid.common.dao;

import com.intel.cid.common.bean.OS;

public interface OSDao {
	OS queryOSById(int id) throws Exception;

	int delOS(OS OS) throws Exception;

	int delOSById(int id) throws Exception;

	int updateOS(OS OS) throws Exception;



	int addOS(OS OS) throws Exception;
}
