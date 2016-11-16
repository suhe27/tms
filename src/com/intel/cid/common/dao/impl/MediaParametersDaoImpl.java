package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.MediaParameters;
import com.intel.cid.common.dao.MediaParametersDao;

public class MediaParametersDaoImpl implements MediaParametersDao {

	private static Logger logger = Logger
			.getLogger(MediaParametersDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;

	
	public int updateMediaParameters(final MediaParameters para) throws Exception
	{
		String sql = "update media_execution_command set hostname=?,password=?,commands=?,description=? where id ='"+para.getId()+"'" ;
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps) throws SQLException {
			
				mapMediaParametersToPs(para, ps);
			}
		});
		return res;
	}
	
	public int addMediaParameters(final MediaParameters para) throws Exception {

		String sql = "insert into media_execution_command(hostname,password,commands,description) values(?,?,?,?)";
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				mapMediaParametersToPs(para, ps);

			}
		});

		return res;
	}

	@SuppressWarnings("unchecked")
	public List<MediaParameters> listAllPaMediaParameterList() throws Exception {

		String sql = "select * from media_execution_command";
		logger.info("sql:" + sql);
		List<MediaParameters> mediaParametersList = userJdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						MediaParameters para = new MediaParameters();
						mapMediaParametersFromRs(rs, para);
						return para;
					}
				});

		return mediaParametersList;
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

	public static void mapMediaParametersFromRs(ResultSet rs,
			MediaParameters para) throws SQLException {
		para.setId(rs.getInt("id"));
		para.setHostName(rs.getString("hostname"));
		para.setPassWord(rs.getString("password"));
		para.setExecutionCommands(rs.getString("commands"));
		para.setDescription(rs.getString("description"));
	}

	public static void mapMediaParametersToPs(final MediaParameters para,
			PreparedStatement ps) throws SQLException {
		ps.setString(1, para.getHostName());
		ps.setString(2, para.getPassWord());
		ps.setString(3, para.getExecutionCommands());
		ps.setString(4, para.getDescription());
	}

}
