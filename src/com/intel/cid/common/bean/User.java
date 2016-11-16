package com.intel.cid.common.bean;

public class User {

    /* user attribute */
    private int userId;

    private String userName;
    
    private String userType;

    private String password;

    private String email;

    private int level;
    /* user realationship members */
    private Team team;

    private String teams;
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    
    public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	
	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String logInfo() {
        return "User [email=" + email + ", password=" + password 
                + ", userId=" + userId + ", userName=" + userName + ", level=" + level +"]";
    }
    
   
}
