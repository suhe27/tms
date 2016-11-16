<!DOCTYPE html>
<html style="width:100%;height:100%;">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>main</title>
		<script type="text/javascript" src="../js/jquery/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery/other/iframe_tabs/tab.js"></script>
		<script type="text/javascript" src="../js/balloontip.js"></script>
		<link type="text/css" href="../js/jquery/other/iframe_tabs/tab.css" rel="stylesheet" />	
		<style>
			.benma_ui_tab {
				background-image: url("images/i-rightbg2.gif");
			}
		</style>
	</head>
	<body style="margin:0px;padding:0px;width:100%;height:100%;">
		<div id="tab_menu" style="width:100%;"></div>
		<div id="page" style="width:100%;">
		</div>
	</body>
	<script>
		var tab;
		function addTab(id,url,name) {
			if("Requirement Analysis"==name){
				tab.add({
					id : id,
					title : name,
					url : url,
					isClosed :true
				});
			}else{
				tab.add({
					id : id,
					title : name,
					url : url,
					isClosed :true
				});
			}
		}
		$(function(){
			$('#page').css("height",document.documentElement.clientHeight-25);
			tab = new TabView( {
				containerId :'tab_menu',  	//标签容器ID
				pageid:'page',							//页面容器Id
				cid :'tab_po',							//指定tab ID
				position :"top"							//tab位置，只支持top和bottom
			});
			tab.add( {
				id :'todo',
				title :"Home",
				//url : "todo!todo_frame.action",
				isClosed :false
			});
		});
	</script>
</html>