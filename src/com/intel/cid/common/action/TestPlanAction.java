package com.intel.cid.common.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.ReportDaoImp;
import com.intel.cid.utils.JsonUtil;
import com.intel.cid.utils.MailGenerator;
import com.intel.cid.utils.MailHelper;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestPlanAction extends BaseAction {

	private static final long serialVersionUID = -1397740216601400127L;

	private TestPlan testPlan;

	private int testPlanId;

	private int subPlanId;

	private int itemSize;

	private int linkSize;

	private int currPage;
	private int totalCases;
	private String multiPlanId;

	private String multiSubPlan;
	private String passRate;
	private String project;

	private ReportDaoImp reportDaoImp;

	private int projectId;
	private String planName;
	private int phaseId;

	HttpServletResponse response = null;

	public String listTestPlan_iframe() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		/*if (user == null) {
			return LOGIN;
		}*/		
		List<Project> projectList = projectSerivce.queryProjectByUserId(user
				.getUserId());	
		context.put("projectList", projectList);
		return SUCCESS;
	}

	public String listTestPlanControl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("projectId", projectId);
		request.setAttribute("phaseID", phaseId);
		return SUCCESS;
	}

	public String toAddTestPlan() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		/*if (user == null) {
			return LOGIN;
		}*/
		List<Project> projectList = projectSerivce.queryProjectByUserId(user
				.getUserId());
		List<Phase> phaseList = phaseService.queryAllPhasesByUserId(user
				.getUserId());
		context.put("phaseList", phaseList);
		context.put("projectList", projectList);
		return SUCCESS;
	}

	public String listTestPlans() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		// request.getAttribute("phaseID");
		Map<String, String> filterMap = new HashMap<String, String>();
		// filterMap.put("teamId", null);
		filterMap.put("userId", String.valueOf(user.getUserId()));
		if (-1 == projectId) {
			projectId = 0;
		}
		if (phaseId == -1) {
			phaseId = 0;
		}
		filterMap.put("planName", String.valueOf(planName));
		filterMap.put("projectId", String.valueOf(projectId));
		filterMap.put("phaseId", String.valueOf(phaseId));
		int rowNumber = testPlanService.queryTestPlanSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<TestPlan> testPlanList = testPlanService.listTestPlanByPage(
				pageBean, filterMap);
		for (TestPlan testPlan : testPlanList) {
			User owUser = userService.queryUserById(testPlan.getOwner());
			testPlan.setUser(owUser);
			Project project = projectSerivce.queryProjectById(testPlan
					.getProjectId());
			testPlan.setProject(project);
			phaseService.queryPhaseById(testPlan.getPhaseId()).getPhaseName();
			Phase phase = phaseService.queryPhaseById(testPlan.getPhaseId());
			testPlan.setPhase(phase);
		}
		context.put("testPlanList", testPlanList);
		context.put("pageBean", pageBean);
		return SUCCESS;
	}

	public String toUpdateTestPlan() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");

		if (user == null) {
			return LOGIN;
		}
		if (!String.valueOf(testPlanId)
				.matches(Constant.POSITIVE_INTEGER_REGEX)) {
			context.put(Constant.ERRORMSG, "invalide parameter!");

			return ERROR;
		}

		testPlan = testPlanService.queryTestPlan(testPlanId);
		Project project = projectSerivce.queryProjectById(testPlan
				.getProjectId());
		testPlan.setProject(project);
		long gap = 0L;
		String today = Utils.dateFormat(System.currentTimeMillis(),
				Constant.DATE_FORMAT_YMD);
		String startDate = testPlan.getStartDate();
		String endDate = testPlan.getEndDate();
		if (Utils.dateCompare(today, endDate, Constant.DATE_FORMAT_YMD) >= 0) {
			gap = -1; // startdate and enddate can not update
		} else {

			if (Utils.dateCompare(today, startDate, Constant.DATE_FORMAT_YMD) >= 0) {

				gap = 0;// start date can not update ,enddate can
			} else {
				gap = 1; // start date and enddate both can  update
			}
		}

		// List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
		List<User> userList = userService.queryUsersInOwnerTeam(user
				.getUserId());
		List<Phase> phaseList = phaseService.queryAllPhasesByProjectId(testPlan.getProjectId());
		context.put("phaseList", phaseList);
		// context.put("teamList", teamList);
		context.put("userList", userList);
		context.put("testPlan", testPlan);
		context.put("gap", gap);
		return SUCCESS;
	}

	public String updateTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		/*if (user == null) {
			return LOGIN;
		}*/

		int result = testPlanService.queryByPlanNameSize(testPlan);
		if(result==1){
			context.put(Constant.ERRORMSG, "Test plan name '"+ testPlan.getPlanName() + "' already exist!");
			return ERROR;
		}
		
		String modifyDate = Utils.dateFormat(new Date(), null);
		testPlan.setModifyDate(modifyDate);
		testPlan.setOwner(user.getUserId());
		testPlanService.updateTestPlan(testPlan);
		return SUCCESS;
	}
 
	public String delTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		if (Utils.isNullORWhiteSpace(multiPlanId)) {
 
			context.put(Constant.ERRORMSG,
					"No test plan have been selected!");
			return ERROR;
		}

		String[] testplans = multiPlanId.split(",");

		testPlanService.delTestPlan(testplans);
		return SUCCESS;
	}

	public String toDetailTestPlan() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testPlan = testPlanService.queryTestPlan(testPlanId);		
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("testPlanId", String.valueOf(testPlanId));
		int rowNumber = subTestPlanService.querySubTestPlanSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		List<SubTestPlan> subPlanList = subTestPlanService.listSubTestPlanByPage(pageBean, filterMap);	
		/**
		for (SubTestPlan subPlan : subPlanList) {			
			User owner = userService.queryUserById(subPlan.getOwner());
			Project project = projectSerivce.queryProjectById(subPlan.getProjectId());
			subPlan.setUser(owner);
			subPlan.setProject(project);
						
		}
		**/
		context.put("subPlanList", subPlanList);
		context.put("testPlanId", testPlanId);
		context.put("pageBean", pageBean);
		return SUCCESS;
	}

	public String delSubTestPlansInOneTestPlan() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (Utils.isNullORWhiteSpace(multiSubPlan)) {

			context.put(Constant.ERRORMSG, "No sub plan have been selected");
			return ERROR;
		}

		String[] multiSubPlanIds = multiSubPlan.split(",");
		subTestPlanService.delBatchSubTestPlanInOneTestPlan(multiSubPlanIds);
		return SUCCESS;

	}

	public String toCopyTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");  
		List<Project> projectList = projectSerivce.queryProjectByUserId(user
				.getUserId());	
		context.put("projectList", projectList);
		return SUCCESS;

	}

/**
 * Function to copy a existed test plan to new one. new test plan name will be added as a tmp name and test plan owner will be set to current user.	
 * @return
 * @throws Exception
 */

	public String copyTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (multiPlanId == null || multiPlanId.equalsIgnoreCase("undefined")) {
			context.put(Constant.ERRORMSG,
					"No test plan have been selected!");
			return ERROR;
		}

		String createDate = Utils.dateFormat(new Date(), null);
		long systemMills = System.currentTimeMillis();
		String[] testplans = multiPlanId.split(",");
		
		TestPlan oldTestPlan = testPlanService.queryTestPlan(Integer.parseInt(testplans[0].trim()));
		TestPlan newTestPlan = testPlanService.queryTestPlan(Integer.parseInt(testplans[0].trim()));
		
		newTestPlan.setCreateDate(createDate);
		newTestPlan.setModifyDate(createDate);
		newTestPlan.setOwner(user.getUserId());
		newTestPlan.setPlanName(oldTestPlan.getPlanName() +" "+ systemMills);
		
		testPlanId = testPlanService.addTestPlan(newTestPlan);
		newTestPlan.setTestPlanId(testPlanId);
		
		testPlanService.copyTestPlan(oldTestPlan, newTestPlan);
		
		return SUCCESS;

	}

	/*
	 * public void getTestPlanLists() { response =
	 * ServletActionContext.getResponse(); List<?> list = null; try { list =
	 * testPlanService.queryTestPlanByProject(projectId); } catch (Exception e1)
	 * {
	 * 
	 * e1.printStackTrace(); } try { String json = JsonUtil.list2json(list);
	 * response.getWriter().print(json); } catch (IOException e) {
	 * 
	 * e.printStackTrace(); } }
	 */

	

	public String addTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		int proId = testPlan.getProjectId();
		if (proId == 0) {
			context.put(Constant.ERRORMSG,
					"Project can not null!");
			return ERROR;
		}

		int result = testPlanService.queryByPlanNameSize(testPlan);
		if(result==1){
			context.put(Constant.ERRORMSG, "Test plan name '"+ testPlan.getPlanName() + "' already exist!");
			return ERROR;
		}
		
		String createDate = Utils.dateFormat(new Date(), null);
		testPlan.setOwner(user.getUserId());
		testPlan.setCreateDate(createDate);
		testPlanService.addTestPlan(testPlan);

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void getPhaseList() throws Exception {

		response = ServletActionContext.getResponse();
		List list = phaseService.queryPhasesByProjectId(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public int getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
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

	public String getMultiPlanId() {
		return multiPlanId;
	}

	public void setMultiPlanId(String multiPlanId) {
		this.multiPlanId = multiPlanId;
	}

	public String getMultiSubPlan() {
		return multiSubPlan;
	}

	public void setMultiSubPlan(String multiSubPlan) {
		this.multiSubPlan = multiSubPlan;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public ReportDaoImp getReportDaoImp() {
		return reportDaoImp;
	}

	public void setReportDaoImp(ReportDaoImp reportDaoImp) {
		this.reportDaoImp = reportDaoImp;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

}
