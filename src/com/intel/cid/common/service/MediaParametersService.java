package com.intel.cid.common.service;

import java.util.List;

import com.intel.cid.common.bean.MediaParameters;
import com.intel.cid.common.dao.impl.MediaParametersDaoImpl;

public class MediaParametersService {

	private MediaParametersDaoImpl mediaParametersDaoImpl;

	public int updateMediaParameters(final MediaParameters para)
			throws Exception {
		return mediaParametersDaoImpl.updateMediaParameters(para);
	}

	public int addMediaParameters(final MediaParameters para) throws Exception {
		
		return mediaParametersDaoImpl.addMediaParameters(para);
	}

	public List<MediaParameters> listAllPaMediaParameterList() throws Exception {
		
		return mediaParametersDaoImpl.listAllPaMediaParameterList()	;
	}

	public MediaParametersDaoImpl getMediaParametersDaoImpl() {
		return mediaParametersDaoImpl;
	}

	public void setMediaParametersDaoImpl(
			MediaParametersDaoImpl mediaParametersDaoImpl) {
		this.mediaParametersDaoImpl = mediaParametersDaoImpl;
	}

}
