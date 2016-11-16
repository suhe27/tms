package com.intel.cid.common.action;

import com.intel.cid.common.service.BoardService;
import com.intel.cid.common.service.BoardStateService;
import com.intel.cid.common.service.BuildService;
import com.intel.cid.common.service.ComponentService;
import com.intel.cid.common.service.ExcelResolverService;
import com.intel.cid.common.service.ExecutionOSService;
import com.intel.cid.common.service.ExecutionPhaseService;
import com.intel.cid.common.service.FeatureService;
import com.intel.cid.common.service.MediaParametersService;
import com.intel.cid.common.service.OSService;
import com.intel.cid.common.service.PhaseService;
import com.intel.cid.common.service.PlatformService;
import com.intel.cid.common.service.ProjectSerivce;
import com.intel.cid.common.service.SubComponentService;
import com.intel.cid.common.service.SubTestPlanService;
import com.intel.cid.common.service.TargetService;
import com.intel.cid.common.service.TeamService;
import com.intel.cid.common.service.TestCaseService;
import com.intel.cid.common.service.TestExecutionService;
import com.intel.cid.common.service.TestPlanService;
import com.intel.cid.common.service.TestResultService;
import com.intel.cid.common.service.TestResultXmlResolverService;
import com.intel.cid.common.service.TestSuiteService;
import com.intel.cid.common.service.TestTypeService;
import com.intel.cid.common.service.TestUnitService;
import com.intel.cid.common.service.TestEnvService;
import com.intel.cid.common.service.UserService;
import com.intel.cid.common.service.XTP_ITMS_TestCase_Transform;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	protected BoardService boardService;

	protected ComponentService componentService;

	protected SubComponentService subComponentService;
	protected FeatureService featureService;

	protected OSService oSService;
	
	protected ExecutionOSService executionOSService;

	protected PlatformService platformService;
	
	protected ExecutionPhaseService executionPhaseService;
	
	protected TeamService teamService;

	protected TestTypeService testTypeService;

	protected TestCaseService testCaseService;

	protected TestSuiteService testSuiteService;

	protected UserService userService;

	protected SubTestPlanService subTestPlanService;

	protected TestPlanService testPlanService;

	protected TestResultService testResultService;

	protected BoardStateService boardStateService;

	protected TestUnitService testUnitService;

	protected ExcelResolverService excelResolverService;

	protected XTP_ITMS_TestCase_Transform transformService;

	protected MediaParametersService mediaParametersService;
	protected PhaseService phaseService;
	
	protected ProjectSerivce projectSerivce;
	protected TargetService targetService;
	protected TestExecutionService testExecutionService;
	protected TestEnvService testEnvService;
	protected BuildService buildService; 
	
	public TestEnvService getTestEnvService() {
		return testEnvService;
	}

	public void setTestEnvService(TestEnvService testEnvService) {
		this.testEnvService = testEnvService;
	}

	public ExcelResolverService getExcelResolverService() {
		return excelResolverService;
	}

	public void setExcelResolverService(
			ExcelResolverService excelResolverService) {
		this.excelResolverService = excelResolverService;
	}

	protected TestResultXmlResolverService testResultXmlResolverService;

	public BoardService getBoardService() {
		return boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	public ComponentService getComponentService() {
		return componentService;
	}

	public void setComponentService(ComponentService componentService) {
		this.componentService = componentService;
	}

	public SubComponentService getSubComponentService() {
		return subComponentService;
	}

	public void setSubComponentService(SubComponentService subComponentService) {
		this.subComponentService = subComponentService;
	}

	public FeatureService getFeatureService() {
		return featureService;
	}

	public void setFeatureService(FeatureService featureService) {
		this.featureService = featureService;
	}

	public OSService getoSService() {
		return oSService;
	}

	public void setoSService(OSService oSService) {
		this.oSService = oSService;
	}

	public PlatformService getPlatformService() {
		return platformService;
	}

	public void setPlatformService(PlatformService platformService) {
		this.platformService = platformService;
	}

	public TeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public TestTypeService getTestTypeService() {
		return testTypeService;
	}

	public void setTestTypeService(TestTypeService testTypeService) {
		this.testTypeService = testTypeService;
	}

	public TestCaseService getTestCaseService() {
		return testCaseService;
	}

	public void setTestCaseService(TestCaseService testCaseService) {
		this.testCaseService = testCaseService;
	}

	public TestSuiteService getTestSuiteService() {
		return testSuiteService;
	}

	public void setTestSuiteService(TestSuiteService testSuiteService) {
		this.testSuiteService = testSuiteService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SubTestPlanService getSubTestPlanService() {
		return subTestPlanService;
	}

	public void setSubTestPlanService(SubTestPlanService subTestPlanService) {
		this.subTestPlanService = subTestPlanService;
	}

	public TestPlanService getTestPlanService() {
		return testPlanService;
	}

	public void setTestPlanService(TestPlanService testPlanService) {
		this.testPlanService = testPlanService;
	}

	public TestResultService getTestResultService() {
		return testResultService;
	}

	public void setTestResultService(TestResultService testResultService) {
		this.testResultService = testResultService;
	}

	public BoardStateService getBoardStateService() {
		return boardStateService;
	}

	public void setBoardStateService(BoardStateService boardStateService) {
		this.boardStateService = boardStateService;
	}

	public TestUnitService getTestUnitService() {
		return testUnitService;
	}

	public void setTestUnitService(TestUnitService testUnitService) {
		this.testUnitService = testUnitService;
	}

	public TestResultXmlResolverService getTestResultXmlResolverService() {
		return testResultXmlResolverService;
	}

	public void setTestResultXmlResolverService(
			TestResultXmlResolverService testResultXmlResolverService) {
		this.testResultXmlResolverService = testResultXmlResolverService;
	}

	public XTP_ITMS_TestCase_Transform getTransformService() {
		return transformService;
	}

	public void setTransformService(XTP_ITMS_TestCase_Transform transformService) {
		this.transformService = transformService;
	}

	public MediaParametersService getMediaParametersService() {
		return mediaParametersService;
	}

	public void setMediaParametersService(
			MediaParametersService mediaParametersService) {
		this.mediaParametersService = mediaParametersService;
	}

	public ProjectSerivce getProjectSerivce() {
		return projectSerivce;
	}

	public void setProjectSerivce(ProjectSerivce projectSerivce) {
		this.projectSerivce = projectSerivce;
	}

	public TargetService getTargetService() {
		return targetService;
	}

	public void setTargetService(TargetService targetService) {
		this.targetService = targetService;
	}

	public TestExecutionService getTestExecutionService() {
		return testExecutionService;
	}

	public void setTestExecutionService(
			TestExecutionService testExecutionService) {
		this.testExecutionService = testExecutionService;
	}

	public PhaseService getPhaseService() {
		return phaseService;
	}

	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

	public BuildService getBuildService() {
		return buildService;
	}

	public void setBuildService(BuildService buildService) {
		this.buildService = buildService;
	}

	public ExecutionOSService getExecutionOSService() {
		return executionOSService;
	}

	public void setExecutionOSService(ExecutionOSService executionOSService) {
		this.executionOSService = executionOSService;
	}

	public ExecutionPhaseService getExecutionPhaseService() {
		return executionPhaseService;
	}

	public void setExecutionPhaseService(ExecutionPhaseService executionPhaseService) {
		this.executionPhaseService = executionPhaseService;
	}




	

}
