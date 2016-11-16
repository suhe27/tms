String.prototype.trim = function() {
	return Trim(this);
};
function LTrim(str) {
	var i;
	for (i = 0; i < str.length; i++) {
		if (str.charAt(i) != " " && str.charAt(i) != " ")
			break;
	}
	str = str.substring(i, str.length);
	return str;
}
function RTrim(str) {
	var i;
	for (i = str.length - 1; i >= 0; i--) {
		if (str.charAt(i) != " " && str.charAt(i) != " ")
			break;
	}
	str = str.substring(0, i + 1);
	return str;
}
function Trim(str) {
	return LTrim(RTrim(str));
}

function selectAllTestExecution(obj) {

	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});

}



function delTestExecution() {

	var checkboxs = $('input:checkbox:checked');

	if (checkboxs.length <= 0) {
		alert("Please choose test executions!");
		return;
	}

	if (!confirm('Confirm delete selected TestExecution ?')) {
		return;
	}
	document.testexecutionForm.action = "deleTestExecution.action";
	document.testexecutionForm.submit();
}

function delSubExecution() {

	var checkboxs = $('input:checkbox:checked');

	if (checkboxs.length <= 0) {
		alert("Please choose sub executions!");
		return;
	}

	if (!confirm('Confirm delete selected SubExecution ?')) {
		return;
	}
	document.testexecutionForm.action = "delSubExecution.action";
	document.testexecutionForm.submit();
}

function checkSubExecutionForm(){
	
	if($("#subExecutionName").val().length<1){
		alert("Sub execution name can't be null .");
		return false ;
	}
	if($("#subPlanId").val()==''){
		alert("Sub test plan can't be null . ");
		return false ;
	}
	
	if($("#platform").val()==''){
		alert("Platform can't be null .");
		return false;
	}
	
	if($("#os").val()==''){
		alert("OS can't be null .");
		return false;
	}
	
	if($("#tester").val()==''){
		alert("Tester can't be null .");
		return false;
	}
	
	if($("#dueDate").val()==''){
		alert("Due date can't be null .");
		return false;
	}

	var dueDate = $("#dueDate").val();
	var startPlan = document.getElementById('ExeStartDate').value.trim();
	var endPlan = document.getElementById('ExeEndDate').value.trim();
	var startGap = compareTime(startPlan,dueDate);
	if(startGap < 0){
		alert("Due date must after test execution's start date: "+startPlan);
		return false;
	}
	var endGap = compareTime(dueDate,endPlan);
	if(endGap < 0){
		alert("Due date must before test execution's end date: " + endPlan);
		return false;
	}
	
	return true;
}

function checkTestExecution(){
	
	if($("#ExecutionName").val().length<1){
		alert("Execution name can not null .");
		return false ;
	}
	if($("#project").val()==''){
		alert("Project can not null . ");
		return false ;
	}
	if($("#testPlan").val()==''){
		alert("Test plan can not null .");
		return false;
	}
	if($("#phase").val()==''){
		alert("Execution type can not null .");
		return false;
	}
	if($("#buildId").val()==''){
		alert("Build type can not null .");
		return false;
	}
	if($("#releaseCycle").val()==''){
		alert("Release cycle can not null .");
		return false;
	}
	
	var  startDate= document.getElementById("startDate").value.trim();
	var  endDate= document.getElementById("endDate").value.trim();
	if (startDate.length<1) {
		alert("Start date can not null!");
		return false;
	}
	if (endDate.length<1) {
		alert("End date can not null!");
		return false;
	}
	var startPlan = document.getElementById('PlanStartDate').value.trim();
	var endPlan = document.getElementById('PlanEndDate').value.trim();
	var startGap = compareTime(startPlan,startDate);
	if(startGap < 0){
		alert("Start date must after test plan's start date: "+startPlan);
		return false;
	}
	var endGap = compareTime(endDate,endPlan);
	if(endGap < 0){
		alert("End date must before test plan's end date: " + endPlan);
		return false;
	}
	
	var gap = compareTime(startDate,endDate);
	if(gap < 0){
		alert("End date must be later than start date!");
		return false;
	}
	
	return true;
}

function compareTime(startdate, enddate) {

	if(startdate =='')
	{
		alert("startdate can not null!");
		return 0;
	}
	if(enddate =='')
	{
		alert("enddate can not null!");
		return 0;
	}
	
	var startdates = startdate.split('-');
	var enddates = enddate.split('-');

	startdate = startdates[1] + '/' + startdates[2] + '/' + startdates[0] ;
			
	enddate = enddates[1] + '/' + enddates[2] + '/' + enddates[0] ;

	// MM/dd/yyyy HH:mm:ss
	// alert(Date.parse(startdate));
	// alert(Date.parse(enddate));
	//var a = (Date.parse(enddate) - Date.parse(startdate)) / (24 * 3600 * 1000);
	 var a = Date.parse(enddate) - Date.parse(startdate) ;
	return a;

}
	



