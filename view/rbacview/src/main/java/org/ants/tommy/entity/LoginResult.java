package org.ants.tommy.entity;

public class LoginResult {
	private int code = 0;
	private String message = "";
	private UserEntity data = null;
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
	public UserEntity getData() {
		return data;
	}
	public void setData(UserEntity data) {
		this.data = data;
	}
}
