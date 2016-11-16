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

function selectAllTestCaseSuites(obj) {

	var testcasesuits = document.getElementsByName("multiCaseId");
	for (i = 0; i < testcases.length; i++) {
		testcasesuits[i].checked = obj.checked;
	}
}

function selectAllTestCases(obj) {
	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
		if($(this).val() != 'on') {
			$.post("addCaseToSuiteBySession.action", { isSelected: obj.checked, multiCaseId: $(this).val() } );
		}
	});

}

function delTestSuite()
{
	var checkboxs = $('input:checkbox:checked');
	
	if(checkboxs.length<=0)
	{
		alert("Please choose test suites to delete!");
		return;
	}

	if (!confirm('Confirm delete selected test suites ?')) {
		return;
	}
	document.testsuiteform.action = "delTestSuite.action";
	document.testsuiteform.submit();

}

function copyTestSuite()
{
	var checkboxs = $('input:checkbox:checked');
	
	if(checkboxs.length <= 0)
	{
		alert("Please choose one suite to copy or chose more than one suite merge to a new one!");
		return;
	}
	for(i=0 ;i<checkboxs.length;i++){
		var index1 =checkboxs[i].parentNode.parentNode.rowIndex;
		var project1 = $('#project_'+index1).html();
		project1= $.trim(project1);
		for(j=i+1;j<checkboxs.length;j++){
			
			var index2 =checkboxs[j].parentNode.parentNode.rowIndex;
			var project2 = $('#project_'+index2).html();
			project2= $.trim(project2);
			if(project1==''){
				
				alert("row : "+(index1)+" project can not null");
				return;
			}
				if(project2==''){
				
					alert("row : "+(index2)+" project can not null");
				return;
			}
				
			if(project1!=project2)	{
				alert("Selected suite must in same project, error in row "+(index1)+" and "+(index2));
				return;
			}
			
		}
		
	}
	
	//document.testsuiteform.action = "copyTestSuite.action";
	//document.testsuiteform.submit();
	var ids = document.getElementById("selectedSuiteIds").value;
	MyWindow.OpenCenterWindowScroll("copyTestSuite.action?multiSuiteId=" + ids,'detail',700,900);
}

function checkAddSuiteForm(){
	
	if($("#testSuiteName").val().length<1){
		alert("Test suite name can not null .");
		return false ;
	}
	if($("#projectId").val()=='-1'){
		alert("Project can not null . ");
		return false ;
	}
}

function createSuperTestSuite() {
	var checkboxs = $('input:checkbox:checked');
	if(checkboxs.length<=0)
	{
		alert("you must choose at least one test suite!");
		return;
	}
//	var index =checkboxs[0].parentNode.parentNode.rowIndex;
//	alert(index);
//	alert($('#project_'+index).html());
	for(i=0 ;i<checkboxs.length;i++){
		
		var index1 =checkboxs[i].parentNode.parentNode.rowIndex;
		var project1 = $('#project_'+index1).html();
		project1= $.trim(project1);
		
		for(j=i+1;j<checkboxs.length;j++){
			
			var index2 =checkboxs[j].parentNode.parentNode.rowIndex;
			var project2 = $('#project_'+index2).html();
			project2= $.trim(project2);
			if(project1==''){
				
				alert("row : "+(index1)+" project can not null");
				return;
			}
				if(project2==''){
				
					alert("row : "+(index2)+" project can not null");
				return;
			}
				
			if(project1!=project2)	{
				alert("the two rows :"+(index1)+", "+(index2)+" project should be the same!");
				return;
			}
			
		}
		
	}
	
	
	if (!confirm('Confirm create Testsuite contains these selected testcases ?')) {
		return;
	}
	
	var name = window.prompt("please input testcase suite name!", "");

	if (name == null || name == '') {

		return;
	}
	document.testsuiteform.action = "createSuperTestSuite.action?testSuiteName="+ name;
	document.testsuiteform.submit();
}

function delTestCaseInTestSuite(){
	
	
	var checkboxs = $('input:checkbox:checked');
	
	if(checkboxs.length<=0)
	{
		alert("Please choose test cases to delete!");
		return;
	}
	
	if(!confirm('Confirm delete selected test cases ?'))
	{
		return ;
	}
	//document.testsuiteform.action = "delTestCaseInTestSuite.action";		
	document.testsuiteform.submit();
} 

function appendSuitIdinHref()
{
	var testcasesuitId = $("#testcasesuitId").val();
	var a = $("#pagination-digg li a");

	for ( var i = 0; i < a.length; i++) {
		var href = a[i].href;
		href = href + "&";
		var tempHref = href.split("&");
		href = tempHref[0];
		href = href + "&testcasesuitId=" + testcasesuitId;
		a[i].href = href;
	}
}

function selectTestCasesInOneSuite(obj)
{
	$("input:checkbox").each(function()
			{	
			$(this).attr("checked",obj.checked);
			});
}

function selectTestSuitesCurrentPage(obj)
{
	$("input:checkbox").each(function()
			{	
			$(this).attr("checked",obj.checked);
			});
}





function checkTestCaseExecution()
{
	
	if (!confirm('Confirm create TestExcution suite contains these selected test suites ?')) {
		return;
	}
	light_obj = document.getElementById('light');
	light_obj.style.display = 'block';
	pop = document.getElementById('pop');
	pop.style.display = 'block';
	document.testsuiteform.action = "createTestExcutionSuit.action";
	document.testsuiteform.submit();

}



