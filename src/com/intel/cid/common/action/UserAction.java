package com.intel.cid.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tjpu.page.bean.PageBean;
import tjpu.page.factory.PageBeanFactory;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = -1977369345323681749L;

	private static Logger logger = Logger.getLogger(UserAction.class);

	private int userId;

	private String userName;

	private String password;

	private String email;

	private int level;

	private User user1;

	private List<User> userList;

	private int itemSize;

	private int linkSize;

	private int currPage;
	
	private int teamId;
	
	private String multiTeam;

	public String toLogin() throws Exception {
		return SUCCESS;
	}

	
	public String frameUsers() throws Exception{
		ActionContext context = ActionContext.getContext();
		User iUser = (User) context.getSession().get("user");
		if (iUser ==null) {
			return LOGIN;
		}
		List<Team> teamList = teamService.listTeams();
		List<User> userList = userService.listAllUsers();
		context.put("teamList", teamList);
		context.put("userList", userList);
		
		return SUCCESS;
	}
	
	public String toRegister() throws Exception {
		ActionContext context = ActionContext.getContext();
		List<Team> teamList = teamService.listTeams();

		if (teamList != null && teamList.size() > 0) {
			context.put("teamList", teamList);
			return SUCCESS;
		} else {
			context.put(Constant.ERRORMSG, "one user must belong to a team!");
			return ERROR;
		}

	}

	public String register() throws Exception {

		ActionContext context = ActionContext.getContext();

		userName = userName.trim();
		password = password.trim();
		email = email.trim();

		User user = userService.queryUserByUsername(userName);

		if (user.getUserId() != Integer.MIN_VALUE
				&& user.getEmail().equals(email)) {
			context
					.put("errorMsg",
							"Email address already exists. please choose one different !");
			List<Team> teamList = teamService.listTeams();
			context.put("teamList", teamList);
			return ERROR;
		} else {
			user.setUserName(userName);
			user.setPassword(Utils.md5(password));
			user.setEmail(email);
		
			user.setLevel(level);
			userService.addUser(user);
		}

		return SUCCESS;
	}

	public String login() throws Exception {
		ActionContext context = ActionContext.getContext();
		User iUser = (User) context.getSession().get("user");

		if (!Utils.isNullORWhiteSpace(userName)&& !Utils.isNullORWhiteSpace(password)) {
			
			
			User user = userService.queryUserByLogin(userName, password);

			if (user.getUserId() != Integer.MIN_VALUE) {
				StringBuilder teamBuilder = new StringBuilder();
				List<Team> teamList = teamService.listTeamByUserId(user.getUserId());
				for (Team team : teamList) {
					teamBuilder.append(team.getTeamName());
				}
				user.setTeams(teamBuilder.toString());
				context.getSession().put("user", user);
				
			} else {
				context.put("errorMsg",
						"Access denied, userName or password error.");
				context.put("userName", userName);
				return LOGIN;
			}
			return SUCCESS;
		}

		if (iUser != null) {
			return SUCCESS;
		}
	
		return LOGIN;
	}

	public String logout() throws Exception {
		ActionContext context = ActionContext.getContext();
		//Utils.removeCookie(context);
		context.getSession().remove("user");
		return SUCCESS;
	}

	public String listUsers() throws Exception {
		logger.info("excute listUsers() method");
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("teamId", String.valueOf(teamId));
		filterMap.put("userId", String.valueOf(userId));
		int rowNum = userService.listUserSize(filterMap);
		PageBeanFactory pageFactory = new PageBeanFactory(itemSize, linkSize);
		PageBean pageBean = pageFactory.getPageBean(rowNum, currPage);
		userList = userService.listUsersByPage(pageBean, filterMap);

		context.put("pageBean", pageBean);
		context.put("teamId", teamId);
		context.put("userId", userId);
		return SUCCESS;
	}

	public String delUser() throws Exception {
		userService.delUser(userId);
		return SUCCESS;
	}

	public String toUpdateUser() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		User user1 = userService.queryUserById(userId);
		List<Team> teamList = teamService.listTeams();
		List<User> userType = userService.queryAllUserType();
		List<Team> ownerTeamList = teamService.listTeamByUserId(user1.getUserId());
		context.put("user1", user1);
		context.put("teamList", teamList);
		context.put("userType", userType);
		context.put("ownerTeamList", ownerTeamList);
		return SUCCESS;

	}
	
	public String toLoginUser() throws Exception {

		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}

		User user1 = userService.queryUserById(user.getUserId());
		List<Team> teamList = teamService.listTeams();
		List<Team> ownerTeamList = teamService.listTeamByUserId(user1.getUserId());
		context.put("user1", user1);
		context.put("ownerTeamList", ownerTeamList);
		context.put("teamList", teamList);

		return SUCCESS;

	}

	public String updateUser() throws Exception {
		ActionContext context = ActionContext.getContext();
		User u = (User) context.getSession().get("user");
		if (u == null) {
			return LOGIN;
		}
		
		User user = userService.queryUserById(userId);
		user.setEmail(email);
		user.setUserName(userName);
		user.setLevel(level);
		
		int result = userService.queryUserNameSize(user);
		if(result==1){
			context.put(Constant.ERRORMSG, "User name '" + user.getUserName() + "' already exist!");
			return ERROR;
		}
		
		int resulte = userService.queryUserEmailSize(user);
		if(resulte==1){
			context.put(Constant.ERRORMSG, "Email '" + user.getEmail() + "' already exist!");
			return ERROR;
		}
		
		String[] teams = multiTeam.split(",");
		userService.updateUser(user,teams);
		return SUCCESS;

	}
	
	public String updateUserNameAndPassword() throws Exception {
		ActionContext context = ActionContext.getContext();
		User u = (User) context.getSession().get("user");
		if (u == null) {
			return LOGIN;
		}
		
		User user = userService.queryUserById(userId);
		user.setUserName(userName.trim());
		user.setPassword(Utils.md5(password.trim()));
		
		int result = userService.queryUserNameSize(user);
		if(result==1){
			context.put(Constant.ERRORMSG, "User name '" + user.getUserName() + "' already exist!");
			return ERROR;
		}
		
		userService.updateUserNameAndPassword(user);
		return SUCCESS;

	}

	public String toAddUser() throws Exception {

	
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		if (user == null) {
			return LOGIN;
		}
		
		List<Team> teamList = teamService.listTeams();		
		List<User> userType = userService.queryAllUserType();
		context.put("userType", userType);		
		context.put("teamList", teamList);
		
		return SUCCESS;

	}

	public String addUser() throws Exception {
		
		ActionContext context = ActionContext.getContext();
		User u = (User) context.getSession().get("user");
		if (u == null) {
			return LOGIN;
		}
		
		if(Utils.isNullORWhiteSpace(userName)){
			context.put(Constant.ERRORMSG, "username can not null,please check!");
			return  ERROR;	
		}
		if(Utils.isNullORWhiteSpace(password)){
			context.put(Constant.ERRORMSG, "password can not null,please check!");
			return  ERROR;	
		}
		if(Utils.isNullORWhiteSpace(email)){
			context.put(Constant.ERRORMSG, "email can not null,please check!");
			return  ERROR;	
		}

		User user = new User();
		user.setEmail(email.trim());
		user.setPassword(Utils.md5(password.trim()));
		user.setUserName(userName.trim());
		user.setLevel(level);
		
		int result = userService.queryUserNameSize(user);
		if(result==1){
			context.put(Constant.ERRORMSG, "User name '" + user.getUserName() + "' already exist!");
			return ERROR;
		}
		
		int resulte = userService.queryUserEmailSize(user);
		if(resulte==1){
			context.put(Constant.ERRORMSG, "Email '" + user.getEmail() + "' already exist!");
			return ERROR;
		}
		
		String[] teams = multiTeam.split(",");
		userService.addUserTeam(user,teams);
		return SUCCESS;
	}

	
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	public int getItemSize() {
		return itemSize;
	}


	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}


	public int getLinkSize() {
		return linkSize;
	}


	public void setLinkSize(int linkSize) {
		this.linkSize = linkSize;
	}


	public int getCurrPage() {
		return currPage;
	}


	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}


	public String getMultiTeam() {
		return multiTeam;
	}


	public void setMultiTeam(String multiTeam) {
		this.multiTeam = multiTeam;
	}


	public int getTeamId() {
		return teamId;
	}


	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	

}
