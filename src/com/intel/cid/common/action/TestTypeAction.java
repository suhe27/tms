package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class TestTypeAction extends BaseAction {

	private static final long serialVersionUID = 4020208837399278745L;

	private static Logger logger = Logger.getLogger(TestTypeAction.class);

	private int typeId;

	private String typeName;
	
	private int projectId;

	public String listTestTypes() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<TestType> testTypeList = testTypeService.listAllTestTypes();
		context.put("testTypeList", testTypeList);
		return SUCCESS;

	}

	public String delTestType() throws Exception {
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("testTypeId", Integer.toString(typeId));
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current test type have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			testTypeService.delTestTypeById(typeId);
			return SUCCESS;
		}

	}

	public String toUpdateTestType() throws Exception {

		ActionContext context = ActionContext.getContext();

		TestType testType = testTypeService.queryTestTypeById(typeId);
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		context.put("testType", testType);
		return SUCCESS;

	}

	public String updateTestType() throws Exception {
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		TestType testType = new TestType();
		testType.setTypeId(typeId);
		testType.setTypeName(typeName);
		testType.setProjectId(projectId);
		
		int result = testTypeService.queryTestTypeSize(testType);
		if(result==1){
			Project project = projectSerivce.queryProjectById(testType.getProjectId());
			context.put(Constant.ERRORMSG, "Test type name '" + testType.getTypeName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		testTypeService.updateTestType(testType);
		logger.info(testType);
		return SUCCESS;

	}

	public String toAddTestType() throws Exception {
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryAllProjects();
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addTestType() throws Exception {
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		TestType testType = new TestType();
		testType.setTypeId(typeId);
		testType.setTypeName(typeName);
		testType.setProjectId(projectId);
		
		int result = testTypeService.queryTestTypeSize(testType);
		if(result==1){
			Project project = projectSerivce.queryProjectById(testType.getProjectId());
			context.put(Constant.ERRORMSG, "Test type name '" + testType.getTypeName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		testTypeService.addTestType(testType);

		return SUCCESS;

	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
