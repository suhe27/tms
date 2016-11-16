/**
 * exportFile
 * @author: zhengbingx.wu@intel.com
 */
function exportFile(type)
{
	document.getElementById('type').value=type;
	document.getElementById('exl').submit();
}
/**
 * popup
 * @author: zhengbingx.wu@intel.com
 */
function fAlert(str) {
	var sWidth, sHeight;
	sWidth = document.body.offsetWidth; 
	sHeight =document.body.clientHeight;
	var bgObj = document.createElement("div"); 
	bgObj.setAttribute('id', 'bgDiv');
	bgObj.style.position = "absolute";
	bgObj.style.top = "0";
	bgObj.style.background = "#777";
	bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=40,finishOpacity=60";
	bgObj.style.opacity = "0.4";
	bgObj.style.left = "0";
	bgObj.style.width = sWidth + "px";
	bgObj.style.height = sHeight + "px";
	bgObj.style.zIndex = "10000";
	document.body.appendChild(bgObj); 
	var msgObj = document.createElement("div")
	msgObj.setAttribute("id", "msgDiv");
	msgObj.style.position = "absolute";
	msgObj.style.left = "50%";
	msgObj.style.top = "50%";
	msgObj.style.marginLeft = "-225px";
	msgObj.style.marginTop = -175 + document.documentElement.scrollTop + "px";
	msgObj.style.lineHeight = "25px";
	msgObj.style.zIndex = "10001"; 
	document.body.appendChild(msgObj); 
	document.getElementById("msgDiv").innerHTML = str;
}
/**
 * @author: zhengbingx.wu@intel.com
 */
function removeObj() {
	bgObj = document.getElementById("bgDiv");
	msgObj = document.getElementById("msgDiv");
	document.body.removeChild(bgObj);
	document.body.removeChild(msgObj); 
}