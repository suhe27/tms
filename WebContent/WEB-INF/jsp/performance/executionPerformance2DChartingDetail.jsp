<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/performance.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>Performance</title>

		<script type="text/javascript" src="<%=basePath %>js/jqplot/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqplot/jquery.jqplot.js"></script>
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
	    <script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>

        <script src="<%=basePath %>DataTables-1.10.4/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="<%=basePath %>DataTables-1.10.4/media/js/jquery.dataTables.rowGrouping.js" type="text/javascript"></script>
		<script type="text/javascript" charset="utf-8">
		function analyzeByPerfExecutionLine(id,name) {
			var fullInformation = document.getElementById("fullInformation").value.trim();
			var data = id+";"+name;
			var url = "analyzePerformanceByExecutionLine1D.action?perfName="+data+"&perfContent="+fullInformation;
			popupwindow(url,'Analyze', '1000', '400');
		}
		function analyzeByPerfAttribute(type,chart) {
			var executionId ;
	        var exeboxes = document.getElementsByName('testExecution');
	        var len = exeboxes.length;
	        var count = 0;
	        for (var i=0; i<len; i++) {
	        	if(exeboxes[i].checked) {
	        		if(count == 0) {
	        			executionId = exeboxes[i].value;
	        		} else {
	        			executionId = executionId + ","+exeboxes[i].value;
	        		}
	        		count ++;
	        	}
	        }
	        if(count <= 1) {
				alert("Please choose more than one test executions!");
				return;
	        }

			var caseId;
	        var caseboxes = document.getElementsByName('testCase');
	        len = caseboxes.length;
	        count = 0;
	        for (var j=0; j<len; j++) {
	        	if(caseboxes[j].checked) {
	        		caseId = caseboxes[j].value;
	        		count ++;
	        	}
	        }
	        if(count != 1) {
				alert("Please choose one test case!");
				return;
	        }
	        
			var attribute;
	        var attrboxes = document.getElementsByName('attribute');
	        len = attrboxes.length;
	        count = 0;
	        for (var x=0; x<len; x++) {
	        	if(attrboxes[x].checked) {
	        		attribute = attrboxes[x].value;
	        		count ++;
	        	}
	        }
	        if(count != 1) {
				alert("Please choose one attribute!");
				return;
	        }
	        
	        var perfContent = executionId+';'+caseId+';'+attribute;
	        if(type == 1 && chart=='bar') {
				var url = "analyzePerformanceByAttribute2DChart1.action?perfContent="+perfContent;
	        } else if (type == 2 && chart=='bar') {
	        	var url = "analyzePerformanceByAttribute2DChart2.action?perfContent="+perfContent;
	        } else if(type == 1 && chart=='line') {
				var url = "analyzePerformanceByAttribute2DChartLine1.action?perfContent="+perfContent;
	        } else if (type == 2 && chart=='line') {
	        	var url = "analyzePerformanceByAttribute2DChartLine2.action?perfContent="+perfContent;
	        }
			popupwindow(url,'Analyze', '1000', '400');
		}
		function analyzeByPerfCase(id,name) {
			var fullInformation = document.getElementById("fullInformation").value.trim();
			var data = id+";"+name;
			var url = "analyzePerformanceByCase1D.action?perfName="+data+"&perfContent="+fullInformation;
			popupwindow(url,'Analyze', '1000', '400');
		}
		function popupwindow(url, title, w, h) {
			  var left = (screen.width/2)-(w/2);
			  var top = (screen.height/2)-(h/2);
			  return window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
		} 
		function dataTable(id){
			$(document).ready( function () {
				$('#'+id).dataTable({ "bLengthChange": false, "bPaginate": false , "bInfo":false , "bFilter":false}).rowGrouping();
			} );
		}

		function bar(data,series,ticks,id,name){
			$(document).ready(function(){			     
			    var plot1 = $.jqplot(id, data, {
			        seriesDefaults:{
			            renderer:$.jqplot.BarRenderer,
			            rendererOptions: {fillToZero: true}
			        },
			        title:name,
			        series: series,
			        legend: {
			            show: true,
			            placement: 'outsideGrid'
			        },
			        axes: {
			            xaxis: {
			                renderer: $.jqplot.CategoryAxisRenderer,
			                ticks: ticks
			            },
			            yaxis: {
			                pad: 1.05
			            }
			        }
			    });
			});
		}
    	</script>
		
	</head>

<body class="mobile">
	<input type="text" name="fullInformation" id="fullInformation" value="${fullInformation}" style="display:none"/>
	
	<div align="center">
		<br>
		<!-- ================================case compare between execution Bar chart================================== -->
		<table class="mobile" style="width:50%;">
			<thead>
				<tr  class="pageHeader">
     				<th colspan="2">Execution List</th>
     			</tr>
			</thead>
  			<tbody>
     			<c:forEach items="${testexecutionlists}" var="row">
     				<tr>
     					<td><input type="checkbox" name="testExecution" value="${row.id}" /></td>
     					<td title="${row.executionName}">${row.executionName}</td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
		<br><hr/><br>
		<!-- ================================attribute compare between execution Bar chart================================== -->
		<table class="mobile" style="width:50%;">
			<thead>
				<tr  class="pageHeader">
     				<th colspan="2">Performance Attribute List</th>
     			</tr>
			</thead>
  			<tbody>
     			<c:forEach items="${perfAtrributeList}" var="row">
     				<tr>
     					<td><input type="checkbox" name="attribute" value="${row}" /></td>
     					<td title="${row}">${row}</td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
		<br><hr/><br>
		<table class="mobile" style="width:80%;">
			<thead>
				<tr  class="pageHeader">
     				<th></th>
     				<th>Case List</th>
     				<th>Target</th>
     				<th>OS</th>
     				<th>Platform</th>
     			</tr>
			</thead>
  			<tbody>
     			<c:forEach items="${caseList}" var="row">
     				<tr>
     					<td><input type="checkbox" name="testCase" value="${row.testCaseId},${row.targetId},${row.osId},${row.platformId}" /></td>
     					<td title="${row.testCaseName}">${row.testCaseName}</td>
     					<td title="${row.targetName}">${row.targetName}</td>
     					<td title="${row.osName}">${row.osName}</td>
     					<td title="${row.platFormName}">${row.platFormName}</td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
		
		<br><hr/><br>
		<input type="button" class="btn" value="Attribute Bar Chart1" onclick="analyzeByPerfAttribute(1,'bar');"/>
		<input type="button" class="btn" value="Attribute Bar Chart2" onclick="analyzeByPerfAttribute(2,'bar');"/>
		<input type="button" class="btn" value="Attribute Line Chart1" onclick="analyzeByPerfAttribute(1,'line');"/>
		<input type="button" class="btn" value="Attribute Line Chart2" onclick="analyzeByPerfAttribute(2,'line');"/>
		<br>
		<!-- ===========Showing performance table list of each execution========== -->
		<br><hr/><br>	
		<s:iterator value="#testexecutionlists" id="execution" >
			<div>${execution.executionName}</div>
			<table class="mobile" id="${execution.id}" style="width:95%;">
			<thead>
				<tr  class="pageHeader">
     				<c:forEach items="${execution.tableHead}" var="row" varStatus="id">
     					<th title="${row}">${row}</th>
     				</c:forEach>
     			</tr>
			</thead>
  			<tbody>
     		<c:forEach items="${execution.table}" var="rows" varStatus="id">
     			<tr>
     				<c:forEach items="${rows}" var="row" varStatus="ids">
     					<td title="${row}">${row}</td>
     				</c:forEach>
     			</tr>
     		</c:forEach>
			</tbody>   
			</table>
			<br>
			<script class="code" type="text/javascript">
				window.onload = dataTable(${execution.id});
			</script>
		</s:iterator>
	</div>
</body>
</html>

