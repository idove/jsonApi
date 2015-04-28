/**
 * 
 */
package com.idove.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idove.commons.AppRuntimeException;

/**
 * @author gery
 * @date 2012-11-2
 */
public class ResponseUtil {

	private final static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	// AJAX输出，返回null
	public static void ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
			logger.debug("HTTP Response:" + content);
		} catch (IOException e) {
			throw new AppRuntimeException(e);
		}
	}

	// AJAX输出文本，返回null
	public static void ajaxText(HttpServletResponse response, String text) {
		ajax(response, text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public static void ajaxHtml(HttpServletResponse response, String html) {
		ajax(response, html, "text/html");
	}

	// AJAX输出XML，返回null
	public static void ajaxXml(HttpServletResponse response, String xml) {
		ajax(response, xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public static void ajaxJson(HttpServletResponse response, String jsonString) {
		ajax(response, jsonString, "application/json");
	}

	// 设置页面不缓存
	public static void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}
}
