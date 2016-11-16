package com.intel.cid.common.bean;

public class Board {

	private int boardId;

	private String crbType;

	private String ixaNo;

	private String location;

	private String os;

	private int userId;

	private int teamId;

	private String boardName;

	private String powerCycle;

	private String ipAddr;

	private String packageInfo;

	private String smartBitOrSTC;

	private String remoteHost;

	private String ear;

	private String biosVersion;

	private String siliconType;

	private int siliconNum;

	private int stateId;

	private String startDate;

	private String endDate;

	private String comments;

	private User user;

	private BoardState boardState;

	private Team team;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getCrbType() {
		return crbType;
	}

	public void setCrbType(String crbType) {
		this.crbType = crbType;
	}

	public String getIxaNo() {
		return ixaNo;
	}

	public void setIxaNo(String ixaNo) {
		this.ixaNo = ixaNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getPowerCycle() {
		return powerCycle;
	}

	public void setPowerCycle(String powerCycle) {
		this.powerCycle = powerCycle;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getSmartBitOrSTC() {
		return smartBitOrSTC;
	}

	public void setSmartBitOrSTC(String smartBitOrSTC) {
		this.smartBitOrSTC = smartBitOrSTC;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getEar() {
		return ear;
	}

	public void setEar(String ear) {
		this.ear = ear;
	}

	public String getBiosVersion() {
		return biosVersion;
	}

	public void setBiosVersion(String biosVersion) {
		this.biosVersion = biosVersion;
	}

	public String getSiliconType() {
		return siliconType;
	}

	public void setSiliconType(String siliconType) {
		this.siliconType = siliconType;
	}

	public int getSiliconNum() {
		return siliconNum;
	}

	public void setSiliconNum(int siliconNum) {
		this.siliconNum = siliconNum;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BoardState getBoardState() {
		return boardState;
	}

	public void setBoardState(BoardState boardState) {
		this.boardState = boardState;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String logInfo() {
		return "Board [biosVersion=" + biosVersion + ", boardId=" + boardId
				+ ", comments=" + comments + ", crbType=" + crbType + ", ear="
				+ ear + ", endDate=" + endDate + ", ipAddr=" + ipAddr
				+ ", ixaNo=" + ixaNo + ", os=" + os + ", packageInfo="
				+ packageInfo + ", powerCycle=" + powerCycle + ", remoteHost="
				+ remoteHost + ", siliconNum=" + siliconNum + ", siliconType="
				+ siliconType + ", smartBitOrSTC=" + smartBitOrSTC
				+ ", startDate=" + startDate + ", stateid=" + stateId
				+ ", userId=" + userId + "]";
	}

}
