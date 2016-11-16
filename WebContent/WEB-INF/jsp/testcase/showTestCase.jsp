<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${testcase.testCaseName}</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body>

<table class="form_table">
	<tr>
		<th colspan="2" class="form_head">用例详情</th>
	</tr>
	<tr>
		<td>部门:</td>
		<td><s:iterator value="#teamList" id="team">
			<s:if test="#testcase.teamId == #team.teamId">
			${team.teamName}
				</s:if>

		</s:iterator></td>
	</tr>
	
	
	<tr>
		<td>项目:</td>
		<td><s:iterator value="#projectList" id="project">
			<s:if test="#testcase.projectId == #project.projectId">
				${project.projectName}
				</s:if>

		</s:iterator></td>
	</tr>
	
	

	<tr>
		<td>功能模块:</td>
		<td><s:iterator value="#featureList" id="feature">
			<s:if test="#testcase.featureId == #feature.featureId">
				${feature.featureName}
				</s:if>

		</s:iterator></td>
	</tr>
	<tr>
		<td>子模块:</td>
		<td><s:iterator value="#componentList" id="comp">
			<s:if test="#testcase.compId == #comp.compId">
					${comp.compName}
				</s:if>

		</s:iterator></td>
	</tr>
	<tr>
		<td>功能点:</td>
		<td><s:iterator value="#subComponentList" id="sub">

			<s:if test="#testcase.subCompId == #sub.subCompId">
				${sub.subCompName}
				</s:if>
		</s:iterator></td>
	</tr>
	<tr>
		<td>用例级别:</td>
		<td><s:iterator value="#osList" id="os">
			<s:if test="#testcase.osId == #os.osId">
				${os.osName}
				</s:if>

		</s:iterator></td>
	</tr>
	<tr>
		<td>提交人:</td>
		<td><s:iterator value="#userList" id="u">

			<s:if test="#testcase.userId == #u.userId">
					${u.userName}
				</s:if>

		</s:iterator></td>
	</tr>
	<tr>
		<td>用例类型:</td>
		<td><s:iterator value="#testTypeList" id="testType">
			<s:if test="#testcase.testTypeId == #testType.typeId">
					${testType.typeName}
				</s:if>

		</s:iterator></td>
	</tr>
	<tr>
		<td>自动化:</td>
		<td><s:iterator value="#automationList" id="automation">
			<s:if test="#testcase.autoId == #automation.autoId">
				<option value="${automation.autoId }" selected="selected">${automation.autoName
				}</option>
			</s:if>

		</s:iterator></td>
	</tr>


	<tr>
		<td>用例名称:</td>
		<td>${testcase.testCaseName}</td>
	</tr>

	<tr>
		<td>用例编号:</td>
		<td>${testcase.testCasealiasId}</td>
	</tr>
	<tr>
		<td>需求编号:</td>
		<td>${testcase.requirementId}</td>
	</tr>

	
	<tr>
		<td>前置条件:</td>
		<td class="alignl">
		<s:if test="#testcase.teamId == 2"><a href="${testcase.configFiles }" target="_blank">${testcase.configFiles }</a></s:if>
		<s:else>
			${testcase.configFiles }
		</s:else></td>
	</tr>
	<tr>
		<td>执行步骤:</td>
		<td>${testcase.executionSteps}</td>
	</tr>
	<tr>
		<td>期望结果:</td>
		<td>${testcase.expectedResult}</td>
	</tr>
		<tr>
		<td>用例描述:</td>
		<td>${testcase.description}</td>
	</tr>
	<tr>
		<td>创建日期:</td>
		<td>${testcase.createDate}</td>
	</tr>
	<tr>
		<td>上次编辑日期:</td>
		<td>${testcase.modifyDate}</td>
	</tr>
<!--  
	<tr>
		<td>State:</td>
		<td><s:iterator value="#caseStateList" id="caseState">
			<s:if test="#testcase.caseStateId == #caseState.caseStateId">
					${caseState.caseStateName}
				</s:if>
		</s:iterator></td>
	</tr>
	<tr>
		<td>Version:</td>
		<td><s:iterator value="#versionStateList" id="version">
			<s:if test="#testcase.versionId == #version.versionId">
					${version.versionName}
				</s:if>
		</s:iterator></td>
	</tr>
	<tr>
		<td>Test Script:</td>
		<td>${testcase.testScript}</td>
	</tr>
	<tr>
		<td>Test Function Call:</td>
		<td>${testcase.testfunctionCall}</td>
	</tr>
	<tr>
		<td>Package Range Size:</td>
		<td>${testcase.packagesizeRange}</td>
	</tr>
	<tr>
		<td>Timeout:</td>
		<td>${testcase.timeout}</td>
	</tr>

	
	-->

</table>
<div style="float:left;margin-top:10px;width:100%;text-align:center;height:30px;">
	<input type="button" value=" Close " onclick="javascript:window.close();" class="btn1"/>
</div>
</body>
</html>