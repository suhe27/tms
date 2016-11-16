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
				        background: 'white',
				        textColor: 'black',
				        fontFamily: 'Times New Roman',
				        border: '1px solid black'
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
	<div align="center">
		<div id="Bar" style="width:95%; height:95%"></div></div>
				<script class="code" type="text/javascript">
					window.onload = bar([${data}],[${series}],[${ticks}],'Bar','${attribute}');
					//window.onload = bar([[91.015,91.015,91.015],[91.015,91.015,91.015],[91.015,91.015,91.015],[191.015,191.015,191.015],[21.015,21.015,21.015],[91.015,91.015,91.015],[91.015,91.015,91.015]],[{label: 'DPDK test case name 2 : NO1'},{label: 'DPDK test case name 2 : NO2'},{label: 'DPDK test case name 2 : NO3'},{label: 'DPDK test case name 2 : NO4'},{label: 'DPDK test case name 2 : NO5'},{label: 'DPDK test case name 2 : NO6'},{label: 'DPDK test case name 2 : NO7'},{label: 'DPDK test case name 2 : NO8'},{label: 'DPDK test case name 2 : NO9'},{label: 'DPDK test case name 2 : NO10'},{label: 'DPDK test case name 2 : NO11'},{label: 'DPDK test case name 2 : NO12'},{label: 'DPDK test case name 2 : NO13'},{label: 'DPDK test case name 2 : NO14'},{label: 'DPDK test case name 2 : NO15'},{label: 'DPDK test case name 2 : NO16'},{label: 'DPDK test case name 2 : NO17'},{label: 'DPDK test case name 2 : NO18'},{label: 'DPDK test case name 2 : NO19'},{label: 'DPDK test case name 2 : NO20'},{label: 'DPDK test case name 2 : NO21'}],['dpdk3','dpdk3 1420724283147','dpdk3 1421928318624'],'Bar','linerate');
					
					//window.onload = bar([[91.015,91.015,91.015,191.015,21.015,91.015,91.015],[91.015,91.015,91.015,191.015,21.015,91.015,91.015],[91.015,91.015,91.015,191.015,21.015,91.015,91.015]],[{label: 'dpdk3'},{label: 'dpdk3 1420724283147'},{label: 'dpdk3 1421928318624'}],[NO1,NO2,NO3,NO4,NO5,NO6,NO7],'Bar','DPDK test case name 2 : linerate');
					
				</script>
</body>
</html>

