package com.intel.cid.common.dao;

import com.intel.cid.common.bean.Feature;

public interface FeatureDao {
	Feature queryFeatureById(int id) throws Exception;

	int delFeature(Feature feature) throws Exception;

	int delFeatureById(int id) throws Exception;

	int updateFeature(Feature feature) throws Exception;

	int addFeature(Feature feature) throws Exception;
	
	
}
