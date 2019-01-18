package org.ants.rbacs.entity;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.utils.AuthToken;
import org.ants.common.utils.SafeUrlBase64;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * @ClassName: HeaderTokenEntity
 * @Description: 服务间通讯header token配置
 * @author: Jerry
 * @date: 2018年12月14日 上午8:55:41
 */
@Component
@ConfigurationProperties(prefix = "header-token")
public class HeaderTokenEntity {
	private static final Gson GSON = new Gson();
	
	private String key;
	private long timeout;
	
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @param tokenInfo 收到的token信息
	 * @param tokenConfig token配置参数
	 * @return
	 */
	public CustomRequestEntity parseAuthToken(String tokenInfo) {
		CustomRequestEntity customRequest = null;
		if (StringUtils.isBlank(tokenInfo)) {
			customRequest = new CustomRequestEntity();
			customRequest.setError(ErrorConstants.SE_AUTH_TOKEN);
			return customRequest;
		}
		
		/// token格式
		String[] tokenData = tokenInfo.split("\\.");
		int tokenDataLen = 2;
		if (tokenData.length != tokenDataLen) {
			customRequest = new CustomRequestEntity();
			customRequest.setError(ErrorConstants.SE_AUTH_TOKEN);
			return customRequest;
		}
		
		/// 检查签名
		String sign = AuthToken.makeSign(tokenData[0], this.getKey());
		if (!tokenData[1].equals(sign)) {
			customRequest = new CustomRequestEntity();
			customRequest.setError(ErrorConstants.SE_TOKEN_SIGN);
			return customRequest;
		}
		try {
			tokenInfo = SafeUrlBase64.decode(tokenData[0]);
			customRequest = GSON.fromJson(tokenInfo, CustomRequestEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			customRequest = new CustomRequestEntity();
			customRequest.setError(ErrorConstants.SE_AUTH_TOKEN);
			return customRequest;
		}
		
		/// 检查时间戳
		if (0 != this.getTimeout()) {
			long ts = System.currentTimeMillis();
			if (Math.abs(customRequest.getTimestamp()-ts) > this.getTimeout()) {
				customRequest = new CustomRequestEntity();
				customRequest.setError(ErrorConstants.SE_TOKEN_TIMEOUT);
				return customRequest;
			}
		}
		
		customRequest.setError(ErrorConstants.SE_SUCCESS);
		
		return customRequest;
	}
}
