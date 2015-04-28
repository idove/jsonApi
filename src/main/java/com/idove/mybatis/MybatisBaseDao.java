package com.idove.mybatis;

import java.io.Serializable;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class MybatisBaseDao extends SqlSessionDaoSupport{
	
	protected int insert(String key, Object object) {
		return getSqlSession().insert(getKey() + key, object);
	}
	
	protected int delete(String key, Serializable id) {
		return getSqlSession().delete(getKey() + key, id);
	}

	protected int delete(String key, Object object) {
		return getSqlSession().delete(getKey() + key, object);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T get(String key, Object object) {
		return (T) getSqlSession().selectOne(getKey() + key, object);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T get(String key) {
		return (T) getSqlSession().selectOne(getKey() + key);
	}

	protected <T> List<T> getList(String key) {
		return getSqlSession().selectList(getKey() + key);
	}
	
	protected <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(getKey() + key, params);
	}
	
	protected int update(String key){
		return getSqlSession().update(getKey() + key);
	}
	
	protected int update(String key, Object object){
		return getSqlSession().update(getKey() + key, object);
	}
	
	private String getKey() {
		return this.getClass().getName() + ".";
	}
}
