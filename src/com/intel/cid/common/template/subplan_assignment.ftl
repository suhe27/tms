<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Build Start</title>

<style type="text/css">
#tbrequest {
	width: 80%;
	vertical-align: top;
	background-color: #EEEEEE;
}

#tbrequest tr td.alignr {
	text-align: right;
	vertical-align: top;
	width: 50px;
}
</style>
</head>
<body>

	Hi ${user.userName},
	<br />
	Test plan:${testPlan.planName} is created for project:${testPlan.projectName}. Subplan: ${testPlan.subPlan.subPlanName} is assigned to you, please complete the task before due date. 
	<br />
	The detail tasks see the below table:
	<br />
	<table id="tbrequest" style="border-collapse: collapse;" border="1">
		
		<th>TASKNAME</th>		
		<th>DUEDATE</th>
		<#list testUnitList as unit>
 		<tr>
			<td style="text-align:center;">${unit.testUnitName}</td>
			<td style="background-color:red;text-align:center;">${unit.dueDate}</td>
		</tr>		
  </#list>	
	</table>
	<br />
	<br />
	<br />For more detailed information,
	<a href="${addr}/iTMS1/">click
		here</a>.
	<br />
	<br /> Best regards,
	<br /> iTMS Server
</body>
</html>