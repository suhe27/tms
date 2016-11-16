package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class SubComponentAction extends BaseAction {

	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BaseAction.class);

	private String compId;

	
	private String compName;

	public String listSubComponents() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<SubComponent> componentList = subComponentService.queryAllSubComponents();
		context.put("componentList", componentList);
		return SUCCESS;

	}

	public String delSubComponent() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("subComp", compId);
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current sub component have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			subComponentService.delSubComponentById(Integer.parseInt(compId));
			return SUCCESS;
		}
	}

	public String toUpdateSubComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		SubComponent component = subComponentService.querySubComponentById(Integer.parseInt(compId));

		context.put("comp", component);

		return SUCCESS;

	}

	public String updateSubComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		SubComponent component = new SubComponent();
		component.setSubCompId(Integer.parseInt(compId));
		component.setSubCompName(compName);

		int result = subComponentService.querySubComponentSize(component);
		if(result==1){
			context.put(Constant.ERRORMSG, "Sub component name '" + component.getSubCompName() + "' already exist!");
			return ERROR;
		}
		
		subComponentService.updateSubComponent(component);
		logger.info(component);
		return SUCCESS;

	}

	public String toAddSubComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		return SUCCESS;

	}

	public String addSubComponent() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		SubComponent component = new SubComponent();
		component.setSubCompName(compName);

		int result = subComponentService.querySubComponentSize(component);
		if(result==1){
			context.put(Constant.ERRORMSG, "Sub component name '" + component.getSubCompName() + "' already exist!");
			return ERROR;
		}
		
		subComponentService.addSubComponent(component);
		return SUCCESS;

	}

	

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	
	
}
