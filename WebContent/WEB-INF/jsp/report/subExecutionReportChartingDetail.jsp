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
<title>Sub Execution</title>

<script class="code" type="text/javascript">
	function caseHealthyReport(subExecutionId,subPlanId) {
		MyWindow.winOpenFullScreen("caseHealthyReport.action?subExecutionId="+ subExecutionId + "&subPlanId=" + subPlanId,'detail');
	}
	
	function performanceAnalysis(subExecutionId) {
		MyWindow.winOpenFullScreen("performanceAnalysis.action?subExecutionId="+ subExecutionId,'detail');
	}
	
</script>
<style type="text/css">
        #div0
        {
          float:left; width:100%; height:100%;
        }
        #shape_bar1 
        {
          float:left; width:49%; height:99%; border:1px solid #000
        }
        #shape_bar2 
        {
          float:right; width:50%;height:99%; border:1px solid #000
        }
</style>
</head>

<body class="mobile">
		<h6  align="center">${subPlanName}</h6>
		<div align="center">
		<!-- Execution bar chart summary -->
		<br>
        <input type="hidden" name="currPage" value="${currPage}"></input>
		<display:table name="subExecutionList" class="mobile" id="row" style="width:95%;">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 5%;" >No.</th>
				<th style="width: 10%;">Execution</th>
				<th style="width: 10%;">Cycle</th>
				<th style="width: 10%;">Platform</th>
				<th style="width: 8%;">OS</th>
				<th style="width: 8%;">Type</th>
				<th style="width: 7%;">Build Type</th>
				<th style="width: 7%;">Pass</th>
				<th style="width: 7%;">Fail</th>
				<th style="width: 7%;">Block</th>
				<th style="width: 7%;">NotRun</th>
				<th style="width: 7%;">CaseReport</th>
				<th style="width: 7%;">PerformanceAnalysis</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>${row.subExecutionName} </display:column>
			<display:column>${row.releaseCycle} </display:column>
			<display:column>${row.platFormName} </display:column>
			<display:column>${row.osName} </display:column>
			<display:column>${row.phaseName} </display:column>
			<display:column>${row.buildTypeName} </display:column>
			<display:column style="background:green;">${row.pass} </display:column>
			<display:column style="background:red;">${row.fail} </display:column>
			<display:column style="background:yellow;">${row.block} </display:column>
			<display:column style="background:white;">${row.notRun} </display:column>
			<display:column><a href="javascript:caseHealthyReport(${row.subExecutionId },${row.subPlanId});" style="color:#0059fd;">CaseHealthyReport</a>&nbsp;</display:column>
			<display:column><a href="javascript:performanceAnalysis(${row.subExecutionId });" style="color:#0059fd;">PerformanceAnalysis</a>&nbsp;</display:column>
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:55%;height:30px;border:1px gray dotted;">
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="subExecutionReportChartingDetail.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="subPlanId" value="#subPlanId"></p:param>
				</p:page>
			</div>
		</div>
		<hr/>
		<br>
		
      <div style="width:100%; height:80%">
		<div id="div0">
		       <div id="shape_bar1"></div>
		       <div id="shape_bar2"></div>
		</div>

      </div>
		<br>
		
    <script src="<%=basePath %>js/echart/build/dist/echarts.js"></script>
    <script type="text/javascript">
        require.config({
            paths: {
                echarts: '<%=basePath %>js/echart/build/dist'
            }
        });
        
        require(
            [
                'echarts',
                'echarts/chart/funnel',
                'echarts/chart/pie',
                'echarts/chart/line',
                'echarts/chart/bar',
            ],
            function (ec) {
            	// ####################Category by phase####################
                var myChart1 = ec.init(document.getElementById('shape_bar1'));
            	var legendData=[];
                var xAxisData1=[]; 
            	var typeList=${platformTypeList};
            	var recycleList=${platformRecycleList};
                var platformlistData = ${platformList};
                
                for(var i=0;i<typeList.length;i++){
                	legendData.push(typeList[i]);
                }
                
                for(var i=0;i<recycleList.length;i++){
                	xAxisData1.push(recycleList[i]);
                } 
                
                var seriesValue=[];
				for (var i=0;i<typeList.length;i++) {
					var dataFlag="false";
	                var dataArray=[];
					for (var k=0; k<recycleList.length;k++) {
						dataFlag="false";
						for (var j=0; j<platformlistData.length; j++) {
							if (platformlistData[j].platFormName==typeList[i] && platformlistData[j].releaseCycle==recycleList[k]) {
								dataArray.push(platformlistData[j].passrate);
									
								dataFlag="true";
								break;
							}
						}
	    			    if (dataFlag=="false") {
	    			        dataArray.push(0);
	    			    } 
					}
   
			        seriesValue.push({
                        name: typeList[i],
        	            type:'bar',
        	            barWidth: 40,
        	            itemStyle: {normal: {barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'},formatter:'{c}%'}}},
        	            data: dataArray
				    });
				}
				
                var option1 = {
                	    title : {
                	        text: 'Pass Rate(Platform)'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        textStyle: {align: 'left'},
                	        'axisPointer' : {
                                'type': 'shadow',
                            },
                            'formatter': '{a} <br/>{b} : {c} ({d}%)'
                	    },
                	    legend: {
                	        data:legendData
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true,
                	            	'title' : {
                                        'mark' : 'Subline',
                                        'markUndo' : 'Subline-delete',
                                        'markClear' : 'Subline-empty'
                                    }},
                	            dataView : {show: true, readOnly: false,'title': 'Data View'},
                	            magicType : {show: true, 
                	            	'title': {
                                        'bar': 'Bar',
                                        'stack': 'Stack',
                                        'tiled': 'Tiled'
                                    },
                	            	type: ['bar', 'stack', 'tiled']},
                	            restore : {show: true,'title': 'Restore'}
                	        }
                	    },
                	    dataZoom : {
                	        show : true,
                	        realtime: true,
                	        start : 20,
                	        end : 60
                	    },
                	    calculable : true,
                	    grid: {y: 70, y2:70, x2:20},
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : xAxisData1
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            axisLabel:{formatter:'{value} %'}
                	        }
                	    ],
                	    series : seriesValue
                	};

                myChart1.setOption(option1); 
                
                // ####################Category by Pass Rate(Os)####################
                var myChart2= ec.init(document.getElementById('shape_bar2'));
            	var legendData2=[];
                var xAxisData2=[]; 
            	var typeList2=${osTypeList};
            	var recycleList2=${osRecycleList};
                var oslistData = ${osList};
                
                for(var i=0;i<typeList2.length;i++){
                	legendData2.push(typeList2[i]);
                }
                
                for(var i=0;i<recycleList2.length;i++){
                	xAxisData2.push(recycleList2[i]);
                } 
                
                var seriesValue2=[];
				for (var i=0;i<typeList2.length;i++) {
					var dataFlag="false";
	                var dataArray=[];
					for (var k=0; k<recycleList2.length;k++) {
						dataFlag="false";
						for (var j=0; j<oslistData.length; j++) {
							if (oslistData[j].osName==typeList2[i] && oslistData[j].releaseCycle==recycleList2[k]) {
								dataArray.push(oslistData[j].passrate);
									
								dataFlag="true";
								break;
							}
						}
	    			    if (dataFlag=="false") {
	    			        dataArray.push(0);
	    			    } 
					}
   
			        seriesValue2.push({
                        name: typeList2[i],
        	            type:'bar',
        	            barWidth: 40,
        	            itemStyle: {normal: {barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'},formatter:'{c}%'}}},
        	            data: dataArray
				    });
				}
				
                var option2 = {
                	    title : {
                	        text: 'Pass Rate(Os)'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        textStyle: {align: 'left'},
                	        'axisPointer' : {
                                'type': 'shadow',
                            },
                            'formatter': '{a} <br/>{b} : {c} ({d}%)'
                	    },
                	    legend: {
                	        data:legendData2
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true,
                	            	'title' : {
                                        'mark' : 'Subline',
                                        'markUndo' : 'Subline-delete',
                                        'markClear' : 'Subline-empty'
                                    }},
                	            dataView : {show: true, readOnly: false,'title': 'Data View'},
                	            magicType : {show: true, 
                	            	'title': {
                                        'bar': 'Bar',
                                        'stack': 'Stack',
                                        'tiled': 'Tiled'
                                    },
                	            	type: ['bar', 'stack', 'tiled']},
                	            restore : {show: true,'title': 'Restore'}
                	        }
                	    },
                	    dataZoom : {
                	        show : true,
                	        realtime: true,
                	        start : 20,
                	        end : 60
                	    },
                	    calculable : true,
                	    grid: {y: 70, y2:70, x2:20},
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : xAxisData2
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            axisLabel:{formatter:'{value} %'}
                	        }
                	    ],
                	    series : seriesValue2
                	};

                myChart2.setOption(option2); 
            }
        );
    </script>		
		</div>
		<br>
 </body>

</html>