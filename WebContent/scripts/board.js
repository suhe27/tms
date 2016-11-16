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

function timerRefresh(t) {
	window.setInterval("refreshY()", t * 1000);
}
function refreshY() {

	document.boardform.action = "listBoards.action";
	document.boardform.submit();
	
}
function checkBoard() {
	var boardname = document.getElementById("boardname").value.trim();
	var ipAddr = document.getElementById("ipAddr").value.trim();
	var ipRegex = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;
	var siliconNum = document.getElementById("siliconNum").value.trim();
	var siliconNumRegx = /^[0-9]$/;
	var startdate = document.getElementById('startdate').value.trim();
	var enddate = document.getElementById('enddate').value.trim();
	if (!(startdate == '' && enddate == '')) {
		var timegap = compareTime(startdate, enddate);
		if (timegap <= 0) {
			alert("end date must be later than start date.");
			return false;
		}
		
	}
	if (boardname == '') {
		alert("boardname can not null");
		return false;
	} else if (ipAddr == '') {
		alert("Ip Address can not null");
		return false;
	} else if (siliconNum != '' && !siliconNumRegx.test(siliconNum)) {
		alert("invalide siliconNum value just only can contains[1-9],please check!");
		return false;
	} else if (!ipRegex.test(ipAddr)) {
		alert("Ip Address error ,please check!");
		return false;
	}

	else {
		return true;
	}

}

function applyBoards() {
	var operation = "apply";
	var checkedBoxs = $('input:checkbox:checked');
	if (checkedBoxs.length <= 0) {
		alert('you must choose  at least one board!');
		return;
	}

	for (i = 0; i < checkedBoxs.length; i++) {
		var row = checkedBoxs[i].parentNode.parentNode.rowIndex;
		var index = row-1;
		var state_index = $('.states_' + index).text();
		state_index = $.trim(state_index);
		if (state_index != 'idle') {
			alert('table row:' + row + ',' + "board has been applied!");
			return;
		}
		var startdate_index = document.getElementById('startdate_' + index).value
				.trim();
		var enddate_index = document.getElementById('enddate_' + index).value
				.trim();
		var user_index = $('#users_' + index).find('option:selected').text()
				.trim();

		if (startdate_index == '' || startdate_index == null) {
			alert('table row:' + row + ',' + "start date can not empty!");
			return;
		}
		if (enddate_index == '' || enddate_index == null) {
			alert('table row:' + row + ',' + "end date can not empty!");
			return;
		}
		if (user_index == 'none') {
			alert('table row:' + row + ',' + "owner can not empty!");
			return;
		}

		var timegap = compareTime(startdate_index, enddate_index);

		if (timegap <= 0) {
			alert('table row:'
					+ row
					+ ','
					+ "end date must be later than start date.");
			return;
		}
	}
	document.boardform.action = "allocateBoards.action?operation=" + operation;
	document.boardform.submit();
}

function releaseBoards() {
	var operation = "release";

	var checkedBoxs = $('input:checkbox:checked');
	if (checkedBoxs.length <= 0) {
		alert('you must choose  at least one board!');
		return;
	}

	for (i = 0; i < checkedBoxs.length; i++) {
		var row = checkedBoxs[i].parentNode.parentNode.rowIndex;
		var index = row-1;
		var state_index = $('.states_' + index).text();
		state_index = $.trim(state_index);
		var user_index = $('#users_' + index).find('option:selected').text()
				.trim();
		if (state_index == 'idle' || user_index == 'none') {
			alert('table row:' + row + ',' + 'board has been been released!');
			return;
		}
	}

	document.boardform.action = "cancelBoards.action?operation=" + operation;
	document.boardform.submit();
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
	
	var startdates = startdate.substring(0, 10).split('-');
	var enddates = enddate.substring(0, 10).split('-');

	startdate = startdates[1] + '/' + startdates[2] + '/' + startdates[0] + ' '
			+ startdate.substring(10, 19);
	enddate = enddates[1] + '/' + enddates[2] + '/' + enddates[0] + ' '
			+ enddate.substring(10, 19);

	// MM/dd/yyyy HH:mm:ss
	// alert(Date.parse(startdate));
	// alert(Date.parse(enddate));
	//var a = (Date.parse(enddate) - Date.parse(startdate)) / (24 * 3600 * 1000);
	var a = Date.parse(enddate) - Date.parse(startdate) ;
	return a;

}

function getCurrentTime() {
	var date = new Date();

	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();

	if (month < 10) {
		month = "0" + month;
	}

	if (day < 10) {
		day = "0" + day;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minute < 10) {
		minute = "0" + minute;
	}
	if (second < 10) {
		second = "0" + second;
	}

	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":"
			+ second;
}

function autoCompleteTime(id) {
	var startdate = $('#' + id).val();
	if (startdate == '') {
		$('#' + id).val(getCurrentTime());
	}
}
function filterBoard() {
	document.boardform.action = "listBoards.action";
	document.boardform.submit();
}
function filterAllocateBoard() {
	document.boardform.action = "toAllocateBoards.action";
	document.boardform.submit();
}
