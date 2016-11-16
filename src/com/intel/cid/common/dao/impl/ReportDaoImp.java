package com.intel.cid.common.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.intel.cid.common.bean.ReleaseCycle;
import com.intel.cid.common.bean.TestCycle;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.dao.ReportDao;

public class ReportDaoImp implements ReportDao{
	
	private Logger log = Logger.getLogger(this.getClass());
	private JdbcTemplate db;

	
	public JdbcTemplate getDb() {
		return db;
	}

	public void setDb(JdbcTemplate db) {
		this.db = db;
	}
	
	public List getProjectList(String username){
		String sql = "select project from testplan group by project order by project asc";
		if(null!=username){
			sql = "select c.project from testplan c where c.teamid in " +
					"(select teamid from user_team b where b.userid = " +
					"(select a.userid from user a where a.username = '"+username+"')) group by project  " +
							"order by project asc";
		}
		return db.queryForList(sql);
	}
	
	public List getLastReleaseCycle(){
		String sql = "select releasecycle from testplan t where t.project = (select project from testplan group by project order by project desc limit 1)";
		return db.queryForList(sql);
	}

	@Override
	public List getReleaseCycle(String project,String cycleid) {
		String sqlTem = "select releasecycle,totalcases,pass,fail,notRun,block,passrate from testplan where project = '"+project+"' and testplanid in ("+cycleid+")";
		List list = db.queryForList(sqlTem);
		return list;
	}

	@Override
	public List getWeekList(String project,String username) {
		String sql= "";
		if(null != project && "init".equals(project)){
			sql = "select testplanid,releasecycle from testplan d where d.project = " +
					"(select c.project from testplan c where c.teamid in " +
					"(select teamid from user_team b where b.userid = " +
					"(select a.userid from user a where a.username = '"+username+"')) " +
					"group by project order by project desc limit 1 )";
		}else{
			sql = "select testplanid,releasecycle from testplan where project = '"+project+"'";
		}
		return db.queryForList(sql);
	}

	@Override
	public List getTestRelease(String project, String releaseCycle) {
		String sqlNum = "select sum(totalcases) from subtestplan where project = '"+project+"' and releasecycle = '"+releaseCycle+"'";
		int caseNumber = db.queryForInt(sqlNum);
		String sql = "select * from subtestplan where project = '"+project+"' and releasecycle = '"+releaseCycle+"'";
		List list =  db.queryForList(sql);
		List cycleList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			LinkedHashMap<?, ?> lhm = (LinkedHashMap<?, ?>)list.get(i);
			TestCycle tc = new TestCycle();
			String releaseCyc = lhm.get("RELEASECYCLE")==null ? "" :lhm.get("RELEASECYCLE").toString();
			String subplanName = lhm.get("SUBPLANNAME")==null ? "" :lhm.get("SUBPLANNAME").toString();
			int totalCase = Integer.parseInt(lhm.get("TOTALCASES")==null ? "0" : lhm.get("TOTALCASES").toString());
			float passRate = Float.parseFloat(lhm.get("PASSRATE") == null ? "0" :lhm.get("PASSRATE").toString());
			int pass = Integer.parseInt(lhm.get("PASS") == null ? "0" : lhm.get("PASS").toString());
			int fail = Integer.parseInt(lhm.get("FAIL") == null ?"0" : lhm.get("FAIL").toString());
			int notRun = Integer.parseInt(lhm.get("NOTRUN") == null ? "0" : lhm.get("NOTRUN").toString());
			int block = Integer.parseInt(lhm.get("BLOCK") == null ? "0" : lhm.get("BLOCK").toString());
			tc.setReleaseCycle(releaseCyc);
			tc.setSubPlanName(subplanName);
			tc.setCaseNumber(caseNumber);
			tc.setTotalCase(totalCase);
			tc.setPass(pass);
			tc.setFail(fail);
			tc.setNotRun(notRun);
			tc.setBlock(block);
			tc.setPassRate(passRate);
			cycleList.add(tc);
		}	
		return cycleList;
	}
	
	
	public List getSubplanForName(String project,String subplanname){
		String sql = "select * from testplan where project = '"+project+"' and releasecycle = '"+subplanname+"'";
		return db.queryForList(sql);
	}
	public List getSubplanInfo(String project,String subplanname){
		//String sql = "select subplanname,pass,fail,notrun,passrate,totalcases,modifydate from subtestplan where project = '"+project+"' and releasecycle = '"+subplanname+"'";
		String sql = "select subplanname,totalcases,pass,fail,notRun,block,passrate from subtestplan where project = '"+project+"' and releasecycle = '"+subplanname+"'";
		return db.queryForList(sql);
	}
	
	public int getSubplanTotalCases(String project,String subplanname){
		String sql = "select sum(totalcases) from testplan t where t.project = '"+project+"' and t.releasecycle = '"+subplanname+"'";
		return db.queryForInt(sql);
	}
	
	public List getSubplanForColumn(String project,String subplanname,String column){
		String sql = "select "+column+" from subtestplan where project = '"+project+"' and releasecycle = '"+subplanname+"'";
		return db.queryForList(sql);
	}
	
	public List getReleaseListForProject(int projectid){
		String sql = "select releasecycle from testexecution where projectid = '"+projectid+"'";
		return db.queryForList(sql);
	}
/*	public List getReleaseListForPhase(int phaseId){
		String sql = "select phaseId,phaseName from phase where phaseId = '"+phaseId+"'";
		return db.queryForList(sql);
	}*/
	
	
	public List getListTestExecution(int projectid){
		   String sql = "select testPlanId, executionName  from testexecution t where projectid= '"+projectid+"'";
		return db.queryForList(sql);
	}
	
	public List getlistTestPlans(int projectid){
		String sql = "select testPlanId,planName from  testplan where (status <> 1 or status is null) and projectid= '"+projectid+"'";
		return db.queryForList(sql);
	}
	
	public List getlistSubTestPlans(int id){
		String sql = "select a.SUBPLANID,a.SUBPLANNAME from  subtestplan a left join planmap b on a.SUBPLANID=b.SUBPLANID where "
				+ "(a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) and b.TESTPLANID= '"+id+"'";
		return db.queryForList(sql);
	}
	
	public List getlistTestenv(int projectid){
		String sql = "select id ,envname, `desc`  from  testenv where (status <> 1 or status is null) and projectid= '"+projectid+"'";
		return db.queryForList(sql);
	}
	public List getListPlatforms(int projectid){
		String sql = "select p.platformid,p.platformname from platform p where (p.status <> 1 or p.status is null) and p.projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List getListTargets(int projectid){
		String sql = "select p.TARGETID,p.TARGETNAME from target p where (p.status <> 1 or p.status is null) and p.projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List getListOSByProjectId(int projectid){
		//String sql = "select p.osid,p.osname from executionos p, project a where (p.status <> 1 or p.status is null) and (a.status <> 1 or a.status is null) and a.teamid = p.teamid and  a.projectid= '"+projectid+"'";
		String sql = "select p.osid,p.osname from executionos p where (p.status <> 1 or p.status is null) and  p.projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	public List getTestResult(String project,String subplanname,int resultType){
		String sql = "select a.resultid,a.testcaseid,a.comments,c.testcasename,a.log,a.bugid,b.subplanname from testresult a,subtestplan b,testcase c " +
				"where a.subplanid = b.subplanid and a.testcaseid = c.testcaseid " +
				"and b.releasecycle = '"+subplanname+"' and b.project = '"+project+"' and a.resulttypeid = "+resultType+"";
		return db.queryForList(sql);
	}
	public int getTotalCases(String project,String releaseCycle){
		String sql = "select max(totalcases) as totalcases from (select t.totalcases from testplan t where t.project = '"+project+"' and t.releasecycle = '"+releaseCycle+"' " +
				"union all select a.totalcases from testplanstatus a where a.project = '"+project+"' and a.releasecycle = '"+releaseCycle+"') b";
		
		return db.queryForInt(sql);
	}

	public List getPhaseByProjectIdJSON(int projectid){
		String sql = "select phaseid ,phasename from executionphase where (status <> 1 or status is null) and projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List getBuildTypeByProjectIdJSON(int projectid){
		String sql = "select buildid ,buildtype from build where (status <> 1 or status is null) and projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List getTestPlanDurationJSON(int id){
		String sql = "select startdate,enddate from testplan where TESTPLANID = '"+id+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List getTestTypeByProjectIdJSON(int projectid){
		String sql = "select testtypeid ,testtypename from testtype where (status <> 1 or status is null) and projectid= '"+projectid+"'";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	@Override
	public int getTotalCases() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getTestPlanStatus() {
		
		return null;
	}
	public List getTestPlanStatus(String project,String releaseCycle){
		
		String sql = "select adddate(b.enddate,0)>=DATE_FORMAT(NOW(),'%Y-%m-%d') as isBufer " +
				"from testplan b where b.project = '"+project+"' and b.releasecycle = '"+releaseCycle+"' ";
		int isBufer;
		try {
			isBufer = db.queryForInt(sql);
			
			if(isBufer >=1){
				sql = "select a.createdate,a.totalcases,a.pass,a.fail,a.notrun,a.block,a.passrate from testplanstatus a where " +
				"a.project = '"+project+"' and a.releasecycle = '"+releaseCycle+"' " +
				"union all " +
				"select DATE_FORMAT(NOW(),'%Y-%m-%d') as createdate,b.totalcases,b.pass,b.fail,b.notrun,b.block,b.passrate from testplan b where " +
				"b.project = '"+project+"' and b.releasecycle = '"+releaseCycle+"'";
				
				String tmpsql = "select DATE_FORMAT(adddate(now(),1),'%Y-%m-%d') as startdate,b.enddate from testplan b where " +
				"b.project = '"+project+"' and b.releasecycle = '"+releaseCycle+"'";
				
				List widthdate = db.queryForList(tmpsql);
				if(null!=widthdate && widthdate.size()>0){
					LinkedHashMap<?, ?> lhm = (LinkedHashMap<?, ?>)widthdate.get(0);
					String startdate = lhm.get("startdate")==null ? "":lhm.get("startdate").toString();
					String enddate = lhm.get("enddate")==null ? "":lhm.get("enddate").toString();
					
					try {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date sdate = df.parse(startdate);
						Date edate = df.parse(enddate);
						long chadate = (edate.getTime()-sdate.getTime());
						long gapdate = chadate/(1000 * 60 * 60 * 24);
						int num = new Long(gapdate).intValue();
						for(int i = num ;i>=0 ;i--){
							if(i>=0){
								sql+=" union all ";
							}
							String thedate = formateDate(edate,i);
							sql+=" select '"+thedate+"' as createdate,0 as totalcases,0 as pass,0 as fail,0 as notrun,0 as block,0 as passrate";
							
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				sql = "select * from (select a.createdate,a.totalcases,a.pass,a.fail,a.notrun,a.block,a.passrate from testplanstatus a where " +
				"a.project = '"+project+"' and a.releasecycle = '"+releaseCycle+"' and not exists (select 1 from testplan d where " +
				"d.project = '"+project+"' and d.releasecycle = '"+releaseCycle+"' and a.createdate = d.enddate )" +
				"union all " +
				"select b.enddate as createdate,b.totalcases,b.pass,b.fail,b.notrun,b.block,b.passrate from testplan b where " +
				"b.project = '"+project+"' and b.releasecycle = '"+releaseCycle+"' ) c order by createdate asc";
			}
			return db.queryForList(sql);
		} catch (DataAccessException e) {
			log.error("org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0");
			
		}
		return new ArrayList();
	}

	@Override
	public List getTestPlanDuration() {
		// TODO Auto-generated method stub
		return null;
	}
	public List getTestPlanDuration(String project,String releaseCycle) {
		String sql = "select startdate,enddate " +
		"from testplan b where b.project = '"+project+"' and b.releasecycle = '"+releaseCycle+"' ";
		System.out.println(sql);
		return db.queryForList(sql);
	}
	
	public List query(String sql){
		return db.queryForList(sql);
	}
	public String formateDate(Date date,int gap){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - gap);
		return dft.format(calendar.getTime());
	}
}