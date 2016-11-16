<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>dtree/dtree.css" />
<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
<style type="text/css">
body {
	font-size: 12px;
	color: #333333;
	overflow-y: hidden;
	scrollbar-face-color: #8fd6f8;
	scrollbar-arrow-color: #FFFFFF;
	scrollbar-track-color: #EDEDED;
	scrollbar-highlight-color: #FFFFFF;
	scrollbar-3dlight-color: #006699;
	scrollbar-shadow-color: #DEE3E7;
	scrollbar-darkshadow-color: #BABABA;
}
</style>
</head>
<body style="margin: 0px; padding: 0px; width: 100%; height: 100%;">
<div
	style="position: relative; float: left; width: 198px; height: 100%; border-right: 2px #006699 solid; border-top: 2px #006699 solid; overflow-x: auto;">
<div class="dtree"
	style="position: relative; float: left; overflow-x: auto; height: 100%; min-WIDTH: 100%;">
<p><script type="text/javascript">

				function action(nodeid,title,url){
					window.parent.frames.right.addTab(nodeid,url,title);
				}
				var basePath = "<%=basePath%>dtree/";
				d = new dTree('d');
				d.add(0,-1,'iTMS');				
				//authorityBoard(d,'<s:property value="#session.user.teams"/>');
				//authorityIxia(d,'<s:property value="#session.user.teams"/>');
				//authorityReq(d,'<s:property value="#session.user.teams"/>');
				authorityConfig(d,'<s:property value="#session.user.level"/>');
				authorityCaseCenter(d,'<s:property value="#session.user.level"/>');
				authorityExecutionCenter(d,'<s:property value="#session.user.level"/>');
				authorityReport(d,'<s:property value="#session.user.level"/>');
				//authorityPerformance(d,'<s:property value="#session.user.level"/>');
				authorityCaseChart(d,'<s:property value="#session.user.level"/>');
				authorityProfile(d,'<s:property value="#session.user.level"/>');
				document.write(d);
				function authorityBoard(dtree,teams){

					if(teams.indexOf('STV')>-1 || teams.indexOf('DPDK')>-1 || teams.indexOf('QAT_Dev')>-1 )
						{
						d.add(1,0,'Board','');
						d.add(11,1,'Board Management','formBoard.action');	
						d.add(35,1,'Board Booking','<%=basePath%>toAllocateBoards.action');
						d.add(36,1,'Board Releasing','<%=basePath%>toReleaseBoards.action');
						}else if(teams==''){
							d.add(1,0,'Board','');
							d.add(11,1,'Board Management','formBoard.action');	
							d.add(35,1,'Board Booking','<%=basePath%>toAllocateBoards.action');
							d.add(36,1,'Board Releasing','<%=basePath%>toReleaseBoards.action');
							}
					}

			  	function authorityIxia(dtree,teams){
				
						if(teams.indexOf('STV')> -1 || teams.indexOf('DPDK')>-1)
							{
							d.add(2,0,'Ixia','');
							d.add(40,2,'Ixia Management','formIxia.action');
							d.add(41,2,'Ixia Booking','<%=basePath%>toAllocateIxias.action');
							d.add(42,2,'Ixia Releasing','<%=basePath%>toReleaseIxias.action');	
							}else if(teams=='') {
								d.add(2,0,'Ixia','');
								d.add(40,2,'Ixia Management','formIxia.action');
								d.add(41,2,'Ixia Booking','<%=basePath%>toAllocateIxias.action');
								d.add(42,2,'Ixia Releasing','<%=basePath%>toReleaseIxias.action');	
								}
				 }
				
				function authorityConfig(dtree,level)
				{
					//Only admin user can see this tab, level value with 1 means user have admin authority
					if( level==1)
					{
						d.add(10,0,'配置中心','');			
						
						d.add(15,10,"用例配置","");
						d.add(32,15,'项目','<%=basePath%>listProject.action');
						d.add(33,15,'功能模块','<%=basePath%>listFeatures.action');
						d.add(34,15,'子模块','<%=basePath%>listComponents.action');
						d.add(35,15,'功能点','<%=basePath%>listSubComponents.action');
						d.add(37,15,'用例级别','<%=basePath%>listOSs.action');
						d.add(38,15,'用例类型','<%=basePath%>listTestTypes.action');
						
						d.add(17,10,"计划配置","");
						d.add(32,17,'Target','<%=basePath%>listTarget.action');
						d.add(33,17,'测试阶段','<%=basePath%>listPhases.action');
						
						d.add(18,10,"执行配置","");
						//d.add(34,18,'测试环境','<%=basePath%>listTestenvs.action');
						d.add(35,18,'执行周期','<%=basePath%>listBuild.action'); 
						d.add(36,18,'执行环境','<%=basePath%>listExecutionOSs.action');
						d.add(37,18,'功能类型','<%=basePath%>listExecutionPhases.action');
						d.add(38,18,'执行版本','<%=basePath%>listPlatforms.action');
					
						d.add(20,10,"系统配置","");
						d.add(31,20,'用户列表','<%=basePath%>frameUsers.action');
						d.add(32,20,'部门','<%=basePath%>listTeams.action');
						
						//d.add(19,10,"Other","");
						//d.add(29,19,'BoardState','<%=basePath%>listBoardStates.action');
						//d.add(30,19,'RTM Project','<%=basePath%>xtp_project.action');
						
		
					}
				}
				
				function authorityCaseCenter(dtree,level){
					
					d.add(51,0,'用例管理','');
					d.add(80,51,'测试用例','<%=basePath%>frameTestCases.action');	
					d.add(81,51,'用例分组','<%=basePath%>frameTestSuites.action');
				}
				
				function authorityExecutionCenter(dtree,level){
					
					d.add(13,0,'执行管理','');
					//d.add(3,13,'TestCase','<%=basePath%>frameTestCases.action');	
					//d.add(4,13,'TestSuite','<%=basePath%>frameTestSuites.action');
					d.add(5,13,'测试计划','<%=basePath%>listTestPlan_iframe.action');
					d.add(6,13,'测试执行','<%=basePath%>listTexeExecutioniframe.action');
				}
				
				function authorityReq(dtree,level){
					  
					d.add(7,0,'Requirement Analysis','');
					//d.add(61,7,'RTM Tasks','<%=basePath%>requirement_db.action');
					//d.add(62,7,'Tasks Checker','<%=basePath%>requirement_task.action');	
				}	

				function authorityReport(dtree,level){
					  
						d.add(8,0,'测试报表','');
						d.add(71,8,'测试报告','<%=basePath%>executionReportChartingFrame.action');
						d.add(72,8,'趋势分析','<%=basePath%>executionTrendChartingFrame.action');
					}	
	
				function authorityPerformance(dtree,level)
				{
					d.add(9,0,'Performance Center','');
					d.add(91,9,'Performance Analysis ','<%=basePath%>executionPerformanceChartingFrame.action');
				}
				
				function authorityCaseChart(dtree,teams){
					  
						d.add(21,0,'用例报表','');
						d.add(71,21,'用例分析','<%=basePath%>caseAnalyze.action');	
					}
				function authorityProfile(dtree,level){
					//Only log in user can see this tab
					if(level > 0) {
						d.add(22,0,'用户中心','');
						d.add(74,22,'用户信息','<%=basePath%>toLoginUser.action');
					}				
				}
			
			</script></p>
</div>
</div>
</body>
</html>