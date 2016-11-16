package com.intel.cid.common.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.intel.cid.common.bean.ExecutionOS;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.JsonUtil;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class ExecutionOSAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(ExecutionOSAction.class);

	private int osId;

	private String osName;

	private ExecutionOS executionOS;

	public String listExecutionOSs() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<ExecutionOS> osList = executionOSService.listAllExecutionOSs();
		context.put("osList", osList);

		return SUCCESS;

	}
	
	public String delExecutionOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		executionOSService.delExecutionOSById(osId);
		return SUCCESS;
	}

	public String toUpdateExecutionOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryProjectByUserId(user
				.getUserId());
		ExecutionOS os = executionOSService.queryOSById(osId);
		context.put("os", os);
		context.put("projectList", projectList);

		return SUCCESS;

	}

	public String updateExecutionOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		int result = executionOSService.queryExecutionOSSize(executionOS);
		if(result==1){
			Project project = projectSerivce.queryProjectById(executionOS.getProjectId());
			context.put(Constant.ERRORMSG, "Execution OS name '" + executionOS.getOsName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}

		executionOSService.updateOS(executionOS);
		logger.info(executionOS);
		return SUCCESS;

	}

	public String toAddExecutionOS() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Project> projectList = projectSerivce.queryProjectByUserId(user
				.getUserId());

		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addExecutionOS() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		int result = executionOSService.queryExecutionOSSize(executionOS);
		if(result==1){
			Project project = projectSerivce.queryProjectById(executionOS.getProjectId());
			context.put(Constant.ERRORMSG, "Execution OS name '" + executionOS.getOsName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		Date date = new Date();
		executionOS.setCreateDate(Utils.dateFormat(date, null));	
		executionOSService.addOS(executionOS);
		return SUCCESS;

	}

	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public ExecutionOS getExecutionOS() {
		return executionOS;
	}

	public void setExecutionOS(ExecutionOS executionOS) {
		this.executionOS = executionOS;
	}

}
