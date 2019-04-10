package org.bogies.tommy.entity;

import org.bogies.common.entity.MemberEntity;

public class LoginResult {
	private int code = 0;
	private String message = "";
	private MemberEntity data = null;
	public int getCode() {
		return code;
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
	public MemberEntity getData() {
		return data;
	}
	public void setData(MemberEntity data) {
		this.data = data;
	}
}
