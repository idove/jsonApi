/**
 * 
 */
package com.idove.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.idove.utils.HttpUtil;

/**
 * @author gery
 * @date 2012-11-20
 */
public class ExecuteTimeHandlerInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();

	private Long startExecuteTime = 0L;

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		requestLocal.set(request);
		startExecuteTime = System.currentTimeMillis();
		Enumeration<Object> enu = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			sb.append("&" + paraName + "=" + request.getParameter(paraName)
					+ " ");
		}
		logger.info("Start ==>> Request IP:[" + HttpUtil.getIpAddr(request)
				+ "], Request URL:[" + request.getRequestURI()
				+ "], Request Parameter:[" + sb.toString() + "]");
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Long executeTime = System.currentTimeMillis() - startExecuteTime;
		logger.info("End ==>> " + request.getRequestURI() + ", execute time: "
				+ executeTime + "ms");
	}

}