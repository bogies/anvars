package org.ants.common.entity;

import org.ants.common.constants.ErrorConstants;

/**
 * @ClassName: CustomRequestEntity
 * @Description: 标准的服务请求头
 * @author: Jerry
 * @date: 2018年12月12日 下午5:03:32
 */
public class CustomRequestEntity {
	/**
	{
		"requestId": 100044,
		"time": "2018-12-04 12:33:55",
		"caller":"gateway",
		"userId":"wangw",
		"clientIp":"141.3.119.22"
	}*/
	private String requestId;
	private long timestamp;
	private String caller;
	private String userId;
	private String clientIp;
	private String sign;
	
	private ErrorConstants error;
	
	public boolean isSuccess() {
		return (error.getCode() == ErrorConstants.SE_SUCCESS.getCode());
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public ErrorConstants getError() {
		return error;
	}
	public void setError(ErrorConstants error) {
		this.error = error;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
