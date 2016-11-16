package com.intel.cid.common.bean;

import org.apache.log4j.Logger;

public class OS {

    
    private static Logger logger = Logger.getLogger(OS.class);

    private int osId;
    
    private String osName;
    
  

    public int getOsId() {
        return osId;
    }

    public void setOsId(int osId) {
        this.osId = osId;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }
    
    public void logInfo() {
        logger.info("OS [osId=" + osId + ", osName=" + osName +"]");
    }
}
