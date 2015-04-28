/**
 * 
 */
package com.idove.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.idove.utils.JsonUtil;
import com.idove.utils.ResponseUtil;
import com.idove.web.ExecuteStatus;
import com.idove.web.FrontModel;

/**
 * @author gery
 * @date 2012-11-2
 */
public class AppExceptionResolver implements HandlerExceptionResolver {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 统一处理从抛出的未被捕获的异常
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		FrontModel<String, Object> frontModel = new FrontModel<String, Object>();
		logger.error(null, e);
		frontModel.put("success", false);
		if(e instanceof AppRuntimeException) {
			AppRuntimeException ex = (AppRuntimeException)e;
			frontModel.put("status", ex.getStatus().getCode() == null?2000:ex.getStatus().getCode());
			frontModel.put("message", ex.getMessage() == null ? ex.getStatus().getMsg() : ex.getMessage());
		} else if(e instanceof BindException){
			frontModel.put("status", ExecuteStatus.DATABINDEXCEPTION.getCode());
			frontModel.put("message", ExecuteStatus.DATABINDEXCEPTION.getMsg());
		} else if (e instanceof ClassCastException) {
			frontModel.put("status", ExecuteStatus.URLERROR.getCode());
			frontModel.put("message", ExecuteStatus.URLERROR.getMsg());
		} else if (e instanceof JedisConnectionException){
			frontModel.put("status", ExecuteStatus.JEDISCONNECTIONFAIL.getCode());
			frontModel.put("message", ExecuteStatus.JEDISCONNECTIONFAIL.getMsg());
		} else if(e instanceof Exception) {
			frontModel.put("status", ExecuteStatus.FAIL.getCode());
			frontModel.put("message", ExecuteStatus.FAIL.getMsg());
		} else {
			frontModel.put("status", ExecuteStatus.SERVERERROR.getCode());
			frontModel.put("message", ExecuteStatus.SERVERERROR.getMsg());
		}
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = false;
		if (null != requestType) {
			isAjax = true;
		}
		if (isAjax) {
			String jsonMessage = JsonUtil.java2json(frontModel);
			ResponseUtil.ajaxJson(response, jsonMessage);
			return null;
		} else {
			String contextPath = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ contextPath + "/";
			return new ModelAndView(basePath+"error",frontModel);
		}
	}
}
