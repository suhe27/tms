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
		function analyzeByPerfAttribute(name) {
			var fullInformation = document.getElementById("fullInformation").value.trim();
			var url = "analyzePerformanceByAttribute1D.action?perfName="+name+"&perfContent="+fullInformation;
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
		<!-- =========================================Execution summary bar=========================================== -->
		<div align="center">
			<div id="executionsBar" style="width:90%; height:70%"></div>
		</div>
		<script class="code" type="text/javascript">
			window.onload = bar([${data}],[${series}],[${ticks}],'executionsBar','${attribute}');
		</script>
		<br><hr/><br>
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
     					<td title="${row.executionName}">${row.executionName}</td>
     					<td><a href="javascript:analyzeByPerfExecutionLine('${row.id}','${row.executionName}');" style="color:#0059fd;">Analyze Line Chart</a></td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
		<br><hr/><br>
		<!-- ================================case compare between execution Bar chart================================== -->
		<table class="mobile" style="width:50%;">
			<thead>
				<tr  class="pageHeader">
     				<th colspan="2">Case List</th>
     			</tr>
			</thead>
  			<tbody>
     			<c:forEach items="${caseList}" var="row">
     				<tr>
     					<td title="${row.testCaseName}">${row.testCaseName}</td>
     					<td><a href="javascript:analyzeByPerfCase('${row.testCaseId}','${row.testCaseName}');" style="color:#0059fd;">Analyze</a></td>
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
     					<td title="${row}">${row}</td>
     					<td><a href="javascript:analyzeByPerfAttribute('${row}');" style="color:#0059fd;">Analyze</a></td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
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
		
		<!-- ================================Single case performance result compare between execution Bar chart================================== -->	
		<!--		
		<br><hr/><br>
		<display:table name="caseList" class="mobile" id="row" style="width:95%;">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 100%;">Sginle Case compare report</th>
			</tr>
			</thead></display:caption>
			<display:column> 
				<div align="center">
					<div id="singleCaseBar${row.testCaseId}" style="width:1000px; height:400px"></div></div>
				<script class="code" type="text/javascript">
					window.onload = bar([${row.perfData}],[${row.perfExecutions}],[${row.perfAttributes}],'singleCaseBar'+${row.testCaseId},'${row.testCaseName}');
				</script>
			</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		-->
	</div>
</body>
</html>

