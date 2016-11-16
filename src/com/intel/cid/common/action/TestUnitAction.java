package com.intel.cid.common.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.ResultType;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class TestUnitAction extends BaseAction {

	private static final long serialVersionUID = 7817832389627220184L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TestUnitAction.class);

	private TestUnit testUnit;

	private int testUnitId;

	private int resultId;

	private int itemSize;

	private int linkSize;

	private int currPage;

	private String resultTypeId = "0"; 

	private String passRate;

	private String multiResult;

	private SubTestPlan subPlan;

	private String dueDate;
 
	private String unitName;

	/*
	 * public String toUpdateTestUnitRs() throws Exception {
	 * 
	 * ActionContext context = ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user"); if (user == null) { return LOGIN; }
	 * testUnit = testUnitService.queryTestUnit(testUnitId);
	 * 
	 * subPlan = subTestPlanService.querySubTestPlanById(testUnit
	 * .getSubPlanId());
	 * 
	 * Map<String, String> filterMap = new HashMap<String, String>();
	 * 
	 * filterMap.put("resultTypeId", resultTypeId); filterMap.put("testUnitId",
	 * String.valueOf(testUnitId)); int rowNumber =
	 * testResultService.queryTestResultSize(filterMap);
	 * 
	 * PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
	 * PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
	 * testResultList = testResultService.listTestResultByPage(pageBean,
	 * filterMap);
	 * 
	 * List<ResultType> resultTypeList = testResultService.listResultTypes();
	 * 
	 * context.put("resultTypeList", resultTypeList);
	 * 
	 * context.put("resultTypeId", resultTypeId); context.put("testUnitId",
	 * testUnitId); context.put("pageBean", pageBean); return SUCCESS; }
	 */
	public String toUpdateTestUnit() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		testUnit = testUnitService.queryTestUnit(testUnitId);

		subPlan = subTestPlanService.querySubTestPlanById(testUnit
				.getSubPlanId());
		long gap = 0L;
		String today = Utils.dateFormat(System.currentTimeMillis(),
				Constant.DATE_FORMAT_YMD);
		String endDate = subPlan.getDueDate();
		if (Utils.dateCompare(today, endDate, Constant.DATE_FORMAT_YMD) >= 0) {
			gap = -1; // test unit duedate can not update
		} else {
			gap = 1; // test unit can update
		}
		context.put("gap", gap);
		return SUCCESS;
	}

	public String updateTestUnit() throws Exception {
		
		ActionContext context = ActionContext.getContext();		
		String modifyDate = Utils.dateFormat(new Date(), null);
		testUnit = testUnitService.queryTestUnit(testUnitId);		
		boolean isUniqueTestUnit = testUnitService.isUniqueTestUnit(testUnit.getSubPlanId(), testUnitId, unitName);
	   if(!isUniqueTestUnit){
		   
		   context.put(Constant.ERRORMSG,
				  unitName+" is already exists!");
		   return Constant.ERROR;
	   }
				
		testUnit.setTestUnitName(unitName);
		testUnit.setDueDate(dueDate);
		testUnit.setModifyDate(modifyDate);
		testUnitService.updateTestUnit(testUnit);

		return SUCCESS;
	}

	/*
	 * public String updateBatchTestResult() throws Exception {
	 * 
	 * ActionContext context = ActionContext.getContext(); User user = (User)
	 * context.getSession().get("user"); if (user == null) { return LOGIN; } if
	 * (multiResult == null) {
	 * 
	 * context.put(Constant.ERRORMSG,
	 * "you  must select at least one testresult to update.");
	 * 
	 * return Constant.ERROR; }
	 * 
	 * 
	 * String[] testresults = multiResult.split(","); Map<Integer, TestResult>
	 * testResultsMap = new HashMap<Integer, TestResult>(); List<TestResult>
	 * updateTestResultList = new ArrayList<TestResult>(); for (TestResult
	 * result : testResultList) { testResultsMap.put(result.getResultId(),
	 * result); } String modifyDate = Utils.dateFormat(new Date(), null); for
	 * (String resultId : testresults) {
	 * 
	 * TestResult vaResult = testResultsMap.get(Integer.parseInt(resultId
	 * .trim())); vaResult.setModifyDate(modifyDate);
	 * updateTestResultList.add(vaResult); }
	 * 
	 * testResultService.updateBatchTestResult(testUnit, updateTestResultList);
	 * 
	 * return SUCCESS;
	 * 
	 * }
	 */

	public String showTestResult() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		TestResult testResult = testResultService.queryTestResult(resultId);

		List<ResultType> resultTypeList = testResultService.listResultTypes();

		context.put("testResult", testResult);
		context.put("resultTypeList", resultTypeList);

		return SUCCESS;

	}

	public TestUnit getTestUnit() {
		return testUnit;
	}

	public void setTestUnit(TestUnit testUnit) {
		this.testUnit = testUnit;
	}

	public int getTestUnitId() {
		return testUnitId;
	}

	public void setTestUnitId(int testUnitId) {
		this.testUnitId = testUnitId;
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

	public String getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(String resultTypeId) {
		this.resultTypeId = resultTypeId;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public String getMultiResult() {
		return multiResult;
	}

	public void setMultiResult(String multiResult) {
		this.multiResult = multiResult;
	}

	public SubTestPlan getSubPlan() {
		return subPlan;
	}

	public void setSubPlan(SubTestPlan subPlan) {
		this.subPlan = subPlan;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
