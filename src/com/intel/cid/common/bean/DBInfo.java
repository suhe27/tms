package com.intel.cid.common.bean;

public class DBInfo {
	
	private int id;
	private String alias;
	private String host;
	private String port;
	private String username;
	private String password;
	private String db_type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDb_type() {
		return db_type;
	}
	public void setDb_type(String dbType) {
		db_type = dbType;
	}
	

}
