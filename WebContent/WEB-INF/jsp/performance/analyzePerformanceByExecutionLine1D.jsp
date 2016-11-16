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
	    
		<script type="text/javascript" charset="utf-8">
		function line(data,series,ticks,name){
			$(document).ready(function(){
				  var plot2 = $.jqplot ('line', data, {
				      // Give the plot a title.
				      title: name,
				      series: series,
				      legend: {
				      	show: true,
				        background: 'white',
				        textColor: 'black',
				        fontFamily: 'Times New Roman',
				        border: '1px solid black'
				      },
				      axesDefaults: {
				        labelRenderer: $.jqplot.CanvasAxisLabelRenderer
				      },
				      seriesDefaults: {
				          rendererOptions: {
				              smooth: true
				          }
				      },
				      axes: {
				        xaxis: {
			                renderer: $.jqplot.CategoryAxisRenderer,
			                ticks: ticks
				        }
				      }
				    });
				});
		}
    	</script>
		
	</head>

<body class="mobile">
	<div align="center">
		<div id="line" style="width:95%; height:95%"></div>
	</div>
	<script class="code" type="text/javascript">
		window.onload = line([${data}],[${series}],[${ticks}],'${name}');
	</script>
</body>
</html>

