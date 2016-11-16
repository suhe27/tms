package com.intel.cid.common.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestSuiteAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TestSuiteAction.class);

	private TestSuite testsuite;

	private int testSuiteId;

	private String testSuiteName;

	private String project;
	
	private int projectId;

	private String featureId;

	private String compId;

	private String testTypeId;

	private String osId;

	private String autoId;

	private String version;

	private int itemSize;

	private int linkSize;

	private int currPage;

	// create test execution

	private String space;

	private String mode;

	private String boardId;

	// create super testcasesuit;
	private String multiSuiteId;

	private String multiCaseId;

	private String isSelected;

	private String selectedIds = "";

	private InputStream inputStream;

	private String exportFileName;

	private String isSelectedAllSuite = "off";

	private String selectedSuiteIds = "";

	private Object TestCase;

	public String frameTestSuites() throws Exception {
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
/**
 * Add test suite function redesign, create test suite record in testsuite table. Working with function addTestSuite() together	
 * @return
 * @throws Exception
 */
	

	public String toAddTestSuite() throws Exception {
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
	
	public String addTestSuite() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		Project project = projectSerivce.queryProjectById(testsuite.getProjectId());
		int result = testSuiteService.queryBySuitNameSize(testsuite);
		if(result==1){
			context.put(Constant.ERRORMSG,
					"Test suite name '" + testsuite.getTestSuiteName() + "' already exist!");
			return ERROR;
		}
		String createDate = Utils.dateFormat(new Date(), null);

		testsuite.setTeamId(project.getTeamId());
		testsuite.setModifyDate(Utils.dateFormat(new Date(), null));
		testsuite.setUserId(user.getUserId());
		testsuite.setCreateDate(createDate);
		testSuiteService.addTestSuite(testsuite);

		return SUCCESS;
	}

	
	public String listTestSuites() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("userId", String.valueOf(user.getUserId()));
		filterMap.put("projectId", String.valueOf(projectId));
		int rowNumber = testSuiteService.queryTestSuiteSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestSuite> testSuiteList = testSuiteService.listTestSuites(
				filterMap, pageBean);
		for (TestSuite suit : testSuiteList) {
			suit.setUser(userService.queryUserById(suit.getUserId()));
			suit.setProject(projectSerivce
					.queryProjectById(suit.getProjectId()).getProjectName());
		}
		
		context.put("testSuiteList", testSuiteList);
		context.put("project", project);
		context.put("pageBean", pageBean);
		context.put("currPage", currPage);
		context.put("isSelectedAllSuite", isSelectedAllSuite);
		context.put("selectedSuiteIds", selectedSuiteIds);
		return SUCCESS;

	}

	public String delTestSuite() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (multiSuiteId == null) {
			context.put(Constant.ERRORMSG,
					"No test suite have been selected!");
			return ERROR;
		}
		String[] testcaseIds = multiSuiteId.split(",");
		logger.info("delTestCaseSuit param multiSuitId:" + multiSuiteId);
		for (String id : testcaseIds) {
			testSuiteService.delTestSuite(Integer.parseInt(id.trim()));
		}

		return SUCCESS;
	}

	/**
	 * Export test cases by search condition, get search condition from saved session value
	 * @return
	 * @throws Exception
	 */
	
	public String exportSearchedCase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		exportFileName = "testcase_" + System.currentTimeMillis();

		//Save session condition value to filter map
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("projectId", (String) context.getSession().get("projectId"));
		filterMap.put("featureId", (String) context.getSession().get("featureId"));
		filterMap.put("compId", (String) context.getSession().get("compId"));
		filterMap.put("testTypeId", (String) context.getSession().get("testTypeId"));
		filterMap.put("autoId", (String) context.getSession().get("autoId"));
		filterMap.put("caseName",(String) context.getSession().get("caseName"));

		List<TestCase> testCaseList = testCaseService.queryTestCasesByCondition(filterMap);
		
		//Call function to export search result to excel file
		inputStream = excelResolverService.exportTestCase(testCaseList, context);
		return SUCCESS;
	}

	public String exportTestCase() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		selectedIds = multiCaseId;
		// if (multiCaseId == null) {
		// context.put(Constant.ERRORMSG,
		// "you must choose at least one testcase!!!");
		// return ERROR;
		// }

		if (selectedIds == null) {
			context.put(Constant.ERRORMSG,
					"you must choose at least one testcase!!!");
			return ERROR;
		}
		exportFileName = "testcase_" + System.currentTimeMillis();

		// export current page test case;
		if (Utils.isNullORWhiteSpace(isSelected)) {
			// modify by Neusoft start
			 //String[] testcases = multiCaseId.split(",");
			 String[] testcases = selectedIds.split(",");
			// modify by Neusoft end
			//String[] testcases = selectedIds.split(",");
			List<TestCase> testcaseList = new ArrayList<TestCase>();
			for (String str : testcases) {
				TestCase newCase = testCaseService.queryTestCaseById(Integer
						.parseInt(str.trim()));
				testcaseList.add(newCase);
			}

			inputStream = excelResolverService.exportTestCase(testcaseList,
					context);
			return SUCCESS;

			// export test cases with filter conditions;
		} else if (isSelected.equals("on")) {
			Map<String, String> filterMap = new HashMap<String, String>();
			if (project == null) {
				project = "-1";
				filterMap.put("project", project);

			} else {
				filterMap.put("project", project);
			}

			if (version == null) {
				version = "-1";
				filterMap.put("ver", version);
			} else {
				filterMap.put("ver", version);
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

			List<TestCase> testCaseList = testCaseService.queryTestCasesByCondition(filterMap);
			inputStream = excelResolverService.exportTestCase(testCaseList,
					context);
			return SUCCESS;

		} else {
			context.put(Constant.ERRORMSG, "unkonwn system error!");
			return ERROR;
		}
	}

	public static String[] getFeatureAndOsNames(List<TestCase> testcaseList,
			List<Feature> featureList, List<OS> osList) throws Exception {
		Map<Integer, String> featureMap = new HashMap<Integer, String>();
		Map<Integer, String> osMap = new HashMap<Integer, String>();

		for (OS os : osList) {
			osMap.put(os.getOsId(), os.getOsName());
		}
		for (Feature feature : featureList) {
			featureMap.put(feature.getFeatureId(), feature.getFeatureName());
		}

		Set<String> osIds = new HashSet<String>();
		Set<String> featureIds = new HashSet<String>();
		for (TestCase testcase : testcaseList) {
			String osName = osMap.get(testcase.getOsId());
			String featureName = featureMap.get(testcase.getFeatureId());
			if (!Utils.isNullORWhiteSpace(osName)) {
				osIds.add(osName);
			}
			if (!Utils.isNullORWhiteSpace(featureName)) {
				featureIds.add(featureName);
			}

		}

		StringBuffer osBuffer = new StringBuffer();
		int i = 0;
		for (String os : osIds) {
			osBuffer.append(os);
			i++;
			if (i != osIds.size()) {
				osBuffer.append(",");
			}
		}
		StringBuffer featureBuffer = new StringBuffer();
		i = 0;
		for (String feature : featureIds) {
			featureBuffer.append(feature);
			i++;
			if (i != featureIds.size()) {
				featureBuffer.append(",");
			}
		}

		String[] strs = new String[2];
		strs[0] = featureBuffer.toString();
		strs[1] = osBuffer.toString();
		return strs;
	}

/**
 * Save checkbox checked status to session when add cases to suite.	
 * @throws Exception
 */
	
	public void addCaseToSuiteBySession() throws Exception {
		ActionContext context = ActionContext.getContext();
		//Check isSelected value to do session set operation
		if(isSelected.equalsIgnoreCase("true") || isSelected.equalsIgnoreCase("false")) {
			if(isSelected.equalsIgnoreCase("true") && !context.getSession().containsKey(multiCaseId)){
				context.getSession().put(multiCaseId, "CaseInSuite:"+multiCaseId);
			} else if(isSelected.equalsIgnoreCase("false")) {
				context.getSession().remove(multiCaseId);
			}
		}
	}

/**
 * Reset checkbox checked status saved in session when add cases suite.
 * @throws Exception
 */
			
	public void resetAddCaseToSuiteSession() throws Exception {

		ActionContext context = ActionContext.getContext();

		Iterable<Object> sessions = context.getSession().values();
		
		for(Object session: sessions)
		{
			String[] values = null;
			values = session.toString().split(":"); 
			if(values[0].equalsIgnoreCase("CaseInSuite"))
			{
				context.getSession().remove(values[1]);
			}
		}
	}
	
	public String toDetailTestSuite() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testsuite = testSuiteService.queryTestSuiteById(testSuiteId);

		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());

		testsuite = testSuiteService.queryTestSuiteById(testSuiteId);
		int rowNumbers = testsuite.getTotalCases();
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumbers, currPage);
		List<TestCase> testCaseList = testSuiteService.listTestCasesByPage(
				pageBean, testSuiteId);

		for (TestCase testCase : testCaseList) {
			Project project = projectSerivce.queryProjectById(testCase
					.getProjectId());
			testCase.setProject(project);
			VersionState versionState = testCaseService
					.queryVersionState(testCase.getVersionId());
			CaseState caseState = testCaseService.queryCaseState(testCase
					.getCaseStateId());
			testCase.setVersionState(versionState);
			testCase.setCaseState(caseState);
		}

		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());

		context.put("teamList", teamList);
		context.put("userList", userList);

		context.put("testCaseList", testCaseList);
		context.put("pageBean", pageBean);
		return SUCCESS;

	}

	public String preAddTestCaseToSuite() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testsuite = testSuiteService.queryTestSuiteById(testSuiteId);
		if(projectId == 0) { projectId = testsuite.getProjectId(); }
		Project pro = projectSerivce.queryProjectById(projectId);
		String projectId =  Integer.toString(pro.getProjectId());
		List<Component> componentList = null;
		List<Feature> featureList = featureService.listFeaturesByProject(pro.getProjectName());
		List<TestType> testTypeList = testTypeService.listAllTestTypes();
		List<Automation> automationList = testCaseService.listAllAutomations();
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

		String caseName = "";
		filterMap.put("caseName", caseName);
		filterMap.put("teamId", null);
		filterMap.put("userId", String.valueOf(user.getUserId()));
				
		int rowNumber = testCaseService.queryTestCaseSizeByFilter(filterMap);

		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestCase> testCaseList = testCaseService.queryTestCaseByPage(
				pageBean, filterMap);
		
		//Clear session if this is a suite switched
		int testSuiteIdTmp = 0;
		if(context.getSession().get("testSuiteIdClearMark") != null) {
			testSuiteIdTmp = (Integer) context.getSession().get("testSuiteIdClearMark");
		}
		
		if(testSuiteIdTmp != 0 && testSuiteIdTmp != testSuiteId) {
			resetAddCaseToSuiteSession();
		}
		// Get current session list of pre added test cases 
		Iterable<Object> sessions = context.getSession().values();
		List<TestCase> sessionCases = new ArrayList<TestCase>();
		
		for(Object session: sessions)
		{
			String[] values = null;
			values = session.toString().split(":"); 
			if(values[0].equalsIgnoreCase("CaseInSuite"))
			{
				TestCase tmp = new TestCase();
				tmp.setTestCaseId(Integer.parseInt(values[1]));
				sessionCases.add(tmp);
			}
		}
		
		context.getSession().put("testSuiteIdClearMark", testSuiteId);
		
		context.put("sessionCases", sessionCases);
		context.put("testCaseList", testCaseList);
		context.put("featureList", featureList);
		context.put("componentList", componentList);
		context.put("automationList", automationList);
		context.put("testTypeList", testTypeList);
		context.put("compId", compId);
		context.put("osId", osId);
		context.put("autoId", autoId);
		context.put("projectId", projectId);
		context.put("featureId", featureId);
		context.put("testTypeId", testTypeId);
		context.put("testSuiteId", testSuiteId);
		context.put("pageBean", pageBean);
		context.put("isSelected", isSelected);
		context.put("selectedIds", selectedIds);
		return SUCCESS;
	}
	
	public String addTestCaseToSuite() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		// Get current session list of pre added test cases 
		Iterable<Object> sessions = context.getSession().values();
		String sessionIds = "";
		int i = 0;
		for(Object session: sessions)
		{
			String[] values = null;
			values = session.toString().split(":"); 
			if(values[0].equalsIgnoreCase("CaseInSuite"))
			{	
				if( i== 0) {
					sessionIds = values[1];
				} else {
					sessionIds = sessionIds + "," + values[1];
				}
				i++;
			}
		}
		//Clear added test cases session
		resetAddCaseToSuiteSession();
		
		if (sessionIds == null || "".equals(sessionIds)) {
			context.put(Constant.ERRORMSG,
					"Please choose at least one testcase!!!");
			return ERROR;
		}
		testsuite = testSuiteService.queryTestSuiteById(testSuiteId);
		List<TestCase> oldTestCaseList = testSuiteService.listTestCasesBySuiteId(testSuiteId);
		List<TestCase> newTestCaseList = new ArrayList<TestCase>();
		String[] newCases = sessionIds.split(",");

		for (String ts : newCases) {
			boolean flag = true;

			for (TestCase tCase : oldTestCaseList) {
				if (Integer.parseInt(ts.trim()) == tCase.getTestCaseId()) {
					flag = false;
				}
			}

			if (flag) {
				TestCase newCase = testCaseService.queryTestCaseById(Integer.parseInt(ts.trim()));
				newTestCaseList.add(newCase);
				oldTestCaseList.add(newCase);
			}
		}

		List<Feature> featureList = featureService.listFeatures();
		List<OS> osList = oSService.listAllOSs();
		String[] strs = getFeatureAndOsNames(oldTestCaseList, featureList,
				osList);
		testsuite.setFeatures(strs[0]);
		testsuite.setOses(strs[1]);
		testsuite.setTotalCases(oldTestCaseList.size());
		testSuiteService.addTestCaseToTestSuite(testsuite, newTestCaseList);
		testSuiteService.updateTestPlanAfterTestSuiteSizeChanged(testSuiteId, true);
		String type = "add";
		testSuiteService.updateTestExecutionAfterTestSuiteSizeChanged(testSuiteId,newTestCaseList, type);
		return SUCCESS;
	}

	public String toUpdateTestSuite() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		testsuite = testSuiteService.queryTestSuiteById(testSuiteId);
		testsuite.setProject(projectSerivce.queryProjectById(
				testsuite.getProjectId()).getProjectName());
		List<User> userList = userService.queryUsersInOwnerTeam(user
				.getUserId());
		//List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		//context.put("teamList", teamList);
		context.put("userList", userList);
		context.put("testsuite", testsuite);
		context.put("projectList", projectList);

		return SUCCESS;

	}

	public String delTestCasesInSuite() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (multiCaseId != null) {

			// delete contains testcases
			String[] testcases = multiCaseId.split(",");
			testSuiteService.delBatchTestCases(testSuiteId, testcases);
			testSuiteService.updateTestPlanAfterTestSuiteSizeChanged(testSuiteId, true);
			
			String type = "del";
			//Save test case id to list type delCaseList
			List<TestCase> delCaseList = new ArrayList<TestCase>();
			for (String ts : testcases) {
				TestCase tcase = new TestCase();
				tcase.setTestCaseId(Integer.parseInt(ts.trim()));
				delCaseList.add(tcase);
			}

			testSuiteService.updateTestExecutionAfterTestSuiteSizeChanged(testSuiteId,delCaseList, type);
			context.put("testcases", testcases.length);
		}

		return SUCCESS;
	}

/**
 * Copy test suite function, support copy and merge more suite to a new one.
 * @return
 * @throws Exception
 */
	
	public String copyTestSuite() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (multiSuiteId == null || multiSuiteId.equalsIgnoreCase("")) {
			context.put(Constant.ERRORMSG,
					"Please choose one or more suite to copy or merge to a new suite!");
			return ERROR;
		}
		String[] suiteIds = multiSuiteId.split(",");
		
		//Create a empty suite for copy selected suite to.
		TestSuite suite = testSuiteService.queryTestSuiteById(Integer.parseInt(suiteIds[0].trim()));
		TestSuite newSuite = new TestSuite();

		String createDate = Utils.dateFormat(new Date(), null);
		long systemMills = System.currentTimeMillis();
		
		newSuite.setTestSuiteName(suite.getTestSuiteName() +systemMills);
		newSuite.setTeamId(suite.getTeamId());
		newSuite.setProjectId(suite.getProjectId());
		newSuite.setModifyDate(Utils.dateFormat(new Date(), null));
		newSuite.setUserId(user.getUserId());
		newSuite.setCreateDate(createDate);
		testSuiteId = testSuiteService.addTestSuite(newSuite);

		//Add test case from selected suite to new created suite
		List<TestCase> newTestCaseList = new ArrayList<TestCase>();
		List<TestCase> oldTestCaseList = new ArrayList<TestCase>();

		for (String id : suiteIds) {
			List<TestCase> caseList = testSuiteService.listTestCasesBySuiteId(Integer.parseInt(id.trim()));
			oldTestCaseList.addAll(caseList);
		}
		
		for (TestCase oCase : oldTestCaseList) {
			boolean flag = true;
			for (TestCase nCase : newTestCaseList) {
				if (nCase.getTestCaseId() == oCase.getTestCaseId()) {
					flag = false;
				}
			}
			if (flag) {
				newTestCaseList.add(oCase);
			}
		}
		
		TestSuite testsuite = testSuiteService.queryTestSuiteById(testSuiteId);
		List<Feature> featureList = featureService.listFeatures();
		List<OS> osList = oSService.listAllOSs();
		String[] strs = getFeatureAndOsNames(newTestCaseList, featureList, osList);
		testsuite.setFeatures(strs[0]);
		testsuite.setOses(strs[1]);
		testsuite.setTotalCases(newTestCaseList.size());
		testSuiteService.addTestCaseToTestSuite(testsuite, newTestCaseList);
		
		return SUCCESS;
	}


	public String updateTestSuite() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		TestSuite suite = testSuiteService.queryTestSuiteById(testsuite.getTestSuiteId());
		Project project = projectSerivce.queryProjectById(testsuite.getProjectId());
		int result = testSuiteService.queryBySuitNameSize(testsuite);
		if(result==1){
			context.put(Constant.ERRORMSG,
					"Test suite name '" + testsuite.getTestSuiteName() + "' already exist!");
			return ERROR;
		}

		suite.setTeamId(project.getTeamId());
		suite.setModifyDate(Utils.dateFormat(new Date(), null));
		suite.setProjectId(project.getProjectId());
		suite.setUserId(testsuite.getUserId());
		suite.setTestSuiteName(testsuite.getTestSuiteName());
		testSuiteService.updateTestSuite(suite);
		return SUCCESS;

	}

	public TestSuite getTestsuite() {
		return testsuite;
	}

	public void setTestsuite(TestSuite testsuite) {
		this.testsuite = testsuite;
	}

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public String getMultiSuiteId() {
		return multiSuiteId;
	}

	public void setMultiSuiteId(String multiSuiteId) {
		this.multiSuiteId = multiSuiteId;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getIsSelectedAllSuite() {
		return isSelectedAllSuite;
	}

	public void setIsSelectedAllSuite(String isSelectedAllSuite) {
		this.isSelectedAllSuite = isSelectedAllSuite;
	}

	public String getSelectedSuiteIds() {
		return selectedSuiteIds;
	}

	public void setSelectedSuiteIds(String selectedSuiteIds) {
		this.selectedSuiteIds = selectedSuiteIds;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
