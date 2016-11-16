package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.dao.impl.ProjectDaoImpl;

public class ProjectSerivce {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ProjectSerivce.class);

	private ProjectDaoImpl projectDaoImpl;

	public int addProject(Project project) throws Exception {
		return projectDaoImpl.addProject(project);
	}

	public int delProjectById(int id) throws Exception {
		return projectDaoImpl.delProjectById(id);
	}

	public List<Project> queryAllProjects() throws Exception{
		return projectDaoImpl.queryAllProjects();
	}
	public int updateProject(Project project) throws Exception {
		return projectDaoImpl.updateProject(project);
	}

	public Project queryProjectById(int id) throws Exception{
		return projectDaoImpl.queryProjectById(id);
	}
	
	public int queryProjectSize(Project project) throws Exception {
		   return projectDaoImpl.queryProjectSize(project);
	}
	
	public List<Project> queryProjectByUserId(int userId) throws Exception{
		return projectDaoImpl.queryProjectByUserId(userId);
	}
	public List<?> queryPorjectByTeamId(String teamId) {
		
		return projectDaoImpl.queryPorjectByTeamId(teamId);
	}
	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}

	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
	}
	

	
}
