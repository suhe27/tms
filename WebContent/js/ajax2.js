MyJqueryAjax = function (v_url, params, func, dataType) {
	this.url = v_url;
	//this.params = params;
	this.func = func;
	this.request = function () {
		var v_response;
		$.ajax({async:false, url:v_url, cache:false, type:"GET", dataType:dataType == null ? "text" : dataType, error:function (XMLHttpRequest, textStatus, errorThrown) {
			alert("MyJqueryAjax Request Error!");
		}, success:(func !== null && func != undefined) ? func : function (req) {
			v_response = req;
		}});
		return v_response;
	};
};