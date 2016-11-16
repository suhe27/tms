function checkTarget() {

	var name = document.getElementById("targetName").value.trim();
	//var structure = document.getElementById("structure").value.trim();
	if (name == '') {

		alert("Target name can not null !");
		return false;
	} else{
		return true;
	}
}

function checkSubComponent() {
	var name = document.getElementById("compName").value.trim();
	if (name == '') {
		alert("Sub component name can not null !");
		return false;
	} else{
		return true;
	}
}