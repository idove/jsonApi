/**
 * 
 */
package com.idove.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.idove.commons.Page;

/**
 * @author gery
 * @date 2012-10-24
 */
@SuppressWarnings("deprecation")
public class BaseAction extends SimpleFormController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String STATUS = "status"; // 返回状态键

	public static final String MESSAGE = "message"; // 返回信息键
	
	public static final String SUCCESS = "success"; // ajax返回状态
	
	public static final String FURL = "furl";

	protected String jsonMessage = null; // 向客户端返回的json形式的数据信息
	protected FrontModel<String, Object> frontModel = new FrontModel<String, Object>(); // 服务端生成的数据模型

	/**
	 * 
	 * 日期类型转换 将从页面接收到的字符串类型的日期转换成Date类型
	 */
	@InitBinder
	public void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
		super.initBinder(request, binder);
	}

	/**
	 * 设置 Page 数据
	 */
	@SuppressWarnings("unchecked")
	public Page wrapPage(HttpServletRequest request) {
		Page page = new Page();
		try {
			//设置参数
			Enumeration<String> en = request.getParameterNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = request.getParameter(key);
				if(key.equals("count")) {
					page.setCount(Long.valueOf(value));
					continue;
				}
				if(key.equals("pageSize")) {
					page.setPageSize(Integer.valueOf(value));
					continue;
				}
				if(key.equals("totalPage")) {
					page.setTotalPage(Integer.valueOf(value));
					continue;
				}
				if(key.equals("appUser") || key.equals("appPassword")) {
					continue;
				}
				if (key.equals("pageNo")) {
					continue;
				}
				//排序参数
				if(key.startsWith("orderBy.")) {
					if(value.toLowerCase().equals("desc") || value.toLowerCase().equals("asc")) {
						page.getOrderBy().put(key.replaceAll("orderBy.", ""), value);
					}
					continue;
				}
				//条件参数
				page.getCondition().put(key, (value.equals("") ? null : value.trim()));
			}
		} catch (Exception e) {
			logger.error("Wrap Page error, " + e.getMessage());
		}

		return page;
	}
	
	/**
	 * 得到所有请求参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequestParameter(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		return map;
	}
}
