package org.bogies.common.entity;

public class JWTPayloadEntity {
	public static final String USER_ID = "userId";
	public static final String USERNAME = "username";
	private String userId;
	private String username;
	
	public JWTPayloadEntity(String userId, String username) {
		this.userId = userId;
		this.username = username;
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
}
