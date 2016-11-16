package com.intel.cid.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.intel.cid.common.bean.OS;
import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.dao.OSDao;
import com.intel.cid.utils.Utils;

public class OSDaoImpl implements OSDao {

    private static Logger logger = Logger.getLogger(OSDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addOS(final OS os) throws Exception {

        String sql = "insert into os(osname) values(?)";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

                mapOsToPs(os, ps);

            }
        });
        logger.info("result:" + result);

        return result;
    }

    public int delOS(OS os) throws Exception {

        return delOSById(os.getOsId());
    }

    public int delOSById(int id) throws Exception {
        //String sql = "delete from os where osid ='" + id + "'";
        String sql = "UPDATE `os` SET `STATUS`='1' WHERE osid='" + id + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }

    public List<OS> queryAllOSs() throws Exception {

        //String sql = " select * from os";
        String sql = " select * from os  where (status <> 1 or status is null)";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<OS> osList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                OS os = new OS();
                mapOsFromRs(rs, os);

                return os;
            }
        });

        logger.info("result:" + osList);
        return osList;
    }

    public OS queryOSById(int id) throws Exception {

        String sql = "select * from os where osid='" + id + "'";
        logger.info("sql:" + sql);
        final OS os = new OS();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
               mapOsFromRs(rs, os);

            }
        });

        logger.info("result:" + os);
        return os;
    }
    
    public OS queryOSByNameExcelImport(String name) throws Exception {

        String sql = "select * from os where (status <> 1 or status is null) and osName='" + name + "'";
        logger.info("queryOSByName sql:" + sql);
        final OS os = new OS();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
               mapOsFromRs(rs, os);

            }
        });

        logger.info("result:" + os);
        return os;
    }

    public int updateOS(final OS os) throws Exception {
        String sql = "update os set osname=? where osid='" + os.getOsId() + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, os.getOsName());

            }
        });

        logger.info("result:" + result);
        return result;
    }

	public int queryOSSize(OS os) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != os.getOsId()) {
			sql = "select count(*) from os where (status <> 1 or status is null) and osName = '"+ os.getOsName() + "'  and osId !='"
			+ os.getOsId() + "'";
			logger.info("queryByOsNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from os where (status <> 1 or status is null) and osName ='"+ os.getOsName() + "'  ";
			logger.info("queryByOsNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByOsNameSize method result:" + result);
		return size;
	}

    
    public static void mapOsToPs(final OS os, PreparedStatement ps) throws SQLException {

        if (Utils.isNullORWhiteSpace(os.getOsName())) {

            ps.setNull(1, Types.NULL);

        } else {
            ps.setString(1, os.getOsName());
        }

    }

    public static void mapOsFromRs(ResultSet rs, OS os) throws SQLException {
        os.setOsId(rs.getInt("osid"));
        os.setOsName(rs.getString("osname"));
    }

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

}
