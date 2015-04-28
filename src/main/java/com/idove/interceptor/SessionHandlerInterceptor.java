/**
 * 
 */
package com.idove.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.idove.annotation.Permission;
import com.idove.redis.Redismvc;
import com.idove.services.users.domain.User;
import com.idove.utils.JsonUtil;
import com.idove.utils.ResponseUtil;
import com.idove.web.FrontModel;

/**
 * @author gery
 * @date 2012-11-5
 */
public class SessionHandlerInterceptor implements HandlerInterceptor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Redismvc redismvc;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 处理Permission Annotation，实现方法级权限控制
		HandlerMethod method = (HandlerMethod) handler;
		Permission permission = method.getMethodAnnotation(Permission.class);
		logger.debug("method : " + method.getMethod());
		logger.debug("pemission is null : " + (null == permission));
		String requestType = request.getHeader("X-Requested-With");
		logger.debug("提交方式：" + requestType);
		boolean isAjax = false;
		FrontModel<String, Object> frontModel = new FrontModel<String, Object>();
		if (null != requestType) {
			isAjax = true;
		}
		
		logger.debug("是否ajax提交:" + isAjax);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		boolean flag = false;//定义session是否存在，如果存在则flag为true
		if (null != user && null != user.getUserId()
				&& 0 != user.getUserId()) {
			logger.info("Request User:" + user.getUserId());
			flag = true;
		}
		
		String contextPath = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ contextPath + "/";
		//如果permission == null ,则不验证登录和权限;
		//如果permission == false,则验证登录不验证权限;
		//如果permission == true ,则验证登录和权限。
		if (null == permission) {
			return true;
		} else if (null != permission && true == permission.value()) {
			String url = request.getRequestURI();
			if (flag) {
				String[] mvcs = url.split("/");
				logger.debug("mvcs len:" + mvcs.length);
				for (int i = 0; i < mvcs.length; i++) {
					logger.debug("mvc["+ i +"]:" + mvcs[i]);
				}
				if (redismvc.checkmvc(user.getUserId(), mvcs[2], mvcs[3], mvcs[4])) {
					return true;
				}
				logger.debug("basePath:" + basePath);
				logger.debug("未通过权限认证");
				response.sendRedirect(basePath+"nopower.jsp");
				//request.getRequestDispatcher(basePath+"nopower.jsp").forward(request,
				//		response);
				return false;
			} else {
				
				String path = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort();
				String furl = path + request.getRequestURI();
				@SuppressWarnings("unchecked")
				Enumeration<Object> enu = request.getParameterNames();
				StringBuilder sb = new StringBuilder();
				sb.append(furl);
				int i = 0;
				while (enu.hasMoreElements()) {
					String paraName = (String) enu.nextElement();
					if (i == 0) {
						sb.append("?");
					}
					if (i != 0) {
						sb.append("&");
					}
					sb.append(paraName + "=" + request.getParameter(paraName));
					i++;
				}
				furl = sb.toString();
				logger.debug("用户未登录，跳转到登录页。furl=" + furl);
				if (isAjax) {
					frontModel.put("success", false);
					frontModel.put("message", "登录已失效请重新登录");
					frontModel.put("furl", basePath + "index.jsp?furl=" + furl);
					String jsonString  = JsonUtil.java2json(frontModel);
					ResponseUtil.ajaxJson(response, jsonString);
				} else {
					response.sendRedirect(basePath + "index.jsp?furl=" + furl);
				}
				return false;
			}
		} else if (null != permission && false == permission.value()) {
			if (flag) {
				return true;
			} else {
				logger.debug("session为空且pemission也为空，跳转到登录页!");
				if (isAjax) {
					frontModel.put("success", false);
					frontModel.put("message", "登录已失效请重新登录");
					frontModel.put("furl", basePath + "index.jsp");
					String jsonString  = JsonUtil.java2json(frontModel);
					ResponseUtil.ajaxJson(response, jsonString);
				} else {
					response.sendRedirect(basePath + "index.jsp");
				}
				return false;
			}
		} else {
			return false;
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
