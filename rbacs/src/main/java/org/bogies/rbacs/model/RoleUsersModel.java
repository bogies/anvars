package org.bogies.rbacs.model;

/**
 * @Description: Mapping ResourcesModel from database role_users table
 * @author: renkun
 * @date: 2018年12月13日上午9:23:50
 */
public class RoleUsersModel {
	private String userId;
	private String roleId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
