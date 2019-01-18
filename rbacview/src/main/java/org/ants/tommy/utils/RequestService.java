package org.ants.tommy.utils;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.common.entity.CustomLogEntity;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.utils.AuthToken;
import org.ants.common.utils.EncryptUtil;
import org.ants.common.utils.OkHttpHelper;
import org.ants.common.utils.SystemUtils;
import org.ants.tommy.entity.ConfigEntity;
import org.ants.tommy.session.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Response;

public class RequestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestService.class);
	
	public static ServiceResult requestService(HttpServletRequest request,   
			CustomLogEntity customLog, Map<String, Object> params) {
		/// 要访问的服务
		String uri = request.getHeader(RequestHeaderConstants.REQUEST_URI);
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		customLog.setClientIP(clientIp);
		ConfigEntity config = AppConfig.instance().getConfig();
		/// REST 方法 
		String requestType = request.getHeader(RequestHeaderConstants.REQUEST_METHOD);
		return requestService(requestType, config.getGateway()+uri, customLog, params);
	}
	/**
	 * 检查是否有权限访问页面
	 * @param request
	 * @param userId
	 * @param page 要检查权限的页面
	 * @return
	 */
	public static ServiceResult checkAuth(HttpServletRequest request, CustomLogEntity customLog, String page) {
		ConfigEntity config =  AppConfig.instance().getConfig();
		String resId = EncryptUtil.md5(page+"get"+config.getServiceName());
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		customLog.setClientIP(clientIp);
		return RequestService.requestService("get", config.getGateway()+config.getPermitAuthUrl()+"?resId="+resId, 
				customLog, null);
	}
	private static ServiceResult requestService(String requestType, 
			String uri, CustomLogEntity customLog, Map<String, Object> params) {
		ConfigEntity config = AppConfig.instance().getConfig();
		/// 要访问的服务
		if (StringUtils.isBlank(uri)) {
			return new ServiceResult(false, ErrorConstants.SE_REQ_PARAMS.getMsg());
		}
		long startTimestamp = System.currentTimeMillis();
		
		ServiceResult rlt = null;
		/// REST 方法 
		Response response = null;
		try {
			OkHttpHelper http = OkHttpHelper.getInstance();
			http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			CustomRequestEntity reqEntity = new CustomRequestEntity();
			reqEntity.setTimestamp(System.currentTimeMillis());
			reqEntity.setCaller(config.getServiceName());
			reqEntity.setUserId(customLog.getUserId());
			String requestId = UUID.randomUUID().toString();
			http.setHeader(RequestHeaderConstants.REQUEST_ID, requestId);
			http.setHeader(RequestHeaderConstants.CLIENT_IP, customLog.getClientIP());
			http.setHeader(RequestHeaderConstants.AUTH_TOKEN, AuthToken.makeToken(reqEntity, config.getSignKey()));
			
			customLog.setRequestId(requestId);
			customLog.setServiceName(config.getServiceName());
			// 登录信息包含密码, 不要打印
			if (!customLog.isOperatorTypeLogin()) {
				customLog.setRequestParams(params.toString());
			}
			response = http.request(uri, params, requestType);
			customLog.setUptime(System.currentTimeMillis()-startTimestamp);
			if (response.isSuccessful()) {
				rlt = new ServiceResult(true, response.body().string());
				customLog.setSuccess(true);
				customLog.setResultMsg(rlt.getResMsg());
				LOGGER.info(customLog.toString());
			} else {
				rlt = new ServiceResult(false, response.message());
				customLog.setSuccess(false);
				customLog.setResultMsg(rlt.getResMsg());
				LOGGER.error(customLog.toString());
			}
		} catch (Exception e) {
			rlt = new ServiceResult(false, e.getCause().getMessage());
			customLog.setSuccess(false);
			customLog.setResultMsg(rlt.getResMsg());
			LOGGER.error(customLog.toString());
		}
		
		return rlt;
	}
}
