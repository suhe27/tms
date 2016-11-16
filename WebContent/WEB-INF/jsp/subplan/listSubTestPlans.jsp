<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sub Test Plan</title>
<script type="text/javascript">
	function selectTestCaseClick(id) {
		window.location.href = "listDetailExcutionSuit.action?testcaseExcutionSuitId=" + id;
	}

	function selectProject(id) {
		projectObj = document.getElementById("project");
		for ( var i = 0; i < projectObj.options.length; i++) {
			if (projectObj.options[i].value == id) {
				projectObj.options[i].selected = true;
			}
		}
	}


function delTestSuite()
{


	var checkedBox = $('#common_table #singlebox  input:checked');
	if(checkedBox.length<=0){


		alert("you must choose at least one test case suite!");
		return ;
	}

	document.testcaseexcutionform.action = "delTestCaseExcution.action";
	document.testcaseexcutionform.submit();

	

}
	
	function exportxml()
	{
		var checkedBox = $('#common_table #singlebox  input:checked');
		if(checkedBox.length<=0){


			alert("you must choose  one test case suite!");
			return ;
		}

		document.testcaseexcutionform.action = "exprotTestExecutionToXml.action";
		document.testcaseexcutionform.submit();
	}



	function selectAllTestSuite(obj)
	{

	var checkBoxs = $('#common_table input:checkbox').each(function (i)
			{

	$(this).attr("checked",obj.checked);

		});
	
	
		
	}
	
</script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/page/head.jsp"></jsp:include>
<div id="pop" class="blackMask" style="height: 2000px; display: none;"></div>
<div id="div_main"><label class="statictittle">TestCaseExecution
List</label>
<hr />
<form method="post" name="testcaseexcutionform">
<table id="common_table" border="1">
	<tr style="background-color: #EEEEEE;">
		<th><input type="checkbox" onclick="selectAllTestSuite(this);"></input></th>
		<th>Test Case Execution ID</th>
		<th>Test Case Execution Suite Name</th>
		<th>Tester</th>
		<th>Release Info</th>
		<th>Pass</th>
		<th>Fail</th>
		<th>Not Run</th>
		<th>In Block</th>
		<th>Pass Rate</th>
		<th>Total Test Cases</th>
	</tr>
	<s:iterator value="#testCaseExcutionSuitList" id="testcaseExecution" status="st">
		<tr onmouseover="selectLine(true, '${testcaseExecution.testcaseExcutionSuitId}', this)"
			onmouseout="selectLine(false, '${testcaseExecution.testcaseExcutionSuitId }', this)" id="singlebox">
			<td><input type="checkbox" name="multiExecutionSuitId"
				value="${testcaseExecution.testcaseExcutionSuitId }" /></td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')"
				class="td_id">${testcaseExecution.testcaseExcutionSuitId}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')">${testcaseExecution.testcaseExcutionSuitName
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')">${testcaseExecution.user.userName
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')">${testcaseExecution.releaseInfo
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')" style="background:green;">${testcaseExecution.pass
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')" style="background:red;">${testcaseExecution.fail
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')" style="background:white;">${testcaseExecution.norun
			}</td>
			
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')" style="background:yellow;">${testcaseExecution.inblock
			}</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')">${testcaseExecution.passRate
			}%</td>
			<td onclick="selectTestCaseClick('${testcaseExecution.testcaseExcutionSuitId }')">${testcaseExecution.totaltestcases
			}</td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="11"><span style="float: left;"><input
			type="submit" value="Delete"
			onclick="delTestSuite();" /></span>
			<span style="float:left; display:inline"><input type="button" value="create" onclick="location='preCreateTestExcutionSuit.action'"></input></span>
			
			<span style="float:left; display:inline"><input type="button" value="export" onclick="exportxml();"></input></span>
			<span
			style="float: right; display: inline"><p:page
			action="listTestCaseExecutionSuit.action" beanName="#pageBean"
			classForId="pagination-digg" activeClass="active"
			linkOffClass="link-off" totalPageClass="total-page">
		</p:page></span></td>
	</tr>
	
</table>

</form>
<div id="light" class="white_content2">Please wait while submit
result and exporting testcaseexcutionsuit ...</div>
</div>
</body>
</html>