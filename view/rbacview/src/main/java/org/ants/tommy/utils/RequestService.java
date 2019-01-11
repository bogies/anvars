package org.ants.tommy.utils;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.utils.AuthToken;
import org.ants.common.utils.EncryptUtil;
import org.ants.common.utils.OkHttpHelper;
import org.ants.common.utils.SystemUtils;
import org.ants.tommy.entity.ConfigEntity;
import org.ants.tommy.session.AppConfig;
import org.apache.commons.lang3.StringUtils;
import okhttp3.Response;

public class RequestService {
	
	public static ServiceResult requestService(HttpServletRequest request,   
			String userId, Map<String, Object> params) {
		/// 要访问的服务
		String uri = request.getHeader(RequestHeaderConstants.REQUEST_URI);
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		ConfigEntity config = AppConfig.instance().getConfig();
		/// REST 方法 
		String requestType = request.getHeader(RequestHeaderConstants.REQUEST_METHOD);
		return requestService(requestType, config.getGateway()+uri, userId, 
				clientIp, params);
	}
	/**
	 * 检查是否有权限访问页面
	 * @param request
	 * @param userId
	 * @param page 要检查权限的页面
	 * @return
	 */
	public static ServiceResult checkAuth(HttpServletRequest request, String userId, String page) {
		ConfigEntity config =  AppConfig.instance().getConfig();
		String resId = EncryptUtil.md5(page+"get"+config.getServiceName());
		String clientIp = SystemUtils.getClientIp(request.getHeader("X-Real-IP"), 
				request.getHeader("X-Forwarded-For"), 
				request.getRemoteAddr());
		return RequestService.requestService("get", config.getGateway()+config.getPermitAuthUrl()+"?resId="+resId, 
				userId, clientIp, null);
	}
	private static ServiceResult requestService(String requestType, 
			String uri, String userId, String clientIp, Map<String, Object> params) {
		ConfigEntity config = AppConfig.instance().getConfig();
		/// 要访问的服务
		if (StringUtils.isBlank(uri)) {
			return new ServiceResult(false, ErrorConstants.SE_REQ_PARAMS.getMsg());
		}
		/// REST 方法 
		Response response = null;
		try {
			OkHttpHelper http = OkHttpHelper.getInstance();
			http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			CustomRequestEntity reqEntity = new CustomRequestEntity();
			reqEntity.setUserId(userId);
			reqEntity.setRequestId(UUID.randomUUID().toString());
			reqEntity.setTimestamp(System.currentTimeMillis());
			reqEntity.setCaller(config.getServiceName());
			http.setHeader(RequestHeaderConstants.AUTH_TOKEN, AuthToken.makeToken(reqEntity, config.getSignKey()));
			
			response = http.request(uri, params, requestType);
			if (response.isSuccessful()) {
				return new ServiceResult(true, response.body().string());
			} else {
				return new ServiceResult(false, response.message());
			}
		} catch (Exception e) {
			return new ServiceResult(false, e.getMessage());
		}
	}
}
