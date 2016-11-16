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
<title>Insert title here</title>

	    <script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>

<script class="code" type="text/javascript">
function action(nodeid,title,url){
	window.parent.parent.parent.parent.frames.right.addTab(nodeid,url,title);
}

    function hebingRows(col){
        var trs = $("table tr");
        var rows = 1;
        for(var i=trs.length;i>0;i--){
            var cur = $($(trs[i]).find("td")[col]).text();
            var next = $($(trs[i-1]).find("td")[col]).text();

            if(cur==next){
                rows++;
                $($(trs[i]).find("td")[col]).remove();
                $($(trs[i]).find("td")[1]).remove();
            } else {
                $($(trs[i]).find("td")[col]).attr("rowspan",rows);
                $($(trs[i]).find("td")[2]).attr("rowspan",rows);
                rows=1;
            }
        }
    }
        
	$(document).ready(function(){
	    hebingRows(1);
	});
	
	function subExecutionReport(subPlanId) {
		MyWindow.winOpenFullScreen("subExecutionReportChartingDetail.action?subPlanId=" + subPlanId,'detail');
	}
</script>    
<style type="text/css">
        #div0
        {
          float:left; width:100%; height:100%;
        }
        #shape_pie 
        {
          float:left; width:49%; height:99%; border:1px solid #000
        }
        #shape_bar 
        {
          float:right; width:50%;height:99%; border:1px solid #000
        }
</style>
</head>

<body class="mobile">
		<div align="center">

		<!-- Execution bar chart summary -->
		<br>
		<input type="hidden" name="currPage" value="${currPage}"></input>

		<display:table name="testplanlists" class="mobile" id="row" style="width:95%;">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 5%;" >No.</th>
				<th style="width: 20%;">Plan</th>
				<th style="width: 20%;">Phase</th>
				<th style="width: 20%;">SubPlan</th>
				<th style="width: 20%;">Target</th>
				<th style="width: 15%;">Report</th>
			</tr>
			</thead></display:caption>		
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>${row.planname} </display:column>
			<display:column>${row.phasename} </display:column>
			<display:column>${row.subplanname}</display:column>
			<display:column>${row.targetname} </display:column>
			<display:column>
				<a href="javascript:subExecutionReport(${row.subplanid });" style="color:#0059fd;">ExecutionReport </a>&nbsp;&nbsp;
			</display:column>
			
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:30px;border:1px gray dotted;">
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="executionReportChartingDetail.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"></p:param>
				</p:page>
			</div>
		</div>
		<hr/>
		<br>
		
      <div style="width:100%; height:75%">
		<div id="div0">
		       <div id="shape_pie"></div>
		       <div id="shape_bar"></div>
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
                var myChart1 = ec.init(document.getElementById('shape_pie')); 	
                var legendData=[];  					
                var seriesValue=[]; 
                var maxValue=0;
                var phaseData = ${phaseLists};
                
    			if(phaseData.length>0){
    				for(var i=0;i<phaseData.length;i++){
    			        if (phaseData[i].phasecount > maxValue) {
    			        	maxValue=phaseData[i].phasecount;
    			        } 
    			        
       					legendData.push(phaseData[i].phasename);
    			        seriesValue.push({
                              name: phaseData[i].phasename,
                              value: phaseData[i].phasecount
    				    });
    				}
    			}
        		
                var option1 = {
                		    title : {
                		        text: 'Category by phase',
                		        subtext: 'Test Plan Count',
                		        x:'center'
                		    },
                		    tooltip : {
                		        trigger: 'item',
                		        textStyle: {align: 'left'},
                		        formatter: '{a} <br/>{b} : {c} ({d}%)'
                		    },
                		    legend: {
                		        orient : 'vertical',
                		        x : 'left',
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
                		            magicType : {
                		                show: true, 
                		                'title': {
                                            'pie': 'Pie',
                                            'funnel': 'Funnel'
                                        },
                		                type: ['pie', 'funnel'],
                		                option: {
                		                    funnel: {
                		                        x: '25%',
                		                        width: '50%',
                		                        funnelAlign: 'left',
                		                        max: maxValue
                		                    }
                		                }
                		            },
                		            restore : {show: true,'title': 'Restore'},
                		        }
                		    },
                		    calculable : true,
                		    series : [
                		        {
                		            name:'Phase',
                		            type:'pie',
                		            radius : '55%',
                		            center: ['50%', '60%'],
                		            data:seriesValue
                		        }
                		    ]
                		};

                myChart1.setOption(option1); 
                
                // ####################Category by plan name####################
                var myChart2 = ec.init(document.getElementById('shape_bar')); 
                var xAxisData2=[]; 
                var passNumberData=[]; 
                var failNumberData=[];
                var notrunNumberData=[];
                var blockNumberData=[];
                var plancaseData = ${caseNumberLists};
                
    			if(plancaseData.length>0){
    				for(var i=0;i<plancaseData.length;i++){
        				xAxisData2.push(plancaseData[i].planname);
    					passNumberData.push(plancaseData[i].passNumber);
    					failNumberData.push(plancaseData[i].failNumber);
    					notrunNumberData.push(plancaseData[i].notrunNumber);
    					blockNumberData.push(plancaseData[i].blockNumber);
    				}
    			}
                
                var option2 = {
                	    title : {
                	        text: 'Category by plan name',
                	        subtext: 'Case number'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        textStyle: {align: 'left'},
                	        'axisPointer' : {
                                'type': 'shadow',
                            },
                            'formatter': '{b} <br/> {a} : {c}'
                	    },
                	    legend: {
                	    	orient:'vertical',
                	    	x : 'right',
                	    	y: 70,
                	        data:['pass','fail','notrun','block']
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
                	        start : 30,
                	        end : 70
                	    },
                	    calculable : true,
                	    grid: {y: 70, y2:70, x2:70},
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : xAxisData2
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            axisLabel:{formatter:'{value} '}
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'pass',
                	            type:'bar',
                	            barWidth: 40,
                	            itemStyle: {normal: {color:'#87CEEB', barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'}}}},
                	            data:passNumberData
                	        },
                	        {
                	            name:'fail',
                	            type:'bar',
                	            barWidth: 40,
                	            itemStyle: {normal: {color:'#DEB887', barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'}}}},
                	            data:failNumberData
                	        },
                	        {
                	            name:'notrun',
                	            type:'bar',
                	            barWidth: 40,
                	            itemStyle: {normal: {color:'#D8BFD8', barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'}}}},
                	            data:notrunNumberData
                	        },
                	        {
                	            name:'block',
                	            type:'bar',
                	            barWidth: 40,
                	            itemStyle: {normal: {color:'#FFB6C1', barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'}}}},
                	            data:blockNumberData
                	        }
                	    ]
                	};

                myChart2.setOption(option2); 
            }
        );
    </script>		
		</div>
 </body>

</html>