package org.ants.common.entity;

import org.ants.common.constants.ErrorConstants;

/**
 * @Description: 统一API响应结果封装
 * @author: renkun
 * @date: 2018年12月13日上午9:28:32
 */
public class Result {
	private int code;
	private String message;
	private Object data;

	public Result() {
		this.code = ErrorConstants.SE_UNKNOW.getCode();
		this.message = ErrorConstants.SE_UNKNOW.getMsg();
	}
	public Result(ErrorConstants error) {
		this.code = error.getCode();
		this.message = error.getMsg();
	}
	public Result(int code, String msg) {
		this.code = code;
		this.message = msg;
	}
	public Result(int code, String msg, Object data) {
		this.code = code;
		this.message = msg;
		this.data = data;
	}
	
	public static Result success() {
		return new Result(ErrorConstants.SE_SUCCESS.getCode(), ErrorConstants.SE_SUCCESS.getMsg());
	}
	public static Result success(Object data) {
		return new Result(ErrorConstants.SE_SUCCESS.getCode(), ErrorConstants.SE_SUCCESS.getMsg(), data);
	}
	public static Result success(String msg,Object data) {
		return new Result(ErrorConstants.SE_SUCCESS.getCode(), msg, data);
	}
	public static Result fail(ErrorConstants error) {
		return new Result(error);
	}
	public static Result fail(int code, String msg) {
		return new Result(code, msg);
	}
	
	public int getCode() {
		return code;
	}
	
	public boolean isSuccess() {
		return (this.code == 200);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}