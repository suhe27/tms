package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.opensymphony.xwork2.ActionContext;

public class OSAction extends BaseAction {

	private static final long serialVersionUID = -8098656951379570018L;

	private static Logger logger = Logger.getLogger(OSAction.class);

	private int osId;

	private String osName;

	public String listOSs() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<OS> osList = oSService.listAllOSs();

		context.put("osList", osList);

		return SUCCESS;

	}

	public String delOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("osId", Integer.toString(osId));
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current OS have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			oSService.delOSById(osId);
			return SUCCESS;
		}
		
	}

	public String toUpdateOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		OS os = oSService.queryOSById(osId);
		context.put("os", os);

		return SUCCESS;

	}

	public String updateOS() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		OS os = new OS();
		os.setOsId(osId);
		os.setOsName(osName);

		int result = oSService.queryOSSize(os);
		if(result==1){
			context.put(Constant.ERRORMSG, "OS name '" + os.getOsName() + "' already exist!");
			return ERROR;
		}
		
		oSService.updateOS(os);
		logger.info(os);
		return SUCCESS;

	}

	public String toAddOS() throws Exception {

		return SUCCESS;

	}

	public String addOS() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		OS os = new OS();
		os.setOsId(osId);
		os.setOsName(osName);

		int result = oSService.queryOSSize(os);
		if(result==1){
			context.put(Constant.ERRORMSG, "OS name '" + os.getOsName() + "' already exist!");
			return ERROR;
		}
		
		oSService.addOS(os);

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

}
