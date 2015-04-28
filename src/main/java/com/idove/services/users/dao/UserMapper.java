package com.idove.services.users.dao;

import java.util.List;

import com.idove.annotation.MyBatisRepository;
import com.idove.services.users.domain.User;

@MyBatisRepository("userMapper")
public interface UserMapper {

	public Integer insert();
	
	public Integer update(User user);
	
	public List<User> getAllList();
	
	public List<User> getList();
	
	public User getUser(Long userId);
}
