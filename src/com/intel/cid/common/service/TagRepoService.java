package com.intel.cid.common.service;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.TagRepo;
import com.intel.cid.common.dao.impl.TagRepoDaoImpl;

public class TagRepoService {

	private static Logger logger = Logger.getLogger(TagRepoService.class);

	private TagRepoDaoImpl tagRepoDaoImpl;

	public TagRepoDaoImpl getTagRepoDaoImpl() {
		return tagRepoDaoImpl;
	}

	public void setTagRepoDaoImpl(TagRepoDaoImpl tagRepoDaoImpl) {
		this.tagRepoDaoImpl = tagRepoDaoImpl;
	}

	TagRepo queryTagRepoById(int id) throws Exception {

		return tagRepoDaoImpl.queryTagRepoById(id);
	}

	TagRepo queryTagRepoByTag(String tag) throws Exception {

		return tagRepoDaoImpl.queryTagRepoByTag(tag);
	}

	int delTagRepo(TagRepo TagRepo) throws Exception {
		
		return tagRepoDaoImpl.delTagRepo(TagRepo);
	}

	int delTagRepoById(int id) throws Exception {
		return tagRepoDaoImpl.delTagRepoById(id);
	}

	int updateTagRepo(TagRepo TagRepo) throws Exception {
		
		return tagRepoDaoImpl.updateTagRepo(TagRepo);
	}

	int addTagRepo(TagRepo TagRepo) throws Exception {
		
		return tagRepoDaoImpl.addTagRepo(TagRepo);
	}

}
