package org.ants.tommy.entity;

import org.ants.common.entity.MembersEntity;

public class LoginResult {
	private int code = 0;
	private String message = "";
	private MembersEntity data = null;
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
	public MembersEntity getData() {
		return data;
	}
	public void setData(MembersEntity data) {
		this.data = data;
	}
}
