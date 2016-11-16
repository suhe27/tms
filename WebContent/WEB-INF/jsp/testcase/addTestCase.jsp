<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<form action="addTestCase.action" method="post" id="form1" onsubmit="return addTestCaseValidation()">

<table id="common_table" class="form_table">
	<tr>
		<th colspan="2" class="form_head">添加用例</th>
	</tr>

	<tr>
		<td class="tdl">部门:</td>
		<td>
			<select name="testCase.teamId" id="teamId" style="width: 200px" onchange="getPlatformList(this.value);">
				<s:iterator value="#teamList" id="team">
					<option value="${team.teamId }">${team.teamName}</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>

	<tr>
		<td class="tdl">项目:</td>
		<td>
			<select name="testCase.projectId" id="projectId" style="width: 200px" onchange="getFeatureList(this.value)">
				<option value="">- Select a value-</option>
				<s:iterator value="#projectList" id="project">
					<option value="${project.projectId }">${project.projectName}</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	

<tr>
		<td class="tdl">功能模块:</td>
		<td>
			<select name="testCase.featureId" id="featureId" style="width: 200px" onchange="getComponent(this.value);">
				<option value="">- Select a value-</option>
				<%--
				<s:iterator value="#featureList" id="feature">
					<option value="${feature.featureId }">${feature.featureName }</option>
				</s:iterator>
				 --%>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td class="tdl">子模块:</td>
		<td>
			<select name="testCase.compId" id="compId" style="width: 200px" onchange="getSubComponent(this.value);">
				<option value="">- Select a value-</option>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	
	<tr>
		<td class="tdl">功能点:</td>
		<td>
			<select name="testCase.subCompId" id="subCompId" style="width: 200px">
				<option value="">- Select a value-</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="tdl">用例级别:</td>
		<td>
			<select name="testCase.osId" id="osId" style="width: 200px">
				<!--  <option value="">- Select a value-</option> -->
				<s:iterator value="#osList" id="os">
					<option value="${os.osId }">${os.osName }</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td class="tdl">提交人:</td>
		<td>
			<select name="testCase.userId" id="userId" style="width: 200px">
				<option value="">- Select a value-</option>
				<s:iterator value="#userList" id="user">
					<option value="${user.userId }">${user.userName }</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td class="tdl">用例类型:</td>
		<td>
			<select name="testCase.testTypeId" id="testTypeId" style="width: 200px">
				<option value="">- Select a value-</option>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
	<tr>
		<td class="tdl">自动化:</td>
		<td>
			<select name="testCase.autoId" id="automation" style="width: 200px">
			<!--	<option value="">- Select a value-</option> -->
				<s:iterator value="#automationList" id="automation">
					<option value="${automation.autoId }">${automation.autoName }</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
<!--  
	<tr>
		<td class="tdl">状态:</td>
		<td>
			<select name="testCase.caseStateId" id="caseStateId" style="width: 200px">
				<s:iterator value="#caseStateList" id="caseState">
					<option value="${caseState.caseStateId }">${caseState.caseStateName}</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
-->
<!--  
	<tr>
		<td class="tdl">版本:</td>
		<td>
			<select name="testCase.versionId" id="versionId" style="width: 200px">
				<s:iterator value="#versionStateList" id="version">
					<option value="${version.versionId }">${version.versionName}</option>
				</s:iterator>
			</select>
			<span style="color:red;">*</span>
		</td>
	</tr>
-->
	<tr>
		<td class="tdl">用例名称:</td>
		<td >
			<input type="text"	name="testCase.testCaseName" id="testcaseName" style="width:80%;"/>
			<span style="color:red;">*</span>
		</td>
	</tr>

	<tr>
		<td class="tdl">用例编号:</td>
		<td>
			<input type="text" name="testCase.testCasealiasId" id="testcasealiasId" style="width:80%;" />
			
		</td>
	</tr>

	<tr>
		<td class="tdl">需求编号:</td>
		<td>
			<input type="text" name="testCase.requirementId" id="requirementId" style="width:80%;" />
		</td>
	</tr>
	<!--  
	<tr>
		<td class="tdl">配置文件:</td>
		<td>
			<textarea name="testCase.configFiles" style="overflow: auto;" rows="5" cols="61"></textarea>
		</td>
	</tr>
	-->
	<tr>
		<td class="tdl">前置条件:</td>
		<td>
			<input type="text" name="testCase.testScript" id="testScript" style="width:80%;"/>
		</td>
	</tr>
	<tr>
		<td class="tdl">执行步骤:</td>
		<td>
			<textarea name="testCase.executionSteps" rows="5" cols="60"></textarea>
		</td>
	</tr>
	<tr>
		<td class="tdl">期望结果:</td>
		<td>
			<input type="text" name="testCase.expectedResult" id="expectedResult" style="width:80%;" />
		</td>
	</tr>
	<!--  
	<tr>
		<td class="tdl">测试脚本:</td>
		<td>
			<input type="text" name="testCase.testScript" id="testScript" style="width:80%;"/>
		</td>
	</tr>
	-->
	<!--  
	<tr>
		<td class="tdl">Test Function Call:</td>
		<td>
			<input type="text" name="testCase.testfunctionCall" id="testfunctionCall" style="width:80%;" />
		</td>
	</tr>
	-->
	<!-- 
	<tr>
		<td class="tdl">Package Range Size:</td>
		<td>
			<input type="text" name="testCase.packagesizeRange" id="packagesizeRange" style="width:80%;" />
		</td>
	</tr>
	-->
	<!-- 
	<tr>
		<td class="tdl">Timeout:</td>
		<td>
			<input type="text" name="testCase.timeout" id="timeout" style="width:80%;"/>
		</td>
	</tr>
	 -->
	
	<tr>
		<td class="tdl">用例描述:</td>
		<td>
			<textarea name="testCase.description" style="overflow: auto;" rows="5" cols="60"></textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<input type="submit" value="提交" class="btn"/>
		</td>
	</tr>
</table>
<br />
</form>
</body>
<script type="text/javascript">

function getPlatformList(value){
	var com = document.getElementById("projectId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
	com.add(o);;
	jQuery.post("getPlatformList.action",{teamId:value},function(data){
		if(data.length>0){
			for(i =0;i<data.length;i++){
				var op = document.createElement("option");
				op.value= data[i].PROJECTID;
				op.text = data[i].PROJECTNAME;
				com.add(op);
			}
		}
	},"json");
	
}

function getFeatureList(value){
	var com = document.getElementById("featureId");
	com.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
	com.add(o);
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
	
	var testType = document.getElementById("testTypeId");
	testType.options.length = 0;
	var o = document.createElement("option");
	o.value= "";
	o.text = "- Select a value -";
	testType.add(o);
	jQuery.post("getTestTypeByProjectIdJSON.action",{projectId:value},function(data){
		if(data.length>0){
			for(i =0;i<data.length;i++){
				var op = document.createElement("option");
				op.value= data[i].testtypeid;
				op.text = data[i].testtypename;
				testType.add(op);
			}
		}
	},"json");
	
	var comp = document.getElementById("compId");
	comp.options.length = 0;
	var oo = document.createElement("option");
	oo.value= "-1";
	oo.text = "All";
	comp.add(oo);
	
	var scom = document.getElementById("subCompId");
	scom.options.length = 0;
	var oo = document.createElement("option");
	oo.value= "-1";
	oo.text = "All";
	scom.add(oo);
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