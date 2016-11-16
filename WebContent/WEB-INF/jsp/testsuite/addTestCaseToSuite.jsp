<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>${testsuite.testSuiteName}</title>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%=basePath %>scripts/testsuite.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
	<input type="hidden" name="testSuiteId" value="${testSuiteId}"></input>
		<form id="form1" name="form1" method="post">
			<input type="hidden" name="projectId" value="${projectId}">		
					<input type="hidden" name="projectName" value="${projectName}">			
		<input type="hidden" name="testSuiteId" value="${testSuiteId}"></input>
			<table class="mobile" height="6%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Test Case List</TD>
				</tr>
				<tr class="pagesearch" height="3%">
					<td align="center">
					Project:
					<select onchange="serFeature(this.value)" id="project" name="project">			
					<option value="${proId}" selected="selected" >${projectName}</option>		
					</select>
					Feature:
					<select onchange="getComponent(this.value)" id="featureId" name="featureId">
						<option value="-1">All</option>
						<s:iterator value="#featureList" id="feature">
							<s:if test="#feature.featureId== #featureId">
								<option value="${feature.featureId }" selected="selected">${feature.featureName}</option>
							</s:if>
							<s:else>
								<option value="${feature.featureId }">${feature.featureName}</option>
							</s:else>
						</s:iterator>
					</select>
					Component:
					<select  name="compId" id="compId">
						<option value="-1">All</option>
						<s:iterator value="#componentList" id="component">	
						<s:if test="#component.compId == #compId">
						<option value="${component.compId}" selected="selected">${component.compName }</option>
						</s:if>
						<s:else>
						<option value="${component.compId}">${component.compName }</option>
						</s:else>
						</s:iterator>
					</select>
					TestType:
					<select id="testTypeId" name="testTypeId">
						<option value="-1">All</option>
						<s:iterator value="#testTypeList" id="testType">
							<s:if test="#testType.typeId == #testTypeId">
								<option value="${testType.typeId }" selected="selected">${testType.typeName}</option>
							</s:if>
							<s:else>
								<option value="${testType.typeId }">${testType.typeName}</option>
			
							</s:else>
						</s:iterator>
					</select>
					Automation:
					<select id="autoId" name="autoId">
						<option value="-1">All</option>
						<s:iterator value="#automationList" id="auto">
							<s:if test="#auto.autoId == #autoId">
								<option value="${auto.autoId }" selected="selected">${auto.autoName}</option>
							</s:if>
							<s:else>
								<option value="${auto.autoId }">${auto.autoName}</option>
			
							</s:else>
						</s:iterator>
					</select>
					 <input type="button" class="btn" value="Search" onClick="sch();" />
					</td>
				</tr>
			</table>
			<table class="mobile" style="width:100%;">
			<tr class="pageHeader">
				<th style="width: 5%;" title="Select All"><input type="checkbox" onclick="selectAllTestCases(this);"></th>
				<th style="width: 30%;">Test Case Name </th>
				<th style="width: 15%;">Test Case Id</th>
				<th style="width: 10%;">Project</th>
				<th style="width: 10%;">Feature</th>
				<th style="width: 10%;">Component</th>
				<th style="width: 10%;">Test Type</th>
				<th style="width: 10%;">Automation</th>
			</tr>
			<s:iterator value="#testCaseList" id="ts" status="sx">
			<tr>
			<td>
				<s:set name="flag" value="false" />
				<s:iterator id="session" value="#sessionCases">
				<s:if test="#session.testCaseId == #attr.ts.testCaseId">
						<s:set name="flag" value="true" />
				</s:if>	
			</s:iterator>
			<s:if test="#flag ==false">
				<input type="checkbox" onclick="setCaseStatus(${ts.testCaseId});" name="selectedIds" id=${ts.testCaseId } value="${ts.testCaseId }" />
			</s:if>
			<s:if test="#flag ==true">
				<input type="checkbox" onclick="setCaseStatus(${ts.testCaseId});" checked="checked" name="selectedIds" id=${ts.testCaseId } value="${ts.testCaseId }" />
			</s:if>				
			</td>
		
			<td>${ts.testCaseName }</td>
			<td>${ts.testCasealiasId }</td>
			<td>${ts.projectName}</td>
			<td>${ts.featureName }</td>
			<td>${ts.compName}</td>
			<td>${ts.testTypeName }</td>
			<td>${ts.autoName }</td>
			</tr>	
			</s:iterator>		
			</table>
		
		<div style="width:100%;height:30px;border:1px gray dotted;">
			<div style="float:left;width:45%;height:25px;">
					<input type="button" value="submit"  class="btn" onclick="addTestCaseToSuite()"></input>
			</div>
			<div style="float:right;width:45%;height:25px;">
				<p:page	action="preAddTestCaseToSuite.action" beanName="#pageBean" classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="projectId" value="#projectId"/>
				<p:param name="featureId" value="#featureId"/>
				<p:param name="compId" value="#compId"/>
				<p:param name="osId" value="#osId"/>
				<p:param name="testTypeId" value="#testTypeId"/>
				<p:param name="autoId" value="#autoId"/>				
				<p:param name="testSuiteId" value="#testSuiteId"/>
			</p:page>
			</div>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	
	function setCaseStatus(id){
		var status = document.getElementById(id).checked;
		$.post("addCaseToSuiteBySession.action", { isSelected: status, multiCaseId: id } );
	}
	
	function serFeature(pro){
		var fea = document.getElementById("featureId");
		fea.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
		o.text = "All";
		fea.add(o);
		jQuery.post("getFeatureList.action",{project:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].FEATUREID;
					op.text = data[i].FEATURENAME;
					fea.add(op);
				}
			}
				
		},"json");
	}
	function getComponent(pro){
		var com = document.getElementById("compId");
		com.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
		o.text = "All";
			com.add(o);;
		jQuery.post("getComponentList.action",{featureId:pro},function(data){
			if(data.length>0){
				for(i =0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].COMPID;
					op.text = data[i].COMPNAME;
					com.add(op);
				}
			}
				
		},"json");
	}
	function sch() {
		form1.action="preAddTestCaseToSuite.action";
		form1.submit();
	}
	function addTestCaseToSuite(){
		
		var checkedBoxs = $('input:checkbox:checked');
		if(checkedBoxs.length<=0)
		{
			alert('Please choose test cases to add!');
			return false;
			
		}
		if(!confirm('Confirm Add selected testcases to suite?'))
		{
			return ;
		}	
	form1.action="addTestCaseToSuite.action";
	form1.submit();
		}
	</script>
</html>