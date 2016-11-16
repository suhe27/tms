package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class ComponentAction extends BaseAction {

	private static final long serialVersionUID = -6322934324106338267L;

	private static Logger logger = Logger.getLogger(BaseAction.class);

	private String compId;

	private String featureId;

	private String compName;

	private String multiSubCompId;
	
	public String listComponents() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		//test
		List<Component> componentList = componentService.listComponents();
		context.put("componentList", componentList);
		return SUCCESS;

	}

	public String delComponent() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("compId", compId);
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current component have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			componentService.delComponentById(Integer.parseInt(compId));
			return SUCCESS;
		}

	}

	public String toUpdateComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Component component = componentService.queryComponentById(Integer.parseInt(compId));
		List<SubComponent> subCompList= subComponentService.queryAllSubComponents();
		List<SubComponent> ownerSubCompList = subComponentService.listSubComponentsByCompId(String.valueOf(compId));
		context.put("subCompList", subCompList);
		context.put("ownerSubCompList", ownerSubCompList);
		context.put("comp", component);

		return SUCCESS;

	}

	public String updateComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Component component = new Component();
		component.setCompId(Integer.parseInt(compId));
		component.setCompName(compName);
		
		int result = componentService.queryComponentSize(component);
		if(result==1){
			context.put(Constant.ERRORMSG, "Component name '" + component.getCompName() + "' already exist!");
			return ERROR;
		}
		
		String[] subcomps = new String[]{};
		if (!Utils.isNullORWhiteSpace(multiSubCompId)) {
			 subcomps = multiSubCompId.split(",");
		}
		componentService.updatComponent(component,subcomps);
		logger.info(component);
		return SUCCESS;

	}

	public String toAddComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		List<SubComponent>subCompList = subComponentService.queryAllSubComponents();
		if (user == null) {
			return LOGIN;
		}
		
		context.put("subCompList", subCompList);
		return SUCCESS;

	}

	public String addComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Component component = new Component();
		component.setCompName(compName);
		
		int result = componentService.queryComponentSize(component);
		if(result==1){
			context.put(Constant.ERRORMSG, "Component name '" + component.getCompName() + "' already exist!");
			return ERROR;
		}
		
		String[] subcomps = new String[]{};
		if (!Utils.isNullORWhiteSpace(multiSubCompId)) {
			 subcomps = multiSubCompId.split(",");
		}
		
		componentService.addComponent(component,subcomps);
		return SUCCESS;

	}

	

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getMultiSubCompId() {
		return multiSubCompId;
	}

	public void setMultiSubCompId(String multiSubCompId) {
		this.multiSubCompId = multiSubCompId;
	}

	
	
}
