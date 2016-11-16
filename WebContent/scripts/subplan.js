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


function selectAllSubTestPlan(obj) {

	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});

}
function dateCompare(srcDate,desDate){
	var srcDates = srcDate.split("-");
	var desDates = desDate.split("-");
	var src = srcDates[1]+"/"+srcDates[2]+"/"+srcDates[0];
	var des = desDates[1]+"/"+desDates[2]+"/"+desDates[0];
	// alert(Date.parse(src));
	return (Date.parse(src)- Date.parse(des));
}
function delSubplan() {

	var checkboxs = $('input:checkbox:checked');

	if (checkboxs.length <= 0) {
		alert("Please choose sub plans to test!");
		return;
	}

	if (!confirm('Confirm delete selected subplan ?')) {
		return;
	}
	document.testplanform.action = "delSubPlan.action";
	document.testplanform.submit();
}

function updateSubTestPlan(date){
		var subPlanName = document.getElementById('subPlanName').value.trim();
		var dueDate = document.getElementById('dueDate').value.trim();			
			if(subPlanName == ''){
				alert("Sub plan name can not null!");
				return false;
			}else{
				return true;		
			}		
}

function selectAllSubTestPlan(obj){
	
	
	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});

	
} 



function checkSubTestPlan(projectName ,dueDate){
	
	var subPlanName = document.getElementById('subPlanName').value.trim();
	var totalTr = $('#table_board tr');
	if(subPlanName == ''){
		alert("Sub plan name can not null!");
		return false;
	}
		
	if(projectName == "DPDK"){
		var unitNames = $("#table_board tr #unitNames");
		var targets = $("#table_board tr #targetIds");
		var subTable = document.getElementById('table_board');
		
		for(i=0;i<totalTr.length;i++){
			
			var unitName = unitNames[i].value.trim();
			var targetValue=  targets[i].options[targets[i].selectedIndex].value;
			if(unitName==''){
				alert("Testunit: In row "+(i+1)+", unitName can not empty!" );
				return false;
			}
			if(targetValue =='-1'){
				
				alert("Testunit: In row "+(i+1)+", please choose target!" );
				return false;
			}
		}			
			for(i= 0;i<totalTr.length;i++){
			
				var target = targets[i].options[targets[i].selectedIndex].value;
				var unitName = unitNames[i].value;
								
				for(j=i+1;j<totalTr.length;j++)
				{
					var target1 = targets[j].options[targets[j].selectedIndex].value;
					var unitName1 = unitNames[j].value;
					
					if(unitName == unitName1){
						
						alert("Testunit: Row "+(i+1)+" equals row "+(j+1)+", unitname can not same!");
						return false;
						
					}	
                    if(target == target1){
						alert("Testunit: Row "+(i+1)+" equals row "+(j+1)+", target can not same ,please check!");
						return false;
						
					}
				}		
			}
			return true;	
		}
}

function checkExportSubTestPlan(){
	
	var checkboxs = $("#common_table input:checkbox:checked");
	if(checkboxs.length<=0){				
		alert("you must choose at least testunit to export!");
		return false;
	}	
	return true;	
}

var index = 0;
function addTarget(subDueDate)
{
var totalTr = $('#table_board tr');

index++;
if(totalTr.length>3){	
	return;
}

var unitNameAttr="testUnitList["+index+"].testUnitName";
var targetNameAttr="testUnitList["+index+"].targetId";
var dueDateAttr= "testUnitList["+index+"].dueDate";
var dueDateClass="dueDate"+index;
var objTable= document.getElementById("table_board");
var desTr=document.getElementById("tr_board");
var newTextUnit= document.createTextNode("UnitName:");
var newTextTarget= document.createTextNode("Target:");
var newTextDuedate=document.createTextNode("DueDate:");
var newunitNames=document.getElementById("unitNames").cloneNode(true);
var newtargetIds=document.getElementById("targetIds").cloneNode(true);
var newDueDates= document.createElement("input");
var _delInput = document.createElement("a");
var td1= document.createElement("td");
var td2= document.createElement("td");
var td3= document.createElement("td");
var td4= document.createElement("td");
var objTr= document.createElement("tr");
id="tr_"+index;
objTr.setAttribute("id", id);
_delInput.setAttribute("href", "javascript:removeBoard('"+id+"');");
var _del = document.createTextNode("Delete");
_delInput.appendChild(_del);
newunitNames.setAttribute("name",unitNameAttr);
newtargetIds.setAttribute("name",targetNameAttr);
newDueDates.setAttribute("type","text");
newDueDates.setAttribute("name",dueDateAttr);
newDueDates.setAttribute("class",dueDateClass);
newDueDates.setAttribute("value",subDueDate);
td1.appendChild(newTextUnit);
td1.appendChild(newunitNames);
objTr.appendChild(td1);


td2.appendChild(newTextTarget);
td2.appendChild(newtargetIds);
objTr.appendChild(td2);

//td3.appendChild(newTextDuedate);
//td3.appendChild(newDueDates);
//objTr.appendChild(td3);

td4.appendChild(_delInput);
objTr.appendChild(td4);

desTr.parentNode.appendChild(objTr);

newDueDates.onclick=new function(){
	$("."+dueDateClass).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm-dd",
		minDate: new Date(),		
		showButtonPanel: true,
		currentText:"Today"
	});
};

}

function removeBoard(id)
{
	var desTr=document.getElementById("tr_board");
	var delTr = document.getElementById(id);
	desTr.parentNode.removeChild(delTr);
	
}

function deleteTestUnits(value) {

	var checkboxs = $('input:checkbox:checked');
	if (checkboxs.length <= 0) {
		alert("you must choose at least one subplan!");
		return;
	}

	if (!confirm('Confirm delete selected subplan ?')) {
		return;
	}
	document.subplanform.action = "delTestUnits.action?subPlanId="+value;
	document.subplanform.submit();
}


