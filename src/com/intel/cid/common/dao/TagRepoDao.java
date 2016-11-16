package com.intel.cid.common.dao;

import com.intel.cid.common.bean.TagRepo;

public interface TagRepoDao {

	TagRepo queryTagRepoById(int id) throws Exception;

	int delTagRepo(TagRepo TagRepo) throws Exception;

	int delTagRepoById(int id) throws Exception;

	int updateTagRepo(TagRepo TagRepo) throws Exception;

	int addTagRepo(TagRepo TagRepo) throws Exception;

}
