var currentTab;
var delay = 500;
var hoverTimer = 0;

function setCookie(c_name, value, expiredays) {
    
    var exdate = new Date(); exdate.setDate(exdate.getDate() + expiredays);
    
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

function getCookie(c_name) {
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

function onTab(myTabNumb) {
    currentTab = myTabNumb;
    hoverTimer = setTimeout("switchTabs()", delay);

}

function offTab() {

    clearTimeout(hoverTimer);
}

function switchTabs() {
    var i = 1;
    while(document.getElementById('tabhdr' + i)){
        var myTabHdr = document.getElementById('tabhdr' + i);
        var myTab = document.getElementById('tabcnt' + i);
        if (i == currentTab) {
            myTabHdr.className = 'active_hdr';
            myTab.className = 'tab_cnt show';
            setCookie('tabNumb', i, 1);
        } else {
            myTabHdr.className = 'inactive_hdr';
            myTab.className = 'tab_cnt hide';
        }
        i++;
    }
}

function hideTabs() {
    var i = 1;
    while(document.getElementById('tabhdr' + i)){
        var myTab = document.getElementById('tabcnt' + i);
        myTab.className = 'tab_cnt hide';
        i++;
    } 
}
