package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import com.intel.cid.common.bean.CaseMap;
import com.intel.cid.common.bean.TestCase;
import com.intel.cid.common.dao.CaseMapDao;

public class CaseMapDaoImpl implements CaseMapDao {

    private static Logger logger = Logger.getLogger(CaseMapDaoImpl.class);

    private JdbcTemplate userJdbcTemplate;

    public int addTestCaseIntoCaseMap(final CaseMap caseMap) throws Exception {

        final String sql = "insert into casemap(oldtestcaseid,newtestcaseid,newversion) values(?,?,?)";
        logger.info("addTestCaseIntoCaseMap method sql:" + sql);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        userJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                mapCaseMapToPs(caseMap, ps);
                return ps;
            }
        }, keyHolder);

        int result = keyHolder.getKey().intValue();
        logger.info("addTestCaseIntoCaseMap result:" + result);
        return result;
    }

    public int delTestCaseByOldIdAndVersion(int testCaseId, String version) throws Exception {

        String sql = "delete from casemap where oldtestcaseid ='" + testCaseId + "' and newversion='" + version + "'";
        logger.info("delTestCaseByOldIdAndVersion method sql:" + sql);
        int result = userJdbcTemplate.update(sql);
        logger.info("delTestCaseByOldIdAndVersion method result:" + result);
        return 0;
    }

    public int delTestCaseFromCaseMap(CaseMap caseMap) throws Exception {

        return delTestCaseByOldIdAndVersion(caseMap.getOldTestcaseId(), caseMap.getNewVersion());
    }

    public TestCase queryTestCaseByOldIdAndVersion(int testCaseId, String version) throws Exception {

        String sql = "select tc.* from casemap as map left join testcase as tc on map.newtestcaseid = tc.testcaseid"
                + "where map.oldtestcaseid ='" + testCaseId + "' and map.newversion='" + version + "'";
        logger.info("queryTestCaseByOldIdAndVersion method sql:" + sql);

        final TestCase testCase = new TestCase();

        userJdbcTemplate.query(sql, new RowCallbackHandler() {

            public void processRow(ResultSet rs) throws SQLException {
                TestCaseDaoImpl.mapTestCaseFromRs(rs, testCase);
            }
        });

        logger.info("queryTestCaseByOldIdAndVersion method result:" + testCase.logInfo());

        return testCase;
    }

    public static void mapCaseMapToPs(final CaseMap caseMap, PreparedStatement ps) throws SQLException {

        if (caseMap.getOldTestcaseId() == 0) {

            ps.setNull(1, Types.NULL);
        } else {
            ps.setInt(1, caseMap.getOldTestcaseId());
        }
        if (caseMap.getNewTestCaseId() ==0) {

            ps.setNull(2, Types.NULL);
        } else {
            ps.setInt(2, caseMap.getNewTestCaseId());
        }
      
        ps.setString(3, caseMap.getNewVersion());
    }

    public JdbcTemplate getUserJdbcTemplate() {
        return userJdbcTemplate;
    }

    public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

}
