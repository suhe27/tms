package com.intel.cid.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.dao.impl.OSDaoImpl;

public class OSService {

	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(OSService.class);

    private OSDaoImpl oSDaoImpl;

    public List<OS> listAllOSs() throws Exception {

        List<OS> oSList = oSDaoImpl.queryAllOSs();

        return oSList;

    }

    public OS queryOSById(int id) throws Exception {

        return oSDaoImpl.queryOSById(id);

    }

    public void delOSById(int id) throws Exception {

        oSDaoImpl.delOSById(id);
    }

    public void addOS(OS oS) throws Exception {
        oSDaoImpl.addOS(oS);

    }

    
    public void updateOS(OS os) throws Exception {
        oSDaoImpl.updateOS(os);
    }
    
	public int queryOSSize(OS os) throws Exception {
		   return oSDaoImpl.queryOSSize(os);
	}
    
    public OSDaoImpl getOSDaoImpl() {
        return oSDaoImpl;
    }

    public void setOSDaoImpl(OSDaoImpl osDaoImpl) {
        this.oSDaoImpl = osDaoImpl;
    }
}
