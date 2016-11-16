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

function selectAllTestPlan(obj) {

	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});

}



function delTestPlan() {

	var checkboxs = $('input:checkbox:checked');

	if (checkboxs.length <= 0) {
		alert("Please choose test plans to delete!");
		return;
	}

	if (!confirm('Confirm delete selected testplan ?')) {
		return;
	}
	document.testplanform.action = "delTestPlan.action";
	document.testplanform.submit();
}

function checkTestPlan()
{
	var  planName= document.getElementById("planName").value.trim();

	var  project= document.getElementById("projectId").value.trim();
	var  phase = document.getElementById("phaseId").value.trim();
	var  startDate= document.getElementById("startDate").value.trim();

	var  endDate= document.getElementById("endDate").value.trim();
	
	if (planName == '') {
		alert("Test plan name can not null!");
		return false;
	}
	if (project == '-1') {
		alert("Project can not null!");
		return false;
	}
	
	if (phase == '-1') {
		alert("Phase can not null!");
		return false;
	}
	if (startDate.length<1) {
		alert("Start date can not null!");
		return false;
	}
	if (endDate.length<1) {
		alert("End date can not null!");
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
	



