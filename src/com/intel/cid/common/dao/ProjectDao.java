package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.Project;

public interface ProjectDao {
	int addProject(Project project) throws Exception;

	int delProjectById(int id) throws Exception;

	int updateProject(Project poject) throws Exception;

	List<Project> queryAllProjects() throws Exception;
	 
	Project queryProjectById(int it) throws Exception;
	
}
