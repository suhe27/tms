package com.intel.cid.common.dao.impl.test;

import org.junit.Assert;
import org.junit.Test;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.test.AbstractTestCase;

public class TestTeamDaoImpl extends AbstractTestCase {

    @Test
    public void testAddTeam() throws Exception {
        
        Team team = new Team();
        team.setTeamName("stv");
        int res = teamDaoImpl.addTeam(team);
        Assert.assertEquals(1, res);
    }
}
