package com.intel.cid.common.dao.impl.test;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intel.cid.common.bean.PlanSuiteMap;
import com.intel.cid.common.test.AbstractTestCase;

public class TestPlanSuiteMapDaoImpl extends AbstractTestCase {

	@Transactional(propagation=Propagation.REQUIRED)
	@Test
	 public void testaddTestSuiteBatchByArrayList() throws Exception
	{
		ArrayList<PlanSuiteMap> list = new ArrayList<PlanSuiteMap>();
		PlanSuiteMap planSuiteMap1 = new PlanSuiteMap();
		PlanSuiteMap planSuiteMap2 = new PlanSuiteMap();
		PlanSuiteMap planSuiteMap3 = new PlanSuiteMap();
		
		planSuiteMap1.setSubPlanId(9);
		planSuiteMap1.setSpace("user");
		planSuiteMap2.setSubPlanId(10);
		planSuiteMap2.setSpace("user");
		planSuiteMap3.setSubPlanId(11);
		list.add(planSuiteMap1);
		list.add(planSuiteMap2);
		list.add(planSuiteMap3);
		
	//	subTestPlanService.addTestSuiteBatchByArrayList(list);
	}
	
}
