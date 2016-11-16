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

function checkPhase() {

	var phaseName = document.getElementById("phaseName").value.trim();
	//var structure = document.getElementById("structure").value.trim();
	if (phaseName == '') {

		alert("Phase name can not null !");
		return false;
	} else{
		return true;
	}
}

function checkExecutionPhase() {

	var phaseName = document.getElementById("phaseName").value.trim();
	//var structure = document.getElementById("structure").value.trim();
	if (phaseName == '') {

		alert("ExecutionType name can not null !");
		return false;
	} else{
		return true;
	}
}