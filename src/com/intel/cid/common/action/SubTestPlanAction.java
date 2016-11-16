package com.intel.cid.common.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.ExecutionOS;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.Target;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.net.Client;
import com.intel.cid.common.net.JavaSCP;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class SubTestPlanAction extends BaseAction {

	private static final long serialVersionUID = 9047891158341117249L;

	private static final Logger logger = Logger
			.getLogger(SubTestPlanAction.class);

	private SubTestPlan subPlan;

	private String phaseId;

	private int testcaseid;

	private String subPlanName;

	private String testUnitNames;

	private TestPlan testPlan;

	private int testPlanId;

	private String testSuiteId;

	private int subPlanId;

	private String multiTestUnits;

	private int itemSize;

	private int linkSize;

	private int currPage;

	private String multiSubPlan;

	private String uploadContentType;

	private File upload;

	private String uploadFileName;

	private byte[] buffer = new byte[4096];

	private String savePath;

	private String allowTypes;

	private String exportFileName;

	private String contentDisposition;

	private InputStream inputStream;

	private List<TestUnit> testUnitList;

	private String passRate;
	private String subId;

	private String dueDate;

	private String ip;
	private ExecutionOS executionOS;
	private String port;
	private int tester;
	private int msg;
	private String osId;
	private int result;
	private List<SubTestPlan> subTestPlanList;
	private HttpServletRequest request = null;

	/*
	 * public String toRunMediaConfiguration() throws Exception { ActionContext
	 * context = ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user"); if(!"0".equals(String.valueOf(msg))){
	 * context.put("subId", subId); }else{ context.put("subId", subPlanId); }
	 * 
	 * List<ExecutionOS>
	 * executionOSs=executionOSService.queryExecutionOsByUserId
	 * (user.getUserId()); context.put("executionOSs", executionOSs);
	 * 
	 * if(!"".equals(osId)&& null!=osId){ List<Project> projectList =
	 * projectSerivce.queryPorjectForm(0); ExecutionOS os =
	 * executionOSService.queryOSById(Integer.parseInt(osId));
	 * os.setProjectName(
	 * projectSerivce.queryProjectById(os.getProjectId()).getProjectName());
	 * context.put("os", os); context.put("projectList", projectList);
	 * context.put("osId", osId); }
	 * 
	 * return SUCCESS; } public void runMediaConfiguration()throws IOException {
	 * ActionContext context = ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user"); JavaSCP scp = new JavaSCP(); Connection
	 * conn = scp.getConnection(executionOS); SCPClient client =
	 * scp.getSCPClient(conn); Session session = scp.getSSHSession(conn);
	 * scp.copyFileTORemote(client, "C:\\work\\itms-case-upload.xml", "/root/");
	 * System.out.println("ExitCode: " + session.getExitStatus());
	 * scp.closeSession(session); scp.closeConnection(conn); }
	 */

	/*
	 * public String runMediaConfiguration() { ActionContext context =
	 * ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user");
	 * 
	 * 
	 * if (user == null) { return LOGIN; } request =
	 * ServletActionContext.getRequest(); String addr = "http://" +
	 * request.getServerName() + ":" + request.getServerPort(); //
	 * ServletContext sc = (ServletContext) //
	 * context.get(ServletActionContext.SERVLET_CONTEXT); // String path =
	 * sc.getRealPath("/"); // String[] paths= path.split("\\"); // String
	 * projectName = paths[paths.length-1]; String cmd = "addtask " + addr + "/"
	 * + "iTMS1" + "/" + "httpExportSubTestPlanToXml.action?subPlanId=" +
	 * subPlanId;
	 * 
	 * try { new Client(ip, Integer.parseInt(port)).sendCommand(cmd); } catch
	 * (NumberFormatException e) { context.put(Constant.ERRORMSG,
	 * "port attribute should be numberic..."); return ERROR; } catch (Exception
	 * e) { context.put(Constant.ERRORMSG,
	 * "can not access to destination machine.."); return ERROR;
	 * 
	 * } context.put(Constant.ERRORMSG, cmd); return SUCCESS;
	 * 
	 * }
	 */
	public String toUploadSubTestPlan() throws Exception {

		return SUCCESS;
	}

/**
	public void httpUploadSubTestPlan() {

		ActionContext context = ActionContext.getContext();
		long time = System.currentTimeMillis();
		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		String path = sc.getRealPath(savePath + File.separator + "testresult"
				+ File.separator + Utils.dateFormat(new Date(), "yyyy_MM_dd"));
		File desDir = new File(path);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}

		if (uploadFileName == null) {

			context.put(Constant.ERRORMSG,
					"please choose at least one xml file to upload!!!");

		}

		String[] allowTypes = this.allowTypes.split(",");
		HashSet<String> allowSet = new HashSet<String>();
		for (String allowType : allowTypes) {
			allowSet.add(allowType);

		}

		if (uploadFileName != null) {

			String suffixName = uploadFileName.substring(uploadFileName
					.lastIndexOf("."), uploadFileName.length());
			if (!allowSet.contains(suffixName)) {
				context.put(Constant.ERRORMSG,
						"Just only support xml file upload!!!");

			}
		}

		if (upload != null) {
			logger.info("get the file:" + uploadFileName);
			InputStream ins = null;
			OutputStream ous = null;

			try {
				ins = new FileInputStream(upload);
				File singleFile = new File(path
						+ File.separator
						+ Utils.renameFileAppendSuffix(uploadFileName, String
								.valueOf(++time)));
				ous = new FileOutputStream(singleFile);
				int len = 0;
				while ((len = ins.read(buffer)) > 0) {
					ous.write(buffer, 0, len);
				}

				SubTestPlan subTestPlan = testResultXmlResolverService
						.resolveSubTestPlanXml(singleFile);
				// subTestPlanService
				// .updateSubTestPlanAfterTestResultsUpload(subTestPlan);

				context.put(Constant.SUCCESSMSG, "update success!");
				logger.info("update success:" + uploadFileName);
			} catch (FileNotFoundException e) {
				context.put(Constant.ERRORMSG, "an error has occurred!");

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				if (null != ins) {
					try {
						ins.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (ous != null) {
					try {
						ous.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}

	}
**/
	
/**
	public String uploadSubTestPlan() {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		long time = System.currentTimeMillis();
		if (user == null) {
			return LOGIN;
		}
		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		String path = sc.getRealPath(savePath + File.separator + "testresult"
				+ File.separator + Utils.dateFormat(new Date(), "yyyy_MM_dd"));
		File desDir = new File(path);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}

		if (uploadFileName == null) {

			context.put(Constant.ERRORMSG,
					"please choose at least one xml file to upload!!!");
			return "input";

		}

		String[] allowTypes = this.allowTypes.split(",");
		HashSet<String> allowSet = new HashSet<String>();
		for (String allowType : allowTypes) {
			allowSet.add(allowType);

		}

		if (uploadFileName != null) {

			String suffixName = uploadFileName.substring(uploadFileName
					.lastIndexOf("."), uploadFileName.length());
			if (!allowSet.contains(suffixName)) {
				context.put(Constant.ERRORMSG,
						"Just only support xml file upload!!!");
				return "input";
			}
		}

		if (upload != null) {
			logger.info("get the file:" + uploadFileName);
			InputStream ins = null;
			OutputStream ous = null;

			try {
				ins = new FileInputStream(upload);
				File singleFile = new File(path
						+ File.separator
						+ Utils.renameFileAppendSuffix(uploadFileName, String
								.valueOf(++time)));
				ous = new FileOutputStream(singleFile);
				int len = 0;
				while ((len = ins.read(buffer)) > 0) {
					ous.write(buffer, 0, len);
				}

				SubTestPlan subTestPlan = testResultXmlResolverService
						.resolveSubTestPlanXml(singleFile);
				// subTestPlanService
				// .updateSubTestPlanAfterTestResultsUpload(subTestPlan);

				context.put(Constant.SUCCESSMSG, "update success!");
				logger.info("update success:" + uploadFileName);
			} catch (FileNotFoundException e) {
				context.put(Constant.ERRORMSG, "an error has occurred!");
				return ERROR;

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				if (null != ins) {
					try {
						ins.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (ous != null) {
					try {
						ous.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}

		return SUCCESS;
	}
**/
	
	public String addSubTestPlan() throws Exception {

		String createDate = Utils.dateFormat(new Date(), null);
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");

		if (user == null) {
			return LOGIN;
		}

		testPlan = testPlanService.queryTestPlan(testPlanId);
		testPlan.setModifyDate(createDate);
		subPlan.setOwner(user.getUserId());
		subPlan.setProjectId(testPlan.getProjectId());
		if (Utils.isNullORWhiteSpace(subPlan.getDueDate())) {
			subPlan.setDueDate(testPlan.getEndDate());
		}
		subPlan.setCreateDate(createDate);
		subPlan.setTestPlanId(testPlanId);
		subPlan.setTestSuiteId( Integer.parseInt(testSuiteId));
		
		int result = subTestPlanService.querySubPlanSize(subPlan);
		if(result==1){
			context.put(Constant.ERRORMSG, "Sub test plan name '" + subPlan.getSubPlanName() + "' already exist!");
			return ERROR;
		}
		
		int result1 = subTestPlanService.querySuiteInSubPlanSize(subPlan);
		if(result1==1){
			context.put(Constant.ERRORMSG, "Selected test suite already used by another sub test plan!");
			return ERROR;
		}
		
		int suiteId = Integer.parseInt(testSuiteId.trim());
		List<TestCase> testCaseList = testSuiteService.listTestCasesBySuiteId(suiteId);
		List<TestUnit> preAddTestUnitList = new ArrayList<TestUnit>();
		int totalTestCases = 0;
		for (TestUnit unit : testUnitList) {

			if (Utils.isNullORWhiteSpace(unit.getTestUnitName())) {
				continue;
			}
			unit.setTestPlanId(testPlanId);
			unit.setTestSuiteId(suiteId);
			if (Utils.isNullORWhiteSpace(unit.getDueDate())) {
				unit.setDueDate(subPlan.getDueDate());
			}
			unit.setCreateDate(createDate);
			unit.setTotalCases(testCaseList.size());
			preAddTestUnitList.add(unit);
			totalTestCases = totalTestCases + testCaseList.size();
		}
		subPlan.setTotalCases(totalTestCases);
		subPlan.setTestUnitList(preAddTestUnitList);
		testPlan.setSubPlan(subPlan);
		subTestPlanService.addSubTestPlan(testPlan);
		return SUCCESS;
	}

	/*
	 * public String ListResultBySubplanId() throws Exception { String
	 * createDate = Utils.dateFormat(new Date(), null); ActionContext context =
	 * ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user");
	 * 
	 * if (user == null) { return LOGIN; }
	 * 
	 * int rowNumber=subTestPlanService.ListResultBySubplanIdSize(subPlanId);
	 * PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
	 * PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
	 * List<TestResult> resultList =
	 * subTestPlanService.ListResultBySubplanId(pageBean, subPlanId);
	 * for(TestResult result:resultList){
	 * result.setTestCasealiasId(testCaseService
	 * .queryTestCaseById(result.getTestCaseId()).getTestCasealiasId()); }
	 * 
	 * context.put("resultList", resultList); context.put("pageBean", pageBean);
	 * context.put("subPlanId", subPlanId);
	 * 
	 * return SUCCESS; }
	 */

	/*
	 * public String ListResultBySubplanId() throws Exception { String
	 * createDate = Utils.dateFormat(new Date(), null); ActionContext context =
	 * ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user");
	 * 
	 * if (user == null) { return LOGIN; }
	 * 
	 * int rowNumber = subTestPlanService.ListResultBySubplanIdSize(subPlanId);
	 * PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
	 * PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
	 * List<TestCase> testCasetList = subTestPlanService
	 * .ListResultBySubplanId(pageBean, subPlanId); context.put("testCasetList",
	 * testCasetList); context.put("pageBean", pageBean);
	 * context.put("testcaseid", testcaseid); return SUCCESS; }
	 */
	public String toAddSubTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		if (testPlanId == 0) {
			context.put(Constant.ERRORMSG, "testPlan is not exists!");
			return ERROR;
		}
		TestPlan testPlan = testPlanService.queryTestPlan(testPlanId);

		int projectId = testPlan.getProjectId();
		Project project = projectSerivce.queryProjectById(projectId);
		testPlan.setProject(project);
		if (projectId == 0) {
			context.put(Constant.ERRORMSG,
					"test plan's project attribute can not null!");
			return ERROR;
 
		}

		List<TestSuite> testSuiteList = testSuiteService.listTestSuiteByProjectId(projectId);
		// List<User> userList = userService.listUserByProjectId(projectId);
		List<Target> targetList = targetService
				.listTargetByProjectId(projectId);
		context.put("testSuiteList", testSuiteList);
		context.put("testPlan", testPlan);
		// context.put("userList", userList);
		context.put("targetList", targetList);
		return SUCCESS;
	}

	
	public String toDetailSubTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		testUnitList = testUnitService.listTestUnits(subPlanId);
		Project project = projectSerivce.queryProjectById(subPlan
				.getProjectId());
		// subPlan.setProjectName(projectName);
		for (TestUnit unit : testUnitList) {
			TestSuite testSuite = testSuiteService.queryTestSuiteById(unit
					.getTestSuiteId());
			Target target = targetService.queryTargetById(unit.getTargetId());
			unit.setTestSuite(testSuite);
			unit.setTarget(target);
		}
		context.put("project", project);
		context.put("subPlan", subPlan);
		return SUCCESS;
	}

	public String addTestUnit() throws Exception {
		String createDate = Utils.dateFormat(new Date(), null);
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);
		int suiteId = Integer.parseInt(testSuiteId.trim());		
		List<TestCase> testCaseList = testSuiteService.listTestCasesBySuiteId(suiteId);
		
		List<TestUnit> preAddTestUnitList = new ArrayList<TestUnit>();
		int totalTestCases = 0;
		for (TestUnit unit : testUnitList) {

			if (Utils.isNullORWhiteSpace(unit.getTestUnitName())) {
				continue;
			}
			unit.setTestPlanId(testPlan.getTestPlanId());
			unit.setSubPlanId(subPlanId);
			unit.setTestSuiteId(suiteId);
			if (Utils.isNullORWhiteSpace(unit.getDueDate())) {
				unit.setDueDate(subPlan.getDueDate());
			}
			unit.setCreateDate(createDate);
			unit.setTotalCases(testCaseList.size());
			preAddTestUnitList.add(unit);
			totalTestCases = totalTestCases + testCaseList.size();
		}
		subPlan.setTotalCases(totalTestCases);
		subPlan.setTestUnitList(preAddTestUnitList);
		testPlan.setSubPlan(subPlan);
		subTestPlanService.addTestUnits(testPlan);
		
		return SUCCESS;
	}

	public String toUpdateSubTestPlan() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}		
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);		
		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);
		long gap = 0L;
		String today = Utils.dateFormat(System.currentTimeMillis(),
				Constant.DATE_FORMAT_YMD);
		String endDate = testPlan.getEndDate();
		if (Utils.dateCompare(today, endDate, Constant.DATE_FORMAT_YMD) >= 0) {
			gap = -1; // test unit duedate can not update
		} else {
			gap = 1; // test unit can update
		}
	
		context.put("gap", gap);
		context.put("testPlan", testPlan);
		context.put("subPlan", subPlan);

		return SUCCESS;
	}

	public String updateSubTestPlan() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);
	
		String modifyDate = Utils.dateFormat(new Date(), null);

		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		subPlan.setSubPlanName(subPlanName);
		subPlan.setDueDate(dueDate);
		subPlan.setModifyDate(modifyDate);
		subPlan.setTestPlanId(testPlan.getTestPlanId());

		int result = subTestPlanService.querySubPlanSize(subPlan);
		if(result==1){
			context.put(Constant.ERRORMSG, "Sub test plan name '" + subPlan.getSubPlanName() + "' already exist!");
			return ERROR;
		}
		
		// TODO when update duedate ,
		// all testUnit duedate belongs to this subplan also may be changed .
		subTestPlanService.updateSubTestPlan(subPlan);

		return SUCCESS;
	}
/**
	public String exportSubTestPlanToXml() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);

		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		String[] testUnits = multiTestUnits.split(",");

		testUnitList = new ArrayList<TestUnit>();
		for (String unitId : testUnits) {

			int testUnitId = Integer.parseInt(unitId.trim());
			TestUnit testUnit = testUnitService.queryTestUnit(testUnitId);
			TestSuite testSuite = testSuiteService.queryTestSuiteById(testUnit
					.getTestSuiteId());
			List<TestCase> testCaseList = testUnitService
					.getTestCasesFromTestUnit(testUnitId);
			testSuite.setTestcaseList(testCaseList);
			testUnit.setTestSuite(testSuite);

			testUnit.setTestSuite(testSuite);
			testUnitList.add(testUnit);
		}

		subPlan.setTestUnitList(testUnitList);
		testPlan.setSubPlan(subPlan);

		exportFileName = testUnitList.get(0).getTestSuite().getTestSuiteName();
		if (Utils.isNullORWhiteSpace(exportFileName)) {
			exportFileName = "tmp";
		}
		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		String path = sc.getRealPath("/test");
		File desDir = new File(path);

		if (!desDir.exists()) {
			desDir.mkdir();
		}
		testResultXmlResolverService.generateTestPlanXml(testPlan, path
				+ File.separator + exportFileName + ".xml");
		InputStream ins = new FileInputStream(new File(path + File.separator
				+ exportFileName + ".xml"));
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = ins.read(buffer)) > 0) {
			ous.write(buffer, 0, len);

		}
		inputStream = new ByteArrayInputStream(ous.toByteArray());

		return SUCCESS;
	}
**/

/**	
	public String httpExportSubTestPlanToXml() throws Exception {
		ActionContext context = ActionContext.getContext();

		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);

		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);
		testUnitList = new ArrayList<TestUnit>();

		testUnitList = testUnitService.listTestUnits(subPlan.getSubPlanId());

		for (TestUnit testUnit : testUnitList) {

			int testUnitId = testUnit.getTestUnitId();
			TestSuite testSuite = testSuiteService.queryTestSuiteById(testUnit
					.getTestSuiteId());
			List<TestCase> testCaseList = testUnitService
					.getTestCasesFromTestUnit(testUnitId);
			testSuite.setTestcaseList(testCaseList);
			testUnit.setTestSuite(testSuite);

			testUnit.setTestSuite(testSuite);

		}

		subPlan.setTestUnitList(testUnitList);
		testPlan.setSubPlan(subPlan);

		exportFileName = testUnitList.get(0).getTestSuite().getTestSuiteName();

		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		String path = sc.getRealPath("/test");
		File desDir = new File(path);

		if (!desDir.exists()) {
			desDir.mkdir();
		}
		testResultXmlResolverService.generateTestPlanXml(testPlan, path
				+ File.separator + exportFileName + ".xml");
		InputStream ins = new FileInputStream(new File(path + File.separator
				+ exportFileName + ".xml"));
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = ins.read(buffer)) > 0) {
			ous.write(buffer, 0, len);

		}
		inputStream = new ByteArrayInputStream(ous.toByteArray());

		return SUCCESS;
	}
**/
	
	public String toExportSubTestPlanToXml() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		List<TestUnit> testUnitList = testUnitService.listTestUnits(subPlanId);
		context.put("testUnitList", testUnitList);
		return SUCCESS;

	}

/**
	public String exportFailedSubTestPlanToXml() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);

		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);

		String[] testUnits = multiTestUnits.split(",");

		testUnitList = new ArrayList<TestUnit>();
		for (String unitId : testUnits) {

			int testUnitId = Integer.parseInt(unitId.trim());
			TestUnit testUnit = testUnitService.queryTestUnit(testUnitId);
			TestSuite testSuite = testSuiteService.queryTestSuiteById(testUnit
					.getTestSuiteId());
			List<TestCase> testCaseList = testUnitService
					.getAllFailedTestCasesFromTestUnit(testUnitId);
			testSuite.setTestcaseList(testCaseList);
			testUnit.setTestSuite(testSuite);

			testUnit.setTestSuite(testSuite);
			testUnitList.add(testUnit);
		}

		subPlan.setTestUnitList(testUnitList);
		testPlan.setSubPlan(subPlan);

		exportFileName = testUnitList.get(0).getTestSuite().getTestSuiteName();
		if (Utils.isNullORWhiteSpace(exportFileName)) {
			exportFileName = "tmp";
		}
		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		String path = sc.getRealPath("/test");
		File desDir = new File(path);

		if (!desDir.exists()) {
			desDir.mkdir();
		}
		testResultXmlResolverService.generateTestPlanXml(testPlan, path
				+ File.separator + exportFileName + ".xml");
		InputStream ins = new FileInputStream(new File(path + File.separator
				+ exportFileName + ".xml"));
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = ins.read(buffer)) > 0) {
			ous.write(buffer, 0, len);

		}
		inputStream = new ByteArrayInputStream(ous.toByteArray());

		return SUCCESS;
	}
**/
	public String toExportFailedSubTestPlanToXml() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);

		List<TestUnit> testUnitList = testUnitService.listFailedTestUnits(
				subPlanId, "No");

		context.put("testUnitList", testUnitList);
		return SUCCESS;

	}

	public String toAddTestUnit() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		List<TestUnit> testUnits = testUnitService.listTestUnits(subPlanId);
		
		int projectId = subPlan.getProjectId();
		
		List<TestSuite> testSuiteList = testSuiteService.listTestSuiteByProjectId(projectId);
		List<Target> targetList = targetService.listTargetByProjectId(projectId);
		
		
		if (projectId == 0) {
			context.put(Constant.ERRORMSG, "sub plan's project attribute can not null!");
			return ERROR;
		}

		context.put("testSuiteId", testSuiteId);
		context.put("testUnitList", testUnits);
		context.put("testSuiteList", testSuiteList);
		context.put("targetList", targetList);

		return SUCCESS;
	}

	public String delTestUnits() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		testPlan = testPlanService.queryTestPlanBySubPlan(subPlanId);
		testPlan.setSubPlan(subPlan);
		if (Utils.isNullORWhiteSpace(multiTestUnits)) {
			context.put(Constant.ERRORMSG, "");
			return ERROR;
		}
		String[] testUnits = multiTestUnits.split(",");
		subTestPlanService.deleteTestUnit(testPlan, testUnits);
		testUnitList = testUnitService.listTestUnits(subPlanId);
		return SUCCESS;
	}

	public SubTestPlan getSubPlan() {
		return subPlan;
	}

	public void setSubPlan(SubTestPlan subPlan) {
		this.subPlan = subPlan;
	}

	public String getphaseId() {
		return phaseId;
	}

	public void setphaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getSubPlanName() {
		return subPlanName;
	}

	public void setSubPlanName(String subPlanName) {
		this.subPlanName = subPlanName;
	}

	public String getTestUnitNames() {
		return testUnitNames;
	}

	public void setTestUnitNames(String testUnitNames) {
		this.testUnitNames = testUnitNames;
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

	public String getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(String testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}

	public String getMultiTestUnits() {
		return multiTestUnits;
	}

	public void setMultiTestUnits(String multiTestUnits) {
		this.multiTestUnits = multiTestUnits;
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

	public String getMultiSubPlan() {
		return multiSubPlan;
	}

	public void setMultiSubPlan(String multiSubPlan) {
		this.multiSubPlan = multiSubPlan;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getAllowTypes() {
		return allowTypes;
	}

	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<TestUnit> getTestUnitList() {
		return testUnitList;
	}

	public void setTestUnitList(List<TestUnit> testUnitList) {
		this.testUnitList = testUnitList;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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

	public int getTester() {
		return tester;
	}

	public void setTester(int tester) {
		this.tester = tester;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public List<SubTestPlan> getSubTestPlanList() {
		return subTestPlanList;
	}

	public void setSubTestPlanList(List<SubTestPlan> subTestPlanList) {
		this.subTestPlanList = subTestPlanList;
	}

	public int getTestcaseid() {
		return testcaseid;
	}

	public void setTestcaseid(int testcaseid) {
		this.testcaseid = testcaseid;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public ExecutionOS getExecutionOS() {
		return executionOS;
	}

	public void setExecutionOS(ExecutionOS executionOS) {
		this.executionOS = executionOS;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

}
