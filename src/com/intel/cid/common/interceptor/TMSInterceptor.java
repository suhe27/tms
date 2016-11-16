package com.intel.cid.common.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.intel.cid.common.action.BaseAction;
import com.intel.cid.common.action.UserAction;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.service.UserService;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class TMSInterceptor implements Interceptor {

    private static Logger logger = Logger.getLogger(TMSInterceptor.class);

    private static final long serialVersionUID = -5019108024072616843L;

    public static final java.lang.String LOGIN = "login";
    
    public void destroy() {

        logger.info("TMSInterceptor destorying!");
    }

    public void init() {
        logger.info("TMSInterceptor initing");
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof UserAction) {
             return invocation.invoke();
        } else {
            
        	  Map session = invocation.getInvocationContext().getSession();

              Object login = session.get("user");
              
              if (login == null) {
				
            	  /*HttpServletRequest req = ServletActionContext.getRequest();
                 
                  String path = req.getRequestURI().substring(10);
                
                  String queryString = req.getQueryString();
                  String realPath = path.replace(".action", "");
                  if (!Utils.isNullORWhiteSpace(queryString)) {
                	  realPath = path + "?" + queryString;
                      
                  }                                 
                  session.put("prePage", realPath);
                */
            	  return LOGIN;
			  }else {
				  //Get all the input parameters and trim space in the begin and end
				  Map map=invocation.getInvocationContext().getParameters();
				  Set keys = map.keySet();
			      Iterator iters = keys.iterator();
			      while(iters.hasNext())
			      {
			    	  Object key = iters.next();
			    	  Object value = map.get(key);
			    	  map.put(key, transfer((String[])value));
			      } 
			      return invocation.invoke();
			}
        }
    }

/**
 * Iterate all the string in array and do trim operation.    
 * @param params
 * @return
 */
	private String[] transfer(String[] params){
		for(int i=0;i<params.length;i++){
			if(StringUtils.isEmpty(params[i])) 
				continue;
			params[i]=params[i].trim();
		}
		return params;
	}

}
