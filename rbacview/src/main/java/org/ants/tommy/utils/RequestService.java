package org.ants.tommy.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.ants.common.constants.ErrorConstants;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.common.entity.MembersEntity;
import org.ants.common.entity.Result;
import org.ants.common.utils.EncryptUtil;
import org.ants.common.utils.OkHttpHelper;
import org.ants.common.utils.ServiceAuthToken;
import org.ants.common.utils.SystemUtils;
import org.ants.tommy.AppConfig;
import org.ants.tommy.entity.ConfigEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import okhttp3.Response;

public class RequestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestService.class);
	private static final Gson GSON = new Gson();
	
	public static ServiceResult requestService(HttpServletRequest request,   
			MembersEntity memberInfo, Map<String, Object> params) {
		/// 要访问的服务
		String uri = request.getHeader(RequestHeaderConstants.REQUEST_URI);
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		ConfigEntity config = AppConfig.instance().getConfig();
		/// REST 方法 
		String requestType = request.getHeader(RequestHeaderConstants.REQUEST_METHOD);
		return requestService(requestType, config.getGateway()+uri, memberInfo, clientIp, params);
	}
	/**
	 * 检查是否有权限访问页面
	 * @param request
	 * @param userId
	 * @param page 要检查权限的页面
	 * @return
	 */
	public static ServiceResult checkAuth(HttpServletRequest request, MembersEntity memberInfo, String page) {
		ConfigEntity config =  AppConfig.instance().getConfig();
		String resId = EncryptUtil.md5(page+"get"+config.getServiceName());
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		return RequestService.requestService("get", config.getGateway()+config.getPermitAuthUrl()+"?resId="+resId, 
				memberInfo, clientIp, null);
	}
	public static Result getServiceToken() {
		String serviceName = AppConfig.instance().getConfig().getServiceName();
		long timestamp = System.currentTimeMillis();
	
		/// 要访问的服务
		ConfigEntity config =  AppConfig.instance().getConfig();
		String sign = ServiceAuthToken.makeToken(serviceName, timestamp, config.getSignKey());
		Map<String, Object> postData = new HashMap<>();
		postData.put("serviceName", serviceName);
		postData.put("timestamp", timestamp);
		postData.put("sign", sign);
		ServiceResult rltService = RequestService.requestService("POST", config.getGateway()+config.getServiceAuthUrl(), 
				null, config.getServiceName(), postData);
		if (!rltService.isReqSuccess()) {
			return Result.fail(ErrorConstants.SE_LOGIN_ERROR.getCode(), rltService.getResMsg());
		}
		return GSON.fromJson(rltService.getResMsg(), Result.class);
	}
	private static ServiceResult requestService(String requestType, 
			String uri, MembersEntity memberInfo, String clientIp, Map<String, Object> params) {
		/// 要访问的服务
		if (StringUtils.isBlank(uri)) {
			return new ServiceResult(false, ErrorConstants.SE_REQ_PARAMS.getMsg());
		}
		ServiceResult rlt = null;
		/// REST 方法 
		Response response = null;
		try {
			OkHttpHelper http = OkHttpHelper.getInstance();
			http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			String requestId = UUID.randomUUID().toString();
			http.setHeader(RequestHeaderConstants.REQUEST_ID, requestId);
			http.setHeader(RequestHeaderConstants.USER_IP, clientIp);
			if (null != memberInfo) {
				http.setHeader(RequestHeaderConstants.USER_ID, memberInfo.getId());
				http.setHeader(RequestHeaderConstants.USERNAME, memberInfo.getUsername());
			}
			http.setHeader(RequestHeaderConstants.CALLER, AppConfig.instance().getConfig().getServiceName());
			http.setHeader(RequestHeaderConstants.AUTH, AppConfig.instance().getToken());
			
			// 登录信息包含密码, 不要打印
			
			response = http.request(uri, params, requestType);
			if (response.isSuccessful()) {
				rlt = new ServiceResult(true, response.body().string());
				LOGGER.info(rlt.getResMsg());
			} else {
				rlt = new ServiceResult(false, response.message());
				LOGGER.error(rlt.getResMsg());
			}
		} catch (Exception e) {
			rlt = new ServiceResult(false, e.getCause().getMessage());
			LOGGER.error(rlt.getResMsg());
		}
		
		return rlt;
	}
}
