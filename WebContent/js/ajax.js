

MyAjax= function(url,params,func){
  
   this.url=url;
   this.params=params;
   this.func=func;
   this.responseText='';
   this.request=function(){
       new Ajax.Request( 
         url,
                     {
                       method: 'post', 
                       parameters:params, 
                       onSuccess:(func!=null&& func!=undefined)?func:function(req)
                       {
                           responseText= req.responseText;  
                       },
                       onFailure:function() {alert('MyAjax Request Error!');},
                       asynchronous:false
                     }
          );
      return responseText; 
   };
}