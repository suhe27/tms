/* *************************************************** */
/* This File is for future menu capabilities. Do not   */
/* remove or change                                    */
/* *************************************************** */

function include_script(script) {
	document.write('<script type="text/javascript" src="'+script+'"></script>');
}

/* IE has not 'hasAttribute' method, so make one */		
function hasAttribute(element, attribute) { 
	element = $(element); 
	var attributeNode = element.getAttributeNode(attribute); 
	return (attributeNode && attributeNode.specified);
}
  
var scripts = document.getElementsByTagName("script");
var script;
for (var i=0; i < scripts.length; i++) {
	var s = scripts[i];
	if (s.src && s.src.match(/AppMenuScript\.js$/))
		script = s;
}
var path = script.src.replace(/AppMenuScript\.js$/,'');

function toggle_trigger(is_active, trigger) {
    if (is_active) {
        HideFormFields();
    } else {
        ShowFormFields();
    }
}

function toggle_org_panel(is_active, trigger, panel) {
    if (is_active) {
        
        panel.style.display = 'block';
	    if (Element.hasClassName(trigger, 'PopupMenu'))
	    {
	        Position.clone(trigger, panel, {
			    setHeight: false,
			    setWidth: false,
			    offsetLeft: trigger.offsetWidth
		    });
	    }
	    else
	    {
	        Position.clone(trigger, panel, {
			    setHeight: false,
			    setWidth: false,
			    offsetTop: trigger.offsetHeight
		    });
	     }
	}
	else {
	    panel.style.display = 'none';
	}
}

function create_hNav(){

    var siteNavigation = new IntelMenu.TD.ProtoMenu(null, toggle_trigger, toggle_org_panel);

	var links = $A(document.getElementsByTagName("li"));
    z_index = links.length;
	links.each(function(link)
	{
		if (Element.hasClassName(link, 'PopupMenu') || Element.hasClassName(link, 'dropdownmenu')) 
		{
		    for (var MenuLoop = 0; MenuLoop < link.childNodes.length; MenuLoop++) 
            {
                if (link.childNodes[MenuLoop].nodeName == 'UL')
                {
                    siteNavigation.add(link, link.childNodes[MenuLoop]); 
                    link.style.zIndex = z_index * 10;
                    z_index--;
                    break;
                }
            }
        }
    });
}

function create_vNav(){

    var siteNavigation = new IntelMenu.TD.ProtoMenu(null, toggle_trigger, null);

	var links = $A(document.getElementsByTagName("li"));
    z_index = links.length;
	links.each(function(link)
	{
		if (Element.hasClassName(link, 'PopupMenu') || Element.hasClassName(link, 'dropdownmenu')) 
		{
		    for (var MenuLoop = 0; MenuLoop < link.childNodes.length; MenuLoop++) 
            {
                if (link.childNodes[MenuLoop].nodeName == 'UL')
                {
                    siteNavigation.add(link, link.childNodes[MenuLoop]); 
                    link.style.zIndex = z_index * 10;
                    z_index--;
                    break;
                }
            }
        }
    });
}



function HideFormFields() {
    if (!!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1)) {
        if (GetIEVersion() <= 6) {
            var CurrentDoc = window.document;
            for (var FormLoop = 0; FormLoop < CurrentDoc.forms.length; FormLoop++) {
                for (var intLoop = 0; intLoop < CurrentDoc.forms[FormLoop].elements.length; intLoop++) {
                    if (CurrentDoc.forms[FormLoop].elements[intLoop].type == "select-one") {
                        CurrentDoc.forms[FormLoop].elements[intLoop].style.visibility = "Hidden";
                    }
                }
            }
        }
    }
}
function ShowFormFields() {
    if (!!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1)) {
        if (GetIEVersion() <= 6) {
            var CurrentDoc = window.document;
            for (var FormLoop = 0; FormLoop < CurrentDoc.forms.length; FormLoop++) {
                for (var intLoop = 0; intLoop < CurrentDoc.forms[FormLoop].elements.length; intLoop++) {
                    if (CurrentDoc.forms[FormLoop].elements[intLoop].type == "select-one") {
                        CurrentDoc.forms[FormLoop].elements[intLoop].style.visibility = "Visible";
                    }
                }
            }
        }
    }
}
function GetIEVersion() {
    var App = navigator.userAgent.toLowerCase();
    var AppArray;
    var temp = "";
    var Brwser;

    AppArray = App.split(";");
    temp = AppArray[1];

    if (temp.indexOf("msie") > 0) {
        temp = temp.replace("msie ", "");
    }
    else {
        temp = "0";
    }
    return parseFloat(temp);
}	

include_script(path + 'prototype-1.6.1_rc2.js');
include_script(path + 'protomenu.js');