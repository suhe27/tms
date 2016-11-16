package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.MediaParameters;

public interface MediaParametersDao {

	
	
	public int addMediaParameters(MediaParameters para) throws Exception ;
	
	
	public List<MediaParameters>  listAllPaMediaParameterList() throws Exception ;
	
	
}
