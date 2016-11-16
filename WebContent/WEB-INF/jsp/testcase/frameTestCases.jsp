<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Test Case</title>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<form id="form1" name="form1" method="post" action="listTestCases.action" target="list">
			<table class="mobile" height="7%" width="100%">
				<tr class="pageheader" height="3%">
					<TD>Test Case List</TD>
				</tr>
				<tr class="pagesearch" height="3%">
					<td align="center">
					
					项目:
					<select onchange="serFeature(this.value)" id="projectId" name="projectId">
						<option value="-1">All</option>
						<s:iterator value="#projectList" id="project">	
							<s:if test="#projectId == #project.projectId">
								<option value="${project.projectId }" selected="selected">${project.projectName}</option>
							</s:if>
							<s:else>
									<option value="${project.projectId}" >${project.projectName}</option>
							</s:else>
						</s:iterator>
					</select>
					功能模块:
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
					子模块:
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
					用例类型:
					<select id="testTypeId" name="testTypeId">
						<option value="-1">All</option>
					</select>
					自动化:
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
					
					<%--
					Version:<select onchange="filterTestCase();"
						id="project" name="versionId">
						<option value="-1">All</option>
						<option disabled="disabled">--------</option>
						<s:iterator value="#versionList" id="vers">
							<s:if test="#vers.versionId == #ver">
								<option value="${vers.versionId }" selected="selected">${vers.versionName}</option>
							</s:if>
							<s:else>
								<option value="${vers.versionId }">${vers.versionName}</option>
			
							</s:else>
						</s:iterator>
					</select>
					 --%>
					 <input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
					<br>
					用例名称: <input type="text" size="15" name="caseName" id="caseName" value="${caseName }"/>
					<input type="button" class="btn" value="搜索" onClick="sch();" />
					<input type="button" class="btn" value="清空搜索条件" onClick="reSet();" />
					<!-- 
					<input type="checkbox"  name="isSelected" id="selectAll" onclick="selectSonAll(this);" /><label for="selectAll">Select All</label>
					-->
					</td>
				</tr>
			</table>
			<iframe  id="list" name="list" frameborder="0" height="85%" width="100%" scrolling="auto"></iframe>
		</form>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		document.getElementById("compId").disabled=true;
		form1.target='list';
		form1.submit();
	});
	function serFeature(pro){
		var fea = document.getElementById("featureId");
		fea.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
		o.text = "All";
		fea.add(o);
		
		/**

		**/
		jQuery.post("getFeatureList.action",{project:pro},function(data){
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].FEATUREID;
					op.text = data[i].FEATURENAME;
					fea.add(op);
				}
			}
				
		},"json");
		
		var com = document.getElementById("compId");
		com.options.length = 0;
		var oo = document.createElement("option");
		oo.value= "-1";
		oo.text = "All";
		com.add(oo);
		
		var testType = document.getElementById("testTypeId");
		testType.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
		o.text = "All";
		testType.add(o);
		jQuery.post("getTestTypeByProjectIdJSON.action",{projectId:pro},function(data){
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].testtypeid;
					op.text = data[i].testtypename;
					testType.add(op);
				}
			}
		},"json");
	}
	function getComponent(pro){
		if (pro == "-1") {
			document.getElementById("compId").disabled=true;
		} else {
			document.getElementById("compId").disabled=false;
		}

		var com = document.getElementById("compId");
		com.options.length = 0;
		var o = document.createElement("option");
		o.value= "-1";
		o.text = "All";
		com.add(o);
		jQuery.post("getComponentList.action",{featureId:pro},function(data){
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var op = document.createElement("option");
					op.value= data[i].COMPID;
					op.text = data[i].COMPNAME;
					com.add(op);
				}
			}
				
		},"json");
	}
	
	function sch() {
		form1.target='list';
		form1.submit();
	}
	function reSet() {
		document.location.href="reSet.action";
	}
 	function addTestCase() {
 		MyWindow.OpenCenterWindowScroll('toAddTestCase.action','dataRemark',600,800);
 	}
 	function selectSonAll(obj){
 	 	window.list.selectAllTestCases(obj);
 	 	var val = "";
 	 	if(obj.checked){
 	 		val="on";
 	 	}
 	 	window.list.checkSelectAllTestCases(val);
 	 	//window.list.document.getElementById("isSelected").value = val;
 	}
	</script>
</html>