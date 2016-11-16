package com.intel.cid.common.dao;

import java.util.List;

import com.intel.cid.common.bean.User;

public interface UserDao {

    List<User> queryAllUsers() throws Exception;

    User queryUserById(int id) throws Exception;

    int delUser(User user) throws Exception;

    int delUserById(int id) throws Exception;

    int updateUser(User user) throws Exception;

    int addUser(User user) throws Exception;
}
