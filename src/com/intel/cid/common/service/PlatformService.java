package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.dao.impl.PlatformDaoImpl;

public class PlatformService {

	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(PlatformService.class);

    private PlatformDaoImpl platformDaoImpl;

    public List<Platform> listAllPlatforms() throws Exception {

        List<Platform> platformList = platformDaoImpl.listAllPlatforms();

        return platformList;

    }
    
	public int queryPlatformSize(Platform platform) throws Exception {
		   return platformDaoImpl.queryPlatformSize(platform);
	}
    
    public List<?>queryPlatformList(String teamId){
    	
    	return platformDaoImpl.queryPlatformList(teamId);
    }
    
    public List<Platform> listPlatformsInTeams(int userId) throws Exception {
    	return platformDaoImpl.listPlatformsInTeams(userId);
    }

    public Platform queryPlatformById(int id) throws Exception {

        return platformDaoImpl.queryPlatformById(id);

    }

    public List<Platform> listPlatforms(int id) throws Exception {

        return platformDaoImpl.listPlatforms(id);
    }
    
    public void delPlatformById(int id) throws Exception {

        platformDaoImpl.delPlatformById(id);
    }

    public void addPlatform(Platform platform) throws Exception {
        platformDaoImpl.addPlatform(platform);

    }

    public void updatePlatform(Platform platform) throws Exception {
        platformDaoImpl.updatePlatform(platform);

    }

    public PlatformDaoImpl getPlatformDaoImpl() {
        return platformDaoImpl;
    }

    public void setPlatformDaoImpl(PlatformDaoImpl platformDaoImpl) {
        this.platformDaoImpl = platformDaoImpl;
    }
}
