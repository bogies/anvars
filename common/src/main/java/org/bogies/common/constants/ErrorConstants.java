package org.bogies.common.constants;

/**
 * @ClassName: ErrorConstans
 * @Description: 错误码和内容定义
 * @author: Jerry
 * @date: 2018年12月14日 上午8:22:20
 */
public enum ErrorConstants {
	/** 200, "成功" */
	SE_SUCCESS(200, "成功"), 
	/** 99999, "未知的错误" */
	SE_UNKNOW(99999, "未知的错误"), 
	/** 99999, "未知的错误" */
	SE_UNAUTHORIZED(401, "未授权的请求"), 
	/** 401, "未授权的请求" */
	SE_NOT_FOUND(404, "未知的服务"),
	/** 404, "未知的服务" */
	SE_REQ_PARAMS(412, "请求的参数不完整"),
	/** 412, "请求的参数不完整" */
	SE_INTERNAL(500, "内部错误"),
	/** 10000, "内部错误" */
	SE_AUTH_TOKEN(10000, "TOKEN错误"), 
	/** 10001, "TOKEN超时" */
	SE_TOKEN_TIMEOUT(10001, "TOKEN超时"), 
	/** 10002, "TOKEN错误" */
	SE_TOKEN_SIGN(10002, "TOKEN签名错误"),
	SE_LOGIN_ERROR(10003, "用户名或密码错误");

	private String msg;
	private int code;

	private ErrorConstants(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public int getCode() {
		return this.code;
	}

}
