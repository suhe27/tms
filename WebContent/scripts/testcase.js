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

function selectAllTestCases_bak(obj) {
	var testcases = document.getElementsByName("multiCaseId");
	for (i = 0; i < testcases.length; i++) {
		testcases[i].checked = obj.checked;
	}
}

function selectAllTestCases(obj) {

	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});
}

function selectAllTestCasesCrossPage(obj){
	var testcases = document.getElementsByName("multiCaseId");
	for (i = 0; i < testcases.length; i++) {
		testcases[i].checked = obj.checked;
	}

	$("#isSelected").val(obj.checked);
	var selectValue = $("#isSelected").val();
	var a = $("#pagination-digg li a");

	for ( var i = 0; i < a.length; i++) {
		var href = a[i].href;
// href = href + "&";
// var tempHref = href.split("&");
// href = tempHref[0];
		href = href + "&isSelected=" + selectValue;
		a[i].href = href;
	}
}


function checkSelectAllTestCases(obj) {	
	var all = document.getElementById("isSelected");
	all.value = obj;
	if (obj == "on") {
		var all = document.getElementById("isSelected");
		all.value = obj;
		var testcases = document.getElementsByName("multiCaseId");
		for (i = 0; i < testcases.length; i++) {
			testcases[i].checked = "true";
		}
		var selectValue = $("#isSelected").val();
		var a = $("#pagination-digg li a");
		for ( var i = 0; i < a.length; i++) {
			var href = a[i].href;
			// href = href + "&";
		    // var tempHref = href.split("&");
			// href = tempHref[0];
			href = href + "&isSelected=" + selectValue;
			a[i].href = href;
		}
	}else{
		var selectValue = $("#isSelected").val();
		var a = $("#pagination-digg li a");
		for ( var i = 0; i < a.length; i++) {
			var href = a[i].href;
		    var selectIndex = href.indexOf("&isSelected=");
		    if(-1 != selectIndex){
		    	 newHref= href.substring(0,selectIndex);
				 a[i].href = newHref;
		    }
		}
	}
}

function filterTestCase() {
	document.testcaseform.action = "listTestCases.action";		
	document.testcaseform.submit();

}
function filterCreateTestSuite() {
	document.casesuiteform.action = "preCreateTestSuite.action";		
	document.casesuiteform.submit();
}

function delTestCases()
{
	
	var checkedBoxs = $('input:checkbox:checked');
	if(checkedBoxs.length<=0)
	{
		alert('Please choose test cases to delete!');
		return;
		
	}
	if(!confirm('Confirm delete selected test cases?'))
	{
		return ;
	}
	
	document.casesuiteform.action = "delTestCase.action";		
	document.casesuiteform.submit();
}


function exportTestCase(){
		
	var checkedBoxs = $('input:checkbox:checked');
	
	if(checkedBoxs.length<=0)
	{
		alert('Please choose test cases to export!');
		return;	
	}
	document.casesuiteform.action = "exportTestCase.action";
	document.casesuiteform.submit();
}


function addTestCaseValidation(){
	var integer = /^[0-9]*[1-9][0-9]*$/;
	var data = $("#teamId");
	if(""==data.val()){
		alert("Team can not null .");
		data.focus();
		return false;
	}
	data = $("#projectId");
	if(""==data.val()){
		alert("Project can not null .");
		data.focus();
		return false;
	}
	data = $("#featureId");
	if(""==data.val()){
		alert("Feature can not null .");
		data.focus();
		return false;
	}
	data = $("#compId");
	if(""==data.val()){
		alert("Component can not null .");
		data.focus();
		return false;
	}
//	data = $("#subCompId");
//	if(""==data.val()){
//		alert("subComponent can not null .");
//		data.focus();
//		return false;
//	}
	data = $("#osId");
	if(""==data.val()){
		alert("OS can not null .");
		data.focus();
		return false;
	}
	data = $("#userId");
	if(""==data.val()){
		alert("Owner can not null .");
		data.focus();
		return false;
	}
	data = $("#testTypeId");
	if(""==data.val()){
		alert("Test Type can not null .");
		data.focus();
		return false;
	}
	data = $("#automation");
	if(""==data.val()){
		alert("Automation can not null .");
		data.focus();
		return false;
	}
	data = $("#caseStateId");
	if(""==data.val()){
		alert("State can not null .");
		data.focus();
		return false;
	}
	data = $("#versionId");
	if(""==data.val()){
		alert("Version can not null .");
		data.focus();
		return false;
	}
	/**
	data = $("#testcasealiasId");
	if(""==data.val()){
		alert("Test Case id can not null .");
		data.focus();
		return false;
	}
	**/
	data = $("#testcaseName");
	if(""==data.val()){
		alert("Test Case Name can not null .");
		data.focus();
		return false;
	}
	/**
	data = $("#timeout");
	if(data.val()!="" && data.val()!="0") {
		if (!integer.test(data.val())) {
			alert("Timeout can only be numbers !");
			return false;
		}
	}
	**/
	return true;
}

function validate(id,hint){
	var data = $("#"+id);
	if(""==data.val()){
		alert(id+" can not null .");
		data.focus();
		return;
	}
}
