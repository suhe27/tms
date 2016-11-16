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

	document.boardform.action = "listIxias.action";
	document.boardform.submit();
	
}
function checkIxia() {
	var boardname = document.getElementById("boardname").value.trim();
	
	if (boardname == '') {
		alert("prot name can not null");
		return false;
	}

	else {
		return true;
	}

}

function applyIxias() {
	var operation = "apply";
	var checkedBoxs = $('input:checkbox:checked');
	if (checkedBoxs.length <= 0) {
		alert('you must choose  at least one ixia port!');
		return;
	}

	for (i = 0; i < checkedBoxs.length; i++) {
		var row = checkedBoxs[i].parentNode.parentNode.rowIndex;
		var index = row-1;
		var state_index = $('.states_' + index).text();
		state_index = $.trim(state_index);
		if (state_index != 'idle') {
			alert('table row:' + row + ',' + "ixia prot has been applied!");
			return;
		}
		var user_index = $('#users_' + index).find('option:selected').text()
				.trim();

	
		if (user_index == 'none') {
			alert('table row:' + row + ',' + "owner can not empty!");
			return;
		}

		
	}
	document.boardform.action = "allocateIxias.action?operation=" + operation;
	document.boardform.submit();
}

function releaseIxias() {
	var operation = "release";

	var checkedBoxs = $('input:checkbox:checked');
	if (checkedBoxs.length <= 0) {
		alert('you must choose  at least one ixia port!');
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
			alert('table row:' + row + ',' + 'ixia port has been been released!');
			return;
		}
	}

	document.boardform.action = "cancelIxias.action?operation=" + operation;
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
function filterIxia() {
	document.boardform.action = "listIxias.action";
	document.boardform.submit();
}
function filterAllocateIxia() {
	document.boardform.action = "toAllocateIxias.action";
	document.boardform.submit();
}
