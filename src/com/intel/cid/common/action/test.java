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
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;
import com.intel.cid.common.action.TestExecutionAction;;

public class test extends BaseAction {

	private static final long serialVersionUID = -1397740216601400127L;

	private int itemSize;

	private int linkSize;

	private int currPage;
	private String exportFileName;
	private int projectId;
	private String testPlanId;
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

	
	public static void main(String[] args) throws Exception  {

		ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
		ArrayList<String> nodeList = new ArrayList<String>();

		String input = "1,2,3";
		String[] array = input.split(",");
		for(String str : array) {
			System.out.println(str);
		}
		
		//sample CSV strings...pretend they came from a file
    	String[] csvStrings = new String[] {
    			"abc,def,ghi,jkl,mno",
    			"pqr,stu,vwx,yz",
    			"123,345,678,90"
    	};

    	List<List<String>> csvList = new ArrayList<List<String>>();

    	//pretend you're looping through lines in a file here
    	for(String line : csvStrings)
    	{
    		String[] linePieces = line.split(",");
    		
    		//List<String> csvPieces = new ArrayList<String>(linePieces.length);
    		List<String> csvPieces = new ArrayList<String>();
    		for(String piece : linePieces)
    		{
    			csvPieces.add(piece);
    		}
    		csvList.add(csvPieces);
    	}
    	System.out.println("=========================");
		for(String line : csvStrings){
			String[] linePieces = line.split(",");
			if(1 == 1) {
				if(1 == 2) {
	  
		    	} else {
		    		List<String> csvPieces = new ArrayList<String>();
		    		for(String piece : linePieces)
		    		{
		    			csvPieces.add(piece);
		    		}
		    		csvList.add(csvPieces);
				}
			} else {
				//resultId = result.getResultId();
			}
		}

    	//write the CSV back out to the console
    	for(List<String> csv : csvList)
    	{
    		//dumb logic to place the commas correctly
    		if(!csv.isEmpty())
    		{
    			System.out.print(csv.get(0));
    			for(int i=1; i < csv.size(); i++)
    			{
    				System.out.print("," + csv.get(i));
    			}
    		}
    		System.out.print("\n");
    	}
    	System.out.println("=========================");
    	List<String> tbody = new ArrayList<String>();
    	tbody.add(0,"23");
    	tbody.add(0,"23");
    	tbody.add(1,"test");
    	System.out.println("==========="+tbody.size()+"==============");
		for(int i=0; i < tbody.size(); i++)
		{
			System.out.print("," + tbody.get(i));
		}
		
		System.out.println("=================================");
		String[] toppings = {"Cheese", "Pepperoni", "Black Olives"};
		//String[] attrs = attr.split(";");
		int j = toppings.length;
		 int size = toppings.length;
		    for (int i=0; i<size; i++)
		    {
		      System.out.println(toppings[i]);
		    }
		    System.out.println("================="+size+"================");
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
	public String getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(String testPlanId) {
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
