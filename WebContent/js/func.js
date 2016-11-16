
/*
判断是否为空
*/
function isEmpty(inValue) {
	if (inValue == "") {
		return true;
	}
	var result = inValue.replace(/^\s+|\s+$/g, "");
	if (result.length == 0) {
		return true;
	}
	return false;
}
/*
判断是否是字母
*/
function isAaZz(inValue) {
	if (isEmpty(inValue)) {
		return false;
	}
	var result = false;
	result = /[^a-zA-Z]/g.test(inValue);
	return !result;
}
function isDate(datestr) {
	var lthdatestr;
	if (datestr != "") {
		lthdatestr = datestr.length;
	} else {
		lthdatestr = 0;
	}
	var tmpy = "";
	var tmpm = "";
	var tmpd = "";
	var status;
	status = 0;
	if (lthdatestr == 0) {
		return 0;
	}
	for (i = 0; i < lthdatestr; i++) {
		if (datestr.charAt(i) == "-") {
			status++;
		}
		if (status > 2) {
			return 0;
		}
		if ((status == 0) && (datestr.charAt(i) != "-")) {
			tmpy = tmpy + datestr.charAt(i);
		}
		if ((status == 1) && (datestr.charAt(i) != "-")) {
			tmpm = tmpm + datestr.charAt(i);
		}
		if ((status == 2) && (datestr.charAt(i) != "-")) {
			tmpd = tmpd + datestr.charAt(i);
		}
	}
	year = new String(tmpy);
	month = new String(tmpm);
	day = new String(tmpd);
	if ((tmpy.length != 4) || (tmpm.length > 2) || (tmpd.length > 2)) {
		return 0;
	}
	if (!((1 <= month) && (12 >= month) && (31 >= day) && (1 <= day))) {
		return 0;
	}
	if (!((year % 4) == 0) && (month == 2) && (day == 29)) {
		return 0;
	}
	if ((month <= 7) && ((month % 2) == 0) && (day >= 31)) {
		return 0;
	}
	if ((month >= 8) && ((month % 2) == 1) && (day >= 31)) {
		return 0;
	}
	if ((month == 2) && (day >= 30)) {
		return 0;
	}
	return 1;
}
function isNum(str) {
	var text = str;
	if (text.length == 0) {
		return false;
	}
	if (text.indexOf("-") > 0) {
		return false;
	}
	if (text.indexOf("-") == 0) {
		if (text.indexOf("-") != text.lastIndexOf("-")) {
			return false;
		}
		text = text.substring(1, text.length);
	}
	if (text.indexOf(".") >= 0) {
		if (text.indexOf(".") != text.lastIndexOf(".")) {
			return false;
		}
		var code = 0;
		for (var i = 0; i < text.length; i++) {
			code = text.charCodeAt(i);
			if (code >= 48 && code <= 57 || code == 46) {
				//do nothing
			} else {
				return false;
			}
		}
	} else {
		for (var i = 0; i < text.length; i++) {
			if (text.charCodeAt(i) < 48 || text.charCodeAt(i) > 57) {
				return false;
			}
		}
	}
	return true;
}
/*
*是否字母数字
*
*/
function isAaZz09(inValue) {
	if (isEmpty(inValue)) {
		return false;
	}
	var result = false;
	result = /[^a-zA-Z0-9]/g.test(inValue);
	return !result;
}
/*
*
*
*/
function isCharacter(inValue) {
	if (isEmpty(inValue)) {
		return false;
	}
	var result = false;
	result = /^([a-zA-z_]{1})([\w]*)$/g.test(inValue);
	return !result;
}
/*
** src：路径；winName：窗口名称；top：上距；left：左距； 
**height：高；width：宽；scrolling：滚动条
*/
function openWindow(src, winName, top, left, height, width, scrolling) {
	if (winName == "0") {
		winName = "";
	}
	window.open(src, winName, "top=" + top + " , left=" + left + " ,height=" + height + " , width=" + width + " , toolbar=no, menubar=no, scrollbars=" + scrolling + ", resizable=no,location=no, status=no");
}
/*
**src：路径；winName：窗口名称；top：上距；left：左距； 
**height：高；width：宽；scrolling：滚动条
*/
function openModalDialog(src, winName, top, left, height, width, scrolling) {
	showModalDialog(src, winName, "dialogHeight: " + height + "px; dialogWidth: " + width + "px; dialogTop: " + top + "px; dialogLeft: " + left + "px; edge: Raised; center: Yes; help: Yes; resizable: no; status: no;scrolling:" + scrolling + ";");
}
/*
**全屏
*/
function windowAllScreen() {
	self.moveTo(0, 0);
	self.resizeTo(screen.availWidth, screen.availHeight);
}
function isMsisdn(textName, str) {
	var msisdn = textName.value;
	var reg = /(158|159|13[4-9]{1})[0-9]{8}$/;
	if (isEmpty(msisdn)) {
		return true;
	}
	if (!reg.test(msisdn)) {
		if (isEmpty(str)) {
			str = "\u5ba2\u6237\u53f7\u7801\u683c\u5f0f\u4e0d\u6b63\u786e\uff01";
		}
		alert(str);
		textName.focus();
		return false;
	}
	return true;
}
function OpenCenterWindow(url, name, height, width) {
	var str = " height=" + height + ",innerHeight=" + height;
	str += ",width=" + width + ",innerWidth=" + width;
	if (window.screen) {
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;
		var xc = (aw - width) / 2;
		var yc = (ah - height) / 2;
		str += ",left=" + xc + ",screenX=" + xc;
		str += ",top=" + yc + ",screenY=" + yc;
	}
	return window.open(url, name, str);
}
function OpenCenterWindowScroll(url, name, height, width) {
	var str = " height=" + height + ",innerHeight=" + height;
	str += ",width=" + width + ",innerWidth=" + width;
	if (window.screen) {
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;
		var xc = (aw - width) / 2;
		var yc = (ah - height) / 2;
		str += ",left=" + xc + ",screenX=" + xc;
		str += ",top=" + yc + ",screenY=" + yc;
		str += ",scrollbars=yes";
	}
	return window.open(url, name, str);
}
function changeColor() {
	var table = document.getElementById("row");
	var rows = table.getElementsByTagName("tr");
	for (i = 0; i < rows.length; i++) {
		var oldClass;
		rows[i].onmouseover = function () {
			oldClass = this.className;
			if (this.className != "clickRow") {
				this.className = "over";
			}
		};
		rows[i].onmouseout = function () {
			if (this.className != "clickRow") {
				this.className = oldClass;
			}
		};
	}
}
function changeColor2(src) {
	var table = document.getElementById(src);
	var rows = table.getElementsByTagName("tr");
	for (var i = 0; i < rows.length; i++) {
		var oldClass;
		rows[i].onmouseover = function () {
			oldClass = this.className;
			if (this.className != "clickRow") {
				this.className = "over";
			}
		};
		rows[i].onmouseout = function () {
			if (this.className != "clickRow") {
				this.className = oldClass;
			}
		};
	}
}
function clickRow(src) {
	var row = src;
	while (row != null && row.tagName != "TR") {
		row = row.parentElement;
	}
	var table = document.getElementById("row");
	var rows = table.getElementsByTagName("tr");
	rows[row.rowIndex].className = "clickRow";
}
function disableImage(src) {
	src.onclick = function () {
	};
	src.style.filter = "gray";
	src.style.cursor = "";
}
	
		//
function enableImage(src, fun1) {
	src.onclick = function () {
		fun1(src);
	};
	src.style.filter = "black";
	src.style.cursor = "hand";
}
/*	图片按钮结合防止重复提交的示例代码
	
	
		<img src="<%=Constants.SAVE_BUTTON %>" onclick="save(this);" 

name="saveButton" align="absmiddle" style="cursor:hand" />
		enableImage(form1.saveButton,save);
		
		function save(src){
	disableImage(src);
      	form1.submit();

	}

*/
function showElement(src) {
	document.getElementById(src).style.display = "";
}
function hideElement(src) {
	document.getElementById(src).style.display = "none";
}
function disableButton(src) {
	src.disabled = true;
}
function enableButton(src) {
	src.disabled = false;
}
function getRow(src) {
	var row = src;
	while (row && row.tagName != "TR") {
		row = row.parentElement;
	}
	return row;
}
function rebuildRowNum(tableId) {
	var tb = document.getElementById(tableId);
	var rows = tb.rows;
	var cell;
	for (i = 1; i < rows.length; i++) {
		cell = rows[i].cells[0];
		cell.innerText = i;
	}
}

