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
<title>Case Analyze</title>
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
function pie(data,id,title){
	$(document).ready(function(){
	  var pie = jQuery.jqplot (id, [data], 
	    { 
		  title:title,
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
}

</script>    
</head>

<body class="mobile">
	<h6  align="center">Case Distribution Center</h6>
		<div align="center">
		<!-- Case pie chart summary -->
		<br>
		<div align="center"><div id="summaryPie" style="width:60%; height:50%"></div></div>
			<script class="code" type="text/javascript">
				window.onload = pie([${pieData}],'summaryPie','Project distribution summary');
			</script>
		<br><hr><br>		
		<!-- Case summary of each project -->
		<table class="mobile" style="width:95%;">
			<thead>
				<tr  class="pageHeader">
     				<th colspan="2">Case Distribution By Project</th>
     			</tr>
			</thead>
  			<tbody>
     			<c:forEach items="${projects}" var="row">
     				<tr>
     					<td>
     						<div align="center"><div id="featurePie${row.projectId}" style="width:95%; height:95%"></div></div>
							<script class="code" type="text/javascript">
								window.onload = pie([${row.featureData}],'featurePie${row.projectId}','${row.projectName} : Feature distribution');
							</script>
     					</td>
     					<td>
     					    <div align="center"><div id="autoPie${row.projectId}" style="width:95%; height:95%"></div></div>
							<script class="code" type="text/javascript">
								window.onload = pie([${row.autoData}],'autoPie${row.projectId}','${row.projectName} : Auto distribution');
							</script>
     					</td>
     				</tr>
     			</c:forEach>
			</tbody>   
		</table>
		<br>
		</div>
		<br>
 </body>

</html>