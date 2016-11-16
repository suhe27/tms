package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.intel.cid.common.bean.TagRepo;
import com.intel.cid.common.dao.TagRepoDao;
import com.intel.cid.utils.Utils;

public class TagRepoDaoImpl implements TagRepoDao {

	private static Logger logger = Logger.getLogger(TagRepoDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public int addTagRepo(final TagRepo TagRepo) throws Exception {

		String sql = "insert into tagrepo(tag,executionid,testplanid,subplanid,tagflags,createdate) values(?,?,?,?,?,?)";
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				mapTagRepoToPs(TagRepo, ps);

			}
		});

		return res;
	}

	public int delTagRepo(TagRepo TagRepo) throws Exception {

		return delTagRepoById(TagRepo.getId());
	}

	public int delTagRepoById(int id) throws Exception {
		String sql = "delete from tagrepo where id=" + id;

		return userJdbcTemplate.update(sql);
	}

	public TagRepo queryTagRepoById(int id) throws Exception {

		String sql = "select * from tagrepo where id=" + id;
		logger.info("sql:" + sql);
		final TagRepo repo = new TagRepo();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTagRepoFromRs(repo, rs);

			}
		});

		return repo;
	}

	public int updateTagRepo(final TagRepo TagRepo) throws Exception {

		String sql = "update tagrepo set tag = ?,executionid = ?,testplanid = ?,subplanid = ?,tagflags = ?,createdate = ? where id ="
				+ TagRepo.getId();
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapTagRepoToPs(TagRepo, ps);
			}
		});

		return res;
	}

	public TagRepo queryTagRepoByTag(String tag) throws Exception {
		
		
		String sql ="select * from tagrepo where tag = "+tag;
		
		logger.info("sql:" + sql);
		final TagRepo repo = new TagRepo();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				mapTagRepoFromRs(repo, rs);

			}
		});

		return repo;
	}

	public void mapTagRepoToPs(final TagRepo TagRepo, PreparedStatement ps)
			throws SQLException {
		if (Utils.isNullORWhiteSpace(TagRepo.getTag())) {
			ps.setString(1, null);
		} else {
			ps.setString(1, TagRepo.getTag());
		}

		if (TagRepo.getExecutionId() == 0) {
			ps.setNull(2, Types.NULL);
		} else {
			ps.setInt(2, TagRepo.getExecutionId());
		}

		if (TagRepo.getTestPlanId() == 0) {
			ps.setNull(3, Types.NULL);
		} else {
			ps.setInt(3, TagRepo.getTestPlanId());
		}
		if (TagRepo.getSubPlanId() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, TagRepo.getSubPlanId());
		}
		if (Utils.isNullORWhiteSpace(TagRepo.getTagFlags())) {
			ps.setString(5, null);
		} else {
			ps.setString(5, TagRepo.getTagFlags());
		}

		if (Utils.isNullORWhiteSpace(TagRepo.getCreateDate())) {
			ps.setString(6, null);
		} else {
			ps.setString(6, TagRepo.getCreateDate());
		}

	}

	public void mapTagRepoFromRs(final TagRepo repo, ResultSet rs)
			throws SQLException {
		repo.setId(rs.getInt("id"));
		repo.setExecutionId(rs.getInt("executionid"));
		repo.setTestPlanId(rs.getInt("testplanid"));
		repo.setSubPlanId(rs.getInt("subplanid"));
		repo.setTag(rs.getString("tag"));
		repo.setTagFlags(rs.getString("tagflags"));
		repo.setCreateDate(rs.getString("createdate"));
	}

}
