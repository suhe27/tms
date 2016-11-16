package com.intel.cid.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.dao.impl.TeamDaoImpl;
import com.intel.cid.common.dao.impl.UserDaoImpl;

public class UserService {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(UserService.class);

	private UserDaoImpl userDaoImpl;

	private TeamDaoImpl teamDaoImpl;

	public List<User> listUserByProjectId(int projectId) throws Exception {
		return userDaoImpl.listUserByProjectId(projectId);

	}

	public List<User> listUsersByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {

		return userDaoImpl.listUsersByPage(pageBean, filterMap);
	}

	public int listUserSize(Map<String, String> filterMap) throws Exception {

		return userDaoImpl.listUserSize(filterMap);
	}

	public void addUserTeam(User user, String[] teams) throws Exception {
		int userId = userDaoImpl.addUserWithKey(user);
		userDaoImpl.addBatchUserTeam(userId, teams);

	}

	public List<User> listAllUsers() throws Exception {
		List<User> userList = userDaoImpl.queryAllUsers();
		return userList;
	}
	
	public List<User> queryAllUserType() throws Exception {
		List<User> userList = userDaoImpl.queryAllUserType();
		return userList;
	}

	public User queryUserById(int id) throws Exception {
		return userDaoImpl.queryUserById(id);
	}

	public void addUser(User user) throws Exception {
		userDaoImpl.addUser(user);
	}

	public void delUser(int id) throws Exception {
		userDaoImpl.delUserById(id);

		List<String> delTeams = new ArrayList<String>();
		List<Team> teamList = teamDaoImpl.listTeamByUserId(id);
		for (Team team : teamList) {
			delTeams.add(String.valueOf(team.getTeamId()));
		}
		userDaoImpl.deleteBatchUserTeam(id, delTeams);

	}
	
	public void updateUserNameAndPassword(User user) throws Exception {

		userDaoImpl.updateUser(user);
	}

	public void updateUser(User user, String[] teams) throws Exception {

		int userId = user.getUserId();
		List<Team> teamList = teamDaoImpl.listTeamByUserId(userId);

		List<String> delTeams = new ArrayList<String>();
		List<String> addTeams = new ArrayList<String>();

		// add
		for (int i = 0; i < teams.length; i++) {
			boolean flag = true; // default is added

			for (int j = 0; j < teamList.size(); j++) {

				if (Integer.parseInt(teams[i].trim()) == teamList.get(j)
						.getTeamId()) {
					flag = false;
					break;
				}

			}
			if (flag) {
				addTeams.add(teams[i].trim());
			}

		}
		// del
		for (int j = 0; j < teamList.size(); j++) {
			boolean flag = true; // default is deleted

			for (int i = 0; i < teams.length; i++) {

				if (Integer.parseInt(teams[i].trim()) == teamList.get(j)
						.getTeamId()) {
					flag = false;
					break;

				}

			}
			if (flag) {
				delTeams.add(String.valueOf(teamList.get(j).getTeamId()));
			}

		}

		if (delTeams.size() > 0) {

			userDaoImpl.deleteBatchUserTeam(userId, delTeams);
		}

		if (addTeams.size() > 0) {
			userDaoImpl.addBatchUserTeam(userId, addTeams);
		}
		userDaoImpl.updateUser(user);
	}

	public User queryUserByUsername(String username) throws Exception {
		return userDaoImpl.queryUserByUsername(username);
	}

	public int queryUserNameSize(User user) throws Exception {
		return userDaoImpl.queryUserNameSize(user);
	}

	public int queryUserEmailSize(User user) throws Exception {
		return userDaoImpl.queryUserEmailSize(user);
	}
	
	public User queryUserByCookie(String username, String password)
			throws Exception {

		return userDaoImpl.queryUserByCookie(username, password);
	}

	public User queryUserByLogin(String username, String password)
			throws Exception {

		return userDaoImpl.queryUserByLogin(username, password);
	}

	public User queryUserByEmail(String email) throws Exception {

		return userDaoImpl.queryUserByEmail(email);
	}

	// public String queryMailReceiver(int teamId) throws Exception {
	//
	// return userDaoImpl.queryMailReceiver(teamId);
	// }

	public List<User> queryUsersInOwnerTeam(int userId) throws Exception {

		return userDaoImpl.queryUsersInOwnerTeam(userId);
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public TeamDaoImpl getTeamDaoImpl() {
		return teamDaoImpl;
	}

	public void setTeamDaoImpl(TeamDaoImpl teamDaoImpl) {
		this.teamDaoImpl = teamDaoImpl;
	}

}
