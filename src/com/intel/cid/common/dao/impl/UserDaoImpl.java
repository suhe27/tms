package com.intel.cid.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import tjpu.page.bean.PageBean;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.dao.UserDao;
import com.intel.cid.utils.Utils;

public class UserDaoImpl implements UserDao {

	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	private JdbcTemplate userJdbcTemplate;
	
	public int queryUserNameSize(User user) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != user.getUserId()) {
			sql = "select count(*) from user where (status <> 1 or status is null) and userName = '"+ user.getUserName() + "'  and userId !='"
			+ user.getUserId() + "'";
			logger.info("queryByUserNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from user where (status <> 1 or status is null) and userName ='"+ user.getUserName() + "'  ";
			logger.info("queryByUserNameSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}
		logger.info("queryByUserNameSize method result:" + result);
		return size;
	}
	
	public int queryUserEmailSize(User user) throws Exception {
		String sql = "";
		int result = 0;
		int size = 0;
		if (0 != user.getUserId()) {
			sql = "select count(*) from user where (status <> 1 or status is null) and email = '"+ user.getEmail() + "'  and userId !='"
			+ user.getUserId() + "'";
			logger.info("queryByUserEmailSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		} else {
			sql = "select count(*) from user where (status <> 1 or status is null) and email ='"+ user.getEmail() + "'  ";
			logger.info("queryByUserEmailSize method result:" + sql);
			result = userJdbcTemplate.queryForInt(sql);
			if(result!=0){
				size = 1;
			}
		}
		logger.info("queryByUserEmailSize method result:" + result);
		return size;
	}

	public int addUser(final User user) throws Exception {

		String sql = "insert into user(username,password,email,level)values(?,?,?,?)";
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {

						mapUserToPs(user, ps);
					}
				});

		logger.info("result:" + result);
		return result;
	}

	public int addUserWithKey(final User user) throws Exception
	{
		
	final	String sql = "insert into user(username,password,email,level)values(?,?,?,?)";
		logger.info("sql:" + sql);
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		
		userJdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				mapUserToPs(user, ps);
				return ps;
			}
		},keyHolder);
		int result = keyHolder.getKey().intValue();
		
		return result;
	}
	
	/**
	
	public  int deleteBatchUserTeam(final int userId, final String[] teams ) throws Exception
	{
		
		
		String sql ="delete from user_team where userid=? and teamid=? ";
		logger.info("sql:"+sql);
		
		int [] results = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setInt(1, userId);
				ps.setInt(2, Integer.parseInt(teams[i].trim()));
			}
			
			@Override
			public int getBatchSize() {
			
				return teams.length;
			}
		});
		
		
		return results.length;
		
	}
	
	**/
	public  int deleteBatchUserTeam(final int userId, final List<String> teams ) throws Exception
	{
		
		
		//String sql ="delete from user_team where userid=? and teamid=? ";
		String sql = "UPDATE `user_team` SET `STATUS`='1' WHERE userid=? and teamid=? ";
		logger.info("Batch delete user_team sql:"+sql);
		
		int [] results = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setInt(1, userId);
				ps.setInt(2, Integer.parseInt(teams.get(i)));
			}
			
			@Override
			public int getBatchSize() {
			
				return teams.size();
			}
		});
		
		
		return results.length;
		
	}
	
	
	public int addBatchUserTeam(final int userId, final String[] teams) throws Exception
	{
		String sql = "insert into user_team(userid,teamid) values(?,?)";
		logger.info("sql:"+sql);
		int[] results = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setInt(1, userId);
				ps.setInt(2, Integer.parseInt(teams[i].trim()));
		
			}						
			public int getBatchSize() {
				
				return teams.length;
			}
		});
		
		return results.length;
		
		
	}
	
	
	public int addBatchUserTeam(final int userId,final List<String> teams) throws Exception
	{
		String sql = "insert into user_team(userid,teamid) values(?,?)";
		logger.info("sql:"+sql);
		int[] results = userJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setInt(1, userId);
				ps.setInt(2, Integer.parseInt(teams.get(i)));
		
			}						
			public int getBatchSize() {
				
				return teams.size();
			}
		});
		
		return results.length;
		
		
	}
	
	
	public int delUser(User user) throws Exception {

		return delUserById(user.getUserId());
	}

	public int delUserById(int id) throws Exception {

		//String sql = "delete from user where userid='" + id + "'";
		String sql = "UPDATE `user` SET `STATUS`='1' WHERE userid='" + id + "'";
		logger.info("Delete user sql:" + sql);
		int result = userJdbcTemplate.update(sql);
		logger.info("result" + result);
		return result;
	}

	public List<User> queryAllUsers() throws Exception {

		String sql = "select * from user  where (status <> 1 or status is null)";

		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				User user = new User();
				mapUserToRs(user, rs);
				return user;
			}
		});

		logger.info("result:" + userList);
		return userList;
	}

	public List<User> queryAllUserType() throws Exception {

		String sql = "select * from usertype";

		logger.info("getUserTypeList sql:" + sql);
		@SuppressWarnings("unchecked")
		List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				User user = new User();
				user.setLevel(rs.getInt("userTypeId"));
				user.setUserType(rs.getString("userTypeName"));
				return user;
			}
		});

		logger.info("result:" + userList);
		return userList;
	}	
	
	public List<User> listUserByProjectId(int projectId) throws Exception {
		
		String sql ="SELECT * FROM user WHERE EXISTS (SELECT * FROM user_team " +
				"WHERE EXISTS (SELECT * FROM project WHERE user.userid = user_team.USERID AND" +
				" user_team.TEAMID = project.TEAMID AND project.PROJECTID="+projectId+"))";
			
		logger.info("sql:"+sql);
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				User user = new User();

				mapUserToRs(user, rs);

				return user;

			}
		});

		logger.info("result:" + userList.size());
		return userList;
		
	}
	
	
	public List<User> queryUsersInOwnerTeam(int userId) throws Exception {

		String sql = "SELECT * from user where EXISTS (SELECT * FROM user_team as map WHERE EXISTS"
				+ "( SELECT * FROM user_team as map1 WHERE map.teamid =map1.teamid AND map1.userid="
				+ userId + ") and user.USERID =map.userid)";
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int count) throws SQLException {
				User user = new User();

				mapUserToRs(user, rs);

				return user;

			}
		});

		logger.info("result:" + userList);
		return userList;
	}

	// public String queryMailReceiver(int teamId) throws Exception {
	//
	// String sql = "select * from user where teamid='" + teamId + "'";
	// logger.info("queryMailReceiver method sql:" + sql);
	// @SuppressWarnings("unchecked")
	// List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {
	//
	// public Object mapRow(ResultSet rs, int count) throws SQLException {
	// User user = new User();
	//
	// mapUserToRs(user, rs);
	//
	// return user;
	//
	// }
	// });
	//
	// StringBuffer mailReceiverBuffer = new StringBuffer();
	//
	// for (int i = 0; i < userList.size(); i++) {
	//
	// User user = userList.get(i);
	// if (i == userList.size() - 1) {
	// mailReceiverBuffer.append(user.getEmail());
	// } else {
	// mailReceiverBuffer.append(user.getEmail() + ",");
	// }
	//
	// }
	//
	// return mailReceiverBuffer.toString();
	//
	// }

	public User queryUserById(int id) throws Exception {

		//String sql = "select * from user where userid='" + id + "'";
		String sql = "select a.*,b.UserTypeName from user a left join usertype b on a.LEVEL=b.UserTypeId where userid='"+id+"'";
		logger.info("sql:" + sql);
		final User user = new User();
		userJdbcTemplate.query(sql, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				mapUserToRs(user, rs);
				user.setUserType(rs.getString("userTypeName"));

			}
		});
		logger.info("result:" + user);
		return user;
	}

	public int updateUser(final User user) throws Exception {

		String sql = "update user set username=?,password=?,email=? ,level=? where userid="
				+ user.getUserId() ;
		logger.info("sql:" + sql);
		int result = userJdbcTemplate.update(sql,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						mapUserToPs(user, ps);
					}
				});
		logger.info("result:" + result);
		return result;
	}

	public User queryUserByUsername(String username) throws Exception {
		String sql = "select * from user where username='" + username + "'";

		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});

		logger.info("result:" + user);
		return user;
	}
	
	public User queryUserByUsernameExcelImport(String username) throws Exception {
		String sql = "select * from user where (status <> 1 or status is null) and username='" + username + "'";

		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});

		logger.info("result:" + user);
		return user;
	}
	
	public User queryUserInSameTeam(String name, int id) throws Exception {
		String sql = "select a.* from user a where (a.status <> 1 or a.status is null) and a.username = '"+name+"' and a.username in "
				+ "(select c.username from user_team b left join user c on c.userid = b.userid where (b.status <> 1 or b.status is null)"
				+ " and b.teamid in (SELECT d.teamid FROM user_team d where (d.status <> 1 or d.status is null) and d.userid = '"+id+"'))";
		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});

		logger.info("result:" + user);
		return user;
	}
	
	
	public User queryUserByEmail(String email) throws Exception {
		String sql = "select * from user where email='" + email + "'";

		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});

		logger.info("result:" + user);
		return user;
	}
	
	

	public List<User> listUsersByPage(PageBean pageBean,
			Map<String, String> filterMap) throws Exception {
		int itemFrom = pageBean.getItemFrom();
		int count = pageBean.getItemTo() - itemFrom + 1;

		//String sql = "select * from user as u where exists(select * from user_team as map where map.userid = u.userid %s %s " +")";
		String sql = "SELECT a.*, d.UserTypeName, GROUP_CONCAT(c.TEAMNAME SEPARATOR ' , ') as teams FROM user a left join user_team b on "
				+ "b.USERID = a.USERID left join team c on c.TEAMID = b.TEAMID left join usertype d on d.usertypeid=a.`level` where "
				+ "(a.status <> 1 or a.status is null) and (b.status <> 1 or b.status is null) %s %s group by a.USERID";
		sql = filterQueryUesrCondition1(sql, filterMap);
		sql = sql + " limit " + itemFrom + "," + count;
		logger.info("sql:" + sql);
		@SuppressWarnings("unchecked")
		List<User> userList = userJdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int i) throws SQLException {

				User user = new User();
				mapUserTeamsToRs(user, rs);
				user.setUserType(rs.getString("userTypeName"));

				return user;
			}
		});

		return userList;
	}

	public int listUserSize(Map<String, String> filterMap) throws Exception {
		
		String sql = "select count(*) from user as u where exists(select * from user_team as map where map.userid = u.userid"
				+ " and (`status` <> 1 or `status` is null) %s %s "+")";
		//String sql = "SELECT count(*) FROM user a left join user_team b on b.USERID = a.USERID left join team c on c.TEAMID = b.TEAMID %s %s group by a.USERID";
		sql = filterQueryUesrCondition(sql, filterMap);
		logger.info("sql:" + sql);
		int res = userJdbcTemplate.queryForInt(sql);
		return res;
	}

	public String filterQueryUesrCondition(String sql , Map<String,String> filterMap)
	{
		String teamId =filterMap.get("teamId");
		String userId = filterMap.get("userId");
		

		if (Utils.isNullORWhiteSpace(teamId) || teamId.trim().equals("-1") ||teamId.trim().equals("0") ) {

			sql = String.format(sql, "","%s");

		} else {
			sql = String.format(sql, "  and map.teamid=" + teamId
					 ,"%s");

		}

		if (Utils.isNullORWhiteSpace(userId) || userId.trim().equals("-1")|| userId.trim().equals("0")) {

			sql = String.format(sql, "");

		} else {
			sql = String.format(sql, "  and u.userid=" + userId);

		}

		return   sql ;
		
	}
	
	public String filterQueryUesrCondition1(String sql , Map<String,String> filterMap)
	{
		String teamId =filterMap.get("teamId");
		String userId = filterMap.get("userId");
		

		if (Utils.isNullORWhiteSpace(teamId) || teamId.trim().equals("-1") ||teamId.trim().equals("0") ) {

			sql = String.format(sql, "","%s");
			
			if (Utils.isNullORWhiteSpace(userId) || userId.trim().equals("-1")|| userId.trim().equals("0")) {

				sql = String.format(sql, "");

			} else {
				sql = String.format(sql, " and a.userid=" + userId);

			}
			

		} else {
			sql = String.format(sql, "  and c.teamid=" + teamId
					 ,"%s");
			if (Utils.isNullORWhiteSpace(userId) || userId.trim().equals("-1")|| userId.trim().equals("0")) {

				sql = String.format(sql, "");

			} else {
				sql = String.format(sql, "  and a.userid=" + userId);

			}

		}



		return   sql ;
		
	}
	
	
	public User queryUserByCookie(String username, String password)
			throws Exception {
		String sql = "select * from user where username='" + username
				+ "' and password='" + password + "'";
		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});
		logger.info("result:" + user);
		return user;
	}

	public User queryUserByLogin(String username, String password)
			throws Exception {
		String sql = "select * from user where username='" + username
				+ "' and password='" + Utils.md5(password) + "'";
		logger.info("sql:" + sql);
		final User user = new User();
		user.setUserId(Integer.MIN_VALUE);

		userJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				mapUserToRs(user, rs);
			}
		});
		logger.info("result:" + user);
		return user;
	}

	public static void mapUserToPs(final User user, PreparedStatement ps)
			throws SQLException {

		if (Utils.isNullORWhiteSpace(user.getUserName())) {
			ps.setNull(1, Types.NULL);

		} else {
			ps.setString(1, user.getUserName());
		}
		if (Utils.isNullORWhiteSpace(user.getPassword())) {

			ps.setNull(2, Types.NULL);
		} else {
			ps.setString(2, user.getPassword());
		}
		if (Utils.isNullORWhiteSpace(user.getEmail())) {
			ps.setString(3, null);
		} else {
			ps.setString(3, user.getEmail());
		}

		if (user.getLevel() == 0) {
			ps.setNull(4, Types.NULL);
		} else {
			ps.setInt(4, user.getLevel());
		}
	}

	public static void mapUserToRs(final User user, ResultSet rs)
			throws SQLException {
		user.setUserId(rs.getInt("userid"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));

		user.setEmail(rs.getString("email"));
		user.setLevel(rs.getInt("level"));
	}
	
	public static void mapUserTeamsToRs(final User user, ResultSet rs)
			throws SQLException {
		user.setUserId(rs.getInt("userid"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));

		user.setEmail(rs.getString("email"));
		user.setLevel(rs.getInt("level"));
		user.setTeams(rs.getString("teams"));
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}

}
