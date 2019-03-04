package org.ants.common.entity;

import java.util.Map;
import java.util.UUID;

public class CustomRequestEntity {
	private static final String DATA_INVALID = "0";
	private static final String OPEARTOR_TYPE_LOGIN = "登录";
	private static final String OPEARTOR_TYPE_ADD = "增加";
	private static final String OPEARTOR_TYPE_DELETE = "删除";
	private static final String OPEARTOR_TYPE_UPDATE = "修改";
	private static final String OPEARTOR_TYPE_GET = "查看";
	
	private String requestId;
	private String requestUri;
	private String requestMethod;
	private String clientIP;
	private String serviceName;
	private String userId = DATA_INVALID;
	private String username = DATA_INVALID;
	private String operatorType;
	// 操作内容
	private String operatorDesc = DATA_INVALID;
	private Map<String, Object> requestParams;
	// 结果
	private boolean success;
	private String resultMsg = DATA_INVALID;
	// 耗时
	private long uptime = -1;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setRequestId() {
		this.requestId = UUID.randomUUID().toString();
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public void setOperatorTypeAdd() {
		this.operatorType = OPEARTOR_TYPE_ADD;
	}
	public void setOperatorTypeDelete() {
		this.operatorType = OPEARTOR_TYPE_DELETE;
	}
	public void setOperatorTypeUpdate() {
		this.operatorType = OPEARTOR_TYPE_UPDATE;
	}
	public void setOperatorTypeGet() {
		this.operatorType = OPEARTOR_TYPE_GET;
	}
	public void setOperatorTypeLogin() {
		this.operatorType = OPEARTOR_TYPE_LOGIN;
	}
	public boolean isOperatorTypeLogin() {
		return (OPEARTOR_TYPE_LOGIN == this.operatorType);
	}
	public String getOperatorDesc() {
		return operatorDesc;
	}
	public void setOperatorDesc(String operatorDesc) {
		this.operatorDesc = operatorDesc;
	}
	public Map<String, Object> getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(Map<String, Object> requestParams) {
		this.requestParams = requestParams;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public long getUptime() {
		return uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
}
