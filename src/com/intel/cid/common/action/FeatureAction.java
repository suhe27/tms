package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class FeatureAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6261315566847739238L;

	private static Logger logger = Logger.getLogger(FeatureAction.class);

	private int featureId;

	private String featureName;

	private String multiComps;
	
	private int projectId;
	
	private String multiProjectforms;

	public String listFeatures() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Feature> featureList = featureService.listFeatures();
		context.put("featureList", featureList);

		return SUCCESS;

	}

	public String delFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("featureId", Integer.toString(featureId));
		int num = testCaseService.queryAllTestCaseSizeByFilter(filterMap);
		if( num > 0){
			context.put(Constant.ERRORMSG, "Current feature have been matched with "+num+" test cases, please delete them first!");
			// modify by Neusoft start
			//return SUCCESS;
			return ERROR;
			// modify by Neusoft end
		}
		else {
			featureService.delFeatureById(featureId);
			return SUCCESS;
		}
	}

	public String toUpdateFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Feature feature = featureService.queryFeatureById(featureId);
		List<Component> compList = componentService.listComponentsForFeature();
		List<Component> ownerCompList = componentService.listComponentsByFeatureId(String.valueOf(featureId));
		//List<Project> projectList = projectSerivce.queryAllProjects();
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("feature", feature);
		context.put("compList", compList);
		context.put("ownerCompList", ownerCompList);
		//context.put("ownerProjectList", ownerProjectList);
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String updateFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Feature feature = new Feature();
		feature.setFeatureId(featureId);
		feature.setProjectId(projectId);
		feature.setFeatureName(featureName);

		int result = featureService.queryFeatureSize(feature);
		if(result==1){
			Project project = projectSerivce.queryProjectById(feature.getProjectId());
			context.put(Constant.ERRORMSG, "Feature name '" + feature.getFeatureName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		String[] comps = multiComps.split(",");
		featureService.updateFeature(feature,comps);
		return SUCCESS;

	}

	public String toAddFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		//List<Component> compList = componentService.listComponents();
		List<Component> compList = componentService.listComponentsForFeature();
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("compList", compList);
		context.put("projectList", projectList);
		return SUCCESS;

	}

	public String addFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Feature feature = new Feature();
		feature.setProjectId(projectId);
		feature.setFeatureName(featureName);
		
		int result = featureService.queryFeatureSize(feature);
		if(result==1){
			Project project = projectSerivce.queryProjectById(feature.getProjectId());
			context.put(Constant.ERRORMSG, "Feature name '" + feature.getFeatureName() + "' already exist under project "+project.getProjectName()+"!");
			return ERROR;
		}
		
		String[] comps = new String[]{};
		if (!Utils.isNullORWhiteSpace(multiComps)) {
			 comps = multiComps.split(",");
		}
		
		featureService.addNewFeature(feature, comps);
		return SUCCESS;

	}
	
	
	
	/*public String addFeature() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Feature feature = new Feature();
		feature.setFeatureId(featureId);

		feature.setFeatureName(featureName);
		String[] comps = multiComps.split(",");
		String[] projects = multiProjectforms.split(",");
		featureService.addNewFeature(feature, comps, projects);
		
		return SUCCESS;

	}
*/
	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getMultiComps() {
		return multiComps;
	}

	public void setMultiComps(String multiComps) {
		this.multiComps = multiComps;
	}

	public String getMultiProjectforms() {
		return multiProjectforms;
	}

	public void setMultiProjectforms(String multiProjectforms) {
		this.multiProjectforms = multiProjectforms;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
	

}
