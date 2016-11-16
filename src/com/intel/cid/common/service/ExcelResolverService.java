package com.intel.cid.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.ResultTrack;
import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.ComponentDaoImpl;
import com.intel.cid.common.dao.impl.FeatureDaoImpl;
import com.intel.cid.common.dao.impl.OSDaoImpl;
import com.intel.cid.common.dao.impl.ProjectDaoImpl;
import com.intel.cid.common.dao.impl.SubComponentDaoImpl;
import com.intel.cid.common.dao.impl.TestExecutionDaoImpl;
import com.intel.cid.common.dao.impl.TeamDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.TestResultDaoImpl;
import com.intel.cid.common.dao.impl.TestTypeDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class ExcelResolverService {

	private static Logger logger = Logger.getLogger(ExcelResolverService.class);

	private List<File> resovleFileList = new ArrayList<File>();

	private Date date = new Date();

	private int insertSqlCount;

	private int updateSqlCount;
	
	private int subExecutionId;

	private int delSqlCount;

	private int excelRowCount;
	
	private String parseLog;
	
	private String successLog;
	
	private JdbcTemplate userJdbcTemplate;

	private TestTypeDaoImpl testTypeDaoImpl;

	private TeamDaoImpl teamDaoImpl;

	private OSDaoImpl oSDaoImpl;

	private ComponentDaoImpl componentDaoImpl;

	private ProjectDaoImpl projectDaoImpl;

	private SubComponentDaoImpl subComponentDaoImpl;

	private UserDaoImpl userDaoImpl;

	private FeatureDaoImpl featureDaoImpl;
	
	private TestExecutionDaoImpl testExecutionDaoImpl;

	private TestCaseDaoImpl testCaseDaoImpl;

	private TestResultDaoImpl testResultDaoImpl;

	public TestResultDaoImpl getTestResultDaoImpl() {
		return testResultDaoImpl;
	}

	public void setTestResultDaoImpl(TestResultDaoImpl testResultDaoImpl) {
		this.testResultDaoImpl = testResultDaoImpl;
	}

	private static Map<String, Integer> testTypeMap = new HashMap<String, Integer>();

	private static Map<String, Integer> projectformMap = new HashMap<String, Integer>();
	private static Map<String, Integer> teamMap = new HashMap<String, Integer>();
	private static Map<String, Integer> osMap = new HashMap<String, Integer>();
	private static Map<String, Integer> compMap = new HashMap<String, Integer>();
	private static Map<String, Integer> subCompMap = new HashMap<String, Integer>();
	private static Map<String, Integer> userMap = new HashMap<String, Integer>();
	private static Map<String, Integer> featureMap = new HashMap<String, Integer>();
	private static Map<String, Integer> autoMap = new HashMap<String, Integer>();
	private static Map<String, Integer> caseStateMap = new HashMap<String, Integer>();
	private static Map<String, Integer> versionMap = new HashMap<String, Integer>();
	private static Map<String, Integer> resultTypeMap = new HashMap<String, Integer>();

	private static Map<Integer, String> etestTypeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eProjectformMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eteamMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eosMap = new HashMap<Integer, String>();
	private static Map<Integer, String> ecompMap = new HashMap<Integer, String>();
	private static Map<Integer, String> esubCompMap = new HashMap<Integer, String>();
	private static Map<Integer, String> euserMap = new HashMap<Integer, String>();
	private static Map<Integer, String> efeatureMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eautoMap = new HashMap<Integer, String>();
	private static Map<Integer, String> ecaseStateMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eversionMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eresultTypeMap = new HashMap<Integer, String>();

	public void miniSetUp() throws Exception {
		List<User> userList = userDaoImpl.queryAllUsers();
		for (User user : userList) {
			userMap.put(user.getUserName(), user.getUserId());
			euserMap.put(user.getUserId(), user.getUserName());
		}

		List<ResultType> resultTypeList = testResultDaoImpl.listResultTypes();

		for (ResultType rt : resultTypeList) {
			resultTypeMap.put(rt.getResultTypeName(), rt.getResultTypeId());
			eresultTypeMap.put(rt.getResultTypeId(), rt.getResultTypeName());
		}
	}

	public void setUp() throws Exception {

		List<TestType> testTypeList = testTypeDaoImpl.queryAllTestTypes();

		for (TestType testType : testTypeList) {
			testTypeMap.put(testType.getTypeName(), testType.getTypeId());
			etestTypeMap.put(testType.getTypeId(), testType.getTypeName());
		}

		/*
		 * List<Platform> platformList = platformDaoImpl.listAllPlatforms(); for
		 * (Platform platform : platformList) {
		 * 
		 * platformMap.put(platform.getPlatformName(), platform
		 * .getPlatformId()); eplatformMap.put(platform.getPlatformId(),
		 * platform .getPlatformName()); }
		 */

		List<Project> projectformList = projectDaoImpl.queryAllProjects();
		for (Project pro : projectformList) {

			projectformMap.put(pro.getProjectName(), pro.getProjectId());
			eProjectformMap.put(pro.getProjectId(), pro.getProjectName());
		}

		List<Team> teamList = teamDaoImpl.queryAllTeams();
		for (Team team : teamList) {
			teamMap.put(team.getTeamName(), team.getTeamId());
			eteamMap.put(team.getTeamId(), team.getTeamName());
		}

		List<OS> osList = oSDaoImpl.queryAllOSs();
		for (OS os : osList) {
			osMap.put(os.getOsName(), os.getOsId());
			eosMap.put(os.getOsId(), os.getOsName());
		}

		List<Component> componentList = componentDaoImpl.queryAllComponents();

		for (Component component : componentList) {
			compMap.put(component.getCompName(), component.getCompId());
			ecompMap.put(component.getCompId(), component.getCompName());
		}

		List<SubComponent> subcComponentList = subComponentDaoImpl
				.queryAllSubComponents();

		for (SubComponent component : subcComponentList) {
			subCompMap
					.put(component.getSubCompName(), component.getSubCompId());
			esubCompMap.put(component.getSubCompId(), component
					.getSubCompName());
		}

		List<User> userList = userDaoImpl.queryAllUsers();
		for (User user : userList) {
			userMap.put(user.getUserName(), user.getUserId());
			euserMap.put(user.getUserId(), user.getUserName());
		}

		List<Feature> featureList = featureDaoImpl.listAllFeatures();
		for (Feature feature : featureList) {
			featureMap.put(feature.getFeatureName(), feature.getFeatureId());
			efeatureMap.put(feature.getFeatureId(), feature.getFeatureName());
		}

		List<Automation> automationList = testCaseDaoImpl.queryAllAutomations();
		for (Automation automation : automationList) {
			autoMap.put(automation.getAutoName(), automation.getAutoId());
			eautoMap.put(automation.getAutoId(), automation.getAutoName());
		}

		List<CaseState> caseStateList = testCaseDaoImpl.queryAllCaseStates();
		for (CaseState caseState : caseStateList) {
			caseStateMap.put(caseState.getCaseStateName(), caseState
					.getCaseStateId());

			ecaseStateMap.put(caseState.getCaseStateId(), caseState
					.getCaseStateName());
		}

		List<VersionState> versionStateList = testCaseDaoImpl
				.queryAllVersions();
		for (VersionState versionState : versionStateList) {
			versionMap.put(versionState.getVersionName(), versionState
					.getVersionId());
			eversionMap.put(versionState.getVersionId(), versionState
					.getVersionName());
		}
	}

	public void updateBatchTestCaseForXLS(ActionContext context,
			List<File> fileList) throws Exception {
		setUp();

		if (fileList.size() > 0) {

			for (File file : fileList) {
				readTestCaseXlsx(file,context);
			}

			context.put(Constant.ERRORMSG, 
			"<div style='text-align:center'>-------------Test case operation error log--------------</div></br>"
			+parseLog);
			context.put(Constant.SUCCESSMSG, 
			"<div style='text-align:center'>-------------Test case operation success log--------------</div></br>"
			+successLog);

		}

	}
	
	public void updateBatchTestResultForXLS(ActionContext context,
			List<File> fileList, int subExecutionId) throws Exception {
		setUp();

		if (fileList.size() > 0) {

			for (File file : fileList) {
					readTestResultXlsx(file, context);
				}

			context.put(Constant.SUCCESSMSG, "Import finished: Please close cuurent page and refrech sub execution page to check result<br/>");
		}

		SubExecution subExecution= testExecutionDaoImpl.querySubExecutionById(subExecutionId);
		testExecutionDaoImpl.refreshSubExecution(subExecution);
		TestExecution testExecution= testExecutionDaoImpl.queryTestExecutionById(subExecution.getExecutionId());
		testExecutionDaoImpl.refreshTestExecution(testExecution);
					
	}
	
	public void updateBatchPerformanceResultByExcel(ActionContext context,
			List<File> fileList, int subExecutionId) throws Exception {
		setUp();

		if (fileList.size() > 0) {

			for (File file : fileList) {
				readPerformanceResultXlsx(file, context);
			}
		}

		SubExecution subExecution= testExecutionDaoImpl.querySubExecutionById(subExecutionId);
		testExecutionDaoImpl.refreshSubExecution(subExecution);
		TestExecution testExecution= testExecutionDaoImpl.queryTestExecutionById(subExecution.getExecutionId());
		testExecutionDaoImpl.refreshTestExecution(testExecution);
					
	}
	
	public void readPerformanceResultXlsx(File file, ActionContext context) throws Exception {
		
    	List<TestResult> testResult = new ArrayList<TestResult>();
		//Define default supported column name and save to array Title
		String Title[] = new String[30];
		Title[0] = "Operation";
		Title[1] = "TestCaseName";
		Title[2] = "ResultId";
		Title[3] = "ResultTrackId";
		Title[4] = "Item";
		Title[5] = "ResultType";
		Title[6] = "BugId";
		Title[7] = "BugName";
		Title[8] = "Comment";
		Title[9] = "Log";
		Title[10] = "Target";
		Map<String, String> headLists = new HashMap<String, String>();

        Workbook wb = new XSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println(wb.getSheetName(i));
            int count = 0;
            for (Row row : sheet) {
            	TestResult result = new TestResult();
                if(count == 0) {
                	for (Cell cell : row) {
                		if(!"".equals(cell.toString().trim())) {
                			headLists.put(cell.toString().trim(), cell.toString().trim());
                		}
                	}
                } else {
                	for (Cell cell : row) {
                		if("Operation".equals(cell.toString().trim())) {
                			headLists.put(cell.toString().trim(), cell.toString().trim());
                		}
                	}               		
                }
                count++;
            }
        }
        
        for (Map.Entry<String, String> head : headLists.entrySet())
        {
        	System.out.println("======="+head.getKey()+"=======");
        }
    }			

	public void readXls(File file, ActionContext context) throws Exception {
		logger.info("file" + file);
		InputStream is = new FileInputStream(file);
		String path = Utils.getProjectPath(context) + "test";
		File desDir = new File(path);
		if (!desDir.exists()) {
			desDir.mkdir();
		}
		OutputStream insertDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "insert_testcase.sql"),
						true));
		OutputStream delDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "del_testcase.sql"), true));
		OutputStream updateDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "upate_testcase.sql"), true));
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			excelRowCount += hssfSheet.getLastRowNum();
			StringBuilder commentBuilder = new StringBuilder();

			commentBuilder.append("/*===========" + file.getName() + "======"
					+ new Date() + "===========*/");
			commentBuilder.append("\r\n");
			commentBuilder.append("/*==========================="
					+ "total records:" + hssfSheet.getLastRowNum()
					+ "===========================*/");
			commentBuilder.append("\r\n");
			// write comments into insert sql file
			insertDestOut.write(commentBuilder.toString().getBytes());
			// write comments into del sql file
			delDestOut.write(commentBuilder.toString().getBytes());
			if (hssfSheet == null) {
				continue;
			}
			logger.info("sheet number--->:" + numSheet);
			// all exist column label name
			List<String> colums = new ArrayList<String>();
			// add column's name to sql .just generate once
			StringBuilder insertRowtemplateBuilder = new StringBuilder(
					"insert into testcase(");
			StringBuilder updateRowtemplateBuilder = new StringBuilder(
					"update testcase set ");
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);

				// update condition.
				StringBuilder whereClause = new StringBuilder();

				// if need to be updated.
				boolean needUpdated = false;
				if (rowNum == 0) {
					if (hssfRow == null) {
						break;
					}
				}
				// add column's name and value to sql .
				StringBuilder insertColumBuilder = new StringBuilder(
						insertRowtemplateBuilder);
				// add update column's value to sql
				StringBuilder updateColumBuilder = new StringBuilder(
						updateRowtemplateBuilder);

				for (int cellNum = 0; cellNum < hssfSheet.getRow(0)
						.getLastCellNum(); cellNum++) {
					if (hssfRow == null) {
						continue;
					}
					HSSFCell xssfCell = hssfRow.getCell(cellNum);

					// obtain all column names
					if (rowNum == 0) {

						if (xssfCell == null) {
							colums.add(null);
							continue;
						} else {
							String cellValue = getXLSValue(xssfCell);

							if (Utils.isNullORWhiteSpace(cellValue)) {
								colums.add(null);
								continue;
							}

							if (cellValue.equalsIgnoreCase("XMS-ID")
									|| cellValue.equalsIgnoreCase("modules")
									|| cellValue
											.equalsIgnoreCase("platformsToTestOn")) {

								colums.add(cellValue.toUpperCase());
								continue;
							}

							if (cellValue.equalsIgnoreCase("procedure")) {

								cellValue = "executionsteps";

							}

							if (cellValue.equalsIgnoreCase("testcaseid")) {
								// continue;
								cellValue = "testcasealiasid";

							}
							if (cellValue.equalsIgnoreCase("headline")) {
								cellValue = "testcasename";
							}

							if (cellValue.equalsIgnoreCase("testtype")) {
								cellValue = "testtypeid";
							}

							if (cellValue.equalsIgnoreCase("team")) {
								cellValue = "teamid";
							}
							/*
							 * if (cellValue.equalsIgnoreCase("platform")) {
							 * cellValue = "platformid"; }
							 */
							if (cellValue.equalsIgnoreCase("feature")) {
								cellValue = "featureid";
							}
							if (cellValue.equalsIgnoreCase("component")) {
								cellValue = "compid";
							}
							if (cellValue.equalsIgnoreCase("automation")) {
								cellValue = "autoid";
							}
							if (cellValue.equalsIgnoreCase("os")) {
								cellValue = "osid";
							}
							if (cellValue.equalsIgnoreCase("owner")) {
								cellValue = "userid";
							}
							if (cellValue.equalsIgnoreCase("version")) {
								cellValue = "versionid";
							}
							if (cellValue.equalsIgnoreCase("state")) {
								cellValue = "casestateid";
							}
							if (cellValue.equalsIgnoreCase("subcomponent")) {
								cellValue = "subcompid";
							}
							insertRowtemplateBuilder.append(cellValue
									.toUpperCase());
							colums.add(cellValue.toUpperCase());

							if (cellNum != hssfSheet.getRow(0).getLastCellNum() - 1) {

								insertRowtemplateBuilder.append(" ,");

							} else {
								insertRowtemplateBuilder.append(") values(");

							}
						}

					}

					if (rowNum > 0) {
						if (hssfRow == null) {
							continue;
						}
						String label = colums.get(cellNum);

						if (label == null) {
							continue;
						}
						if (xssfCell == null) {
							if (label.equalsIgnoreCase("createDate")) {

								if (!needUpdated) {

									insertColumBuilder.append("'"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",CREATEDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									continue;
								}

							} else if (label.equalsIgnoreCase("modifyDate")) {

								if (needUpdated) {
									//
									// updateColumBuilder = updateColumBuilder
									// .deleteCharAt(updateColumBuilder
									// .lastIndexOf(","));
									updateColumBuilder.append(label + "='"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",MODIFYDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									int commaPos = insertColumBuilder
											.lastIndexOf(",");

									insertColumBuilder = insertColumBuilder
											.deleteCharAt(commaPos);
									// continue;
								}

							} else {

								insertColumBuilder.append("NULL");
								updateColumBuilder.append(label + "=NULL");
							}
							if (cellNum != hssfSheet.getRow(0).getLastCellNum() - 1) {
								insertColumBuilder.append(" , ");
								updateColumBuilder.append(" , ");
							} else {
								insertColumBuilder.append(");");
								insertColumBuilder.append("\r\n\n");
								updateColumBuilder.append(whereClause
										.toString());
								updateColumBuilder.append("\r\n\n");
								logger.info(insertColumBuilder.toString());
								logger.info(updateColumBuilder.toString());
								byte[] bytes = insertColumBuilder.toString()
										.getBytes();

								// add insert sql to file.
								insertDestOut.write(bytes);
								// add update sql to file.
								updateDestOut.write(updateColumBuilder
										.toString().getBytes());
								if (needUpdated) {
									int res = userJdbcTemplate
											.update(updateColumBuilder
													.toString());
									if (res == 1) {
										updateSqlCount++;
									}
								}
								if (!needUpdated) {
									int result = userJdbcTemplate
											.update(insertColumBuilder
													.toString());
									if (result == 1) {
										insertSqlCount++;
									}
								}
							}
							continue;
						}
						String cellValue = getXLSValue(xssfCell);
						if (cellValue != null) {

							if (label.equalsIgnoreCase("XMS-ID")
									|| label.equalsIgnoreCase("modules")
									|| label
											.equalsIgnoreCase("platformsToTestOn")) {

								continue;
							} else if (label
									.equalsIgnoreCase("testcasealiasid")) {

								whereClause.append(" where testcasealiasid="
										+ "'" + cellValue + "' ");

							}

							else if (label.equalsIgnoreCase("projectid")) {

								whereClause.append(" and projectid='"
										+ cellValue + "' ;");

								StringBuilder delSqlBuilder = new StringBuilder();
								delSqlBuilder.append("delete from testcase "
										+ whereClause.toString());

								int totalNum = userJdbcTemplate
										.queryForInt("select count(*) from testcase"
												+ whereClause.toString());
								if (totalNum > 0) {
									needUpdated = true;
								}

								delSqlBuilder.append(";");
								delSqlBuilder.append("\r\n\n");
								delDestOut.write(delSqlBuilder.toString()
										.getBytes());
								delSqlCount++;
								logger.info(delSqlBuilder.toString());
							}

							if (label.equalsIgnoreCase("timeout")) {

								insertColumBuilder.append(Float.valueOf(
										cellValue).intValue());
								updateColumBuilder.append(label + "="
										+ Float.valueOf(cellValue).intValue());
							} else if (label.equalsIgnoreCase("testtypeid")) {
								Integer value = testTypeMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("PROJECTID")) {
								Integer value = projectformMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("TEAMID")) {
								Integer value = teamMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("OSID")) {
								Integer value = osMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("COMPID")) {
								Integer value = compMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("SUBCOMPID")) {
								Integer value = subCompMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("USERID")) {
								Integer value = userMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("FEATUREID")) {
								Integer value = featureMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("AUTOID")) {
								Integer value = autoMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("versionid")) {
								Integer value = versionMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("casestateid")) {
								Integer value = caseStateMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("createDate")) {

								if (!needUpdated) {
									cellValue = Utils.dateFormat(date, null);
									insertColumBuilder.append("'" + cellValue
											+ "'");
								} else {

									int pos = insertColumBuilder
											.indexOf(",CREATEDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									continue;
								}

							} else if (label.equalsIgnoreCase("modifyDate")) {

								if (needUpdated) {

									// updateColumBuilder = updateColumBuilder
									// .deleteCharAt(updateColumBuilder
									// .lastIndexOf(","));
									updateColumBuilder.append(label + "='"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",MODIFYDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									continue;
								}

							}

							else {
								if (Utils.isNullORWhiteSpace(cellValue)) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {

									insertColumBuilder.append("'"
											+ cellValue.replace("'", "&#039")
											+ "'");
									updateColumBuilder.append(label + "='"
											+ cellValue.replace("'", "&#039")
											+ "'");
								}
							}

						} else {
							if (label.equalsIgnoreCase("TESTCASEALIASID")) {
								break;
							}
							insertColumBuilder.append("NULL");
							updateColumBuilder.append(label + "=NULL");

						}

						if (cellNum != hssfSheet.getRow(0).getLastCellNum() - 1) {
							insertColumBuilder.append(" , ");
							updateColumBuilder.append(" , ");
						} else {

							insertColumBuilder.append(");");
							insertColumBuilder.append("\r\n\n");
							updateColumBuilder.append(whereClause);
							updateColumBuilder.append("\r\n\n");
							logger.info(insertColumBuilder.toString());
							logger.info(updateColumBuilder.toString());
							byte[] bytes = insertColumBuilder.toString()
									.getBytes();
							// out.write(bytes);
							insertDestOut.write(bytes);
							updateDestOut.write(updateColumBuilder.toString()
									.getBytes());
							if (needUpdated) {
								int res = userJdbcTemplate
										.update(updateColumBuilder.toString());
								if (res == 1) {
									updateSqlCount++;
								}
							}
							if (!needUpdated) {
								int result = userJdbcTemplate
										.update(insertColumBuilder.toString());
								if (result == 1) {
									insertSqlCount++;
								}
							}
						}

					}

					logger.info("rowNum:--->:" + rowNum + " cellNum--->:"
							+ cellNum + "  the CellValue--->:"
							+ getXLSValue(xssfCell));

				}

			}

		}

		is.close();
		insertDestOut.close();
		delDestOut.close();
		updateDestOut.close();

	}

	private String getXLSValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return String.valueOf(hssfCell.getStringCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			return String.valueOf(hssfCell.getCellFormula());
		}

		else {
			return null;
		}
	}

	public void readXlsx(File file, ActionContext context) throws IOException {
		logger.info("file:" + file);
		InputStream is = new FileInputStream(file);
		String path = Utils.getProjectPath(context) + "test";
		File desDir = new File(path);
		if (!desDir.exists()) {
			desDir.mkdir();
		}
		OutputStream insertDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "insert_testcase.sql"),
						true));
		OutputStream delDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "del_testcase.sql"), true));
		OutputStream updateDestOut = new BufferedOutputStream(
				new FileOutputStream(new File(Utils.getProjectPath(context)
						+ "test" + File.separator + "upate_testcase.sql"), true));
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			excelRowCount += xssfSheet.getLastRowNum();
			StringBuilder commentBuilder = new StringBuilder();

			commentBuilder.append("/*===========" + file.getName() + "======"
					+ new Date() + "===========*/");
			commentBuilder.append("\r\n");
			commentBuilder.append("/*==========================="
					+ "total records:" + xssfSheet.getLastRowNum()
					+ "===========================*/");
			commentBuilder.append("\r\n");
			// write comments into insert sql file
			insertDestOut.write(commentBuilder.toString().getBytes());
			// write comments into del sql file
			delDestOut.write(commentBuilder.toString().getBytes());
			if (xssfSheet == null) {
				continue;
			}
			logger.info("sheet number--->:" + numSheet);
			// all exist column label name
			List<String> colums = new ArrayList<String>();
			// add column's name to sql .just generate once
			StringBuilder insertRowtemplateBuilder = new StringBuilder(
					"insert into testcase(");
			StringBuilder updateRowtemplateBuilder = new StringBuilder(
					"update testcase set ");
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);

				// update condition.
				StringBuilder whereClause = new StringBuilder();
				// if need to be updated.
				boolean needUpdated = false;
				if (rowNum == 0) {
					if (xssfRow == null) {
						break;
					}
				}
				// add column's name and value to sql .
				StringBuilder insertColumBuilder = new StringBuilder(
						insertRowtemplateBuilder);
				// add update column's value to sql
				StringBuilder updateColumBuilder = new StringBuilder(
						updateRowtemplateBuilder);

				for (int cellNum = 0; cellNum < xssfSheet.getRow(0)
						.getLastCellNum(); cellNum++) {
					if (xssfRow == null) {
						continue;
					}
					XSSFCell xssfCell = xssfRow.getCell(cellNum);

					// obtain all column names
					if (rowNum == 0) {

						if (xssfCell == null) {
							colums.add(null);
							continue;
						} else {
							String cellValue = getXLSXValue(xssfCell);

							if (Utils.isNullORWhiteSpace(cellValue)) {
								colums.add(null);
								continue;
							}

							if (cellValue.equalsIgnoreCase("XMS-ID")
									|| cellValue.equalsIgnoreCase("modules")
									|| cellValue
											.equalsIgnoreCase("platformsToTestOn")) {

								colums.add(cellValue.toUpperCase());
								continue;
							}
							if (cellValue.equalsIgnoreCase("project")) {
								cellValue = "projectid";
							}
							if (cellValue.equalsIgnoreCase("procedure")) {

								cellValue = "executionsteps";

							}

							if (cellValue.equalsIgnoreCase("testcaseid")) {
								// continue;
								cellValue = "testcasealiasid";

							}
							if (cellValue.equalsIgnoreCase("headline")) {
								cellValue = "testcasename";
							}

							if (cellValue.equalsIgnoreCase("testtype")) {
								cellValue = "testtypeid";
							}

							if (cellValue.equalsIgnoreCase("team")) {
								cellValue = "teamid";
							}
							/*
							 * if (cellValue.equalsIgnoreCase("platform")) {
							 * cellValue = "platformid"; }
							 */
							if (cellValue.equalsIgnoreCase("feature")) {
								cellValue = "featureid";
							}
							if (cellValue.equalsIgnoreCase("component")) {
								cellValue = "compid";
							}
							if (cellValue.equalsIgnoreCase("automation")) {
								cellValue = "autoid";
							}
							if (cellValue.equalsIgnoreCase("os")) {
								cellValue = "osid";
							}
							if (cellValue.equalsIgnoreCase("owner")) {
								cellValue = "userid";
							}
							if (cellValue.equalsIgnoreCase("version")) {
								cellValue = "versionid";
							}
							if (cellValue.equalsIgnoreCase("state")) {
								cellValue = "casestateid";
							}
							if (cellValue.equalsIgnoreCase("subcomponent")) {
								cellValue = "subcompid";
							}
							insertRowtemplateBuilder.append(cellValue
									.toUpperCase());
							colums.add(cellValue.toUpperCase());

							if (cellNum != xssfSheet.getRow(0).getLastCellNum() - 1) {

								insertRowtemplateBuilder.append(" ,");

							} else {
								insertRowtemplateBuilder.append(") values(");

							}
						}

					}

					if (rowNum > 0) {
						if (xssfRow == null) {
							continue;
						}
						String label = colums.get(cellNum);

						if (label == null) {
							continue;
						}

						if (xssfCell == null) {

							if (label.equalsIgnoreCase("createDate")) {

								if (!needUpdated) {

									insertColumBuilder.append("'"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",CREATEDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									continue;
								}

							} else if (label.equalsIgnoreCase("modifyDate")) {

								if (needUpdated) {

									// updateColumBuilder = updateColumBuilder
									// .deleteCharAt(updateColumBuilder
									// .lastIndexOf(","));
									updateColumBuilder.append(label + "='"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",MODIFYDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									int commaPos = insertColumBuilder
											.lastIndexOf(",");

									insertColumBuilder = insertColumBuilder
											.deleteCharAt(commaPos);
									// continue;
								}

							} else {

								insertColumBuilder.append("NULL");
								updateColumBuilder.append(label + "=NULL");
							}

							if (cellNum != xssfSheet.getRow(0).getLastCellNum() - 1) {
								insertColumBuilder.append(" , ");
								updateColumBuilder.append(" , ");
							} else {
								insertColumBuilder.append(");");
								insertColumBuilder.append("\r\n\n");
								updateColumBuilder.append(whereClause
										.toString());
								updateColumBuilder.append("\r\n\n");
								logger.info(insertColumBuilder.toString());
								logger.info(updateColumBuilder.toString());
								byte[] bytes = insertColumBuilder.toString()
										.getBytes();

								// add insert sql to file.
								insertDestOut.write(bytes);
								// add update sql to file.
								updateDestOut.write(updateColumBuilder
										.toString().getBytes());
								if (needUpdated) {
									int res = userJdbcTemplate
											.update(updateColumBuilder
													.toString());
									if (res == 1) {
										updateSqlCount++;
									}
								}
								if (!needUpdated) {
									int result = userJdbcTemplate
											.update(insertColumBuilder
													.toString());
									if (result == 1) {
										insertSqlCount++;
									}
								}
							}
							continue;
						}
						String cellValue = getXLSXValue(xssfCell);
						if (cellValue != null) {

							if (label.equalsIgnoreCase("XMS-ID")
									|| label.equalsIgnoreCase("modules")
									|| label
											.equalsIgnoreCase("platformsToTestOn")) {

								continue;
							} else if (label
									.equalsIgnoreCase("testcasealiasid")) {

								whereClause.append(" where testcasealiasid="
										+ "'" + cellValue + "' ");

							}

							else if (label.equalsIgnoreCase("projectid")) {

								whereClause.append(" and projectid='"
										+ cellValue + "' ;");

								StringBuilder delSqlBuilder = new StringBuilder();
								delSqlBuilder.append("delete from testcase "
										+ whereClause.toString());

								int totalNum = userJdbcTemplate
										.queryForInt("select count(*) from testcase"
												+ whereClause.toString());
								if (totalNum > 0) {
									needUpdated = true;
								}

								delSqlBuilder.append(";");
								delSqlBuilder.append("\r\n\n");
								delDestOut.write(delSqlBuilder.toString()
										.getBytes());
								delSqlCount++;
								logger.info(delSqlBuilder.toString());
							}
							if (label.equalsIgnoreCase("timeout")) {

								insertColumBuilder.append(Float.valueOf(
										cellValue).intValue());
								updateColumBuilder.append(label + "="
										+ Float.valueOf(cellValue).intValue());
							} else if (label.equalsIgnoreCase("testtypeid")) {
								Integer value = testTypeMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("PROJECTID")) {
								Integer value = projectformMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("TEAMID")) {
								Integer value = teamMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("OSID")) {
								Integer value = osMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("COMPID")) {
								Integer value = compMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("SUBCOMPID")) {
								Integer value = subCompMap.get(cellValue);

								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("USERID")) {
								Integer value = userMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("FEATUREID")) {
								Integer value = featureMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("AUTOID")) {
								Integer value = autoMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("versionid")) {
								Integer value = versionMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("casestateid")) {
								Integer value = caseStateMap.get(cellValue);
								if (value == null) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {
									insertColumBuilder.append(value);
									updateColumBuilder.append(label + "="
											+ value);
								}
							} else if (label.equalsIgnoreCase("createDate")) {

								if (!needUpdated) {
									cellValue = Utils.dateFormat(date, null);
									insertColumBuilder.append("'" + cellValue
											+ "'");
								} else {

									int pos = insertColumBuilder
											.indexOf(",CREATEDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									continue;
								}

							} else if (label.equalsIgnoreCase("modifyDate")) {

								if (needUpdated) {

									updateColumBuilder.append(label + "='"
											+ Utils.dateFormat(date, null)
											+ "'");

								} else {

									int pos = insertColumBuilder
											.indexOf(",MODIFYDATE");
									insertColumBuilder = insertColumBuilder
											.delete(pos, pos + 11);
									int commaPos = insertColumBuilder
											.lastIndexOf(",");

									insertColumBuilder = insertColumBuilder
											.deleteCharAt(commaPos);
									// continue;
								}
							}

							else {

								if (Utils.isNullORWhiteSpace(cellValue)) {
									insertColumBuilder.append("NULL");
									updateColumBuilder.append(label + "=NULL");
								} else {

									insertColumBuilder.append("'"
											+ cellValue.replace("'", "&#039")
											+ "'");
									updateColumBuilder.append(label + "='"
											+ cellValue.replace("'", "&#039")
											+ "'");
								}
							}

						}

						else {

							if (label.equalsIgnoreCase("TESTCASEALIASID")) {
								break;
							}
							insertColumBuilder.append("NULL");
							updateColumBuilder.append(label + "=NULL");

						}

						if (cellNum != xssfSheet.getRow(0).getLastCellNum() - 1) {
							insertColumBuilder.append(" , ");
							updateColumBuilder.append(" , ");
						} else {

							insertColumBuilder.append(");");
							insertColumBuilder.append("\r\n\n");
							updateColumBuilder.append(whereClause);
							updateColumBuilder.append("\r\n\n");
							logger.info(insertColumBuilder.toString());
							logger.info(updateColumBuilder.toString());
							byte[] bytes = insertColumBuilder.toString()
									.getBytes();
							// out.write(bytes);
							insertDestOut.write(bytes);
							updateDestOut.write(updateColumBuilder.toString()
									.getBytes());
							if (needUpdated) {
								int res = userJdbcTemplate
										.update(updateColumBuilder.toString());
								if (res == 1) {
									updateSqlCount++;
								}
							}
							if (!needUpdated) {
								int result = userJdbcTemplate
										.update(insertColumBuilder.toString());
								if (result == 1) {
									insertSqlCount++;
								}
							}
						}

					}

					logger.info("rowNum:--->:" + rowNum + " cellNum--->:"
							+ cellNum + "  the CellValue--->:"
							+ getXLSXValue(xssfCell));

				}

			}

		}

		is.close();
		insertDestOut.close();
		delDestOut.close();
		updateDestOut.close();

	}

	private String getXLSXValue(XSSFCell xssfCell) {

		if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
			return String.valueOf(xssfCell.getStringCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
			return String.valueOf(xssfCell.getCellFormula());
		} else {
			return null;
		}
	}

	/*
	 * public InputStream exportTestPlan(TestPlan testplan, ActionContext
	 * context) throws Exception{ this.miniSetUp(); XSSFWorkbook xssfWorkbook =
	 * new XSSFWorkbook(); XSSFSheet planSummarySheet =
	 * xssfWorkbook.createSheet("TestPlanSummary"); String[] planSummaryTitle =
	 * {"Release Cycle", "Project", "Release Version", "Duration",
	 * "Package Info", "Owner", "Pass", "Fail", "Not Run", "Block", "Pass Rate",
	 * "Total Case"}; XSSFRow xssfRow = planSummarySheet.createRow(0); for (int
	 * i = 0; i < planSummaryTitle.length; i++)
	 * xssfRow.createCell(i).setCellValue(planSummaryTitle[i]); xssfRow =
	 * planSummarySheet.createRow(1);
	 * xssfRow.createCell(0).setCellValue(testplan.getReleaseCycle());
	 * xssfRow.createCell(1).setCellValue(testplan.getProject());
	 * xssfRow.createCell(2).setCellValue(testplan.getReleaseVersion());
	 * xssfRow.createCell(3).setCellValue(testplan.getStartDate() + "->" +
	 * testplan.getEndDate());
	 * xssfRow.createCell(4).setCellValue(testplan.getPackageInfos());
	 * xssfRow.createCell(5).setCellValue(euserMap.get(testplan.getOwner()));
	 * xssfRow.createCell(6).setCellValue(testplan.getPass());
	 * xssfRow.createCell(7).setCellValue(testplan.getFail());
	 * xssfRow.createCell(8).setCellValue(testplan.getNotRun());
	 * xssfRow.createCell(9).setCellValue(testplan.getBlock());
	 * xssfRow.createCell(10).setCellValue(testplan.getPassRate());
	 * xssfRow.createCell(11).setCellValue(testplan.getTotalCases());
	 * 
	 * generateSubTestPlanSheet(testplan.getSubPlanList(), xssfWorkbook);
	 * 
	 * generateCaseResultSheet(testplan.getSubPlanList(), xssfWorkbook);
	 * 
	 * String desDir = Utils.getProjectPath(context) + "/testplan"; long
	 * systemMills = System.currentTimeMillis(); File desDirFile = new
	 * File(desDir); System.out.println("testplan paht:" + desDir); if
	 * (!desDirFile.exists()) { desDirFile.mkdir(); } File saveFile = new
	 * File(desDir + File.separator + "testplan_" + systemMills + ".xlsx");
	 * saveFile.setWritable(true); FileOutputStream fos = new
	 * FileOutputStream(saveFile);
	 * 
	 * xssfWorkbook.write(fos); fos.close(); FileInputStream fis = new
	 * FileInputStream(saveFile); return fis; }
	 */

	private void generateSubTestPlanSheet(List<SubTestPlan> subplanList,
			XSSFWorkbook xssfWorkbook) {
	/*	XSSFSheet sheet = xssfWorkbook.createSheet("SubTestPlanSummary");
		String[] subPlanSummaryTitle = { "SubPlan Name", "Release Cycle",
				"Pass", "Fail", "Not Run", "Block", "Pass Rate", "Total Case",
				"DueDate", "Testers" };
		XSSFRow xssfRow = sheet.createRow(0);
		for (int i = 0; i < subPlanSummaryTitle.length; i++)
			xssfRow.createCell(i).setCellValue(subPlanSummaryTitle[i]);
		int rowCount = subplanList.size();
		SubTestPlan subplan = null;
		for (int i = 0; i < rowCount; i++) {
			subplan = subplanList.get(i);
			xssfRow = sheet.createRow(i + 1);
			xssfRow.createCell(0).setCellValue(subplan.getSubPlanName());
//			xssfRow.createCell(1).setCellValue(subplan.getReleaseCycle());
			xssfRow.createCell(2).setCellValue(subplan.getPass());
			xssfRow.createCell(3).setCellValue(subplan.getFail());
			xssfRow.createCell(4).setCellValue(subplan.getNotRun());
			xssfRow.createCell(5).setCellValue(subplan.getBlock());
			xssfRow.createCell(6).setCellValue(subplan.getPassRate());
			xssfRow.createCell(7).setCellValue(subplan.getTotalCases());
			xssfRow.createCell(8).setCellValue(subplan.getDueDate());
			xssfRow.createCell(9).setCellValue(subplan.getTesters());
		}*/
	}

	private void generateCaseResultSheet(List<SubTestPlan> subplanList,
			XSSFWorkbook xssfWorkbook) {
	/*	XSSFSheet sheet = xssfWorkbook.createSheet("DetailedTestResult");
		String[] detailSheetTitle = { "SubPlan Name", "Test Unit Name",
				"Test Case Name", "Result", "Log", "Bug Id", "Comments" };
		XSSFRow xssfRow = sheet.createRow(0);
		for (int i = 0; i < detailSheetTitle.length; i++)
			xssfRow.createCell(i).setCellValue(detailSheetTitle[i]);
		int rowCount = 1, colCount = 0;
		SubTestPlan subplan = null;

		for (SubTestPlan itPlan : subplanList) {
			List<TestUnit> tmpUnitList = itPlan.getTestUnitList();
			for (TestUnit tmpUnit : tmpUnitList) {
				List<TestResult> tmprs = tmpUnit.getTestResultList();

				for (TestResult rs : tmprs) {
					xssfRow = sheet.createRow(rowCount++);
					colCount = 0;
					xssfRow.createCell(colCount++).setCellValue(
							itPlan.getSubPlanName());
					xssfRow.createCell(colCount++).setCellValue(
							tmpUnit.getTestUnitName());
					xssfRow.createCell(colCount++).setCellValue(
							rs.getTestCaseName());
					xssfRow.createCell(colCount++).setCellValue(
							eresultTypeMap.get(rs.getResultTypeId()));
					xssfRow.createCell(colCount++).setCellValue(rs.getLog());
					xssfRow.createCell(colCount++).setCellValue(rs.getBugId());
					xssfRow.createCell(colCount++).setCellValue(
							rs.getComments());
				}
			}
		}*/
	}

	public InputStream exportTestCase(List<TestCase> testcaseList, ActionContext context) throws Exception {
		setUp();
		
		// Set test case table column
		List<String> exportColumns = new ArrayList<String>();
		exportColumns.add("操作");
		exportColumns.add("用例ID");
		exportColumns.add("用例名称");
		exportColumns.add("项目");
		exportColumns.add("功能模块");
		exportColumns.add("子模块");
		exportColumns.add("功能点");
		exportColumns.add("用例级别");
		exportColumns.add("自动化");
		exportColumns.add("用例类型");
		exportColumns.add("用例编号");
		exportColumns.add("需求编号");
		exportColumns.add("前置条件");
		exportColumns.add("执行步骤");
		exportColumns.add("期望结果");
		exportColumns.add("用例描述");
		exportColumns.add("提交人");

		// create XssfWorkBook object;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		// create fist sheet
		XSSFSheet xssfSheet = xssfWorkbook.createSheet("testcase");
		
		// create fist row and add array exportcolumns to row[0]
		XSSFRow FirstRow = xssfSheet.createRow(0);	
		FirstRow.createCell(0).setCellValue("This file can be used to edit test case, Please don't change column Operation/test case id");	
		
		// create second row and add array exportcolumns to row[1]
		XSSFRow xssfRow = xssfSheet.createRow(1);	
		for (int j = 0; j < exportColumns.size(); j++) {
			xssfRow.createCell(j).setCellValue(exportColumns.get(j));
		}

		// add all test cases into cell
		for (int i = 0; i < testcaseList.size(); i++) {
			TestCase tCase = testcaseList.get(i);
			// row index start with 1;
			int rowIndex = i + 2;
			XSSFRow oXssfRow = xssfSheet.createRow(rowIndex);
			oXssfRow.createCell(exportColumns.indexOf("操作")).setCellValue("Update");
			
			if (tCase.getTestCaseId() > 0) {
				oXssfRow.createCell(exportColumns.indexOf("用例ID")).setCellValue(tCase.getTestCaseId());
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例ID")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			}

			if (Utils.isNullORWhiteSpace(tCase.getTestCaseName())) {
				oXssfRow.createCell(exportColumns.indexOf("用例名称")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例名称")).setCellValue(tCase.getTestCaseName());
			}						

			if (Utils.isNullORWhiteSpace(tCase.getProjectName())) {
				oXssfRow.createCell(exportColumns.indexOf("项目")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("项目")).setCellValue(tCase.getProjectName());
			}
			
			if (Utils.isNullORWhiteSpace(tCase.getFeatureName())) {
				oXssfRow.createCell(exportColumns.indexOf("功能模块")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("功能模块")).setCellValue(tCase.getFeatureName());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getCompName())) {
				oXssfRow.createCell(exportColumns.indexOf("子模块")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("子模块")).setCellValue(tCase.getCompName());
			}	
		
			if (Utils.isNullORWhiteSpace(tCase.getSubCompName())) {
				oXssfRow.createCell(exportColumns.indexOf("功能点")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("功能点")).setCellValue(tCase.getSubCompName());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getOSName())) {
				oXssfRow.createCell(exportColumns.indexOf("用例级别")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例级别")).setCellValue(tCase.getOSName());
			}	

			if (Utils.isNullORWhiteSpace(tCase.getAutoName())) {
				oXssfRow.createCell(exportColumns.indexOf("自动化")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("自动化")).setCellValue(tCase.getAutoName());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getTestTypeName())) {
				oXssfRow.createCell(exportColumns.indexOf("用例类型")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例类型")).setCellValue(tCase.getTestTypeName());
			}	
						
			if (Utils.isNullORWhiteSpace(tCase.getTestCasealiasId())) {
				oXssfRow.createCell(exportColumns.indexOf("用例编号")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例编号")).setCellValue(tCase.getTestCasealiasId());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getRequirementId())) {
				oXssfRow.createCell(exportColumns.indexOf("需求编号")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("需求编号")).setCellValue(tCase.getRequirementId());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getConfigFiles())) {
				oXssfRow.createCell(exportColumns.indexOf("前置条件")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("前置条件")).setCellValue(tCase.getConfigFiles());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getExecutionSteps())) {
				oXssfRow.createCell(exportColumns.indexOf("执行步骤")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("执行步骤")).setCellValue(tCase.getExecutionSteps());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getExpectedResult())) {
				oXssfRow.createCell(exportColumns.indexOf("期望结果")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("期望结果")).setCellValue(tCase.getExpectedResult());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getDescription())) {
				oXssfRow.createCell(exportColumns.indexOf("用例描述")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("用例描述")).setCellValue(tCase.getDescription());
			}	

			if (Utils.isNullORWhiteSpace(tCase.getUserName())) {
				oXssfRow.createCell(exportColumns.indexOf("提交人")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("提交人")).setCellValue(tCase.getUserName());
			}
			/** 
			if (Utils.isNullORWhiteSpace(tCase.getTestScript())) {
				oXssfRow.createCell(exportColumns.indexOf("TestScript")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("TestScript")).setCellValue(tCase.getTestScript());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getTestfunctionCall())) {
				oXssfRow.createCell(exportColumns.indexOf("TestFunctionCall")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("TestFunctionCall")).setCellValue(tCase.getTestfunctionCall());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getPackagesizeRange())) {
				oXssfRow.createCell(exportColumns.indexOf("PackageRangeSize")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("PackageRangeSize")).setCellValue(tCase.getPackagesizeRange());
			}	
			
			if (tCase.getTimeout() > 0) {
				oXssfRow.createCell(exportColumns.indexOf("Timeout")).setCellValue(tCase.getTimeout());
			} else {
				
				oXssfRow.createCell(exportColumns.indexOf("Timeout")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getVersionName())) {
				oXssfRow.createCell(exportColumns.indexOf("Version")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("Version")).setCellValue(tCase.getVersionName());
			}	
			
			if (Utils.isNullORWhiteSpace(tCase.getCaseStateName())) {
				oXssfRow.createCell(exportColumns.indexOf("CaseState")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(exportColumns.indexOf("CaseState")).setCellValue(tCase.getCaseStateName());
			}	
			**/

		}

		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		File saveFile = new File(desDir + File.separator + "testcase_"
				+ systemMills + ".xlsx");
		saveFile.setWritable(true);
		FileOutputStream fos = new FileOutputStream(saveFile);

		xssfWorkbook.write(fos);
		fos.close();
		FileInputStream fis = new FileInputStream(saveFile);
		return fis;
	}

	public InputStream exportPerformanceResult(int subExecutionId, ActionContext context) throws Exception {
		
		List<TestResult> testcaseList = testResultDaoImpl.listTestResultBySubExecutionId(subExecutionId);
		List<TestResult> perfResults = testResultDaoImpl.listPerformanceResultBySubExecutionId(subExecutionId);
		Map<String, String> perfHeadList = new HashMap<String, String>();
		
		//Get performance attributes list in current sub execution		
		for(TestResult result : perfResults){
			perfHeadList.put(result.getAttributeName(), result.getAttributeName());
		}
		
		// first step : get test case table column
		List<String> exportColumns = new ArrayList<String>();
		exportColumns.add("Operation");
		exportColumns.add("TestCaseName");
		exportColumns.add("ResultId");
		exportColumns.add("ResultTrackId");
		exportColumns.add("Item");
		for (Map.Entry<String, String> head : perfHeadList.entrySet())
		{
    		exportColumns.add(head.getKey());
		}
		exportColumns.add("ResultType");
		exportColumns.add("BugId");
		exportColumns.add("BugName");
		exportColumns.add("Comment");
		exportColumns.add("Log");
		exportColumns.add("Target");
		// create XssfWorkBook object;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		// create fist sheet
		XSSFSheet xssfSheet = xssfWorkbook.createSheet("Performance");
		// create fist row ,add column name
		XSSFRow xssfRow = xssfSheet.createRow(0);
		
		// add exprot columns to row[0]
		for (int j = 0; j < exportColumns.size(); j++) {
			xssfRow.createCell(j).setCellValue(exportColumns.get(j));
		}

		int rowIndex = 1;
		// add all test cases into cell
		for (TestResult tCase : testcaseList) {
			if(tCase.getResultTrackId() > 0) {
				List<PerformanceResult> items = testResultDaoImpl.queryPerfResult2ByResultTrackId(tCase.getResultTrackId());
				XSSFRow oXssfRow = xssfSheet.createRow(rowIndex);
				if(items.size() > 0){
					oXssfRow.createCell(exportColumns.indexOf("Operation")).setCellValue("Update");
				} else {
					oXssfRow.createCell(exportColumns.indexOf("Operation")).setCellValue("New");
				}
				int resultId = tCase.getResultId();
				int resultIdIndex = exportColumns.indexOf("ResultId");
				oXssfRow.createCell(resultIdIndex).setCellValue(resultId);
				String TestCaseName = tCase.getTestCaseName();
				int TestCaseNameIndex = exportColumns.indexOf("TestCaseName");
				if (Utils.isNullORWhiteSpace(TestCaseName)) {
					oXssfRow.createCell(TestCaseNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(TestCaseNameIndex).setCellValue(TestCaseName);
				}
				int resultTrackId = exportColumns.indexOf("ResultTrackId");
				oXssfRow.createCell(resultTrackId).setCellValue(tCase.getResultTrackId());
				String Log = tCase.getLog();
				int logIndex = exportColumns.indexOf("Log");
				if (Utils.isNullORWhiteSpace(Log)) {
					oXssfRow.createCell(logIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(logIndex).setCellValue(Log);
				}
				String bugId = tCase.getBugId();
				int bugIdIndex = exportColumns.indexOf("BugId");
				if (Utils.isNullORWhiteSpace(bugId)) {
					oXssfRow.createCell(bugIdIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(bugIdIndex).setCellValue(bugId);
				}
				String resultTypeName = tCase.getResultTypeName();
				int ResultTypeNameIndex = exportColumns.indexOf("ResultType");
				if (Utils.isNullORWhiteSpace(resultTypeName)) {
					oXssfRow.createCell(ResultTypeNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(ResultTypeNameIndex).setCellValue(resultTypeName);
				}
				String bugName = tCase.getBugName();
				int bugNameIndex = exportColumns.indexOf("BugName");
				if (Utils.isNullORWhiteSpace(bugId)) {
					oXssfRow.createCell(bugNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(bugNameIndex).setCellValue(bugName);
				}
				String comments = tCase.getComments();
				int commentsIndex = exportColumns.indexOf("Comment");
				if (Utils.isNullORWhiteSpace(comments)) {
					oXssfRow.createCell(commentsIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(commentsIndex).setCellValue(comments);
				}
				String Target = tCase.getTargetName();
				int TargetIndex = exportColumns.indexOf("Target");
				if (Utils.isNullORWhiteSpace(Target)) {
					oXssfRow.createCell(TargetIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(TargetIndex).setCellValue(Target);
				}
				rowIndex++;
				
				if(items.size() > 0 ){
					int itemCount = 1;
					for(PerformanceResult item : items) {	
						String attr = item.getPerformance();
						String[] attrs = attr.split(";");
						XSSFRow oXssfRow1 = xssfSheet.createRow(rowIndex);
						oXssfRow1.createCell(resultIdIndex).setCellValue(resultId);
						if (Utils.isNullORWhiteSpace(TestCaseName)) {
							oXssfRow1.createCell(TestCaseNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
						} else {
							oXssfRow1.createCell(TestCaseNameIndex).setCellValue(TestCaseName);
						}
						oXssfRow1.createCell(resultTrackId).setCellValue(tCase.getResultTrackId());
						for(String tmp : attrs) {
							String[] attributes = tmp.split(":");
							int attributeIndex = exportColumns.indexOf(attributes[0].trim());
							oXssfRow1.createCell(attributeIndex).setCellValue(attributes[1].trim());
						}
						int itemIndex = exportColumns.indexOf("Item");
						oXssfRow1.createCell(itemIndex).setCellValue(itemCount);
						itemCount++;
						rowIndex++;
					}
				}
			} else {
				XSSFRow oXssfRow = xssfSheet.createRow(rowIndex);
				
				int resultId = tCase.getResultId();
				int resultIdIndex = exportColumns.indexOf("ResultId");
				oXssfRow.createCell(resultIdIndex).setCellValue(resultId);
				String resultTypeName = tCase.getResultTypeName();
				int ResultTypeNameIndex = exportColumns.indexOf("ResultType");
				
				oXssfRow.createCell(exportColumns.indexOf("Operation")).setCellValue("New");
				if (Utils.isNullORWhiteSpace(resultTypeName)) {
					oXssfRow.createCell(ResultTypeNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(ResultTypeNameIndex).setCellValue(resultTypeName);
				}
				String TestCaseName = tCase.getTestCaseName();
				int TestCaseNameIndex = exportColumns.indexOf("TestCaseName");
				if (Utils.isNullORWhiteSpace(TestCaseName)) {
					oXssfRow.createCell(TestCaseNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(TestCaseNameIndex).setCellValue(TestCaseName);
				}
				int resultTrackId = exportColumns.indexOf("ResultTrackId");
				oXssfRow.createCell(resultTrackId).setCellValue(tCase.getResultTrackId());
				String Log = tCase.getLog();
				int logIndex = exportColumns.indexOf("Log");
				if (Utils.isNullORWhiteSpace(Log)) {
					oXssfRow.createCell(logIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(logIndex).setCellValue(Log);
				}
				String bugId = tCase.getBugId();
				int bugIdIndex = exportColumns.indexOf("BugId");
				if (Utils.isNullORWhiteSpace(bugId)) {
					oXssfRow.createCell(bugIdIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(bugIdIndex).setCellValue(bugId);
				}
		
				String bugName = tCase.getBugName();
				int bugNameIndex = exportColumns.indexOf("BugName");
				if (Utils.isNullORWhiteSpace(bugId)) {
					oXssfRow.createCell(bugNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(bugNameIndex).setCellValue(bugName);
				}
				String comments = tCase.getComments();
				int commentsIndex = exportColumns.indexOf("Comment");
				if (Utils.isNullORWhiteSpace(comments)) {
					oXssfRow.createCell(commentsIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(commentsIndex).setCellValue(comments);
				}
				String Target = tCase.getTargetName();
				int TargetIndex = exportColumns.indexOf("Target");
				if (Utils.isNullORWhiteSpace(Target)) {
					oXssfRow.createCell(TargetIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
				} else {
					oXssfRow.createCell(TargetIndex).setCellValue(Target);
				}
				rowIndex++;
			}
		}

		//Save content to Excel stream
		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		File saveFile = new File(desDir + File.separator + "testcase_"
				+ systemMills + ".xlsx");
		saveFile.setWritable(true);
		FileOutputStream fos = new FileOutputStream(saveFile);

		xssfWorkbook.write(fos);
		fos.close();
		FileInputStream fis = new FileInputStream(saveFile);
		return fis;
	}

	public InputStream exportSubTestPlanTestCase(List<TestResult> testcaseList,
			ActionContext context) throws Exception {
		setUp();
		// first step : get test case table column
		List<String> exportColumns = new ArrayList<String>();
		exportColumns.add("ResultId");
		exportColumns.add("TestCaseName");
		exportColumns.add("ResultType");
		exportColumns.add("Log");
		exportColumns.add("BugId");
		exportColumns.add("BugName");
		exportColumns.add("Comments");
		exportColumns.add("Target");
		exportColumns.add("Platform");
		exportColumns.add("ExecutionOS");
		exportColumns.add("ExecutionSteps");
		exportColumns.add("ExpectedResult");
		// create XssfWorkBook object;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		// create fist sheet
		XSSFSheet xssfSheet = xssfWorkbook.createSheet("testcase");
		// create fist row ,add column name
		XSSFRow xssfRow = xssfSheet.createRow(0);
		
		// add exprot columns to row[0]
		for (int j = 0; j < exportColumns.size(); j++) {
			xssfRow.createCell(j).setCellValue(exportColumns.get(j));
		}

		// add all test cases into cell
		for (int i = 0; i < testcaseList.size(); i++) {
			TestResult tCase = testcaseList.get(i);
			// row index start with 1;
			int rowIndex = i + 1;
			XSSFRow oXssfRow = xssfSheet.createRow(rowIndex);
			
			int resultId = tCase.getResultId();
			int resultIdIndex = exportColumns.indexOf("ResultId");
			oXssfRow.createCell(resultIdIndex).setCellValue(resultId);
		
			String resultTypeName = tCase.getResultTypeName();
			int ResultTypeNameIndex = exportColumns.indexOf("ResultType");
			if (Utils.isNullORWhiteSpace(resultTypeName)) {

				oXssfRow.createCell(ResultTypeNameIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ResultTypeNameIndex).setCellValue(resultTypeName);
			}
			
			String TestCaseName = tCase.getTestCaseName();
			int TestCaseNameIndex = exportColumns.indexOf("TestCaseName");
			if (Utils.isNullORWhiteSpace(TestCaseName)) {

				oXssfRow.createCell(TestCaseNameIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(TestCaseNameIndex).setCellValue(TestCaseName);
			}
			
			String Log = tCase.getLog();
			int logIndex = exportColumns.indexOf("Log");
			if (Utils.isNullORWhiteSpace(Log)) {

				oXssfRow.createCell(logIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(logIndex).setCellValue(Log);
			}

			String bugId = tCase.getBugId();
			int bugIdIndex = exportColumns.indexOf("BugId");
			if (Utils.isNullORWhiteSpace(bugId)) {

				oXssfRow.createCell(bugIdIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(bugIdIndex).setCellValue(bugId);
			}
			
			String bugName = tCase.getBugName();
			int bugNameIndex = exportColumns.indexOf("BugName");
			if (Utils.isNullORWhiteSpace(bugId)) {

				oXssfRow.createCell(bugNameIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(bugNameIndex).setCellValue(bugName);
			}
			
			String comments = tCase.getComments();
			int commentsIndex = exportColumns.indexOf("Comments");
			if (Utils.isNullORWhiteSpace(comments)) {

				oXssfRow.createCell(commentsIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(commentsIndex).setCellValue(comments);
			}
			
			String Target = tCase.getTargetName();
			int TargetIndex = exportColumns.indexOf("Target");
			if (Utils.isNullORWhiteSpace(Target)) {

				oXssfRow.createCell(TargetIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(TargetIndex).setCellValue(Target);
			}
			
			String Platform = tCase.getPlatformName();
			int PlatformIndex = exportColumns.indexOf("Platform");
			if (Utils.isNullORWhiteSpace(Platform)) {

				oXssfRow.createCell(PlatformIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(PlatformIndex).setCellValue(Platform);
			}			
	
			String ExecutionOS = tCase.getOsName();
			int ExecutionOSIndex = exportColumns.indexOf("ExecutionOS");
			if (Utils.isNullORWhiteSpace(ExecutionOS)) {

				oXssfRow.createCell(ExecutionOSIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExecutionOSIndex).setCellValue(ExecutionOS);
			}	
			
			String ExecutionSteps = tCase.getExecutionSteps();
			int ExecutionStepsIndex = exportColumns.indexOf("ExecutionSteps");
			if (Utils.isNullORWhiteSpace(ExecutionSteps)) {

				oXssfRow.createCell(ExecutionStepsIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExecutionStepsIndex).setCellValue(ExecutionSteps);
			}
			
			String ExpectedResult = tCase.getExpectedResult();
			int ExpectedResultIndex = exportColumns.indexOf("ExpectedResult");
			if (Utils.isNullORWhiteSpace(ExpectedResult)) {

				oXssfRow.createCell(ExpectedResultIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExpectedResultIndex).setCellValue(ExpectedResult);
			}
			/*
			int testtypeId = tCase.getTestTypeId();
			int testtypeIndex = exportColumns.indexOf("TESTTYPE");
			if (testtypeId == 0) {

				oXssfRow.createCell(testtypeIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testtypeIndex).setCellValue(
						etestTypeMap.get(testtypeId));
			}
			*/

		}

		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		File saveFile = new File(desDir + File.separator + "testcase_"
				+ systemMills + ".xlsx");
		saveFile.setWritable(true);
		FileOutputStream fos = new FileOutputStream(saveFile);

		xssfWorkbook.write(fos);
		fos.close();
		FileInputStream fis = new FileInputStream(saveFile);
		return fis;

	}	
	
/**
 * Export test execution report to Excel	
 * @param testcaseList
 * @param context
 * @return
 * @throws Exception
 */
	
	public InputStream exportExecutionReportToExcel(int id, ActionContext context) throws Exception {
		
		TestExecution testExecution= testExecutionDaoImpl.queryTestExecutionById(id);
		List<SubExecution> subExecutionList = testExecutionDaoImpl.listSubExecutionByExecutionId(id);
		List<TestResult> testcaseList = testResultDaoImpl.listTestResultByTestExecutionId(id);

		// create XssfWorkBook object;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		
		//Create sheet to save execution summary information
		XSSFSheet summarySheet = xssfWorkbook.createSheet("Summary");
		// create fist row ,add column name
		List<String> summaryColumns = new ArrayList<String>();
		summaryColumns.add("Execution Name");
		summaryColumns.add("Release Cycle");
		summaryColumns.add("Test Plan");
		summaryColumns.add("Execution Type");
		summaryColumns.add("Build Type");
		summaryColumns.add("Pass");
		summaryColumns.add("Fail");
		summaryColumns.add("Block");
		summaryColumns.add("Not Run");
		summaryColumns.add("Total Cases");
		summaryColumns.add("Pass Rate");
		summaryColumns.add("Executed Rate");
		summaryColumns.add("Description");

		// create second row ,add content match with first row
		double tmp = testExecution.getPassrate()*100;
		int passRate = (int)tmp;
		double tmp1 = testExecution.getExecuteRate()*100;
		int executeRate = (int)tmp1;
		List<String> summaryContent = new ArrayList<String>();
		summaryContent.add(testExecution.getExecutionName());
		summaryContent.add(testExecution.getReleaseCycle());
		summaryContent.add(testExecution.getTestPlanName());
		summaryContent.add(testExecution.getPhaseName());
		summaryContent.add(testExecution.getBuildTypeName());
		summaryContent.add(Integer.toString(testExecution.getPass()));
		summaryContent.add(Integer.toString(testExecution.getFail()));
		summaryContent.add(Integer.toString(testExecution.getBlock()));
		summaryContent.add(Integer.toString(testExecution.getNotRun()));
		summaryContent.add(Integer.toString(testExecution.getTotalCases()));
		summaryContent.add(Integer.toString(passRate)+"%");
		summaryContent.add(Integer.toString(executeRate)+"%");
		summaryContent.add(testExecution.getDesc());
		
		XSSFRow summaryHeadRow = summarySheet.createRow(0);
		XSSFRow summaryContentRow = summarySheet.createRow(1);
		for (int k = 0; k < summaryColumns.size(); k++) {
			summaryHeadRow.createCell(k).setCellValue(summaryColumns.get(k));
			summaryContentRow.createCell(k).setCellValue(summaryContent.get(k));
		}
		
		//=============================================================================//
		
		//Create sheet to save sub execution list
		XSSFSheet subExecutionSheet = xssfWorkbook.createSheet("SubExecutionList");
		// create fist row ,add column name
		List<String> subExecutionColumns = new ArrayList<String>();
		subExecutionColumns.add("Sub Execution Name");
		subExecutionColumns.add("Sub Plan Name");
		subExecutionColumns.add("Platform");
		subExecutionColumns.add("Execution OS");
		subExecutionColumns.add("Pass");
		subExecutionColumns.add("Fail");
		subExecutionColumns.add("Block");
		subExecutionColumns.add("Not Run");
		subExecutionColumns.add("Total Cases");
		subExecutionColumns.add("Pass Rate");
		subExecutionColumns.add("Executed Rate");
		subExecutionColumns.add("Due Date");
		
		XSSFRow subExecutionHeadRow = subExecutionSheet.createRow(0);
		for (int k = 0; k < subExecutionColumns.size(); k++) {
			subExecutionHeadRow.createCell(k).setCellValue(subExecutionColumns.get(k));
		}
		// add all sub executions into sub execution list sheet
		for (int n = 0; n < subExecutionList.size(); n++) {
			SubExecution sub = subExecutionList.get(n);
			// row index start with 1;
			int subIndex = n + 1;
			XSSFRow subRow = subExecutionSheet.createRow(subIndex);

			if (Utils.isNullORWhiteSpace(sub.getSubExecutionName())) {
				subRow.createCell(subExecutionColumns.indexOf("Sub Execution Name")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else { subRow.createCell(subExecutionColumns.indexOf("Sub Execution Name")).setCellValue(sub.getSubExecutionName());}
			
			if (Utils.isNullORWhiteSpace(sub.getSubPlanName())) {
				subRow.createCell(subExecutionColumns.indexOf("Sub Plan Name")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else { subRow.createCell(subExecutionColumns.indexOf("Sub Plan Name")).setCellValue(sub.getSubPlanName());}
			
			if (Utils.isNullORWhiteSpace(sub.getPlatFormName())) {
				subRow.createCell(subExecutionColumns.indexOf("Platform")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else { subRow.createCell(subExecutionColumns.indexOf("Platform")).setCellValue(sub.getPlatFormName());}
			
			if (Utils.isNullORWhiteSpace(sub.getOsName())) {
				subRow.createCell(subExecutionColumns.indexOf("Execution OS")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else { subRow.createCell(subExecutionColumns.indexOf("Execution OS")).setCellValue(sub.getOsName());}
			
			subRow.createCell(subExecutionColumns.indexOf("Pass")).setCellValue(sub.getPass());
			
			subRow.createCell(subExecutionColumns.indexOf("Fail")).setCellValue(sub.getFail());
			
			subRow.createCell(subExecutionColumns.indexOf("Block")).setCellValue(sub.getBlock());
			
			subRow.createCell(subExecutionColumns.indexOf("Not Run")).setCellValue(sub.getNotRun());
			
			subRow.createCell(subExecutionColumns.indexOf("Total Cases")).setCellValue(sub.getTotalCases());
			
			double subtmp = sub.getPassrate()*100;
			int subPassRate = (int)subtmp;
			subRow.createCell(subExecutionColumns.indexOf("Pass Rate")).setCellValue(subPassRate+"%");
			
			double subtmp_ = sub.getExecuteRate()*100;
			int subExecuteRate = (int)subtmp_;
			subRow.createCell(subExecutionColumns.indexOf("Executed Rate")).setCellValue(subExecuteRate+"%");
			
			if (Utils.isNullORWhiteSpace(sub.getDueDate())) {
				subRow.createCell(subExecutionColumns.indexOf("Due Date")).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else { subRow.createCell(subExecutionColumns.indexOf("Due Date")).setCellValue(sub.getDueDate());}
		}	
		//=============================================================================//
		
		// create sheet to save contained case list of current execution
		XSSFSheet caseSheet = xssfWorkbook.createSheet("testCaseList");
		// first step : get test case table column
		
		List<String> exportColumns = new ArrayList<String>();
		//exportColumns.add("ResultId");
		exportColumns.add("TestCaseName");
		exportColumns.add("ResultType");
		exportColumns.add("Log");
		exportColumns.add("BugId");
		exportColumns.add("Comments");
		exportColumns.add("Target");
		exportColumns.add("Platform");
		exportColumns.add("ExecutionOS");
		exportColumns.add("Description");
		exportColumns.add("ExecutionSteps");
		exportColumns.add("ExpectedResult");
		
		// create fist row ,add column name
		XSSFRow xssfRow = caseSheet.createRow(0);
		for (int j = 0; j < exportColumns.size(); j++) {
			xssfRow.createCell(j).setCellValue(exportColumns.get(j));
		}

		// add all test cases into case sheet
		for (int i = 0; i < testcaseList.size(); i++) {
			TestResult tCase = testcaseList.get(i);
			// row index start with 1;
			int rowIndex = i + 1;
			XSSFRow oXssfRow = caseSheet.createRow(rowIndex);
			/**
			int resultId = tCase.getResultId();
			int resultIdIndex = exportColumns.indexOf("ResultId");
			oXssfRow.createCell(resultIdIndex).setCellValue(resultId);
			**/
			String resultTypeName = tCase.getResultTypeName();
			int ResultTypeNameIndex = exportColumns.indexOf("ResultType");
			if (Utils.isNullORWhiteSpace(resultTypeName)) {
				oXssfRow.createCell(ResultTypeNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ResultTypeNameIndex).setCellValue(resultTypeName);
			}
			
			String TestCaseName = tCase.getTestCaseName();
			int TestCaseNameIndex = exportColumns.indexOf("TestCaseName");
			if (Utils.isNullORWhiteSpace(TestCaseName)) {
				oXssfRow.createCell(TestCaseNameIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(TestCaseNameIndex).setCellValue(TestCaseName);
			}
			
			String Log = tCase.getLog();
			int logIndex = exportColumns.indexOf("Log");
			if (Utils.isNullORWhiteSpace(Log)) {
				oXssfRow.createCell(logIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(logIndex).setCellValue(Log);
			}

			String bugId = tCase.getBugId();
			int bugIdIndex = exportColumns.indexOf("BugId");
			if (Utils.isNullORWhiteSpace(bugId)) {
				oXssfRow.createCell(bugIdIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(bugIdIndex).setCellValue(bugId);
			}
			
			String comments = tCase.getComments();
			int commentsIndex = exportColumns.indexOf("Comments");
			if (Utils.isNullORWhiteSpace(comments)) {
				oXssfRow.createCell(commentsIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(commentsIndex).setCellValue(comments);
			}
			
			String Target = tCase.getTargetName();
			int TargetIndex = exportColumns.indexOf("Target");
			if (Utils.isNullORWhiteSpace(Target)) {
				oXssfRow.createCell(TargetIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(TargetIndex).setCellValue(Target);
			}
			
			String Platform = tCase.getPlatformName();
			int PlatformIndex = exportColumns.indexOf("Platform");
			if (Utils.isNullORWhiteSpace(Target)) {
				oXssfRow.createCell(PlatformIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(PlatformIndex).setCellValue(Platform);
			}			
	
			String ExecutionOS = tCase.getOsName();
			int ExecutionOSIndex = exportColumns.indexOf("ExecutionOS");
			if (Utils.isNullORWhiteSpace(Target)) {
				oXssfRow.createCell(ExecutionOSIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExecutionOSIndex).setCellValue(ExecutionOS);
			}	
			
			String Description = tCase.getDescription();
			int DescriptionIndex = exportColumns.indexOf("Description");
			if (Utils.isNullORWhiteSpace(Description)) {
				oXssfRow.createCell(DescriptionIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(DescriptionIndex).setCellValue(Description);
			}
			
			String ExecutionSteps = tCase.getExecutionSteps();
			int ExecutionStepsIndex = exportColumns.indexOf("ExecutionSteps");
			if (Utils.isNullORWhiteSpace(ExecutionSteps)) {
				oXssfRow.createCell(ExecutionStepsIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExecutionStepsIndex).setCellValue(ExecutionSteps);
			}
			
			String ExpectedResult = tCase.getExpectedResult();
			int ExpectedResultIndex = exportColumns.indexOf("ExpectedResult");
			if (Utils.isNullORWhiteSpace(ExpectedResult)) {
				oXssfRow.createCell(ExpectedResultIndex).setCellType(XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(ExpectedResultIndex).setCellValue(ExpectedResult);
			}
		}

		String desDir = Utils.getProjectPath(context) + "/testcase";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		File saveFile = new File(desDir + File.separator + "testcase_"
				+ systemMills + ".xlsx");
		saveFile.setWritable(true);
		FileOutputStream fos = new FileOutputStream(saveFile);

		xssfWorkbook.write(fos);
		fos.close();
		FileInputStream fis = new FileInputStream(saveFile);
		return fis;

	}
	
/**
 * Function import test case by Excel, key part of analyze excel file and import test case in each row to table testcase	
 * @param file
 * @param context
 * @throws Exception
 */
	
	public void readTestCaseXlsx(File file, ActionContext context) throws Exception {
		//List type reserved for save each row's value get from Excel
    	List<TestCase> testCase = new ArrayList<TestCase>();
    	User SUser = (User) context.getSession().get("user");
		//Define default supported column name and save to array Title
		String Title[] = new String[30];
		Title[0] = "操作";
		Title[1] = "用例ID";
		Title[2] = "用例名称";
		Title[3] = "项目";
		Title[4] = "功能模块";
		Title[5] = "子模块";
		Title[6] = "功能点";
		Title[7] = "用例级别";
		Title[8] = "自动化";
		Title[9] = "用例类型";
		Title[10] = "用例编号";
		Title[11] = "需求编号";
		Title[12] = "前置条件";
		Title[13] = "执行步骤";
		Title[14] = "期望结果";
		Title[15] = "用例描述";
		Title[16] = "提交人";
		
		//Title[12] = "TestScript";
		//Title[13] = "TestFunctionCall";
		//Title[14] = "PackageRangeSize";
		//Title[15] = "Timeout";
		//Title[16] = "Version";
		//Title[17] = "CaseState";
		
		
		List<String> title = Arrays.asList(Title);

		//Define String TitleTmp to save excel imported Column title sequence
		String TitleTmp[] = new String[30];
        Workbook wb = new XSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println(wb.getSheetName(i));
            for (Row row : sheet) {
            	TestCase tCase = null;
            	tCase = new TestCase();
                for (Cell cell : row) {
                	String cellValue = cell.toString().trim();
					//Save Column title in excel to string to check if the column title is valid
					if(row.getRowNum() == 1){
						if (title.contains(cellValue)){
							 TitleTmp[cell.getColumnIndex()] = cellValue ;
						}
					}
					else {
						if(Title[0].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setOperation(cellValue); }
						if(Title[1].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTestCaseIdString(cellValue); }
						if(Title[2].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTestCaseName(cellValue); }
						if(Title[3].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setProjectName(cellValue); }
						if(Title[4].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setFeatureName(cellValue); }
						if(Title[5].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setCompName(cellValue); }
						if(Title[6].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setSubCompName(cellValue);; }
						if(Title[7].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setOSName(cellValue); }
						if(Title[8].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setAutoName(cellValue); }
						if(Title[9].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTestTypeName(cellValue); }
						if(Title[10].equals(TitleTmp[cell.getColumnIndex()])) {tCase.setTestCasealiasId(cellValue);}
						if(Title[11].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setRequirementId(cellValue);; }
						if(Title[12].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setConfigFiles(cellValue);; }
						if(Title[13].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setExecutionSteps(cellValue); }
						if(Title[14].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setExpectedResult(cellValue); }
						if(Title[15].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setDescription(cellValue); }
						if(Title[16].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setUserName(cellValue); }
						
						//if(Title[12].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTestScript(cellValue); }
						//if(Title[13].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTestfunctionCall(cellValue); }
						//if(Title[14].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setPackagesizeRange(cellValue); }
						//if(Title[15].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setTimeoutString(cellValue);}
						//if(Title[16].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setVersionName(cellValue); }
						//if(Title[17].equals(TitleTmp[cell.getColumnIndex()])) { tCase.setCaseStateName(cellValue); }
						
						
						
					}
                }
                tCase.setRowNumber(row.getRowNum()+1);
                testCase.add(tCase);
            }
        }
        
        String currentDate = Utils.dateFormat(new Date(), null);
        //Analyze test case information in each row of testCase and save into DB.
        parseLog = "";
        successLog = "";
        for( TestCase tmp : testCase) {
        	boolean stopFlag = false;
        	//Shared operation between update and create a new test case
        	if( "Update".equalsIgnoreCase(tmp.getOperation()) || "New".equalsIgnoreCase(tmp.getOperation())) {
        		//Shared operation between update and create a new test case
        		tmp.setModifyDate(currentDate);
        		// Empty value check no reply relationship columns
        		if(tmp.getOSName() != null && !"".equals(tmp.getOSName())) {
        			OS os = oSDaoImpl.queryOSByNameExcelImport(tmp.getOSName());
        			if(os.getOsName() == null) { 
        			parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: OS name '"+tmp.getOSName()+"' in Excel OS column not exist in iTMS OS list.\n</br>";
        			stopFlag = true;
        			} else {tmp.setOsId(os.getOsId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: OS name can't be empty.\n</br>";} 
        		
        		if(tmp.getUserName() != null && !"".equals(tmp.getUserName())) {
        			User user = userDaoImpl.queryUserInSameTeam(tmp.getUserName() , SUser.getUserId());	
        			if(user.getUserName() == null) { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: User name '"+tmp.getUserName()+"' in Excel User column not exist in iTMS User list or not in same team with current user.\n</br>";
        				stopFlag = true;
        			} else { tmp.setUserId(user.getUserId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: User name can't be empty.\n</br>";} 
        		 
        		if(tmp.getAutoName() != null && !"".equals(tmp.getAutoName())) {
        			Automation auto = testCaseDaoImpl.queryAutomationByNameExcelImport(tmp.getAutoName());
        			if(auto.getAutoName() == null) { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Automation name '"+tmp.getAutoName()+"' in Excel Automation column not exist in iTMS Automation list.\n</br>";
        				stopFlag = true;
        			} else { tmp.setAutoId(auto.getAutoId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Automation name can't be empty.\n</br>";} 
        		/**
        		if(tmp.getCaseStateName() != null && !"".equals(tmp.getCaseStateName())) {
        			CaseState state = testCaseDaoImpl.queryCaseStateByNameExcelImport(tmp.getCaseStateName());
        			if(state.getCaseStateName() == null) { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: CaseState name '"+tmp.getCaseStateName()+"' in Excel CaseState column not exist in iTMS CaseState list.\n</br>";
        				stopFlag = true;
        			} else { tmp.setCaseStateId(state.getCaseStateId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: CaseState name can't be empty.\n</br>";} 
        		**/
        		/**
        		if(tmp.getVersionName() != null && !"".equals(tmp.getVersionName())) {
        			VersionState version = testCaseDaoImpl.queryVersionStateByNameExcelImport(tmp.getVersionName());
        			if(version.getVersionName() == null) { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Version name '"+tmp.getVersionName()+"' in Excel Version column not exist in iTMS Version list.\n</br>";
        				stopFlag = true;
        			} else { tmp.setVersionId(version.getVersionId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Version name can't be empty.\n</br>";} 
        		**/
        		//Check Project/Feature/Component column rely relationship is correct and not empty.
        		if(tmp.getProjectName() != null && !"".equals(tmp.getProjectName()) && tmp.getFeatureName() != null && !"".equals(tmp.getFeatureName()) && tmp.getCompName() != null && !"".equals(tmp.getCompName())) {
        			TestCase caseTmp = testCaseDaoImpl.verifyProjectFeatureComponentExcelImport(tmp.getProjectName(),tmp.getFeatureName(),tmp.getCompName());
        			if(caseTmp.getProjectId() > 0) { 
        				tmp.setProjectId(caseTmp.getProjectId());
        				tmp.setFeatureId(caseTmp.getFeatureId());
        				tmp.setCompId(caseTmp.getCompId());
        				tmp.setTeamId(caseTmp.getTeamId());
        			} else { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Project/Feature/Component value in in Excel column is not match rely relationship saved in iTMS.\n</br>";
        				stopFlag = true;
        			}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Project/Feature/Component column can't be empty.\n</br>";} 
        		
        		if(tmp.getTestTypeName() != null && !"".equals(tmp.getTestTypeName())) {
        			TestType testType = testTypeDaoImpl.queryTestTypeByNameExcelImport(tmp.getTestTypeName(),tmp.getProjectId());
        			if(testType.getTypeName() == null) { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestType name '"+tmp.getTestTypeName()+"' in Excel TestType column not exist in project "+tmp.getProjectName()+" TestType list .\n</br>";
        				stopFlag = true;
        			} else { tmp.setTestTypeId(testType.getTypeId());}
        		} else {parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Test Type name can't be empty.\n</br>";}

        		//Check Component/SubComponent column rely relationship is correct and not empty.
        		if(tmp.getSubCompName() != null && !"".equals(tmp.getSubCompName()) && tmp.getCompName() != null && !"".equals(tmp.getCompName())) {
        			TestCase caseTmp = testCaseDaoImpl.verifySubCompAndComponentExcelImport(tmp.getSubCompName(),tmp.getCompName());
        			if(caseTmp.getSubCompId() > 0) { 
        				tmp.setSubCompId(caseTmp.getSubCompId());
        			} else { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Component/SubComponent value "+tmp.getSubCompName() +"/"+ tmp.getCompName() +" in in Excel column is not match rely relationship saved in iTMS.\n</br>";
        				stopFlag = true;
        			}
        		}
        		
        		//Verify testCaseAliasId is not null
        		/**
        		if(tmp.getTestCasealiasId() == null || "".equals(tmp.getTestCasealiasId())) {
        			parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseAliasID can't be null. \n</br>";
        			stopFlag = true;
        		}
        		**/
        		/**
        		//Verify testCaseAliasId only include numbers
        		if(tmp.getTestCasealiasId() != null && !"".equals(tmp.getTestCasealiasId()) && tmp.getTestCasealiasId().matches("^\\d+\\.\\d+$")){
        			tmp.setTestCasealiasId(tmp.getTestCasealiasId().substring(0, tmp.getTestCasealiasId().indexOf(".")));
        		} else { 
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseAliasId "+tmp.getTestCasealiasId()+" can only include numbers.\n</br>";
        				stopFlag = true;
        		}
        		**/
        		//Verify testCaseName is not null 
        		if(tmp.getTestCaseName() == null || "".equals(tmp.getTestCaseName())) {
        			parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseName "+tmp.getTestCaseName()+"can't be null. \n</br>";
        			stopFlag = true;
        		}
        		
        		//Check TimeoutString only include numbers and save value to Timeout
        		/**
        		if(tmp.getTimeoutString() != null && !"".equals(tmp.getTimeoutString())) {
            		if(tmp.getTimeoutString().matches("\\d*")) {
            			tmp.setTimeout(Integer.parseInt(tmp.getTimeoutString()));
            		} else if(tmp.getTimeoutString().matches("^\\d+\\.\\d+$")){
            			tmp.setTimeout(Integer.parseInt(tmp.getTimeoutString().substring(0, tmp.getTimeoutString().indexOf("."))));
            		} else { 
            				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Timeout value "+tmp.getTimeoutString()+" can only include numbers.\n</br>";
            				stopFlag = true;
            		}
        		} 
        		**/
        		//If value in Column Operation is Match string update, then will update this test case.
        		if( "Update".equalsIgnoreCase(tmp.getOperation()) && tmp.getOperation() != null && !stopFlag) {
            		//Check TestCaseIdString only include numbers and save value to TestCaseId
            		if(tmp.getTestCaseIdString() != null && !"".equals(tmp.getTestCaseIdString()) && tmp.getTestCaseIdString().matches("\\d*")) {
            			tmp.setTestCaseId(Integer.parseInt(tmp.getTestCaseIdString()));
            		} else if(tmp.getTestCaseIdString() != null && !"".equals(tmp.getTestCaseIdString()) && tmp.getTestCaseIdString().matches("^\\d+\\.\\d+$")) {
            			tmp.setTestCaseId(Integer.parseInt(tmp.getTestCaseIdString().substring(0, tmp.getTestCaseIdString().indexOf("."))));
        			}else { 
            				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseId value "+tmp.getTestCaseIdString()+" can only include numbers.\n</br>";
            				stopFlag = true;
            		}
        			//Verify test case id can't be null 
        			if(tmp.getTestCaseId() > 0 && !stopFlag) {
        				//Verify test case name can't be duplicate
        				if (1 == testCaseDaoImpl.queryByNameCaseIdSize(tmp)) {
        					parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseName "+tmp.getTestCaseName()+" already exist or TestCaseId is not correct. \n</br>";
        					stopFlag = true;
        				}
        				//Verify test case alias id can't be duplicate
        				/**
        				if (2 ==  testCaseDaoImpl.queryByNameCaseIdSize(tmp)) {
        					parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseAliasId "+tmp.getTestCasealiasId()+" already exist. \n</br>";
        					stopFlag = true;
        				}
        				**/
                		//Check Project is not changed, edit case project is not allowed 
        				TestCase projectCheck = testCaseDaoImpl.queryTestCaseById(tmp.getTestCaseId());
        				if (!projectCheck.getProjectName().equalsIgnoreCase(tmp.getProjectName())) {
        					parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Project is not support edit. \n</br>";
        					stopFlag = true;
        				}
        				//If all the check point passed, do update operation
        				if(!stopFlag) {
        					int result = testCaseDaoImpl.updateTestCase(tmp);
        					if(result > 0){
        						successLog = successLog + "Row "+tmp.getRowNumber()+" update success: TestCaseName "+tmp.getTestCaseName()+" update finished. \n</br>";
        					} else {
        						parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseId "+tmp.getTestCasealiasId()+" is not exist in iTMS test case database. \n</br>";
        					}
        				}
        			} 
        			else {
        				parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseId can't be null when update a test case. \n</br>";
        			}
        		}
        		//If value in Column Operation is match string new, then will create a new test case.
        		else if( "New".equalsIgnoreCase(tmp.getOperation()) && tmp.getOperation() != null && !stopFlag ) {
        			tmp.setCreateDate(currentDate);
        			tmp.setTestCaseId(0);
       				//Verify test case name can't be duplicate
    				if (1 == testCaseDaoImpl.queryByNameCaseIdSize(tmp)) {
    					parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseName "+tmp.getTestCaseName()+" already exist. \n</br>";
    					stopFlag = true;
    				}
    				//Verify test case alias id can't be duplicate
    				/**
    				if (2 ==  testCaseDaoImpl.queryByNameCaseIdSize(tmp)) {
    					parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: TestCaseAliasId "+tmp.getTestCasealiasId()+" already exist. \n</br>";
    					stopFlag = true;
    				}
    				**/
    				//If all the check point passed, do test case insert operation
    				if(!stopFlag) {
    					int result = testCaseDaoImpl.addTestCase(tmp);
    					if (result > 0) {
    						successLog = successLog + "Row "+tmp.getRowNumber()+" insert success: TestCaseName "+tmp.getTestCaseName()+" insert finished. \n</br>";
    					} else {
    						parseLog = parseLog + "Row "+tmp.getRowNumber()+" import failed: Unknow system error. \n</br>";
    					}
    				}
        		}
        	}
        }
    }		
/**
 * Function used for analyze imported excel file and analyze test execution result in each row. Import the result to table testresult
 * @param file
 * @param context
 * @throws Exception
 */
	
	public void readTestResultXlsx(File file, ActionContext context) throws Exception {
    	List<TestResult> testResult = new ArrayList<TestResult>();
		//Define default supported column name and save to array Title
		String Title[] = new String[30];
		Title[0] = "ResultId";
		Title[1] = "TestCaseName";
		Title[2] = "ResultType";
		Title[3] = "Log";
		Title[4] = "BugId";
		Title[5] = "BugName";
		Title[6] = "Comments";
		//Title[6] = "ResultId";
		List<String> title = Arrays.asList(Title);

		//Define String TitleTmp to save excel imported Column title sequence
		String TitleTmp[] = new String[30];
        Workbook wb = new XSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println(wb.getSheetName(i));
            for (Row row : sheet) {
            	TestResult result = null;
            	result = new TestResult();
                for (Cell cell : row) {
                	String cellValue = cell.toString().trim();
					//Save Column title in excel to string to check if the column title is valid
					if(row.getRowNum() == 0){
						if (title.contains(cellValue)){
							 TitleTmp[cell.getColumnIndex()] = cellValue ;
						}
					}
					else {
						if(Title[0].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setResultForExcel(cellValue.substring(0, cellValue.indexOf(".")));
						}
						if(Title[1].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setTestCaseName(cellValue);
						}
						if(Title[2].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setResultTypeName(cellValue);
						}
						if(Title[3].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setLog(cellValue);
						}
						if(Title[4].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setBugId(cellValue);
						}
						if(Title[5].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setBugName(cellValue);
						}
						if(Title[6].equals(TitleTmp[cell.getColumnIndex()])) {
							result.setComments(cellValue);
						}
					}
                }
                testResult.add(result);
            }
        }
        String currentDate = Utils.dateFormat(new Date(), null);
        List<ResultTrack> resultTrackList = new ArrayList<ResultTrack>();
        for(TestResult tmp : testResult) {
        	if(!"".equals(tmp.getResultForExcel()) && tmp.getResultForExcel() != null ){
        		tmp.setResultTypeId(testResultDaoImpl.queryResultTypeByName(tmp.getResultTypeName()).getResultTypeId());
        		tmp.setModifyDate(currentDate);
        		ResultTrack resultTrack = new ResultTrack();
        		resultTrack.setTestResultId(Integer.parseInt(tmp.getResultForExcel()));
        		resultTrack.setResultTypeId(tmp.getResultTypeId());
        		resultTrack.setCreateDate(currentDate);
        		resultTrack.setModifyDate(currentDate);
        		resultTrackList.add(resultTrack);
        	}
        }
        //Save result in resulttrack table to save result change list
        testResultDaoImpl.addBatchResultTrack(resultTrackList);
        //Save result in testresult table for final display
		testResultDaoImpl.updateTestResultByExcel(testResult);
    }			
	
	public List<File> getResovleFileList() {
		return resovleFileList;
	}

	public void setResovleFileList(List<File> resovleFileList) {
		this.resovleFileList = resovleFileList;
	}
	
	public int getSubExecutionId() {
		return subExecutionId;
	}
	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}

	public int getInsertSqlCount() {
		return insertSqlCount;
	}

	public void setInsertSqlCount(int insertSqlCount) {
		this.insertSqlCount = insertSqlCount;
	}

	public int getDelSqlCount() {
		return delSqlCount;
	}

	public void setDelSqlCount(int delSqlCount) {
		this.delSqlCount = delSqlCount;
	}

	public int getExcelRowCount() {
		return excelRowCount;
	}

	public void setExcelRowCount(int excelRowCount) {
		this.excelRowCount = excelRowCount;
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public TestTypeDaoImpl getTestTypeDaoImpl() {
		return testTypeDaoImpl;
	}

	public void setTestTypeDaoImpl(TestTypeDaoImpl testTypeDaoImpl) {
		this.testTypeDaoImpl = testTypeDaoImpl;
	}

	/*
	 * public PlatformDaoImpl getPlatformDaoImpl() { return platformDaoImpl; }
	 * 
	 * public void setPlatformDaoImpl(PlatformDaoImpl platformDaoImpl) {
	 * this.platformDaoImpl = platformDaoImpl; }
	 */

	public TeamDaoImpl getTeamDaoImpl() {
		return teamDaoImpl;
	}

	public void setTeamDaoImpl(TeamDaoImpl teamDaoImpl) {
		this.teamDaoImpl = teamDaoImpl;
	}

	public OSDaoImpl getoSDaoImpl() {
		return oSDaoImpl;
	}

	public void setoSDaoImpl(OSDaoImpl oSDaoImpl) {
		this.oSDaoImpl = oSDaoImpl;
	}

	public ComponentDaoImpl getComponentDaoImpl() {
		return componentDaoImpl;
	}

	public void setComponentDaoImpl(ComponentDaoImpl componentDaoImpl) {
		this.componentDaoImpl = componentDaoImpl;
	}
	
	public TestExecutionDaoImpl getTestExecutionDaoImpl() {
		return testExecutionDaoImpl;
	}

	public void setTestExecutionDaoImpl(TestExecutionDaoImpl testExecutionDaoImpl) {
		this.testExecutionDaoImpl = testExecutionDaoImpl;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public FeatureDaoImpl getFeatureDaoImpl() {
		return featureDaoImpl;
	}

	public void setFeatureDaoImpl(FeatureDaoImpl featureDaoImpl) {
		this.featureDaoImpl = featureDaoImpl;
	}

	public TestCaseDaoImpl getTestCaseDaoImpl() {
		return testCaseDaoImpl;
	}

	public void setTestCaseDaoImpl(TestCaseDaoImpl testCaseDaoImpl) {
		this.testCaseDaoImpl = testCaseDaoImpl;
	}

	public SubComponentDaoImpl getSubComponentDaoImpl() {
		return subComponentDaoImpl;
	}

	public void setSubComponentDaoImpl(SubComponentDaoImpl subComponentDaoImpl) {
		this.subComponentDaoImpl = subComponentDaoImpl;
	}

	public int getUpdateSqlCount() {
		return updateSqlCount;
	}

	public void setUpdateSqlCount(int updateSqlCount) {
		this.updateSqlCount = updateSqlCount;
	}

	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}

	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
	}
	
	public String getParseLog() {
		return parseLog;
	}

	public void setParseLog(String parseLog) {
		this.parseLog = parseLog;
	}
	
	public String getSuccessLog() {
		return successLog;
	}

	public void setSuccessLog(String successLog) {
		this.successLog = successLog;
	}
	
}
