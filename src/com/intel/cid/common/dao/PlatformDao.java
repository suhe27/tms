package com.intel.cid.common.dao;

import com.intel.cid.common.bean.Platform;

public interface PlatformDao {
    Platform queryPlatformById(int id) throws Exception;

    int delPlatform(Platform Platform) throws Exception;

    int delPlatformById(int id) throws Exception;

    int updatePlatform(Platform Platform) throws Exception;

    int addPlatform(Platform Platform) throws Exception;
    
}
