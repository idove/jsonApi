package com.idove.services.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idove.services.users.dao.UserMapper;
import com.idove.services.users.domain.User;

/**
 * 
 * @author think
 * @date   2015-04-27
 */
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public List<User> getAllUsers(){
		return userMapper.getAllList();
	}
	
	public List<User> getUsers(){
		return userMapper.getList();
	}
}
