package com.intel.cid.common.bean;

public class Team {

    private int teamId;

    private String teamName;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String logInfo() {
        return "Team [teamId=" + teamId + ", teamName=" + teamName + "]";
    }

}
