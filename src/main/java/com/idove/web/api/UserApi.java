package com.idove.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idove.services.users.service.UserService;
import com.idove.utils.JsonUtil;
import com.idove.utils.ResponseUtil;
import com.idove.utils.XmlUtil;
import com.idove.web.BaseAction;
import com.idove.web.FrontModel;

@Controller
@RequestMapping("api/user")
public class UserApi extends BaseAction {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "all/{dataType}", method = RequestMethod.GET)
	public void getAllUsers(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("dataType") String dataType) {
		frontModel = new FrontModel<String, Object>();
		frontModel.put("list", userService.getAllUsers());
		if ("json".equals(dataType)) {
			String jsonMessage = null;
			jsonMessage = JsonUtil.java2json(frontModel);
			ResponseUtil.ajaxJson(response, jsonMessage);
		} else if ("xml".equals(dataType)) {
			String xmlMessage = null;
			xmlMessage = XmlUtil.java2xml(frontModel);
			ResponseUtil.ajaxXml(response, xmlMessage);
		} else {
			ResponseUtil.ajaxHtml(response, "未知的数据格式");
		}
	}

	@RequestMapping(value = "list/{dataType}", method = RequestMethod.GET)
	public void getUsers(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("dataType") String dataType) {
		frontModel = new FrontModel<String, Object>();
		frontModel.put("list", userService.getUsers());
		if ("json".equals(dataType)) {
			String jsonMessage = null;
			jsonMessage = JsonUtil.java2json(frontModel);
			ResponseUtil.ajaxJson(response, jsonMessage);
		} else if ("xml".equals(dataType)) {
			String xmlMessage = null;
			xmlMessage = XmlUtil.java2xml(frontModel);
			ResponseUtil.ajaxXml(response, xmlMessage);
		} else {
			ResponseUtil.ajaxHtml(response, "未知的数据格式");
		}
	}

}
