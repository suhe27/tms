package com.intel.cid.common.bean;



public class SubTestExecution {

	private int subExecutionId;
	
	private String subExecutionName;
	
	private String releaseCycle ;
	
	private int executionId ;
	
	private int projectId;
	
	private int subPlanId;
	
	private int platformId;
	
	private int envId;
	
	private int nicId;
	
	private int phaseId;
	
	private int osId;
	
	private int tester;
	
	private int pass;
	
	private int fail;
	
	private int block;
	
	private int notRun;
	
	private int totalCases;
	
	private double passRate;
	
	private double executeRate;
	
	private String dueDate;
	
	private String createDate;
	
	private String modifyDate;
	
	private String desc;
	
	private TestExecution testExecution;
	
	private Project project;
	
	private SubTestPlan subTestPlan;
	
	private Platform platform;
	
	private TestEnv env;
	
	private Nic nic;
	
	private ExecutionPhase executionPhase;
	
	private ExecutionOS executionOS ;
	
	private User user;

	public int getSubExecutionId() {
		return subExecutionId;
	}

	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}

	public String getSubExecutionName() {
		return subExecutionName;
	}

	public void setSubExecutionName(String subExecutionName) {
		this.subExecutionName = subExecutionName;
	}

	public String getReleaseCycle() {
		return releaseCycle;
	}

	public void setReleaseCycle(String releaseCycle) {
		this.releaseCycle = releaseCycle;
	}

	public int getExecutionId() {
		return executionId;
	}

	public void setExecutionId(int executionId) {
		this.executionId = executionId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getSubPlanId() {
		return subPlanId;
	}

	public void setSubPlanId(int subPlanId) {
		this.subPlanId = subPlanId;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public int getEnvId() {
		return envId;
	}

	public void setEnvId(int envId) {
		this.envId = envId;
	}

	public int getNicId() {
		return nicId;
	}

	public void setNicId(int nicId) {
		this.nicId = nicId;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}

	public int getTester() {
		return tester;
	}

	public void setTester(int tester) {
		this.tester = tester;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getNotRun() {
		return notRun;
	}

	public void setNotRun(int notRun) {
		this.notRun = notRun;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public double getPassRate() {
		return passRate;
	}

	public void setPassRate(double passRate) {
		this.passRate = passRate;
	}

	public double getExecuteRate() {
		return executeRate;
	}

	public void setExecuteRate(double executeRate) {
		this.executeRate = executeRate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public TestExecution getTestExecution() {
		return testExecution;
	}

	public void setTestExecution(TestExecution testExecution) {
		this.testExecution = testExecution;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public SubTestPlan getSubTestPlan() {
		return subTestPlan;
	}

	public void setSubTestPlan(SubTestPlan subTestPlan) {
		this.subTestPlan = subTestPlan;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public TestEnv getEnv() {
		return env;
	}

	public void setEnv(TestEnv env) {
		this.env = env;
	}

	public Nic getNic() {
		return nic;
	}

	public void setNic(Nic nic) {
		this.nic = nic;
	}

	public ExecutionPhase getExecutionPhase() {
		return executionPhase;
	}

	public void setExecutionPhase(ExecutionPhase executionPhase) {
		this.executionPhase = executionPhase;
	}

	public ExecutionOS getExecutionOS() {
		return executionOS;
	}

	public void setExecutionOS(ExecutionOS executionOS) {
		this.executionOS = executionOS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String logInfo() {
		return "SubTestExecution [block=" + block + ", createDate="
				+ createDate + ", desc=" + desc + ", dueDate=" + dueDate
				+ ", envId=" + envId + ", executeRate=" + executeRate
				+ ", executionId=" + executionId + ", fail=" + fail
				+ ", modifyDate=" + modifyDate + ", nic=" + nic + ", nicId="
				+ nicId + ", notRun=" + notRun + ", osId=" + osId + ", pass="
				+ pass + ", passRate=" + passRate + ", phaseId=" + phaseId
				+ ", platformId=" + platformId + ", projectId=" + projectId
				+ ", releaseCycle=" + releaseCycle + ", subExecutionId="
				+ subExecutionId + ", subExecutionName=" + subExecutionName
				+ ", subPlanId=" + subPlanId + ", tester=" + tester
				+ ", totalCases=" + totalCases + "]";
	}
	
	
	
	
	
	
	
	
}
