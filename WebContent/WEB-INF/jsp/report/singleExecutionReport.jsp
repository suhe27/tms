<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Report</title>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/jquery.jqplot.js"></script>
		<!--[if IE]><script language="javascript" type="text/javascript" src="<%=basePath %>js/jqplot/excanvas.js"></script><![endif]-->
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.barRenderer.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.pieRenderer.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.highlighter.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.cursor.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.pointLabels.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></script>
		
		<script type="text/javascript" src="<%=basePath %>js/jqplot/syntaxhighlighter/scripts/shCore.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/jqplot/syntaxhighlighter/scripts/shBrushJScript.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/jqplot/syntaxhighlighter/scripts/shBrushXml.min.js"></script>
	    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqplot/jquery.jqplot.min.css" /> 

<script class="code" type="text/javascript">

//Pie chart generation script
$(document).ready(function(){
	
	var subTotalCases = [${subTotalCases}];
	var subNames = [${subNames}];
	var subData = [];
	for (var i in subNames) {
		subData[i] = [subNames[i],subTotalCases[i]];
	}

  var pie = jQuery.jqplot ('summaryPie', [subData], 
    { 
	  title:'Sub Execution Distribution',
      seriesDefaults: {
        renderer: jQuery.jqplot.PieRenderer, 
        rendererOptions: {
          showDataLabels: true,
          dataLabels: 'value'
        }
      }, 
      legend: { show:true, location: 'e' }
    }
  );
});
//Bar chart generation script
$(document).ready(function(){
	var subPass = [${subPass}];
	var subFail = [${subFail}];
	var subBlock = [${subBlock}];
	var subNotrun = [${subNotrun}];
	var ticks = [${subNames}];
	bar = $.jqplot('summaryBar', [subPass, subFail, subBlock, subNotrun], {
	title:'Execution Result Summary',
    stackSeries: true,
    captureRightClick: true,
    seriesDefaults:{
      renderer:$.jqplot.BarRenderer,
      rendererOptions: {
          barMargin: 30 
      },
      pointLabels:{show:true, stackedValue: true}
    },
    series:[
            {label:'Pass'},
            {label:'Fail'},
            {label:'Block'},
            {label:'Notrun'}
        ],
        legend: {
            show: true,
            placement: 'outsideGrid'
    },
   	seriesColors: [ "#00FF00", "#FF0000", "#FFFF00", "#BEBEBE"], 
    axes: {
      xaxis: {
          renderer: $.jqplot.CategoryAxisRenderer,
          ticks: ticks
      },
      yaxis: {
    	  label: "Cases"
      }
    },
    legend: {
      show: true,
      location: 'e',
      placement: 'outside'
    }      
  });
});
//Line chart generation script
$(document).ready(function(){
	var executionDate = [${executionDate}];
	var executionValue = [${executionValue}];
	var expectedDate = [${expectedDate}];
	var expectedValue = [${expectedValue}];
	var expectedLine = [];
	var executionLine = [];
	//alert(executionDate.lenth);
	if(executionDate.length > 0) {
		for (var i in executionDate) {
			executionLine[i] = [executionDate[i],executionValue[i]];
		}
	} else {
		executionLine[0] = [expectedDate[0],expectedValue[0]];
	}
	for (var i in expectedDate) {
		expectedLine[i] = [expectedDate[i],expectedValue[i]];
	}
	var line = $.jqplot('executeStatus', [executionLine,expectedLine], {
      title:'Execution Status',
      axes:{
        xaxis:{
          renderer:$.jqplot.DateAxisRenderer,
          label: "Date"
        },
        yaxis:{
          label: "Cases"
        }
      },
      series:[
              {label:'Execution trend'},
              {label:'Expected trend'}
          ],
          legend: {
              show: true,
              placement: 'outsideGrid'
      },
      highlighter: {
        show: true,
        sizeAdjust: 7.5
      },
      cursor: {
        show: false
      }
  });
});
</script>    
</head>

<body class="mobile">
		<h6  align="center">${testExecution.executionName}</h6>
		<div align="center">
		<table class="mobile" style="width:95%;">
			<thead>
			<tr  class="pageHeader">
		  		<th style="width:50%;" colspan="2">Execution Detail</th>
		  		<th style="width:50%;">Sub execution distribution</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="thead" >Execution Name</td>
				<td class="tdata">${testExecution.executionName}</td>
				<td rowspan="14" style="width:80%;">
					<div align="center"><div id="summaryPie" style="width:90%; height:50%"></div></div>
				</td>
			</tr>
			<tr>
				<td class="thead">Test Plan</td>
				<td class="tdata">${testExecution.testPlanName}</td>
			</tr>
			<tr>
				<td class="thead">Test Plan Duration</td>
				<td class="tdata">${testPlan.startDate} ~ ${testPlan.endDate}</td>
			</tr>
			<tr>
				<td class="thead">Release Cycle</td>
				<td class="tdata">${testExecution.releaseCycle}</td>
			</tr>
			<tr>
				<td class="thead">Execution Duration</td>
				<td class="tdata">${testExecution.startDate} ~ ${testExecution.endDate}</td>
			</tr>
			<tr>
				<td class="thead">Execution Type</td>
				<td class="tdata">${testExecution.phaseName}</td>
			</tr>
			<tr>
				<td class="thead">Build Type</td>
				<td class="tdata">${testExecution.buildTypeName}</td>
			</tr>
			<tr>
				<td class="thead">Pass</td>
				<td class="tdata">${testExecution.pass}</td>
			</tr>
			<tr>
				<td class="thead">Fail</td>
				<td class="tdata">${testExecution.fail}</td>
			</tr>
			<tr>
				<td class="thead">Block</td>
				<td class="tdata">${testExecution.block}</td>
			</tr>
			<tr>
				<td class="thead">Not Run</td>
				<td class="tdata">${testExecution.notRun}</td>
			</tr>
			<tr>
				<td class="thead">Total Cases</td>
				<td class="tdata">${testExecution.totalCases}</td>
			</tr>
			<tr>
				<td class="thead">Pass Rate</td>
				<td class="tdata"><fmt:formatNumber value="${testExecution.passrate*100}" maxFractionDigits="0"/>%</td>
			</tr>
			<tr>
				<td class="thead" >Executed Rate</td>
				<td class="tdata"><fmt:formatNumber value="${testExecution.executeRate*100}" maxFractionDigits="0"/>%</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align:left">${testExecution.desc}</td>
			</tr>
			</tbody>
		</table>
		<!-- Execution bar chart summary -->
		<br>
		<table class="mobile" style="width:95%;">
			<tbody>
			<tr class="odd">
				<td>
					<div align="center"><div id="summaryBar" style="width:80%; height:50%"></div></div>
				</td>
			</tr>
			</tbody>
		</table>
		<!-- Execution status in line chart -->
		<br>
		<table class="mobile" style="width:95%;">
			<tbody>
			<tr class="odd">
				<td>
					<div align="center"><div id="executeStatus" style="width:80%; height:50%"></div></div>
				</td>
			</tr>
			</tbody>
		</table>		
		<!-- Sub execution list in table -->
		<br>
		<display:table name="testexecutionlists" class="mobile" id="row" style="width:95%;" cellpadding="0" cellspacing="0">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 12%;">Sub Execution Name</th>
				<th style="width: 10%;">Sub Plan Name</th>
				<th style="width: 10%;">Platform</th>
				<th style="width: 10%;">ExecutionOS</th>
				<th style="width: 5%;">Pass</th>
				<th style="width: 5%;">Fail</th>
				<th style="width: 5%;">Not Run</th>
				<th style="width: 5%;">Block</th>
				<th style="width: 10%;">Total Cases</th>
				<th style="width: 8%;">Pass Rate</th>
				<th style="width: 10%;">Executed Rate</th>
				<th style="width: 10%;">DueDate</th>
			</tr>
			</thead></display:caption>		
			<display:column  property="subExecutionName" />
			<display:column  property="subPlanName" />
			<display:column  property="platFormName" />
			<display:column  property="osName" />
			<display:column  style="background:green;" property="pass" />
			<display:column  style="background:red;" property="fail" />
			<display:column  style="background:white;" property="notRun" />
			<display:column  style="background:yellow;" property="block" />
			<display:column  property="totalCases" />
			<display:column>
				<fmt:formatNumber value="${row.passrate*100}" maxFractionDigits="0"/>% 
			</display:column>
			<display:column><fmt:formatNumber value="${row.executeRate*100}" maxFractionDigits="0"/>%</display:column>
			<display:column  property="dueDate" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>		
		<br>
		<!-- Failed test case in execution -->
		<display:table name="testresultlists" class="mobile" id="row" style="width:95%;" cellpadding="0" cellspacing="0">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 13%;">Sub Execution Name</th>
				<th style="width: 15%;">Test Case Name</th>
				<th style="width: 7%;">Result</th>
				<th style="width: 10%;">Log</th>
				<th style="width: 15%;">Bug Id</th>
				<th style="width: 10%;">Comments</th>
				<th style="width: 10%;">Target</th>
				<th style="width: 10%;">Platform</th>
				<th style="width: 10%;">ExecutionOS</th>
			</tr>
			</thead></display:caption>
			<display:column  property="subExecutionName" />
			<display:column  property="testCaseName" />
			<display:column style="background:red;" property="resultTypeName" />
			<display:column  property="log" />
			<display:column  >
				<a href= "javascript:window.open('${row.bugId}');" style="text-decoration:underline;color:#0059fd;">
					<s:if test="#attr.row.bugName == null ">${row.bugId}</s:if>
					<s:else>${row.bugName}</s:else>
				</a>
			</display:column>
			<display:column  property="comments" />
			<display:column  property="targetName" />
			<display:column  property="platformName" />
			<display:column  property="osName" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<br>
		</div>
		<br>
 </body>

</html>