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

import com.intel.cid.common.bean.Phase;
import com.intel.cid.common.bean.TestType;
import com.intel.cid.common.dao.TestTypeDao;
import com.intel.cid.utils.Utils;

public class TestTypeDaoImpl implements TestTypeDao {

    private static Logger logger = Logger.getLogger(TestTypeDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addTestType(final TestType testType) throws Exception {

        String sql = "insert into testtype(testtypename,projectid) values(?,?)";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {

                mapTestTypeToPs(testType, ps);

            }
        });
        logger.info("result:" + result);

        return result;
    }

    public int delTestType(TestType testType) throws Exception {

        return delTestTypeById(testType.getTypeId());
    }

    public int delTestTypeById(int id) throws Exception {
        //String sql = "delete from testtype where testtypeid ='" + id + "'";
        String sql = "UPDATE `testtype` SET `STATUS`='1' WHERE testtypeid='" + id + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("result:" + result);
        return result;
    }

    public List<TestType> queryAllTestTypes() throws Exception {

        //String sql = " select * from testtype";
        String sql = "select a.*, b.PROJECTNAME from testtype a left join project b on a.projectid=b.PROJECTID where (a.status <> 1 or a.status is null)";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<TestType> testTypeList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                TestType testType = new TestType();
                mapTestTypeFromRs(rs, testType);
                return testType;
            }
        });

        logger.info("result:" + testTypeList);
        return testTypeList;
    }

	public int queryTestTypeSize(TestType testType) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != testType.getTypeId()) {
			sql = "select count(*) from testtype where (status <> 1 or status is null) and projectId = '"+ testType.getProjectId() +"' "
					+ "and testTypeName = '"+ testType.getTypeName() + "'  and testTypeId !='"
			+ testType.getTypeId() + "'";
			logger.info("queryByTestTypeNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from testtype where (status <> 1 or status is null) and projectId = '"+ testType.getProjectId() +"'  "
					+ "and testTypeName ='"+ testType.getTypeName() + "'  ";
			logger.info("queryByTestTypeNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}

		logger.info("queryByTestTypeNameSize method result:" + result);
		return size;
	}

    
    public List<TestType> listTestTypeByProjectId(int id) throws Exception {

        //String sql = " select * from testtype";
        String sql = "select a.*, b.PROJECTNAME from testtype a left join project b on a.projectid=b.PROJECTID where (a.status <> 1 or a.status is null) and a.projectid ='" + id + "'";
        logger.info("sql:" + sql);
        @SuppressWarnings("unchecked")
        List<TestType> testTypeList = userJdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int count) throws SQLException {

                TestType testType = new TestType();
                mapTestTypeFromRs(rs, testType);
                return testType;
            }
        });

        logger.info("result:" + testTypeList);
        return testTypeList;
    }
    
    public TestType queryTestTypeById(int id) throws Exception {

        //String sql = "select * from testtype where testtypeid='" + id + "'";
        String sql = "select a.*, b.PROJECTNAME from testtype a left join project b on a.projectid=b.PROJECTID where testtypeid='" + id + "'";
        logger.info("sql:" + sql);
        final TestType testType = new TestType();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
                mapTestTypeFromRs(rs, testType);
            }
        });

        logger.info("result:" + testType);
        return testType;
    }
    
    public TestType queryTestTypeByNameExcelImport(String type , int id) throws Exception {

        String sql = "select * from testtype where (status <> 1 or status is null) and testtypename='" + type + "' and projectid = '"+id+"'";
        logger.info("sql:" + sql);
        final TestType testType = new TestType();
        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
                testType.setTypeId(rs.getInt("testtypeid"));
                testType.setTypeName(rs.getString("testtypename"));
            }
        });

        logger.info("result:" + testType);
        return testType;
    }

    public int updateTestType(final TestType testType) throws Exception {
        String sql = "update testtype set testtypename=?,projectid =? where testtypeid='" + testType.getTypeId() + "'";
        logger.info("sql:" + sql);
        int result = userJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {

                mapTestTypeToPs(testType, ps);
            }
        });

        logger.info("result:" + result);
        return result;
    }

    public static void mapTestTypeFromRs(ResultSet rs, TestType testType) throws SQLException {
        testType.setTypeId(rs.getInt("testtypeid"));
        testType.setTypeName(rs.getString("testtypename"));
        testType.setProjectName(rs.getString("projectName"));
        testType.setProjectId(rs.getInt("projectId"));
    }

    public static void mapTestTypeToPs(final TestType testType, PreparedStatement ps) throws SQLException {
        if (Utils.isNullORWhiteSpace(testType.getTypeName())) {
            ps.setNull(1, Types.NULL);
        } else {
            ps.setString(1, testType.getTypeName());
        }
        if (testType.getProjectId() == 0) {
            ps.setNull(2, Types.NULL);
        } else {
            ps.setInt(2, testType.getProjectId());
        }
    }

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

}
