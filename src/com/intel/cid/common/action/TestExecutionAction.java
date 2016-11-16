package com.intel.cid.common.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intel.cid.common.net.Client;

import org.apache.struts2.ServletActionContext;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Build;
import com.intel.cid.common.bean.ExecutionOS;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.ResultTrack;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.service.SubTestPlanService;
import com.intel.cid.utils.MailGenerator;
import com.intel.cid.utils.MailHelper;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestExecutionAction extends BaseAction {

	private static final long serialVersionUID = -1397740216601400127L;

	private int itemSize;

	private int linkSize;

	private int currPage;
	private String exportFileName;
	private int projectId;
	private int testPlanId;
	private String releaseCycle;
	private String fileType;
	private int osId;
	private String multiTestExecution;
	private int platformId;
	private int executionId;
	private int subExecutionId;
	private String executionName;
	private int buildId; 
	private int phaseId;
	private int resultId;
	private TestExecution testexecution;
	private SubExecution subexecution;
	private TestResult testResult;
	private InputStream inputStream;
	private String ip;
	private String port;
	private List<TestResult> testResultLists;
	private List<TestUnit> testUnitList;

	HttpServletResponse response = null;

	public String listTexeExecutioniframe() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<Project> projectList = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("teamList", teamList);
		context.put("projectList", projectList);
		return SUCCESS;
	
	}
	public String listTestExecutionControl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("projectId", projectId);
		request.setAttribute("releaseCycle", releaseCycle);
		request.setAttribute("platformId", platformId);
		request.setAttribute("osId", osId);

		return SUCCESS;
	}
	public String toAddTestExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<ExecutionOS> osList=executionOSService.listAllExecutionOSs();
		// modify by Neusoft start
		//List<Build> buildList=buildService.listAllBuilds();
		// modify by Neusoft start
		List<Project> listProject = projectSerivce.queryProjectByUserId(user.getUserId());
		context.put("listProject", listProject);
		context.put("osList", osList);
		// modify by Neusoft start
		//context.put("buildList", buildList);
		// modify by Neusoft start
		
		return SUCCESS;
	}
	
/**
 * Action: Add sub execution action, Form generate action. is working with addSubExecution action together.	
 * @return
 * @throws Exception
 */
	public String toAddSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		SubExecution subexecution = new SubExecution();
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		Project project = projectSerivce.queryProjectById(testExecution.getProjectId());
		subexecution.setTestPlanName(testPlanService.queryTestPlan(testExecution.getTestPlanId()).getPlanName());
		List<SubTestPlan> subTestPlanList=subTestPlanService.listSubTestPlansInOnePlan(testExecution.getTestPlanId());
		List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		List<ExecutionOS> osList=executionOSService.listAllExecutionOSByProject(testExecution.getProjectId());
		List<Platform> platform = platformService.listPlatforms(project.getProjectId());
		subexecution.setId(executionId);
		context.put("osList", osList);
		context.put("platformList", platform);
		context.put("subexecution", subexecution);
		context.put("subTestPlanList",subTestPlanList);
		context.put("userList", userList);
		context.put("testExecution", testExecution);
		return SUCCESS;
	}
	
	public String copyTestExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null || multiTestExecution.equalsIgnoreCase("undefined")) {
			context.put(Constant.ERRORMSG,
					"No test execution have been selected!");
			return ERROR;
		}
		String createDate = Utils.dateFormat(new Date(), null);
		long systemMills = System.currentTimeMillis();
		String[] multiTestExecutions= multiTestExecution.split(",");
		
		TestExecution oldTestExecution = testExecutionService.queryTestExecutionById(Integer.parseInt(multiTestExecutions[0].trim()));
		TestExecution newTestExecution = testExecutionService.queryTestExecutionById(Integer.parseInt(multiTestExecutions[0].trim()));
		
		newTestExecution.setCreateDate(createDate);
		newTestExecution.setModifyDate(createDate);
		newTestExecution.setPass(0);
		newTestExecution.setFail(0);
		newTestExecution.setNotRun(oldTestExecution.getTotalCases());
		newTestExecution.setBlock(0);
		newTestExecution.setExecuteRate(null);
		newTestExecution.setPassrate(null);
		newTestExecution.setExecutionName(oldTestExecution.getExecutionName() + " " + systemMills);
		newTestExecution.setReleaseCycle(oldTestExecution.getReleaseCycle() + " " + systemMills);
		
		executionId = testExecutionService.addTestexecution(newTestExecution);
		newTestExecution.setExecutionId(executionId);
		newTestExecution.setId(executionId);
		
		testExecutionService.copyTestExecution(oldTestExecution, newTestExecution);
		
		return SUCCESS;
	}
	
	
	public String addTestExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		int result = testExecutionService.queryByExeNameCycleSize(testexecution);
		if(result==1){
			context.put(Constant.ERRORMSG,
					"Test Execution '" + testexecution.getExecutionName()
							+ "' already exist!");
			return ERROR;
		}
		/**
		if(result==2){
				context.put(Constant.ERRORMSG,
						"Release Cycle '" + testexecution.getReleaseCycle()
								+ "' already exist!");
				return ERROR;
		}
		**/
		
		String createDate = Utils.dateFormat(new Date(), null);
		//	testPlan.setTeamId(team.getTeamId());
		testexecution.setOwnner(user.getUserId());
		testexecution.setCreateDate(createDate);
		testExecutionService.addTestexecution(testexecution);
		return SUCCESS;
	}
/**
 * Action: Add sub execution, database access operation. is working with toAddSubExecution action together.
 * @return
 * @throws Exception
 */
	
	public String addSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		//Check duplicate sub execution name, not allowed.
		subexecution.setExecutionId(subexecution.getId());
		int result = testExecutionService.querySubExecutionNameSize(subexecution);
		if(result>=1){
			context.put(Constant.ERRORMSG,
					"Sub Execution '" + subexecution.getSubExecutionName()
							+ "' already exist!");
			return ERROR;
		}
		
		//Check reused sub test plan, don't allow duplicate sub test plan use in same execution.
		subexecution.setExecutionId(subexecution.getId());
		int result1 = testExecutionService.verifyDuplicateSubExecution(subexecution);
		if(result1>=1){
			context.put(Constant.ERRORMSG,
					"Selected sub test plan already been added,"
					+ " one sub test plan can only be used once in same execution under same platform and OS.");
			return ERROR;
		}
		
		TestExecution testexecution= testExecutionService.queryTestExecutionById(subexecution.getId());
		SubTestPlan  subTestPlan = null;
		subTestPlan =subTestPlanService.querySubTestPlanById(subexecution.getSubPlanId());
		String currentDate = Utils.dateFormat(new Date(), null);	

		//Create sub execution
		subexecution.setNotRun(subTestPlan.getTotalCases());
		subexecution.setTotalCases(subTestPlan.getTotalCases());
		//subexecution.setPlatformId(testexecution.getPlatformId());
		subexecution.setEnvId(testexecution.getEnvId());
		subexecution.setExecutionId(testexecution.getId());
		subexecution.setPhaseId(testexecution.getPhaseId());
		//subexecution.setOsId(testexecution.getOsId());
		subexecution.setProjectId(testexecution.getProjectId());
		subexecution.setReleaseCycle(testexecution.getReleaseCycle());
		subexecution.setCreateDate(currentDate);
		subexecution.setModifyDate(currentDate);		
		int result_subExecutionId = testExecutionService.addSubExecution(subexecution);
		
		//Update test execution for Not run/Total cases column.
		testexecution.setTotalCases(testexecution.getTotalCases() + subTestPlan.getTotalCases());
		testexecution.setNotRun(testexecution.getNotRun() + subTestPlan.getTotalCases());
		//testexecution.setPassrate( (double)(testexecution.getPass() / testexecution.getTotalCases()) );
		//testexecution.setExecuteRate( (double)((testexecution.getTotalCases() - testexecution.getNotRun()) / testexecution.getTotalCases()));
		testExecutionService.updateTestexecution(testexecution);
		
		//Insert added test case record into testresult table
		List<TestCase> testcaselists=subTestPlanService.listTestCasesInSubTestPlan1(subexecution.getSubPlanId());				
		for (TestCase tcase : testcaselists) {
			tcase.setTestPlanId(testexecution.getTestPlanId());
			tcase.setModifyDate(currentDate);
			tcase.setSubExecutionId(result_subExecutionId);
			tcase.setExecutionId(subexecution.getId());
			tcase.setPlatformId(subexecution.getPlatformId());
			tcase.setOsId(subexecution.getOsId());
		}
		testResultService.addBatchTestResult(testcaselists);
		return SUCCESS;
	}
	
	
	public String deleTestExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test execution have been selected!");
			return ERROR;
		}

		String[] multiTestExecutions= multiTestExecution.split(",");
		testExecutionService.deleTestexecution(multiTestExecutions);

		return SUCCESS;
	}
	
	public String delSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No sub execution have been selected!");
			return ERROR;
		}
		
		String[] multiTestExecutions= multiTestExecution.split(",");
		testExecutionService.delSubExecution(multiTestExecutions);

		return SUCCESS;
	}
	
	public String toUpdateExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		testExecution.setOsName(executionOSService.queryOSById(testExecution.getOsId()).getOsName());
	    testExecution.setPlatFormName(platformService.queryPlatformById(testExecution.getPlatformId()).getPlatformName());
		testExecution.setTestPlanName(testPlanService.queryTestPlan(testExecution.getTestPlanId()).getPlanName());
		//testExecution.setTestenvName(testEnvService.querytestEnvById(testExecution.getTesId()).getEnvName());
		testExecution.setProjectName(projectSerivce.queryProjectById(testExecution.getProjectId()).getProjectName());
		 
		List<Build> buildList=buildService.queryBuildByProjectId(testExecution.getProjectId());
		List<Phase> phaseList=executionPhaseService.queryPhaseByProjectId(testExecution.getProjectId());
		TestPlan testPlan = testPlanService.queryTestPlan(testExecution.getTestPlanId());
			
		context.put("phaseList", phaseList);
		context.put("buildList", buildList);
		context.put("testExecution", testExecution);
		context.put("testPlan", testPlan);
		return SUCCESS;
	}
	
	public String toUpdateSingleTestResult() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		TestResult testResult = testResultService.queryTestResult(resultId);
		List<ResultType> resulttype = testResultService.listResultTypes();
		context.put("resulttypelist", resulttype); 
		context.put("testResult", testResult);
		return SUCCESS;
	}
	
	public String listPerformanceResult() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		List<TestResult> testResults = testResultService.listTestResultsByExecutionId(executionId); 
		List<TestResult> perfResults = testResultService.listPerformanceResultByExecutionId(executionId);
		 
		Map<String, String> headLists = new HashMap<String, String>();
		List<List<String>> table = new ArrayList<List<String>>();
		for(TestResult result : perfResults){
			headLists.put(result.getAttributeName(), result.getAttributeName());
		}
		//Save table head
		List<String> tableHead = new ArrayList<String>();
		tableHead.add("Summary");
		tableHead.add("ExecutionOS");
		tableHead.add("Platform");
		tableHead.add("Target");
		for (Map.Entry<String, String> head : headLists.entrySet())
		{
    		tableHead.add(head.getKey());
		}
		int size = tableHead.size();
		
		//Iterate perfResults to analyze data and save to table as new row
		for(TestResult testResult : testResults){
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				for(PerformanceResult item : items) {	
					List<String> tbody = new ArrayList<String>();
					String attr = item.getPerformance();
					String[] attrs = attr.split(";");
					
					for(int j=0; j < size ; j++) {
						boolean status = false;
						if(tableHead.get(j).trim().equalsIgnoreCase("Summary")) {
							String summary = "Case: "+testResult.getTestCaseName()+" | Result: "+testResult.getResultTypeName() + " | BugId: " + testResult.getBugId()
									+ " | Log: "+ testResult.getLog() + " | Comments: "+testResult.getComments();
							tbody.add(summary);
							status = true;
						}
						if(tableHead.get(j).trim().equalsIgnoreCase("ExecutionOS")) {
							tbody.add(testResult.getOsName());
							status = true;
						}
						if(tableHead.get(j).trim().equalsIgnoreCase("Platform")) {
							tbody.add(testResult.getPlatformName());
							status = true;
						}
						if(tableHead.get(j).trim().equalsIgnoreCase("Target")) {
							tbody.add(testResult.getTargetName());
							status = true;
						}
						for(int i=0; i < attrs.length; i++){
							String[] element = attrs[i].split(":");
							if(tableHead.get(j).trim().equalsIgnoreCase(element[0].trim())) {
								tbody.add(element[1].trim());
								status = true;
							}
						}
						if(!status) {
								tbody.add(" ");
						}
					}
					table.add(tbody);
				}
			}
		}		
				
		context.put("lists", table);
		context.put("head",tableHead);
		context.put("testExecution", testExecution);
		return SUCCESS;
	}

	public String listPerformanceResultBySubExecutionId() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		SubExecution subExecution= testExecutionService.querySubExecutionById(subExecutionId);
		List<TestResult> testResults = testResultService.listTestResultsBySubExecutionId(subExecutionId); 
		List<TestResult> perfResults = testResultService.listPerformanceResultBySubExecutionId(subExecutionId);
		 
		Map<String, String> headLists = new HashMap<String, String>();
		List<List<String>> table = new ArrayList<List<String>>();
		for(TestResult result : perfResults){
			headLists.put(result.getAttributeName(), result.getAttributeName());
		}
		//Save table head
		List<String> tableHead = new ArrayList<String>();
		tableHead.add("Summary");
		tableHead.add("Target");
		for (Map.Entry<String, String> head : headLists.entrySet())
		{
    		tableHead.add(head.getKey());
		}
		int size = tableHead.size();
		
		//Iterate perfResults to analyze data and save to table as new row
		for(TestResult testResult : testResults){
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				for(PerformanceResult item : items) {	
					List<String> tbody = new ArrayList<String>();
					String attr = item.getPerformance();
					String[] attrs = attr.split(";");
					
					for(int j=0; j < size ; j++) {
						boolean status = false;
						if(tableHead.get(j).trim().equalsIgnoreCase("Summary")) {
							String summary = "Case: "+testResult.getTestCaseName()+" | Result: "+testResult.getResultTypeName() + " | BugId: " + testResult.getBugId()
									+ " | Log: "+ testResult.getLog() + " | Comments: "+testResult.getComments();
							tbody.add(summary);
							status = true;
						}
						if(tableHead.get(j).trim().equalsIgnoreCase("Target")) {
							tbody.add(testResult.getTargetName());
							status = true;
						}
						for(int i=0; i < attrs.length; i++){
							String[] element = attrs[i].split(":");
							if(tableHead.get(j).trim().equalsIgnoreCase(element[0].trim())) {
								tbody.add(element[1].trim());
								status = true;
							}
						}
						if(!status) {
								tbody.add(" ");
						}
					}
					table.add(tbody);
				}
			}
		}		
				
		context.put("lists", table);
		context.put("head",tableHead);
		context.put("subExecution", subExecution);
		return SUCCESS;
	}
	
	public String testCaseExecutionHistory() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		TestResult testResult = testResultService.queryTestResult(resultId);
		List<ResultTrack> resultTracks = testResultService.queryResultTrachkByResultId(resultId);
		for(ResultTrack track: resultTracks) {
			List< PerformanceResult > perfResults = testResultService.queryPerfResultByResultTrackId(track.getResultTrackId());
			String tmp = "";
			for(PerformanceResult perfResult: perfResults) {
				tmp = tmp + perfResult.getPerformance() + "</br>";
			}
			track.setPerformance(tmp);
		}
		context.put("ResultTrack", resultTracks);
		context.put("TestResult", testResult);
		return SUCCESS;
	}
	
	public String updateSingleTestResult() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String currentDate = Utils.dateFormat(new Date(), null);
		
		//Save result in table testresult for final display
		TestResult testresult = testResultService.queryTestResult(testResult.getResultId());
		testresult.setResultTypeId(testResult.getResultTypeId());
		testresult.setLog(testResult.getLog());
		testresult.setBugId(testResult.getBugId());
		testresult.setComments(testResult.getComments());
		testresult.setBugName(testResult.getBugName());
		testresult.setModifyDate(currentDate);
		testResultService.updateTestRusultInSubExecution(testresult);
		
		//Save result in table resulttrack to save result change history
		ResultTrack resultTrack = new ResultTrack();
		resultTrack.setTestResultId(testResult.getResultId());
		resultTrack.setResultTypeId(testResult.getResultTypeId());
		resultTrack.setCreateDate(currentDate);
		resultTrack.setModifyDate(currentDate);
		List<ResultTrack> resultTrackList = new ArrayList<ResultTrack>();
		resultTrackList.add(resultTrack);
		testResultService.addBatchResultTrack(resultTrackList);
		
		//Refresh calculate result in subexecution and execution
		TestResult result = testResultService.queryTestResult(testResult.getResultId());
		SubExecution subExecution= testExecutionService.querySubExecutionById(result.getSubExecutionId());
		testExecutionService.refreshSubExecution(subExecution);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(result.getExecutionId());
		testExecutionService.refreshTestExecution(testExecution);

		return SUCCESS;
	}
	
	public String topSelectedResult() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test result have been selected!");
			return ERROR;
		}
		String[] multiTestExecutions= multiTestExecution.split(",");
		int resultTrackId = Integer.parseInt(multiTestExecutions[0]);

		String currentDate = Utils.dateFormat(new Date(), null);
		
		//Save result in table resulttrack to save result change history
		ResultTrack resultTrack = testResultService.queryResultTrack(resultTrackId);
		resultTrack.setModifyDate(currentDate);
		testResultService.updateResultTrackById(resultTrack);
		
		//Save result in table testresult for final display
		TestResult testResult = testResultService.queryTestResult(resultTrack.getTestResultId());
		testResult.setResultTrackId(resultTrackId);
		testResult.setResultTypeId(resultTrack.getResultTypeId());
		testResult.setModifyDate(currentDate);
		testResultService.updateTestRusultInSubExecution(testResult);

		return SUCCESS;
	}	
	
/**
 * Update sub execution form generator action, is working with updateSubExecution action together.	
 * @return
 * @throws Exception
 */
	
	public String toUpdateSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
	
		 SubExecution subExecution= testExecutionService.querySubExecutionById(subExecutionId);
		 TestExecution testExecution= testExecutionService.queryTestExecutionById(subExecution.getExecutionId());

		 List<User> userList = userService.queryUsersInOwnerTeam(user.getUserId());
		 context.put("subexecution", subExecution);
		 context.put("userList", userList);
		 context.put("testExecution", testExecution);
		return SUCCESS;
	}

/**
 * Update sub execution database operation action, is working with toUpdateSubExecution action together.	
 * @return
 * @throws Exception
 */
	
	public String updateSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		//Check duplicate sub execution name, not allowed.

		SubExecution subExecution = testExecutionService.querySubExecutionById(subexecution.getSubExecutionId());
		subExecution.setSubExecutionName(subexecution.getSubExecutionName());
		subExecution.setTester(subexecution.getTester());
		subExecution.setDueDate(subexecution.getDueDate());
		subExecution.setDesc(subexecution.getDesc());
	
		int result1 = testExecutionService.querySubExecutionNameSize(subExecution);
		if(result1>=1){
			context.put(Constant.ERRORMSG,
					"Sub Execution '" + subExecution.getSubExecutionName()
							+ "' already exist!");
			return ERROR;
		}
		
		testExecutionService.updateSubExecution(subExecution);
		return SUCCESS;
	}
	
	
	/**
	 * Update Test execution action: Will check if name or cycle duplicate if user change any of this two parameter
	 * @return
	 * @throws Exception
	 */
	
	public String updateTestexecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		int result = testExecutionService.queryByExeNameCycleSize(testexecution);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(testexecution.getId());

		if(result==1){
				context.put(Constant.ERRORMSG,
						"Test execution name '" + testexecution.getExecutionName()
						+ "' already exist!");
				return ERROR;
		}
		/**
		if(result==2){
				context.put(Constant.ERRORMSG,
						"ReleaseCycle '" + testexecution.getReleaseCycle()
								+ "' already exist!");
				return ERROR;
		}
		**/
		testExecution.setExecutionName(testexecution.getExecutionName());
		testExecution.setReleaseCycle(testexecution.getReleaseCycle());
		testExecution.setStartDate(testexecution.getStartDate());
		testExecution.setEndDate(testexecution.getEndDate());
		testExecution.setDesc(testexecution.getDesc());
		testExecution.setBuildId(testexecution.getBuildId());
		testExecution.setPhaseId(testexecution.getPhaseId());
		testExecutionService.updateTestexecution(testExecution);
		return SUCCESS;
	}
	
	
	
	
	public String listTestTextExecution() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		HttpServletRequest request = ServletActionContext.getRequest();

		TestExecution  testexecution = new TestExecution();
		testexecution.setProjectId(projectId);
		testexecution.setReleaseCycle(releaseCycle);
		testexecution.setUserId(user.getUserId());
		testexecution.setOsId(osId);
		testexecution.setPlatformId(platformId);
		testexecution.setExecutionName(executionName);
		testexecution.setBuildId(buildId);
		testexecution.setPhaseId(phaseId);
		testexecution.setTestPlanId(testPlanId);
		int rowNumber = testExecutionService.queryTestexecutionSize(testexecution);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestExecution> testexecutionlists = testExecutionService.listTestExecutionByPage(pageBean, testexecution);
		context.put("testexecutionlists", testexecutionlists);
		context.put("pageBean", pageBean);
		return SUCCESS;
	}
	
	public String toDetailTestExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("executionId", String.valueOf(executionId));
		int rowNumber = testExecutionService.querySubExecutionSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		SubExecution  subexecution = new SubExecution();
		subexecution.setExecutionId(executionId);
		List<SubExecution> subexecutionlists = testExecutionService.listSubExecutionByPage(pageBean, subexecution);
		/**
		for (SubExecution execution : subexecutionlists) {
			execution.setTesterName(userService.queryUserById(execution.getTester()).getUserName());
			execution.setPhaseName(executionPhaseService.queryPhaseById(execution.getPhaseId()).getPhaseName());
			execution.setOsName(executionOSService.queryOSById(execution.getOsId()).getOsName());
			execution.setTestPlanName(testPlanService.queryTestPlan(execution.getTestPlanId()).getPlanName());
		
		}
		**/
		context.put("testexecutionlists", subexecutionlists);
		context.put("pageBean", pageBean);
		context.put("executionId", executionId);
		context.put("executionName", testExecutionService.queryTestExecutionById(executionId).getExecutionName());
		return SUCCESS;
	}

	public String toDetailSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("subExecutionId", String.valueOf(subExecutionId));
		int rowNumber = testResultService.querySubExecutionCaseSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		TestResult  testresult = new TestResult();
		testresult.setSubExecutionId(subExecutionId);
		List<TestResult> testresultlists = testResultService.listSubExecutionCaseByPage(pageBean, testresult);
		List<ResultType> resulttype = testResultService.listResultTypes();
		
		context.put("testresultlists",testresultlists);
		context.put("pageBean", pageBean);
		context.put("subExecution",testExecutionService.querySubExecutionById(subExecutionId));
		context.put("resulttypelist", resulttype);
		return SUCCESS;
	}
	
	public String toEditCaseListOfSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("subExecutionId", String.valueOf(subExecutionId));
		int rowNumber = testResultService.querySubExecutionCaseSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		TestResult  testresult = new TestResult();
		testresult.setSubExecutionId(subExecutionId);
		List<TestResult> testresultlists = testResultService.listSubExecutionCaseByPage(pageBean, testresult);
		List<ResultType> resulttype = testResultService.listResultTypes();
		
		context.put("testresultlist",testresultlists);
		context.put("pageBean", pageBean);
		context.put("subExecution",testExecutionService.querySubExecutionById(subExecutionId));
		context.put("resulttypelist", resulttype);
		return SUCCESS;
	}
	
	public String editCaseListOfSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		String currentDate = Utils.dateFormat(new Date(), null);
		List<ResultTrack> resultTrackList = new ArrayList<ResultTrack>();
		List<TestResult> resultList = new ArrayList<TestResult>();
		for(TestResult testResult : testResultLists){
			if(testResult.getResultId() > 0) {
				//Save result in table testresult for final display
				TestResult testresult = testResultService.queryTestResult(testResult.getResultId());
				testresult.setResultTypeId(testResult.getResultTypeId());
				testresult.setLog(testResult.getLog());
				testresult.setBugId(testResult.getBugId());
				testresult.setBugName(testResult.getBugName());
				testresult.setComments(testResult.getComments());
				testresult.setModifyDate(currentDate);
				testresult.setResultForExcel(Integer.toString(testResult.getResultId()));
				resultList.add(testresult);
				
				//Save result in table resulttrack to save result change history
				ResultTrack resultTrack = new ResultTrack();
				resultTrack.setTestResultId(testResult.getResultId());
				resultTrack.setResultTypeId(testResult.getResultTypeId());
				resultTrack.setCreateDate(currentDate);
				resultTrack.setModifyDate(currentDate);
				resultTrackList.add(resultTrack);
			}
		}
		testResultService.updateTestResultByExcel(resultList);
		testResultService.addBatchResultTrack(resultTrackList);
		
		//Refresh calculate result in subexecution and execution
		SubExecution subExecution= testExecutionService.querySubExecutionById(subExecutionId);
		testExecutionService.refreshSubExecution(subExecution);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(subExecution.getExecutionId());
		testExecutionService.refreshTestExecution(testExecution);

		return SUCCESS;
	}
	

/**
 * Export case list in test execution to excel
 * @return
 * @throws Exception
 */
	public String exportTestExecutionToExcel() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		exportFileName = "TestExecution_" + System.currentTimeMillis();
		inputStream = excelResolverService.exportExecutionReportToExcel(executionId, context);

		return SUCCESS;
	}	
	
/**
 * Export sub execution to Excel file
 * @return
 * @throws Exception
 */
	
	public String exportSubExecutionToExcel() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		exportFileName = "SubExecution_" + System.currentTimeMillis()+".xlsx";
		List<TestResult> testresult = testResultService.listTestResultBySubExecutionId(subExecutionId);

		inputStream = excelResolverService.exportSubTestPlanTestCase(testresult, context);

		return SUCCESS;
	}

/**
 * Export performance result to Excel, this Excel can also be used edit performance result
 * @return
 * @throws Exception
 */	
		public String exportPerformanceResultToExcel() throws Exception {

			ActionContext context = ActionContext.getContext();
			User user = (User) context.getSession().get("user");
			if (user == null) {
				return LOGIN;
			}

			exportFileName = "Performance_" + System.currentTimeMillis();
			inputStream = excelResolverService.exportPerformanceResult(subExecutionId, context);

			return SUCCESS;
		}
	
/**
 * 	Export sub execution to XML file
 * @return
 * @throws Exception
 */
	public String exportSubExecutionToXML() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		exportFileName = "subExecution";
		inputStream = testResultXmlResolverService.generateSubExecutionXml(subExecutionId,context);

		return SUCCESS;
	}
	
	public String exportSelectedCaseToXML() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test case have been selected!");
			return ERROR;
		}
		String[] selectedTestCases = multiTestExecution.split(",");
		
		inputStream = testResultXmlResolverService.generateSelectedCaseToXml(selectedTestCases,subExecutionId,context);

		return SUCCESS;
	}

	public String interfaceExportSubExecutionToXML() throws Exception {

		ActionContext context = ActionContext.getContext();
		//Manual set login status
		User user = new User();
		user.setUserName("import");
		context.getSession().put("user", user);

		exportFileName = "subExecution";
		inputStream = testResultXmlResolverService.generateSubExecutionXml(subExecutionId,context);

		return SUCCESS;
	}
	
	public String interfaceExportSelectedCaseToXML() throws Exception {

		ActionContext context = ActionContext.getContext();
		//Manual set login status
		User user = new User();
		user.setUserName("import");
		context.getSession().put("user", user);

		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test case have been selected!");
			return ERROR;
		}
		String[] selectedTestCases = multiTestExecution.split(",");
		exportFileName = "subExecution";		
		inputStream = testResultXmlResolverService.generateSelectedCaseToXml(selectedTestCases,subExecutionId,context);

		return SUCCESS;
	}
	
	public String toRunSubExecution() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		SubExecution subExecution = testExecutionService.querySubExecutionById(subExecutionId);
		context.put("subExecution",subExecution);
		
		return SUCCESS;
	}
	
	public String runSubExecution() { 
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String addr = "http://" + request.getServerName() + ":" + request.getServerPort();                  
		String cmd = "addtask " + addr + "/" + "iTMS" + "/" + "interfaceExportSubExecutionToXML.action?subExecutionId=" + subExecutionId; 
		
		try { 
				new Client(ip, Integer.parseInt(port)).sendCommand(cmd); 
			} 
			catch (NumberFormatException e) {
				context.put(Constant.ERRORMSG, "port attribute should be numberic..."); 
				return ERROR; 
			} catch (Exception e) { 
				context.put(Constant.ERRORMSG, "can not access to destination machine.."); 
				return ERROR;                    
			} 
			context.put(Constant.ERRORMSG, cmd); return SUCCESS;                            
	}
	
	public String toRunSelectedCase() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test case have been selected!");
			return ERROR;
		}
		
		SubExecution subExecution = testExecutionService.querySubExecutionById(subExecutionId);
		context.put("subExecution",subExecution);
		context.put("multiTestExecution", multiTestExecution);
		
		return SUCCESS;
	}
	
	public String runSelectedCase() { 
		
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (multiTestExecution == null) {
			context.put(Constant.ERRORMSG,
					"No test execution have been selected!");
			return ERROR;
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String addr = "http://" + request.getServerName() + ":" + request.getServerPort();                  
		String cmd = "addtask " + addr + "/" + "iTMS" + "/" + "interfaceExportSelectedCaseToXML.action?subExecutionId=" + subExecutionId+"&multiTestExecution="+multiTestExecution; 
		
		try { 
				new Client(ip, Integer.parseInt(port)).sendCommand(cmd); 
			} 
			catch (NumberFormatException e) {
				context.put(Constant.ERRORMSG, "port attribute should be numberic..."); 
				return ERROR; 
			} catch (Exception e) { 
				context.put(Constant.ERRORMSG, "can not access to destination machine.."); 
				return ERROR;                    
			} 
			context.put(Constant.ERRORMSG, cmd); return SUCCESS;                            
	}

/**
 * Send email to user notification assigned task by tester information of each sub execution.	
 * @return
 * @throws Exception
 */
	public String assignTask() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		if (executionId == 0) {
			context.put(Constant.ERRORMSG, "No execution have selected!");
			return ERROR;
		}
		
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		List<SubExecution> subExecutionList = testExecutionService.listSubExecutionByExecutionId(executionId);
		
		String email = "";
		
		for(SubExecution sub : subExecutionList) {
			User user_tmp = userService.queryUserById(sub.getTester());
			email = user_tmp.getEmail()+ "," + email ;
		}
		
		String subject = "[iTMS Task Assign] " + testExecution.getExecutionName();
		String content = "Hi, <br><br>";
		content = content + "You have been assigned task in test execution '"+testExecution.getExecutionName() + "', Please go to iTMS to check detail. <br><br>";
		content = content + "Regards, <br> iTMS";
		
		MailHelper.sendMailNew(subject, content, email);
		return SUCCESS;
	}
	
	public List<TestResult> getTestResultLists() {
		return testResultLists;
	}

	public void setTestResultLists(List<TestResult> testResultLists) {
		this.testResultLists = testResultLists;
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
	public TestExecution getTestexecution() {
		return testexecution;
	}
	public void setTestexecution(TestExecution testexecution) {
		this.testexecution = testexecution;
	}
	public SubExecution getSubexecution() {
		return subexecution;
	}
	public void setSubexecution(SubExecution subexecution) {
		this.subexecution = subexecution;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getReleaseCycle() {
		return releaseCycle;
	}
	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getOsId() {
		return osId;
	}
	public void setOsId(int osId) {
		this.osId = osId;
	}
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getPlatformId() {
		return platformId;
	}
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	public String getExecutionName() {
		return executionName;
	}
	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}
	public String getMultiTestExecution() {
		return multiTestExecution;
	}
	public void setMultiTestExecution(String multiTestExecution) {
		this.multiTestExecution = multiTestExecution;
	}

	public int getExecutionId() {
		return executionId;
	}
	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}
	public int getSubExecutionId() {
		return subExecutionId;
	}
	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}
	public int getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	public int getphaseId() {
		return phaseId;
	}
	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}
	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public List<TestUnit> getTestUnitList() {
		return testUnitList;
	}

	public void setTestUnitList(List<TestUnit> testUnitList) {
		this.testUnitList = testUnitList;
	}
}
