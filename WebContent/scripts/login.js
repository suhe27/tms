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

function checkLogin() {

	username = document.getElementById("username").value.trim();
	password = document.getElementById("password").value.trim();

	if (username == '' || password == '') {
		alert("username or password can not null!");
		return false;
	}
	return true;
}


function timer(){
	
	window.setTimeout("loginError()", 5000); 
	
}

function loginError() {
	
	var obj = $('#errorMsg').text();
	if (obj != null && obj != '') {

		$('#errorMsg').hide('slow');
	

	}

}

function checkRegister() {
	username = document.getElementById("username").value.trim();
	password = document.getElementById("password").value.trim();
	email = document.getElementById("email").value.trim();
	level = document.getElementById("level").value.trim();
	var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	var levelReg = /^[1-9]$/;
	var teamSize = $('.form_table tr input:checkbox:checked').length;
	
	if (username == '') {
		alert("User name can not null!");
		return false;
	} else if (password == '') {
		alert("Password  can not null!");
		return false;
	} else if (email == '') {

		alert("Email can not null!");
		return false;
	} else if (!emailReg.test(email)) {

		alert("Invalid email address!");
		return false;
	} else if(teamSize<=0){
		alert("Please choose at least one team!");
		return false;
	}
	
	else {
		return true;
	}

}

function checkUpdate() {
	username = document.getElementById("userName").value.trim();
	password = document.getElementById("password").value.trim();
	
	if (username == '') {
		alert("User name can not null!");
		return false;
	} else if (password == '') {
		alert("Password  can not null!");
		return false;
	} else {
		return true;
	}

}

function logout() {
	window.location.href = "logout.action";
}

// $(document).ready(function() {
// var obj = {
// one : 1,
// two : 2,
// three : 3,
// four : 4,
// five : 5,
// six : 6
// };
// $.each(obj, function(key, val) {
// $("#title_" + obj[key]).click(function() {
// $("#opt_" + obj[key]).toggle("slow");
// $("#title_" + obj[key]).toggleClass("title2");
// });
// });
// });

function selectLine(over, id, obj) {
	if (over) {
		obj.style.backgroundColor = "#FFEBCD";
		obj.style.cursor = "pointer";
	} else {
		obj.style.backgroundColor = "#FFFFFF";
		obj.style.cursor = "auto";
	}
}
