package com.intel.cid.common.action;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.PerformanceResult;
import com.intel.cid.common.bean.Project;
import com.intel.cid.common.bean.SubExecution;
import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.bean.TestExecution;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestResult;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.dao.impl.ReportDaoImp;
import com.intel.cid.utils.JsonUtil;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;
public class ReportCenterAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReportDaoImp reportDaoImp;
	private Logger log = Logger.getLogger(this.getClass());
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private String project;
	private int projectId;
	private String releaseCycle;
	private long currentTime;
	private String startWeek;
	private String endWeek;
	private int startid;
	private int phaseId;
	private int endid;
	private String cycleid;
	private int executionId;
	private Map releaseCycleMap = new HashMap();
	private  JFreeChart chart;
	private int osId;
	private int maxId;
	private int platformId;
	private int buildId;
	private int testPlanId;
	private int subPlanId;
	private int targetId;
	private String executionName;
	private String perfName;
	private String perfContent;
	private int itemSize;
	private int linkSize;
	private int currPage;
	private int subExecutionId;
	
	public String executionReportChartingFrame() throws Exception {
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
	
	public String executionReportChartingDetail() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		HttpServletRequest request = ServletActionContext.getRequest();

		int rowNumber = testPlanService.queryTestPlanSize(projectId);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		// find TestPlanList Data
		List<?> testplanlists = testPlanService.listTestPlanInfoByProjectId(projectId,pageBean);
		
		// find Category by phase Data
		List<?> phaseLists = testPlanService.getTestPlanCountByPhaseList(projectId);
		String json1 = JsonUtil.list2json(phaseLists);
		
		// find Category by plan name Data
		List<?> caseNumberLists = testPlanService.getCaseNumberByTestPlanList(projectId);
		String json2 = JsonUtil.list2json(caseNumberLists);
		
		context.put("pageBean", pageBean);
		context.put("currPage", currPage);
		context.put("testplanlists", testplanlists);
		context.put("phaseLists", json1);
		context.put("caseNumberLists", json2);
		
		return SUCCESS;
	}
	
	public String subExecutionReportChartingDetail() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		SubTestPlan subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		
		int rowNumber = testExecutionService.querySubExecutionCountBySubPlanId(subPlanId);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);
		// find TestPlanList Data
		List<SubExecution> subExecutionList = testExecutionService.listSubExecutionPageBySubPlanId(pageBean, subPlanId);
		
		List<SubExecution> platformList = testExecutionService.listSubExecutionGroupByPlatform(subPlanId);
		
		List<SubExecution> osList = testExecutionService.listSubExecutionGroupByOS(subPlanId);
		
		List<String> platformTypeList = new ArrayList<String>();
		List<String> platformRecycleList = new ArrayList<String>();
		for(SubExecution sub : platformList) {
			if (!platformTypeList.contains(sub.getPlatFormName())) {
				platformTypeList.add(sub.getPlatFormName());
			}
			if (!platformRecycleList.contains(sub.getReleaseCycle())) {
				platformRecycleList.add(sub.getReleaseCycle());
			}
		}
		
		List<String> osTypeList = new ArrayList<String>();
		List<String> osRecycleList = new ArrayList<String>();
		for(SubExecution sub : osList) {
			if (!osTypeList.contains(sub.getOsName())) {
				osTypeList.add(sub.getOsName());
			}
			if (!osRecycleList.contains(sub.getReleaseCycle())) {
				osRecycleList.add(sub.getReleaseCycle());
			}
		}
		
		String json1 = JsonUtil.list2json(platformRecycleList);
		String json2 = JsonUtil.list2json(platformTypeList);
		String json3 = JsonUtil.list2json(platformList);
		String json4 = JsonUtil.list2json(osRecycleList);
		String json5 = JsonUtil.list2json(osTypeList);
		String json6 = JsonUtil.list2json(osList);
		
		context.put("subPlanName", subPlan.getSubPlanName());
		context.put("subExecutionList", subExecutionList);
		context.put("pageBean", pageBean);
		context.put("currPage", currPage);

		context.put("platformRecycleList", json1);
		context.put("platformTypeList", json2);
		context.put("platformList", json3);
		context.put("osRecycleList", json4);
		context.put("osTypeList", json5);
		context.put("osList", json6);
		//context.put("chartDto", chartDto);
		return SUCCESS;
	}
	
	public String caseHealthyReport() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		HttpServletRequest request = ServletActionContext.getRequest();

		SubTestPlan subPlan = subTestPlanService.querySubTestPlanById(subPlanId);
		
		int rowNumber = testExecutionService.queryCaseInfoBySubPlanIdSize(subExecutionId, subPlanId);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNumber, currPage);

		List<?> subplanCaseList = testExecutionService.listCaseInfoBySubPlanId(subExecutionId, subPlanId,pageBean);
		
		// Case PassRate(History)
		List<TestResult> casePassRateList = testExecutionService.listCasePassRateHistory(subExecutionId, subPlanId);
		
		List<String> caseNameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		for(TestResult sub : casePassRateList) {
			if (!caseNameList.contains(sub.getTestCaseName())) {
				caseNameList.add(sub.getTestCaseName());
			}
		}
		
		int passNumber=0;
		int otherNumber=0;
		for (String caseName:caseNameList) {
			passNumber=0;
			otherNumber=0;
			for(TestResult sub : casePassRateList) {
				if (sub.getTestCaseName().equals(caseName)) {
					if ("pass".equals(sub.getResultTypeName())) {
						passNumber = passNumber + 1;
					} else {
						otherNumber = otherNumber + 1;
					}
				}
			}
			
			double passrate= (double)passNumber/(double)(passNumber + otherNumber) * 100;
			valueList.add(String.valueOf(new BigDecimal(passrate).setScale(2, BigDecimal.ROUND_HALF_UP)));
		}
		
		String json1 = JsonUtil.list2json(casePassRateList);
		String json2 = JsonUtil.list2json(caseNameList);
		String json3 = JsonUtil.list2json(valueList);
		
		context.put("subPlanName", subPlan.getSubPlanName());
		context.put("subplanCaseList", subplanCaseList);
		context.put("pageBean", pageBean);
		context.put("currPage", currPage);
		context.put("casePassRateList", json1);
		context.put("caseNameList", json2);
		context.put("valueList", json3);
		context.put("subExecutionId", subExecutionId);
		
		return SUCCESS;
	}
	
	public String performanceAnalysis() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		List<PerformanceResult> resultList = testExecutionService.listPerformanceAnalysis(subExecutionId);

		for (PerformanceResult result:resultList) {
			
		}
		context.put("performanceList", resultList);

		return SUCCESS;
	
	}
	
	public String executionTrendChartingFrame() throws Exception {
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
	
	public String executionTrendChartingDetail() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		TestExecution  testexecution = new TestExecution();
		testexecution.setProjectId(projectId);
		testexecution.setTestPlanId(testPlanId);
		testexecution.setSubPlanId(subPlanId);
		testexecution.setPlatformId(platformId);
		testexecution.setOsId(osId);
		testexecution.setTargetId(targetId);
		testexecution.setUserId(user.getUserId());
		testexecution.setReleaseCycle(releaseCycle);
		testexecution.setExecutionName(executionName);
		testexecution.setBuildId(buildId);
		testexecution.setPhaseId(phaseId);
		
		List<TestExecution> testexecutionlists = testExecutionService.listTestExecutions(testexecution, 10000 );
		List<TestResult> suiteCaseLists = testExecutionService.listTestCaseInSuiteBySubPlanAndTarget(testexecution);
		
		Map<String, String> headLists = new HashMap<String, String>();
		for(TestExecution exe : testexecutionlists){
			headLists.put(exe.getExecutionName(), exe.getExecutionName());
		}
		List<List<String>> table = new ArrayList<List<String>>();

		//Save head in table
		List<String> tableHead = new ArrayList<String>();
		tableHead.add("CaseName");
		tableHead.add("PassRate");
		for (Map.Entry<String, String> head : headLists.entrySet())
		{
    		tableHead.add(head.getKey());
		}
		int size = tableHead.size();
		
		//Save testCaseName column list
		Map<String, String> columnLists = new HashMap<String, String>();
		for(TestResult results : suiteCaseLists) {
			headLists.put(results.getTestCaseName(), results.getTestCaseName());
		}
		
		//Iterate test result data and save to table as new row
		
		List<String> rowHead = new ArrayList<String>();
		for (Map.Entry<String, String> headRow : columnLists.entrySet())
		{
    		rowHead.add(headRow.getKey());
		}
		
		for(TestResult testResult : suiteCaseLists){
			List<String> tbody = new ArrayList<String>();
			testexecution.setTestCaseName(testResult.getTestCaseName());
			List<TestResult> resultLists = testExecutionService.listResultInTestExecutionsByCsaeId(testexecution);

			//Calculate passRate of each case
			int pass = 0;
			String rate = null;
			for(TestResult resultTmp : resultLists) {
				if("pass".equalsIgnoreCase(resultTmp.getResultTypeName())) {
					pass = pass +1;
				}
			}
			if( pass > 0 ){
			    double operation = (double)pass / (double) resultLists.size();
		        BigDecimal big = new BigDecimal(operation);     
		        //big = big.setScale(4, RoundingMode.HALF_UP);  
		        //double passRate = big.doubleValue();
		        double passRate = big.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		        rate = Double.toString(passRate*100);
		        rate = rate+"%";
			} else {
				rate = "0%";
			}
			
			for(String head : tableHead) {
				if(head.equalsIgnoreCase("CaseName")){
					tbody.add(testResult.getTestCaseName());
				} else if(head.equalsIgnoreCase("PassRate")){
					tbody.add(rate);
				} else{
					if(resultLists.size() == 0) {
						tbody.add(" ");
					} else {
						for(TestResult result : resultLists) {
							if(head.equalsIgnoreCase(result.getExecutionName()))
							tbody.add(result.getResultTypeName());
						}
					}
				}
			}
			table.add(tbody);
		}		
		context.put("lists", table);
		context.put("head",tableHead);
		//context.put("testExecution", testExecution);
		return SUCCESS;
	}
	
	public String singleExecutionReport() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		TestPlan testPlan = testPlanService.queryTestPlan(testExecution.getTestPlanId());
		testExecution.setDesc(Utils.replaceHTMLSpecialChars(testExecution.getDesc()));
		List<SubExecution> subExecutionList = testExecutionService.listSubExecutionByExecutionId(executionId);
		List<TestResult> testresultlists = testResultService.listFailedCaseInExecution(executionId);
		List<TestResult> flowlists = testResultService.listCaseExecutionFlow(executionId);

		//Generate data used for line chart
        String startDate = testExecution.getStartDate().substring(0, 10);
        String endDate = testExecution.getEndDate().substring(0, 10);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		d1 = format.parse(startDate);
		d2 = format.parse(endDate);
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		int average = 0;
		if(testExecution.getTotalCases() > 0) {
			average = (int) (diffDays/testExecution.getTotalCases());
		} 
		
		String executionDate = null ;
		String executionValue = null;
		String expectedDate = null;
		String expectedValue = null;

        for(int j = 0; j < flowlists.size(); j++) {
        	if(j == 0){
        		executionValue = Integer.toString(flowlists.get(j).getResultNum());
        		executionDate = "'"+flowlists.get(j).getExecuteDay()+"'";
        	} else {
        		executionValue = executionValue +","+ Integer.toString(flowlists.get(j).getResultNum());
        		executionDate = executionDate +","+ "'"+flowlists.get(j).getExecuteDay()+"'";
        	}
        }
        
		expectedValue = Integer.toString(average);
		expectedDate = "'"+startDate+"'";
        for(int j = 0; j < flowlists.size(); j++) {
        	if(j == 0){
        		if(!startDate.equalsIgnoreCase(flowlists.get(j).getExecuteDay())){
                    expectedValue = expectedValue +","+ Integer.toString(average);
                    expectedDate = expectedDate +","+ "'"+flowlists.get(j).getExecuteDay()+"'";
        		}
        	} else if(j == flowlists.size()-1) {
           		if(!endDate.equalsIgnoreCase(flowlists.get(j).getExecuteDay())){
                    expectedValue = expectedValue +","+ Integer.toString(average);
                    expectedDate = expectedDate +","+ "'"+flowlists.get(j).getExecuteDay()+"'";
        		}
        	} else {
                expectedValue = expectedValue +","+ Integer.toString(average);
                expectedDate = expectedDate +","+ "'"+flowlists.get(j).getExecuteDay()+"'";
        	}
        }
        expectedValue = expectedValue +","+ Integer.toString(average);
        expectedDate = expectedDate +","+ "'"+endDate+"'";
		
		//Generate data used for Pie and Bar chart
		String subPass = null ;
		String subFail = null ;
		String subBlock = null;
		String subNotrun = null;
		String subNames = null;
		String subTotalCases = null;
		for(int i = 0 ; i < subExecutionList.size(); i++){
			if(i == 0) {
				subPass = Integer.toString(subExecutionList.get(i).getPass());
				subFail = Integer.toString(subExecutionList.get(i).getFail());
				subBlock = Integer.toString(subExecutionList.get(i).getBlock());
				subNotrun = Integer.toString(subExecutionList.get(i).getNotRun());
				subTotalCases = Integer.toString(subExecutionList.get(i).getTotalCases());
				subNames = "'"+subExecutionList.get(i).getSubExecutionName()+"'";
			}
			else {
				subPass = subPass +","+ Integer.toString(subExecutionList.get(i).getPass());
				subFail = subFail +","+ Integer.toString(subExecutionList.get(i).getFail());
				subBlock = subBlock +","+ Integer.toString(subExecutionList.get(i).getBlock());
				subNotrun = subNotrun +","+ Integer.toString(subExecutionList.get(i).getNotRun());
				subTotalCases = subTotalCases +","+ Integer.toString(subExecutionList.get(i).getTotalCases());
				subNames = subNames +","+ "'"+subExecutionList.get(i).getSubExecutionName()+"'";
			}
		}
		
		context.put("executionDate", executionDate);
		context.put("executionValue", executionValue);
		context.put("expectedValue", expectedValue);
		context.put("expectedDate", expectedDate);
		
		context.put("subPass", subPass);
		context.put("subFail", subFail);
		context.put("subBlock", subBlock);
		context.put("subNotrun", subNotrun);
		context.put("subTotalCases", subTotalCases);
		context.put("subNames", subNames);
		
		context.put("testExecution", testExecution);
		context.put("testPlan", testPlan);
		context.put("testexecutionlists", subExecutionList);
		context.put("testresultlists",testresultlists);
		return SUCCESS;
	}

/**
 * List sub executions in selected test execution in pie chart.
 * @return
 * @throws Exception
 */
	public String testExecutionPie() throws Exception {

		List<SubExecution> lists = testExecutionService.listSubExecutionByExecutionId(executionId);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
		DefaultPieDataset featureChart = new DefaultPieDataset(); 
		for(SubExecution list : lists) {
			featureChart.setValue(list.getSubExecutionName()+": "+list.getTotalCases(), list.getTotalCases());  
		} 
        chart = ChartFactory.createPieChart3D(testExecution.getExecutionName()+": sub execution distribution", featureChart, true, true, false); 
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGap(0.02);

		return SUCCESS;
	}

/**
 * List test cases executed status in test execution	
 * @return
 * @throws Exception
 */
	
	@SuppressWarnings("deprecation")
	public String executeStatusLine() throws Exception {

		List<TestResult> lists = testResultService.listCaseExecutionFlow(executionId);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		//HH converts hour in 24 hours format (0-23), day calculation
        String startDate = testExecution.getStartDate().substring(0, 10);
        String endDate = testExecution.getEndDate().substring(0, 10);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		d1 = format.parse(startDate);
		d2 = format.parse(endDate);
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		int average = (int) (diffDays/testExecution.getTotalCases());
		
        dataset.addValue(average, "Expected trend", startDate);
        for(TestResult line : lists) {
        	dataset.addValue(line.getResultNum(), "Execution trend", line.getExecuteDay());
        	dataset.addValue(average, "Expected trend", line.getExecuteDay());
        }
        dataset.addValue(average, "Expected trend", endDate);

        chart = ChartFactory.createLineChart(
                testExecution.getExecutionName()+": Execution chart",       // chart title
                "Date",                    // domain axis label
                "Cases",                   // range axis label
                dataset,                   // data
                PlotOrientation.VERTICAL,  // orientation
                true,                      // include legend
                true,                      // tooltips
                false                      // urls
            );

            final CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setNoDataMessage("No data available");
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setAutoRangeIncludesZero(true);
            final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setShapesVisible(true);
            renderer.setSeriesStroke( 0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] {10.0f, 6.0f}, 0.0f) );
            renderer.setSeriesStroke( 1, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] {6.0f, 6.0f}, 0.0f) );
            
		return SUCCESS;
	}
	
/**
 * 	List sub executions in selected test execution in Bar chart.
 * @return
 * @throws Exception
 */
	
	public String testExecutionBar() throws Exception {

		List<SubExecution> lists = testExecutionService.listSubExecutionByExecutionId(executionId);
		TestExecution testExecution= testExecutionService.queryTestExecutionById(executionId);		
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for(SubExecution list : lists) {
			data.setValue(list.getNotRun(), "NotRun", list.getSubExecutionName()); 
			data.addValue(list.getPass(), "Pass", list.getSubExecutionName());
			data.addValue(list.getFail(), "Fail", list.getSubExecutionName());
			data.addValue(list.getBlock(), "Block", list.getSubExecutionName());
		}
		
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("NotRun", new Color(179, 170, 170));
		LegendItem item2 = new LegendItem("Pass", new Color(0x22, 0xFF, 0x22));
		LegendItem item3 = new LegendItem("Fail", new Color(0xFF, 0x22, 0x22));
		LegendItem item4 = new LegendItem("Block", new Color(0xFF, 0xFF, 0x22));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
		
        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        renderer.setItemMargin(0.0);
        Paint p1 = new GradientPaint(
            0.0f, 0.0f, new Color(179, 170, 170), 0.0f, 0.0f, new Color(179, 170, 170)
        );
        renderer.setSeriesPaint(0, p1); 
        Paint p2 = new GradientPaint(
            0.0f, 0.0f, new Color(0x22, 0xFF, 0x22), 0.0f, 0.0f, new Color(0x22, 0xFF, 0x22)
        );
        renderer.setSeriesPaint(1, p2);    
        Paint p3 = new GradientPaint(
            0.0f, 0.0f, new Color(0xFF, 0x22, 0x22), 0.0f, 0.0f, new Color(0xFF, 0x22, 0x22)
        );
        renderer.setSeriesPaint(2, p3);
        Paint p4 = new GradientPaint(
            0.0f, 0.0f, new Color(0xFF, 0xFF, 0x22), 0.0f, 0.0f, new Color(0xFF, 0xFF, 0x22)
        );
        renderer.setSeriesPaint(3, p4);
		
	    chart = ChartFactory.createStackedBarChart(
                testExecution.getExecutionName()+": Execution summary",  // chart title
                " ",                  // domain axis label
                "Cases",                     // range axis label
                data,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                true,                        // legend
                true,                        // tooltips
                false                        // urls
            );
		
	    CategoryPlot plot = (CategoryPlot) chart.getPlot();
	    plot.setNoDataMessage("No data available");
	    plot.setRenderer(renderer);
	    plot.setFixedLegendItems(result);

		return SUCCESS;
	}
	
	public String formateDate(Date date,int gap){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - gap);
		return dft.format(calendar.getTime());
	}
	
	public void getReleaseCyList() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getReleaseListForProject(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListTestPlans() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getlistTestPlans(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListSubTestPlans() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getlistSubTestPlans(testPlanId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListTargets() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getListTargets(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPhaseByProjectIdJSON() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getPhaseByProjectIdJSON(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getBuildTypeByProjectIdJSON() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getBuildTypeByProjectIdJSON(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getTestPlanDurationJSON() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getTestPlanDurationJSON(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getTestTypeByProjectIdJSON() {
		response = ServletActionContext.getResponse();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		List list = reportDaoImp.getTestTypeByProjectIdJSON(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListTestExecution() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getListTestExecution(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getListTestenvs() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getlistTestenv(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListPlatforms() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getListPlatforms(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getListOSByProjectId() {
		response = ServletActionContext.getResponse();
		List list = reportDaoImp.getListOSByProjectId(projectId);
		try {
			String json = JsonUtil.list2json(list);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//==========================================Performance analysis action==============================================
	public String executionPerformanceChartingFrame() throws Exception {
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

	public String executionPerformance1DChartingDetail() throws Exception {
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
		List<TestExecution> testexecutionlists = testExecutionService.listTestExecutions(testexecution, maxId);
		
		//===============================================================================================================//
		//Get performance result of each execution
		Map<String, String> perfHeadList = new HashMap<String, String>();
		Map<String, String> caseListTmp = new HashMap<String, String>();
		for(TestExecution testExecution : testexecutionlists) {
			List<TestResult> testResults = testResultService.listTestResultsByExecutionId(testExecution.getId()); 
			List<TestResult> perfResults = testResultService.listPerformanceResultByExecutionId(testExecution.getId());
			 
			Map<String, String> headLists = new HashMap<String, String>();
			List<List<String>> table = new ArrayList<List<String>>();
			for(TestResult result : perfResults){
				headLists.put(result.getAttributeName(), result.getAttributeName());
				perfHeadList.put(result.getAttributeName(), result.getAttributeName());
			}
			//Save table head
			List<String> tableHead = new ArrayList<String>();
			tableHead.add("Summary");
			tableHead.add("CaseName");
			tableHead.add("Result");
			for (Map.Entry<String, String> head : headLists.entrySet())
			{
	    		tableHead.add(head.getKey());
			}
			int size = tableHead.size();
			
			//Iterate perfResults to analyze data and save to table as new row
			for(TestResult testResult : testResults){
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String tmp = Integer.toString(testResult.getTestCaseId())+",:;"+testResult.getTestCaseName();
						caseListTmp.put(tmp, tmp);
					}
					for(PerformanceResult item : items) {	
						List<String> tbody = new ArrayList<String>();
						String attr = item.getPerformance();
						String[] attrs = attr.split(";");
						
						for(int j=0; j < size ; j++) {
							boolean status = false;
							if(tableHead.get(j).trim().equalsIgnoreCase("Summary")) {
								String summary = "ExecutionOS: "+testResult.getOsName()+" | Platform: "+testResult.getPlatformName();
								tbody.add(summary);
								status = true;
							}
							if(tableHead.get(j).trim().equalsIgnoreCase("CaseName")) {
								tbody.add(testResult.getTestCaseName());
								status = true;
							}
							if(tableHead.get(j).trim().equalsIgnoreCase("Result")) {
								tbody.add(testResult.getResultTypeName());
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
			testExecution.setTableHead(tableHead);
			testExecution.setTable(table);
		}
		//===================================================================================================//
		//Get test case list and performance attribute name list in searched executions
		List<TestExecution> caseList = new ArrayList<TestExecution>();
		List<String> perfAtrributeList = new ArrayList<String>();
		for (Map.Entry<String, String> caseTmp : caseListTmp.entrySet())
		{
			TestExecution tmpExecution = new TestExecution();
			String[] tmp = caseTmp.getKey().split(",:;");
			tmpExecution.setTestCaseId(Integer.parseInt(tmp[0]));
			tmpExecution.setTestCaseName(tmp[1]);
			caseList.add(tmpExecution);
		}
		for (Map.Entry<String, String> caseTmp : perfHeadList.entrySet())
		{
			if(!caseTmp.getKey().trim().equals("") && caseTmp.getKey() != null) {
				perfAtrributeList.add(caseTmp.getKey().trim());
			}
		}
		
		//Defind var used for single case compare Bar chart
		String singleCaseTicks = null;
		String singleCaseData = "" ;
		String singleCaseSeries = "" ;
		//Save performance Attribute list to String singleCaseTicks, all the test cases have same Tick result.
		int perfAttributeListCount = 0 ;
		for(String tmp : perfAtrributeList) {
			if(perfAttributeListCount == 0) {
				singleCaseTicks = "'"+tmp+"'";
			} else {
				singleCaseTicks = singleCaseTicks+",'"+tmp+"'";
			}
			perfAttributeListCount++;
		}

		//Iterate each execution to save all the compare result to single bar chart
		String ticks = singleCaseTicks;
		String data = "" ;
		String series = "" ;
		String chartName = "Compare between Executions";
		int seriesCount = 0;
		String subData = "";
		String caseSubData = "";
		//Generate single case performance compare result between executions
		for(TestExecution testExecution : testexecutionlists) {

			subData = "";
			for(TestExecution singleCase: caseList) {
				caseSubData = "";
				subData = "";
				int caseSubDataCount = 0 ;
				TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(testExecution.getId(),singleCase.getTestCaseId());
				//singleCase.setTestCaseName(testResult.getTestCaseName());
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String attr = items.get(0).getPerformance();
						attrs = attr.split(";");
					} else {
						String attr="0:0;";
						attrs = attr.split(";");
					}
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
				
				//Save each performance attribute value to a javascript array group
				boolean status = true;
				for(String perfAttributeTmp : perfAtrributeList) {
					if(caseSubDataCount == 0) {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = tmpAttrs[1].trim();
								status = false;
							}
						}
						if(status) {
							subData = "0";
						}
					} else {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = subData+","+tmpAttrs[1];
								status = false;
							}
						}
						if(status) {
							subData = subData + ",0";
						}
					}
					caseSubDataCount++;
				}
				
				caseSubData = "["+subData+"]";
				if(seriesCount == 0) {
					series =  "{label:'"+singleCase.getTestCaseName()+" of "+testExecution.getExecutionName()+"'}";
					data = caseSubData;
				} else {
					series =  series + "," + "{label:'"+singleCase.getTestCaseName()+" of "+testExecution.getExecutionName()+"'}";
					data = data +","+caseSubData+"";
				}
				
				seriesCount++;
			}
		}
		if(testexecutionlists.size() == 0) {
			ticks = "0";
			data = "0" ;
			series = "0" ;
		}
		/**
		//Iterate execution to save performance compare result compare by each case.
		int caseListCount = 0;
		//Generate single case performance compare result between executions
		for(TestExecution singleCase: caseList) {
			
			caseListCount = 0;
			singleCaseData = "" ;
			singleCaseSeries = "" ;
			for(TestExecution testExecution : testexecutionlists) {
				
				TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(testExecution.getId(),singleCase.getTestCaseId());
				singleCase.setTestCaseName(testResult.getTestCaseName());
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String attr = items.get(0).getPerformance();
						attrs = attr.split(";");
					} else {
						String attr="0:0;";
						attrs = attr.split(";");
					}
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
				
				String subData = null;
				int subDataCount = 0 ;
				//Save each performance attribute value to a javascript array group
				boolean status = true;
				for(String perfAttributeTmp : perfAtrributeList) {
					if(subDataCount == 0) {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = tmpAttrs[1].trim();
								status = false;
							}
						}
						if(status) {
							subData = "0";
						}
					} else {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = subData+","+tmpAttrs[1];
								status = false;
							}
						}
						if(status) {
							subData = subData + ",0";
						}
					}
					subDataCount++;
				}

				if(testexecutionlists.size()==1){
					singleCaseSeries =  "{label:'"+testExecution.getExecutionName()+"'}";
					singleCaseData = "["+subData+"]";
					
				} else if(caseListCount == 0) {
					singleCaseSeries =  "{label:'"+testExecution.getExecutionName()+"'}";
					singleCaseData = "["+subData+"]";
				} else {
					singleCaseSeries =  singleCaseSeries + "," + "{label:'"+testExecution.getExecutionName()+"'}";
					singleCaseData = singleCaseData +",["+subData+"]";
				}
				caseListCount++ ;
			}
			
			singleCase.setPerfAttributes(singleCaseTicks);
			singleCase.setPerfExecutions(singleCaseSeries);
			singleCase.setPerfData(singleCaseData);
		}
		if(caseList.size() == 0) {
			TestExecution tmpExecution = new TestExecution();
			tmpExecution.setTestCaseId(0);
			tmpExecution.setPerfAttributes("0");
			tmpExecution.setPerfExecutions("0");
			tmpExecution.setPerfData("0");
			caseList.add(tmpExecution);
		}
		**/
		//Get execution/case/performance attribute list
		String executionsString = null;
		String casesString = null;
		String perfAttributesString = null;
		String fullInformation = null;
		int count = 0;
		for(TestExecution testExecution : testexecutionlists) {
			if(count == 0) {
				executionsString = Integer.toString(testExecution.getId());
			} else {
				executionsString = executionsString + "," + Integer.toString(testExecution.getId());
			}
			count++;
		}
		count = 0;
		for(TestExecution testExecution : caseList) {
			if(count == 0) {
				casesString = Integer.toString(testExecution.getTestCaseId());
			} else {
				casesString = casesString + "," + Integer.toString(testExecution.getTestCaseId()) ;
			}
			count++;	
		}
		count = 0;
		for(String tmp : perfAtrributeList) {
			if(count == 0) {
				perfAttributesString = tmp;
			} else {
				perfAttributesString = perfAttributesString + "," + tmp;
			}
			count++;	
		}
		fullInformation = executionsString +";"+ casesString + ";" +perfAttributesString;

		context.put("testexecutionlists", testexecutionlists);
		context.put("caseList", caseList);
		context.put("perfAtrributeList", perfAtrributeList);
		context.put("fullInformation", fullInformation);
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		return SUCCESS;
	}
	
	public String analyzePerformanceByAttribute2DChart1() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		String[] tmp = perfContents[1].split(",");
		int testCaseId = Integer.parseInt(tmp[0]);
		int targetId = Integer.parseInt(tmp[1]);
		int osId = Integer.parseInt(tmp[2]);
		int platformId = Integer.parseInt(tmp[3]);
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		TestCase testCase = testCaseService.queryTestCaseById(testCaseId);
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfContents[2];
		String chartName = testCase.getTestCaseName()+" : "+perfContents[2];
		//Save ticks information.
		
		int count = 0 ;
		for(TestExecution exeTmp : testExecutions) {
			if(count == 0) {
				ticks = "'"+exeTmp.getExecutionName()+"'";
			} else {
				ticks = ticks+",'"+exeTmp.getExecutionName()+"'";
			}
			count++;
		}
		
		//Save data information
		count = 1;
		//Get item list and series
		List<String> itemList = new ArrayList<String>();
		int itemsCount = 0;
		for(TestExecution testExecution : testExecutions) {	
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 && itemsCount ==0){
					itemList.clear();
					for(PerformanceResult item : items) {
						if(count == 1) {
							series =  "{label: 'NO"+count+"'}";
						} else {
							series =  series + "," + "{label: 'NO"+count+"'}";
						}
						itemList.add(Integer.toString(count));
						count++;
					}
					itemsCount++;
				}
			}
		}
		List<String> itemSize = new ArrayList<String>();
		for(int x=1;x<=itemList.size();x++) {
			itemSize.add(Integer.toString(x));
		}

		count = 0;
		for(String itemCount : itemSize) {
			String subData = null;
			int subDataCount = 0 ;
			for(TestExecution testExecution : testExecutions) {	
				//Get performance attribute value list
				TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						for(PerformanceResult item : items) {
							String attr = item.getPerformance();
							attrs = attr.split(";");
							for(String tmpp : attrs) {
								String[] tmpAttrs = tmpp.split(":");
								if(attribute.equalsIgnoreCase(tmpAttrs[0].trim()) && item.getElementIndex() == Integer.parseInt(itemCount)) {							
									if(subDataCount == 0){
										subData = tmpAttrs[1].trim();
									} else {
										subData = subData+","+tmpAttrs[1].trim();
									}
									subDataCount++;
								}
							}
						}
					}
				}
			}
			if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		
		return SUCCESS;
	}
	
	public String analyzePerformanceByAttribute2DChartLine1() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		String[] tmp = perfContents[1].split(",");
		int testCaseId = Integer.parseInt(tmp[0]);
		int targetId = Integer.parseInt(tmp[1]);
		int osId = Integer.parseInt(tmp[2]);
		int platformId = Integer.parseInt(tmp[3]);
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		TestCase testCase = testCaseService.queryTestCaseById(testCaseId);
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfContents[2];
		String chartName = testCase.getTestCaseName()+" : "+perfContents[2];
		//Save ticks information.
		
		int count = 0 ;
		for(TestExecution exeTmp : testExecutions) {
			if(count == 0) {
				ticks = "'"+exeTmp.getExecutionName()+"'";
			} else {
				ticks = ticks+",'"+exeTmp.getExecutionName()+"'";
			}
			count++;
		}
		
		//Save data information
		count = 1;
		//Get item list and series
		List<String> itemList = new ArrayList<String>();
		int itemsCount = 0;
		for(TestExecution testExecution : testExecutions) {	
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 && itemsCount ==0){
					itemList.clear();
					for(PerformanceResult item : items) {
						if(count == 1) {
							series =  "{label: 'NO"+count+"'}";
						} else {
							series =  series + "," + "{label: 'NO"+count+"'}";
						}
						itemList.add(Integer.toString(count));
						count++;
					}
					itemsCount++;
				}
			}
		}
		List<String> itemSize = new ArrayList<String>();
		for(int x=1;x<=itemList.size();x++) {
			itemSize.add(Integer.toString(x));
		}

		count = 0;
		for(String itemCount : itemSize) {
			String subData = null;
			int subDataCount = 0 ;
			for(TestExecution testExecution : testExecutions) {	
				//Get performance attribute value list
				TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						for(PerformanceResult item : items) {
							String attr = item.getPerformance();
							attrs = attr.split(";");
							for(String tmpp : attrs) {
								String[] tmpAttrs = tmpp.split(":");
								if(attribute.equalsIgnoreCase(tmpAttrs[0].trim()) && item.getElementIndex() == Integer.parseInt(itemCount)) {							
									if(subDataCount == 0){
										subData = tmpAttrs[1].trim();
									} else {
										subData = subData+","+tmpAttrs[1].trim();
									}
									subDataCount++;
								}
							}
						}
					} else {
						if(subDataCount == 0){
							subData = "0";
						} else {
							subData = subData+",0";
						}
						subDataCount++;
					}
				} else {
					if(subDataCount == 0){
						subData = "0";
					} else {
						subData = subData+",0";
					}
					subDataCount++;
				}
			}
			if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		
		return SUCCESS;
	}	
	
	public String analyzePerformanceByAttribute2DChart2() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		String[] tmp = perfContents[1].split(",");
		int testCaseId = Integer.parseInt(tmp[0]);
		int targetId = Integer.parseInt(tmp[1]);
		int osId = Integer.parseInt(tmp[2]);
		int platformId = Integer.parseInt(tmp[3]);
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		TestCase testCase = testCaseService.queryTestCaseById(testCaseId);
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfContents[2];
		String chartName = testCase.getTestCaseName()+" : "+perfContents[2];
		//Save ticks information.
		
		int count = 0 ;
		for(TestExecution exeTmp : testExecutions) {
			if(count == 0) {
				series =  "{label: '"+exeTmp.getExecutionName()+"'}";
			} else {
				series =  series + "," + "{label: '"+exeTmp.getExecutionName()+"'}";
			}
			count++;
		}
		
		//Save data information
		count = 1;
		//Get item list and series
		List<String> itemList = new ArrayList<String>();
		for(TestExecution testExecution : testExecutions) {	
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0){
					itemList.clear();
					for(PerformanceResult item : items) {
						if(count == 1) {
							ticks =  "NO"+count;
						} else {
							ticks =  ticks + "," + "NO"+count;
						}
						itemList.add(Integer.toString(count));
						count++;
					}
				}
			}
		}

		count = 0;
		for(TestExecution testExecution : testExecutions) {	
			String subData = null;
			int subDataCount = 0 ;
			//Get performance attribute value list
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			String[] attrs = null;
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 ){
					int itemCount = 1;
					for(PerformanceResult item : items) {
						if(itemCount == 1) {
							ticks =  "'NO"+itemCount+"'";
						} else {
							ticks =  ticks + "," + "'NO"+itemCount+"'";
						}
						itemCount++;
						String attr = item.getPerformance();
						attrs = attr.split(";");
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(attribute.equalsIgnoreCase(tmpAttrs[0].trim())) {							
								if(subDataCount == 0){
									subData = tmpAttrs[1].trim();
								} else {
									subData = subData+","+tmpAttrs[1].trim();
								}
								subDataCount++;
							}
						}
					}
				}
			}
			if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		
		return SUCCESS;
	}

	public String analyzePerformanceByAttribute2DChartLine2() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		String[] tmp = perfContents[1].split(",");
		int testCaseId = Integer.parseInt(tmp[0]);
		int targetId = Integer.parseInt(tmp[1]);
		int osId = Integer.parseInt(tmp[2]);
		int platformId = Integer.parseInt(tmp[3]);
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		TestCase testCase = testCaseService.queryTestCaseById(testCaseId);
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfContents[2];
		String chartName = testCase.getTestCaseName()+" : "+perfContents[2];
		//Save ticks information.
		
		int count = 0 ;
		for(TestExecution exeTmp : testExecutions) {
			if(count == 0) {
				series =  "{label: '"+exeTmp.getExecutionName()+"'}";
			} else {
				series =  series + "," + "{label: '"+exeTmp.getExecutionName()+"'}";
			}
			count++;
		}
		
		//Save data information
		count = 1;
		//Get item list and series
		List<String> itemList = new ArrayList<String>();
		for(TestExecution testExecution : testExecutions) {	
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0){
					itemList.clear();
					for(PerformanceResult item : items) {
						if(count == 1) {
							ticks =  "NO"+count;
						} else {
							ticks =  ticks + "," + "NO"+count;
						}
						itemList.add(Integer.toString(count));
						count++;
					}
				}
			}
		}

		count = 0;
		for(TestExecution testExecution : testExecutions) {	
			String subData = null;
			int subDataCount = 0 ;
			//Get performance attribute value list
			TestResult testResult = testResultService.listTestResultsByExeIdAndCaseIdAndTargetIdAndOsIdAndPlatformId(testExecution.getId(),testCase.getTestCaseId(),targetId,osId,platformId);
			String[] attrs = null;
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 ){
					int itemCount = 1;
					for(PerformanceResult item : items) {
						if(itemCount == 1) {
							ticks =  "'NO"+itemCount+"'";
						} else {
							ticks =  ticks + "," + "'NO"+itemCount+"'";
						}
						itemCount++;
						String attr = item.getPerformance();
						attrs = attr.split(";");
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(attribute.equalsIgnoreCase(tmpAttrs[0].trim())) {							
								if(subDataCount == 0){
									subData = tmpAttrs[1].trim();
								} else {
									subData = subData+","+tmpAttrs[1].trim();
								}
								subDataCount++;
							}
						}
					}
				}
			}
			if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		
		return SUCCESS;
	}
	
	public String analyzePerformanceByAttribute1D() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		List<TestCase> testCases = testCaseService.listTestCasesByIds(perfContents[1].trim());
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfName;
		//Save ticks information.
		int count = 0 ;
		for(TestCase tmp : testCases) {
			if(count == 0) {
				ticks = "'"+tmp.getTestCaseName()+"'";
			} else {
				ticks = ticks+",'"+tmp.getTestCaseName()+"'";
			}
			count++;
		}
		
		//Save series information
		count = 0;
		for(TestExecution testExecution : testExecutions) {
			if(testExecutions.size()==1){
				series =  "{label:'"+testExecution.getExecutionName()+"'}";
				
			} else if(count == 0) {
				series =  "{label:'"+testExecution.getExecutionName()+"'}";
			} else {
				series =  series + "," + "{label:'"+testExecution.getExecutionName()+"'}";
			}
			count++;
		}
		
		//Save data information
		count = 0;
		
		for(TestExecution testExecution : testExecutions) {	
			String subData = null;
			int subDataCount = 0 ;
			
			for(TestCase testCase: testCases) {
				boolean status = true;
				//Get performance attribute value list
				TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(testExecution.getId(),testCase.getTestCaseId());
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String attr = items.get(0).getPerformance();
						attrs = attr.split(";");
					} else {
						String attr="0:0;";
						attrs = attr.split(";");
					}
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
				for(String tmpp : attrs) {
					
					String[] tmpAttrs = tmpp.split(":");
					if(attribute.equalsIgnoreCase(tmpAttrs[0].trim())) {
						if(subDataCount == 0){
							subData = tmpAttrs[1].trim();
						} else {
							subData = subData+","+tmpAttrs[1].trim();
						}
						status = false;
					}
				}
				if(status) {
					if(subDataCount == 0){
						subData = "0";
					} else {
						subData = subData+",0";
					}
				}
				subDataCount++;
			}
			if(testExecutions.size()==1){
				data = "["+subData+"]";
				
			} else if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++ ;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", attribute);
		
		return SUCCESS;
	}
	
	public String analyzePerformanceByCase1D() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		String[] perfContents = perfContent.split(";");
		List<TestExecution> testExecutions = testExecutionService.listTestExecutionsByIds(perfContents[0].trim());
		
		//Defind var used for performance attribute compare Bar chart
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String[] tmp = perfName.split(";");
		String caseId= tmp[0];
		String caseName = tmp[1];
		//Save ticks information.
		int count = 0 ;
		String[] attributes = perfContents[2].split(",");
		for(String attribute : attributes) {
			if(count == 0) {
				ticks = "'"+attribute+"'";
			} else {
				ticks = ticks+",'"+attribute+"'";
			}
			count++;
		}
		
		//Save series information
		count = 0;
		for(TestExecution testExecution : testExecutions) {
			if(testExecutions.size()==1){
				series =  "{label:'"+testExecution.getExecutionName()+"'}";
				
			} else if(count == 0) {
				series =  "{label:'"+testExecution.getExecutionName()+"'}";
			} else {
				series =  series + "," + "{label:'"+testExecution.getExecutionName()+"'}";
			}
			count++;
		}
		
		//Save data information
		int eCount = 0;
		for(TestExecution testExecution : testExecutions) {
			
			TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(testExecution.getId(),Integer.parseInt(caseId));
			String[] attrs = null;
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 ){
					String attr = items.get(0).getPerformance();
					attrs = attr.split(";");
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
			} else {
				String attr="0:0;";
				attrs = attr.split(";");
			}
			
			String subData = null;
			count = 0; ;
			//Save each performance attribute value to a javascript array group
			boolean status = true;
			for(String perfAttributeTmp : attributes) {
				if(count == 0) {
					for(String tmpp : attrs) {
						String[] tmpAttrs = tmpp.split(":");
						if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
							subData = tmpAttrs[1].trim();
							status = false;
						}
					}
					if(status) {
						subData = "0";
					}
				} else {
					for(String tmpp : attrs) {
						String[] tmpAttrs = tmpp.split(":");
						if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
							subData = subData+","+tmpAttrs[1];
							status = false;
						}
					}
					if(status) {
						subData = subData + ",0";
					}
				}
				count++;
			}

			if(testExecutions.size()==1){
				data = "["+subData+"]";
				
			} else if(eCount == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			eCount++ ;
		}
		
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", caseName);
		
		return SUCCESS;
	}
	
	public String analyzePerformanceByExecutionLine1D() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		//Defind var used for performance attribute compare Bar chart
		//var ticks = ['test1','test2','test3'];
		//var data = [[3,7,9],[2,9,4]];
		//var series = [{label:'line1'},{label:'line2'}];
		String ticks = "";
		String data = "" ;
		String series = "" ;
		String attribute = perfName;
		String[] tmpPerfName= perfName.split(";");
		executionId = Integer.parseInt(tmpPerfName[0]);
		executionName = tmpPerfName[1];
		
		TestExecution testExecution = testExecutionService.queryTestExecutionById(executionId);
		List<TestResult> testResults = testResultService.listTestResultsByExecutionId(testExecution.getId()); 
		List<TestResult> perfResults = testResultService.listPerformanceResultByExecutionId(testExecution.getId());
		 
		Map<String, String> attributeListTmp = new HashMap<String, String>();
		for(TestResult result : perfResults){
			attributeListTmp.put(result.getAttributeName(), result.getAttributeName());
		}
		Map<String, String> caseListTmp = new HashMap<String, String>();
		for(TestResult testResult : testResults){
			if(testResult.getResultTrackId() >0) {
				List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
				if(items.size() > 0 ){
					String tmp = Integer.toString(testResult.getTestCaseId())+",:;"+testResult.getTestCaseName();
					caseListTmp.put(tmp, tmp);
				}

				}
		}		

		List<String> caseList = new ArrayList<String>();
		List<String> attributeList = new ArrayList<String>();
		for (Map.Entry<String, String> tmp : caseListTmp.entrySet())
		{
			caseList.add(tmp.getKey().trim());
		}
		
		for (Map.Entry<String, String> tmp : attributeListTmp.entrySet())
		{
			attributeList.add(tmp.getKey().trim());
		}
		
		//Save ticks information
		int count = 0 ;
		for(String tmp : caseList) {
			String[] tmpp = tmp.split(",:;");
			if(count == 0) {
				ticks = "'"+tmpp[1]+"'";
			} else {
				ticks = ticks+",'"+tmpp[1]+"'";
			}
			count++;
		}
		
		//Save series information
		count = 0;
		for(String tmp : attributeList) {
			if(count == 0) {
				series =  "{label:'"+tmp+"'}";
			} else {
				series =  series + "," + "{label:'"+tmp+"'}";
			}
			count++;
		}
		
		//Save data information
		count = 0;	
		for(String tmpAttribute : attributeList) {	
			String subData = null;
			int subDataCount = 0 ;
			
			for(String tmpCase : caseList) {
				boolean status = true;
				String[] tmppp = tmpCase.split(",:;");
				//Get performance attribute value list
				TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(executionId,Integer.parseInt(tmppp[0]));
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String attr = items.get(0).getPerformance();
						attrs = attr.split(";");
					} else {
						String attr="0:0;";
						attrs = attr.split(";");
					}
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
				for(String tmpp : attrs) {
					
					String[] tmpAttrs = tmpp.split(":");
					if(tmpAttribute.equalsIgnoreCase(tmpAttrs[0].trim())) {
						if(subDataCount == 0){
							subData = tmpAttrs[1].trim();
						} else {
							subData = subData+","+tmpAttrs[1].trim();
						}
						status = false;
					}
				}
				if(status) {
					if(subDataCount == 0){
						subData = "0";
					} else {
						subData = subData+",0";
					}
				}
				subDataCount++;
			}
			if(count == 0) {
				data = "["+subData+"]";
			} else {
				data = data +",["+subData+"]";
			}
			count++ ;
		}

		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("name", executionName);

		
		return SUCCESS;
	}
	
	public String analyzePerformanceByExecutionBar1D() throws Exception {
		ActionContext context = ActionContext.getContext();

		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		//context.put("fullInformation", fullInformation);
		return SUCCESS;
	}
	
	public String executionPerformance2DChartingDetail() throws Exception {
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
		List<TestExecution> testexecutionlists = testExecutionService.listTestExecutions(testexecution, maxId);
		
		//===============================================================================================================//
		//Get performance result of each execution
		Map<String, String> perfHeadList = new HashMap<String, String>();
		Map<String, String> caseListTmp = new HashMap<String, String>();
		for(TestExecution testExecution : testexecutionlists) {
			List<TestResult> testResults = testResultService.listTestResultsByExecutionId(testExecution.getId()); 
			List<TestResult> perfResults = testResultService.listPerformanceResultByExecutionId(testExecution.getId());
			 
			Map<String, String> headLists = new HashMap<String, String>();
			List<List<String>> table = new ArrayList<List<String>>();
			for(TestResult result : perfResults){
				headLists.put(result.getAttributeName(), result.getAttributeName());
				perfHeadList.put(result.getAttributeName(), result.getAttributeName());
			}
			//Save table head
			List<String> tableHead = new ArrayList<String>();
			tableHead.add("Summary");
			tableHead.add("CaseName");
			tableHead.add("Result");
			for (Map.Entry<String, String> head : headLists.entrySet())
			{
	    		tableHead.add(head.getKey());
			}
			tableHead.add("Target");
			int size = tableHead.size();
			
			//Iterate perfResults to analyze data and save to table as new row
			for(TestResult testResult : testResults){
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String tmp = Integer.toString(testResult.getTestCaseId())+".:."+testResult.getTestCaseName()
									+".;."+testResult.getTargetId()+".:."+testResult.getTargetName()
									+".;."+testResult.getOsId()+".:."+testResult.getOsName()
									+".;."+testResult.getPlatformId()+".:."+testResult.getPlatformName();
						caseListTmp.put(tmp, tmp);
					}
					for(PerformanceResult item : items) {	
						List<String> tbody = new ArrayList<String>();
						String attr = item.getPerformance();
						String[] attrs = attr.split(";");
						
						for(int j=0; j < size ; j++) {
							boolean status = false;
							if(tableHead.get(j).trim().equalsIgnoreCase("Summary")) {
								String summary = "ExecutionOS: "+testResult.getOsName()+" | Platform: "+testResult.getPlatformName();
								tbody.add(summary);
								status = true;
							}
							if(tableHead.get(j).trim().equalsIgnoreCase("CaseName")) {
								tbody.add(testResult.getTestCaseName());
								status = true;
							}
							if(tableHead.get(j).trim().equalsIgnoreCase("Result")) {
								tbody.add(testResult.getResultTypeName());
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
			testExecution.setTableHead(tableHead);
			testExecution.setTable(table);
		}
		//===================================================================================================//
		//Get test case list and performance attribute name list in searched executions
		List<TestExecution> caseList = new ArrayList<TestExecution>();
		List<String> perfAtrributeList = new ArrayList<String>();
		for (Map.Entry<String, String> caseTmp : caseListTmp.entrySet())
		{
			TestExecution tmpExecution = new TestExecution();
			String[] tmp = caseTmp.getKey().split(".;.");
			String[] tmpCase = tmp[0].split(".:.");
			String[] tmpTarget = tmp[1].split(".:.");
			String[] tmpOS = tmp[2].split(".:.");
			String[] tmpPlatform = tmp[3].split(".:.");
			tmpExecution.setTestCaseId(Integer.parseInt(tmpCase[0]));
			tmpExecution.setTestCaseName(tmpCase[1]);
			tmpExecution.setTargetId(Integer.parseInt(tmpTarget[0]));
			tmpExecution.setTargetName(tmpTarget[1]);
			tmpExecution.setOsId(Integer.parseInt(tmpOS[0]));
			tmpExecution.setOsName(tmpOS[1]);
			tmpExecution.setPlatformId(Integer.parseInt(tmpPlatform[0]));
			tmpExecution.setPlatFormName(tmpPlatform[1]);
			caseList.add(tmpExecution);
		}
		for (Map.Entry<String, String> caseTmp : perfHeadList.entrySet())
		{
			if(!caseTmp.getKey().trim().equals("") && caseTmp.getKey() != null) {
				perfAtrributeList.add(caseTmp.getKey().trim());
			}
		}
		
		//Defind var used for single case compare Bar chart
		String singleCaseTicks = null;
		String singleCaseData = "" ;
		String singleCaseSeries = "" ;
		//Save performance Attribute list to String singleCaseTicks, all the test cases have same Tick result.
		int perfAttributeListCount = 0 ;
		for(String tmp : perfAtrributeList) {
			if(perfAttributeListCount == 0) {
				singleCaseTicks = "'"+tmp+"'";
			} else {
				singleCaseTicks = singleCaseTicks+",'"+tmp+"'";
			}
			perfAttributeListCount++;
		}

		//Iterate each execution to save all the compare result to single bar chart
		String ticks = singleCaseTicks;
		String data = "" ;
		String series = "" ;
		String chartName = "Compare between Executions";
		int seriesCount = 0;
		String subData = "";
		String caseSubData = "";
		//Generate single case performance compare result between executions
		for(TestExecution testExecution : testexecutionlists) {

			subData = "";
			for(TestExecution singleCase: caseList) {
				caseSubData = "";
				subData = "";
				int caseSubDataCount = 0 ;
				TestResult testResult = testResultService.listTestResultsByExecutionIdAndTestCaseId(testExecution.getId(),singleCase.getTestCaseId());
				//singleCase.setTestCaseName(testResult.getTestCaseName());
				String[] attrs = null;
				if(testResult.getResultTrackId() >0) {
					List<PerformanceResult> items = testResultService.queryPerfResult2ByResultTrackId(testResult.getResultTrackId());
					if(items.size() > 0 ){
						String attr = items.get(0).getPerformance();
						attrs = attr.split(";");
					} else {
						String attr="0:0;";
						attrs = attr.split(";");
					}
				} else {
					String attr="0:0;";
					attrs = attr.split(";");
				}
				
				//Save each performance attribute value to a javascript array group
				boolean status = true;
				for(String perfAttributeTmp : perfAtrributeList) {
					if(caseSubDataCount == 0) {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = tmpAttrs[1].trim();
								status = false;
							}
						}
						if(status) {
							subData = "0";
						}
					} else {
						for(String tmpp : attrs) {
							String[] tmpAttrs = tmpp.split(":");
							if(perfAttributeTmp.equalsIgnoreCase(tmpAttrs[0].trim())) {
								subData = subData+","+tmpAttrs[1];
								status = false;
							}
						}
						if(status) {
							subData = subData + ",0";
						}
					}
					caseSubDataCount++;
				}
				
				caseSubData = "["+subData+"]";
				if(seriesCount == 0) {
					series =  "{label:'"+singleCase.getTestCaseName()+" of "+testExecution.getExecutionName()+"'}";
					data = caseSubData;
				} else {
					series =  series + "," + "{label:'"+singleCase.getTestCaseName()+" of "+testExecution.getExecutionName()+"'}";
					data = data +","+caseSubData+"";
				}
				
				seriesCount++;
			}
		}
		if(testexecutionlists.size() == 0) {
			ticks = "0";
			data = "0" ;
			series = "0" ;
		}

		//Get execution/case/performance attribute list
		String executionsString = null;
		String casesString = null;
		String perfAttributesString = null;
		String fullInformation = null;
		int count = 0;
		for(TestExecution testExecution : testexecutionlists) {
			if(count == 0) {
				executionsString = Integer.toString(testExecution.getId());
			} else {
				executionsString = executionsString + "," + Integer.toString(testExecution.getId());
			}
			count++;
		}
		count = 0;
		for(TestExecution testExecution : caseList) {
			if(count == 0) {
				casesString = Integer.toString(testExecution.getTestCaseId());
			} else {
				casesString = casesString + "," + Integer.toString(testExecution.getTestCaseId()) ;
			}
			count++;	
		}
		count = 0;
		for(String tmp : perfAtrributeList) {
			if(count == 0) {
				perfAttributesString = tmp;
			} else {
				perfAttributesString = perfAttributesString + "," + tmp;
			}
			count++;	
		}
		fullInformation = executionsString +";"+ casesString + ";" +perfAttributesString;

		context.put("testexecutionlists", testexecutionlists);
		context.put("caseList", caseList);
		context.put("perfAtrributeList", perfAtrributeList);
		context.put("fullInformation", fullInformation);
		context.put("data", data);
		context.put("ticks", ticks);
		context.put("series", series);
		context.put("attribute", chartName);
		return SUCCESS;
	
	}
	
	public String report_to_file(){
		
		return SUCCESS;
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
	
	public int getSubExecutionId() {
		return subExecutionId;
	}

	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
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

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	public String getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}
	public String getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}
	public int getStartid() {
		return startid;
	}
	public void setStartid(int startid) {
		this.startid = startid;
	}
	public int getEndid() {
		return endid;
	}
	public void setEndid(int endid) {
		this.endid = endid;
	}
	public Map getReleaseCycleMap() {
		return releaseCycleMap;
	}
	public void setReleaseCycleMap(Map releaseCycleMap) {
		this.releaseCycleMap = releaseCycleMap;
	}
	public String getReleaseCycle() {
		return releaseCycle;
	}
	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}
	public String getCycleid() {
		return cycleid;
	}
	public void setCycleid(String cycleid) {
		this.cycleid = cycleid;
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
	public int getExecutionId() {
		return executionId;
	}
	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}
	public  JFreeChart getChart() {  
		return  chart;  
	}  
	public   void  setChart(JFreeChart chart) {  
		this.chart = chart;  
	}
	public int getOsId() {
		return osId;
	}
	public void setOsId(int osId) {
		this.osId = osId;
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
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	
	public int geMaxId() {
		return maxId;
	}
	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}
	
	public int geTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
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
	
	public String getPerfName() {
		return perfName;
	}
	public void setPerfName(String perfName) {
		this.perfName = perfName;
	}
	
	public String getPerfContent() {
		return perfContent;
	}
	public void setPerfContent(String perfContent) {
		this.perfContent = perfContent;
	}
}