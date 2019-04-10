package org.bogies.tommy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.constants.RequestHeaderConstants;
import org.bogies.common.entity.CustomRequestEntity;
import org.bogies.common.entity.MemberEntity;
import org.bogies.common.entity.Result;
import org.bogies.common.utils.SystemUtils;
import org.bogies.tommy.AppConfig;
import org.bogies.tommy.entity.LoginResult;
import org.bogies.tommy.session.SessionParam;
import org.bogies.tommy.utils.RequestService;
import org.bogies.tommy.utils.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/s")
public class ServicesController {
	// 前台传过来的NONE参数名称
	private final String NONE_PARAM = "none";
	
	/**
	 * nginx auth_request模块登录验证
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/auth.jsd", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<HttpStatus> auth(HttpServletRequest request, HttpSession session) {
		MemberEntity memberInfo = (MemberEntity)session.getAttribute(SessionParam.LOGIN_USER);
		ResponseEntity<HttpStatus> res;
		if (null != memberInfo) {
			res = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} else {
			res = new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/do.jsd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String doService(HttpServletRequest request, HttpSession session, 
			@RequestParam Map<String, Object> params) {
		if (null == params || params.isEmpty()) {
			return JSON.toJSONString(Result.success(ErrorConstants.SE_REQ_PARAMS));
		}
		params.remove(NONE_PARAM);
		
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		
		CustomRequestEntity customReq = new CustomRequestEntity();
		customReq.setRequestId();
		customReq.setClientIP(clientIp);
		customReq.setRequestUri(request.getHeader(RequestHeaderConstants.REQUEST_URI));
		customReq.setRequestMethod(request.getHeader(RequestHeaderConstants.REQUEST_METHOD));
		customReq.setRequestParams(params);
		MemberEntity memberInfo = (MemberEntity)session.getAttribute(SessionParam.LOGIN_USER);
		if (null != memberInfo) {
			customReq.setUserId(memberInfo.getId());
			customReq.setUsername(memberInfo.getUsername());
		}
		/// 要访问的服务
		ServiceResult rlt = RequestService.requestService(customReq);
		if (rlt.isReqSuccess()) {
			return rlt.getResMsg();
		} else {
			return JSON.toJSONString(Result.fail(ErrorConstants.SE_INTERNAL.getCode(), rlt.getResMsg()));
		}
	}
	@ResponseBody
	@RequestMapping(value = "/login.jsd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String login(HttpServletRequest request, HttpSession session, 
			@RequestParam Map<String, Object> params) {
		if (null == params || params.isEmpty()) {
			return JSON.toJSONString(Result.success(ErrorConstants.SE_REQ_PARAMS));
		}
		
		MemberEntity user = (MemberEntity)session.getAttribute(SessionParam.LOGIN_USER);
		if (null != user) {
			return JSON.toJSONString(Result.success(user));
		}
		
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		
		CustomRequestEntity customReq = new CustomRequestEntity();
		customReq.setOperatorTypeLogin();
		customReq.setRequestId();
		customReq.setClientIP(clientIp);
		customReq.setUsername(params.get("username").toString());
		customReq.setRequestUri(AppConfig.instance().getConfig().getLoginUrl());
		customReq.setRequestMethod("POST");
		customReq.setRequestParams(params);
		
		ServiceResult serviceRlt = RequestService.requestService(customReq);
		if (serviceRlt.isReqSuccess()) {
			LoginResult rlt = JSON.parseObject(serviceRlt.getResMsg(), LoginResult.class);
			if (rlt.getCode() == 200) {
				session.setAttribute(SessionParam.LOGIN_USER, rlt.getData());
			}
			return serviceRlt.getResMsg();
		} else {
			return JSON.toJSONString(serviceRlt.getResMsg());
		}
	}
}
