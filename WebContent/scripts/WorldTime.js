 

var dt = new Date();
var possDt  = 0;
var tdChecked = false;
var tz     = -6;
var tzMin  = 0;
var id     = null;
var cYear = dt.getFullYear()
var DST = 0
var EDST = 1
var cities;

var timeViewer = document.getElementById("timeshow");
var locDropDown = document.getElementById("selectplace");

//Define start and end dates of daylight savings times
var dstStart = new Date(cYear,2,1); //1st of March
var edstStart = new Date(cYear,2,31); //31st of March for Europe
var dstEnd = new Date(cYear,10,1); //1st of November
var edstEnd = new Date(cYear,9,31); //31st of October for Europ
	//
	//Find the appropriate Sundays
	var found1st = false;
	for(i = 1; i < 15; i++) //Find the 2nd Sunday of March
	{
		dstStart.setDate(i);
		if (dstStart.getDay() == 0) //Sunday?
		{
			if (found1st)
			{
				break;
			}
			else
			{
				found1st = true;
			}
		}
	}

	if (edstStart.getDay() != 0) //Find the last Sunday of April
	{
		i = 30
		while (i > 22)
			{
				edstStart.setDate(i);
			if (edstStart.getDay() == 0) //Sunday?
				break;
			i--;
			}
	}

	if (dstEnd.getDay() != 0)	//Find the first Sunday of November
	{
		i = 2
		while (i < 8)
			{
				dstEnd.setDate(i);
			if (dstEnd.getDay() == 0) //Sunday?
				break;
			i++;
			}
	}

	if (edstEnd.getDay() != 0)	// Last Sunday of October
	{
		i = 30
		while (i > 22)
			{
				edstEnd.setDate(i);
			if (edstEnd.getDay() == 0) //Sunday?
				break;
			i--;
			}
	}

//alert(dstStart + " " + dstEnd);
	timeZone = new Array();
	timeZone["ZA"] = 13;
timeZone["Z"] = 0;
timeZone["A"] = 1;
timeZone["B"] = 2;
timeZone["C"] = 3;
timeZone["Cc"] = 3.5;
timeZone["D"] = 4;
timeZone["Dd"] = 4.5;
timeZone["E"] = 5;
timeZone["Ee"] = 5.5;
timeZone["F"] = 6;
timeZone["Ff"] = 6.5;
timeZone["G"] = 7;
timeZone["H"] = 8;
timeZone["I"] = 9;
timeZone["Ii"] = 9.5;
timeZone["K"] = 10;
timeZone["Kk"] = 10.5;
timeZone["L"] = 11;
timeZone["Ll"] = 11.5;
timeZone["M"] = 12;
timeZone["N"] = -1;
timeZone["O"] = -2;
timeZone["P"] = -3;
timeZone["Pp"] = -3.5;
timeZone["Q"] = -4;
timeZone["R"] = -5;
timeZone["S"] = -6;
timeZone["T"] = -7;
timeZone["U"] = -8;
timeZone["V"] = -9;
timeZone["Vv"] = -9.5;
timeZone["W"] = -10;
timeZone["X"] = -11;
timeZone["Y"] = -12;
//To add an entry, look up time zone and determine if location supports DST or EDST.
cities = new Object();


//Return true if date passed is within Daylight Saving Time (DST)
function isDST(dDate) {
	//alert("OK? " + dDate + " " + dstStart);
	if (dDate.valueOf() > dstStart.valueOf() && dDate.valueOf() < dstEnd.valueOf())
		return true;
	else
		return false;
}
//Return true if date passed is within European Daylight Saving Time (EDST)
function isEDST(dDate)
{
	if (dDate.valueOf() > edstStart.valueOf() && dDate.valueOf() < edstEnd.valueOf())
		return true;
	else
		return false;
}

function IfZero(num)
{
	return ((num <= 9) ? ("0" + num.toString()) : num.toString());
}

function check24(hour)
{
	return (hour >= 24) ? hour - 24 : hour;
}

function setTimeClick() {
   
    
	var city;
	var dt = new Date();

	if (isDST(dt))
		DST = 1
	else
		DST = 0
	if (isEDST(dt))
		EDST = 1
	else
		EDST = 0
	var def = dt.getTimezoneOffset()/60;
	var gmt = (dt.getHours() + def);


	var timeViewer = document.getElementById("timeshow");
	var locDropDown = document.getElementById("selectplace");

	city = locDropDown.options[locDropDown.selectedIndex].value;

	setTimeCookie("myLoc", city, 1000)
	
	var cv = locDropDown.options[locDropDown.selectedIndex].innerText;
	//alert("city: " + city);
	//alert("cv: " + cv);
	//alert("eval: " + eval(cities[city]));
    var cHour = IfZero(eval(cities[city]))
    var cMinute = IfZero(dt.getMinutes())
    var cSecond = IfZero(dt.getSeconds())
    cHour = cHour.toString();  //make sure we have a two character hour string.
    // Adjust hour/minute for .5 hr offsets.
    if (cHour.indexOf(".") > -1)
		{

		cHour = cHour.substr(0,2); // Just the hour portion.
		if (parseInt(cMinute) > 29) //greater than half hour adds an hour.
			{
			cMinute = (parseInt(cMinute) + 30) - 60;
			cMinute = IfZero(cMinute);
			cHour = parseInt(cHour) + 1;
			cHour = IfZero(cHour);
			}
		else //Simply add 30 minutes to minutes.
			{
			cMinute = (parseInt(cMinute) + 30);
			cMinute = IfZero(cMinute);
			}
		}
		//Put the results in the control
		timeViewer.value = cHour + ":" + cMinute + ":" + cSecond;

}

function putShortcut()
{
	var p = "putshortcut.asp?id=home";
	var x = window.open(p,"","width=640,height=550,left=30,top=30,resizable,scrollbars");
}
function FillCities() {

    var timeViewer = document.getElementById("timeshow");
    var locDropDown = document.getElementById("selectplace");
    var selectedLoc = getTimeCookie("myLoc");
    
	//alert("FillCities");
	var cnt = 0;
	var j;
	var i = null;
	for(i in cities)
	{
		cnt = cnt + 1
	}
	locDropDown.length = cnt;
	cnt = 0;
	i = null;
	for(i in cities) {
	    
		if (i == "New_Hampshire")
			j = "New Hampshire";
		else if (i == "New_Jersey")
			j = "New Jersey";
		else if (i == "New_Zealand")
			j = "New Zealand";
		else if (i == "New_Mexico")
			j = "New Mexico";
		else if (i == "New_York")
			j = "New York";
		else if (i == "North_Carolina")
			j = "North Carolina";
		else if (i == "Hong_Kong")
			j = "Hong Kong";
		else if (i == "Puerto_Rico")
			j = "Puerto Rico";
		else if (i == "Vancouver_BC")
			j = "Vancouver BC";
		else
			j = i;

        locDropDown.options[cnt].innerHTML = j;
        locDropDown.options[cnt].value = i;


        if (selectedLoc) {
            if (i == selectedLoc) {
                locDropDown.options[cnt].selected = true;
            }
        } else {
            if (i == "California") {
                locDropDown.options[cnt].selected = true;
            }
        }
		cnt = cnt + 1
    }
}


function setTimeCookie(c_name, value, expiredays) {
    var exdate = new Date(); exdate.setDate(exdate.getDate() + expiredays);

    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

function getTimeCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}


