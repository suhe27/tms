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
<title>Test Case</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/testcase.js"></script>
</head>
<body>
<form action="updateTestCase.action" method="post" onsubmit="return addTestCaseValidation()" id="form1">
	<input type="hidden" name="testCase.testCaseId" id="testcaseId" value="${testcase.testCaseId} " /> 
	<input name="testCase.createDate" type="hidden" id="createDate" value="${testcase.createDate}" />
	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	<input type="text" name="testCase.teamId" id="teamId" value="${testcase.teamId}" style="display:none"/>
<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">用例详情</th>
	</tr>

	<tr>
		<td>部门:</td>
		<td>
		<input type="radio" name="testCase.teamId" id="teamId" value="${testcase.teamId}" disabled="disabled" checked="checked"/>
			<label>${testcase.teamName}</label>
		</td>
	</tr>

	<tr>
		<td width="150">项目:</td>
		<td><input type="radio" name="testCase.projectId" id="projectId" value="${testcase.projectId}"  checked="checked" />
			<label>${testcase.projectName}</label>
		</td>
	</tr>
	<!-- 
	<tr>
		<td>Project:</td>
		<td>
		<select name="testCase.projectId" id="projectId" style="width: 100px" onchange="getFeatureList(this.value)">
			<s:iterator value="#projectList" id="project">
				<s:if test="#testcase.projectId == #project.projectId">
					<option value="${project.projectId }"  selected="selected">${project.projectName}</option>
				</s:if>
				<s:else>
				<option value="${project.projectId }">${project.projectName}</option>
				</s:else>
			</s:iterator>
		</select>
		<span style="color:red;">*</span>
	</td>
	</tr>
-->


	<tr>
		<td>功能模块:</td>
		<td>
		<select name="testCase.featureId" id="featureId" style="width: 100px" onchange="getComponent(this.value);">
			<s:iterator value="#featureList" id="feature">
				<s:if test="#testcase.featureId == #feature.featureId">
					<option value="${feature.featureId }" selected="selected">${feature.featureName
					}</option>
				</s:if>
				<s:else>
					<option value="${feature.featureId }">${feature.featureName
					}</option>
				</s:else>
			</s:iterator>
		</select>
		<span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td>子模块:</td>
		<td>
		<select name="testCase.compId" id="compId" style="width: 100px" onchange="getSubComponent(this.value);">
			<s:iterator value="#componentList" id="comp">

				<s:if test="#testcase.compId == #comp.compId">
					<option value="${comp.compId }" selected="selected">${comp.compName
					}</option>
				</s:if>
				<s:else>
					<option value="${comp.compId }">${comp.compName }</option>
				</s:else>
			</s:iterator>
		</select>
		<span style="color:red;">*</span>
		</td>
	</tr>
	
	<tr>
		<td>功能点:</td>
		<td><select name="testCase.subCompId" id="subCompId"
			style="width: 100px">	
			<s:iterator value="#subComponentList" id="sub">

				<s:if test="#testcase.subCompId == #sub.subCompId">
					<option value="${sub.subCompId }" selected="selected">${sub.subCompName
					}</option>
				</s:if>
				<s:else>
					<option value="${sub.subCompId }">${sub.subCompName }</option>
				</s:else>
			</s:iterator>
		</select>
		</td>
	</tr>
	<tr>
		<td>用例级别:</td>
		<td><select name="testCase.osId" id="osId" style="width: 100px">
			<s:iterator value="#osList" id="os">
				<s:if test="#testcase.osId == #os.osId">
					<option value="${os.osId }" selected="selected">${os.osName
					}</option>
				</s:if>
				<s:else>
					<option value="${os.osId }">${os.osName }</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>提交人:</td>
		<td><select name="testCase.userId" id="userId"
			style="width: 100px">
			<s:iterator value="#userList" id="u">

				<s:if test="#testcase.userId == #u.userId">
					<option value="${u.userId }" selected="selected">${u.userName
					}</option>
				</s:if>
				<s:else>
					<option value="${u.userId }">${u.userName }</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>用例类型:</td>
		<td><select name="testCase.testTypeId" id="testTypeId"
			style="width: 100px">
			<s:iterator value="#testTypeList" id="testType">
				<s:if test="#testcase.testTypeId == #testType.typeId">
					<option value="${testType.typeId }" selected="selected">${testType.typeName
					}</option>
				</s:if>
				<s:else>
					<option value="${testType.typeId }">${testType.typeName }</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>自动化:</td>
		<td><select name="testCase.autoId" id="automation"
			style="width: 100px">
			<s:iterator value="#automationList" id="automation">
				<s:if test="#testcase.autoId == #automation.autoId">
					<option value="${automation.autoId }" selected="selected">${automation.autoName
					}</option>
				</s:if>
				<s:else>
					<option value="${automation.autoId }">${automation.autoName
					}</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
<!--  
	<tr>
		<td>State:</td>
		<td><select name="testCase.caseStateId" id="caseStateId"
			style="width: 100px">
			<s:iterator value="#caseStateList" id="caseState">
				<s:if test="#testcase.caseStateId == #caseState.caseStateId">
					<option value="${caseState.caseStateId }" selected="selected">${caseState.caseStateName}</option>
				</s:if>
				<s:else>
					<option value="${caseState.caseStateId }">${caseState.caseStateName}</option>

				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>Version:</td>
		<td><select name="testCase.versionId" id="versionId"
			style="width: 100px">
			<s:iterator value="#versionStateList" id="version">
				<s:if test="#testcase.versionId == #version.versionId">
					<option value="${version.versionId }" selected="selected">${version.versionName
					}</option>
				</s:if>
				<s:else>
					<option value="${version.versionId }">${version.versionName
					}</option>
				</s:else>
			</s:iterator>
		</select><span style="color:red;">*</span></td>
	</tr>
-->

	<tr>
		<td>用例名称:</td>
		<td><input type="text" name="testCase.testCaseName"
			id="testcaseName" value="${testcase.testCaseName}"
			style="width: 80%;" /><span style="color:red;">*</span></td>
	</tr>
	<tr>
		<td>用例编号:</td>
		<td><input type="text" name="testCase.testCasealiasId"
			id="testcasealiasId" value="${testcase.testCasealiasId}" style="width: 80%;"  /></td>
	</tr>
	<tr>
		<td>需求编号:</td>
		<td><input type="text" name="testCase.requirementId"
			id="requirementId" value="${testcase.requirementId}"
			style="width: 80%;" /></td>
	</tr>

	<tr>
		<td>前置条件:</td>
		<td class="alignl"><textarea name="testCase.configFiles"
			style="overflow: auto;" rows="5" cols="61">${testcase.configFiles }</textarea>
		</td>
	</tr>
	<tr>
		<td>执行步骤:</td>
		<td><textarea name="testCase.executionSteps"
			style="overflow: auto;" rows="5" cols="61">${testcase.executionSteps}</textarea>
		</td>
	</tr>
	<tr>
		<td>期望结果:</td>
		<td><input type="text" name="testCase.expectedResult"
			id="expectedResult" value="${testcase.expectedResult}"
			style="width: 80%;" /></td>
	</tr>
	<tr>
		<td>用例描述:</td>
		<td><textarea name="testCase.description" style="overflow: auto;"
			rows="5" cols="61">${testcase.description}</textarea></td>
	</tr>
<!--  
	<tr>
		<td>Test Script:</td>
		<td><input type="text" name="testCase.testScript" id="testScript"
			value="${testcase.testScript}" style="width: 80%;" /></td>
	</tr>
	<tr>
		<td>Test Function Call:</td>
		<td><input type="text" name="testCase.testfunctionCall"
			id="testfunctionCall" value="${testcase.testfunctionCall}"
			style="width: 80%;" /></td>
	</tr>
	<tr>
		<td>Package Range Size:</td>
		<td><input type="text" name="testCase.packagesizeRange"
			id="packagesizeRange" value="${testcase.packagesizeRange}"
			style="width: 80%;" /></td>
	</tr>
	<tr>
		<td>Timeout:</td>
		<td><input type="text" name="testCase.timeout" id="timeout"
			value="${testcase.timeout}" style="width: 80%;" /></td>
	</tr>
	

	-->
	<tr>
		<td>&nbsp;</td>
		<td class="operation"><input type="submit" value="update" class="btn1" /></td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">

function getFeatureList(value){
	var com = document.getElementById("featureId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
	com.add(o);;
	jQuery.post("getFeatureListAsID.action",{projectId:value},function(data){
		if(data.length>0){
			for(i =0;i<data.length;i++){
				var op = document.createElement("option");
				op.value= data[i].FEATUREID;
				op.text = data[i].FEATURENAME;
				com.add(op);
			}
		}
	},"json");
	
}

function getComponent(pro){
	var com = document.getElementById("compId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
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

function getSubComponent(pro){
	var com = document.getElementById("subCompId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
	com.add(o);;
	jQuery.post("getSubComponentList.action",{subCompId:pro},function(data){
		if(data.length>0){
			for(i =0;i<data.length;i++){
				var op = document.createElement("option");
				op.value= data[i].SUBCOMPID;
				op.text = data[i].SUBCOMPNAME;
				com.add(op);
			}
		}
			
	},"json");
	
}
</script>
</html>