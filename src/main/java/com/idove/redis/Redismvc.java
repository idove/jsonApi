package com.idove.redis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.idove.utils.JsonUtil;

/**
 * 用户权限验证类
 * @author : kardel
 * @date : 2014-8-30
 * 
 */
@Repository("redismvc")
public class Redismvc {

	@Autowired
	private RedisClientTemplate redis;

	/**
	 * 验证用户权限
	 * @param userId
	 * @param module
	 * @param controller
	 * @param action
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkmvc(Long userId, String module, String controller,
			String action) {
		if (null == userId || 0 == userId) {
			return false;
		}
		String json = (String) redis.get(userId.toString());
		List<Map<String, Object>> list = JsonUtil.json2java(json, List.class);
		if (null == list || 0 == list.size()) {
			return false;
		}
		for (Map<String, Object> map : list) {
			String m = map.get("module").toString();
			String c = map.get("controller").toString();
			String a = map.get("action").toString();
			if (module.equals(m)
					&& controller.equals(c)
					&& action.equals(a)) {
				return true;
			}
		}
		return false;
	}
}
