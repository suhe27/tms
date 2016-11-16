package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Build;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.impl.BuildDaoImpl;

public class BuildService {

	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(BuildService.class);

    private BuildDaoImpl BuildDaoImpl;

    public List<Build> listAllBuilds() throws Exception {

        List<Build> BuildList = BuildDaoImpl.queryAllbuilds();

        return BuildList;

    }

	public int queryBuildSize(Build build) throws Exception {
		   return BuildDaoImpl.queryBuildSize(build);
	}
    
    public List<Build> queryBuildByProjectId(int id) throws Exception {
    	
        List<Build> BuildList = BuildDaoImpl.queryBuildByProjectId(id);
        return BuildList;

    }
    
    public Build queryBuildById(int id) throws Exception {

        return BuildDaoImpl.queryBuildById(id);

    }

    public void delBuildById(int id) throws Exception {

        BuildDaoImpl.delBuild(id);
    }

    public void addBuild(Build Build) throws Exception {
        BuildDaoImpl.addBuild(Build);

    }

    
    public void updateBuild(Build Build) throws Exception {
        
        BuildDaoImpl.updateBuild(Build);
        
    }
    public BuildDaoImpl getBuildDaoImpl() {
        return BuildDaoImpl;
    }

    public void setBuildDaoImpl(BuildDaoImpl BuildDaoImpl) {
        this.BuildDaoImpl = BuildDaoImpl;
    }
}
