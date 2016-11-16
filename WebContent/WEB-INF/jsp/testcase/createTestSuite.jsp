<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Case</title>
</head>
<body onload="checkSelectAllTestCases('${isSelected }')">

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="pop" class="blackMask" style="height:2000px;display:none;"></div>
<div id="div_main"><label class="statictittle">Create
TestSuite</label>
<hr />
<form method="post" name="casesuiteform">
<div>
<table id="filter_table">
	<tr>
	<td><span style="float: left; font-size: 15px; margin-right: 50px;">Filters:</span></td>
	<td width="180px">Project:<select onchange="filterCreateTestSuite();"
			id="project" name="project">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#teamList" id="fk">
			<s:iterator value="#platformList" id="platform">	
			<s:if test="#fk.teamName.concat('_').concat(#platform.platformName) == #project">
				<option value="${fk.teamName}_${platform.platformName}" selected="selected">${fk.teamName}_${platform.platformName}</option>
			</s:if>
			<s:else>
			<option value="${fk.teamName}_${platform.platformName}" >${fk.teamName}_${platform.platformName}</option>
			</s:else>
			</s:iterator>
			</s:iterator>
		</select></td>
		
		<td width="180px">Feature:<select onchange="filterCreateTestSuite();"
			id="featureId" name="featureId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#featureList" id="feature">
				<s:if test="#feature.featureId== #featureId">
					<option value="${feature.featureId }" selected="selected">${feature.featureName}</option>
				</s:if>
				<s:else>
					<option value="${feature.featureId }">${feature.featureName}</option>
				</s:else>
			</s:iterator>
		</select></td>
		<td width="180px">Component:<select onchange="filterCreateTestSuite();"
			id="component" name="compId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>	
			<s:iterator value="#componentList" id="component">	
			<s:if test="#component.compId == #compId">
			<option value="${component.compId}" selected="selected">${component.compName }</option>
			</s:if>
			<s:else>
			<option value="${component.compId}">${component.compName }</option>
			</s:else>
			</s:iterator>
		</select></td>
		<td width="180px">TestType:<select id="testTypeId"
			onchange="filterCreateTestSuite();" name="testTypeId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#testTypeList" id="testType">
				<s:if test="#testType.typeId == #testTypeId">
					<option value="${testType.typeId }" selected="selected">${testType.typeName}</option>
				</s:if>
				<s:else>
					<option value="${testType.typeId }">${testType.typeName}</option>

				</s:else>
			</s:iterator>
		</select></td>
		<!--<td width="180px">OS:<select onchange="filterCreateTestSuite();"
			id="os" name="osId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#osList" id="os">
				<s:if test="#os.osId == #osId">
					<option value="${os.osId }" selected="selected">${os.osName}</option>
				</s:if>
				<s:else>
					<option value="${os.osId }">${os.osName}</option>

				</s:else>
			</s:iterator>
		</select></td>
		--><td width="180px">Automation:<select onchange="filterCreateTestSuite();"
			id="autoId" name="autoId">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#automationList" id="auto">
				<s:if test="#auto.autoId == #autoId">
					<option value="${auto.autoId }" selected="selected">${auto.autoName}</option>
				</s:if>
				<s:else>
					<option value="${auto.autoId }">${auto.autoName}</option>

				</s:else>
			</s:iterator>
		</select></td>
		<!--<td width="180px">Version:<select onchange="filterCreateTestSuite();"
			id="project" name="version">
			<option value="-1">All</option>
			<option disabled="disabled">--------</option>
			<s:iterator value="#versionList" id="vers">
				<s:if test="#vers == #ver">
					<option value="${vers }" selected="selected">${vers}</option>
				</s:if>
				<s:else>
					<option value="${vers }">${vers}</option>

				</s:else>
			</s:iterator>
		</select></td>
		--><td >Select ALL: <input id="isSelected" name="isSelected" type="checkbox"
			onclick="selectAllTestCasesCrossPage(this);" value="${isSelected}"></td>
	</tr>
</table>
</div>
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th><input  type="checkbox"
			onclick="selectAllTestCases(this);"></input></th><!--
		<th>TESTCASEID</th>
		--><th>TESTCASENAME</th>
		<th>PROJECT</th>
		<th>FEATURE</th>
		<th>COMPONENT</th>
		<th>TESTTYPE</th>
		<!--<th>OS</th>
		--><th>AUTOMATION</th>
		<!--<th>VERSION</th>
	--></tr>
	<s:iterator value="#testCaseList" id="testcase" status="st">
		<tr onmouseover="selectLine(true, '${testcase.testCaseId }', this)"
			onmouseout="selectLine(false, '${testcase.testCaseId }', this)">
			<td><input type="checkbox" name="multiCaseId"
				value="${testcase.testCaseId }" /></td>
			<!--<td>${testcase.testCasealiasId}</td>
			--><td>${testcase.testCaseName }</td>
			<td>${testcase.project }</td>
			<td>${testcase.feature.featureName }</td>
			<td>${testcase.component.compName }</td>
			<td>${testcase.testType.typeName }</td><!--
			<td>${testcase.os.osName }</td>
			--><td>${testcase.automation.autoName }</td>
			<!--<td>${testcase.version}</td>
		--></tr>
	</s:iterator>
	<tr>
		<td colspan="11"><span style="float: left;"><input
			type="button" value="Create TestSuite"
			onclick="createTestSuite();" /></span><span
			style="float: right; display: inline"><p:page
			action="preCreateTestSuite.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
			<p:param name="project" value="#project"/>
			<p:param name="featureId" value="#featureId"/>
			<p:param name="compId" value="#compId"/>
			<p:param name="testTypeId" value="#testTypeId"/><!--
			<p:param name="osId" value="#osId"/>
			--><p:param name="autoId" value="#autoId"/><!--
			<p:param name="version" value="#ver"/>
		--></p:page></span></td>
	</tr>
</table>

</form>
<div id="light" class="white_content2">Please wait while submit
result and creating testsuite ...</div>
</div>
</body>
</html>