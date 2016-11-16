package com.intel.cid.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.TestEnv;
import com.intel.cid.common.bean.User;
import com.opensymphony.xwork2.ActionContext;

public class TestEnvAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(TestEnvAction.class);

	private TestEnv testenv;

	public String listTestenvs() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<TestEnv> testenvList = testEnvService.queryAllEnvs();
		context.put("testenvList", testenvList);
		return SUCCESS;
		

	}

	public String delTestenv() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testEnvService.delTestEnvById(testenv.getId());
		return SUCCESS;
	}


	
	
	public String toUpdateTestenv() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		TestEnv testenvObj = testEnvService.querytestEnvById(testenv.getId());
		context.put("testenvObj", testenvObj);
		context.put("projectList", projectList);

		return SUCCESS;

	}

	public String updateTestenv() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testEnvService.updateTestenv(testenv);
		logger.info(testenv);
		return SUCCESS;

	}

	public String toAddTestenv() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addTestenv() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testEnvService.addTestenv(testenv);
		return SUCCESS;

	}

	public TestEnv getTestenv() {
		return testenv;
	}

	public void setTestenv(TestEnv testenv) {
		this.testenv = testenv;
	}

	

}
