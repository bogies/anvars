package org.bogies.tommy.utils;

import java.util.HashMap;
import java.util.Map;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.constants.RequestHeaderConstants;
import org.bogies.common.entity.CustomRequestEntity;
import org.bogies.common.entity.Result;
import org.bogies.common.utils.EncryptUtil;
import org.bogies.common.utils.OkHttpHelper;
import org.bogies.common.utils.ServiceAuthToken;
import org.bogies.tommy.AppConfig;
import org.bogies.tommy.entity.ConfigEntity;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import okhttp3.Response;

public class RequestService {
	/**
	 * 检查是否有权限访问页面
	 * @param request
	 * @param userId
	 * @param page 要检查权限的页面
	 * @return
	 */
	public static ServiceResult checkAuth(CustomRequestEntity customReq, String page) {
		ConfigEntity config =  AppConfig.instance().getConfig();
		String resId = EncryptUtil.md5(page+"get"+config.getServiceName());
		customReq.setRequestUri(config.getPermitAuthUrl()+"?resId="+resId);
		customReq.setRequestMethod("GET");
		return RequestService.requestService(customReq);
	}
	public static Result getServiceToken(CustomRequestEntity customReq) {
		String serviceName = AppConfig.instance().getConfig().getServiceName();
		long timestamp = System.currentTimeMillis();
		/// 要访问的服务
		ConfigEntity config =  AppConfig.instance().getConfig();
		String sign = ServiceAuthToken.makeToken(serviceName, timestamp, config.getSignKey());
		Map<String, Object> postData = new HashMap<>();
		postData.put("serviceName", serviceName);
		postData.put("timestamp", timestamp);
		postData.put("sign", sign);
		customReq.setRequestUri(config.getServiceAuthUrl());
		customReq.setServiceName(serviceName);
		customReq.setRequestMethod("POST");
		customReq.setRequestParams(postData);
		ServiceResult rltService = RequestService.requestService(customReq);
		if (!rltService.isReqSuccess()) {
			return Result.fail(ErrorConstants.SE_LOGIN_ERROR.getCode(), rltService.getResMsg());
		}
		return JSON.parseObject(rltService.getResMsg(), Result.class);
	}
	public static ServiceResult requestService(CustomRequestEntity customReq) {
		/// 要访问的服务
		String reqUri = customReq.getRequestUri();
		if (StringUtils.isBlank(reqUri)) {
			return new ServiceResult(false, ErrorConstants.SE_REQ_PARAMS.getMsg());
		}
		ConfigEntity config = AppConfig.instance().getConfig();
		
		ServiceResult rlt = null;
		/// REST 方法 
		Response response = null;
		try {
			OkHttpHelper http = OkHttpHelper.getInstance();
			http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			http.setHeader(RequestHeaderConstants.REQUEST_ID, customReq.getRequestId());
			http.setHeader(RequestHeaderConstants.USER_IP, customReq.getClientIP());
			http.setHeader(RequestHeaderConstants.USER_ID, customReq.getUserId());
			http.setHeader(RequestHeaderConstants.USERNAME, customReq.getUsername());
			http.setHeader(RequestHeaderConstants.CALLER, AppConfig.instance().getConfig().getServiceName());
			http.setHeader(RequestHeaderConstants.AUTH, AppConfig.instance().getToken());
			
			// 登录信息包含密码, 不要打印
			String reqUrl = config.getGateway() + reqUri;
			response = http.request(reqUrl, customReq.getRequestParams(), customReq.getRequestMethod());
			if (response.isSuccessful()) {
				rlt = new ServiceResult(true, response.body().string());
				customReq.setSuccess(true);
				customReq.setResultMsg(rlt.getResMsg());
			} else {
				rlt = new ServiceResult(false, response.message());
				customReq.setSuccess(false);
				customReq.setResultMsg(rlt.getResMsg());
			}
		} catch (Exception e) {
			Throwable cause = e.getCause();
			rlt = new ServiceResult(false, cause.getMessage());
			customReq.setSuccess(false);
			customReq.setResultMsg(rlt.getResMsg());
		}
		
		return rlt;
	}
}
