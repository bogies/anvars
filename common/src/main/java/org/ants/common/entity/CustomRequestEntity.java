package org.ants.common.entity;

import org.ants.common.constants.ErrorConstants;

/**
 * @ClassName: CustomRequestEntity
 * @Description: 标准的服务请求头
 * @author: Jerry
 * @date: 2018年12月12日 下午5:03:32
 */
public class CustomRequestEntity {
	// 原始请求者的服务名
	private String caller;
	/*
	 * 用户的id
	 */
	private String userId;
	/*
	 * 时间戳
	 */
	private long timestamp;
	
	private ErrorConstants error;
	
	public boolean isSuccess() {
		return (error.getCode() == ErrorConstants.SE_SUCCESS.getCode());
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
}
