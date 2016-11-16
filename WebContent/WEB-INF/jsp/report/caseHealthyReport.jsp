<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>Report Test Plan</title>

		<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
        <style type="text/css">
        #div0
        {
          float:left; width:100%; height:100%;
        }
        #shape_bar1 
        {
          float:center; width:95%;height:99%; border:1px solid #000
        }
        </style>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
	
		<h6  align="center">${subPlanName}</h6>
		<div align="center">
		<!-- Execution bar chart summary -->
		<br>

        <input type="hidden" name="currPage" value="${currPage}"></input>
 
		<display:table name="subplanCaseList" class="mobile" id="row" style="width:95%;">
			<display:caption><thead>
			<tr  class="pageHeader">
				<th style="width: 5%;" >No.</th>
				<th style="width: 10%;">CaseName</th>
				<th style="width: 10%;">Feature</th>
				<th style="width: 10%;">Component</th>
				<th style="width: 10%;">TestType</th>
				<th style="width: 10%;">Automation</th>
				<th style="width: 10%;">History</th>

			</tr>
			</thead></display:caption>		
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>${row.testcasename} </display:column>
			<display:column>${row.featurename} </display:column>
			<display:column>${row.compname} </display:column>
			<display:column>${row.testtypename} </display:column>
			<display:column>${row.autoname} </display:column>
			<display:column><a href="javascript:caseHistory(${row.resultid });" style="color:#0059fd;">History </a> </display:column>

			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:55%;height:30px;border:1px gray dotted;">
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="caseHealthyReport.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="subPlanId" value="#subPlanId"></p:param>
				<p:param name="subExecutionId" value="#subExecutionId"></p:param>
				</p:page>
			</div>
		</div>
		<hr/>
		<br>
		
      <div style="width:100%; height:80%">
		<div id="div0">
		       <div id="shape_bar1"></div>
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
            	// ####################Case PassRate(History)####################
                var myChart1 = ec.init(document.getElementById('shape_bar1'));
                var xAxisData1=[]; 
            	var nameList=${caseNameList};
            	var valueList=${valueList};
                
                for(var i=0;i<nameList.length;i++){
                	xAxisData1.push(nameList[i]);
                } 
                
                var seriesValue=[];
				for (var i=0;i<valueList.length;i++) {
					seriesValue.push(valueList[i]);
				}
				
                var option1 = {
                	    title : {
                	        text: 'Case Pass Rate(History)'
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
                	        data:['PassRate']
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
                	        end : 70
                	    },
                	    calculable : true,
                	    grid: {y: 70, y2:70, x2:20},
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap: true,
                	            data : xAxisData1
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            axisLabel:{formatter:'{value} %'}
                	        }
                	    ],
                	    series : [
                      	        {
                    	            name:'PassRate',
                    	            type:'bar',
                    	            barWidth: 40,
                    	            itemStyle: {normal: {color:'#87CEEB', barBorderRadius: 5, label:{show:true,textStyle:{color:'#FF0000'},formatter:'{c}%'}}},
                    	            data:seriesValue
                    	        }]
                	};

                myChart1.setOption(option1); 
                
            }
        );
    </script>	

    </div>
    <br>
	</body>
		
<script type="text/javascript">
	function caseHistory(id) {
 		MyWindow.OpenCenterWindowScroll("testCaseExecutionHistory.action?resultId=" + id,'dataRemark',700,700);
 	}
 
	function closewin(){  
		self.opener=null;  
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		self.close();
		//parent.$("#floatBoxBg").hide();
	    //parent.$("#floatBox").hide();
	} 

</script>

	</html>