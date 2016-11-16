function checkHardWare() {
	var hardwareName = document.getElementById("hardwareName").value.trim();
	if (hardwareName == '') {

		alert("hardwareName can not null!");
		return false;

	} else {
		return true;
	}

}