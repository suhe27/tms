//<!-- 
var imglist = new Array (
"../img/ithomeon.gif",
"../img/globalnav/ithomeoff.gif",
"../img/globalnav/circuiton.gif",
"../img/globalnav/circuitoff.gif",
"../img/howdoibuttonon.gif",
"../img/howdoibutton.gif",
"../img/goon.gif",
"../img/go.gif"
);
var imgs = new Array();
var count
if (document.images)
for (count=0; count<imglist.length; count++)
{imgs[count]=new Image(); imgs[count].src=imglist[count];}
 //-->