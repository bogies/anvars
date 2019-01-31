package org.ants.tommy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.CustomLogEntity;
import org.ants.common.entity.MembersEntity;
import org.ants.common.entity.Result;
import org.ants.tommy.entity.LoginResult;
import org.ants.tommy.session.SessionParam;
import org.ants.tommy.utils.RequestService;
import org.ants.tommy.utils.ServiceResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/s")
public class ServicesController {
	// 前台传过来的NONE参数名称
	private final String NONE_PARAM = "none";
	
	private static final Gson gson = new Gson();
	
	@ResponseBody
	@RequestMapping(value = "/do.jsd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String doService(HttpServletRequest request, HttpSession session, 
			@RequestParam Map<String, Object> params) {
		if (null == params || params.isEmpty()) {
			return gson.toJson(Result.success(ErrorConstants.SE_REQ_PARAMS));
		}
		params.remove(NONE_PARAM);
		
		MembersEntity memberInfo = (MembersEntity)session.getAttribute(SessionParam.LOGIN_USER);
		/// 要访问的服务
		ServiceResult rlt = RequestService.requestService(request, memberInfo, params);
		if (rlt.isReqSuccess()) {
			return rlt.getResMsg();
		} else {
			return gson.toJson(Result.fail(ErrorConstants.SE_INTERNAL.getCode(), rlt.getResMsg()));
		}
	}
	@ResponseBody
	@RequestMapping(value = "/login.jsd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String login(HttpServletRequest request, HttpSession session, 
			@RequestParam Map<String, Object> params) {
		if (null == params || params.isEmpty()) {
			return gson.toJson(Result.success(ErrorConstants.SE_REQ_PARAMS));
		}
		
		MembersEntity user = (MembersEntity)session.getAttribute(SessionParam.LOGIN_USER);
		if (null != user) {
			return gson.toJson(Result.success(user));
		}
		
		CustomLogEntity customLog = new CustomLogEntity();
		customLog.setOperatorTypeLogin();
		customLog.setUsername(params.get("username").toString());
		
		ServiceResult serviceRlt = RequestService.requestService(request, user, params);
		if (serviceRlt.isReqSuccess()) {
			LoginResult rlt = gson.fromJson(serviceRlt.getResMsg(), LoginResult.class);
			if (rlt.getCode() == 200) {
				session.setAttribute(SessionParam.LOGIN_USER, rlt.getData());
			}
			return serviceRlt.getResMsg();
		} else {
			return gson.toJson(serviceRlt.getResMsg());
		}
	}
}
