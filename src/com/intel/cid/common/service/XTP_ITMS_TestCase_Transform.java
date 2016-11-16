package com.intel.cid.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import com.intel.cid.common.bean.Automation;
import com.intel.cid.common.bean.CaseState;
import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.bean.VersionState;
import com.intel.cid.common.dao.impl.ComponentDaoImpl;
import com.intel.cid.common.dao.impl.FeatureDaoImpl;
import com.intel.cid.common.dao.impl.OSDaoImpl;
import com.intel.cid.common.dao.impl.PlatformDaoImpl;
import com.intel.cid.common.dao.impl.SubComponentDaoImpl;
import com.intel.cid.common.dao.impl.TeamDaoImpl;
import com.intel.cid.common.dao.impl.TestCaseDaoImpl;
import com.intel.cid.common.dao.impl.TestTypeDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class XTP_ITMS_TestCase_Transform {

	private static Logger logger = Logger
			.getLogger(XTP_ITMS_TestCase_Transform.class);

	private List<File> resovleFileList = new ArrayList<File>();

	private Date date = new Date();

	private int insertSqlCount;

	private int updateSqlCount;

	private int delSqlCount;

	private int excelRowCount;

	private JdbcTemplate userJdbcTemplate;

	private TestTypeDaoImpl testTypeDaoImpl;

	private PlatformDaoImpl platformDaoImpl;

	private TeamDaoImpl teamDaoImpl;

	private OSDaoImpl oSDaoImpl;

	private ComponentDaoImpl componentDaoImpl;

	private SubComponentDaoImpl subComponentDaoImpl;
	
	private UserDaoImpl userDaoImpl;

	private FeatureDaoImpl featureDaoImpl;

	private TestCaseDaoImpl testCaseDaoImpl;

	private static Map<String, Integer> testTypeMap = new HashMap<String, Integer>();

	private static Map<String, Integer> platformMap = new HashMap<String, Integer>();
	private static Map<String, Integer> teamMap = new HashMap<String, Integer>();
	private static Map<String, Integer> osMap = new HashMap<String, Integer>();
	private static Map<String, Integer> compMap = new HashMap<String, Integer>();
	private static Map<String, Integer> subCompMap = new HashMap<String, Integer>();
	private static Map<String, Integer> userMap = new HashMap<String, Integer>();
	private static Map<String, Integer> featureMap = new HashMap<String, Integer>();
	private static Map<String, Integer> autoMap = new HashMap<String, Integer>();
	private static Map<String, Integer> caseStateMap = new HashMap<String, Integer>();
	private static Map<String, Integer> versionMap = new HashMap<String, Integer>();

	private static Map<Integer, String> etestTypeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eplatformMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eteamMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eosMap = new HashMap<Integer, String>();
	private static Map<Integer, String> ecompMap = new HashMap<Integer, String>();
	private static Map<Integer, String> esubCompMap = new HashMap<Integer, String>();
	private static Map<Integer, String> euserMap = new HashMap<Integer, String>();
	private static Map<Integer, String> efeatureMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eautoMap = new HashMap<Integer, String>();
	private static Map<Integer, String> ecaseStateMap = new HashMap<Integer, String>();
	private static Map<Integer, String> eversionMap = new HashMap<Integer, String>();

	public void setUp() throws Exception {

		List<TestType> testTypeList = testTypeDaoImpl.queryAllTestTypes();

		for (TestType testType : testTypeList) {
			testTypeMap.put(testType.getTypeName(), testType.getTypeId());
			etestTypeMap.put(testType.getTypeId(), testType.getTypeName());
		}

		List<Platform> platformList = platformDaoImpl.listAllPlatforms();
		for (Platform platform : platformList) {

			platformMap.put(platform.getPlatformName(), platform
					.getPlatformId());
			eplatformMap.put(platform.getPlatformId(), platform
					.getPlatformName());
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

				//
				String fileName = file.getName();
				String[] suffixNames = fileName.split("\\.");
				if (suffixNames[1].equalsIgnoreCase("xls")) {
					readXls(file, context);
				} else {
					readXlsx(file, context);
				}
			}

		}

		// FileUtils.deleteDirectory(destDir);
	}

	public void readXls(File file, ActionContext context) throws Exception {
		logger.info("file" + file);
		InputStream is = new FileInputStream(file);
		String desFileName = file.getName();
		String[] platformNames = desFileName.split("\\.")[0].split("_");
		String platformName = platformNames[platformNames.length - 1];
		List<TestCase> testcaseList = new ArrayList<TestCase>();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			excelRowCount += hssfSheet.getLastRowNum();

			if (hssfSheet == null) {
				continue;
			}

			List<String> colums = new ArrayList<String>();
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);

				if (rowNum == 0) {
					if (hssfRow == null) {
						break;
					}
				}
				TestCase testcase = null;
				if (rowNum > 0) {
					testcase = new TestCase();
					Integer teamId = teamMap.get("STV");
					Integer osId = osMap.get("Linux");		
					Integer autoId = autoMap.get("Auto");
					Integer userId = userMap.get("qxu10");
					Integer platformId = platformMap.get(platformName);
					Integer featureId = featureMap.get("Accel");
					Integer compId = compMap.get("Combo");
					Integer caseStateId = caseStateMap.get("In use");
					Integer versionId = versionMap.get("lastest");
					String project = "STV_" + platformName;
//					testcase.setProject(project);

					if (teamId != null) {
						testcase.setTeamId(teamId);
					}

					if (userId != null) {
						testcase.setUserId(userId);
					}
					
//					if (platformId != null) {
//						testcase.setPlatformId(platformId);
//					}
					if (featureId != null) {
						testcase.setFeatureId(featureId);
					}
					if (compId != null) {
						testcase.setCompId(compId);
					}

					if (versionId != null) {
						testcase.setVersionId(versionId);
					}

					if (caseStateId != null) {
						testcase.setCaseStateId(caseStateId);
					}

					if (osId != null) {
						testcase.setOsId(osId);
					}
					if (autoId != null) {
						testcase.setAutoId(autoId);
					}
				}

				for (int cellNum = 0; cellNum < hssfSheet.getRow(0)
						.getLastCellNum(); cellNum++) {
					if (hssfRow == null) {
						continue;
					}

					HSSFCell hssfCell = hssfRow.getCell(cellNum);

					// obtain all column names
					if (rowNum == 0) {

						if (hssfCell == null) {
							colums.add(null);
							continue;
						} else {
							String cellValue = getXLSValue(hssfCell);

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
								cellValue = "testcasealiasid";
							}
							if (cellValue.equalsIgnoreCase("headline")) {
								cellValue = "testcasename";
							}
							if (cellValue.equalsIgnoreCase("testtype")) {
								cellValue = "testtypeid";
							}

							if (cellValue.equalsIgnoreCase("service")) {
								cellValue = "subcompid";
							}
							colums.add(cellValue.toUpperCase());

						}

					}
					if (rowNum > 0) {

						if (hssfRow == null) {
							continue;
						}
						String label = colums.get(cellNum);
						String value = getXLSValue(hssfCell);
						if (label == null) {
							continue;
						}

						if (label.equalsIgnoreCase("XMS-ID")
								|| label.equalsIgnoreCase("modules")
								|| label.equalsIgnoreCase("platformsToTestOn")) {

							continue;
						}

						// set default value

						if (label.equalsIgnoreCase("testcasename")) {
							testcase.setTestCaseName(value);
						}
						if (label.equalsIgnoreCase("testcasealiasid")) {

							testcase.setTestCasealiasId(value);
						}
						if (label.equalsIgnoreCase("REQUIREMENTID")) {

							testcase.setRequirementId(value);
						}
						if (label.equalsIgnoreCase("executionsteps")) {

							testcase.setExecutionSteps(value);
						}
						if (label.equalsIgnoreCase("TESTFUNCTIONCALL")) {
							testcase.setTestfunctionCall(value);
						}
						if (label.equalsIgnoreCase("PACKETSIZERANGE")) {
							testcase.setPackagesizeRange(value);
						}
						if (label.equalsIgnoreCase("CONFIGFILES")) {
							testcase.setConfigFiles(value);
						}
						if (label.equalsIgnoreCase("DESCRIPTION")) {
							testcase.setDescription(value);
						}

						if (label.equalsIgnoreCase("testtypeid")) {
//							if (value != null) {
//								if (value.contains("fig")) {
//									value = "Configuration";
//								}
//								if (value.contains("stress")) {
//									value = "Stress";
//								}
//								if (value.contains("forman")) {
//									value = "Performance";
//								}
//								if (value.contains("unction")) {
//									value = "Function";
//								}
//								Integer testTypeId = testTypeMap.get(value);
//								if (testTypeId != null) {
//									testcase.setTestTypeId(testTypeId);
//								}
//
//							}
							Integer testTypeId = testTypeMap.get("Integration");
							if (testTypeId != null) {
								testcase.setTestTypeId(testTypeId);
							}

						}

						if (label.equalsIgnoreCase("timeout")) {
							if (!Utils.isNullORWhiteSpace(value)) {
								testcase.setTimeout(Float.valueOf(value)
										.intValue());

							}

						}

					}

				}
				if (testcase != null) {
					testcaseList.add(testcase);
				}

			}

		}

		is.close();
		exportTestCase(testcaseList, context, desFileName);
	}

	private String getXLSValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return null;
		}

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
				String whereClause = "";
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
							if (cellValue.equalsIgnoreCase("platform")) {
								cellValue = "platformid";
							}
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

							} else if (label
									.equalsIgnoreCase("TESTCASEALIASID")) {
								break;

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
								updateColumBuilder.append(whereClause);
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
								StringBuilder delSqlBuilder = new StringBuilder();
								delSqlBuilder
										.append("delete from testcase where testcasealiasid="
												+ "'" + cellValue + "'");
								int totalNum = userJdbcTemplate
										.queryForInt("select count(*) from testcase where testcasealiasid="
												+ "'" + cellValue + "'");
								if (totalNum > 0) {
									needUpdated = true;
								}
								whereClause = " where testcasealiasid=" + "'"
										+ cellValue + "' ;";
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
							} else if (label.equalsIgnoreCase("PLATFORMID")) {
								Integer value = platformMap.get(cellValue);
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

		if (xssfCell == null) {
			return null;
		}
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

	public void exportTestCase(List<TestCase> testcaseList,
			ActionContext context, String filename) throws Exception {

		setUp();

		// first step : get test case table column

		String sql = "select * from testcase";
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		userJdbcTemplate.query(sql, rcch);
		// save all test case table columns
		String[] columnNames = rcch.getColumnNames();
		List<String> exportColumns = new ArrayList<String>();
		// create XssfWorkBook object;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		// create fist sheet
		XSSFSheet xssfSheet = xssfWorkbook.createSheet("testcase");
		// create fist row ,add column name
		XSSFRow xssfRow = xssfSheet.createRow(0);

		// filter columns ;
		for (int i = 0; i < columnNames.length; i++) {

			if (columnNames[i].equalsIgnoreCase("TESTCASEID")) {
				continue;
			} else if (columnNames[i].equalsIgnoreCase("PLATFORMID")) {

				exportColumns.add("PLATFORM");

			} else if (columnNames[i].equalsIgnoreCase("TEAMID")) {

				exportColumns.add("TEAM");

			} else if (columnNames[i].equalsIgnoreCase("OSID")) {

				exportColumns.add("OS");

			} else if (columnNames[i].equalsIgnoreCase("TESTTYPEID")) {

				exportColumns.add("TESTTYPE");

			} else if (columnNames[i].equalsIgnoreCase("COMPID")) {

				exportColumns.add("COMPONENT");

			} else if (columnNames[i].equalsIgnoreCase("SUBCOMPID")) {

				exportColumns.add("SUBCOMPONENT");
			} else if (columnNames[i].equalsIgnoreCase("USERID")) {

				exportColumns.add("OWNER");

			} else if (columnNames[i].equalsIgnoreCase("FEATUREID")) {

				exportColumns.add("FEATURE");

			} else if (columnNames[i].equalsIgnoreCase("TESTCASEALIASID")) {

				exportColumns.add("TESTCASEID");

			} else if (columnNames[i].equalsIgnoreCase("AUTOID")) {

				exportColumns.add("AUTOMATION");

			} else if (columnNames[i].equalsIgnoreCase("CASESTATEID")) {

				exportColumns.add("STATE");

			} else if (columnNames[i].equalsIgnoreCase("VERSIONID")) {

				exportColumns.add("VERSION");

			}

			else {
				exportColumns.add(columnNames[i].toUpperCase());
			}

		}

		// add exprot columns to row[0]
		for (int j = 0; j < exportColumns.size(); j++) {
			xssfRow.createCell(j).setCellValue(exportColumns.get(j));
		}

		// add all test cases into cell
		for (int i = 0; i < testcaseList.size(); i++) {
			TestCase tCase = testcaseList.get(i);
			// row index start with 1;
			int rowIndex = i + 1;
			XSSFRow oXssfRow = xssfSheet.createRow(rowIndex);

//			int platformId = tCase.getPlatformId();
			int platformIndex = exportColumns.indexOf("PLATFORM");
//			if (platformId == 0) {
//
//				oXssfRow.createCell(platformIndex).setCellType(
//						XSSFCell.CELL_TYPE_BLANK);
//			} else {
//
//				oXssfRow.createCell(platformIndex).setCellValue(
//						eplatformMap.get(platformId));
//			}

			int teamId = tCase.getTeamId();
			int teamIndex = exportColumns.indexOf("TEAM");
			if (teamId == 0) {

				oXssfRow.createCell(teamIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(teamIndex).setCellValue(
						eteamMap.get(teamId));
			}
			int osId = tCase.getOsId();
			int osIndex = exportColumns.indexOf("OS");
			if (osId == 0) {

				oXssfRow.createCell(osIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(osIndex).setCellValue(eosMap.get(osId));
			}
			int testtypeId = tCase.getTestTypeId();
			int testtypeIndex = exportColumns.indexOf("TESTTYPE");
			if (testtypeId == 0) {

				oXssfRow.createCell(testtypeIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testtypeIndex).setCellValue(
						etestTypeMap.get(testtypeId));
			}
			int compId = tCase.getCompId();
			int compIndex = exportColumns.indexOf("COMPONENT");
			if (compId == 0) {

				oXssfRow.createCell(compIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(compIndex).setCellValue(
						ecompMap.get(compId));
			}
			int subCompId = tCase.getSubCompId();
			int subCompIndex = exportColumns.indexOf("SUBCOMPONENT");
			if (subCompId == 0) {

				oXssfRow.createCell(subCompIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(subCompIndex).setCellValue(
						esubCompMap.get(subCompId));
			}
			int userId = tCase.getUserId();
			int userIndex = exportColumns.indexOf("OWNER");
			if (userId == 0) {

				oXssfRow.createCell(userIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(userIndex).setCellValue(
						euserMap.get(userId));
			}
			int featureId = tCase.getFeatureId();
			int featureIndex = exportColumns.indexOf("FEATURE");
			if (featureId == 0) {

				oXssfRow.createCell(featureIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(featureIndex).setCellValue(
						efeatureMap.get(featureId));
			}

			String testcaseId = tCase.getTestCasealiasId();
			int testcaseIndex = exportColumns.indexOf("TESTCASEID");
			if (Utils.isNullORWhiteSpace(testcaseId)) {

				oXssfRow.createCell(testcaseIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testcaseIndex).setCellValue(testcaseId);
			}

			String testcaseName = tCase.getTestCaseName();
			int testcaseNameIndex = exportColumns.indexOf("TESTCASENAME");
			if (Utils.isNullORWhiteSpace(testcaseName)) {

				oXssfRow.createCell(testcaseNameIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testcaseNameIndex).setCellValue(
						testcaseName);
			}
			String requirementId = tCase.getRequirementId();
			int requirementIndex = exportColumns.indexOf("REQUIREMENTID");
			if (Utils.isNullORWhiteSpace(requirementId)) {

				oXssfRow.createCell(requirementIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(requirementIndex).setCellValue(
						requirementId);
			}
//			String project = tCase.getProject();
//			int projectIndex = exportColumns.indexOf("PROJECT");
//			if (Utils.isNullORWhiteSpace(project)) {
//
//				oXssfRow.createCell(projectIndex).setCellType(
//						XSSFCell.CELL_TYPE_BLANK);
//			} else {
//				oXssfRow.createCell(projectIndex).setCellValue(project);
//			}
			String configFiles = tCase.getConfigFiles();
			int configFilesIndex = exportColumns.indexOf("CONFIGFILES");
			if (Utils.isNullORWhiteSpace(configFiles)) {

				oXssfRow.createCell(configFilesIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(configFilesIndex).setCellValue(configFiles);
			}
			String executionSteps = tCase.getExecutionSteps();
			int executionStepsIndex = exportColumns.indexOf("EXECUTIONSTEPS");
			if (Utils.isNullORWhiteSpace(executionSteps)) {

				oXssfRow.createCell(executionStepsIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(executionStepsIndex).setCellValue(
						executionSteps);
			}
			String expectedResult = tCase.getExpectedResult();
			int expectedResultIndex = exportColumns.indexOf("EXPECTEDRESULT");
			if (Utils.isNullORWhiteSpace(expectedResult)) {

				oXssfRow.createCell(expectedResultIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(expectedResultIndex).setCellValue(
						expectedResult);
			}

			int autoId = tCase.getAutoId();
			int autoIndex = exportColumns.indexOf("AUTOMATION");
			if (autoId == 0) {

				oXssfRow.createCell(autoIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(autoIndex).setCellValue(
						eautoMap.get(autoId));
			}
			String testscript = tCase.getTestScript();
			int testscriptIndex = exportColumns.indexOf("TESTSCRIPT");
			if (Utils.isNullORWhiteSpace(testscript)) {

				oXssfRow.createCell(testscriptIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testscriptIndex).setCellValue(testscript);
			}
			String testfunctioncall = tCase.getTestfunctionCall();
			int testfunctioncallIndex = exportColumns
					.indexOf("TESTFUNCTIONCALL");
			if (Utils.isNullORWhiteSpace(testfunctioncall)) {

				oXssfRow.createCell(testfunctioncallIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(testfunctioncallIndex).setCellValue(
						testfunctioncall);
			}
			String packetsizerange = tCase.getPackagesizeRange();
			int packetsizerangeIndex = exportColumns.indexOf("PACKETSIZERANGE");
			if (Utils.isNullORWhiteSpace(packetsizerange)) {

				oXssfRow.createCell(packetsizerangeIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(packetsizerangeIndex).setCellValue(
						packetsizerange);
			}

			int timeout = tCase.getTimeout();
			int timeoutIndex = exportColumns.indexOf("TIMEOUT");
			if (timeout == 0) {

				oXssfRow.createCell(timeoutIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(timeoutIndex).setCellValue(timeout);
			}

			int versionId = tCase.getVersionId();
			int versionIndex = exportColumns.indexOf("VERSION");
			if (versionId == 0) {

				oXssfRow.createCell(versionIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(versionIndex).setCellValue(
						eversionMap.get(versionId));
			}
			int casestateId = tCase.getCaseStateId();
			int casestateIndex = exportColumns.indexOf("STATE");
			if (casestateId == 0) {

				oXssfRow.createCell(casestateIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(casestateIndex).setCellValue(
						ecaseStateMap.get(casestateId));
			}

			String description = tCase.getDescription();
			int descriptionIndex = exportColumns.indexOf("DESCRIPTION");
			if (Utils.isNullORWhiteSpace(description)) {

				oXssfRow.createCell(descriptionIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(descriptionIndex).setCellValue(description);
			}

			String createdate = tCase.getCreateDate();
			int createdateIndex = exportColumns.indexOf("CREATEDATE");
			if (Utils.isNullORWhiteSpace(createdate)) {

				oXssfRow.createCell(createdateIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(createdateIndex).setCellValue(createdate);
			}
			String modifydate = tCase.getModifyDate();
			int modifydateIndex = exportColumns.indexOf("MODIFYDATE");
			if (Utils.isNullORWhiteSpace(modifydate)) {

				oXssfRow.createCell(modifydateIndex).setCellType(
						XSSFCell.CELL_TYPE_BLANK);
			} else {
				oXssfRow.createCell(modifydateIndex).setCellValue(modifydate);
			}

		}

		String desDir = Utils.getProjectPath(context) + "des";
		long systemMills = System.currentTimeMillis();
		File desDirFile = new File(desDir);
		if (!desDirFile.exists()) {
			desDirFile.mkdir();
		}
		File saveFile = new File(desDir + File.separator
				+ filename.split("\\.")[0] + systemMills + ".xlsx");
		saveFile.setWritable(true);
		FileOutputStream fos = new FileOutputStream(saveFile);

		xssfWorkbook.write(fos);
		fos.close();

	}

	public List<File> getResovleFileList() {
		return resovleFileList;
	}

	public void setResovleFileList(List<File> resovleFileList) {
		this.resovleFileList = resovleFileList;
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

	public PlatformDaoImpl getPlatformDaoImpl() {
		return platformDaoImpl;
	}

	public void setPlatformDaoImpl(PlatformDaoImpl platformDaoImpl) {
		this.platformDaoImpl = platformDaoImpl;
	}

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

}
