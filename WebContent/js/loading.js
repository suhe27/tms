function loadBar(fl)
//fl is show/hide flag
{
    var x,y;
    if (self.innerHeight)
    {// all except Explorer
        x = self.innerWidth;
        y = self.innerHeight;
    }
    else
        if (document.documentElement && document.documentElement.clientHeight)
        {// Explorer 6 Strict Mode
            x = document.documentElement.clientWidth;
            y = document.documentElement.clientHeight;
        }
        else
            if (document.body)
            {// other Explorers
                x = document.body.clientWidth;
                y = document.body.clientHeight;
            }

   
     
	
     var el = document.getElementById('loader');
     var bg = document.getElementById('loadbg');
     bg.style.display = (fl == 1)?'block':'none';
    if (null != el)
    {
        var top = 3;
        var left = 10;
        if (left <= 0) left = 10;
        var aw = document.body.clientWidth;
        var ah= document.body.clientHeight;
        el.style.visibility = (fl == 1)?'visible':'hidden';
        el.style.display = (fl == 1)?'block':'none';
       // el.style.left = (aw - el.style.width) / 2+ "px";
       // el.style.top = (aw - el.style.height) / 2+ "px";
        //el.style.top = top + "px";
      //  bg.style.display = (fl == 1)?'block':'none';
        el.style.zIndex = 99;
    }

}
function tmpLoad()
{
    window.setTimeout('loadBar(0)', 500);
}

/*
if(window.addEventListener){
    window.addEventListener("load",tmpLoad,false);
}else{
    window.attachEvent("onload",tmpLoad);
};
*/
function writeloading(msg,width)
{
    document.writeln('<div id="loadbg" style="position: absolute; left:0;top:0;width:100%;height:100%;display:hidden;background:#CCCCCC;  FILTER: Alpha(opacity=50); ></div>');
    
	var aw = document.body.clientWidth;
	var left=(aw - width) / 2;
    document.writeln("<style type=\"text\/css\">");
    document.writeln("<!--");
    document.writeln("-->");
    document.writeln("<\/style>")
     
    document.write('<div id="loaderbar" style="position: relative;top:45%;width:'+width+'px;background:url(../images/loading.gif) repeat-x">')
    document.write(' <br/><b >'+msg+'</b>')
    document.write('</div>')
    
   
    document.write('<script>loadBar(0);</script>');
}