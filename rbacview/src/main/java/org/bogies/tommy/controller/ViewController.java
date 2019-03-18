package org.bogies.tommy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bogies.common.entity.CustomRequestEntity;
import org.bogies.common.entity.MembersEntity;
import org.bogies.common.utils.SystemUtils;
import org.bogies.tommy.AppConfig;
import org.bogies.tommy.session.SessionParam;
import org.bogies.tommy.utils.RequestService;
import org.bogies.tommy.utils.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ViewController {
	private static final String JSP_PREFIX = "/pages/";
	private static final String HOME_URI = "/v/index.html";
	private static final String HOME_JSP = "/pages/index";
	private static final String LOGIN_JSP = "/login/login";
	private static final String NOT_FOUND = "404";
	
	@RequestMapping(value = "/v/{page}.html", method = RequestMethod.GET)
	public String showPage(Model m, HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String page) {
		if (StringUtils.isBlank(page)) {
			return HOME_URI;
		}
		
		MembersEntity userInfo = (MembersEntity)request.getSession().getAttribute(SessionParam.LOGIN_USER);
		if (!AppConfig.instance().getConfig().isPermitAllUrl(page)) {
			String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
					request.getHeader("X-Forwarded-For"), 
					request.getRemoteAddr());
			
			/// 检查权限
			CustomRequestEntity customReq = new CustomRequestEntity();
			customReq.setOperatorTypeLogin();
			customReq.setRequestId();
			customReq.setClientIP(clientIp);
			if (null != userInfo) {
				customReq.setUserId(userInfo.getId());
				customReq.setUsername(userInfo.getUsername());
			}
			ServiceResult rlt = RequestService.checkAuth(customReq, page);
			if (!rlt.isReqSuccess()) {
				return NOT_FOUND;
			}
		}
		
		page = page.replace("-", "/");
		
		m.addAttribute("webConfig", AppConfig.instance().getWebConfig());
		m.addAttribute("userInfo", userInfo);
		
		return JSP_PREFIX+page;
	}
	@RequestMapping(value = "/i/{page}.html", method = RequestMethod.GET)
	public String renderPage(Model m, HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String page) {
		if (StringUtils.isBlank(page)) {
			return HOME_URI;
		}
		
		page = page.toLowerCase();
		
		// 判断开始不能为 /pages, 防止越权访问
		if (page.startsWith("/pages")) {
			return NOT_FOUND;
		}
		
		page = page.replace("-", "/");
		if (StringUtils.isBlank(page)) {
			/// 这里应该返回404错误页面
			return NOT_FOUND;
		}
		MembersEntity userInfo = (MembersEntity)request.getSession().getAttribute(SessionParam.LOGIN_USER);
		
		m.addAttribute("webConfig", AppConfig.instance().getWebConfig());
		m.addAttribute("userInfo", userInfo);
		
		return page;
	}
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login(Model m, HttpServletRequest request, HttpSession session, 
			HttpServletResponse response, String returnUrl) {
		MembersEntity user = (MembersEntity)session.getAttribute(SessionParam.LOGIN_USER);
		m.addAttribute("webConfig", AppConfig.instance().getWebConfig());
		if (StringUtils.isEmpty(returnUrl)) {
			returnUrl = request.getHeader("cookie");
			if (null!=returnUrl && returnUrl.startsWith("http")) {
				m.addAttribute("returnUrl", returnUrl);
			} else {
				m.addAttribute("returnUrl", "");
			}
		} else {
			m.addAttribute("returnUrl", returnUrl);
		}
		
		
		String viewUri = returnUrl;
		if (null == user) {
			return LOGIN_JSP;
		} else {
			if (StringUtils.isBlank(returnUrl)) {
				returnUrl = HOME_JSP;
				viewUri = HOME_URI;
			} else {
				returnUrl = returnUrl.replace("-", "/");
				returnUrl = returnUrl.replace(".html", ".jsp");
			}
			try {
				response.sendRedirect(viewUri);
			} catch (Exception e) {
				
			}
			return returnUrl;
		}
	}
	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logout(Model m, HttpServletRequest request, HttpSession session, 
			HttpServletResponse response) {
		session.setAttribute(SessionParam.LOGIN_USER, null);
		try {
			response.sendRedirect(HOME_URI);
		} catch (Exception e) {
			
		}
		return HOME_JSP;
	}
}
