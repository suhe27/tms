package com.intel.cid.common.bean;

public class MediaParameters {

	private int id ;
	
	private String hostName;
	
	private String passWord;
	
	private String executionCommands;
	
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getExecutionCommands() {
		return executionCommands;
	}

	public void setExecutionCommands(String executionCommands) {
		this.executionCommands = executionCommands;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
