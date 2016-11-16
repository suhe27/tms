package com.intel.cid.common.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.JsonUtil;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestCaseAction extends BaseAction {

	private static final long serialVersionUID = 2808894795411071801L;

	private static Logger logger = Logger.getLogger(TestCaseAction.class);

	private TestCase testCase;

	private int testCaseId;

	private String project;

	private String featureId;

	private String compId;

	private String subCompId;

	private String testTypeId;

	private String osId;

	private String autoId;

	private String versionId;

	private String caseStateId;

	private int itemSize;

	private int linkSize;

	private int currPage;

	private String multiCaseId;

	private String projectId;

	private String isSelected;

	private String selectedIds = "";

	private String projectName;

	private String caseName;

	private String teamId;
	
	private InputStream inputStream;
	
	HttpServletResponse response = null;

	private int testSuiteId;

	public String frameTestCases() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		//test

		List<TestType> testTypeList = testTypeService.listAllTestTypes();
		//List<VersionState> versionList = testCaseService.listAllVersions();
		List<Automation> automationList = testCaseService.listAllAutomations();
		List<OS> osList = oSService.listAllOSs();
		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		//List<SubComponent> subComponentList = subComponentService.queryAllSubComponents();
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());

		//Set session value for searching parameter
		setProjectId((String) context.getSession().get("projectId"));
		setFeatureId((String) context.getSession().get("featureId"));
		setCompId((String) context.getSession().get("compId"));
		setTestTypeId((String) context.getSession().get("testTypeId"));
		setAutoId((String) context.getSession().get("autoId"));
		
		if(context.getSession().get("featureId") != null) {
			@SuppressWarnings("unchecked")
			List<Feature> featureList = featureService.getFeatureList(Integer.parseInt((String) context.getSession().get("projectId")));
			context.put("featureList", featureList);
			
		} else {
			List<Feature> featureList = null;
			context.put("featureList", featureList);
		}
		
		if(context.getSession().get("compId") != null) {
			@SuppressWarnings("unchecked")
			List<Feature> componentList = featureService.getComponentList((String) context.getSession().get("featureId"));
			context.put("componentList", componentList);
		} else {
			List<Component> componentList = null;
			context.put("componentList", componentList);
		}
		
		context.put("teamList", teamList);
		context.put("testTypeList", testTypeList);
		
		//context.put("versionList", versionList);
		context.put("automationList", automationList);
		
		//context.put("subComponentList", subComponentList);
		context.put("osList", osList);
		context.put("compId", compId);
		context.put("osId", osId);
		context.put("autoId", autoId);
		context.put("ver", versionId);
		context.put("featureId", featureId);
		context.put("projectId", projectId);
		context.put("testTypeId", testTypeId);
		context.put("projectList", projectList);
		context.put("isSelected", isSelected);
		context.put("selectedIds", selectedIds);
		context.put("caseName", caseName);
		return SUCCESS;
	}
	
	public String reSet() throws Exception {
		ActionContext context = ActionContext.getContext();
				User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		context.getSession().remove("testTypeId");
		context.getSession().remove("autoId");
		context.getSession().remove("projectId");
		context.getSession().remove("featureId");
		context.getSession().remove("compId");
		context.getSession().remove("caseName");

		List<TestType> testTypeList = testTypeService.listAllTestTypes();
		List<Automation> automationList = testCaseService.listAllAutomations();	
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		
		context.put("testTypeList", testTypeList);
		context.put("automationList", automationList);
		context.put("projectList", projectList);
		context.put("caseName", null);
		
		return SUCCESS;
	}

	public String caseAnalyze() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String pieData = null;
		String tmpData = null;
		int count = 0;
		
		List<TestCase> caseInProject = testCaseService.listTestCaseNumInProjects(user.getUserId());
		for(TestCase tmp : caseInProject) {
			if(count == 0) {
				pieData = "['"+tmp.getProjectName()+"',"+tmp.getNum()+"]";
			} else {
				pieData = pieData + ","+"['"+tmp.getProjectName()+"',"+tmp.getNum()+"]";
			}
			count++;
		}
		
		List<Project> projects = projectSerivce.queryProjectByUserId(user.getUserId());
		for(Project project : projects) {
			List<TestCase> featureList = testCaseService.listTestCaseNumInProjectByFeature(project.getProjectId());
			count = 0;
			tmpData = null;
			for(TestCase tmp : featureList) {
				if(count == 0) {
					tmpData = "['"+tmp.getFeatureName()+"',"+tmp.getNum()+"]";
				} else {
					tmpData = tmpData + ","+"['"+tmp.getFeatureName()+"',"+tmp.getNum()+"]";
				}
				count++;
			}
			project.setFeatureData(tmpData);
			
			List<TestCase> autoList = testCaseService.listTestCaseNumInProjectByAuto(project.getProjectId());
			count = 0;
			tmpData = null;
			for(TestCase tmp : autoList) {
				if(count == 0) {
					tmpData = "['"+tmp.getAutoName()+"',"+tmp.getNum()+"]";
				} else {
					tmpData = tmpData + ","+"['"+tmp.getAutoName()+"',"+tmp.getNum()+"]";
				}
				count++;
			}
			project.setAutoData(tmpData);
		}
		
		context.put("pieData", pieData);
		context.put("projects", projects);
		
		return SUCCESS;
	}
	
	public String listTestCases() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		List<Component> componentList = null;
		Map<String, String> filterMap = new HashMap<String, String>();

		//Get http get parameter value and save to transfer to SQL, save value in session
		if (projectId == null || projectId == "-1") {
			projectId = "-1";
			filterMap.put("projectId", projectId);
		}  else {
			filterMap.put("projectId", projectId);
			context.getSession().put("projectId", projectId);
			Project project = projectSerivce.queryProjectById(Integer.parseInt(projectId));
			context.getSession().put("projectName", project.getProjectName());
		}
		if (versionId == null) {
			versionId = "-1";
			filterMap.put("ver", versionId);
		} else {
			filterMap.put("ver", versionId);
		}

		if (featureId == null) {
			featureId = "-1";
			filterMap.put("featureId", featureId);
		} else {
			filterMap.put("featureId", featureId);
			context.getSession().put("featureId", featureId);
			Feature feature = featureService.queryFeatureById(Integer.parseInt(featureId));
			context.getSession().put("featureName", feature.getFeatureName());
		}
		if (compId == null) {
			compId = "-1";
			filterMap.put("compId", compId);
		} else {
			filterMap.put("compId", compId);
			context.getSession().put("compId", compId);
			Component component = componentService.queryComponentById(Integer.parseInt(compId));
			context.getSession().put("compName", component.getCompName());
		}
		if (testTypeId == null) {
			testTypeId = "-1";
			filterMap.put("testTypeId", testTypeId);
		} else {
			filterMap.put("testTypeId", testTypeId);
			context.getSession().put("testTypeId", testTypeId);
			TestType testType = testTypeService.queryTestTypeById(Integer.parseInt(testTypeId));
			context.getSession().put("testTypeName", testType.getTypeName());
		}
		if (osId == null) {
			osId = "-1";
			filterMap.put("osId", osId);
		} else {
			filterMap.put("osId", osId);
		}
		if (autoId == null) {
			autoId = "-1";
			filterMap.put("autoId", autoId);
		} else {
			filterMap.put("autoId", autoId);
			context.getSession().put("autoId", autoId);
			Automation auto = testCaseService.queryAutomation(Integer.parseInt(autoId));
			context.getSession().put("autoName", auto.getAutoName());
		}

		if (caseName == null) {
			caseName = "";
		} else {
			context.getSession().put("caseName", caseName);
		}
		
		filterMap.put("caseName", caseName);
		filterMap.put("teamId", null);
		filterMap.put("userId", String.valueOf(user.getUserId()));
				
		int rowNumber = testCaseService.queryTestCaseSizeByFilter(filterMap);

		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestCase> testCaseList = testCaseService.queryTestCaseByPage(
				pageBean, filterMap);
		context.put("testCaseList", testCaseList);
		context.put("componentList", componentList);
		context.put("compId", compId);
		context.put("osId", osId);
		context.put("autoId", autoId);
		context.put("projectId", projectId);
		context.put("ver", versionId);
		context.put("featureId", featureId);
		context.put("testTypeId", testTypeId);
		context.put("pageBean", pageBean);
		context.put("isSelected", isSelected);
		context.put("selectedIds", selectedIds);
		return SUCCESS;
	}

	public String selecctTestCases() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (Utils.isNullORWhiteSpace(project)) {
			context.put(Constant.ERRORMSG,
					"testsuite project attribute can not null!");
		}
		List<Feature> featureList = null;
		if (project != null) {
			featureList = featureService.listFeaturesByProject(projectName);
		} else {
			featureList = featureService.listFeaturesByProject(projectName);

		}
		List<TestType> testTypeList = testTypeService.listAllTestTypes();
		List<Automation> automationList = testCaseService.listAllAutomations();
		List<OS> osList = oSService.listAllOSs();
		List<Component> componentList = componentService.listComponents();
		Map<String, String> filterMap = new HashMap<String, String>();
		if (caseName == null) {
			caseName = "";
		}
		filterMap.put("caseName", caseName);
		if (project == null) {
			project = "-1";
			filterMap.put("projectId", String.valueOf(project));

		} else {
			filterMap.put("projectId", String.valueOf(project));
		}

		/*
		 * if (proId==0) { filterMap.put("projectId", "-1");
		 * 
		 * } else { filterMap.put("projectId", String.valueOf(proId)); }
		 */

		if (versionId == null) {
			versionId = "-1";
			filterMap.put("ver", versionId);
		} else {
			filterMap.put("ver", versionId);
		}

		if (featureId == null) {
			featureId = "-1";
			filterMap.put("featureId", featureId);
		} else {
			filterMap.put("featureId", featureId);
		}
		if (compId == null) {
			compId = "-1";
			filterMap.put("compId", compId);
		} else {
			filterMap.put("compId", compId);
		}
		if (testTypeId == null) {
			testTypeId = "-1";
			filterMap.put("testTypeId", testTypeId);
		} else {
			filterMap.put("testTypeId", testTypeId);
		}
		if (osId == null) {
			osId = "-1";
			filterMap.put("osId", osId);
		} else {
			filterMap.put("osId", osId);
		}
		if (autoId == null) {
			autoId = "-1";
			filterMap.put("autoId", autoId);
		} else {
			filterMap.put("autoId", autoId);
		}
		filterMap.put("teamId", null);
		filterMap.put("userId", String.valueOf(user.getUserId()));
		int rowNumber = testCaseService.queryTestCaseSizeByFilter(filterMap);

		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestCase> testCaseList = testCaseService.queryTestCaseByPage(
				pageBean, filterMap);
		for (TestCase ts : testCaseList) {
			Project project = projectSerivce
					.queryProjectById(ts.getProjectId());
			Feature feature = featureService
					.queryFeatureById(ts.getFeatureId());
			Component comp = componentService
					.queryComponentById(ts.getCompId());
			TestType testType = testTypeService.queryTestTypeById(ts
					.getTestTypeId());
			OS os = oSService.queryOSById(ts.getOsId());
			Automation automation = testCaseService.queryAutomation(ts
					.getAutoId());
			CaseState caseState = testCaseService.queryCaseState(ts
					.getCaseStateId());
			VersionState version = testCaseService.queryVersionState(ts
					.getVersionId());
			SubComponent subComponent = subComponentService
					.querySubComponentById(ts.getSubCompId());
			ts.setFeature(feature);
			ts.setComponent(comp);
			ts.setTestType(testType);
			ts.setOs(os);
			ts.setAutomation(automation);
			ts.setCaseState(caseState);
			ts.setVersionState(version);
			ts.setSubComponent(subComponent);
			ts.setProject(project);
		}

		context.put("testCaseList", testCaseList);
		context.put("osList", osList);
		context.put("automationList", automationList);
		context.put("testTypeList", testTypeList);
		context.put("componentList", componentList);
		context.put("featureList", featureList);
		context.put("compId", compId);
		context.put("osId", osId);
		context.put("autoId", autoId);
		context.put("ver", versionId);
		context.put("featureId", featureId);
		context.put("testTypeId", testTypeId);
		context.put("project", project);
		context.put("pageBean", pageBean);
		context.put("testSuiteId", testSuiteId);
		context.put("selectedIds", selectedIds);
		context.put("proId", project);
		context.put("projectName", projectName);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void getFeatureList() {
		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List list = null;
		if (null != project && project.equals("-1")) {
			list = featureService.getFeatureList(Integer.parseInt(project));
		} else {
			list = featureService.getFeatureList(Integer.parseInt(project));
		}
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void getFeatureListAsID() {
		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List<?> list = null;
		try {
			list = featureService.listFeatures(Integer.parseInt(projectId));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void getPlatformList() throws Exception {
		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List list = projectSerivce.queryPorjectByTeamId(teamId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void getComponentList() {
		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List list = featureService.getComponentList(featureId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void getSubComponentList() throws Exception {

		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List list = componentService.querySubComponentList(subCompId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String delBatchTestCases() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		selectedIds = multiCaseId;
		System.out.println(selectedIds);
		// modify by Neusoft start
		//if (multiCaseId == null) {
		if (selectedIds == null) {
		// modify by Neusoft end
			context.put(Constant.ERRORMSG,
					"No test case have been selected");
			return ERROR;
		}

		// delete current page test cases;
		if (Utils.isNullORWhiteSpace(isSelected)) {
			// modify by Neusoft start
			//String[] testcaseIds = multiCaseId.split(",");
			String[] testcaseIds = selectedIds.split(",");
			// modify by Neusoft end
			int result = testCaseService.delBatchTestCases(testcaseIds);
			logger.info("delBatchTestCases total records:" + result);
			return SUCCESS;

			// delete test cases with filter conditions/cross pages;
		} else if (isSelected.equals("on")) {
			Map<String, String> filterMap = new HashMap<String, String>();
			if (project == null) {
				project = "-1";
				filterMap.put("project", project);

			} else {
				filterMap.put("project", project);
			}

			if (versionId == null) {
				versionId = "-1";
				filterMap.put("ver", versionId);
			} else {
				filterMap.put("ver", versionId);
			}

			if (featureId == null) {
				featureId = "-1";
				filterMap.put("featureId", featureId);
			} else {
				filterMap.put("featureId", featureId);
			}
			if (compId == null) {
				compId = "-1";
				filterMap.put("compId", compId);
			} else {
				filterMap.put("compId", compId);
			}
			if (testTypeId == null) {
				testTypeId = "-1";
				filterMap.put("testTypeId", testTypeId);
			} else {
				filterMap.put("testTypeId", testTypeId);
			}
			if (osId == null) {
				osId = "-1";
				filterMap.put("osId", osId);
			} else {
				filterMap.put("osId", osId);
			}
			if (autoId == null) {
				autoId = "-1";
				filterMap.put("autoId", autoId);
			} else {
				filterMap.put("autoId", autoId);
			}
			filterMap.put("userId", String.valueOf(user.getUserId()));

			List<TestCase> testCaseList = testCaseService
					.queryTestCasesByFilter(filterMap);

			String[] testcaseIds = new String[testCaseList.size()];
			for (int i = 0; i < testCaseList.size(); i++) {
				int caseId = testCaseList.get(i).getTestCaseId();
				testcaseIds[i] = String.valueOf(caseId);
			}
			int result = testCaseService.delBatchTestCases(testcaseIds);
			logger.info("delBatchTestCases total records:" + result);
			return SUCCESS;

		} else {
			context.put(Constant.ERRORMSG, "unkonwn system error!");
			return ERROR;
		}

	}

	public String toUpdateTestCase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		TestCase testcase = testCaseService.queryTestCaseById(testCaseId);
		// testcase.setProjectName(projectSerivce.queryProjectById(testCase.getProjectId()).getProjectName());
		List<TestType> testTypeList = testTypeService.listTestTypeByProjectId(testcase.getProjectId());
		preSetUpTestCase(context);
		context.put("testcase", testcase);
		context.put("testTypeList", testTypeList);
		return SUCCESS;

	}

	public String updateTestCase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (1 == testCaseService.queryByNameCaseIdSize(testCase)) {

			context.put(Constant.ERRORMSG,
					"Test Case Name '" + testCase.getTestCaseName() +"' already exist!");
			return ERROR;
		}
		/**
		if (2 ==  testCaseService.queryByNameCaseIdSize(testCase)) {
			context.put(Constant.ERRORMSG,
					"Test Case Id '" + testCase.getTestCasealiasId() + "' already exist!");
			return ERROR;
		}
		**/
		testCase.setModifyDate(Utils.dateFormat(new Date(), null));
		Project project = projectSerivce.queryProjectById(testCase.getProjectId());
		testCase.setTeamId(project.getTeamId());
		testCaseService.updateTestCase(testCase);
		return SUCCESS;

	}

	public String toAddTestCase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		preSetUpTestCase(context);

		return SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public void preSetUpTestCase(ActionContext context) throws Exception {

		User user = (User) context.getSession().get("user");

		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		/*
		 * List<Platform> platformList =
		 * platformService.listPlatformsInTeams(user .getUserId());
		 */
		TestCase testCase = testCaseService.queryTestCaseById(testCaseId);
		List<Automation> automationList = testCaseService.listAllAutomations();
		List<OS> osList = oSService.listAllOSs();
		//List<Feature> featureList = featureService.listFeatures();
		List<Feature> featureList = featureService.listFeatures(testCase.getProjectId());
		List<Component> componentList = featureService.getComponentList(Integer.toString(testCase.getFeatureId()));
		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		//List<TestType> testTypeList = testTypeService.listAllTestTypes();
		List<VersionState> versionStateList = testCaseService.listAllVersions();
		List<CaseState> caseStateList = testCaseService.listAllCaseStates();
		List<SubComponent> subComponentList = componentService.querySubComponentList(Integer.toString(testCase.getCompId()));
		context.put("projectList", projectList);
		context.put("teamList", teamList);
		context.put("osList", osList);
		context.put("featureList", featureList);
		context.put("componentList", componentList);
		context.put("subComponentList", subComponentList);
		context.put("userList", userList);
		//context.put("testTypeList", testTypeList);
		context.put("automationList", automationList);
		context.put("versionStateList", versionStateList);
		context.put("caseStateList", caseStateList);
	}

	public String addTestCase() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (1 == testCaseService.queryByNameCaseIdSize(testCase)) {

			context.put(Constant.ERRORMSG,
					"Test Case Name '" + testCase.getTestCaseName() + "' already exist!");
			return ERROR;
		}
		/**
		if (2 ==  testCaseService.queryByNameCaseIdSize(testCase)) {
			context.put(Constant.ERRORMSG,
					"Test Case Id '" + testCase.getTestCasealiasId() + "' already exist!");
			return ERROR;
		}
		**/
		
		Date date = new Date();
		testCase.setCreateDate(Utils.dateFormat(date, null));
		testCase.setModifyDate(Utils.dateFormat(date, null));
		testCaseService.addTestCase(testCase);
		return SUCCESS;
	}

	public String downloadCaseTemplate() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String desDir = Utils.getProjectPath(context) + "/Template/Import_Test_Cases_Template.xlsx";
		FileInputStream fis = new FileInputStream(desDir);
		inputStream = fis;
		return SUCCESS;
	}
	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getSubCompId() {
		return subCompId;
	}

	public void setSubCompId(String subCompId) {
		this.subCompId = subCompId;
	}

	public String getTestTypeId() {
		return testTypeId;
	}

	public void setTestTypeId(String testTypeId) {
		this.testTypeId = testTypeId;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	public int getLinkSize() {
		return linkSize;
	}

	public void setLinkSize(int linkSize) {
		this.linkSize = linkSize;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public String getMultiCaseId() {
		return multiCaseId;
	}

	public void setMultiCaseId(String multiCaseId) {
		this.multiCaseId = multiCaseId;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getCaseStateId() {
		return caseStateId;
	}

	public void setCaseStateId(String caseStateId) {
		this.caseStateId = caseStateId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
