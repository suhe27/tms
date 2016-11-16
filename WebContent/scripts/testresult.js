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
function dateCompare(srcDate, desDate) {
	var srcDates = srcDate.split("-");
	var desDates = desDate.split("-");
	var src = srcDates[1] + "/" + srcDates[2] + "/" + srcDates[0];
	var des = desDates[1] + "/" + desDates[2] + "/" + desDates[0];
	// alert(Date.parse(src));
	return (Date.parse(src) - Date.parse(des));
}
function selectAllTestResult(obj) {

	$('input:checkbox').each(function() {
		$(this).attr("checked", obj.checked);
	});

}

function updateBatchTestResults() {
	document.testunitform.action = "updateBatchTestResult.action";
	document.testunitform.submit();
}

function filterTestResult() {

	document.testunitform.action = "toUpdateTestUnitRs.action";
	document.testunitform.submit();

}

function filterShowTestResult() {

	document.testunitform.action = "showTestResult.action";
	document.testunitform.submit();

}

function selectDefaultColor() {

	$('#row select').find('option:selected').each(

	function() {
		var value = $(this).text();

		if (value == "pass") {

			$(this.parentNode).css("background", "green");
		} else if (value == "fail") {

			$(this.parentNode).css("background", "red");

		} else if (value == "not run") {

			$(this.parentNode).css("background", "white");

		} else if (value == "block") {

			$(this.parentNode).css("background", "yellow");

		}

	}

	);

	$('#row select option').each(

	function() {
		var value = $(this).text();

		if (value == "pass") {

			$(this).css("background", "green");
		} else if (value == "fail") {

			$(this).css("background", "red");

		} else if (value == "not run") {

			$(this).css("background", "white");

		} else if (value == "block") {

			$(this).css("background", "yellow");

		}

	}

	);

	$('#row select').each(function() {

		$(this).change(

		function() {

			var value = $(this).find('option:selected').text();

			if (value == "pass") {

				$(this).css("background", "green");
			} else if (value == "fail") {

				$(this).css("background", "red");

			} else if (value == "not run") {

				$(this).css("background", "white");

			} else if (value == "block") {

				$(this).css("background", "yellow");

			}

		}

		);

	});
}

function addTestUnit(dueDate) {

	var unitNames = $("#table_board tr #unitNames");
	var targets = $("#table_board tr #targetIds");
	var subTable = document.getElementById('table_board');
	var testUnits = $("#common_table tr #flag");
	var testSuiteName = $('#testSuiteId').find('option:selected').text();
	var totalTr = $('#table_board tr');

	if (testSuiteName == '') {

		alert("please choose one testsuite!");
		return false;
	}

	for (i = 0; i < totalTr.length; i++) {

		var unitName = unitNames[i].value.trim();
		var targetValue = targets[i].options[targets[i].selectedIndex].value;
		if (unitName == '') {
			alert("add testunit: the row:" + (i + 1)
					+ " unitName can not empty!");
			return false;
		}
		if (targetValue == '-1') {

			alert("add testunit: the row:" + (i + 1)
					+ " please choose one target!");
			return false;
		}

	}

	for (i = 0; i < totalTr.length; i++) {

		var target = targets[i].options[targets[i].selectedIndex].value;
		var unitName = unitNames[i].value;

		for ( var k = 0; k < testUnits.length; k++) {
			var value = $(testUnits[k]).text();
			if (unitName == value) {

				alert("add testunit:the row :" + (i + 1)
						+ " ,this testUnit is already exists ,"
						+ " please check!");

				return false;
			}
		}

		for (j = i + 1; j < totalTr.length; j++) {
			var target1 = targets[j].options[targets[j].selectedIndex].value;
			var unitName1 = unitNames[j].value;

			if (unitName == unitName1) {

				alert("add testunit:the row :" + (i + 1) + " equals the row:"
						+ (j + 1)
						+ ", unitname can not be the same,please check!");

				return false;

			}
			if (target == target1) {

				alert("add testunit:the row :" + (i + 1) + " equals the row:"
						+ (j + 1)
						+ ", target can not be the same ,please check!");

				return false;

			}
		}

	}

	for ( var i = 0; i < subTable.rows.length; i++) {
		var unitDueDate = subTable.rows[i].cells[2].childNodes[1].value;

		if (unitDueDate == '') {

			unitDueDate = subDueDate;
		}

		var timegap = dateCompare(unitDueDate, dueDate);
		if (timegap > 0) {
			alert("at row index:" + (i + 1)
					+ "unit duedate can not later than sub plan's dueDate:"
					+ dueDate);
			return false;
		}
	}

	return true;

}

function selectShowTestResultsColor() {

	$('#row tr .result').each(

	function() {

		var value = $(this).text();
		value = $.trim(value);
		if (value == "pass") {

			$(this).css("background", "green");
		} else if (value == "fail") {

			$(this).css("background", "red");
 
		} else if (value == "not run") {

			$(this).css("background", "white");

		} else if (value == "block") {

			$(this).css("background", "yellow");

		}

	}

	);

}
 
function upadateTestUnit(date) {
	var testUnitName = document.getElementById('testUnitName').value.trim();
	var dueDate = document.getElementById('dueDate').value.trim();
	if(testUnitName == ''){
		alert("testUnitName can not null!please check!");
		return false;
	}
	if (dueDate == '') {
		alert("duedate can not null!");
		return false;
	} else {
		if (dateCompare(dueDate, date) > 0) {

			alert("testunit's duedate can not later than subplan's duedate:"
					+ date);
			return false;

		}

	}
	return true;
}