package com.idove.services.users.domain;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * 用户实体类
 * @author idove
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

	private Long userId;
	private String userName;
	private Integer age;

	public User() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
