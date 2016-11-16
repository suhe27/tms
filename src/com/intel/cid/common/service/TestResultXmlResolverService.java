package com.intel.cid.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.Nic;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.ResultTrack;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestPlanTrack;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.TestSuite;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.PlanMapDaoImpl;
import com.intel.cid.common.dao.impl.SubTestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestTypeDaoImpl;
import com.intel.cid.common.dao.impl.TestUnitDaoImpl;
import com.intel.cid.common.dao.impl.TestExecutionDaoImpl;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestResultXmlResolverService {
	
	private int subExecutionId;

	private SubTestPlanDaoImpl subTestPlanDaoImpl;

	private TestUnitDaoImpl testUnitDaoImpl;

	private TestPlanDaoImpl testPlanDaoImpl;

	private PlanMapDaoImpl planMapDaoImpl;

	private TestCaseDaoImpl testCaseDaoImpl;

	private TestResultDaoImpl testResultDaoImpl;

	private TestTypeDaoImpl testTypeDaoImpl;
	
	private TestExecutionDaoImpl testExecutionDaoImpl;

	private static  Map<Integer, String> testTypeMap = new HashMap<Integer, String>();

	public void setUp() throws Exception {
		List<TestType> testTypeList = testTypeDaoImpl.queryAllTestTypes();
		for (TestType testType : testTypeList) {
			testTypeMap.put(testType.getTypeId(), testType.getTypeName());
			
		}
	}
/**
	public void generateTestPlanXml(TestPlan testPlan, String path)
			throws Exception {
		SubTestPlan subPlan = testPlan.getSubPlan();

		List<TestUnit> testUnitList = subPlan.getTestUnitList();

		Document doc = Utils.getDocument();

		Element testUnitsElem = doc.createElement("TestUnits");

		doc.appendChild(testUnitsElem);
		// testUnitsElem.setAttribute("Project", testPlan.getProject());
		// testUnitsElem.setAttribute("ReleaseCycle",
		// testPlan.getReleaseCycle());
		testUnitsElem.setAttribute("SubPlan", subPlan.getSubPlanName());

		for (TestUnit testUnit : testUnitList) {
			TestSuite testSuite = testUnit.getTestSuite();
			List<TestCase> testCaseList = testSuite.getTestcaseList();
			Board board = testUnit.getBoard();
			Element testUnitElem = doc.createElement("TestUnit");

			testUnitElem.setAttribute("Name", testUnit.getTestUnitName());

			testUnitElem.setAttribute("Space", testUnit.getSpace());
			testUnitElem.setAttribute("Mode", testUnit.getMode());
			testUnitElem.setAttribute("package", subPlan.getPackageInfos());
			testUnitElem.setAttribute("Board", board.getBoardName());
			testUnitElem.setAttribute("tag", testUnit.getScriptFlag());

			for (TestCase testCase : testCaseList) {

				Element testCaseElem = doc.createElement("TestCase");

				testCaseElem.setAttribute("Name", testCase.getTestCaseName());
				testCaseElem.setAttribute("ID", testCase.getTestCasealiasId());
				testCaseElem.setAttribute("Automation", String.valueOf(testCase
						.getAutoId()));
				testCaseElem.setAttribute("Timeout", String.valueOf(testCase
						.getTimeout()));

				Element cofigElem = doc.createElement("ConfigFiles");

				Text configNode = createTextNode(doc, testCase.getConfigFiles());
				cofigElem.appendChild(configNode);
				Element funcElem = doc.createElement("testfunctioncall");
				Text funcNode = createTextNode(doc, testCase
						.getTestfunctionCall());
				funcElem.appendChild(funcNode);
				Element scriptElem = doc.createElement("TestScript");
				Text scriptNode = createTextNode(doc, testCase.getTestScript());
				scriptElem.appendChild(scriptNode);
				testCaseElem.appendChild(cofigElem);
				testCaseElem.appendChild(funcElem);
				testCaseElem.appendChild(scriptElem);
				testUnitElem.appendChild(testCaseElem);

			}

			testUnitsElem.appendChild(testUnitElem);

		}

		Utils.writeXmlToFile(doc, path);
	}
**/
/**
 * Export test case list in execution to XML	
 * @param caseList
 * @param path
 * @throws Exception
 */

	public InputStream generateSubExecutionXml(int subExecutionId,ActionContext context) throws Exception {
		
		SubExecution subexecution =testExecutionDaoImpl.querySubExecutionById(subExecutionId);
		List<TestResult> units = testResultDaoImpl.listTestUnitsBySubExecutionId(subExecutionId);
		
		Document doc = Utils.getDocument();

		Element rootDom = doc.createElement("TestExecution");

		doc.appendChild(rootDom);
		rootDom.setAttribute("Name", subexecution.getExecutionName());
		rootDom.setAttribute("Project", subexecution.getProjectName());
		rootDom.setAttribute("ReleaseCycle", subexecution.getReleaseCycle());
		Element subExecutionE = doc.createElement("SubExecution");
		
		rootDom.appendChild(subExecutionE);
		subExecutionE.setAttribute("Name", subexecution.getSubExecutionName());
		subExecutionE.setAttribute("SubPlan", subexecution.getSubPlanName());
		subExecutionE.setAttribute("Platform", subexecution.getPlatFormName());
		subExecutionE.setAttribute("ExecutionOS", subexecution.getOsName());
		subExecutionE.setAttribute("subExecutionId", Integer.toString(subexecution.getSubExecutionId()));
		for(TestResult tUnit : units) {
			Element suiteE = doc.createElement("TestSuite");
			subExecutionE.appendChild(suiteE);
			suiteE.setAttribute("Name", tUnit.getSuiteName());
			suiteE.setAttribute("Target", tUnit.getTargetName());
			List<TestResult> cList = testResultDaoImpl.listTestResultBySubExecutionIdAndUnitId(subExecutionId,tUnit.getTestunitId());
			for(TestResult tCase : cList) {
				Element tCaseE = doc.createElement("TestCase");
				suiteE.appendChild(tCaseE);
				tCaseE.setAttribute("Automation", tCase.getAutoName());
				tCaseE.setAttribute("ID", tCase.getTestCasealiasId());
				tCaseE.setAttribute("TestType", tCase.getTestTypeName());
				tCaseE.setAttribute("ResultId", Integer.toString(tCase.getResultId()));
				tCaseE.setAttribute("Name", tCase.getTestCaseName());
				tCaseE.setAttribute("Timeout", Integer.toString(tCase.getTimeout()));
				
				Element cofigElem = doc.createElement("ConfigFiles");

				Text configNode = createTextNode(doc, tCase.getConfigFiles());
				cofigElem.appendChild(configNode);
				Element funcElem = doc.createElement("testfunctioncall");
				Text funcNode = createTextNode(doc, tCase.getTestfunctionCall());
				funcElem.appendChild(funcNode);
				Element scriptElem = doc.createElement("TestScript");
				Text scriptNode = createTextNode(doc, tCase.getTestScript());
				scriptElem.appendChild(scriptNode);
				tCaseE.appendChild(cofigElem);
				tCaseE.appendChild(funcElem);
				tCaseE.appendChild(scriptElem);
			}
		}
		//Save generated XML content to file
		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		
		String file = desDir + File.separator + "subExecution.xml";
		Utils.writeXmlToFile(doc, file);
		FileInputStream fis = new FileInputStream(file);
		
		return fis;
	}

/**
 * Export selected test case to XML	
 * @param cases
 * @param context
 * @return
 * @throws Exception
 */
	public InputStream generateSelectedCaseToXml(String[] cases,int subExecutionId,ActionContext context) throws Exception {
		
		SubExecution subexecution =testExecutionDaoImpl.querySubExecutionById(subExecutionId);
		List<TestResult> units = testResultDaoImpl.listTestUnitsBySubExecutionId(subExecutionId);

		Document doc = Utils.getDocument();

		Element rootDom = doc.createElement("TestExecution");

		doc.appendChild(rootDom);
		rootDom.setAttribute("Name", subexecution.getExecutionName());
		rootDom.setAttribute("Project", subexecution.getProjectName());
		rootDom.setAttribute("ReleaseCycle", subexecution.getReleaseCycle());
		Element subExecutionE = doc.createElement("SubExecution");
		
		rootDom.appendChild(subExecutionE);
		subExecutionE.setAttribute("Name", subexecution.getSubExecutionName());
		subExecutionE.setAttribute("SubPlan", subexecution.getSubPlanName());
		subExecutionE.setAttribute("Platform", subexecution.getPlatFormName());
		subExecutionE.setAttribute("ExecutionOS", subexecution.getOsName());
		subExecutionE.setAttribute("subExecutionId", Integer.toString(subexecution.getSubExecutionId()));
		for(TestResult tUnit : units) {
			Element suiteE = doc.createElement("TestSuite");
			subExecutionE.appendChild(suiteE);
			suiteE.setAttribute("Name", tUnit.getSuiteName());
			suiteE.setAttribute("Target", tUnit.getTargetName());
			List<TestResult> cList = testResultDaoImpl.listTestResultBySubExecutionIdAndUnitId(subExecutionId,tUnit.getTestunitId());
			for(TestResult tCase : cList) {
				if(Arrays.asList(cases).contains(Integer.toString(tCase.getResultId()))) {
					Element tCaseE = doc.createElement("TestCase");
					suiteE.appendChild(tCaseE);
					tCaseE.setAttribute("Automation", tCase.getAutoName());
					tCaseE.setAttribute("ID", tCase.getTestCasealiasId());
					tCaseE.setAttribute("TestType", tCase.getTestTypeName());
					tCaseE.setAttribute("ResultId", Integer.toString(tCase.getResultId()));
					tCaseE.setAttribute("Name", tCase.getTestCaseName());
					tCaseE.setAttribute("Timeout", Integer.toString(tCase.getTimeout()));
					
					Element cofigElem = doc.createElement("ConfigFiles");

					Text configNode = createTextNode(doc, tCase.getConfigFiles());
					cofigElem.appendChild(configNode);
					Element funcElem = doc.createElement("testfunctioncall");
					Text funcNode = createTextNode(doc, tCase.getTestfunctionCall());
					funcElem.appendChild(funcNode);
					Element scriptElem = doc.createElement("TestScript");
					Text scriptNode = createTextNode(doc, tCase.getTestScript());
					scriptElem.appendChild(scriptNode);
					tCaseE.appendChild(cofigElem);
					tCaseE.appendChild(funcElem);
					tCaseE.appendChild(scriptElem);
				}
			}
		}
		//Save generated XML content to file
		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		
		String file = desDir + File.separator + "subExecution.xml";
		Utils.writeXmlToFile(doc, file);
		FileInputStream fis = new FileInputStream(file);
		
		return fis;
	}

	Text createTextNode(Document doc, String str) {
		if (str == null) {
			str = "";
		}
		return doc.createTextNode(str);
	}

/**
 * this is key function for analyze imported XML file and import result to DB.	
 * @param context
 * @param fileList
 * @param subExecutionId
 * @throws Exception
 */
	public void updateBatchTestResultForXML(ActionContext context, List<File> fileList) throws Exception {

		if (fileList.size() > 0) {

			for (File file : fileList) {
					subExecutionId = readTestResultXML(file, context);
				}

			context.put(Constant.SUCCESSMSG, "Import Finished!");
		}

		SubExecution subExecution= testExecutionDaoImpl.querySubExecutionById(subExecutionId);
		testExecutionDaoImpl.refreshSubExecution(subExecution);
		TestExecution testExecution= testExecutionDaoImpl.queryTestExecutionById(subExecution.getExecutionId());
		testExecutionDaoImpl.refreshTestExecution(testExecution);
					
	}	
	
	public int readTestResultXML(File file, ActionContext context) throws Exception {
    	String currentDate = Utils.dateFormat(new Date(), null);
    	
		Document doc = Utils.getDocument(file);
        Element root = doc.getDocumentElement();
        NodeList lists = root.getElementsByTagName("TestCase");
        
        for(int i=0;i<lists.getLength();i++)
        {  	
        	Element node = (Element)lists.item(i);
        	
        	//Parse parameter and save to testresult
        	TestResult testResult = new TestResult();
        	testResult = testResultDaoImpl.queryTestResult(Integer.parseInt(node.getAttribute("ResultId")));
        	subExecutionId = testResult.getSubExecutionId();
        	testResult.setResultTypeId(testResultDaoImpl.queryResultTypeByName(node.getAttribute("result")).getResultTypeId());
        	testResult.setLog(node.getAttribute("log"));
        	testResult.setComments(node.getAttribute("comment"));
        	testResult.setBugId(node.getAttribute("bugId"));
        	
        	//Update result track table
    		ResultTrack resultTrack = new ResultTrack();
    		resultTrack.setTestResultId(testResult.getResultId());
    		resultTrack.setResultTypeId(testResult.getResultTypeId());
    		resultTrack.setCreateDate(currentDate);
    		resultTrack.setModifyDate(currentDate);
    		int trackId = testResultDaoImpl.addResultTrack(resultTrack);
    	
    		//Update test result in testresult table
    		testResult.setResultTrackId(trackId);
    		testResultDaoImpl.updateTestRusultInSubExecution(testResult);
    		
        	if(node.getElementsByTagName("Performance").getLength() > 0) {
        		
            	NodeList perfs = node.getElementsByTagName("Performance");
            	Element perf = (Element) perfs.item(0);
            	NodeList items =  perf.getElementsByTagName("item");
            	
            	for(int j=0;j<items.getLength();j++) {
            		Element item = (Element)items.item(j);
            		
                    NamedNodeMap innerElmnt_gold_attr = item.getAttributes();
            		for (int k = 0; k < innerElmnt_gold_attr.getLength(); ++k)
            		{
            		    Node attr = innerElmnt_gold_attr.item(k);
            		    if(!"No".equalsIgnoreCase(attr.getNodeName())){
            		    	//Update performanceresult table
                    		PerformanceResult perfResult = new PerformanceResult();
                    		perfResult.setResultTrackId(trackId);
                    		perfResult.setTestResultId(testResult.getResultId());
                    		perfResult.setElementIndex(j+1);
                    		perfResult.setModifyDate(currentDate);
                    		perfResult.setCreateDate(currentDate);
                		    perfResult.setAttributeName(attr.getNodeName());
                		    perfResult.setAttributeValue(attr.getNodeValue());
                		    testResultDaoImpl.addPerformanceResult(perfResult);
            		    }
            		}
            	}
        	}
        }
        return subExecutionId;
    }
/**
	public SubTestPlan resolveSubTestPlanXml(File file) throws Exception {

		Document doc = Utils.getDocument(file);
		Element testUnitsElem = (Element) doc.getFirstChild();
		String project = testUnitsElem.getAttribute("Project");
		String releaseCycle = testUnitsElem.getAttribute("ReleaseCycle");
		String subPlanName = testUnitsElem.getAttribute("SubPlan");
		// System.out.println(project + ReleaseCycle + SubPlan);
		String modifyDate = Utils.dateFormat(new Date(), null);
		TestPlan testPlan = testPlanDaoImpl.queryTestPlan(releaseCycle);
		SubTestPlan subPlan = planMapDaoImpl.querySubTestPlan(testPlan
				.getTestPlanId(), subPlanName);

		List<TestUnit> testUnitList = new ArrayList<TestUnit>();
		List<TestPlanTrack> trackList = subPlan.getTrackList();
		NodeList testUnitNodeList = doc.getElementsByTagName("TestUnit");

		for (int i = 0; i < testUnitNodeList.getLength(); i++) {

			int pass = 0;
			int fail = 0;
			int notRun = 0;
			int block = 0;
			int totalCases = 0;
			TestPlanTrack track = new TestPlanTrack();
			Element testUnitElement = (Element) testUnitNodeList.item(i);
			String testUnitName = testUnitElement.getAttribute("Name");
			// System.out.println(name);
			TestUnit testUnit = testUnitDaoImpl.queryTestUnit(testUnitName,
					testPlan.getTestPlanId());
			track.setTestPlanId(testUnit.getTestPlanId());
			track.setSubPlanId(testUnit.getSubPlanId());
			track.setTestUnitId(testUnit.getTestUnitId());
			List<TestResult> testResultList = testResultDaoImpl
					.queryTestResults(testUnit.getTestUnitId());
			Map<Integer, TestResult> resultMap = new HashMap<Integer, TestResult>();
			for (TestResult testResult : testResultList) {

				resultMap.put(testResult.getTestCaseId(), testResult);
			}
			NodeList testCaseNodeList = testUnitElement
					.getElementsByTagName("TestCase");
			totalCases = testCaseNodeList.getLength();
			track.setTotalCases(totalCases);
			for (int j = 0; j < testCaseNodeList.getLength(); j++) {
				Element testcaseElement = (Element) testCaseNodeList.item(j);
				// String testcaseName = testcaseElement.getAttribute("Name");
				String testcaseId = testcaseElement.getAttribute("ID");
				if (testcaseId == null) {
					continue;
				}
				TestCase testCase = testCaseDaoImpl.queryTestCase(testcaseId,
						project);
				String log = testcaseElement.getAttribute("log");
				String result = testcaseElement.getAttribute("result");
				TestResult testResult = resultMap.get(testCase.getTestCaseId());
				testResult.setLog(log);
				testResult.setModifyDate(modifyDate);
				if (result == null) {

					testResult.setResultTypeId(Constant.RESULT_NONE);
				} else if (result.equalsIgnoreCase("pass")) {
					pass++;
					testResult.setResultTypeId(Constant.RESULT_PASS);
				} else if (result.equalsIgnoreCase("fail")) {
					fail++;
					testResult.setResultTypeId(Constant.RESULT_FAIL);
				} else if (result.equalsIgnoreCase("not run")) {
					notRun++;
					testResult.setResultTypeId(Constant.RESULT_NOTRUN);
				} else if (result.equalsIgnoreCase("block")) {
					block++;
					testResult.setResultTypeId(Constant.RESULT_BLOCK);
				} else {
					testResult.setResultTypeId(Constant.RESULT_NONE);
				}

			}
			track.setPass(pass);
			track.setFail(fail);
			track.setNotRun(notRun);
			track.setBlock(block);
			track.setCreateDate(modifyDate);
			testUnit.setTestResultList(testResultList);
			testUnitList.add(testUnit);
			trackList.add(track);

		}
		subPlan.setTestUnitList(testUnitList);

		return subPlan;
	}
**/
	
/**
	public TestExecution resolveXmlToTestExecution(File file) throws Exception {

		Document doc = Utils.getDocument(file);
		Element testUnitsElem = (Element) doc.getFirstChild();
		String project = testUnitsElem.getAttribute("Project");
		String releaseCycle = testUnitsElem.getAttribute("ReleaseCycle");
		String subPlanName = testUnitsElem.getAttribute("SubPlan");
		// System.out.println(project + ReleaseCycle + SubPlan);
		String modifyDate = Utils.dateFormat(new Date(), null);
		TestPlan testPlan = testPlanDaoImpl.queryTestPlan(releaseCycle);
		SubTestPlan subPlan = planMapDaoImpl.querySubTestPlan(testPlan
				.getTestPlanId(), subPlanName);

		List<TestUnit> testUnitList = new ArrayList<TestUnit>();
		List<TestPlanTrack> trackList = subPlan.getTrackList();
		NodeList testUnitNodeList = doc.getElementsByTagName("TestUnit");

		for (int i = 0; i < testUnitNodeList.getLength(); i++) {

			int pass = 0;
			int fail = 0;
			int notRun = 0;
			int block = 0;
			int totalCases = 0;
			TestPlanTrack track = new TestPlanTrack();
			Element testUnitElement = (Element) testUnitNodeList.item(i);
			String testUnitName = testUnitElement.getAttribute("Name");
			// System.out.println(name);
			TestUnit testUnit = testUnitDaoImpl.queryTestUnit(testUnitName,
					testPlan.getTestPlanId());
			track.setTestPlanId(testUnit.getTestPlanId());
			track.setSubPlanId(testUnit.getSubPlanId());
			track.setTestUnitId(testUnit.getTestUnitId());
			List<TestResult> testResultList = testResultDaoImpl
					.queryTestResults(testUnit.getTestUnitId());
			Map<Integer, TestResult> resultMap = new HashMap<Integer, TestResult>();
			for (TestResult testResult : testResultList) {

				resultMap.put(testResult.getTestCaseId(), testResult);
			}
			NodeList testCaseNodeList = testUnitElement
					.getElementsByTagName("TestCase");
			totalCases = testCaseNodeList.getLength();
			track.setTotalCases(totalCases);
			for (int j = 0; j < testCaseNodeList.getLength(); j++) {
				Element testcaseElement = (Element) testCaseNodeList.item(j);
				// String testcaseName = testcaseElement.getAttribute("Name");
				String testcaseId = testcaseElement.getAttribute("ID");
				if (testcaseId == null) {
					continue;
				}
				TestCase testCase = testCaseDaoImpl.queryTestCase(testcaseId,
						project);
				String log = testcaseElement.getAttribute("log");
				String result = testcaseElement.getAttribute("result");
				TestResult testResult = resultMap.get(testCase.getTestCaseId());
				testResult.setLog(log);
				testResult.setModifyDate(modifyDate);
				if (result == null) {

					testResult.setResultTypeId(Constant.RESULT_NONE);
				} else if (result.equalsIgnoreCase("pass")) {
					pass++;
					testResult.setResultTypeId(Constant.RESULT_PASS);
				} else if (result.equalsIgnoreCase("fail")) {
					fail++;
					testResult.setResultTypeId(Constant.RESULT_FAIL);
				} else if (result.equalsIgnoreCase("not run")) {
					notRun++;
					testResult.setResultTypeId(Constant.RESULT_NOTRUN);
				} else if (result.equalsIgnoreCase("block")) {
					block++;
					testResult.setResultTypeId(Constant.RESULT_BLOCK);
				} else {
					testResult.setResultTypeId(Constant.RESULT_NONE);
				}

			}
			track.setPass(pass);
			track.setFail(fail);
			track.setNotRun(notRun);
			track.setBlock(block);
			track.setCreateDate(modifyDate);
			testUnit.setTestResultList(testResultList);
			testUnitList.add(testUnit);
			trackList.add(track);

		}
		subPlan.setTestUnitList(testUnitList);

		return null;
	}
**/
	public SubTestPlanDaoImpl getSubTestPlanDaoImpl() {
		return subTestPlanDaoImpl;
	}

	public void setSubTestPlanDaoImpl(SubTestPlanDaoImpl subTestPlanDaoImpl) {
		this.subTestPlanDaoImpl = subTestPlanDaoImpl;
	}

	public TestUnitDaoImpl getTestUnitDaoImpl() {
		return testUnitDaoImpl;
	}

	public void setTestUnitDaoImpl(TestUnitDaoImpl testUnitDaoImpl) {
		this.testUnitDaoImpl = testUnitDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
	}

	public PlanMapDaoImpl getPlanMapDaoImpl() {
		return planMapDaoImpl;
	}

	public void setPlanMapDaoImpl(PlanMapDaoImpl planMapDaoImpl) {
		this.planMapDaoImpl = planMapDaoImpl;
	}

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	public TestCaseDaoImpl getTestCaseDaoImpl() {
		return testCaseDaoImpl;
	}

	public void setTestCaseDaoImpl(TestCaseDaoImpl testCaseDaoImpl) {
		this.testCaseDaoImpl = testCaseDaoImpl;
	}

	public TestTypeDaoImpl getTestTypeDaoImpl() {
		return testTypeDaoImpl;
	}

	public void setTestTypeDaoImpl(TestTypeDaoImpl testTypeDaoImpl) {
		this.testTypeDaoImpl = testTypeDaoImpl;
	}

	public TestExecutionDaoImpl getTestExecutionDaoImpl() {
		return testExecutionDaoImpl;
	}

	public void setTestExecutionDaoImpl(TestExecutionDaoImpl testExecutionDaoImpl) {
		this.testExecutionDaoImpl = testExecutionDaoImpl;
	}

	public int getSubExecutionId() {
		return subExecutionId;
	}
	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}
}
