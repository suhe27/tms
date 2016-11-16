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

function check_IP(ip)    
{  
	   var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//������ʽ   
	   if(re.test(ip))   
	   {   
	       if(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) 
	       { return true; }
	       else {return false;}
	   }   
	   else { return false; }
}

function checkTCOs() {

	var name = document.getElementById("osName").value.trim();
	//var structure = document.getElementById("structure").value.trim();
	if (name == '') {

		alert("OS name can not null !");
		return false;
	} else{
		return true;
	}
}

function checkOS() {
	var osName = document.getElementById("osName").value.trim();
	var projectId = document.getElementById("projectId").value.trim();
	var hostName = document.getElementById("hostName").value.trim();
	var ip = document.getElementById("ip").value.trim();
	var loginName = document.getElementById("loginName").value.trim();
	var passwd = document.getElementById("passwd").value.trim();
	var port = document.getElementById("port").value.trim();
	var codePath = document.getElementById("codePath").value.trim();
	var cmds = document.getElementById("cmds").value.trim();
	var parameters = document.getElementById("parameters").value.trim();
	var integer = /^[0-9]*[1-9][0-9]*$/;
	if (osName == '') {
		alert("Os name can not null !");
		return false;
	} else if (projectId == '') {
		alert("Project can not null !");
		return false;

	} else if (hostName == '') {
		alert("Host name can not null !");
		return false;
	
	} else if ( check_IP(ip) == false ) {
		alert("Please input correct IP address");
		return false;
	} else if (loginName == '') {
		alert("Login name can not null !");
		return false;
	} else if (passwd == '') {
		alert("Password can not null !");
		return false;
	} else if (!integer.test(port)) {
		alert("Port can only be numbers !");
		return false;
	} else if (codePath == '') {
		alert("Code path can not null !");
		return false;
	} else if (cmds == '') {
		alert("Command can not null !");
		return false;
	} else if (parameters == '') {
		alert("Parameters can not null !");
		return false;
	} 
	else {
		return true;
	}
}